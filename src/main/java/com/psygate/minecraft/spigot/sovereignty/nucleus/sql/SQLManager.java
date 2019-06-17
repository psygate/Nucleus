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

package com.psygate.minecraft.spigot.sovereignty.nucleus.sql;

import com.codahale.metrics.Timer;
import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.nucleus.managment.NucleusPlugin;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.configuration.SQLConfiguration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;
import org.bukkit.plugin.java.JavaPlugin;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultTransactionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * Created by psygate (https://github.com/psygate) on 18.01.2016.
 */
public class SQLManager implements DatabaseInterface {
    private HikariPool connectionPool;
    private HikariConfig poolConfig;
    private Configuration jooqConfiguration;
    private SQLConfiguration conf;
    private ExecutorService threadpool;
    private Settings jooqSettings;
    private final static boolean DEBUG = false;

    public void init(SQLConfiguration sqlconf) throws Exception {
        conf = sqlconf;
        configurePool(sqlconf);
        configureJooq(sqlconf);
        configureThreadPool(sqlconf);
    }

    private void configurePool(SQLConfiguration conf) {
        poolConfig = new HikariConfig();
        poolConfig.setPoolName("Nucleus-SQLPool");
//        poolConfig.setTransactionIsolation("TRANSACTION_REPEATABLE_READ");
        poolConfig.setAutoCommit(false);
        // fail early, fail fast.
        try {
            Class.forName(conf.getDBType().driverName);
        } catch (Exception e) {
            throw new AssertionError("Cannot find driver class: " + conf.getDBType().driverName, e);
        }
        poolConfig.setDriverClassName(conf.getDBType().driverName);
        poolConfig.setConnectionTimeout(conf.getPoolConfiguration().getConnectionTimeout());
        poolConfig.setMinimumIdle(conf.getPoolConfiguration().getMinimumIdle());
        poolConfig.setMaxLifetime(conf.getPoolConfiguration().getMaxLifetime());
        poolConfig.setMaximumPoolSize(conf.getPoolConfiguration().getMaximumPoolSize());
        poolConfig.setPoolName(conf.getPoolConfiguration().getPoolName());
        poolConfig.setLeakDetectionThreshold(conf.getPoolConfiguration().getLeakDetectionThreshold());

        poolConfig.setJdbcUrl(conf.getJdbcUrl());
        poolConfig.setUsername(conf.getUsername());
        poolConfig.setPassword(conf.getPassword());
//        poolConfig.addDataSourceProperty("cachePrepStmts", "true");
//        poolConfig.addDataSourceProperty("prepStmtCacheSize", "250");
//        poolConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        connectionPool = new HikariPool(poolConfig);
    }

    private void configureJooq(SQLConfiguration conf) {
        jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(new HikariConnectionProvider(connectionPool));
        jooqSettings = new Settings();
        jooqSettings.setRenderSchema(false);
        jooqConfiguration.set(new DefaultTransactionProvider(new HikariConnectionProvider(connectionPool)));
        jooqConfiguration.set(conf.getDBType().dialect);
        jooqConfiguration.set(jooqSettings);
    }

    private void configureThreadPool(SQLConfiguration conf) {
        assert conf.getWorkerConfiguration().getMaxWorkers() >= 1;
        //ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
        ThreadPoolExecutor ex = new ThreadPoolExecutor(
                conf.getWorkerConfiguration().getMinWorkers(),
                conf.getWorkerConfiguration().getMaxWorkers(),
                conf.getWorkerConfiguration().getMaxIdleTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                (r) -> {
                    Nucleus.getMetricRegistry().counter("SQLManager-ThreadPool-Thread-Created").inc();
                    Thread t = new Thread(r);
                    t.setUncaughtExceptionHandler((t1, e) -> {
                        e.printStackTrace(System.err);
                        System.out.println("Thrown in SQLManager Execution Thread pool");
                    });
                    return new Thread(r);
                },
                (r, executor) -> System.err.println("ERROR! Exection rejected by SQLManager: " + r)
        );

        threadpool = ex;

//        threadpool = Executors.newFixedThreadPool(conf.getWorkerConfiguration().getMaxWorkers(), r -> {
//            Thread t = new Thread(r);
//            t.setUncaughtExceptionHandler((t1, e) -> e.printStackTrace(System.err));
//            return t;
//        });
    }

    public void asyncSubmit(TransactionalRunnable run) {
        Nucleus.getMetricRegistry().counter("SQLManager-asyncSubmit-Transaction-Submitted").inc();

        String source;
        if (DEBUG) {
            int sourceindex = 1;
            StackTraceElement[] ste = new RuntimeException().getStackTrace();
            source = ste[sourceindex].getClassName() + "#" + ste[sourceindex].getMethodName();
            System.out.println(source);
        } else {
            source = "Undefined source";
        }
        threadpool.submit(() -> {
            internalSubmit(run);
        });
    }

    public <T> Future<T> asyncSubmit(TransactionalCallable<T> tc) {
        Nucleus.getMetricRegistry().counter("SQLManager-asyncSubmit#Future-Transaction-Submitted").inc();
        String source;
        if (DEBUG) {
            int sourceindex = 1;
            StackTraceElement[] ste = new RuntimeException().getStackTrace();
            source = ste[sourceindex].getClassName() + "#" + ste[sourceindex].getMethodName();
            System.out.println(source);
        } else {
            source = "Undefined source";
        }
        return threadpool.submit(() -> {
            return internalSubmit(tc);
        });
    }

    public <T> T submit(TransactionalCallable<T> tc) {
        return internalSubmit(tc);
    }

    private <T> T internalSubmit(TransactionalCallable<T> tc) {
        Connection con = null;
        try {
            Timer.Context acquireTimer = Nucleus.getMetricRegistry().timer("SQLManager-submit-Transaction-ConnectionAcquire").time();
            con = connectionPool.getConnection();

            acquireTimer.stop();
            DSLContext ctx = DSL.using(con, jooqSettings);

            Timer.Context executionTimer = Nucleus.getMetricRegistry().timer("SQLManager-submit-Transaction-Execution").time();
            T out = null;
            try {
                out = ctx.transactionResult(tc);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            con.commit();
            executionTimer.stop();
            return out;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    //Pass
                }
            }
        }

        throw new IllegalStateException("Query execution failed.");
    }

    public void submit(TransactionalRunnable run) {
        internalSubmit(run);
    }

    private void internalSubmit(TransactionalRunnable run) {
        Connection con = null;
        try {
            Timer.Context acquireTimer = Nucleus.getMetricRegistry().timer("SQLManager-submit-Transaction-ConnectionAcquire").time();
            con = connectionPool.getConnection();

            acquireTimer.stop();
            DSLContext ctx = DSL.using(con, jooqSettings);

            Timer.Context executionTimer = Nucleus.getMetricRegistry().timer("SQLManager-submit-Transaction-Execution").time();
            try {
                ctx.transaction(run);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            con.commit();
            executionTimer.stop();
            return;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    //Pass
                }
            }
        }

        throw new IllegalStateException("Query execution failed.");
    }

    public <T extends JavaPlugin & NucleusPlugin> void shutdown() throws InterruptedException {
        threadpool.shutdown();
        threadpool.awaitTermination(10, TimeUnit.MINUTES);
        connectionPool.shutdown();
    }

    public <T extends JavaPlugin & NucleusPlugin> DatabaseInterface forPlugin(T plug) {
        return new DatabaseInterface() {
            @Override
            public void asyncSubmit(TransactionalRunnable run) {
                Timer.Context timer = Nucleus.getMetricRegistry().timer(plug.getName() + "-submit-async").time();
                try {
                    SQLManager.this.asyncSubmit(run);
                } finally {
                    timer.stop();
                }
            }

            @Override
            public <T> Future<T> asyncSubmit(TransactionalCallable<T> tc) {
                Timer.Context timer = Nucleus.getMetricRegistry().timer(plug.getName() + "-submit-return-async").time();
                try {
                    return SQLManager.this.asyncSubmit(tc);
                } finally {
                    timer.stop();
                }
            }

            @Override
            public <T> T submit(TransactionalCallable<T> tc) {
                Timer.Context timer = Nucleus.getMetricRegistry().timer(plug.getName() + "-submit-return").time();
                try {
                    return SQLManager.this.submit(tc);
                } finally {
                    timer.stop();
                }
            }

            @Override
            public void submit(TransactionalRunnable run) {
                Timer.Context timer = Nucleus.getMetricRegistry().timer(plug.getName() + "-submit").time();
                try {
                    SQLManager.this.submit(run);
                } finally {
                    timer.stop();
                }
            }
        };
    }

    private final static class HikariConnectionProvider implements ConnectionProvider {
        private final HikariPool pool;

        public HikariConnectionProvider(HikariPool pool) {
            this.pool = pool;
        }

        @Override
        public Connection acquire() throws DataAccessException {
            try {
                return pool.getConnection();
            } catch (SQLException e) {
                throw new DataAccessException("Cannot acquire connection.", e);
            }
        }

        @Override
        public void release(Connection connection) throws DataAccessException {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Cannot release connection.", e);
            }
        }
    }
}
