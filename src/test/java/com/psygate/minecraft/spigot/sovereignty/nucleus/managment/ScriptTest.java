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

package com.psygate.minecraft.spigot.sovereignty.nucleus.managment;

import org.junit.Test;

/**
 * Created by psygate (https://github.com/psygate) on 23.01.2016.
 */
public class ScriptTest {
    @Test
    public void testScript() {
        String lines = "-- SCRIPT INFORMATION --\n" +
                "-- Types: mysql mariadb\n" +
                "-- Version: 1\n" +
                "-- Upgrades: 0\n" +
                "-- SCRIPT INFORMATION --";
        Script script = new Script(lines);
//        System.out.println(script);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testScriptFail() {
        String lines = "-- SCRIPT INFORMATION --\n" +
                "-- Types: mysql mariadb\n" +
                "-- Version: 1 2\n" +
                "-- Upgrades: 0\n" +
                "-- SCRIPT INFORMATION --";
        Script script = new Script(lines);
//        System.out.println(script);
    }
}