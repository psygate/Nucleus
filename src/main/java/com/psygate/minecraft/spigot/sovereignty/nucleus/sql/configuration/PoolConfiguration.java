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
import java.util.concurrent.TimeUnit;

/**
 * Created by psygate (https://github.com/psygate) on 19.01.2016.
 */
public class PoolConfiguration implements Serializable {
    private long connectionTimeout = 30000;
    private long idleTimeout = 60000;
    private long maxLifetime = 1800000;
    private int minimumIdle = Runtime.getRuntime().availableProcessors() / 2;
    private int maximumPoolSize = Runtime.getRuntime().availableProcessors() - 1;
    private String poolName = "Nucleus-Hikari-Pool";
    private long leakDetectionThreshold = TimeUnit.MINUTES.toMillis(5);

    public PoolConfiguration() {

    }

    public PoolConfiguration(ConfigurationSection config) {
        connectionTimeout = config.getLong("connectionTimeout");
        idleTimeout = config.getLong("idleTimeout");
        maxLifetime = config.getLong("maxLifetime");
        minimumIdle = config.getInt("minimumIdle");
        maximumPoolSize = config.getInt("maximumPoolSize");
        leakDetectionThreshold = config.getLong("leakDetectionThreshold");
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public long getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(long leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }
}
