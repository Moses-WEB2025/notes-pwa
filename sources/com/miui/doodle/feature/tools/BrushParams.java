package com.miui.doodle.feature.tools;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BrushParams.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010¨\u0006\u001d"}, d2 = {"Lcom/miui/doodle/feature/tools/BrushParams;", "", "alpha", "", "colorInt", "", "strokeLevel", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FII)V", "getAlpha", "()F", "setAlpha", "(F)V", "getColorInt", "()I", "setColorInt", "(I)V", "getStrokeLevel", "setStrokeLevel", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "toString", "", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class BrushParams {
    private float alpha;
    private int colorInt;
    private int strokeLevel;

    public BrushParams() {
        this(0.0f, 0, 0, 7, null);
    }

    public static /* synthetic */ BrushParams copy$default(BrushParams brushParams, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            f = brushParams.alpha;
        }
        if ((i3 & 2) != 0) {
            i = brushParams.colorInt;
        }
        if ((i3 & 4) != 0) {
            i2 = brushParams.strokeLevel;
        }
        return brushParams.copy(f, i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getAlpha() {
        return this.alpha;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getColorInt() {
        return this.colorInt;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getStrokeLevel() {
        return this.strokeLevel;
    }

    public final BrushParams copy(float alpha, int colorInt, int strokeLevel) {
        return new BrushParams(alpha, colorInt, strokeLevel);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrushParams)) {
            return false;
        }
        BrushParams brushParams = (BrushParams) other;
        return Float.compare(this.alpha, brushParams.alpha) == 0 && this.colorInt == brushParams.colorInt && this.strokeLevel == brushParams.strokeLevel;
    }

    public int hashCode() {
        return (((Float.hashCode(this.alpha) * 31) + Integer.hashCode(this.colorInt)) * 31) + Integer.hashCode(this.strokeLevel);
    }

    public String toString() {
        return "BrushParams(alpha=" + this.alpha + ", colorInt=" + this.colorInt + ", strokeLevel=" + this.strokeLevel + ")";
    }

    public BrushParams(float f, int i, int i2) {
        this.alpha = f;
        this.colorInt = i;
        this.strokeLevel = i2;
    }

    public /* synthetic */ BrushParams(float f, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 1.0f : f, (i3 & 2) != 0 ? -16777216 : i, (i3 & 4) != 0 ? 1 : i2);
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final void setAlpha(float f) {
        this.alpha = f;
    }

    public final int getColorInt() {
        return this.colorInt;
    }

    public final void setColorInt(int i) {
        this.colorInt = i;
    }

    public final int getStrokeLevel() {
        return this.strokeLevel;
    }

    public final void setStrokeLevel(int i) {
        this.strokeLevel = i;
    }
}
