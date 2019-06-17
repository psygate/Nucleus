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

import com.psygate.minecraft.spigot.sovereignty.nucleus.grammars.SQLFileHeaderLexer;
import com.psygate.minecraft.spigot.sovereignty.nucleus.grammars.SQLFileHeaderListener;
import com.psygate.minecraft.spigot.sovereignty.nucleus.grammars.SQLFileHeaderParser;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.DatabaseType;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by psygate (https://github.com/psygate) on 22.01.2016.
 */
public class Script {
    private final static Pattern HEADER = Pattern.compile("\\s*--\\s*SCRIPT\\s*INFORMATION\\s*--\\s*");
    private final ArrayList<DatabaseType> databaseTypes = new ArrayList<>();
    private final ArrayList<Integer> upgradeVersions = new ArrayList<>();
    private final String text;
    private int version;

    public Script(String script) {
        version = 0;
        StringBuilder scriptheader = new StringBuilder();
        boolean append = false;

        for (String s : script.split("\n|\r")) {
            String line = s.toUpperCase();

            if (append) {
                scriptheader.append(line).append("\n");
            }

            if (HEADER.matcher(line).matches()) {
                append = !append;
                if (append) {
                    scriptheader.append(line).append("\n");
                }
            }
        }

        SQLFileHeaderLexer lexer = new SQLFileHeaderLexer(new ANTLRInputStream(scriptheader.toString()));
        SQLFileHeaderParser parser = new SQLFileHeaderParser(new CommonTokenStream(lexer));
        parser.addParseListener(new SQLFileHeaderListener() {

            @Override
            public void visitTerminal(TerminalNode terminalNode) {

            }

            @Override
            public void visitErrorNode(ErrorNode errorNode) {
                throw new IllegalArgumentException("Illegal text header.");
            }

            @Override
            public void enterEveryRule(ParserRuleContext parserRuleContext) {

            }

            @Override
            public void exitEveryRule(ParserRuleContext parserRuleContext) {

            }

            @Override
            public void enterTypes(SQLFileHeaderParser.TypesContext ctx) {

            }

            @Override
            public void exitTypes(SQLFileHeaderParser.TypesContext ctx) {
                databaseTypes.addAll(ctx.TYPE().stream().map(node -> DatabaseType.valueOf(node.getText().toUpperCase())).collect(Collectors.toList()));
            }

            @Override
            public void enterVersions(SQLFileHeaderParser.VersionsContext ctx) {

            }

            @Override
            public void exitVersions(SQLFileHeaderParser.VersionsContext ctx) {
                version = Integer.parseInt(ctx.NUMBER().getText());
            }

            @Override
            public void enterUpgrades(SQLFileHeaderParser.UpgradesContext ctx) {

            }

            @Override
            public void exitUpgrades(SQLFileHeaderParser.UpgradesContext ctx) {
                upgradeVersions.addAll(ctx.NUMBER().stream().map(TerminalNode::getText).map(Integer::parseInt).collect(Collectors.toList()));
            }

            @Override
            public void enterHeader(SQLFileHeaderParser.HeaderContext ctx) {

            }

            @Override
            public void exitHeader(SQLFileHeaderParser.HeaderContext ctx) {

            }

            @Override
            public void enterScript(SQLFileHeaderParser.ScriptContext ctx) {

            }

            @Override
            public void exitScript(SQLFileHeaderParser.ScriptContext ctx) {

            }
        });
        parser.script();

        this.text = script;
    }

    public List<DatabaseType> getDatabaseTypes() {
        return Collections.unmodifiableList(databaseTypes);
    }

    public int getVersion() {
        return version;
    }

    public List<Integer> getUpgradeVersions() {
        return Collections.unmodifiableList(upgradeVersions);
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Script " + databaseTypes + " V." + version + " -> " + upgradeVersions + " [" + text.length() + "]";
    }
}
