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

import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.nucleus.db.model.Tables;
import com.psygate.minecraft.spigot.sovereignty.nucleus.db.model.tables.records.NucleusUsernamesRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.util.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jooq.TransactionalCallable;
import org.jooq.impl.DSL;

import java.util.Optional;

/**
 * Created by psygate on 02.05.2016.
 */
public class SetPlayerCapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + command.getUsage());
        } else {
            try {
                int cap = Integer.parseInt(args[0]);
                if (cap <= 0) {
                    sender.sendMessage(ChatColor.RED + "Cap cannot be <= 0.");
                } else {
                    Nucleus.getInstance().getConfiguration().setPlayerCap(cap);
                    sender.sendMessage(ChatColor.GREEN + "Cap set to " + cap + ".");
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Number required as argument.");
            }
        }

        return true;
    }
}
