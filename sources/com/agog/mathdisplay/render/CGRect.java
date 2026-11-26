package com.agog.mathdisplay.render;

import ch.qos.logback.core.CoreConstants;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MTMathListDisplay.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001e"}, d2 = {"Lcom/agog/mathdisplay/render/CGRect;", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "(FFFF)V", "getHeight", "()F", "setHeight", "(F)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "mathdisplaylib_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final /* data */ class CGRect {
    private float height;
    private float width;
    private float x;
    private float y;

    public CGRect() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public static /* synthetic */ CGRect copy$default(CGRect cGRect, float f, float f2, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = cGRect.x;
        }
        if ((i & 2) != 0) {
            f2 = cGRect.y;
        }
        if ((i & 4) != 0) {
            f3 = cGRect.width;
        }
        if ((i & 8) != 0) {
            f4 = cGRect.height;
        }
        return cGRect.copy(f, f2, f3, f4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getX() {
        return this.x;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getY() {
        return this.y;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final float getWidth() {
        return this.width;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final float getHeight() {
        return this.height;
    }

    public final CGRect copy(float x, float y, float width, float height) {
        return new CGRect(x, y, width, height);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CGRect)) {
            return false;
        }
        CGRect cGRect = (CGRect) other;
        return Intrinsics.areEqual((Object) Float.valueOf(this.x), (Object) Float.valueOf(cGRect.x)) && Intrinsics.areEqual((Object) Float.valueOf(this.y), (Object) Float.valueOf(cGRect.y)) && Intrinsics.areEqual((Object) Float.valueOf(this.width), (Object) Float.valueOf(cGRect.width)) && Intrinsics.areEqual((Object) Float.valueOf(this.height), (Object) Float.valueOf(cGRect.height));
    }

    public int hashCode() {
        return (((((Float.hashCode(this.x) * 31) + Float.hashCode(this.y)) * 31) + Float.hashCode(this.width)) * 31) + Float.hashCode(this.height);
    }

    public String toString() {
        return "CGRect(x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public CGRect(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public /* synthetic */ CGRect(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? 0.0f : f4);
    }

    public final float getHeight() {
        return this.height;
    }

    public final float getWidth() {
        return this.width;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final void setHeight(float f) {
        this.height = f;
    }

    public final void setWidth(float f) {
        this.width = f;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final void setY(float f) {
        this.y = f;
    }
}
