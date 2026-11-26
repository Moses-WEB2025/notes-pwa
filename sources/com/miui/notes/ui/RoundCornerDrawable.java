package com.miui.notes.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes4.dex */
public class RoundCornerDrawable extends Drawable {
    private float mCorner;
    private Paint mPaint;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public RoundCornerDrawable(int i, float f) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setColor(i);
        this.mCorner = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        RectF rectF = new RectF(bounds.left, bounds.top, bounds.right, bounds.bottom);
        float f = this.mCorner;
        canvas.drawRoundRect(rectF, f, f, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
