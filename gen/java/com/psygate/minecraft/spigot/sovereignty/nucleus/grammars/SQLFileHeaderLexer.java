// Generated from SQLFileHeader.g4 by ANTLR 4.5.1

package com.psygate.minecraft.spigot.sovereignty.nucleus.grammars;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLFileHeaderLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, SQL_COMMENT=2, NUMBER=3, HEADER_MARKER=4, TYPE=5, TYPE_MARKER=6, 
		VERSION_MARKER=7, UPGRADE_MARKER=8;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"WS", "SQL_COMMENT", "NUMBER", "DIGITS", "OCTAL_DIGITS", "HEX_DIGITS", 
		"COLON", "HEADER_MARKER", "TYPE", "TYPE_MARKER", "VERSION_MARKER", "UPGRADE_MARKER"
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


	public SQLFileHeaderLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SQLFileHeader.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\n\u00b3\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\6\2\35\n\2\r\2\16\2\36\3\2\3\2\3\3\3\3\6\3%"+
		"\n\3\r\3\16\3&\3\3\3\3\3\4\3\4\3\4\5\4.\n\4\3\5\3\5\7\5\62\n\5\f\5\16"+
		"\5\65\13\5\3\5\6\58\n\5\r\5\16\59\5\5<\n\5\3\6\3\6\6\6@\n\6\r\6\16\6A"+
		"\3\7\3\7\3\7\3\7\6\7H\n\7\r\7\16\7I\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\7\tV\n\t\f\t\16\tY\13\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\6\nh\n\n\r\n\16\ni\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13{\n\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\5\f\u0094\n\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00b0\n\r\3\r\3\r"+
		"\2\2\16\3\3\5\4\7\5\t\2\13\2\r\2\17\2\21\6\23\7\25\b\27\t\31\n\3\2\5\5"+
		"\2\13\f\16\17\"\"\5\2\62;CHch\4\2\62;C|\u00bf\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\3\34\3\2\2\2\5\"\3\2\2\2\7-\3\2\2\2\t;\3\2\2\2\13=\3\2\2\2\r"+
		"C\3\2\2\2\17K\3\2\2\2\21M\3\2\2\2\23g\3\2\2\2\25z\3\2\2\2\27\u0093\3\2"+
		"\2\2\31\u00af\3\2\2\2\33\35\t\2\2\2\34\33\3\2\2\2\35\36\3\2\2\2\36\34"+
		"\3\2\2\2\36\37\3\2\2\2\37 \3\2\2\2 !\b\2\2\2!\4\3\2\2\2\"$\7/\2\2#%\7"+
		"/\2\2$#\3\2\2\2%&\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'(\3\2\2\2()\b\3\2\2)\6"+
		"\3\2\2\2*.\5\t\5\2+.\5\13\6\2,.\5\r\7\2-*\3\2\2\2-+\3\2\2\2-,\3\2\2\2"+
		".\b\3\2\2\2/\63\4\63;\2\60\62\4\62;\2\61\60\3\2\2\2\62\65\3\2\2\2\63\61"+
		"\3\2\2\2\63\64\3\2\2\2\64<\3\2\2\2\65\63\3\2\2\2\668\7\62\2\2\67\66\3"+
		"\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2\2;/\3\2\2\2;\67\3\2\2\2"+
		"<\n\3\2\2\2=?\7\62\2\2>@\4\629\2?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2"+
		"\2B\f\3\2\2\2CD\7\62\2\2DE\7z\2\2EG\3\2\2\2FH\t\3\2\2GF\3\2\2\2HI\3\2"+
		"\2\2IG\3\2\2\2IJ\3\2\2\2J\16\3\2\2\2KL\7<\2\2L\20\3\2\2\2MN\7U\2\2NO\7"+
		"E\2\2OP\7T\2\2PQ\7K\2\2QR\7R\2\2RS\7V\2\2SW\3\2\2\2TV\5\3\2\2UT\3\2\2"+
		"\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2XZ\3\2\2\2YW\3\2\2\2Z[\7K\2\2[\\\7P\2"+
		"\2\\]\7H\2\2]^\7Q\2\2^_\7T\2\2_`\7O\2\2`a\7C\2\2ab\7V\2\2bc\7K\2\2cd\7"+
		"Q\2\2de\7P\2\2e\22\3\2\2\2fh\t\4\2\2gf\3\2\2\2hi\3\2\2\2ig\3\2\2\2ij\3"+
		"\2\2\2j\24\3\2\2\2kl\7V\2\2lm\7{\2\2mn\7r\2\2no\7g\2\2o{\7u\2\2pq\7V\2"+
		"\2qr\7[\2\2rs\7R\2\2st\7G\2\2t{\7U\2\2uv\7v\2\2vw\7{\2\2wx\7r\2\2xy\7"+
		"g\2\2y{\7u\2\2zk\3\2\2\2zp\3\2\2\2zu\3\2\2\2{|\3\2\2\2|}\5\17\b\2}\26"+
		"\3\2\2\2~\177\7X\2\2\177\u0080\7g\2\2\u0080\u0081\7t\2\2\u0081\u0082\7"+
		"u\2\2\u0082\u0083\7k\2\2\u0083\u0084\7q\2\2\u0084\u0094\7p\2\2\u0085\u0086"+
		"\7X\2\2\u0086\u0087\7G\2\2\u0087\u0088\7T\2\2\u0088\u0089\7U\2\2\u0089"+
		"\u008a\7K\2\2\u008a\u008b\7Q\2\2\u008b\u0094\7P\2\2\u008c\u008d\7x\2\2"+
		"\u008d\u008e\7g\2\2\u008e\u008f\7t\2\2\u008f\u0090\7u\2\2\u0090\u0091"+
		"\7k\2\2\u0091\u0092\7q\2\2\u0092\u0094\7p\2\2\u0093~\3\2\2\2\u0093\u0085"+
		"\3\2\2\2\u0093\u008c\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096\5\17\b\2"+
		"\u0096\30\3\2\2\2\u0097\u0098\7W\2\2\u0098\u0099\7r\2\2\u0099\u009a\7"+
		"i\2\2\u009a\u009b\7t\2\2\u009b\u009c\7c\2\2\u009c\u009d\7f\2\2\u009d\u009e"+
		"\7g\2\2\u009e\u00b0\7u\2\2\u009f\u00a0\7W\2\2\u00a0\u00a1\7R\2\2\u00a1"+
		"\u00a2\7I\2\2\u00a2\u00a3\7T\2\2\u00a3\u00a4\7C\2\2\u00a4\u00a5\7F\2\2"+
		"\u00a5\u00a6\7G\2\2\u00a6\u00b0\7U\2\2\u00a7\u00a8\7w\2\2\u00a8\u00a9"+
		"\7r\2\2\u00a9\u00aa\7i\2\2\u00aa\u00ab\7t\2\2\u00ab\u00ac\7c\2\2\u00ac"+
		"\u00ad\7f\2\2\u00ad\u00ae\7g\2\2\u00ae\u00b0\7u\2\2\u00af\u0097\3\2\2"+
		"\2\u00af\u009f\3\2\2\2\u00af\u00a7\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2"+
		"\5\17\b\2\u00b2\32\3\2\2\2\20\2\36&-\639;AIWiz\u0093\u00af\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}