package ch.qos.logback.core.pattern.parser;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class Token {
    static final int COMPOSITE_KEYWORD = 1005;
    static final int CURLY_LEFT = 123;
    static final int CURLY_RIGHT = 125;
    static final int DOT = 46;
    static final int EOF = Integer.MAX_VALUE;
    static final int FORMAT_MODIFIER = 1002;
    static final int LITERAL = 1000;
    static final int MINUS = 45;
    static final int OPTION = 1006;
    static final int PERCENT = 37;
    static final int RIGHT_PARENTHESIS = 41;
    static final int SIMPLE_KEYWORD = 1004;
    private final List<String> optionsList;
    private final int type;
    private final String value;
    static Token EOF_TOKEN = new Token(Integer.MAX_VALUE, "EOF");
    static Token RIGHT_PARENTHESIS_TOKEN = new Token(41);
    static Token BARE_COMPOSITE_KEYWORD_TOKEN = new Token(1005, "BARE");
    static Token PERCENT_TOKEN = new Token(37);

    public Token(int i) {
        this(i, null, null);
    }

    public Token(int i, String str) {
        this(i, str, null);
    }

    public Token(int i, String str, List<String> list) {
        this.type = i;
        this.value = str;
        this.optionsList = list;
    }

    public Token(int i, List<String> list) {
        this(i, null, list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Token)) {
            return false;
        }
        Token token = (Token) obj;
        if (this.type != token.type) {
            return false;
        }
        String str = this.value;
        return str == null ? token.value == null : str.equals(token.value);
    }

    public List<String> getOptionsList() {
        return this.optionsList;
    }

    public int getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        int i = this.type * 29;
        String str = this.value;
        return i + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        String str;
        StringBuilder sbAppend;
        String str2;
        int i = this.type;
        if (i == 37) {
            str = "%";
        } else if (i == 41) {
            str = "RIGHT_PARENTHESIS";
        } else if (i == 1000) {
            str = "LITERAL";
        } else if (i != 1002) {
            switch (i) {
                case 1004:
                    str = "SIMPLE_KEYWORD";
                    break;
                case 1005:
                    str = "COMPOSITE_KEYWORD";
                    break;
                case 1006:
                    str = "OPTION";
                    break;
                default:
                    str = "UNKNOWN";
                    break;
            }
        } else {
            str = "FormatModifier";
        }
        if (this.value == null) {
            sbAppend = new StringBuilder("Token(").append(str);
            str2 = ")";
        } else {
            sbAppend = new StringBuilder("Token(").append(str).append(", \"").append(this.value);
            str2 = "\")";
        }
        return sbAppend.append(str2).toString();
    }
}
