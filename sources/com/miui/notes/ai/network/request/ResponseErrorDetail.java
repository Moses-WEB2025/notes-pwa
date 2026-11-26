package com.miui.notes.ai.network.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResponseErrorDetail.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/ai/network/request/ResponseErrorDetail;", "", "code", "", "message", "", "traceId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;Ljava/lang/String;)V", "getCode", "()I", "getMessage", "()Ljava/lang/String;", "getTraceId", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ResponseErrorDetail {
    private final int code;
    private final String message;
    private final String traceId;

    public ResponseErrorDetail(int i, String str, String traceId) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        this.code = i;
        this.message = str;
        this.traceId = traceId;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }

    public final String getTraceId() {
        return this.traceId;
    }
}
