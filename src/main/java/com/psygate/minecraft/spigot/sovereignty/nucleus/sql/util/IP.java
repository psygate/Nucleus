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

import java.util.Arrays;

/**
 * Created by psygate (https://github.com/psygate) on 27.01.2016.
 */
public class IP {
    private final byte[] SINGLE_IP_MASK = new byte[]{~0, ~0, ~0, ~0};
    private final byte[] ip, netmask;

    public IP(byte[] ip, byte[] netmask) {
        assert ip.length == netmask.length;
        this.ip = ip;
        this.netmask = netmask;
    }

    public IP(byte[] ip) {
        this.ip = ip;
        this.netmask = Arrays.copyOf(SINGLE_IP_MASK, SINGLE_IP_MASK.length);
    }

    public byte[] getIp() {
        return ip;
    }

    public byte[] getNetmask() {
        return netmask;
    }

    @Override
    public String toString() {
        String out = "";

        for (byte anIp : ip) {
            out += anIp & 0xFF;
        }

        if (!Arrays.equals(netmask, SINGLE_IP_MASK)) {
            out += "/";
            for (byte aNetmask : netmask) {
                out += aNetmask & 0xFF;
            }
        }

        return out;
    }
}
