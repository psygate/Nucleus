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

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by psygate on 20.07.2016.
 */
public class Configuration {
    private SQLConfiguration sqlConfiguration;
    private LoggingConfiguration loggingConfiguration;
    private WorkerPoolConfiguration workerPoolConfiguration;
    private PlayerNameCacheSettings playerNameCacheSettings;

    public Configuration(FileConfiguration config) {
        sqlConfiguration = new SQLConfiguration(config.getConfigurationSection("sqlConfiguration"));
        loggingConfiguration = new LoggingConfiguration(config.getConfigurationSection("logging"));
        workerPoolConfiguration = new WorkerPoolConfiguration(config.getConfigurationSection("workerpool"));
        playerNameCacheSettings = new PlayerNameCacheSettings(config.getConfigurationSection("playernamecache"));
    }

    public SQLConfiguration getSqlConfiguration() {
        return sqlConfiguration;
    }

    public LoggingConfiguration getLoggingConfiguration() {
        return loggingConfiguration;
    }

    public WorkerPoolConfiguration getWorkerPoolConfiguration() {
        return workerPoolConfiguration;
    }

    public PlayerNameCacheSettings getPlayerNameCacheSettings() {
        return playerNameCacheSettings;
    }
}
