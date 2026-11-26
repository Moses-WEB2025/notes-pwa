package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.feature.draw.DoodleBrushPath;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemovedBrushPathInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/miui/doodle/feature/action/RemovedBrushPathInfo;", "", "brushPath", "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "indexInLayerBitmap", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/feature/draw/DoodleBrushPath;I)V", "getBrushPath", "()Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "getIndexInLayerBitmap", "()I", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class RemovedBrushPathInfo {
    private final DoodleBrushPath brushPath;
    private final int indexInLayerBitmap;

    public static /* synthetic */ RemovedBrushPathInfo copy$default(RemovedBrushPathInfo removedBrushPathInfo, DoodleBrushPath doodleBrushPath, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            doodleBrushPath = removedBrushPathInfo.brushPath;
        }
        if ((i2 & 2) != 0) {
            i = removedBrushPathInfo.indexInLayerBitmap;
        }
        return removedBrushPathInfo.copy(doodleBrushPath, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DoodleBrushPath getBrushPath() {
        return this.brushPath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getIndexInLayerBitmap() {
        return this.indexInLayerBitmap;
    }

    public final RemovedBrushPathInfo copy(DoodleBrushPath brushPath, int indexInLayerBitmap) {
        Intrinsics.checkNotNullParameter(brushPath, "brushPath");
        return new RemovedBrushPathInfo(brushPath, indexInLayerBitmap);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RemovedBrushPathInfo)) {
            return false;
        }
        RemovedBrushPathInfo removedBrushPathInfo = (RemovedBrushPathInfo) other;
        return Intrinsics.areEqual(this.brushPath, removedBrushPathInfo.brushPath) && this.indexInLayerBitmap == removedBrushPathInfo.indexInLayerBitmap;
    }

    public int hashCode() {
        return (this.brushPath.hashCode() * 31) + Integer.hashCode(this.indexInLayerBitmap);
    }

    public String toString() {
        return "RemovedBrushPathInfo(brushPath=" + this.brushPath + ", indexInLayerBitmap=" + this.indexInLayerBitmap + ")";
    }

    public RemovedBrushPathInfo(DoodleBrushPath brushPath, int i) {
        Intrinsics.checkNotNullParameter(brushPath, "brushPath");
        this.brushPath = brushPath;
        this.indexInLayerBitmap = i;
    }

    public final DoodleBrushPath getBrushPath() {
        return this.brushPath;
    }

    public final int getIndexInLayerBitmap() {
        return this.indexInLayerBitmap;
    }
}
