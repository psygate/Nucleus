// Generated from SQLFileHeader.g4 by ANTLR 4.5.1

package com.psygate.minecraft.spigot.sovereignty.nucleus.grammars;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLFileHeaderParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, SQL_COMMENT=2, NUMBER=3, HEADER_MARKER=4, TYPE=5, TYPE_MARKER=6, 
		VERSION_MARKER=7, UPGRADE_MARKER=8;
	public static final int
		RULE_types = 0, RULE_versions = 1, RULE_upgrades = 2, RULE_header = 3, 
		RULE_script = 4;
	public static final String[] ruleNames = {
		"types", "versions", "upgrades", "header", "script"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WS", "SQL_COMMENT", "NUMBER", "HEADER_MARKER", "TYPE", "TYPE_MARKER", 
		"VERSION_MARKER", "UPGRADE_MARKER"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SQLFileHeader.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLFileHeaderParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class TypesContext extends ParserRuleContext {
		public TerminalNode TYPE_MARKER() { return getToken(SQLFileHeaderParser.TYPE_MARKER, 0); }
		public List<TerminalNode> TYPE() { return getTokens(SQLFileHeaderParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(SQLFileHeaderParser.TYPE, i);
		}
		public TypesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_types; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).enterTypes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).exitTypes(this);
		}
	}

	public final TypesContext types() throws RecognitionException {
		TypesContext _localctx = new TypesContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_types);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			match(TYPE_MARKER);
			setState(12); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(11);
				match(TYPE);
				}
				}
				setState(14); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TYPE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VersionsContext extends ParserRuleContext {
		public TerminalNode VERSION_MARKER() { return getToken(SQLFileHeaderParser.VERSION_MARKER, 0); }
		public TerminalNode NUMBER() { return getToken(SQLFileHeaderParser.NUMBER, 0); }
		public VersionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_versions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).enterVersions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).exitVersions(this);
		}
	}

	public final VersionsContext versions() throws RecognitionException {
		VersionsContext _localctx = new VersionsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_versions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			match(VERSION_MARKER);
			setState(17);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpgradesContext extends ParserRuleContext {
		public TerminalNode UPGRADE_MARKER() { return getToken(SQLFileHeaderParser.UPGRADE_MARKER, 0); }
		public List<TerminalNode> NUMBER() { return getTokens(SQLFileHeaderParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(SQLFileHeaderParser.NUMBER, i);
		}
		public UpgradesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_upgrades; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).enterUpgrades(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).exitUpgrades(this);
		}
	}

	public final UpgradesContext upgrades() throws RecognitionException {
		UpgradesContext _localctx = new UpgradesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_upgrades);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			match(UPGRADE_MARKER);
			setState(21); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(20);
				match(NUMBER);
				}
				}
				setState(23); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeaderContext extends ParserRuleContext {
		public TerminalNode HEADER_MARKER() { return getToken(SQLFileHeaderParser.HEADER_MARKER, 0); }
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).exitHeader(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			match(HEADER_MARKER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScriptContext extends ParserRuleContext {
		public List<HeaderContext> header() {
			return getRuleContexts(HeaderContext.class);
		}
		public HeaderContext header(int i) {
			return getRuleContext(HeaderContext.class,i);
		}
		public List<TypesContext> types() {
			return getRuleContexts(TypesContext.class);
		}
		public TypesContext types(int i) {
			return getRuleContext(TypesContext.class,i);
		}
		public List<VersionsContext> versions() {
			return getRuleContexts(VersionsContext.class);
		}
		public VersionsContext versions(int i) {
			return getRuleContext(VersionsContext.class,i);
		}
		public List<UpgradesContext> upgrades() {
			return getRuleContexts(UpgradesContext.class);
		}
		public UpgradesContext upgrades(int i) {
			return getRuleContext(UpgradesContext.class,i);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).enterScript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SQLFileHeaderListener ) ((SQLFileHeaderListener)listener).exitScript(this);
		}
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			header();
			setState(31); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(31);
				switch (_input.LA(1)) {
				case TYPE_MARKER:
					{
					setState(28);
					types();
					}
					break;
				case VERSION_MARKER:
					{
					setState(29);
					versions();
					}
					break;
				case UPGRADE_MARKER:
					{
					setState(30);
					upgrades();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(33); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TYPE_MARKER) | (1L << VERSION_MARKER) | (1L << UPGRADE_MARKER))) != 0) );
			setState(35);
			header();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\n(\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\6\2\17\n\2\r\2\16\2\20\3\3\3\3\3\3"+
		"\3\4\3\4\6\4\30\n\4\r\4\16\4\31\3\5\3\5\3\6\3\6\3\6\3\6\6\6\"\n\6\r\6"+
		"\16\6#\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2\2\'\2\f\3\2\2\2\4\22\3\2\2\2\6\25"+
		"\3\2\2\2\b\33\3\2\2\2\n\35\3\2\2\2\f\16\7\b\2\2\r\17\7\7\2\2\16\r\3\2"+
		"\2\2\17\20\3\2\2\2\20\16\3\2\2\2\20\21\3\2\2\2\21\3\3\2\2\2\22\23\7\t"+
		"\2\2\23\24\7\5\2\2\24\5\3\2\2\2\25\27\7\n\2\2\26\30\7\5\2\2\27\26\3\2"+
		"\2\2\30\31\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\7\3\2\2\2\33\34\7\6"+
		"\2\2\34\t\3\2\2\2\35!\5\b\5\2\36\"\5\2\2\2\37\"\5\4\3\2 \"\5\6\4\2!\36"+
		"\3\2\2\2!\37\3\2\2\2! \3\2\2\2\"#\3\2\2\2#!\3\2\2\2#$\3\2\2\2$%\3\2\2"+
		"\2%&\5\b\5\2&\13\3\2\2\2\6\20\31!#";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}