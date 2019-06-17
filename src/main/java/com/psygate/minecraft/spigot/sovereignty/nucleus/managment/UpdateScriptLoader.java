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

package com.psygate.minecraft.spigot.sovereignty.nucleus.managment;

import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by psygate (https://github.com/psygate) on 21.01.2016.
 */
public class UpdateScriptLoader {
    final static Pattern FILENAMEPATTERN = Pattern.compile("dbm.*\\.sql", Pattern.DOTALL);
//    final static Pattern HEADERMARKER = Pattern.compile("--\\w*SCRIPT\\w*INFORMATION\\w*--", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
//    final static Pattern VERSIONMARKER = Pattern.compile("--\\w*Version:\\w*--([0-9]+)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private final Logger LOG = Nucleus.getInstance().getSubLogger(UpdateScriptLoader.class.getName());

    private final List<Script> scripts = new LinkedList<>();

    public UpdateScriptLoader(URL location) throws IOException {

        JarFile file = new JarFile(location.getFile());
        Enumeration<JarEntry> en = file.entries();
        while (en.hasMoreElements()) {
            JarEntry entry = en.nextElement();

            String cleanname = entry.getName().replaceFirst(".*/", "");
            Matcher matcher = FILENAMEPATTERN.matcher(cleanname);
            if (matcher.matches()) {
                LOG.finer("Loading: " + entry + " from " + file);
                String text = loadScript(file, entry);
                try {
                    Script script = new Script(text);
                    LOG.finer("Adding: " + text);
                    this.scripts.add(script);
                } catch (IllegalArgumentException expected) {
                    //Expected. Log for comprehension.
                    expected.printStackTrace(System.err);
                }
            }
        }

//        buildGraph();
    }

    public List<Script> getScripts() {
        return Collections.unmodifiableList(scripts);
    }

    private String loadScript(JarFile file, JarEntry entry) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream is = file.getInputStream(entry)) {
            int read;

            while ((read = is.read()) != -1) {
                out.write(read);
            }
        }

        return new String(out.toByteArray(), Charset.forName("UTF-8")).replace("\r\n", "\n").replace("\n\r", "\n").replace("\r", "\n");
    }

    private ScriptHeader parseScriptHeader(String script) {
        boolean parse = false;

//        int version = 0;

        for (String line : script.split("\n")) {
            if (parse) {
                if (line.contains("-- Version:")) {
                    String versions[] = line.replace("-- Version:", "").replace("\\s+", " ").split(" ");
                }
            }
            if (line.equals("-- SCRIPT INFORMATION --")) {
                parse = !parse;
            }
        }

        throw new UnsupportedOperationException();
    }

    private class ScriptHeader {
        private int sourceVersion;
        private Set<Integer> upgrades;

        public ScriptHeader(int sourceVersion, Set<Integer> upgrades) {
            this.sourceVersion = sourceVersion;
            this.upgrades = upgrades;
        }

        public int getSourceVersion() {
            return sourceVersion;
        }

        public void setSourceVersion(int sourceVersion) {
            this.sourceVersion = sourceVersion;
        }

        public Set<Integer> getUpgrades() {
            return upgrades;
        }

        public void setUpgrades(Set<Integer> upgrades) {
            this.upgrades = upgrades;
        }

        public boolean isValid() {
            return sourceVersion >= 0;
        }
    }
}
