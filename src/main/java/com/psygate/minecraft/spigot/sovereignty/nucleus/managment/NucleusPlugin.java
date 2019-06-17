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

package com.psygate.minecraft.spigot.sovereignty.nucleus.managment;

import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.DatabaseInterface;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by psygate (https://github.com/psygate) on 21.01.2016.
 */
public interface NucleusPlugin {
    int getWantedDBVersion();

    void fail();

    String getName();

    void setLogger(Logger logger);

    default Logger getSubLogger(String logname) {
        Logger logger = Logger.getLogger(logname);
        logger.setParent(getPluginLogger());
        return logger;
    }

    Logger getPluginLogger();

    default List<Listener> getListeners() {
        return Collections.emptyList();
    }

    void setDatabaseInterface(DatabaseInterface dbi);
}
