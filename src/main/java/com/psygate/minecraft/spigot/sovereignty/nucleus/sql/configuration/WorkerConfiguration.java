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

package com.psygate.minecraft.spigot.sovereignty.nucleus.sql.configuration;

import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;

/**
 * Created by psygate (https://github.com/psygate) on 19.01.2016.
 */
public class WorkerConfiguration implements Serializable {
    private int workerNumber = 3;
    private int maxQueueSize = 100000;
    private int spinLockTimeOut = 5000;
    private int minWorkers = Runtime.getRuntime().availableProcessors() / 2;
    private int maxWorkers = Runtime.getRuntime().availableProcessors() - 1;
    private int maxIdleTime = 30000;

    public WorkerConfiguration() {

    }

    public WorkerConfiguration(ConfigurationSection config) {
        workerNumber = config.getInt("workerNumber");
        maxQueueSize = config.getInt("maxQueueSize");
        spinLockTimeOut = config.getInt("spinLockTimeOut");
        minWorkers = config.getInt("minWorkers");
        maxWorkers = config.getInt("maxWorkers");
        maxIdleTime = config.getInt("maxIdleTime");

    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getWorkerNumber() {
        return workerNumber;
    }

    public void setWorkerNumber(int workerNumber) {
        this.workerNumber = workerNumber;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public int getSpinLockTimeOut() {
        return spinLockTimeOut;
    }

    public void setSpinLockTimeOut(int spinLockTimeOut) {
        this.spinLockTimeOut = spinLockTimeOut;
    }

    public int getMinWorkers() {
        return minWorkers;
    }

    public void setMinWorkers(int minWorkers) {
        this.minWorkers = minWorkers;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void setMaxWorkers(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }
}
