package com.miui.handwriting.common.ai.data;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UploadMetaData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b¨\u0006\u001b"}, d2 = {"Lcom/miui/handwriting/common/ai/data/UploadMetaData;", "", "algorithm", "", "key", "options", "Lcom/miui/handwriting/common/ai/data/Options;", "requestId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;Lcom/miui/handwriting/common/ai/data/Options;Ljava/lang/String;)V", "getAlgorithm", "()Ljava/lang/String;", "getKey", "getOptions", "()Lcom/miui/handwriting/common/ai/data/Options;", "getRequestId", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class UploadMetaData {
    private final String algorithm;
    private final String key;
    private final Options options;
    private final String requestId;

    public static /* synthetic */ UploadMetaData copy$default(UploadMetaData uploadMetaData, String str, String str2, Options options, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = uploadMetaData.algorithm;
        }
        if ((i & 2) != 0) {
            str2 = uploadMetaData.key;
        }
        if ((i & 4) != 0) {
            options = uploadMetaData.options;
        }
        if ((i & 8) != 0) {
            str3 = uploadMetaData.requestId;
        }
        return uploadMetaData.copy(str, str2, options, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getKey() {
        return this.key;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Options getOptions() {
        return this.options;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getRequestId() {
        return this.requestId;
    }

    public final UploadMetaData copy(String algorithm, String key, Options options, String requestId) {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(requestId, "requestId");
        return new UploadMetaData(algorithm, key, options, requestId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UploadMetaData)) {
            return false;
        }
        UploadMetaData uploadMetaData = (UploadMetaData) other;
        return Intrinsics.areEqual(this.algorithm, uploadMetaData.algorithm) && Intrinsics.areEqual(this.key, uploadMetaData.key) && Intrinsics.areEqual(this.options, uploadMetaData.options) && Intrinsics.areEqual(this.requestId, uploadMetaData.requestId);
    }

    public int hashCode() {
        return (((((this.algorithm.hashCode() * 31) + this.key.hashCode()) * 31) + this.options.hashCode()) * 31) + this.requestId.hashCode();
    }

    public String toString() {
        return "UploadMetaData(algorithm=" + this.algorithm + ", key=" + this.key + ", options=" + this.options + ", requestId=" + this.requestId + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public UploadMetaData(String algorithm, String key, Options options, String requestId) {
        Intrinsics.checkNotNullParameter(algorithm, "algorithm");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(requestId, "requestId");
        this.algorithm = algorithm;
        this.key = key;
        this.options = options;
        this.requestId = requestId;
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final String getKey() {
        return this.key;
    }

    public final Options getOptions() {
        return this.options;
    }

    public final String getRequestId() {
        return this.requestId;
    }
}
