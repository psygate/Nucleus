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

package com.psygate.minecraft.spigot.sovereignty.nucleus.commands;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by psygate (https://github.com/psygate) on 25.01.2016.
 */
public class BanCommandTest {
    @Test
    public void test() throws Exception {
        Pattern MINECRAFT_NAME_STRING = Pattern.compile("[0-9A-Za-z_]{1,16}");
        Pattern UUID_STRING = Pattern.compile("[0-9A-Za-z-]{32,36}");
        Pattern IP_DECL = Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");
        Pattern IP_RANGE_DECL = Pattern.compile(IP_DECL.pattern() + "-" + IP_DECL.pattern());
        Pattern IP_MASK_DECL = Pattern.compile(IP_DECL.pattern() + "/[0-9]+");

        System.out.println(MINECRAFT_NAME_STRING.matcher("ALGOL").matches());
        System.out.println(MINECRAFT_NAME_STRING.matcher("psygate").matches());
        System.out.println(MINECRAFT_NAME_STRING.matcher("Blisschen").matches());
        System.out.println(MINECRAFT_NAME_STRING.matcher("_test_name_0385").matches());
        System.out.println(UUID_STRING.matcher(new UUID(0xFFFFFFFF, 0xFFFFFFFF).toString()).matches());
        System.out.println(UUID_STRING.matcher(new UUID(0xFFFFFFFF, 0xFFFFFFFF).toString().replaceAll("-", "").toString()).matches());

        System.out.println(IP_DECL.matcher("1.2.3.4").matches());
        System.out.println(IP_DECL.matcher("127.0.0.1").matches());
        System.out.println(IP_RANGE_DECL.matcher("1.2.3.4-1.2.3.4").matches());
        System.out.println(IP_RANGE_DECL.matcher("127.0.0.1-255.255.255.255").matches());
        System.out.println(IP_MASK_DECL.matcher("1.2.3.4/235908").matches());
        System.out.println(IP_MASK_DECL.matcher("127.0.0.1/12").matches());

        String datestr = "11.2.2012-13:24";
        SimpleDateFormat europe = new SimpleDateFormat("dd.MM.yyyy-HH:mm");
        System.out.println(europe.parse(datestr) + "    " + "dd.MM.yyyy-HH:mm");

        SimpleDateFormat america = new SimpleDateFormat("MM.dd.yyyy-HH:mm");
        System.out.println(america.parse(datestr) + "    " + "MM.dd.yyyy-HH:mm");
    }
}