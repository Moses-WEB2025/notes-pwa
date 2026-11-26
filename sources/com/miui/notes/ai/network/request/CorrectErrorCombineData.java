package com.miui.notes.ai.network.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CorrectErrorCombineData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0004\b\b\u0010\tJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J5\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/ai/network/request/CorrectErrorCombineData;", "", "resultContent", "", "resultContentStream", "correctErrorsNum", "", "traceId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getResultContent", "()Ljava/lang/String;", "getResultContentStream", "getCorrectErrorsNum", "()I", "getTraceId", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class CorrectErrorCombineData {
    private final int correctErrorsNum;
    private final String resultContent;
    private final String resultContentStream;
    private final String traceId;

    public static /* synthetic */ CorrectErrorCombineData copy$default(CorrectErrorCombineData correctErrorCombineData, String str, String str2, int i, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = correctErrorCombineData.resultContent;
        }
        if ((i2 & 2) != 0) {
            str2 = correctErrorCombineData.resultContentStream;
        }
        if ((i2 & 4) != 0) {
            i = correctErrorCombineData.correctErrorsNum;
        }
        if ((i2 & 8) != 0) {
            str3 = correctErrorCombineData.traceId;
        }
        return correctErrorCombineData.copy(str, str2, i, str3);
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
    public final int getCorrectErrorsNum() {
        return this.correctErrorsNum;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getTraceId() {
        return this.traceId;
    }

    public final CorrectErrorCombineData copy(String resultContent, String resultContentStream, int correctErrorsNum, String traceId) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        return new CorrectErrorCombineData(resultContent, resultContentStream, correctErrorsNum, traceId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CorrectErrorCombineData)) {
            return false;
        }
        CorrectErrorCombineData correctErrorCombineData = (CorrectErrorCombineData) other;
        return Intrinsics.areEqual(this.resultContent, correctErrorCombineData.resultContent) && Intrinsics.areEqual(this.resultContentStream, correctErrorCombineData.resultContentStream) && this.correctErrorsNum == correctErrorCombineData.correctErrorsNum && Intrinsics.areEqual(this.traceId, correctErrorCombineData.traceId);
    }

    public int hashCode() {
        String str = this.resultContent;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.resultContentStream;
        return ((((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + Integer.hashCode(this.correctErrorsNum)) * 31) + this.traceId.hashCode();
    }

    public String toString() {
        return "CorrectErrorCombineData(resultContent=" + this.resultContent + ", resultContentStream=" + this.resultContentStream + ", correctErrorsNum=" + this.correctErrorsNum + ", traceId=" + this.traceId + ")";
    }

    public CorrectErrorCombineData(String str, String str2, int i, String traceId) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        this.resultContent = str;
        this.resultContentStream = str2;
        this.correctErrorsNum = i;
        this.traceId = traceId;
    }

    public final int getCorrectErrorsNum() {
        return this.correctErrorsNum;
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
