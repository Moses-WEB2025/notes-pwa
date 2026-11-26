package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import com.miui.creation.common.R;

/* JADX INFO: loaded from: classes3.dex */
public class ShapeColorSelectView extends View {
    public static final int MODE_SOLID = 0;
    public static final int MODE_STROKE = 1;
    public static final int MODE_STROKE_SOLID = 2;
    public static final int MODE_STROKE_WITH_INNER = 4;
    private boolean isGrayedOut;
    private boolean mCheck;
    private int mColor;
    private Context mContext;
    private boolean mIsBlackBg;
    public int mMode;
    private final Paint mPaint;
    private boolean mShowForbiden;

    public ShapeColorSelectView(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mShowForbiden = false;
        this.mMode = 0;
        init(context);
    }

    public ShapeColorSelectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mShowForbiden = false;
        this.mMode = 0;
        init(context);
    }

    public ShapeColorSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mShowForbiden = false;
        this.mMode = 0;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public int getColor() {
        return this.mColor;
    }

    public void setColor(int i) {
        this.mColor = i;
        invalidate();
    }

    public boolean isCheck() {
        return this.mCheck;
    }

    public void setCheck(boolean z) {
        this.mCheck = z;
        invalidate();
    }

    public void setBlackBg(boolean z) {
        this.mIsBlackBg = z;
        invalidate();
    }

    public void setGrayedOut(boolean z) {
        this.isGrayedOut = z;
        invalidate();
    }

    public int getMode() {
        return this.mMode;
    }

    public void setMode(int i) {
        this.mMode = i;
        invalidate();
    }

    public void setShowForbiden(boolean z) {
        this.mShowForbiden = z;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Canvas canvas2;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        this.mPaint.reset();
        int width = getWidth();
        if (this.mCheck) {
            this.mPaint.reset();
            float f = width;
            float f2 = f / 2.0f;
            SweepGradient sweepGradient = new SweepGradient(f2, f2, this.mContext.getResources().getIntArray(R.array.creation_gradual_color), (float[]) null);
            Matrix matrix = new Matrix();
            matrix.preRotate(90.0f, f2, f2);
            sweepGradient.setLocalMatrix(matrix);
            this.mPaint.setShader(sweepGradient);
            canvas.drawCircle(f2, f2, f2 - 0.2f, this.mPaint);
            this.mPaint.reset();
            this.mPaint.setColor(-1);
            canvas.drawCircle(f2, f2, f2 - (f / 9.0f), this.mPaint);
        }
        int i = this.mMode;
        if (i == 0) {
            if (this.mCheck) {
                this.mPaint.reset();
                this.mPaint.setColor(-1);
                float f3 = width;
                float f4 = f3 / 2.0f;
                canvas.drawCircle(f4, f4, f4 - (f3 / 9.0f), this.mPaint);
                this.mPaint.reset();
                this.mPaint.setColor(this.mColor);
                canvas.drawCircle(f4, f4, f4 - (f3 / 5.0f), this.mPaint);
            } else {
                this.mPaint.reset();
                this.mPaint.setColor(this.mColor);
                float f5 = width;
                float f6 = f5 / 2.0f;
                float f7 = f6 - (f5 / 9.0f);
                canvas.drawCircle(f6, f6, f7, this.mPaint);
                this.mPaint.setColor(getResources().getColor(R.color.creation_color_pickview_select_bg, null));
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeWidth(1.0f);
                canvas.drawCircle(f6, f6, f7, this.mPaint);
            }
        } else if (i == 2) {
            this.mPaint.reset();
            this.mPaint.setColor(this.mColor);
            float f8 = width;
            float f9 = f8 / 2.0f;
            float f10 = f9 - (f8 / 9.0f);
            canvas.drawCircle(f9, f9, f10, this.mPaint);
            if (!this.mCheck) {
                this.mPaint.setColor(getResources().getColor(R.color.creation_color_pickview_select_bg, null));
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeWidth(1.0f);
                canvas.drawCircle(f9, f9, f10, this.mPaint);
            }
        } else if (i == 1) {
            this.mPaint.reset();
            this.mPaint.setColor(getResources().getColor(R.color.creation_color_pickview_select_bg));
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(1.0f);
            float f11 = width;
            float f12 = f11 / 2.0f;
            canvas.drawCircle(f12, f12, f12 - (f11 / 9.0f), this.mPaint);
            this.mPaint.setColor(this.mColor);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(6.0f);
            canvas.drawCircle(f12, f12, f12 - (f11 / 6.0f), this.mPaint);
        } else if (i == 4) {
            this.mPaint.reset();
            this.mPaint.setColor(getResources().getColor(R.color.creation_color_pickview_select_bg));
            float f13 = width;
            float f14 = f13 / 2.0f;
            canvas.drawCircle(f14, f14, f14 - (f13 / 6.0f), this.mPaint);
            this.mPaint.setColor(this.mColor);
            canvas.drawCircle(f14, f14, f14 - (f13 / 9.0f), this.mPaint);
            this.mPaint.reset();
            this.mPaint.setColor(-1);
            canvas.drawCircle(f14, f14, f14 - (f13 / 5.0f), this.mPaint);
        }
        if (this.isGrayedOut) {
            this.mPaint.reset();
            this.mPaint.setColor(getResources().getColor(R.color.color_picker_picker_filter));
            if (this.mCheck) {
                float f15 = width / 2.0f;
                canvas.drawCircle(f15, f15, f15, this.mPaint);
            } else {
                float f16 = width;
                float f17 = f16 / 2.0f;
                canvas.drawCircle(f17, f17, f17 - (f16 / 9.0f), this.mPaint);
            }
        }
        if (this.mShowForbiden) {
            this.mPaint.reset();
            this.mPaint.setColor(Color.parseColor("#FA311B"));
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mPaint.setStrokeWidth(4.0f);
            float f18 = width;
            float f19 = f18 / 7.0f;
            canvas2 = canvas;
            canvas2.drawLine(f19, getHeight() - f19, f18 - f19, f19, this.mPaint);
        } else {
            canvas2 = canvas;
        }
        super.onDraw(canvas2);
    }
}
