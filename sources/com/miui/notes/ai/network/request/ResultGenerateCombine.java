package com.miui.notes.ai.network.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResultGenerateCombine.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\t\u0010\nJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003JO\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u001f"}, d2 = {"Lcom/miui/notes/ai/network/request/ResultGenerateCombine;", "", "resultContent", "", "resultContentStream", "theme", "resultContentMode", "traceId", "reasoning", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getResultContent", "()Ljava/lang/String;", "getResultContentStream", "getTheme", "getResultContentMode", "getTraceId", "getReasoning", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ResultGenerateCombine {
    private final String reasoning;
    private final String resultContent;
    private final String resultContentMode;
    private final String resultContentStream;
    private final String theme;
    private final String traceId;

    public static /* synthetic */ ResultGenerateCombine copy$default(ResultGenerateCombine resultGenerateCombine, String str, String str2, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = resultGenerateCombine.resultContent;
        }
        if ((i & 2) != 0) {
            str2 = resultGenerateCombine.resultContentStream;
        }
        if ((i & 4) != 0) {
            str3 = resultGenerateCombine.theme;
        }
        if ((i & 8) != 0) {
            str4 = resultGenerateCombine.resultContentMode;
        }
        if ((i & 16) != 0) {
            str5 = resultGenerateCombine.traceId;
        }
        if ((i & 32) != 0) {
            str6 = resultGenerateCombine.reasoning;
        }
        String str7 = str5;
        String str8 = str6;
        return resultGenerateCombine.copy(str, str2, str3, str4, str7, str8);
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
    public final String getTheme() {
        return this.theme;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getResultContentMode() {
        return this.resultContentMode;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getTraceId() {
        return this.traceId;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getReasoning() {
        return this.reasoning;
    }

    public final ResultGenerateCombine copy(String resultContent, String resultContentStream, String theme, String resultContentMode, String traceId, String reasoning) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        return new ResultGenerateCombine(resultContent, resultContentStream, theme, resultContentMode, traceId, reasoning);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResultGenerateCombine)) {
            return false;
        }
        ResultGenerateCombine resultGenerateCombine = (ResultGenerateCombine) other;
        return Intrinsics.areEqual(this.resultContent, resultGenerateCombine.resultContent) && Intrinsics.areEqual(this.resultContentStream, resultGenerateCombine.resultContentStream) && Intrinsics.areEqual(this.theme, resultGenerateCombine.theme) && Intrinsics.areEqual(this.resultContentMode, resultGenerateCombine.resultContentMode) && Intrinsics.areEqual(this.traceId, resultGenerateCombine.traceId) && Intrinsics.areEqual(this.reasoning, resultGenerateCombine.reasoning);
    }

    public int hashCode() {
        String str = this.resultContent;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.resultContentStream;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.theme;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.resultContentMode;
        int iHashCode4 = (((iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.traceId.hashCode()) * 31;
        String str5 = this.reasoning;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }

    public String toString() {
        return "ResultGenerateCombine(resultContent=" + this.resultContent + ", resultContentStream=" + this.resultContentStream + ", theme=" + this.theme + ", resultContentMode=" + this.resultContentMode + ", traceId=" + this.traceId + ", reasoning=" + this.reasoning + ")";
    }

    public ResultGenerateCombine(String str, String str2, String str3, String str4, String traceId, String str5) {
        Intrinsics.checkNotNullParameter(traceId, "traceId");
        this.resultContent = str;
        this.resultContentStream = str2;
        this.theme = str3;
        this.resultContentMode = str4;
        this.traceId = traceId;
        this.reasoning = str5;
    }

    public final String getReasoning() {
        return this.reasoning;
    }

    public final String getResultContent() {
        return this.resultContent;
    }

    public final String getResultContentMode() {
        return this.resultContentMode;
    }

    public final String getResultContentStream() {
        return this.resultContentStream;
    }

    public final String getTheme() {
        return this.theme;
    }

    public final String getTraceId() {
        return this.traceId;
    }
}
