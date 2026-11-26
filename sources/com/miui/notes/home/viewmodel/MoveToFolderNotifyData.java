package com.miui.notes.home.viewmodel;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MoveToFolderNotifyData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0016\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lcom/miui/notes/home/viewmodel/MoveToFolderNotifyData;", "", "folderId", "", "folderName", "", "ids", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(JLjava/lang/String;[J)V", "getFolderId", "()J", "getFolderName", "()Ljava/lang/String;", "getIds", "()[J", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class MoveToFolderNotifyData {
    private final long folderId;
    private final String folderName;
    private final long[] ids;

    public static /* synthetic */ MoveToFolderNotifyData copy$default(MoveToFolderNotifyData moveToFolderNotifyData, long j, String str, long[] jArr, int i, Object obj) {
        if ((i & 1) != 0) {
            j = moveToFolderNotifyData.folderId;
        }
        if ((i & 2) != 0) {
            str = moveToFolderNotifyData.folderName;
        }
        if ((i & 4) != 0) {
            jArr = moveToFolderNotifyData.ids;
        }
        return moveToFolderNotifyData.copy(j, str, jArr);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getFolderId() {
        return this.folderId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getFolderName() {
        return this.folderName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final long[] getIds() {
        return this.ids;
    }

    public final MoveToFolderNotifyData copy(long folderId, String folderName, long[] ids) {
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        Intrinsics.checkNotNullParameter(ids, "ids");
        return new MoveToFolderNotifyData(folderId, folderName, ids);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MoveToFolderNotifyData)) {
            return false;
        }
        MoveToFolderNotifyData moveToFolderNotifyData = (MoveToFolderNotifyData) other;
        return this.folderId == moveToFolderNotifyData.folderId && Intrinsics.areEqual(this.folderName, moveToFolderNotifyData.folderName) && Intrinsics.areEqual(this.ids, moveToFolderNotifyData.ids);
    }

    public int hashCode() {
        return (((Long.hashCode(this.folderId) * 31) + this.folderName.hashCode()) * 31) + Arrays.hashCode(this.ids);
    }

    public String toString() {
        return "MoveToFolderNotifyData(folderId=" + this.folderId + ", folderName=" + this.folderName + ", ids=" + Arrays.toString(this.ids) + ")";
    }

    public MoveToFolderNotifyData(long j, String folderName, long[] ids) {
        Intrinsics.checkNotNullParameter(folderName, "folderName");
        Intrinsics.checkNotNullParameter(ids, "ids");
        this.folderId = j;
        this.folderName = folderName;
        this.ids = ids;
    }

    public final long getFolderId() {
        return this.folderId;
    }

    public final String getFolderName() {
        return this.folderName;
    }

    public final long[] getIds() {
        return this.ids;
    }
}
