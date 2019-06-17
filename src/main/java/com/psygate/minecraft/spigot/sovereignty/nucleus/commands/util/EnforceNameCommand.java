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
public class EnforceNameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + command.getUsage());
        } else {
            String oldname = args[0];
            String newname = args[1];

            if (newname.length() > 16 || !newname.matches("[A-z_]+")) {
                sender.sendMessage(ChatColor.RED + "Illegal name. Rename aborted. (\"" + newname + "\")");
            } else {
                Optional<NucleusUsernamesRecord> recopt = Nucleus.DBI().submit(
                        (TransactionalCallable<Optional<NucleusUsernamesRecord>>) conf ->
                                DSL.using(conf)
                                        .selectFrom(Tables.NUCLEUS_USERNAMES)
                                        .where(Tables.NUCLEUS_USERNAMES.USERNAME.eq(oldname))
                                        .fetchOptional()
                );

                if (!recopt.isPresent()) {
                    sender.sendMessage(ChatColor.RED + "Can't find player \"" + oldname + "\"");
                } else {
                    sender.sendMessage(ChatColor.GREEN + "Enforcing name on player: \"" + oldname + "\" new name: \"" + newname + "\"");
                    NucleusUsernamesRecord rec = recopt.get();
                    rec.setUsername(newname);
                    Nucleus.DBI().submit(conf -> {
                        DSL.using(conf).executeUpdate(rec);
                    });

                    Player player = Bukkit.getPlayer(rec.getPuuid());

                    if (player != null) {
                        player.kickPlayer("Please relog.");
                        sender.sendMessage(ChatColor.YELLOW + "Player was online and has been kicked. Relog necessary.");
                    }
                    PlayerManager.getInstance().invalidate(rec.getPuuid(), oldname, newname);
                    sender.sendMessage(ChatColor.GREEN + "Enforced name on player.");
                }
            }
        }

        return true;
    }
}
