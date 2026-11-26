package xc.engine.core.graphics.engine.core;

import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.Matrix;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.onetrack.b.e;
import com.xiaomi.onetrack.util.a;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.opengl.GLRenderUtil;

/* JADX INFO: compiled from: MiMathUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lxc/engine/core/graphics/engine/core/MiMathUtils;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiMathUtils {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "GLUtils";

    /* JADX INFO: compiled from: MiMathUtils.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J:\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\u000f\u001a\u00020\nJD\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\n2\b\b\u0002\u0010\u0017\u001a\u00020\nJ:\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0019\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u001b\u001a\u00020\u001cJ2\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\n2\b\b\u0002\u0010 \u001a\u00020\n2\b\b\u0002\u0010!\u001a\u00020\nJ&\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nJ&\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010$\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nJ\u001e\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\nJ\u001e\u0010*\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\nJ\u001e\u0010+\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\nJ2\u0010,\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\n2\b\b\u0002\u0010 \u001a\u00020\n2\b\b\u0002\u0010!\u001a\u00020\nJ\u001e\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u001cJ\u001e\u00101\u001a\u00020\n2\u0006\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\n2\u0006\u00104\u001a\u00020\nJ\u000e\u00105\u001a\u00020\b2\u0006\u00106\u001a\u000207J\u000e\u00108\u001a\u00020\n2\u0006\u0010$\u001a\u00020\nJ\u000e\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\u001cJ\u001e\u0010<\u001a\u00020:2\u0006\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\n2\u0006\u00104\u001a\u00020\nJ\u0016\u0010=\u001a\u00020\u001c2\u0006\u0010>\u001a\u00020\u001c2\u0006\u0010?\u001a\u00020\nR\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lxc/engine/core/graphics/engine/core/MiMathUtils$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "calculateMatrix", "", e.a, "", Constants.RANDOM_LONG, Constants.TIMESTAMP, "b", "n", "f", "calculateMatrix2D", "left", EditStat.ELEMENT_RIGHT, Layer.KEY_TOP, "bottom", "angle", "centerX", "centerY", "calculateMatrixTranslateM2D", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, a.e, "Landroid/graphics/RectF;", "remapRange", "srcMin", "srcMax", "dstMin", "dstMax", "getRotatedBoundingBox", "rectF", "degrees", "getRotatedPoint", "Landroid/graphics/PointF;", "point", "smoothLerp", HtmlParser.LinkCardElement.TAG, "lerp", "smoothEaseSinInOut", "remapRangeClamped", "remapArea", "inArea", "srcArea", "targetArea", "clamp", "value", "minValue", "maxValue", "getColorFromInt", "color", "", "degreesToRadians", "isValidCropRect", "", "cropRect", "isInRange", "getScaledRectF", "inRect", "scale", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float degreesToRadians(float degrees) {
            return degrees * 0.017453292f;
        }

        public final boolean isInRange(float value, float minValue, float maxValue) {
            return minValue <= value && value <= maxValue;
        }

        public final float lerp(float a, float b, float x) {
            return a + (x * (b - a));
        }

        public final float remapRange(float x, float srcMin, float srcMax, float dstMin, float dstMax) {
            return dstMin + (((x - srcMin) / (srcMax - srcMin)) * (dstMax - dstMin));
        }

        public final float smoothLerp(float a, float b, float x) {
            return a + (x * (3 - (2 * x)) * x * (b - a));
        }

        private Companion() {
        }

        public static /* synthetic */ float[] calculateMatrix$default(Companion companion, float f, float f2, float f3, float f4, float f5, float f6, int i, Object obj) {
            if ((i & 16) != 0) {
                f5 = -1.0f;
            }
            float f7 = f5;
            if ((i & 32) != 0) {
                f6 = 1.0f;
            }
            return companion.calculateMatrix(f, f2, f3, f4, f7, f6);
        }

        public final float[] calculateMatrix(float l, float r, float t, float b, float n, float f) {
            float f2 = r - l;
            float f3 = t - b;
            float f4 = f - n;
            return new float[]{2.0f / f2, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f / f3, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f / f4, 0.0f, (-(r + l)) / f2, (-(t + b)) / f3, (-(f + n)) / f4, 1.0f};
        }

        public static /* synthetic */ float[] calculateMatrix2D$default(Companion companion, float f, float f2, float f3, float f4, float f5, float f6, float f7, int i, Object obj) {
            if ((i & 16) != 0) {
                f5 = 0.0f;
            }
            if ((i & 32) != 0) {
                f6 = 0.0f;
            }
            if ((i & 64) != 0) {
                f7 = 0.0f;
            }
            return companion.calculateMatrix2D(f, f2, f3, f4, f5, f6, f7);
        }

        public final float[] calculateMatrix2D(float left, float right, float top, float bottom, float angle, float centerX, float centerY) {
            float f = right - left;
            float f2 = top - bottom;
            float[] fArr = {2.0f / f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f / f2, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, (-(right + left)) / f, (-(top + bottom)) / f2, 0.0f, 1.0f};
            if (angle == 0.0f) {
                return fArr;
            }
            double d = -((float) Math.toRadians(angle));
            float fCos = (float) Math.cos(d);
            float fSin = (float) Math.sin(d);
            float[] fArr2 = new float[16];
            float[] fArr3 = new float[16];
            float[] fArr4 = new float[16];
            Matrix.multiplyMM(fArr2, 0, new float[]{fCos, -fSin, 0.0f, 0.0f, fSin, fCos, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}, 0, new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, -centerX, -centerY, 0.0f, 1.0f}, 0);
            Matrix.multiplyMM(fArr3, 0, new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, centerX, centerY, 0.0f, 1.0f}, 0, fArr2, 0);
            Matrix.multiplyMM(fArr4, 0, fArr, 0, fArr3, 0);
            return fArr4;
        }

        public static /* synthetic */ float[] calculateMatrixTranslateM2D$default(Companion companion, float f, float f2, float f3, float f4, float f5, float f6, int i, Object obj) {
            if ((i & 16) != 0) {
                f5 = 0.0f;
            }
            if ((i & 32) != 0) {
                f6 = 0.0f;
            }
            return companion.calculateMatrixTranslateM2D(f, f2, f3, f4, f5, f6);
        }

        public final float[] calculateMatrixTranslateM2D(float left, float right, float top, float bottom, float x, float y) {
            float f = right - left;
            float f2 = 2.0f / f;
            float f3 = top - bottom;
            float f4 = 2.0f / f3;
            return new float[]{f2, 0.0f, 0.0f, 0.0f, 0.0f, f4, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, (x * f2) - ((right + left) / f), (y * f4) - ((top + bottom) / f3), 0.0f, 1.0f};
        }

        public final float[] calculateMatrix(RectF area) {
            Intrinsics.checkNotNullParameter(area, "area");
            return calculateMatrix$default(this, area.left, area.right, area.top, area.bottom, 0.0f, 0.0f, 48, null);
        }

        public static /* synthetic */ float remapRange$default(Companion companion, float f, float f2, float f3, float f4, float f5, int i, Object obj) {
            if ((i & 8) != 0) {
                f4 = -1.0f;
            }
            float f6 = f4;
            if ((i & 16) != 0) {
                f5 = 1.0f;
            }
            return companion.remapRange(f, f2, f3, f6, f5);
        }

        public final RectF getRotatedBoundingBox(RectF rectF, float degrees, float centerX, float centerY) {
            Intrinsics.checkNotNullParameter(rectF, "rectF");
            PointF rotatedPoint = getRotatedPoint(new PointF(rectF.centerX(), rectF.centerY()), degrees, centerX, centerY);
            PointF rotatedPoint2 = getRotatedPoint(new PointF(rectF.left, rectF.top), degrees, centerX, centerY);
            PointF rotatedPoint3 = getRotatedPoint(new PointF(rectF.left, rectF.bottom), degrees, centerX, centerY);
            PointF pointF = new PointF((rotatedPoint.x * 2.0f) - rotatedPoint3.x, (rotatedPoint.y * 2.0f) - rotatedPoint3.y);
            PointF pointF2 = new PointF((rotatedPoint.x * 2.0f) - rotatedPoint2.x, (rotatedPoint.y * 2.0f) - rotatedPoint2.y);
            RectF rectF2 = new RectF(rotatedPoint2.x, rotatedPoint2.y, rotatedPoint2.x, rotatedPoint2.y);
            rectF2.union(rotatedPoint3.x, rotatedPoint3.y);
            rectF2.union(pointF.x, pointF.y);
            rectF2.union(pointF2.x, pointF2.y);
            return rectF2;
        }

        public final PointF getRotatedPoint(PointF point, float degrees, float centerX, float centerY) {
            Intrinsics.checkNotNullParameter(point, "point");
            PointF pointF = new PointF();
            float f = point.x - centerX;
            float f2 = point.y - centerY;
            double radians = Math.toRadians(degrees);
            float fCos = (float) Math.cos(radians);
            float fSin = (float) Math.sin(radians);
            pointF.x = ((f * fCos) - (f2 * fSin)) + centerX;
            pointF.y = (f * fSin) + (f2 * fCos) + centerY;
            return pointF;
        }

        public final float smoothEaseSinInOut(float a, float b, float x) {
            return lerp(a, b, (1.0f - ((float) Math.cos(x * 3.1415927f))) / 2.0f);
        }

        public static /* synthetic */ float remapRangeClamped$default(Companion companion, float f, float f2, float f3, float f4, float f5, int i, Object obj) {
            if ((i & 8) != 0) {
                f4 = -1.0f;
            }
            float f6 = f4;
            if ((i & 16) != 0) {
                f5 = 1.0f;
            }
            return companion.remapRangeClamped(f, f2, f3, f6, f5);
        }

        public final float remapRangeClamped(float x, float srcMin, float srcMax, float dstMin, float dstMax) {
            float f = (((x - srcMin) / (srcMax - srcMin)) * (dstMax - dstMin)) + dstMin;
            if (dstMax > dstMin) {
                return Math.max(Math.min(f, dstMax), dstMin);
            }
            return Math.max(Math.min(f, dstMin), dstMax);
        }

        public final RectF remapArea(RectF inArea, RectF srcArea, RectF targetArea) {
            Intrinsics.checkNotNullParameter(inArea, "inArea");
            Intrinsics.checkNotNullParameter(srcArea, "srcArea");
            Intrinsics.checkNotNullParameter(targetArea, "targetArea");
            return new RectF(remapRange(inArea.left, srcArea.left, srcArea.right, targetArea.left, targetArea.right), remapRange(inArea.top, srcArea.top, srcArea.bottom, targetArea.top, targetArea.bottom), remapRange(inArea.right, srcArea.left, srcArea.right, targetArea.left, targetArea.right), remapRange(inArea.bottom, srcArea.top, srcArea.bottom, targetArea.top, targetArea.bottom));
        }

        public final float clamp(float value, float minValue, float maxValue) {
            return Math.min(Math.max(value, minValue), maxValue);
        }

        public final float[] getColorFromInt(int color) {
            float[] rgba = GLRenderUtil.toRGBA(android.graphics.Color.argb(255, (color >> 16) & 255, (color >> 8) & 255, color & 255));
            Intrinsics.checkNotNullExpressionValue(rgba, "toRGBA(...)");
            return rgba;
        }

        public final boolean isValidCropRect(RectF cropRect) {
            Intrinsics.checkNotNullParameter(cropRect, "cropRect");
            return isInRange(cropRect.left, 0.0f, 1.0f) && isInRange(cropRect.right, 0.0f, 1.0f) && isInRange(cropRect.top, 0.0f, 1.0f) && isInRange(cropRect.bottom, 0.0f, 1.0f) && cropRect.left < cropRect.right && cropRect.top < cropRect.bottom;
        }

        public final RectF getScaledRectF(RectF inRect, float scale) {
            Intrinsics.checkNotNullParameter(inRect, "inRect");
            if (scale <= 0.0f) {
                return inRect;
            }
            float fCenterX = inRect.centerX();
            float fCenterY = inRect.centerY();
            float fWidth = inRect.width() * 0.5f * scale;
            float fHeight = inRect.height() * 0.5f * scale;
            return new RectF(fCenterX - fWidth, fCenterY - fHeight, fCenterX + fWidth, fCenterY + fHeight);
        }
    }
}
