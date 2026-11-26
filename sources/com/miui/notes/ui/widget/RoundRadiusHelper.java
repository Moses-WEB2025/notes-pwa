package com.miui.notes.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class RoundRadiusHelper {
    private static final String TAG = "RoundRadiusHelper";
    private Paint mBorderPaint;
    private float mBorderWidth;
    private RectF mRectF;
    private Path mRoundPath;
    private float mRoundRadius;

    public RoundRadiusHelper(float f, float f2, int i) {
        this.mRoundRadius = f;
        this.mBorderWidth = f2;
        if (f <= 0.0f) {
            throw new IllegalStateException("RoundRadius be greater than 0");
        }
        Paint paint = new Paint();
        this.mBorderPaint = paint;
        paint.setColor(i);
    }

    protected void onLayout(View view) {
        if (view.getWidth() <= 0 || view.getHeight() == 0) {
            Logger.INSTANCE.w(TAG, "Can not be clip to round radius view");
            return;
        }
        if (this.mRoundRadius > view.getWidth()) {
            Logger.INSTANCE.w(TAG, "Radius is greater than view width()");
        }
        if (this.mRoundRadius > view.getHeight()) {
            Logger.INSTANCE.w(TAG, "Radius is greater than view width()");
        }
        RectF rectF = this.mRectF;
        if (rectF == null) {
            RectF rectF2 = new RectF();
            this.mRectF = rectF2;
            rectF2.set(0.0f, 0.0f, view.getMeasuredWidth(), view.getMeasuredHeight());
        } else if (rectF.bottom != view.getMeasuredWidth() || this.mRectF.right != view.getMeasuredHeight()) {
            this.mRectF.set(0.0f, 0.0f, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        Path path = this.mRoundPath;
        if (path == null) {
            this.mRoundPath = new Path();
        } else if (!path.isEmpty()) {
            this.mRoundPath.reset();
        }
        Path path2 = this.mRoundPath;
        float f = this.mBorderWidth + this.mRectF.left;
        float f2 = this.mBorderWidth + this.mRectF.top;
        float f3 = this.mRectF.right - this.mBorderWidth;
        float f4 = this.mRectF.bottom - this.mBorderWidth;
        float f5 = this.mRoundRadius;
        path2.addRoundRect(f, f2, f3, f4, f5, f5, Path.Direction.CW);
    }

    public void draw(Canvas canvas) {
        RectF rectF;
        if (this.mBorderWidth > 0.0f && (rectF = this.mRectF) != null) {
            float f = this.mRoundRadius;
            canvas.drawRoundRect(rectF, f + 1.0f, f + 1.0f, this.mBorderPaint);
        }
        Path path = this.mRoundPath;
        if (path == null || path.isEmpty()) {
            return;
        }
        canvas.clipPath(this.mRoundPath);
    }
}
