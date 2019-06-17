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

import com.psygate.minecraft.spigot.sovereignty.nucleus.util.ParseUtil;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by psygate on 20.07.2016.
 */
public class PlayerNameCacheSettings {
    private int minSize;
    private int maxSize;
    private long expireAfterAccess;
    private long expireAfterWrite;

    public PlayerNameCacheSettings(ConfigurationSection sec) {
        minSize = sec.getInt("minSize");
        maxSize = sec.getInt("maxSize");
        expireAfterAccess = ParseUtil.parseTimeStringToMillis(sec.getString("expireAfterAccess"));
        expireAfterWrite = ParseUtil.parseTimeStringToMillis(sec.getString("expireAfterWrite"));

    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public long getExpireAfterAccess() {
        return expireAfterAccess;
    }

    public long getExpireAfterWrite() {
        return expireAfterWrite;
    }
}
