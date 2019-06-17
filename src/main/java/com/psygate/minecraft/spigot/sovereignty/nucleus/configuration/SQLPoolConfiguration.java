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

import com.psygate.minecraft.spigot.sovereignty.nucleus.configuration.util.ConfigUtils;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by psygate on 20.07.2016.
 */
public class SQLPoolConfiguration {
    private long connectionTimeout;
    private long idleTimeout;
    private long leakDetectionThreshold;
    private long maxLifeTime;
    private int maxPoolSize;
    private int minPoolSize;
    private String poolName;

    public SQLPoolConfiguration(ConfigurationSection sec) {
        connectionTimeout = sec.getLong("connectionTimeout");
        idleTimeout = sec.getLong("idleTimeout");
        leakDetectionThreshold = sec.getLong("leakDetectionThreshold");
        maxLifeTime = sec.getLong("maxLifetime");
        maxPoolSize = sec.getInt("maximumPoolSize");
        minPoolSize = sec.getInt("minimumIdle");
        poolName = ConfigUtils.checkedString(sec.getString("poolName"));
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public long getMaxLifeTime() {
        return maxLifeTime;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public String getPoolName() {
        return poolName;
    }
}
