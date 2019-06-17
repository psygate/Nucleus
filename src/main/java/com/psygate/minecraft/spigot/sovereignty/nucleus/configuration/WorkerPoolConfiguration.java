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

package com.psygate.minecraft.spigot.sovereignty.nucleus.configuration;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by psygate on 20.07.2016.
 */
public class WorkerPoolConfiguration {
    private long maxIdleTime;
    private long maxQueueSize;
    private int maxWorkers;
    private int minWorkers;
    private int workerNumber;

    public WorkerPoolConfiguration(ConfigurationSection workerConfiguration) {
        maxIdleTime = workerConfiguration.getLong("maxIdleTime");
        maxQueueSize = workerConfiguration.getLong("maxQueueSize");
        maxWorkers = workerConfiguration.getInt("maxWorkers");
        minWorkers = workerConfiguration.getInt("minWorkers");
        workerNumber = workerConfiguration.getInt("workerNumber");
    }

    public long getMaxIdleTime() {
        return maxIdleTime;
    }

    public long getMaxQueueSize() {
        return maxQueueSize;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public int getMinWorkers() {
        return minWorkers;
    }

    public int getWorkerNumber() {
        return workerNumber;
    }
}
