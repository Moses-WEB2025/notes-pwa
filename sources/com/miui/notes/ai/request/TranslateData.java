package com.miui.notes.ai.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TranslateData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/ai/request/TranslateData;", "", AINotesViewModel.PARAM_SRC_LANG_CODE, "", AINotesViewModel.PARAM_TARGET_LANG_CODE, "resultContent", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getSrcLangCode", "()Ljava/lang/String;", "getTargetLangCode", "getResultContent", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class TranslateData {
    private final String resultContent;
    private final String srcLangCode;
    private final String targetLangCode;

    public static /* synthetic */ TranslateData copy$default(TranslateData translateData, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = translateData.srcLangCode;
        }
        if ((i & 2) != 0) {
            str2 = translateData.targetLangCode;
        }
        if ((i & 4) != 0) {
            str3 = translateData.resultContent;
        }
        return translateData.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getSrcLangCode() {
        return this.srcLangCode;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getTargetLangCode() {
        return this.targetLangCode;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getResultContent() {
        return this.resultContent;
    }

    public final TranslateData copy(String srcLangCode, String targetLangCode, String resultContent) {
        Intrinsics.checkNotNullParameter(srcLangCode, "srcLangCode");
        Intrinsics.checkNotNullParameter(targetLangCode, "targetLangCode");
        Intrinsics.checkNotNullParameter(resultContent, "resultContent");
        return new TranslateData(srcLangCode, targetLangCode, resultContent);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslateData)) {
            return false;
        }
        TranslateData translateData = (TranslateData) other;
        return Intrinsics.areEqual(this.srcLangCode, translateData.srcLangCode) && Intrinsics.areEqual(this.targetLangCode, translateData.targetLangCode) && Intrinsics.areEqual(this.resultContent, translateData.resultContent);
    }

    public int hashCode() {
        return (((this.srcLangCode.hashCode() * 31) + this.targetLangCode.hashCode()) * 31) + this.resultContent.hashCode();
    }

    public String toString() {
        return "TranslateData(srcLangCode=" + this.srcLangCode + ", targetLangCode=" + this.targetLangCode + ", resultContent=" + this.resultContent + ")";
    }

    public TranslateData(String srcLangCode, String targetLangCode, String resultContent) {
        Intrinsics.checkNotNullParameter(srcLangCode, "srcLangCode");
        Intrinsics.checkNotNullParameter(targetLangCode, "targetLangCode");
        Intrinsics.checkNotNullParameter(resultContent, "resultContent");
        this.srcLangCode = srcLangCode;
        this.targetLangCode = targetLangCode;
        this.resultContent = resultContent;
    }

    public final String getResultContent() {
        return this.resultContent;
    }

    public final String getSrcLangCode() {
        return this.srcLangCode;
    }

    public final String getTargetLangCode() {
        return this.targetLangCode;
    }
}
