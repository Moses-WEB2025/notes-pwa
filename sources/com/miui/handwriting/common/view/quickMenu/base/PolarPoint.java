package com.miui.handwriting.common.view.quickMenu.base;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;

/* JADX INFO: compiled from: PolarPoint.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/PolarPoint;", "", "radius", "", "angle", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FF)V", "getRadius", "()F", "setRadius", "(F)V", "getAngle", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class PolarPoint {
    private final float angle;
    private float radius;

    public static /* synthetic */ PolarPoint copy$default(PolarPoint polarPoint, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = polarPoint.radius;
        }
        if ((i & 2) != 0) {
            f2 = polarPoint.angle;
        }
        return polarPoint.copy(f, f2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getRadius() {
        return this.radius;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getAngle() {
        return this.angle;
    }

    public final PolarPoint copy(float radius, float angle) {
        return new PolarPoint(radius, angle);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PolarPoint)) {
            return false;
        }
        PolarPoint polarPoint = (PolarPoint) other;
        return Float.compare(this.radius, polarPoint.radius) == 0 && Float.compare(this.angle, polarPoint.angle) == 0;
    }

    public int hashCode() {
        return (Float.hashCode(this.radius) * 31) + Float.hashCode(this.angle);
    }

    public String toString() {
        return "PolarPoint(radius=" + this.radius + ", angle=" + this.angle + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public PolarPoint(float f, float f2) {
        this.radius = f;
        this.angle = f2;
    }

    public final float getAngle() {
        return this.angle;
    }

    public final float getRadius() {
        return this.radius;
    }

    public final void setRadius(float f) {
        this.radius = f;
    }
}
