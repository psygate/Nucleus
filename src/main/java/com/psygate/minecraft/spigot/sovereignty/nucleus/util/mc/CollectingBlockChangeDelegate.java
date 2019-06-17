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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.*;

/**
 * Created by psygate (https://github.com/psygate) on 20.02.2016.
 */
public class CollectingBlockChangeDelegate implements BlockChangeDelegate {
    private final Block reference;
    private final Map<Location, BlockState> states = new HashMap<>();
    private final Set<Material> ignored = new HashSet<>();

    public CollectingBlockChangeDelegate(Block reference, Material... ignored) {
        this.reference = Objects.requireNonNull(reference);
        this.ignored.addAll(Arrays.asList(ignored));
    }

    public CollectingBlockChangeDelegate(Block reference, Collection<Material> ignored) {
        this.reference = Objects.requireNonNull(reference);
        this.ignored.addAll(ignored);
    }

    @Override
    public boolean setRawTypeId(int x, int y, int z, int typeId) {
        //noinspection deprecation
        return stateOf(x, y, z).setTypeId(typeId);
    }

    @Override
    public boolean setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
        return setTypeIdAndData(x, y, z, typeId, data);
    }

    @Override
    public boolean setTypeId(int x, int y, int z, int typeId) {
        //noinspection deprecation
        return stateOf(x, y, z).setTypeId(typeId);
    }

    @Override
    public boolean setTypeIdAndData(int x, int y, int z, int typeId, int data) {
        //noinspection deprecation
        if (stateOf(x, y, z).setTypeId(typeId)) {
            //noinspection deprecation
            stateOf(x, y, z).setRawData((byte) data);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getTypeId(int x, int y, int z) {
        if (ignored.contains(stateOf(x, y, z).getType())) {
            //noinspection deprecation
            return Material.AIR.getId();
        } else {
            //noinspection deprecation
            return stateOf(x, y, z).getTypeId();
        }
    }

    @Override
    public int getHeight() {
        return reference.getWorld().getMaxHeight();
    }

    @Override
    public boolean isEmpty(int x, int y, int z) {
//        if (ignored.contains(stateOf(x, y, z).getType())) {
//            System.out.println("EMPTY!");
//        } else {
//            System.out.println(stateOf(x, y, z).getType());
//        }

        return ignored.contains(stateOf(x, y, z).getType());
    }

    private BlockState stateOf(int x, int y, int z) {
        return states.compute(new Location(reference.getWorld(), x, y, z), (location, blockState) -> {
            if (blockState == null) {
                return reference.getWorld().getBlockAt(location).getState();
            } else {
                return blockState;
            }
        });
    }

    public List<BlockState> getStates() {
        return new ArrayList<>(states.values());
    }
}
