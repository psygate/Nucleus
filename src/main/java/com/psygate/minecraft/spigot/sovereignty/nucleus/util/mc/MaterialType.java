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

import org.bukkit.Material;

/**
 * Created by psygate (https://github.com/psygate) on 21.02.2016.
 */
public class MaterialType {
    private final Material material;
    private final byte data;

    public MaterialType(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public MaterialType(Material material, int data) {
        this.material = material;

        if (data > Byte.MAX_VALUE || data < Byte.MIN_VALUE) {
            throw new IllegalArgumentException("Byte state out of range.");
        }
        this.data = (byte) data;
    }


    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialType that = (MaterialType) o;

        if (data != that.data) return false;
        return material == that.material;

    }

    @Override
    public int hashCode() {
        int result = material != null ? material.hashCode() : 0;
        result = 31 * result + (int) data;
        return result;
    }

    @Override
    public String toString() {
        return "Material[" + getMaterial() + ", " + getData() + "]";
    }
}
