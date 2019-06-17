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

package com.psygate.minecraft.spigot.sovereignty.nucleus;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.SetPlayerCapCommand;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.SetPlayerCapMessage;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.SetWhitelistMessage;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandLoader;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.EnforceNameCommand;
import com.psygate.minecraft.spigot.sovereignty.nucleus.configuration.Configuration;
import com.psygate.minecraft.spigot.sovereignty.nucleus.db.model.Tables;
import com.psygate.minecraft.spigot.sovereignty.nucleus.db.model.tables.records.NucleusPluginVersionsRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.db.model.tables.records.NucleusUsernamesRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.listeners.NameEnforcementListener;
import com.psygate.minecraft.spigot.sovereignty.nucleus.managment.DatabaseIntegrityChecker;
import com.psygate.minecraft.spigot.sovereignty.nucleus.managment.NucleusPlugin;
import com.psygate.minecraft.spigot.sovereignty.nucleus.managment.UpdateScriptLoader;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.DatabaseInterface;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.SQLManager;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.util.SqlRunner;
import com.psygate.minecraft.spigot.sovereignty.nucleus.util.WorkerPool;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.*;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by psygate (https://github.com/psygate) on 18.01.2016.
 */
public class Nucleus extends JavaPlugin {
    private Configuration configuration;
    private Logger LOG;
    private SQLManager sqlmanager;
    private static Nucleus instance = null;
    private MetricRegistry nucleusMetrics = new MetricRegistry();

    public static Nucleus getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Not initialized.");
        }
        return instance;
    }

    public static DatabaseInterface DBI() {
        if (Nucleus.getInstance().sqlmanager == null) {
            throw new IllegalStateException();
        } else {
            return Nucleus.getInstance().sqlmanager;
        }
    }

    @Override
    public void onEnable() {
        try {
            subEnable();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void subEnable() {
        instance = this;
        saveDefaultConfig();
        configuration = new Configuration(getConfig());


        conf = loadConfiguration();
        WorkerPool.init(conf);
        initSQL();
        register(this);

        getServer().getPluginManager().registerEvents(new NameEnforcementListener(), this);
        getServer().getPluginCommand("forcename").setExecutor(new EnforceNameCommand());
        getServer().getPluginCommand("setplayercap").setExecutor(new SetPlayerCapCommand());
        getServer().getPluginCommand("setwhitelistmessage").setExecutor(new SetWhitelistMessage());
        getServer().getPluginCommand("setplayercapmessage").setExecutor(new SetPlayerCapMessage());

        //If this is a reload and tables were dropped, make sure all players are reinserted.
        List<NucleusUsernamesRecord> recs = getServer().getOnlinePlayers()
                .stream()
                .map(v -> new NucleusUsernamesRecord(v.getName(), v.getUniqueId()))
                .collect(Collectors.toList());

        DBI().asyncSubmit((conf) -> {
            DSLContext ctx = DSL.using(conf);
            for (NucleusUsernamesRecord rec : recs) {
                ctx.insertInto(Tables.NUCLEUS_USERNAMES)
                        .set(rec)
                        .onDuplicateKeyIgnore()
                        .execute();
            }
        });
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public <T extends JavaPlugin & NucleusPlugin> void register(T plug) {
        plug.setLogger(Logger.getLogger(plug.getClass().getName()));
        int requiredDatabaseVersion = plug.getWantedDBVersion();
        Future<Optional<NucleusPluginVersionsRecord>> versionFuture = sqlmanager.asyncSubmit(configuration -> {
                    return DSL.using(configuration)
                            .selectFrom(Tables.NUCLEUS_PLUGIN_VERSIONS)
                            .where(Tables.NUCLEUS_PLUGIN_VERSIONS.PLUGIN_NAME.eq(plug.getName()))
                            .fetchOptional();
                }
        );

        try {
            UpdateScriptLoader loader = new UpdateScriptLoader(plug.getClass().getProtectionDomain().getCodeSource().getLocation());
            Optional<NucleusPluginVersionsRecord> opt;

            DatabaseIntegrityChecker checker = new DatabaseIntegrityChecker(
                    new File(plug.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()),
                    conf.getSqlConfiguration().getDatabaseName()
            );
            if (!checker.check()) {
                System.err.println("---------------".replace("-", "--"));
                System.err.println("DROPPING DATABASE. IF THIS IS NOT WHAT YOU WANT, KILL THE SERVER NOW.");
                Thread.sleep(10000);
                System.err.println("---------------".replace("-", "--"));
                sqlmanager.asyncSubmit(configuration -> {
                    Connection con = configuration.connectionProvider().acquire();
                    SqlRunner runner = new SqlRunner(con, true, true);
                    assert loader.getScripts().size() > 0 : "Plugin doesn't provide a database script.";
                    LOG.info("Running script: \n" + loader.getScripts().get(0));
                    runner.runScript(new StringReader(loader.getScripts().get(0).getText()));
                    con.commit();
                    configuration.connectionProvider().release(con);
                    DSL.using(configuration).deleteFrom(Tables.NUCLEUS_PLUGIN_VERSIONS)
                            .where(Tables.NUCLEUS_PLUGIN_VERSIONS.PLUGIN_NAME.eq(plug.getName()))
                            .execute();
                    DSL.using(configuration)
                            .insertInto(Tables.NUCLEUS_PLUGIN_VERSIONS)
                            .set(new NucleusPluginVersionsRecord(plug.getName(), requiredDatabaseVersion))
                            .execute();
                    LOG.info("database rebuilt.");
                });
            }
            plug.setDatabaseInterface(sqlmanager.forPlugin(plug));
            loadCommands(plug);
            loadListeners(plug);
        } catch (Exception e) {
            plug.fail();
            throw new RuntimeException("Initialization failure.", e);
        }
    }

    private <T extends JavaPlugin & NucleusPlugin> void loadCommands(T plug) throws IOException {
        CommandLoader loader = new CommandLoader(new File(plug.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()));
        loader.load(plug);
    }

    private <T extends JavaPlugin & NucleusPlugin> void loadListeners(T plug) {
        for (Listener li : plug.getListeners()) {
            getServer().getPluginManager().registerEvents(li, plug);
        }
    }

    private NucleusConfiguration loadConfiguration() {
        saveDefaultConfig();
        return new NucleusConfiguration(getConfig());
    }

    @Override
    public void setDatabaseInterface(DatabaseInterface dbi) {

    }

    @Override
    public void onDisable() {
        try {
            WorkerPool.shutdown();
            sqlmanager.shutdown();
//            ConsoleReporter reporter = ConsoleReporter.forRegistry(getMetricRegistry()).build();
//            reporter.report();
            File metricsFolder = new File(getDataFolder(), "metrics");
            if (!metricsFolder.exists()) {
                if (!metricsFolder.mkdirs()) {
                    throw new IllegalStateException("Cannot create metrics folder. " + metricsFolder.getAbsolutePath());
                }
            }
            Calendar cal = Calendar.getInstance();
            String file = "metrics_" +
                    cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH)
                    + "_" + cal.get(Calendar.HOUR) + "-" + cal.get(Calendar.MINUTE)
                    + ".txt";
            try (PrintStream metricsPrintstream = new PrintStream(new FileOutputStream(new File(metricsFolder, file), true))) {
//                metricsPrintstream.write(new String("\n\n").getBytes());
                ConsoleReporter.forRegistry(getMetricRegistry()).outputTo(metricsPrintstream).build().report();
                metricsPrintstream.flush();
            }
        } catch (InterruptedException | IOException | IllegalStateException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public int getWantedDBVersion() {
        return 1;
    }

    @Override
    public void fail() {
        Logger.getLogger(Nucleus.class.getName()).severe("Unable to load nucleus.");
        getServer().shutdown();
    }
}
