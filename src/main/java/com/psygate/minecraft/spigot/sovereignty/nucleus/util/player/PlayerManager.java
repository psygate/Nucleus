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

package com.psygate.minecraft.spigot.sovereignty.nucleus.util.player;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.nucleus.db.model.Tables;
import org.jooq.impl.DSL;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by psygate (https://github.com/psygate) on 01.04.2016.
 */
public class PlayerManager {
    private static PlayerManager instance;

    public static PlayerManager getInstance() {
        if (instance == null) {
            instance = new PlayerManager();
        }

        return instance;
    }

    private final LoadingCache<UUID, Optional<String>> nameCache = Nucleus.getInstance().getConfiguration().getBuilder()
            .build(new CacheLoader<UUID, Optional<String>>() {
                @Override
                public Optional<String> load(UUID uuid) throws Exception {
                    return Nucleus.DBI().submit((conf) -> {
                        return DSL.using(conf).selectFrom(Tables.NUCLEUS_USERNAMES)
                                .where(Tables.NUCLEUS_USERNAMES.PUUID.eq(uuid))
                                .fetchOptional(Tables.NUCLEUS_USERNAMES.USERNAME);
                    });
                }
            });

    private final LoadingCache<String, Optional<UUID>> uuidCache = Nucleus.getInstance().getConfiguration().getBuilder()
            .build(new CacheLoader<String, Optional<UUID>>() {
                @Override
                public Optional<UUID> load(String name) throws Exception {
                    return Nucleus.DBI().submit((conf) -> {
                        return DSL.using(conf).selectFrom(Tables.NUCLEUS_USERNAMES)
                                .where(Tables.NUCLEUS_USERNAMES.USERNAME.eq(name))
                                .fetchOptional(Tables.NUCLEUS_USERNAMES.PUUID);
                    });
                }
            });

    private PlayerManager() {
    }

    public String toName(UUID uuid) {
        return nameCache.getUnchecked(
                Objects.requireNonNull(uuid, () -> "UUID cannot be null.")
        ).orElseThrow(() -> new NoSuchElementException("Player not found for " + uuid));
    }

    public UUID toUUID(String name) {
        return uuidCache.getUnchecked(
                Objects.requireNonNull(name, () -> "Name cannot be null.")
        ).orElseThrow(() -> new NoSuchElementException("Player not found for " + name));
    }

    public void invalidate(UUID uniqueId, String... names) {
        nameCache.invalidateAll(Arrays.asList(names));
        uuidCache.invalidate(uniqueId);
    }
}
