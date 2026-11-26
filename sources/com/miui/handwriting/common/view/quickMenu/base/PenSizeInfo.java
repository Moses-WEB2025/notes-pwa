package com.miui.handwriting.common.view.quickMenu.base;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;

/* JADX INFO: compiled from: PenSizeInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0013"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/PenSizeInfo;", "", "minSize", "", "maxSize", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(II)V", "getMinSize", "()I", "getMaxSize", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class PenSizeInfo {
    private final int maxSize;
    private final int minSize;

    public static /* synthetic */ PenSizeInfo copy$default(PenSizeInfo penSizeInfo, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = penSizeInfo.minSize;
        }
        if ((i3 & 2) != 0) {
            i2 = penSizeInfo.maxSize;
        }
        return penSizeInfo.copy(i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getMinSize() {
        return this.minSize;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getMaxSize() {
        return this.maxSize;
    }

    public final PenSizeInfo copy(int minSize, int maxSize) {
        return new PenSizeInfo(minSize, maxSize);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PenSizeInfo)) {
            return false;
        }
        PenSizeInfo penSizeInfo = (PenSizeInfo) other;
        return this.minSize == penSizeInfo.minSize && this.maxSize == penSizeInfo.maxSize;
    }

    public int hashCode() {
        return (Integer.hashCode(this.minSize) * 31) + Integer.hashCode(this.maxSize);
    }

    public String toString() {
        return "PenSizeInfo(minSize=" + this.minSize + ", maxSize=" + this.maxSize + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public PenSizeInfo(int i, int i2) {
        this.minSize = i;
        this.maxSize = i2;
    }

    public final int getMaxSize() {
        return this.maxSize;
    }

    public final int getMinSize() {
        return this.minSize;
    }
}
