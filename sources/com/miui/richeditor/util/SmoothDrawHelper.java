package com.miui.richeditor.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import miuix.smooth.SmoothPathProvider;

/* JADX INFO: loaded from: classes4.dex */
public class SmoothDrawHelper {
    private Path mClipPath;
    private PorterDuffXfermode mClipXfermode;
    private RectF mLayer;
    private Path mOutterPath;
    private SmoothPathProvider mPathProvider;
    private float[] mRadii;
    private float mRadius;
    private int mStrokeWidth = 0;
    private int mStrokeColor = 0;
    private Paint mStrokePaint = new Paint(1);
    private Paint mClipPaint = new Paint(1);

    public SmoothDrawHelper() {
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mOutterPath = new Path();
        this.mClipPath = new Path();
        this.mClipXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.mPathProvider = new SmoothPathProvider();
        this.mLayer = new RectF();
    }

    public void setStrokeWidth(int i) {
        this.mStrokeWidth = i;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public void setStrokeColor(int i) {
        this.mStrokeColor = i;
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public void setRadii(float[] fArr) {
        this.mRadii = fArr;
    }

    public float[] getRadii() {
        return this.mRadii;
    }

    public void setRadius(float f) {
        this.mRadius = f;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public void onBoundsChange(Rect rect) {
        this.mLayer.set(rect);
        int i = this.mStrokeWidth;
        float f = (i == 0 || this.mStrokeColor == 0) ? 0.0f : i / 2.0f;
        this.mOutterPath = getSmoothPathFromProvider(this.mOutterPath, this.mLayer, this.mRadii, this.mRadius, f, f);
        Path path = this.mClipPath;
        if (path != null) {
            path.reset();
        } else {
            this.mClipPath = new Path();
        }
        this.mClipPath.addRect(this.mLayer, Path.Direction.CW);
        this.mClipPath.op(this.mOutterPath, Path.Op.DIFFERENCE);
    }

    public Path getSmoothPathFromProvider(Path path, RectF rectF, float[] fArr, float f, float f2, float f3) {
        if (fArr == null) {
            this.mPathProvider.buildSmoothData(rectF, f, f2, f3);
        } else {
            this.mPathProvider.buildSmoothData(rectF, fArr, f2, f3);
        }
        return this.mPathProvider.getSmoothPath(path);
    }

    public void drawMask(Canvas canvas) {
        this.mClipPaint.setXfermode(this.mClipXfermode);
        canvas.drawPath(this.mClipPath, this.mClipPaint);
        this.mClipPaint.setXfermode(null);
    }

    public void drawStroke(Canvas canvas) {
        if (this.mStrokeWidth == 0 || this.mStrokeColor == 0) {
            return;
        }
        canvas.save();
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
        this.mStrokePaint.setColor(this.mStrokeColor);
        canvas.drawPath(this.mOutterPath, this.mStrokePaint);
        canvas.restore();
    }
}
