// Generated from SQLFileHeader.g4 by ANTLR 4.5.1

package com.psygate.minecraft.spigot.sovereignty.nucleus.grammars;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SQLFileHeaderParser}.
 */
public interface SQLFileHeaderListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SQLFileHeaderParser#types}.
	 * @param ctx the parse tree
	 */
	void enterTypes(SQLFileHeaderParser.TypesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLFileHeaderParser#types}.
	 * @param ctx the parse tree
	 */
	void exitTypes(SQLFileHeaderParser.TypesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLFileHeaderParser#versions}.
	 * @param ctx the parse tree
	 */
	void enterVersions(SQLFileHeaderParser.VersionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLFileHeaderParser#versions}.
	 * @param ctx the parse tree
	 */
	void exitVersions(SQLFileHeaderParser.VersionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLFileHeaderParser#upgrades}.
	 * @param ctx the parse tree
	 */
	void enterUpgrades(SQLFileHeaderParser.UpgradesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLFileHeaderParser#upgrades}.
	 * @param ctx the parse tree
	 */
	void exitUpgrades(SQLFileHeaderParser.UpgradesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLFileHeaderParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(SQLFileHeaderParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLFileHeaderParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(SQLFileHeaderParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLFileHeaderParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(SQLFileHeaderParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLFileHeaderParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(SQLFileHeaderParser.ScriptContext ctx);
}