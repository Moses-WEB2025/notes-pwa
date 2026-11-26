package com.miui.handwriting.common.view.quickMenu.utils;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.handwriting.common.view.quickMenu.base.PolarPoint;
import com.miui.handwriting.common.view.quickMenu.base.Sector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: QuickUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0004\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JL\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0006*\u00020\u0007\"\n\b\u0001\u0010\u0005\u0018\u0001*\u00020\u00072\u0006\u0010\b\u001a\u0002H\u00062\u0006\u0010\t\u001a\u0002H\u00062\u0006\u0010\n\u001a\u0002H\u00062\u0006\u0010\u000b\u001a\u0002H\u00052\u0006\u0010\f\u001a\u0002H\u0005H\u0086\b¢\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J(\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u0019¨\u0006\u001a"}, d2 = {"Lcom/miui/handwriting/common/view/quickMenu/utils/QuickUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "valueMapping", "R", ExifInterface.GPS_DIRECTION_TRUE, "", "value", "fromMin", "fromMax", "toMin", "toMax", "(Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;", "getSectorPath", "Landroid/graphics/Path;", "sector", "Lcom/miui/handwriting/common/view/quickMenu/base/Sector;", "getSector", "menuRes", "Lcom/miui/handwriting/common/view/quickMenu/utils/PenMenuRes;", "startAngle", "", "endAngle", "startPoint", "Landroid/graphics/PointF;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class QuickUtils {
    public static final QuickUtils INSTANCE = new QuickUtils();

    private QuickUtils() {
    }

    public final /* synthetic */ <T extends Number, R extends Number> R valueMapping(T value, T fromMin, T fromMax, R toMin, R toMax) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(fromMin, "fromMin");
        Intrinsics.checkNotNullParameter(fromMax, "fromMax");
        Intrinsics.checkNotNullParameter(toMin, "toMin");
        Intrinsics.checkNotNullParameter(toMax, "toMax");
        double dDoubleValue = value.doubleValue();
        double dDoubleValue2 = fromMin.doubleValue();
        double dDoubleValue3 = fromMax.doubleValue();
        double dDoubleValue4 = toMin.doubleValue();
        double dDoubleValue5 = toMax.doubleValue();
        if (dDoubleValue2 >= dDoubleValue3) {
            throw new IllegalArgumentException("fromMin must be less than fromMax".toString());
        }
        if (dDoubleValue4 > dDoubleValue5) {
            throw new IllegalArgumentException("toMin must be less than or equal to toMax".toString());
        }
        double d = dDoubleValue4 + (((dDoubleValue - dDoubleValue2) / (dDoubleValue3 - dDoubleValue2)) * (dDoubleValue5 - dDoubleValue4));
        if (toMin instanceof Integer) {
            Integer numValueOf = Integer.valueOf((int) d);
            Intrinsics.reifiedOperationMarker(1, "R");
            return numValueOf;
        }
        if (toMin instanceof Long) {
            Long lValueOf = Long.valueOf((long) d);
            Intrinsics.reifiedOperationMarker(1, "R");
            return lValueOf;
        }
        if (toMin instanceof Float) {
            Float fValueOf = Float.valueOf((float) d);
            Intrinsics.reifiedOperationMarker(1, "R");
            return fValueOf;
        }
        if (!(toMin instanceof Double)) {
            throw new IllegalArgumentException("Unsupported target type: " + Reflection.getOrCreateKotlinClass(toMin.getClass()).getSimpleName());
        }
        Double dValueOf = Double.valueOf(d);
        Intrinsics.reifiedOperationMarker(1, "R");
        return dValueOf;
    }

    public final Path getSectorPath(Sector sector) {
        Intrinsics.checkNotNullParameter(sector, "sector");
        float startAngle = sector.getStartAngle();
        float endAngle = sector.getEndAngle();
        RectF outerOvalRect = sector.getOuterOvalRect();
        RectF innerOvalRect = sector.getInnerOvalRect();
        float outerCircleRadius = sector.getOuterCircleRadius();
        float innerCircleRadius = sector.getInnerCircleRadius();
        float fWidth = (outerOvalRect.width() / 2.0f) + outerOvalRect.left;
        float fHeight = (outerOvalRect.height() / 2.0f) + outerOvalRect.top;
        float fAdjustAngle = AngleUtils.INSTANCE.adjustAngle(startAngle);
        float fAdjustAngle2 = AngleUtils.INSTANCE.adjustAngle(endAngle);
        float f = fAdjustAngle2 - fAdjustAngle;
        Path path = new Path();
        path.moveTo(fWidth, fHeight);
        path.addArc(outerOvalRect, fAdjustAngle, f);
        path.arcTo(innerOvalRect, fAdjustAngle2, (-1) * f);
        path.close();
        Path path2 = new Path();
        float f2 = (outerCircleRadius - innerCircleRadius) / 2;
        float f3 = innerCircleRadius + f2;
        PointF pointFConvertPolarToPlane = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(f3, fAdjustAngle), outerOvalRect);
        path2.addCircle(pointFConvertPolarToPlane.x, pointFConvertPolarToPlane.y, f2, Path.Direction.CW);
        Path path3 = new Path(path2);
        PointF pointFConvertPolarToPlane2 = PolarUtils.INSTANCE.convertPolarToPlane(new PolarPoint(f3, fAdjustAngle2), outerOvalRect);
        path3.addCircle(pointFConvertPolarToPlane2.x, pointFConvertPolarToPlane2.y, f2, Path.Direction.CW);
        path.op(path2, Path.Op.UNION);
        path.op(path3, Path.Op.UNION);
        return path;
    }

    public static /* synthetic */ Sector getSector$default(QuickUtils quickUtils, PenMenuRes penMenuRes, float f, float f2, PointF pointF, int i, Object obj) {
        if ((i & 8) != 0) {
            pointF = new PointF(0.0f, 0.0f);
        }
        return quickUtils.getSector(penMenuRes, f, f2, pointF);
    }

    public final Sector getSector(PenMenuRes menuRes, float startAngle, float endAngle, PointF startPoint) {
        Intrinsics.checkNotNullParameter(menuRes, "menuRes");
        Intrinsics.checkNotNullParameter(startPoint, "startPoint");
        float outerCircleRadius = menuRes.getOuterCircleRadius();
        float innerCircleRadius = menuRes.getInnerCircleRadius();
        float f = 2;
        float f2 = outerCircleRadius * f;
        RectF rectF = new RectF(startPoint.x, startPoint.y, startPoint.x + f2, startPoint.y + f2);
        float f3 = outerCircleRadius - innerCircleRadius;
        float f4 = startPoint.x + f3;
        float f5 = startPoint.y + f3;
        float f6 = f * innerCircleRadius;
        return new Sector(startAngle, endAngle, rectF, new RectF(f4, f5, f4 + f6, f6 + f5), outerCircleRadius, innerCircleRadius);
    }
}
