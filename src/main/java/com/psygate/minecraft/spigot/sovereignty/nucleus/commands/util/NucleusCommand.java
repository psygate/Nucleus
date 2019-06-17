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
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Command root class, providing some utility to extending classes.
 */
public abstract class NucleusCommand implements CommandExecutor {
    private final int minargs;
    private final int maxargs;

    public NucleusCommand(int minargs, int maxargs) {
        this.minargs = minargs;
        this.maxargs = maxargs;
    }

    @Override
    public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            if (strings.length < minargs || strings.length > maxargs) {
                throw new CommandException("Argument count mismatch. (" + command.getUsage() + ")");
            }
            subOnCommand(commandSender, command, s, strings);
        } catch (CommandException e) {
            commandSender.sendMessage(e.getColor() + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * @param sender
     * @param command
     * @param alias
     * @param strings
     * @throws CommandException
     */
    protected abstract void subOnCommand(CommandSender sender, Command command, String alias, String[] strings) throws Exception;

    /**
     * @return
     */
    protected abstract String[] getName();


    protected final void asyncMessage(CommandSender sender, String message) {
        Bukkit.getScheduler().runTask(Nucleus.getInstance(), () -> sender.sendMessage(message));
    }

    protected final void asyncMessage(CommandSender sender, String[] message) {
        Bukkit.getScheduler().runTask(Nucleus.getInstance(), () -> sender.sendMessage(message));
    }
}
