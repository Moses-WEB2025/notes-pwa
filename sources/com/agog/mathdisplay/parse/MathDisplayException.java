package com.agog.mathdisplay.parse;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathAtom.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0005¨\u0006\t"}, d2 = {"Lcom/agog/mathdisplay/parse/MathDisplayException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "setMessage", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class MathDisplayException extends Exception {
    private String message;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MathDisplayException(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
        this.message = message;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.message = str;
    }
}
