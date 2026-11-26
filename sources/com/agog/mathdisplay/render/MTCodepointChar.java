package com.agog.mathdisplay.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTCharset.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/agog/mathdisplay/render/MTCodepointChar;", "", "codepoint", "", "(I)V", "getCodepoint", "()I", "toUnicodeString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MTCodepointChar {
    private final int codepoint;

    public MTCodepointChar(int i) {
        this.codepoint = i;
    }

    public final int getCodepoint() {
        return this.codepoint;
    }

    public final String toUnicodeString() {
        char[] chars = Character.toChars(this.codepoint);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(chars);
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }
}
