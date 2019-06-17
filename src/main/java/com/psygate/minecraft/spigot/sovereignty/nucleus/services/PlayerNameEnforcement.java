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

package com.psygate.minecraft.spigot.sovereignty.nucleus.services;

import com.psygate.minecraft.spigot.sovereignty.nucleus.logging.LogManager;

import java.util.logging.Logger;

/**
 * Created by psygate on 20.07.2016.
 */
public class PlayerNameEnforcement {
    private static PlayerNameEnforcement instance;


    private PlayerNameEnforcement() {
        Logger logger = LogManager.getInstance().getPassthroughLogger(PlayerNameEnforcement.class);

        logger.info("Instrumenting player name uuid gameprofile class.");
    }

    public static PlayerNameEnforcement getInstance() {
        if (instance == null) {
            instance = new PlayerNameEnforcement();
        }
        return instance;
    }
}
