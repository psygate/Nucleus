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

import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.DatabaseType;
import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;

/**
 * Created by psygate (https://github.com/psygate) on 19.01.2016.
 */
public class SQLConfiguration implements Serializable {
    private DatabaseType DBType = DatabaseType.MYSQL;
    private String host = "localhost";
    private int port = 3306;
    private String username = "nucleus";
    private String password = "nucleus";
    private String databaseName = "nucleus";
    private WorkerConfiguration workerConfiguration = new WorkerConfiguration();
    private PoolConfiguration poolConfiguration = new PoolConfiguration();

    public SQLConfiguration() {

    }

    public SQLConfiguration(ConfigurationSection config) {
        workerConfiguration = new WorkerConfiguration(config.getConfigurationSection("workerConfiguration"));
        poolConfiguration = new PoolConfiguration(config.getConfigurationSection("poolConfiguration"));
        DBType = DatabaseType.valueOf(config.getString("DBType").toUpperCase());
        host = config.getString("host");
        port = config.getInt("port");
        username = config.getString("username");
        password = config.getString("password");
        databaseName = config.getString("databaseName");
    }


    public PoolConfiguration getPoolConfiguration() {
        return poolConfiguration;
    }

    public void setPoolConfiguration(PoolConfiguration poolConfiguration) {
        this.poolConfiguration = poolConfiguration;
    }

    public WorkerConfiguration getWorkerConfiguration() {
        return workerConfiguration;
    }

    public void setWorkerConfiguration(WorkerConfiguration workerConfiguration) {
        this.workerConfiguration = workerConfiguration;
    }

    public DatabaseType getDBType() {
        return DBType;
    }

    public void setDBType(DatabaseType DBType) {
        this.DBType = DBType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getJdbcUrl() {
        return "jdbc:" + getDBType().jdbcName() + "://" + host + ":" + port + "/" + databaseName;
    }
}
