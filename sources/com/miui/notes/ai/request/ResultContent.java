package com.miui.notes.ai.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResultContent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/ai/request/ResultContent;", "", "resultContent", "", "resultContentStream", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "getResultContent", "()Ljava/lang/String;", "getResultContentStream", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ResultContent {
    private final String resultContent;
    private final String resultContentStream;

    public static /* synthetic */ ResultContent copy$default(ResultContent resultContent, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = resultContent.resultContent;
        }
        if ((i & 2) != 0) {
            str2 = resultContent.resultContentStream;
        }
        return resultContent.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getResultContent() {
        return this.resultContent;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getResultContentStream() {
        return this.resultContentStream;
    }

    public final ResultContent copy(String resultContent, String resultContentStream) {
        return new ResultContent(resultContent, resultContentStream);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ResultContent)) {
            return false;
        }
        ResultContent resultContent = (ResultContent) other;
        return Intrinsics.areEqual(this.resultContent, resultContent.resultContent) && Intrinsics.areEqual(this.resultContentStream, resultContent.resultContentStream);
    }

    public int hashCode() {
        String str = this.resultContent;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.resultContentStream;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "ResultContent(resultContent=" + this.resultContent + ", resultContentStream=" + this.resultContentStream + ")";
    }

    public ResultContent(String str, String str2) {
        this.resultContent = str;
        this.resultContentStream = str2;
    }

    public final String getResultContent() {
        return this.resultContent;
    }

    public final String getResultContentStream() {
        return this.resultContentStream;
    }
}
