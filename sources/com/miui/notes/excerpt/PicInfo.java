package com.miui.notes.excerpt;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PicInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\u000f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/excerpt/PicInfo;", "", "filePath", "", "mediaId", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/lang/String;)V", "getFilePath", "()Ljava/lang/String;", "setFilePath", "(Ljava/lang/String;)V", "getMediaId", "setMediaId", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class PicInfo {
    private String filePath;
    private String mediaId;

    public static /* synthetic */ PicInfo copy$default(PicInfo picInfo, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = picInfo.filePath;
        }
        if ((i & 2) != 0) {
            str2 = picInfo.mediaId;
        }
        return picInfo.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getFilePath() {
        return this.filePath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMediaId() {
        return this.mediaId;
    }

    public final PicInfo copy(String filePath, String mediaId) {
        return new PicInfo(filePath, mediaId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PicInfo)) {
            return false;
        }
        PicInfo picInfo = (PicInfo) other;
        return Intrinsics.areEqual(this.filePath, picInfo.filePath) && Intrinsics.areEqual(this.mediaId, picInfo.mediaId);
    }

    public int hashCode() {
        String str = this.filePath;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.mediaId;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "PicInfo(filePath=" + this.filePath + ", mediaId=" + this.mediaId + ")";
    }

    public PicInfo(String str, String str2) {
        this.filePath = str;
        this.mediaId = str2;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final String getMediaId() {
        return this.mediaId;
    }

    public final void setFilePath(String str) {
        this.filePath = str;
    }

    public final void setMediaId(String str) {
        this.mediaId = str;
    }
}
