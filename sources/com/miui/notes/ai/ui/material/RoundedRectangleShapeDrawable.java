package com.miui.notes.ai.ui.material;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;

/* JADX INFO: loaded from: classes4.dex */
class RoundedRectangleShapeDrawable extends Drawable {
    private static final String TAG = "RoundedRectangleShapeDrawable";
    private ColorStateList mBackground;
    private final Rect mBorerRect;
    private ShapeDrawable mBorerShape;
    private float mCornerRadius;
    private final Paint mSolidPaint = new Paint(5);
    private int[] mStrokeColors;
    private Paint mStrokePaint;
    private int mStrokeWidth;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    RoundedRectangleShapeDrawable(float f, int i, int[] iArr) {
        this.mCornerRadius = f;
        setSolidColor(ColorStateList.valueOf(0));
        this.mBorerRect = new Rect();
        this.mStrokeWidth = i;
        this.mStrokeColors = iArr;
        if (i <= 0 || iArr == null) {
            return;
        }
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        paint.setAntiAlias(true);
        this.mStrokePaint.setDither(true);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
    }

    public void setSolidColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.mBackground = colorStateList;
        this.mSolidPaint.setColor(colorStateList.getColorForState(getState(), this.mBackground.getDefaultColor()));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        ShapeDrawable shapeDrawable;
        float f = this.mBorerRect.left;
        float f2 = this.mBorerRect.top;
        float f3 = this.mBorerRect.right;
        float f4 = this.mBorerRect.bottom;
        float f5 = this.mCornerRadius;
        canvas.drawRoundRect(f, f2, f3, f4, f5, f5, this.mSolidPaint);
        if (this.mStrokePaint == null || (shapeDrawable = this.mBorerShape) == null) {
            return;
        }
        shapeDrawable.draw(canvas);
    }

    private void updateBounds(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.mBorerRect.set(rect);
        if (this.mStrokeColors != null) {
            this.mStrokePaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, this.mBorerRect.height(), this.mStrokeColors, (float[]) null, Shader.TileMode.CLAMP));
        }
        ShapeDrawable shapeDrawable = this.mBorerShape;
        if (shapeDrawable != null) {
            shapeDrawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRoundRect(this.mBorerRect, this.mCornerRadius);
    }

    public void setBorderShape(ShapeDrawable shapeDrawable) {
        this.mBorerShape = shapeDrawable;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mSolidPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mSolidPaint.setColorFilter(colorFilter);
    }

    public float getRadius() {
        return this.mCornerRadius;
    }

    public void setColor(ColorStateList colorStateList) {
        setSolidColor(colorStateList);
        invalidateSelf();
    }

    public ColorStateList getColor() {
        return this.mBackground;
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        ColorStateList colorStateList = this.mBackground;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        boolean z = colorForState != this.mSolidPaint.getColor();
        if (z) {
            this.mSolidPaint.setColor(colorForState);
        }
        return z;
    }
}
