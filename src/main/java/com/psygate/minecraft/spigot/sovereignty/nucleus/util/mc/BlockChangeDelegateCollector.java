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

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import java.util.*;

/**
 * Created by psygate (https://github.com/psygate) on 29.02.2016.
 */
public class BlockChangeDelegateCollector implements BlockChangeDelegate {
    private final Set<MaterialType> transparent;
    private final World world;
    private Map<BlockKey, BlockState> states = new HashMap<>();

    public BlockChangeDelegateCollector(Set<MaterialType> transparent, World world) {
        this.transparent = transparent;
        this.world = world;
    }

    @Override
    public boolean setRawTypeId(int x, int y, int z, int typeId) {
        BlockState state = world.getBlockAt(x, y, z).getState();

        if (transparent.contains(new MaterialType(state.getType(), state.getRawData()))) {
            state.setType(Material.getMaterial(typeId));
            states.put(new BlockKey(state), state);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
        BlockState state = world.getBlockAt(x, y, z).getState();

        if (transparent.contains(new MaterialType(state.getType(), state.getRawData()))) {
            state.setType(Material.getMaterial(typeId));
            state.setRawData((byte) data);
            states.put(new BlockKey(state), state);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setTypeId(int x, int y, int z, int typeId) {
        return setRawTypeId(x, y, z, typeId);
    }

    @Override
    public boolean setTypeIdAndData(int x, int y, int z, int typeId, int data) {
        return setRawTypeIdAndData(x, y, z, typeId, data);
    }

    @Override
    public int getTypeId(int x, int y, int z) {
        BlockState state = world.getBlockAt(x, y, z).getState();
        if (transparent.contains(new MaterialType(state.getType(), state.getRawData()))) {
            return 0;
        } else {
            return state.getType().getId();
        }
    }

    @Override
    public int getHeight() {
        return world.getMaxHeight();
    }

    @Override
    public boolean isEmpty(int x, int y, int z) {
        return getTypeId(x, y, z) == 0;
    }

    public List<BlockState> getStates() {
        return new ArrayList<>(states.values());
    }
}
