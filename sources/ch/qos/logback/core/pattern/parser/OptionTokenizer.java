package ch.qos.logback.core.pattern.parser;

import ch.qos.logback.core.pattern.util.AsIsEscapeUtil;
import ch.qos.logback.core.pattern.util.IEscapeUtil;
import ch.qos.logback.core.spi.ScanException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class OptionTokenizer {
    private static final int EXPECTING_STATE = 0;
    private static final int QUOTED_COLLECTING_STATE = 2;
    private static final int RAW_COLLECTING_STATE = 1;
    final IEscapeUtil escapeUtil;
    final String pattern;
    final int patternLength;
    char quoteChar;
    int state;
    final TokenStream tokenStream;

    OptionTokenizer(TokenStream tokenStream) {
        this(tokenStream, new AsIsEscapeUtil());
    }

    OptionTokenizer(TokenStream tokenStream, IEscapeUtil iEscapeUtil) {
        this.state = 0;
        this.tokenStream = tokenStream;
        this.pattern = tokenStream.pattern;
        this.patternLength = tokenStream.patternLength;
        this.escapeUtil = iEscapeUtil;
    }

    void emitOptionToken(List<Token> list, List<String> list2) {
        list.add(new Token(1006, list2));
        this.tokenStream.state = TokenStream.TokenizerState.LITERAL_STATE;
    }

    void escape(String str, StringBuffer stringBuffer) {
        if (this.tokenStream.pointer < this.patternLength) {
            String str2 = this.pattern;
            TokenStream tokenStream = this.tokenStream;
            int i = tokenStream.pointer;
            tokenStream.pointer = i + 1;
            this.escapeUtil.escape(str, stringBuffer, str2.charAt(i), this.tokenStream.pointer);
        }
    }

    void tokenize(char c, List<Token> list) throws ScanException {
        String strTrim;
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList();
        while (this.tokenStream.pointer < this.patternLength) {
            int i = this.state;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        char c2 = this.quoteChar;
                        if (c == c2) {
                            strTrim = stringBuffer.toString();
                            arrayList.add(strTrim);
                            stringBuffer.setLength(0);
                            this.state = 0;
                        } else if (c == '\\') {
                            escape(String.valueOf(c2), stringBuffer);
                        } else {
                            stringBuffer.append(c);
                        }
                    }
                } else if (c != ',') {
                    if (c == '}') {
                        arrayList.add(stringBuffer.toString().trim());
                        emitOptionToken(list, arrayList);
                    }
                    stringBuffer.append(c);
                } else {
                    strTrim = stringBuffer.toString().trim();
                    arrayList.add(strTrim);
                    stringBuffer.setLength(0);
                    this.state = 0;
                }
            } else if (c != '\t' && c != '\n' && c != '\r' && c != ' ') {
                if (c == '\"' || c == '\'') {
                    this.state = 2;
                    this.quoteChar = c;
                } else if (c == ',') {
                    continue;
                } else if (c == '}') {
                    emitOptionToken(list, arrayList);
                    return;
                } else {
                    stringBuffer.append(c);
                    this.state = 1;
                }
            }
            c = this.pattern.charAt(this.tokenStream.pointer);
            this.tokenStream.pointer++;
        }
        if (c != '}') {
            throw new ScanException("Unexpected end of pattern string in OptionTokenizer");
        }
        int i2 = this.state;
        if (i2 == 0) {
            emitOptionToken(list, arrayList);
            return;
        }
        if (i2 != 1) {
            throw new ScanException("Unexpected end of pattern string in OptionTokenizer");
        }
        arrayList.add(stringBuffer.toString().trim());
        emitOptionToken(list, arrayList);
    }
}
