package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
public class TokenQueue implements AutoCloseable {
    private static final char Esc = '\\';
    private static final char Hyphen_Minus = '-';
    private static final char Replacement = 65533;
    private static final char Unicode_Null = 0;
    private final CharacterReader reader;
    private static final char[] ElementSelectorChars = {'*', '|', '_', '-'};
    private static final char[] CssIdentifierChars = {'-', '_'};

    private static boolean isNewline(char c) {
        return c == '\n' || c == '\r' || c == '\f';
    }

    private static boolean isNonAscii(char c) {
        return c >= 128;
    }

    public TokenQueue(String str) {
        this.reader = new CharacterReader(str);
    }

    public boolean isEmpty() {
        return this.reader.isEmpty();
    }

    public char consume() {
        return this.reader.consume();
    }

    public void advance() {
        if (isEmpty()) {
            return;
        }
        this.reader.advance();
    }

    char current() {
        return this.reader.current();
    }

    public boolean matches(String str) {
        return this.reader.matchesIgnoreCase(str);
    }

    public boolean matches(char c) {
        return this.reader.matches(c);
    }

    public boolean matchesAny(char... cArr) {
        return this.reader.matchesAny(cArr);
    }

    public boolean matchChomp(String str) {
        return this.reader.matchConsumeIgnoreCase(str);
    }

    public boolean matchChomp(char c) {
        if (!this.reader.matches(c)) {
            return false;
        }
        consume();
        return true;
    }

    public boolean matchesWhitespace() {
        return StringUtil.isWhitespace(this.reader.current());
    }

    public boolean matchesWord() {
        return Character.isLetterOrDigit(this.reader.current());
    }

    public void consume(String str) {
        if (!this.reader.matchConsumeIgnoreCase(str)) {
            throw new IllegalStateException("Queue did not match expected sequence");
        }
    }

    public String consumeTo(String str) {
        return this.reader.consumeTo(str);
    }

    public String consumeToAny(String... strArr) {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        loop0: while (!isEmpty()) {
            for (String str : strArr) {
                if (this.reader.matchesIgnoreCase(str)) {
                    break loop0;
                }
            }
            sbBorrowBuilder.append(consume());
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    public String chompBalanced(char c, char c2) {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        this.reader.mark();
        char c3 = 0;
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        boolean z3 = false;
        while (!isEmpty()) {
            char cConsume = consume();
            if (c3 == '\\') {
                if (cConsume == 'Q') {
                    z3 = true;
                } else if (cConsume == 'E') {
                    z3 = false;
                }
                sbBorrowBuilder.append(cConsume);
            } else {
                if (cConsume == '\'' && cConsume != c && !z) {
                    z2 = !z2;
                } else if (cConsume == '\"' && cConsume != c && !z2) {
                    z = !z;
                }
                if (z2 || z || z3) {
                    sbBorrowBuilder.append(cConsume);
                } else if (cConsume == c) {
                    i++;
                    if (i > 1) {
                        sbBorrowBuilder.append(cConsume);
                    }
                } else if (cConsume != c2 || (i = i - 1) > 0) {
                    sbBorrowBuilder.append(cConsume);
                }
            }
            if (i <= 0) {
                break;
            }
            c3 = cConsume;
        }
        String strReleaseBuilder = StringUtil.releaseBuilder(sbBorrowBuilder);
        if (i > 0) {
            this.reader.rewindToMark();
            Validate.fail("Did not find balanced marker at '" + strReleaseBuilder + "'");
        }
        return strReleaseBuilder;
    }

    public static String unescape(String str) {
        if (str.indexOf(92) == -1) {
            return str;
        }
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        char c = 0;
        for (char c2 : str.toCharArray()) {
            if (c2 == '\\') {
                if (c == '\\') {
                    sbBorrowBuilder.append(c2);
                    c = 0;
                }
            } else {
                sbBorrowBuilder.append(c2);
            }
            c = c2;
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    public static String escapeCssIdentifier(String str) {
        if (str.isEmpty()) {
            return str;
        }
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        TokenQueue tokenQueue = new TokenQueue(str);
        char cCurrent = tokenQueue.current();
        if (cCurrent == '-') {
            tokenQueue.advance();
            if (tokenQueue.isEmpty()) {
                appendEscaped(sbBorrowBuilder, '-');
            } else {
                sbBorrowBuilder.append('-');
                if (StringUtil.isDigit(tokenQueue.current())) {
                    appendEscapedCodepoint(sbBorrowBuilder, tokenQueue.consume());
                }
            }
        } else if (StringUtil.isDigit(cCurrent)) {
            appendEscapedCodepoint(sbBorrowBuilder, tokenQueue.consume());
        }
        while (!tokenQueue.isEmpty()) {
            char cConsume = tokenQueue.consume();
            if (cConsume == 0) {
                sbBorrowBuilder.append((char) 65533);
            } else if (cConsume <= 31 || cConsume == 127) {
                appendEscapedCodepoint(sbBorrowBuilder, cConsume);
            } else if (isIdent(cConsume)) {
                sbBorrowBuilder.append(cConsume);
            } else {
                appendEscaped(sbBorrowBuilder, cConsume);
            }
        }
        tokenQueue.close();
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    private static void appendEscaped(StringBuilder sb, char c) {
        sb.append('\\').append(c);
    }

    private static void appendEscapedCodepoint(StringBuilder sb, char c) {
        sb.append('\\').append(Integer.toHexString(c)).append(' ');
    }

    public boolean consumeWhitespace() {
        boolean z = false;
        while (matchesWhitespace()) {
            advance();
            z = true;
        }
        return z;
    }

    public String consumeElementSelector() {
        return consumeEscapedCssIdentifier(ElementSelectorChars);
    }

    public String consumeCssIdentifier() {
        if (isEmpty()) {
            throw new IllegalArgumentException("CSS identifier expected, but end of input found");
        }
        String strConsumeMatching = this.reader.consumeMatching(new CharacterReader.CharPredicate() { // from class: org.jsoup.parser.TokenQueue$$ExternalSyntheticLambda0
            @Override // org.jsoup.parser.CharacterReader.CharPredicate
            public final boolean test(char c) {
                return TokenQueue.isIdent(c);
            }
        });
        char cCurrent = current();
        if (cCurrent != '\\' && cCurrent != 0) {
            return strConsumeMatching;
        }
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        if (!strConsumeMatching.isEmpty()) {
            sbBorrowBuilder.append(strConsumeMatching);
        }
        while (!isEmpty()) {
            char cCurrent2 = current();
            if (!isIdent(cCurrent2)) {
                if (cCurrent2 != 0) {
                    if (cCurrent2 != '\\') {
                        break;
                    }
                    advance();
                    if (!isEmpty() && isNewline(current())) {
                        this.reader.unconsume();
                        break;
                    }
                    consumeCssEscapeSequenceInto(sbBorrowBuilder);
                } else {
                    advance();
                    sbBorrowBuilder.append((char) 65533);
                }
            } else {
                sbBorrowBuilder.append(consume());
            }
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    private void consumeCssEscapeSequenceInto(StringBuilder sb) {
        if (isEmpty()) {
            sb.append((char) 65533);
            return;
        }
        char cConsume = consume();
        if (!StringUtil.isHexDigit(cConsume)) {
            sb.append(cConsume);
            return;
        }
        this.reader.unconsume();
        String strConsumeMatching = this.reader.consumeMatching(new CharacterReader$$ExternalSyntheticLambda10(), 6);
        try {
            int i = Integer.parseInt(strConsumeMatching, 16);
            if (isValidCodePoint(i)) {
                sb.appendCodePoint(i);
            } else {
                sb.append((char) 65533);
            }
            if (isEmpty()) {
                return;
            }
            char cCurrent = current();
            if (cCurrent == '\r') {
                advance();
                if (isEmpty() || current() != '\n') {
                    return;
                }
                advance();
                return;
            }
            if (cCurrent == ' ' || cCurrent == '\t' || isNewline(cCurrent)) {
                advance();
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid escape sequence: " + strConsumeMatching, e);
        }
    }

    private static boolean isIdentStart(char c) {
        return c == '_' || StringUtil.isAsciiLetter(c) || isNonAscii(c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isIdent(char c) {
        return c == '-' || StringUtil.isDigit(c) || isIdentStart(c);
    }

    private static boolean isValidCodePoint(int i) {
        return (i == 0 || !Character.isValidCodePoint(i) || Character.isSurrogate((char) i)) ? false : true;
    }

    private String consumeEscapedCssIdentifier(char... cArr) {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        while (!isEmpty()) {
            char cCurrent = current();
            if (cCurrent == '\\') {
                advance();
                if (isEmpty()) {
                    break;
                }
                sbBorrowBuilder.append(consume());
            } else {
                if (!matchesCssIdentifier(cArr)) {
                    break;
                }
                sbBorrowBuilder.append(cCurrent);
                advance();
            }
        }
        return StringUtil.releaseBuilder(sbBorrowBuilder);
    }

    private boolean matchesCssIdentifier(char... cArr) {
        return matchesWord() || this.reader.matchesAny(cArr);
    }

    public String remainder() {
        return this.reader.consumeToEnd();
    }

    public String toString() {
        return this.reader.toString();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.reader.close();
    }
}
