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

import org.jooq.TransactionalCallable;
import org.jooq.TransactionalRunnable;

import java.util.concurrent.Future;

/**
 * Created by psygate (https://github.com/psygate) on 28.01.2016.
 */
public interface DatabaseInterface {
    void asyncSubmit(TransactionalRunnable run);

    <T> Future<T> asyncSubmit(TransactionalCallable<T> tc);

    <T> T submit(TransactionalCallable<T> tc);

    void submit(TransactionalRunnable run);
}
