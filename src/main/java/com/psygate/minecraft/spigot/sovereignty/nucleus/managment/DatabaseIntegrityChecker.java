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

import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import org.jooq.Table;
import org.jooq.TransactionalCallable;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.TableImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class providing table object loading from jar files.
 */
public class DatabaseIntegrityChecker {
    private final File loadFrom;
    private final Logger LOG = Nucleus.getInstance().getSubLogger("DatabaseIntegrityChecker");
    private final String schemaName;

    public DatabaseIntegrityChecker(File loadFrom, String schemaName) {
        this.loadFrom = loadFrom;
        this.schemaName = schemaName;
    }

    /**
     * Loads table definitions from the file and checks for them in the database.
     *
     * @throws IOException
     */
    public boolean check() throws IOException, ClassNotFoundException {
        boolean metaAccess = false;
        List<Table<?>> tablesList = new LinkedList<>();
//        List<String> tablesList = new LinkedList<>();
        try {
            tablesList = Nucleus.DBI().submit((TransactionalCallable<List<Table<?>>>) (conf) -> DSL.using(conf).meta().getTables());
//            tablesList = Nucleus.DBI().submit((TransactionalCallable<List<Table<?>>>) (conf) -> DSL.using(conf).meta().getTables())
//                    .stream().map(table -> table.getSchema() + "." + table.getName())
//                    .collect(Collectors.toList());

            metaAccess = true;
//            LOG.info("Meta data access was granted. Here is a list of all tables for debug purposes: " + tablesList);
        } catch (DataAccessException e) {
            LOG.log(Level.WARNING, "Nucleus doesn't have meta data access. You should fix this.");
        }
//        List<String> classNames = getClassNames();

        final boolean finalMetaAccess = metaAccess;
        final List<Table<?>> finalTablesList = tablesList;
//        final List<String> finalTablesList = tablesList;

        return !getClassNames()
                .stream()
                .map(this::classForNameIgnoredError)    //Map classes, unloadable classes are mapped to null.
                .filter(c -> c != null)                 // Filter unloadable classes.
                .filter(clazz -> SchemaImpl.class.isAssignableFrom(clazz))  //Filter to schema assignable classes.
                .map(Class::getDeclaredFields)              // Extracted declared fields
                .flatMap(Arrays::stream)
                .filter(field -> Modifier.isStatic(field.getModifiers()))   //filter static fields
                .filter(field -> Modifier.isFinal(field.getModifiers()))    //filter final static fields
                .filter(field -> SchemaImpl.class.isAssignableFrom(field.getType()))    //filter schema impl assignable fields
                .map(this::castOrNull)
                .filter(impl -> impl != null)
                .map(SchemaImpl::getTables)
                .flatMap(List::stream)
                .map(table -> new TableImpl<>(table.getName(), new SchemaImpl(schemaName), table))
                .filter(table -> {
                    if (finalMetaAccess) {
                        LOG.info("Checking TalbeList from Meta Data for table: " + table + " -> " + finalTablesList.contains(table));
                        return !finalTablesList.contains(table);
                    } else {
                        try {
                            LOG.info("Using fallback count of rows check for table " + table);
                            if (Nucleus.DBI().submit((TransactionalCallable<Integer>) conf -> DSL.using(conf).selectCount().from(table).fetchOne().value1()) < 0) {
                                LOG.info("Count failed, assuming table corrupted.");
                                return true;
                            }
                        } catch (Exception e) {
                            LOG.log(Level.INFO, "Count failed, throwing exception. Assuming table corrupted." + e.getMessage());
                            return true;
                        }
                    }

                    return false;
                })
                .findAny()
                .isPresent();
//        ;
//        for (String name : classNames) {
//            try {
//                Class<?> clazz = Class.forName(name);
//
//                if (SchemaImpl.class.isAssignableFrom(clazz)) {
//                    LOG.fine("Found schema implementation. " + clazz);
//                    //	public static final Nucleus NUCLEUS = new Nucleus();
//
//                    for (Field field : clazz.getDeclaredFields()) {
//                        if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && SchemaImpl.class.isAssignableFrom(field.getType())) {
//                            LOG.fine("Found schema implementation field. " + field);
//                            try {
//                                SchemaImpl imp = (SchemaImpl) field.get(clazz);
//                                for (Table<?> table : imp.getTables()) {
//                                    LOG.fine("Checking table: " + table);
//                                    boolean found = false;
//                                    try {
//                                        for (Table<?> t : Nucleus.DBI().submit((TransactionalCallable<List<Table<?>>>) (conf) -> DSL.using(conf).meta().getTables())) {
//                                            if (t.equals(table)) {
//                                                found = true;
//                                            }
//                                        }
//                                    } catch (DataAccessException e) {
//                                        //Fallback check
//                                        if (Nucleus.DBI().submit((TransactionalCallable<Integer>) conf -> DSL.using(conf).selectCount().from(table).fetchOne().value1()) < 0) {
//                                            found = false;
//                                        }
//                                    }
//
//                                    if (found == false) {
//                                        throw new IllegalStateException("Table not found: " + table);
//                                    }
////                                    Nucleus.DBI().submit((TransactionalRunnable) (config) -> DSL.using(config).selectCount().from(table));
//                                }
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                LOG.log(Level.SEVERE, "Database corrupted. " + e.getMessage());
//                                return false;
//                            }
//                        }
//                    }
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return true;
    }

    private SchemaImpl castOrNull(Field field) {
        try {
            return (SchemaImpl) field.get(field.getDeclaringClass());
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private List<String> getClassNames() throws IOException {
        List<String> classNames = new ArrayList<>();
        ZipInputStream zip = new ZipInputStream(new FileInputStream(loadFrom));
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                // This ZipEntry represents a class. Now, what class does it represent?
                String className = entry.getName().replace('/', '.'); // including ".class"
                classNames.add(className.substring(0, className.length() - ".class".length()));
            }
        }

        return classNames;
    }

    private Class<?> classForNameIgnoredError(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
