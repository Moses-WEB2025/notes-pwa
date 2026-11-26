package com.miui.aiengine.common;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Response.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\t\u001a\u00060\u0003j\u0002`\u0004HÆ\u0003J\u0017\u0010\n\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0015\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"Lcom/miui/aiengine/common/Error;", "Lcom/miui/aiengine/common/Result;", "exception", "Ljava/lang/Exception;", "Lkotlin/Exception;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Exception;)V", "getException", "()Ljava/lang/Exception;", "component1", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "", "hashCode", "", "toString", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class Error extends Result {
    private final Exception exception;

    public static /* synthetic */ Error copy$default(Error error, Exception exc, int i, Object obj) {
        if ((i & 1) != 0) {
            exc = error.exception;
        }
        return error.copy(exc);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Exception getException() {
        return this.exception;
    }

    public final Error copy(Exception exception) {
        Intrinsics.checkNotNullParameter(exception, "exception");
        return new Error(exception);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Error) && Intrinsics.areEqual(this.exception, ((Error) other).exception);
    }

    public int hashCode() {
        return this.exception.hashCode();
    }

    public String toString() {
        return "Error(exception=" + this.exception + ")";
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Error(Exception exception) {
        super(null);
        Intrinsics.checkNotNullParameter(exception, "exception");
        this.exception = exception;
    }

    public final Exception getException() {
        return this.exception;
    }
}
