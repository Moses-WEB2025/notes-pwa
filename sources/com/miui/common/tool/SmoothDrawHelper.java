package com.miui.common.tool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.verificationsdk.internal.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.smooth.SmoothPathProvider;

/* JADX INFO: compiled from: SmoothDrawHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0014\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J<\u0010)\u001a\u00020\u001f2\b\u0010*\u001a\u0004\u0018\u00010\u001f2\b\u0010+\u001a\u0004\u0018\u00010\u001a2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0014J\u000e\u0010.\u001a\u00020&2\u0006\u0010/\u001a\u000200J\u000e\u00101\u001a\u00020&2\u0006\u0010/\u001a\u000200R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lcom/miui/common/tool/SmoothDrawHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "strokeWidth", "", "getStrokeWidth", "()I", "setStrokeWidth", "(I)V", "strokeColor", "getStrokeColor", "setStrokeColor", "radii", "", "getRadii", "()[F", "setRadii", "([F)V", "radius", "", "getRadius", "()F", "setRadius", "(F)V", "mLayer", "Landroid/graphics/RectF;", "mStrokePaint", "Landroid/graphics/Paint;", "mClipPaint", "mOutterPath", "Landroid/graphics/Path;", "mClipPath", "mClipXfermode", "Landroid/graphics/PorterDuffXfermode;", "mPathProvider", "Lmiuix/smooth/SmoothPathProvider;", "onBoundsChange", "", "rect", "Landroid/graphics/Rect;", "getSmoothPathFromProvider", TypedValues.AttributesType.S_TARGET, Constants.RANDOM_LONG, "offsetX", "offsetY", "drawMask", "canvas", "Landroid/graphics/Canvas;", "drawStroke", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class SmoothDrawHelper {
    private final Paint mClipPaint;
    private Path mClipPath;
    private final PorterDuffXfermode mClipXfermode;
    private final RectF mLayer;
    private Path mOutterPath;
    private final SmoothPathProvider mPathProvider;
    private final Paint mStrokePaint;
    private float[] radii;
    private float radius;
    private int strokeColor;
    private int strokeWidth;

    public SmoothDrawHelper() {
        Paint paint = new Paint(1);
        this.mStrokePaint = paint;
        this.mClipPaint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        this.mOutterPath = new Path();
        this.mClipPath = new Path();
        this.mClipXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.mPathProvider = new SmoothPathProvider();
        this.mLayer = new RectF();
    }

    public final int getStrokeWidth() {
        return this.strokeWidth;
    }

    public final void setStrokeWidth(int i) {
        this.strokeWidth = i;
    }

    public final int getStrokeColor() {
        return this.strokeColor;
    }

    public final void setStrokeColor(int i) {
        this.strokeColor = i;
    }

    public final float[] getRadii() {
        return this.radii;
    }

    public final void setRadii(float[] fArr) {
        this.radii = fArr;
    }

    public final float getRadius() {
        return this.radius;
    }

    public final void setRadius(float f) {
        this.radius = f;
    }

    public final void onBoundsChange(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.mLayer.set(rect);
        int i = this.strokeWidth;
        float f = (i == 0 || this.strokeColor == 0) ? 0.0f : i / 2.0f;
        this.mOutterPath = getSmoothPathFromProvider(this.mOutterPath, this.mLayer, this.radii, this.radius, f, f);
        Path path = this.mClipPath;
        if (path != null) {
            Intrinsics.checkNotNull(path);
            path.reset();
        } else {
            this.mClipPath = new Path();
        }
        Path path2 = this.mClipPath;
        Intrinsics.checkNotNull(path2);
        path2.addRect(this.mLayer, Path.Direction.CW);
        Path path3 = this.mClipPath;
        Intrinsics.checkNotNull(path3);
        path3.op(this.mOutterPath, Path.Op.DIFFERENCE);
    }

    public final Path getSmoothPathFromProvider(Path target, RectF r, float[] radii, float radius, float offsetX, float offsetY) {
        if (radii == null) {
            this.mPathProvider.buildSmoothData(r, radius, offsetX, offsetY);
        } else {
            this.mPathProvider.buildSmoothData(r, radii, offsetX, offsetY);
        }
        Path smoothPath = this.mPathProvider.getSmoothPath(target);
        Intrinsics.checkNotNullExpressionValue(smoothPath, "getSmoothPath(...)");
        return smoothPath;
    }

    public final void drawMask(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.mClipPaint.setXfermode(this.mClipXfermode);
        Path path = this.mClipPath;
        Intrinsics.checkNotNull(path);
        canvas.drawPath(path, this.mClipPaint);
        this.mClipPaint.setXfermode(null);
    }

    public final void drawStroke(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.strokeWidth == 0 || this.strokeColor == 0) {
            return;
        }
        canvas.save();
        this.mStrokePaint.setStrokeWidth(this.strokeWidth);
        this.mStrokePaint.setColor(this.strokeColor);
        canvas.drawPath(this.mOutterPath, this.mStrokePaint);
        canvas.restore();
    }
}
