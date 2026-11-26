package com.miui.handwriting.common.ai.data.requestparam;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParamImageInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0013"}, d2 = {"Lcom/miui/handwriting/common/ai/data/requestparam/ParamImageInfo;", "", "fileHash", "", "fileSize", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "getFileHash", "()Ljava/lang/String;", "getFileSize", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ParamImageInfo {
    private final String fileHash;
    private final String fileSize;

    public static /* synthetic */ ParamImageInfo copy$default(ParamImageInfo paramImageInfo, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = paramImageInfo.fileHash;
        }
        if ((i & 2) != 0) {
            str2 = paramImageInfo.fileSize;
        }
        return paramImageInfo.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getFileHash() {
        return this.fileHash;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getFileSize() {
        return this.fileSize;
    }

    public final ParamImageInfo copy(String fileHash, String fileSize) {
        Intrinsics.checkNotNullParameter(fileHash, "fileHash");
        Intrinsics.checkNotNullParameter(fileSize, "fileSize");
        return new ParamImageInfo(fileHash, fileSize);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParamImageInfo)) {
            return false;
        }
        ParamImageInfo paramImageInfo = (ParamImageInfo) other;
        return Intrinsics.areEqual(this.fileHash, paramImageInfo.fileHash) && Intrinsics.areEqual(this.fileSize, paramImageInfo.fileSize);
    }

    public int hashCode() {
        return (this.fileHash.hashCode() * 31) + this.fileSize.hashCode();
    }

    public String toString() {
        return "ParamImageInfo(fileHash=" + this.fileHash + ", fileSize=" + this.fileSize + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public ParamImageInfo(String fileHash, String fileSize) {
        Intrinsics.checkNotNullParameter(fileHash, "fileHash");
        Intrinsics.checkNotNullParameter(fileSize, "fileSize");
        this.fileHash = fileHash;
        this.fileSize = fileSize;
    }

    public final String getFileHash() {
        return this.fileHash;
    }

    public final String getFileSize() {
        return this.fileSize;
    }
}
