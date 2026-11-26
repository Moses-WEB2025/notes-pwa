package com.miui.handwriting.common.view.quickMenu.utils;

import android.graphics.PointF;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.view.quickMenu.base.ArcArea;
import com.miui.handwriting.common.view.quickMenu.base.PolarPoint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: PolarUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ.\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bJ.\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rJ\u0016\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005J>\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0005J\u001e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e¨\u0006\u001f"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/PolarUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "convertPolarToPlane", "Landroid/graphics/PointF;", "polarPoint", "Lcom/miui/handwriting/common/view/quickMenu/base/PolarPoint;", "rect", "Landroid/graphics/RectF;", "valueToAngle", "", "value", "", "minValue", "maxValue", "minAngle", "maxAngle", "angleToValue", "angle", "convertPlaneToPolar", "point", "centerPoint", "isTouchInArc", "", "touchX", "touchY", "minRadius", "maxRadius", "arcArea", "Lcom/miui/handwriting/common/view/quickMenu/base/ArcArea;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PolarUtils {
    public static final PolarUtils INSTANCE = new PolarUtils();

    private PolarUtils() {
    }

    public final PointF convertPolarToPlane(PolarPoint polarPoint, RectF rect) {
        Intrinsics.checkNotNullParameter(polarPoint, "polarPoint");
        Intrinsics.checkNotNullParameter(rect, "rect");
        float radius = polarPoint.getRadius();
        double radians = Math.toRadians(360 - polarPoint.getAngle());
        double d = radius;
        return new PointF(rect.centerX() + ((float) (Math.cos(radians) * d)), rect.centerY() - ((float) (d * Math.sin(radians))));
    }

    public final float valueToAngle(int value, int minValue, int maxValue, float minAngle, float maxAngle) {
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(minAngle);
        return fAdjustAngle + (((value - minValue) / (maxValue - minValue)) * (AngleUtils.INSTANCE.adjustAngle(maxAngle) - fAdjustAngle));
    }

    public final int angleToValue(float angle, float minAngle, float maxAngle, int minValue, int maxValue) {
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(minAngle);
        return minValue + MathKt.roundToInt(((angle - fAdjustAngle) / (AngleUtils.INSTANCE.adjustAngle(maxAngle) - fAdjustAngle)) * (maxValue - minValue));
    }

    public final PolarPoint convertPlaneToPolar(PointF point, PointF centerPoint) {
        Intrinsics.checkNotNullParameter(point, "point");
        Intrinsics.checkNotNullParameter(centerPoint, "centerPoint");
        float f = point.x;
        float f2 = point.y;
        float f3 = f - centerPoint.x;
        float f4 = centerPoint.y - f2;
        float fSqrt = (float) Math.sqrt((f3 * f3) + (f4 * f4));
        double degrees = Math.toDegrees((float) Math.atan2(f4, f3));
        if (degrees < 0.0d) {
            degrees += (double) 360;
        }
        return new PolarPoint(fSqrt, (float) (((double) 360) - degrees));
    }

    public final boolean isTouchInArc(float touchX, float touchY, float minRadius, float maxRadius, float minAngle, float maxAngle, PointF centerPoint) {
        Intrinsics.checkNotNullParameter(centerPoint, "centerPoint");
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(minAngle);
        float fAdjustAngle2 = AngleUtils.INSTANCE.adjustAngle(maxAngle);
        PolarPoint polarPointConvertPlaneToPolar = convertPlaneToPolar(new PointF(touchX, touchY), centerPoint);
        float radius = polarPointConvertPlaneToPolar.getRadius();
        float angle = polarPointConvertPlaneToPolar.getAngle();
        return minRadius <= radius && radius <= maxRadius && fAdjustAngle <= angle && angle <= fAdjustAngle2;
    }

    public final boolean isTouchInArc(float touchX, float touchY, ArcArea arcArea) {
        Intrinsics.checkNotNullParameter(arcArea, "arcArea");
        return isTouchInArc(touchX, touchY, arcArea.getInnerRadius(), arcArea.getOuterRadius(), arcArea.getMinAngle(), arcArea.getMaxAngle(), arcArea.getCenterPoint());
    }
}
