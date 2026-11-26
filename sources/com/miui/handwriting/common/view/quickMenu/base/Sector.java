package com.miui.handwriting.common.view.quickMenu.base;

import android.graphics.RectF;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Sector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\t\u0010#\u001a\u00020$HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\r\"\u0004\b\u0016\u0010\u0014¨\u0006%"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/base/Sector;", "", "startAngle", "", "endAngle", "outerOvalRect", "Landroid/graphics/RectF;", "innerOvalRect", "outerCircleRadius", "innerCircleRadius", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(FFLandroid/graphics/RectF;Landroid/graphics/RectF;FF)V", "getStartAngle", "()F", "getEndAngle", "getOuterOvalRect", "()Landroid/graphics/RectF;", "getInnerOvalRect", "getOuterCircleRadius", "setOuterCircleRadius", "(F)V", "getInnerCircleRadius", "setInnerCircleRadius", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class Sector {
    private final float endAngle;
    private float innerCircleRadius;
    private final RectF innerOvalRect;
    private float outerCircleRadius;
    private final RectF outerOvalRect;
    private final float startAngle;

    public static /* synthetic */ Sector copy$default(Sector sector, float f, float f2, RectF rectF, RectF rectF2, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = sector.startAngle;
        }
        if ((i & 2) != 0) {
            f2 = sector.endAngle;
        }
        if ((i & 4) != 0) {
            rectF = sector.outerOvalRect;
        }
        if ((i & 8) != 0) {
            rectF2 = sector.innerOvalRect;
        }
        if ((i & 16) != 0) {
            f3 = sector.outerCircleRadius;
        }
        if ((i & 32) != 0) {
            f4 = sector.innerCircleRadius;
        }
        float f5 = f3;
        float f6 = f4;
        return sector.copy(f, f2, rectF, rectF2, f5, f6);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final float getStartAngle() {
        return this.startAngle;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final float getEndAngle() {
        return this.endAngle;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final RectF getOuterOvalRect() {
        return this.outerOvalRect;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final RectF getInnerOvalRect() {
        return this.innerOvalRect;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final float getOuterCircleRadius() {
        return this.outerCircleRadius;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final float getInnerCircleRadius() {
        return this.innerCircleRadius;
    }

    public final Sector copy(float startAngle, float endAngle, RectF outerOvalRect, RectF innerOvalRect, float outerCircleRadius, float innerCircleRadius) {
        Intrinsics.checkNotNullParameter(outerOvalRect, "outerOvalRect");
        Intrinsics.checkNotNullParameter(innerOvalRect, "innerOvalRect");
        return new Sector(startAngle, endAngle, outerOvalRect, innerOvalRect, outerCircleRadius, innerCircleRadius);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Sector)) {
            return false;
        }
        Sector sector = (Sector) other;
        return Float.compare(this.startAngle, sector.startAngle) == 0 && Float.compare(this.endAngle, sector.endAngle) == 0 && Intrinsics.areEqual(this.outerOvalRect, sector.outerOvalRect) && Intrinsics.areEqual(this.innerOvalRect, sector.innerOvalRect) && Float.compare(this.outerCircleRadius, sector.outerCircleRadius) == 0 && Float.compare(this.innerCircleRadius, sector.innerCircleRadius) == 0;
    }

    public int hashCode() {
        return (((((((((Float.hashCode(this.startAngle) * 31) + Float.hashCode(this.endAngle)) * 31) + this.outerOvalRect.hashCode()) * 31) + this.innerOvalRect.hashCode()) * 31) + Float.hashCode(this.outerCircleRadius)) * 31) + Float.hashCode(this.innerCircleRadius);
    }

    public String toString() {
        return "Sector(startAngle=" + this.startAngle + ", endAngle=" + this.endAngle + ", outerOvalRect=" + this.outerOvalRect + ", innerOvalRect=" + this.innerOvalRect + ", outerCircleRadius=" + this.outerCircleRadius + ", innerCircleRadius=" + this.innerCircleRadius + CoreConstants.RIGHT_PARENTHESIS_CHAR;
    }

    public Sector(float f, float f2, RectF outerOvalRect, RectF innerOvalRect, float f3, float f4) {
        Intrinsics.checkNotNullParameter(outerOvalRect, "outerOvalRect");
        Intrinsics.checkNotNullParameter(innerOvalRect, "innerOvalRect");
        this.startAngle = f;
        this.endAngle = f2;
        this.outerOvalRect = outerOvalRect;
        this.innerOvalRect = innerOvalRect;
        this.outerCircleRadius = f3;
        this.innerCircleRadius = f4;
    }

    public final float getStartAngle() {
        return this.startAngle;
    }

    public final float getEndAngle() {
        return this.endAngle;
    }

    public final RectF getOuterOvalRect() {
        return this.outerOvalRect;
    }

    public final RectF getInnerOvalRect() {
        return this.innerOvalRect;
    }

    public final float getOuterCircleRadius() {
        return this.outerCircleRadius;
    }

    public final void setOuterCircleRadius(float f) {
        this.outerCircleRadius = f;
    }

    public final float getInnerCircleRadius() {
        return this.innerCircleRadius;
    }

    public final void setInnerCircleRadius(float f) {
        this.innerCircleRadius = f;
    }
}
