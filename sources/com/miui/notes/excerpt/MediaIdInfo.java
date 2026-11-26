package com.miui.notes.excerpt;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaIdInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0006HÆ\u0003J)\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0006HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lcom/miui/notes/excerpt/MediaIdInfo;", "", "mediaId", "", "changeTime", "filePath", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(JJLjava/lang/String;)V", "getMediaId", "()J", "setMediaId", "(J)V", "getChangeTime", "setChangeTime", "getFilePath", "()Ljava/lang/String;", "setFilePath", "(Ljava/lang/String;)V", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class MediaIdInfo {
    private long changeTime;
    private String filePath;
    private long mediaId;

    public static /* synthetic */ MediaIdInfo copy$default(MediaIdInfo mediaIdInfo, long j, long j2, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            j = mediaIdInfo.mediaId;
        }
        long j3 = j;
        if ((i & 2) != 0) {
            j2 = mediaIdInfo.changeTime;
        }
        long j4 = j2;
        if ((i & 4) != 0) {
            str = mediaIdInfo.filePath;
        }
        return mediaIdInfo.copy(j3, j4, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getMediaId() {
        return this.mediaId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getChangeTime() {
        return this.changeTime;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getFilePath() {
        return this.filePath;
    }

    public final MediaIdInfo copy(long mediaId, long changeTime, String filePath) {
        return new MediaIdInfo(mediaId, changeTime, filePath);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MediaIdInfo)) {
            return false;
        }
        MediaIdInfo mediaIdInfo = (MediaIdInfo) other;
        return this.mediaId == mediaIdInfo.mediaId && this.changeTime == mediaIdInfo.changeTime && Intrinsics.areEqual(this.filePath, mediaIdInfo.filePath);
    }

    public int hashCode() {
        int iHashCode = ((Long.hashCode(this.mediaId) * 31) + Long.hashCode(this.changeTime)) * 31;
        String str = this.filePath;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "MediaIdInfo(mediaId=" + this.mediaId + ", changeTime=" + this.changeTime + ", filePath=" + this.filePath + ")";
    }

    public MediaIdInfo(long j, long j2, String str) {
        this.mediaId = j;
        this.changeTime = j2;
        this.filePath = str;
    }

    public final long getChangeTime() {
        return this.changeTime;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final long getMediaId() {
        return this.mediaId;
    }

    public final void setChangeTime(long j) {
        this.changeTime = j;
    }

    public final void setFilePath(String str) {
        this.filePath = str;
    }

    public final void setMediaId(long j) {
        this.mediaId = j;
    }
}
