package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.document.LayerBitmap;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemovedLayerBitmapInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/miui/doodle/feature/action/RemovedLayerBitmapInfo;", "", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "layerBitmapIndex", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/document/LayerBitmap;I)V", "getLayerBitmap", "()Lcom/miui/doodle/document/LayerBitmap;", "getLayerBitmapIndex", "()I", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class RemovedLayerBitmapInfo {
    private final LayerBitmap layerBitmap;
    private final int layerBitmapIndex;

    public static /* synthetic */ RemovedLayerBitmapInfo copy$default(RemovedLayerBitmapInfo removedLayerBitmapInfo, LayerBitmap layerBitmap, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            layerBitmap = removedLayerBitmapInfo.layerBitmap;
        }
        if ((i2 & 2) != 0) {
            i = removedLayerBitmapInfo.layerBitmapIndex;
        }
        return removedLayerBitmapInfo.copy(layerBitmap, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final LayerBitmap getLayerBitmap() {
        return this.layerBitmap;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getLayerBitmapIndex() {
        return this.layerBitmapIndex;
    }

    public final RemovedLayerBitmapInfo copy(LayerBitmap layerBitmap, int layerBitmapIndex) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        return new RemovedLayerBitmapInfo(layerBitmap, layerBitmapIndex);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RemovedLayerBitmapInfo)) {
            return false;
        }
        RemovedLayerBitmapInfo removedLayerBitmapInfo = (RemovedLayerBitmapInfo) other;
        return Intrinsics.areEqual(this.layerBitmap, removedLayerBitmapInfo.layerBitmap) && this.layerBitmapIndex == removedLayerBitmapInfo.layerBitmapIndex;
    }

    public int hashCode() {
        return (this.layerBitmap.hashCode() * 31) + Integer.hashCode(this.layerBitmapIndex);
    }

    public String toString() {
        return "RemovedLayerBitmapInfo(layerBitmap=" + this.layerBitmap + ", layerBitmapIndex=" + this.layerBitmapIndex + ")";
    }

    public RemovedLayerBitmapInfo(LayerBitmap layerBitmap, int i) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        this.layerBitmap = layerBitmap;
        this.layerBitmapIndex = i;
    }

    public final LayerBitmap getLayerBitmap() {
        return this.layerBitmap;
    }

    public final int getLayerBitmapIndex() {
        return this.layerBitmapIndex;
    }
}
