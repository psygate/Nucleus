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

package com.psygate.minecraft.spigot.sovereignty.nucleus.util;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author psygate (https://github.com/psygate)
 */
public class ParseUtil {


    private final static Map<String, TimeUnit> TIME_UNITS = new LinkedHashMap<>();
    private final static List<Character> NUMBER_CHARS = new ArrayList<>();

    static {
        TIME_UNITS.put("d", TimeUnit.DAYS);
        TIME_UNITS.put("h", TimeUnit.HOURS);
        TIME_UNITS.put("m", TimeUnit.MINUTES);
        TIME_UNITS.put("s", TimeUnit.SECONDS);
        TIME_UNITS.put("ms", TimeUnit.MILLISECONDS);
        for (int i = 0; i < 10; i++) {
            NUMBER_CHARS.add(Integer.toString(i).charAt(0));
        }
    }

    public static byte[] toBytes(final UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID fromBytes(final byte[] data) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        return new UUID(bb.getLong(), bb.getLong());
    }

    public static long parseTimeStringToMillis(final String timestr) {
        if (timestr == null) {
            throw new NullPointerException("Argument cannot be null.");
        }
        long timems = 0;
        StringBuilder numberbuffer = new StringBuilder();
        StringBuilder timeunitbuffer = new StringBuilder();

        for (char chr : timestr.toCharArray()) {
            if (NUMBER_CHARS.contains(chr)) {
                if (timeunitbuffer.length() != 0) {
                    TimeUnit unit = TIME_UNITS.get(timeunitbuffer.toString());
                    if (unit == null) {
                        throw new IllegalArgumentException("Illegal time unit: " + timeunitbuffer.toString());
                    }
                    timeunitbuffer.setLength(0);
                    timems += unit.toMillis(Long.parseLong(numberbuffer.toString()));
                    numberbuffer.setLength(0);
                }
                numberbuffer.append(chr);
            } else {
                timeunitbuffer.append(chr);
            }
        }

        if (numberbuffer.length() != 0) {
            if (timeunitbuffer.length() != 0) {
                String unitstr = timeunitbuffer.toString();
                if (!TIME_UNITS.containsKey(unitstr)) {
                    throw new IllegalArgumentException("Unknown time unit: \"" + unitstr + "\"");
                }
                TimeUnit unit = TIME_UNITS.get(unitstr);
                timems += unit.toMillis(Long.parseLong(numberbuffer.toString()));
            } else {
                timems += TimeUnit.MILLISECONDS.toMillis(Long.parseLong(numberbuffer.toString()));
            }
        }

        return timems;
    }

    public static String prettyfyTime(long time) {
        String output = "";

        for (Map.Entry<String, TimeUnit> unit : TIME_UNITS.entrySet()) {
            if (time / unit.getValue().toMillis(1) > 0) {
                long amount = (time / unit.getValue().toMillis(1));
                output += amount + unit.getKey();
                time -= unit.getValue().toMillis(amount);
            }
        }

        return output;
    }

    public static double parsePercent(final String percent) {
        return Double.parseDouble(percent.replaceAll("[ \t]*%", "")) / 100;
    }
}
