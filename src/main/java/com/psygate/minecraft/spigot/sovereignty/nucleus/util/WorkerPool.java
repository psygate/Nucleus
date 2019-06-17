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

package com.psygate.minecraft.spigot.sovereignty.nucleus.util;

import com.psygate.minecraft.spigot.sovereignty.nucleus.NucleusConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by psygate (https://github.com/psygate) on 25.01.2016.
 */
public class WorkerPool {
    private static ExecutorService service;
    private static int workerPoolSize;

    public static void init(NucleusConfiguration conf) {
        workerPoolSize = conf.getWorkerPoolSize();
        service = Executors.newWorkStealingPool(workerPoolSize);
    }

    public static void shutdown() {
        service.shutdown();
    }

    public static void execute(Runnable command) {
        service.execute(new CaptureRunnable(command));
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return service.invokeAny(tasks);
    }

    public static boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return service.awaitTermination(timeout, unit);
    }

    public static <T> Future<T> submit(Runnable task, T result) {
        return service.submit(new CaptureRunnable(task), result);
    }

    public static List<Runnable> shutdownNow() {
        return service.shutdownNow();
    }

    public static Future<?> submit(Runnable task) {
        return service.submit(new CaptureRunnable(task));
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return service.submit(task);
    }

    public static boolean isShutdown() {
        return service.isShutdown();
    }

    public static boolean isTerminated() {
        return service.isTerminated();
    }

    public static <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return service.invokeAny(tasks, timeout, unit);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return service.invokeAll(tasks, timeout, unit);
    }

    public static <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return service.invokeAll(tasks);
    }

    private final static class CaptureRunnable implements Runnable {
        public CaptureRunnable(Runnable run) {
            this.run = Objects.requireNonNull(run);
        }

        private final Runnable run;

        @Override
        public void run() {
            try {
                run.run();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
