package com.miui.doodle.feature.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.utils.DrawUtil;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CopyLocation.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005J\u0016\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005J(\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002J\u0018\u0010\u001b\u001a\u00020\u00192\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u0005J\u001e\u0010\u001f\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u0005J\u0006\u0010!\u001a\u00020\u0000J\u0006\u0010\"\u001a\u00020\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/miui/doodle/feature/draw/CopyLocation;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "copyStartX", "", "copyStartY", "touchStartX", "touchStartY", AnimatedProperty.PROPERTY_NAME_X, "getX", "()F", "setX", "(F)V", AnimatedProperty.PROPERTY_NAME_Y, "getY", "setY", "paint", "Landroid/graphics/Paint;", "isRelocating", "", "isCopying", "mTemp", "Landroid/graphics/PointF;", "updateLocation", "", "setStartPosition", "drawItSelf", "canvas", "Landroid/graphics/Canvas;", "size", "contains", "mPaintSize", "copy", "reset", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CopyLocation {
    private float copyStartX;
    private float copyStartY;
    private boolean isCopying;
    private boolean isRelocating;
    private final PointF mTemp;
    private final Paint paint;
    private float touchStartX;
    private float touchStartY;
    private float x;
    private float y;

    public CopyLocation() {
        Paint paint = new Paint();
        this.paint = paint;
        this.isRelocating = true;
        this.mTemp = new PointF();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final void updateLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final void setStartPosition(float touchStartX, float touchStartY) {
        setStartPosition(touchStartX, touchStartY, this.x, this.y);
    }

    private final void setStartPosition(float touchStartX, float touchStartY, float copyStartX, float copyStartY) {
        this.copyStartX = copyStartX;
        this.copyStartY = copyStartY;
        this.touchStartX = touchStartX;
        this.touchStartY = touchStartY;
    }

    public final void drawItSelf(Canvas canvas, float size) {
        this.paint.setStrokeWidth(size / 4);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(-1436129690);
        DrawUtil drawUtil = DrawUtil.INSTANCE;
        Intrinsics.checkNotNull(canvas);
        float f = size / 2;
        drawUtil.drawCircle(canvas, this.x, this.y, f + (size / 8), this.paint);
        this.paint.setStrokeWidth(size / 16);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(-1426063361);
        DrawUtil.INSTANCE.drawCircle(canvas, this.x, this.y, f + (size / 32), this.paint);
        this.paint.setStyle(Paint.Style.FILL);
        if (!this.isCopying) {
            this.paint.setColor(1157562368);
            DrawUtil.INSTANCE.drawCircle(canvas, this.x, this.y, f, this.paint);
        } else {
            this.paint.setColor(1140850824);
            DrawUtil.INSTANCE.drawCircle(canvas, this.x, this.y, f, this.paint);
        }
    }

    public final boolean contains(float x, float y, float mPaintSize) {
        float f = this.x;
        float f2 = this.y;
        return ((f - x) * (f - x)) + ((f2 - y) * (f2 - y)) <= mPaintSize * mPaintSize;
    }

    public final CopyLocation copy() {
        CopyLocation copyLocation = new CopyLocation();
        copyLocation.copyStartX = this.copyStartX;
        copyLocation.copyStartY = this.copyStartY;
        copyLocation.touchStartX = this.touchStartX;
        copyLocation.touchStartY = this.touchStartY;
        copyLocation.x = this.x;
        copyLocation.y = this.y;
        return copyLocation;
    }

    public final void reset() {
        this.y = 0.0f;
        this.x = 0.0f;
        this.touchStartY = 0.0f;
        this.touchStartX = 0.0f;
        this.copyStartY = 0.0f;
        this.copyStartX = 0.0f;
        this.isRelocating = true;
        this.isCopying = false;
    }
}
