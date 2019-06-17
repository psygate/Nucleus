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

import org.jooq.Converter;

/**
 * Created by psygate (https://github.com/psygate) on 24.01.2016.
 */
public class ByteBooleanConverter implements Converter<Byte, Boolean> {
    @Override
    public Boolean from(Byte aByte) {
        return (aByte != 0);
    }

    @Override
    public Byte to(Boolean aBoolean) {
        return (byte) (aBoolean ? 1 : 0);
    }

    @Override
    public Class<Byte> fromType() {
        return Byte.class;
    }

    @Override
    public Class<Boolean> toType() {
        return Boolean.class;
    }
}
