package com.miui.notes.ai.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CorrectErrorData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/request/CorrectErrorData;", "", "resultContent", "", "correctErrorsNum", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;I)V", "getResultContent", "()Ljava/lang/String;", "getCorrectErrorsNum", "()I", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class CorrectErrorData {
    private final int correctErrorsNum;
    private final String resultContent;

    public static /* synthetic */ CorrectErrorData copy$default(CorrectErrorData correctErrorData, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = correctErrorData.resultContent;
        }
        if ((i2 & 2) != 0) {
            i = correctErrorData.correctErrorsNum;
        }
        return correctErrorData.copy(str, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getResultContent() {
        return this.resultContent;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getCorrectErrorsNum() {
        return this.correctErrorsNum;
    }

    public final CorrectErrorData copy(String resultContent, int correctErrorsNum) {
        Intrinsics.checkNotNullParameter(resultContent, "resultContent");
        return new CorrectErrorData(resultContent, correctErrorsNum);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CorrectErrorData)) {
            return false;
        }
        CorrectErrorData correctErrorData = (CorrectErrorData) other;
        return Intrinsics.areEqual(this.resultContent, correctErrorData.resultContent) && this.correctErrorsNum == correctErrorData.correctErrorsNum;
    }

    public int hashCode() {
        return (this.resultContent.hashCode() * 31) + Integer.hashCode(this.correctErrorsNum);
    }

    public String toString() {
        return "CorrectErrorData(resultContent=" + this.resultContent + ", correctErrorsNum=" + this.correctErrorsNum + ")";
    }

    public CorrectErrorData(String resultContent, int i) {
        Intrinsics.checkNotNullParameter(resultContent, "resultContent");
        this.resultContent = resultContent;
        this.correctErrorsNum = i;
    }

    public final int getCorrectErrorsNum() {
        return this.correctErrorsNum;
    }

    public final String getResultContent() {
        return this.resultContent;
    }
}
