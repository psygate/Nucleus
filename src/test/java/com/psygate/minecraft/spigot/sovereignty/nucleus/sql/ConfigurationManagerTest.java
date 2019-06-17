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

package com.psygate.minecraft.spigot.sovereignty.nucleus.sql;

import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.configuration.SQLConfiguration;
import org.bukkit.configuration.file.YamlRepresenter;
import org.junit.Test;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.PropertyUtils;

/**
 * Created by psygate (https://github.com/psygate) on 19.01.2016.
 */
public class ConfigurationManagerTest {
    @Test
    public void testStoreLoad() {
        PropertyUtils utils = new PropertyUtils();

        YamlRepresenter repr = new YamlRepresenter();
        repr.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        repr.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        repr.setPropertyUtils(utils);

        DumperOptions opts = new DumperOptions();
        opts.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        opts.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        opts.setAllowUnicode(true);
        opts.setIndent(2);
        opts.setLineBreak(DumperOptions.LineBreak.UNIX);
        opts.setPrettyFlow(true);
        opts.setSplitLines(true);
        opts.setWidth(80);
//        opts.setCanonical(true);

        Yaml yaml = new Yaml(repr, opts);

        String vals = yaml.dump(new SQLConfiguration());
        yaml.loadAs(vals, SQLConfiguration.class);
    }
}
