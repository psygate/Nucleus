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

package com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class providing command loading from local jar files.
 */
public class CommandLoader {
    private final File loadFrom;

    public CommandLoader(File loadFrom) {
        this.loadFrom = loadFrom;
    }

    /**
     * Loads all commands from the provided file.
     *
     * @throws IOException
     */
    public void load(JavaPlugin context) throws IOException {
//        Enumeration<JarEntry> entries = loadFrom.entries();
//        for (JarEntry en = null; entries.hasMoreElements(); en = entries.nextElement()) {
//            if (en.getName().endsWith(".class")) {
//
//            }
//        }

        List<String> classNames = new ArrayList<>();
        ZipInputStream zip = new ZipInputStream(new FileInputStream(loadFrom));
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                // This ZipEntry represents a class. Now, what class does it represent?
                String className = entry.getName().replace('/', '.'); // including ".class"
                classNames.add(className.substring(0, className.length() - ".class".length()));
            }
        }

        for (String name : classNames) {
            try {
                Class<?> clazz = Class.forName(name, false, CommandLoader.class.getClassLoader());
                if (NucleusCommand.class.isAssignableFrom(clazz)) {
                    Class<?> classinit = Class.forName(name);
                    if ((classinit.getModifiers() & Modifier.ABSTRACT) == 0 && (classinit.getModifiers() & Modifier.INTERFACE) == 0) {
                        NucleusCommand com = (NucleusCommand) classinit.getConstructor(new Class[0]).newInstance();
                        for (String cmdname : com.getName()) {
                            try {
                                context.getServer().getPluginCommand(cmdname).setExecutor(com);
                            } catch (NullPointerException e) {
                                throw new NullPointerException("Cannot find command alias: " + cmdname);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

    }
}
