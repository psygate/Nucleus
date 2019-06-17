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

import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.DatabaseType;
import org.bukkit.configuration.ConfigurationSection;

import static com.psygate.minecraft.spigot.sovereignty.nucleus.configuration.util.ConfigUtils.checkedString;

/**
 * Created by psygate on 20.07.2016.
 */
public class SQLConfiguration {
    private DatabaseType databaseType;
    private String databaseName;
    private String host;
    private String username;
    private String password;
    private int port;
    private SQLPoolConfiguration poolConfiguration;
    private SQLWorkerPoolConfiguration workerPoolConfiguration;

    public SQLConfiguration(ConfigurationSection section) {
        databaseType = databaseType.valueOf(section.getString("DBType"));
        databaseName = checkedString(section.getString("databaseName"));
        host = checkedString(section.getString("host"));
        username = checkedString(section.getString("username"));
        password = checkedString(section.getString("password"));
        port = section.getInt("port");
        poolConfiguration = new SQLPoolConfiguration(section.getConfigurationSection("poolConfiguration"));
        workerPoolConfiguration = new SQLWorkerPoolConfiguration(section.getConfigurationSection("workerConfiguration"));
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public SQLPoolConfiguration getPoolConfiguration() {
        return poolConfiguration;
    }

    public SQLWorkerPoolConfiguration getWorkerPoolConfiguration() {
        return workerPoolConfiguration;
    }
}
