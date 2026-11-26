package com.miui.notes.excerpt;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExcerptInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0016\u001a\u00020\u0005H\u0016J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÂ\u0003J'\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001f"}, d2 = {"Lcom/miui/notes/excerpt/ExcerptInfo;", "", "excerptType", "", "excerptContent", "", "updateTime", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(ILjava/lang/String;J)V", "getExcerptType", "()I", "setExcerptType", "(I)V", "getExcerptContent", "()Ljava/lang/String;", "setExcerptContent", "(Ljava/lang/String;)V", "uUID", "Ljava/util/UUID;", "getUUID", "()Ljava/util/UUID;", "toString", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ExcerptInfo {
    private String excerptContent;
    private int excerptType;
    private final UUID uUID;
    private long updateTime;

    /* JADX INFO: renamed from: component3, reason: from getter */
    private final long getUpdateTime() {
        return this.updateTime;
    }

    public static /* synthetic */ ExcerptInfo copy$default(ExcerptInfo excerptInfo, int i, String str, long j, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = excerptInfo.excerptType;
        }
        if ((i2 & 2) != 0) {
            str = excerptInfo.excerptContent;
        }
        if ((i2 & 4) != 0) {
            j = excerptInfo.updateTime;
        }
        return excerptInfo.copy(i, str, j);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getExcerptType() {
        return this.excerptType;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getExcerptContent() {
        return this.excerptContent;
    }

    public final ExcerptInfo copy(int excerptType, String excerptContent, long updateTime) {
        Intrinsics.checkNotNullParameter(excerptContent, "excerptContent");
        return new ExcerptInfo(excerptType, excerptContent, updateTime);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExcerptInfo)) {
            return false;
        }
        ExcerptInfo excerptInfo = (ExcerptInfo) other;
        return this.excerptType == excerptInfo.excerptType && Intrinsics.areEqual(this.excerptContent, excerptInfo.excerptContent) && this.updateTime == excerptInfo.updateTime;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.excerptType) * 31) + this.excerptContent.hashCode()) * 31) + Long.hashCode(this.updateTime);
    }

    public ExcerptInfo(int i, String excerptContent, long j) {
        Intrinsics.checkNotNullParameter(excerptContent, "excerptContent");
        this.excerptType = i;
        this.excerptContent = excerptContent;
        this.updateTime = j;
        UUID uuidRandomUUID = UUID.randomUUID();
        Intrinsics.checkNotNullExpressionValue(uuidRandomUUID, "randomUUID(...)");
        this.uUID = uuidRandomUUID;
    }

    public final String getExcerptContent() {
        return this.excerptContent;
    }

    public final int getExcerptType() {
        return this.excerptType;
    }

    public final void setExcerptContent(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.excerptContent = str;
    }

    public final void setExcerptType(int i) {
        this.excerptType = i;
    }

    public final UUID getUUID() {
        return this.uUID;
    }

    public String toString() {
        int i = this.excerptType;
        return "ExcerptInfo:type:" + i + ",updateTime: " + this.updateTime + ",content: " + (i == 1 ? this.excerptContent : "txt-private!!!");
    }
}
