/*
 *     Copyright (C) 2016 psygate (https://github.com/psygate)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 */

package com.psygate.minecraft.spigot.sovereignty.nucleus.services;

import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.nucleus.configuration.WorkerPoolConfiguration;
import com.psygate.minecraft.spigot.sovereignty.nucleus.logging.LogManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Created by psygate on 20.07.2016.
 */
public class WorkerPool {
    private final Logger LOG = LogManager.getInstance().getPassthroughLogger(WorkerPool.class);
    private static WorkerPool instance;
    private final ThreadGroup group;
    private final ExecutorService pool;
    private final AtomicLong threadID = new AtomicLong();

    private WorkerPool() {
        WorkerPoolConfiguration conf = Nucleus.getInstance().getConfiguration().getWorkerPoolConfiguration();
        group = new ThreadGroup("Nucleus-Worker-Pool-Group");
        pool = new ThreadPoolExecutor(
                conf.getWorkerNumber(),
                conf.getMaxWorkers(),
                conf.getMaxIdleTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                r -> {
                    Thread thread = new Thread(group, r, "NucleusWorkerPoolThread#" + threadID.getAndIncrement());
                    return thread;
                }
        );
    }

    public static WorkerPool getInstance() {
        if (instance == null) {
            instance = new WorkerPool();
        }
        return instance;
    }

    public void stop() throws InterruptedException {
        LOG.info("Awaiting threadpool termination with 5 minute timeout.");
        pool.awaitTermination(5, TimeUnit.MINUTES);
        LOG.info("Threadpool shutdown.");
    }

    public <T> Future<T> submit(Callable<T> task) {
        return pool.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return pool.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        return pool.submit(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return pool.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return pool.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return pool.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return pool.invokeAny(tasks, timeout, unit);
    }

    public void execute(Runnable command) {
        pool.execute(command);
    }
}
