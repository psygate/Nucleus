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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by psygate on 02.05.2016.
 */
public class SetWhitelistMessage implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + command.getUsage());
        } else {
            /*
              setwhitelistmessage:
    default: op
    description: Sets a whitelist message.
    usage: /setwhitelistmessage <whitelist message>
  setplayercapmessage:
    default: op
    description: Sets a player cap reached message.
    usage: /setplayercapmessage <player cap message>
             */
            String msg = args[0];

            for (int i = 1; i < args.length; i++) {
                msg += " " + args[i];
                msg = msg.replaceAll("\\$\\$", "§");
                Nucleus.getInstance().getConfiguration().setWhitelistMsg(msg);
            }
            Nucleus.getInstance().getConfiguration().setWhitelistMsg(msg);
            sender.sendMessage(ChatColor.GREEN + "Whitelist message set to: \"" + msg + "\"");
        }

        return true;
    }
}
