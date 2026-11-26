package org.jsoup.parser;

import org.jsoup.internal.StringUtil;

/* JADX INFO: loaded from: classes6.dex */
class TokenData {
    private StringBuilder builder;
    private String value;

    TokenData() {
    }

    void set(String str) {
        reset();
        this.value = str;
    }

    void append(String str) {
        StringBuilder sb = this.builder;
        if (sb != null) {
            sb.append(str);
        } else if (this.value != null) {
            flipToBuilder();
            this.builder.append(str);
        } else {
            this.value = str;
        }
    }

    void append(char c) {
        StringBuilder sb = this.builder;
        if (sb != null) {
            sb.append(c);
        } else if (this.value != null) {
            flipToBuilder();
            this.builder.append(c);
        } else {
            this.value = String.valueOf(c);
        }
    }

    void appendCodePoint(int i) {
        StringBuilder sb = this.builder;
        if (sb != null) {
            sb.appendCodePoint(i);
        } else if (this.value != null) {
            flipToBuilder();
            this.builder.appendCodePoint(i);
        } else {
            this.value = String.valueOf(Character.toChars(i));
        }
    }

    private void flipToBuilder() {
        StringBuilder sbBorrowBuilder = StringUtil.borrowBuilder();
        this.builder = sbBorrowBuilder;
        sbBorrowBuilder.append(this.value);
        this.value = null;
    }

    boolean hasData() {
        return (this.builder == null && this.value == null) ? false : true;
    }

    void reset() {
        StringBuilder sb = this.builder;
        if (sb != null) {
            StringUtil.releaseBuilderVoid(sb);
            this.builder = null;
        }
        this.value = null;
    }

    String value() {
        StringBuilder sb = this.builder;
        if (sb != null) {
            this.value = sb.toString();
            StringUtil.releaseBuilder(this.builder);
            this.builder = null;
            return this.value;
        }
        String str = this.value;
        return str != null ? str : "";
    }

    public String toString() {
        StringBuilder sb = this.builder;
        if (sb != null) {
            return sb.toString();
        }
        String str = this.value;
        return str != null ? str : "";
    }
}
