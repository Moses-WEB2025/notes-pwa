package com.agog.mathdisplay.render;

import ch.qos.logback.core.CoreConstants;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/agog/mathdisplay/render/CGPoint;", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "(FF)V", "getX", "()F", "setX", "(F)V", "getY", "setY", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class CGPoint {
    private float x;
    private float y;

    /* JADX WARN: Illegal instructions before constructor call */
    public CGPoint() {
        float f = 0.0f;
        this(f, f, 3, null);
    }

    public static /* synthetic */ CGPoint copy$default(CGPoint cGPoint, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = cGPoint.x;
        }
        if ((i & 2) != 0) {
            f2 = cGPoint.y;
        }
        return cGPoint.copy(f, f2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getX() {
        return this.x;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getY() {
        return this.y;
    }

    public final CGPoint copy(float x, float y) {
        return new CGPoint(x, y);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CGPoint)) {
            return false;
        }
        CGPoint cGPoint = (CGPoint) other;
        return Intrinsics.areEqual((Object) Float.valueOf(this.x), (Object) Float.valueOf(cGPoint.x)) && Intrinsics.areEqual((Object) Float.valueOf(this.y), (Object) Float.valueOf(cGPoint.y));
    }

    public int hashCode() {
        return (Float.hashCode(this.x) * 31) + Float.hashCode(this.y);
    }

    public String toString() {
        return "CGPoint(x=" + this.x + ", y=" + this.y + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public CGPoint(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public /* synthetic */ CGPoint(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final void setY(float f) {
        this.y = f;
    }
}
