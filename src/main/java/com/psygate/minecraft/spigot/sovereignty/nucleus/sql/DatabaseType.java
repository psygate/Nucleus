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

import org.jooq.SQLDialect;

/**
 * Created by psygate (https://github.com/psygate) on 18.01.2016.
 */
public enum DatabaseType {
    MYSQL(SQLDialect.MYSQL, "com.mysql.jdbc.jdbc2.optional.MysqlDataSource", "mysql"),
    MARIADB(SQLDialect.MARIADB, "org.mariadb.jdbc.MySQLDataSource", "mysql"),
    POSTGRESQL(SQLDialect.POSTGRES, "com.impossibl.postgres.jdbc.PGDataSource", "postgresql"),
    HSQLDB(SQLDialect.HSQLDB, "org.hsqldb.jdbc.JDBCDataSource", "hsqldb");

    public final SQLDialect dialect;
    public final String driverName;
    private final String jdbcName;

    DatabaseType(SQLDialect dialect, String driverName, String jdbcName) {
        this.dialect = dialect;
        this.driverName = driverName;
        this.jdbcName = jdbcName;
    }

    public String jdbcName() {
        return jdbcName;
    }


    /*
    Apache Derby	Derby	org.apache.derby.jdbc.ClientDataSource
Firebird	Jaybird	org.firebirdsql.pool.FBSimpleDataSource
H2	H2	org.h2.jdbcx.JdbcDataSource
HSQLDB	HSQLDB	org.hsqldb.jdbc.JDBCDataSource
IBM AS400	IBM	com.ibm.as400.access.AS400JDBCDriver
IBM DB2	DB2	com.ibm.db2.jcc.DB2SimpleDataSource
MariaDB & MySQL	MariaDB	org.mariadb.jdbc.MySQLDataSource
MySQL	Connector/J	com.mysql.jdbc.jdbc2.optional.MysqlDataSource
MS SQL Server	Microsoft	com.microsoft.sqlserver.jdbc.SQLServerDataSource
Oracle	Oracle	oracle.jdbc.pool.OracleDataSource
OrientDB	OrientDB	com.orientechnologies.orient.jdbc.OrientDataSource
PostgreSQL	pgjdbc-ng	com.impossibl.postgres.jdbc.PGDataSource
PostgreSQL	PostgreSQL	org.postgresql.ds.PGSimpleDataSource
SAP MaxDB	SAP	com.sap.dbtech.jdbc.DriverSapDB
SQLite	xerial	org.sqlite.SQLiteDataSource
SyBase	jConnect	com.sybase.jdbc4.jdbc.SybDataSource
     */
}
