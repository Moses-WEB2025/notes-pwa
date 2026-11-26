package com.miui.handwriting.common.view.quickMenu.base;

import android.graphics.PointF;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ArcArea.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J;\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001f"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/ArcArea;", "", "innerRadius", "", "outerRadius", "minAngle", "maxAngle", "centerPoint", "Landroid/graphics/PointF;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FFFFLandroid/graphics/PointF;)V", "getInnerRadius", "()F", "getOuterRadius", "getMinAngle", "getMaxAngle", "getCenterPoint", "()Landroid/graphics/PointF;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class ArcArea {
    private final PointF centerPoint;
    private final float innerRadius;
    private final float maxAngle;
    private final float minAngle;
    private final float outerRadius;

    public static /* synthetic */ ArcArea copy$default(ArcArea arcArea, float f, float f2, float f3, float f4, PointF pointF, int i, Object obj) {
        if ((i & 1) != 0) {
            f = arcArea.innerRadius;
        }
        if ((i & 2) != 0) {
            f2 = arcArea.outerRadius;
        }
        if ((i & 4) != 0) {
            f3 = arcArea.minAngle;
        }
        if ((i & 8) != 0) {
            f4 = arcArea.maxAngle;
        }
        if ((i & 16) != 0) {
            pointF = arcArea.centerPoint;
        }
        PointF pointF2 = pointF;
        float f5 = f3;
        return arcArea.copy(f, f2, f5, f4, pointF2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getInnerRadius() {
        return this.innerRadius;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getOuterRadius() {
        return this.outerRadius;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getMinAngle() {
        return this.minAngle;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final float getMaxAngle() {
        return this.maxAngle;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final PointF getCenterPoint() {
        return this.centerPoint;
    }

    public final ArcArea copy(float innerRadius, float outerRadius, float minAngle, float maxAngle, PointF centerPoint) {
        Intrinsics.checkNotNullParameter(centerPoint, "centerPoint");
        return new ArcArea(innerRadius, outerRadius, minAngle, maxAngle, centerPoint);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ArcArea)) {
            return false;
        }
        ArcArea arcArea = (ArcArea) other;
        return Float.compare(this.innerRadius, arcArea.innerRadius) == 0 && Float.compare(this.outerRadius, arcArea.outerRadius) == 0 && Float.compare(this.minAngle, arcArea.minAngle) == 0 && Float.compare(this.maxAngle, arcArea.maxAngle) == 0 && Intrinsics.areEqual(this.centerPoint, arcArea.centerPoint);
    }

    public int hashCode() {
        return (((((((Float.hashCode(this.innerRadius) * 31) + Float.hashCode(this.outerRadius)) * 31) + Float.hashCode(this.minAngle)) * 31) + Float.hashCode(this.maxAngle)) * 31) + this.centerPoint.hashCode();
    }

    public String toString() {
        return "ArcArea(innerRadius=" + this.innerRadius + ", outerRadius=" + this.outerRadius + ", minAngle=" + this.minAngle + ", maxAngle=" + this.maxAngle + ", centerPoint=" + this.centerPoint + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public ArcArea(float f, float f2, float f3, float f4, PointF centerPoint) {
        Intrinsics.checkNotNullParameter(centerPoint, "centerPoint");
        this.innerRadius = f;
        this.outerRadius = f2;
        this.minAngle = f3;
        this.maxAngle = f4;
        this.centerPoint = centerPoint;
    }

    public final PointF getCenterPoint() {
        return this.centerPoint;
    }

    public final float getInnerRadius() {
        return this.innerRadius;
    }

    public final float getMaxAngle() {
        return this.maxAngle;
    }

    public final float getMinAngle() {
        return this.minAngle;
    }

    public final float getOuterRadius() {
        return this.outerRadius;
    }
}
