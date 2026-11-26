package com.miui.doodle.base;

import android.graphics.PointF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ItemTransitInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\bHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lcom/miui/doodle/base/ItemTransitInfo;", "", "scale", "", "location", "Landroid/graphics/PointF;", "rotation", "alpha", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FLandroid/graphics/PointF;FI)V", "getScale", "()F", "getLocation", "()Landroid/graphics/PointF;", "getRotation", "getAlpha", "()I", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ItemTransitInfo {
    private final int alpha;
    private final PointF location;
    private final float rotation;
    private final float scale;

    public static /* synthetic */ ItemTransitInfo copy$default(ItemTransitInfo itemTransitInfo, float f, PointF pointF, float f2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f = itemTransitInfo.scale;
        }
        if ((i2 & 2) != 0) {
            pointF = itemTransitInfo.location;
        }
        if ((i2 & 4) != 0) {
            f2 = itemTransitInfo.rotation;
        }
        if ((i2 & 8) != 0) {
            i = itemTransitInfo.alpha;
        }
        return itemTransitInfo.copy(f, pointF, f2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getScale() {
        return this.scale;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final PointF getLocation() {
        return this.location;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getRotation() {
        return this.rotation;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getAlpha() {
        return this.alpha;
    }

    public final ItemTransitInfo copy(float scale, PointF location, float rotation, int alpha) {
        Intrinsics.checkNotNullParameter(location, "location");
        return new ItemTransitInfo(scale, location, rotation, alpha);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ItemTransitInfo)) {
            return false;
        }
        ItemTransitInfo itemTransitInfo = (ItemTransitInfo) other;
        return Float.compare(this.scale, itemTransitInfo.scale) == 0 && Intrinsics.areEqual(this.location, itemTransitInfo.location) && Float.compare(this.rotation, itemTransitInfo.rotation) == 0 && this.alpha == itemTransitInfo.alpha;
    }

    public int hashCode() {
        return (((((Float.hashCode(this.scale) * 31) + this.location.hashCode()) * 31) + Float.hashCode(this.rotation)) * 31) + Integer.hashCode(this.alpha);
    }

    public String toString() {
        return "ItemTransitInfo(scale=" + this.scale + ", location=" + this.location + ", rotation=" + this.rotation + ", alpha=" + this.alpha + ")";
    }

    public ItemTransitInfo(float f, PointF location, float f2, int i) {
        Intrinsics.checkNotNullParameter(location, "location");
        this.scale = f;
        this.location = location;
        this.rotation = f2;
        this.alpha = i;
    }

    public final int getAlpha() {
        return this.alpha;
    }

    public final PointF getLocation() {
        return this.location;
    }

    public final float getRotation() {
        return this.rotation;
    }

    public final float getScale() {
        return this.scale;
    }
}
