package androidx.appsearch.util;

import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class IndentingStringBuilder {
    private final StringBuilder mStringBuilder = new StringBuilder();
    private boolean mIndentNext = false;
    private int mIndentLevel = 0;

    public IndentingStringBuilder increaseIndentLevel() {
        this.mIndentLevel++;
        return this;
    }

    public IndentingStringBuilder decreaseIndentLevel() throws IllegalStateException {
        int i = this.mIndentLevel;
        if (i == 0) {
            throw new IllegalStateException("Cannot set indent level below 0.");
        }
        this.mIndentLevel = i - 1;
        return this;
    }

    public IndentingStringBuilder append(String str) {
        applyIndentToString(str);
        return this;
    }

    public IndentingStringBuilder append(Object obj) {
        applyIndentToString(obj.toString());
        return this;
    }

    public String toString() {
        return this.mStringBuilder.toString();
    }

    private void applyIndent() {
        for (int i = 0; i < this.mIndentLevel; i++) {
            this.mStringBuilder.append("  ");
        }
    }

    private void applyIndentToString(String str) {
        int iIndexOf = str.indexOf(StringUtils.LF);
        if (iIndexOf == 0) {
            this.mStringBuilder.append(StringUtils.LF);
            this.mIndentNext = true;
            if (str.length() > 1) {
                applyIndentToString(str.substring(iIndexOf + 1));
                return;
            }
            return;
        }
        if (iIndexOf >= 1) {
            applyIndentToString(str.substring(0, iIndexOf));
            this.mStringBuilder.append(StringUtils.LF);
            this.mIndentNext = true;
            int i = iIndexOf + 1;
            if (str.length() > i) {
                applyIndentToString(str.substring(i));
                return;
            }
            return;
        }
        if (this.mIndentNext) {
            applyIndent();
            this.mIndentNext = false;
        }
        this.mStringBuilder.append(str);
    }
}
