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

package com.psygate.minecraft.spigot.sovereignty.nucleus.sql.util;

import com.psygate.minecraft.spigot.sovereignty.nucleus.bans.BanType;
import org.jooq.Converter;

/**
 * Created by psygate (https://github.com/psygate) on 24.01.2016.
 */
public class BanTypeConverter implements Converter<Integer, BanType> {

    @Override
    public BanType from(Integer integer) {
        if (integer == BanType.OPERATOR.ordinal()) {
            return BanType.OPERATOR;
        } else if (integer == BanType.PLUGIN.ordinal()) {
            return BanType.PLUGIN;
        } else if (integer == BanType.CONSOLE.ordinal()) {
            return BanType.CONSOLE;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Integer to(BanType banType) {
        return banType.ordinal();
    }

    @Override
    public Class<Integer> fromType() {
        return Integer.class;
    }

    @Override
    public Class<BanType> toType() {
        return BanType.class;
    }
}
