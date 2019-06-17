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

import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by psygate (https://github.com/psygate) on 21.01.2016.
 */
public abstract class NucleusOPCommand extends NucleusCommand {

    public NucleusOPCommand(int minargs, int maxargs) {
        super(minargs, maxargs);
    }

    @Override
    public final void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        if (!commandSender.isOp() || !(commandSender instanceof Player)) {
            throw new CommandException("This is a player OP only command.");
        } else {
            subOnCommand((Player) commandSender, command, s, strings);
        }
    }

    protected abstract void subOnCommand(Player sender, Command command, String alias, String[] args) throws Exception;
}
