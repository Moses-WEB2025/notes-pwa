package com.miui.aiengine.common.cipher;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CipherException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\u0005\u0010\t¨\u0006\n"}, d2 = {"Lcom/miui/aiengine/common/cipher/CipherException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "message", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "throwable", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CipherException extends Exception {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CipherException(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CipherException(String message, Throwable throwable) {
        super(message, throwable);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
    }
}
