package com.miui.notes.ai.network.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResultContentCombine.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J+\u0010\u000f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/ai/network/request/ResultContentCombine;", "", "resultContent", "", "resultContentStream", "traceId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getResultContent", "()Ljava/lang/String;", "getResultContentStream", "getTraceId", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ResultContentCombine {
    private final String resultContent;
    private final String resultContentStream;
    private final String traceId;

    public static /* synthetic */ ResultContentCombine copy$default(ResultContentCombine resultContentCombine, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = resultContentCombine.resultContent;
        }
        if ((i & 2) != 0) {
            str2 = resultContentCombine.resultContentStream;
        }
        if ((i & 4) != 0) {
            str3 = resultContentCombine.traceId;
        }
        return resultContentCombine.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getResultContent() {
        return this.resultContent;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getResultContentStream() {
        return this.resultContentStream;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getTraceId() {
        return this.traceId;
    }

    public final ResultContentCombine copy(String resultContent, String resultContentStream, String traceId) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        return new ResultContentCombine(resultContent, resultContentStream, traceId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResultContentCombine)) {
            return false;
        }
        ResultContentCombine resultContentCombine = (ResultContentCombine) other;
        return Intrinsics.areEqual(this.resultContent, resultContentCombine.resultContent) && Intrinsics.areEqual(this.resultContentStream, resultContentCombine.resultContentStream) && Intrinsics.areEqual(this.traceId, resultContentCombine.traceId);
    }

    public int hashCode() {
        String str = this.resultContent;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.resultContentStream;
        return ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + this.traceId.hashCode();
    }

    public String toString() {
        return "ResultContentCombine(resultContent=" + this.resultContent + ", resultContentStream=" + this.resultContentStream + ", traceId=" + this.traceId + ")";
    }

    public ResultContentCombine(String str, String str2, String traceId) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        this.resultContent = str;
        this.resultContentStream = str2;
        this.traceId = traceId;
    }

    public final String getResultContent() {
        return this.resultContent;
    }

    public final String getResultContentStream() {
        return this.resultContentStream;
    }

    public final String getTraceId() {
        return this.traceId;
    }
}
