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

import java.io.File;

/**
 * Created by psygate on 20.07.2016.
 */
public class LoggingConfiguration {
    private File basedir;

    public LoggingConfiguration(ConfigurationSection sec) {
        this.basedir = new File(sec.getString("basedir"));
        if (!basedir.isDirectory() && basedir.exists()) {
            throw new IllegalStateException("Logging directory " + basedir + " is a file.");
        } else if (!basedir.exists()) {
            if (!basedir.mkdirs()) {
                throw new IllegalStateException("Cannot create logging directory.");
            }
        }
    }

    public File getBasedir() {
        return basedir;
    }
}
