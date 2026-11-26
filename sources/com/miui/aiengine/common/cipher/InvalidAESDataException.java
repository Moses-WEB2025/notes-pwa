package com.miui.aiengine.common.cipher;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InvalidAESDataException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/miui/aiengine/common/cipher/InvalidAESDataException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "message", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class InvalidAESDataException extends Exception {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InvalidAESDataException(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
    }
}
