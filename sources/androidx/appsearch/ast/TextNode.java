package androidx.appsearch.ast;

import androidx.core.util.Preconditions;
import ch.qos.logback.core.CoreConstants;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class TextNode implements Node {
    private boolean mPrefix;
    private String mValue;
    private boolean mVerbatim;

    private boolean isLatinLetter(char c) {
        if (c < 'A' || c > 'Z') {
            return c >= 'a' && c <= 'z';
        }
        return true;
    }

    private boolean isSpecialCharacter(char c) {
        if (c == '\"' || c == '&' || c == ':' || c == '\\' || c == '|') {
            return true;
        }
        switch (c) {
            case '(':
            case ')':
            case '*':
                return true;
            default:
                switch (c) {
                    case ',':
                    case '-':
                    case '.':
                        return true;
                    default:
                        switch (c) {
                            case '<':
                            case '=':
                            case '>':
                                return true;
                            default:
                                return false;
                        }
                }
        }
    }

    public TextNode(String str) {
        this.mPrefix = false;
        this.mVerbatim = false;
        this.mValue = (String) Preconditions.checkNotNull(str);
    }

    public TextNode(TextNode textNode) {
        this.mPrefix = false;
        this.mVerbatim = false;
        Preconditions.checkNotNull(textNode);
        this.mValue = textNode.mValue;
        this.mPrefix = textNode.mPrefix;
        this.mVerbatim = textNode.mVerbatim;
    }

    public String getValue() {
        return this.mValue;
    }

    public boolean isPrefix() {
        return this.mPrefix;
    }

    public boolean isVerbatim() {
        return this.mVerbatim;
    }

    public void setValue(String str) {
        this.mValue = (String) Preconditions.checkNotNull(str);
    }

    public void setPrefix(boolean z) {
        this.mPrefix = z;
    }

    public void setVerbatim(boolean z) {
        this.mVerbatim = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CoreConstants.LEFT_PARENTHESIS_CHAR);
        if (this.mVerbatim) {
            sb.append('\"');
        }
        escapeString(sb);
        if (this.mVerbatim) {
            sb.append('\"');
        }
        if (this.mPrefix) {
            sb.append('*');
        }
        sb.append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        return sb.toString();
    }

    private void escapeString(StringBuilder sb) {
        for (int i = 0; i < this.mValue.length(); i++) {
            char cCharAt = this.mValue.charAt(i);
            if ((!this.mVerbatim && isSpecialCharacter(cCharAt)) || cCharAt == '\"') {
                sb.append(CoreConstants.ESCAPE_CHAR);
            } else if (!this.mVerbatim && isLatinLetter(cCharAt) && Character.isUpperCase(cCharAt)) {
                cCharAt = Character.toLowerCase(cCharAt);
            }
            sb.append(cCharAt);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            TextNode textNode = (TextNode) obj;
            if (this.mPrefix == textNode.mPrefix && this.mVerbatim == textNode.mVerbatim && Objects.equals(this.mValue, textNode.mValue)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mValue, Boolean.valueOf(this.mPrefix), Boolean.valueOf(this.mVerbatim));
    }
}
