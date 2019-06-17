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

package com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util;

import org.bukkit.ChatColor;

import java.util.Objects;

/**
 * A general exception thrown by commands, that will be displayed to the invoking client.
 */
public class CommandException extends RuntimeException {
    private ChatColor color = ChatColor.RED;

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, ChatColor color) {
        super(message);
        this.color = Objects.requireNonNull(color);
    }

    public ChatColor getColor() {
        return color;
    }

    /**
     * Sets the color in which the error will appear to the client.
     *
     * @param color
     */
    public void setColor(ChatColor color) {
        this.color = Objects.requireNonNull(color);
    }
}
