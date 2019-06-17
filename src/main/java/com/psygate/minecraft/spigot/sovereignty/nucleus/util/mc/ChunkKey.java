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

package com.psygate.minecraft.spigot.sovereignty.nucleus.util.mc;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

/**
 * Created by psygate (https://github.com/psygate) on 04.03.2016.
 */
public final class ChunkKey {
    private final int x, z;
    private final UUID uuid;

    public ChunkKey(Chunk block) {
        this.x = block.getX();
        this.z = block.getZ();
        this.uuid = block.getWorld().getUID();
    }

    public ChunkKey(int x, int z, UUID uuid) {
        this.x = x;
        this.z = z;
        this.uuid = uuid;
    }

    public int getX() {
        return x;
    }


    public int getZ() {
        return z;
    }

    public UUID getUuid() {
        return uuid;
    }

    public World getWorld() {
        return Bukkit.getWorld(uuid);
    }

    public Location getLocation() {
        return new Location(getWorld(), x, 0, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChunkKey blockKey = (ChunkKey) o;

        if (x != blockKey.x) return false;
        if (z != blockKey.z) return false;
        return uuid.equals(blockKey.uuid);

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        result = 31 * result + uuid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + z + ", " + uuid + ")";
    }
}