package ch.qos.logback.core.subst;

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.spi.ScanException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Tokenizer {
    final String pattern;
    final int patternLength;
    TokenizerState state = TokenizerState.LITERAL_STATE;
    int pointer = 0;

    /* JADX INFO: renamed from: ch.qos.logback.core.subst.Tokenizer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ch$qos$logback$core$subst$Tokenizer$TokenizerState;

        static {
            int[] iArr = new int[TokenizerState.values().length];
            $SwitchMap$ch$qos$logback$core$subst$Tokenizer$TokenizerState = iArr;
            try {
                iArr[TokenizerState.LITERAL_STATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$subst$Tokenizer$TokenizerState[TokenizerState.START_STATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$ch$qos$logback$core$subst$Tokenizer$TokenizerState[TokenizerState.DEFAULT_VAL_STATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    enum TokenizerState {
        LITERAL_STATE,
        START_STATE,
        DEFAULT_VAL_STATE
    }

    public Tokenizer(String str) {
        this.pattern = str;
        this.patternLength = str.length();
    }

    private void addLiteralToken(List<Token> list, StringBuilder sb) {
        if (sb.length() == 0) {
            return;
        }
        list.add(new Token(Token.Type.LITERAL, sb.toString()));
    }

    private void handleDefaultValueState(char c, List<Token> list, StringBuilder sb) {
        TokenizerState tokenizerState;
        Token token;
        if (c != '$') {
            if (c != '-') {
                if (c != '{') {
                    sb.append(CoreConstants.COLON_CHAR).append(c);
                } else {
                    sb.append(CoreConstants.COLON_CHAR);
                    addLiteralToken(list, sb);
                    sb.setLength(0);
                    token = Token.CURLY_LEFT_TOKEN;
                }
                tokenizerState = TokenizerState.LITERAL_STATE;
            } else {
                token = Token.DEFAULT_SEP_TOKEN;
            }
            list.add(token);
            tokenizerState = TokenizerState.LITERAL_STATE;
        } else {
            sb.append(CoreConstants.COLON_CHAR);
            addLiteralToken(list, sb);
            sb.setLength(0);
            tokenizerState = TokenizerState.START_STATE;
        }
        this.state = tokenizerState;
    }

    private void handleLiteralState(char c, List<Token> list, StringBuilder sb) {
        if (c == '$') {
            addLiteralToken(list, sb);
            sb.setLength(0);
            this.state = TokenizerState.START_STATE;
            return;
        }
        if (c == ':') {
            addLiteralToken(list, sb);
            sb.setLength(0);
            this.state = TokenizerState.DEFAULT_VAL_STATE;
        } else if (c == '{') {
            addLiteralToken(list, sb);
            list.add(Token.CURLY_LEFT_TOKEN);
            sb.setLength(0);
        } else {
            if (c != '}') {
                sb.append(c);
                return;
            }
            addLiteralToken(list, sb);
            list.add(Token.CURLY_RIGHT_TOKEN);
            sb.setLength(0);
        }
    }

    private void handleStartState(char c, List<Token> list, StringBuilder sb) {
        if (c == '{') {
            list.add(Token.START_TOKEN);
        } else {
            sb.append('$').append(c);
        }
        this.state = TokenizerState.LITERAL_STATE;
    }

    List<Token> tokenize() throws ScanException {
        char c;
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        while (true) {
            int i = this.pointer;
            if (i >= this.patternLength) {
                break;
            }
            char cCharAt = this.pattern.charAt(i);
            this.pointer++;
            int i2 = AnonymousClass1.$SwitchMap$ch$qos$logback$core$subst$Tokenizer$TokenizerState[this.state.ordinal()];
            if (i2 == 1) {
                handleLiteralState(cCharAt, arrayList, sb);
            } else if (i2 == 2) {
                handleStartState(cCharAt, arrayList, sb);
            } else if (i2 == 3) {
                handleDefaultValueState(cCharAt, arrayList, sb);
            }
        }
        int i3 = AnonymousClass1.$SwitchMap$ch$qos$logback$core$subst$Tokenizer$TokenizerState[this.state.ordinal()];
        if (i3 == 1) {
            addLiteralToken(arrayList, sb);
            return arrayList;
        }
        if (i3 == 2) {
            c = '$';
        } else {
            if (i3 != 3) {
                return arrayList;
            }
            c = CoreConstants.COLON_CHAR;
        }
        sb.append(c);
        addLiteralToken(arrayList, sb);
        return arrayList;
    }
}
