package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.HSLColor;

/* JADX INFO: loaded from: classes3.dex */
public class ColorSelectView extends View {
    private boolean isGrayedOut;
    private boolean mCheck;
    private int mColor;
    private Context mContext;
    private HSLColor mHSLColor;
    private boolean mIsBlackBg;
    private Boolean mIsFull;
    private final Paint mPaint;

    public ColorSelectView(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mHSLColor = new HSLColor(0);
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mIsFull = false;
        init(context);
    }

    public ColorSelectView(Context context, Boolean bool) {
        super(context);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mHSLColor = new HSLColor(0);
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mIsFull = false;
        init(context);
        this.mIsFull = bool;
    }

    public ColorSelectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mHSLColor = new HSLColor(0);
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mIsFull = false;
        init(context);
    }

    public ColorSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.mColor = -1;
        this.mHSLColor = new HSLColor(0);
        this.mCheck = false;
        this.mIsBlackBg = false;
        this.isGrayedOut = false;
        this.mIsFull = false;
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

    public HSLColor getHSLColor() {
        return this.mHSLColor;
    }

    public void setHSLColor(HSLColor hSLColor) {
        this.mHSLColor = hSLColor;
        this.mColor = hSLColor.getColor();
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

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        this.mPaint.reset();
        int width = getWidth();
        if (this.mCheck) {
            this.mPaint.setColor(getResources().getColor(R.color.creation_color_pickview_select_bg));
            float f = width;
            float f2 = f / 2.0f;
            canvas.drawCircle(f2, f2, f2, this.mPaint);
            this.mPaint.reset();
            SweepGradient sweepGradient = new SweepGradient(f2, f2, this.mContext.getResources().getIntArray(R.array.creation_gradual_color), (float[]) null);
            Matrix matrix = new Matrix();
            matrix.preRotate(90.0f, f2, f2);
            sweepGradient.setLocalMatrix(matrix);
            this.mPaint.setShader(sweepGradient);
            canvas.drawCircle(f2, f2, f2 - 0.2f, this.mPaint);
            this.mPaint.reset();
            this.mPaint.setColor(-1);
            canvas.drawCircle(f2, f2, f2 - (f / 9.0f), this.mPaint);
            this.mPaint.setColor(this.mColor);
            canvas.drawCircle(f2, f2, f2 - (f / 5.0f), this.mPaint);
        } else {
            this.mPaint.reset();
            if (this.mIsFull.booleanValue()) {
                this.mPaint.setColor(-1);
                float f3 = width / 2.0f;
                float f4 = f3 - 0.2f;
                canvas.drawCircle(f3, f3, f4, this.mPaint);
                this.mPaint.setColor(this.mColor);
                canvas.drawCircle(f3, f3, f4, this.mPaint);
            } else {
                this.mPaint.setColor(-1);
                float f5 = width;
                float f6 = f5 / 2.0f;
                float f7 = f6 - (f5 / 9.0f);
                canvas.drawCircle(f6, f6, f7, this.mPaint);
                this.mPaint.setColor(this.mColor);
                canvas.drawCircle(f6, f6, f7, this.mPaint);
            }
            this.mPaint.setColor(getResources().getColor(R.color.creation_colorselect_borderline, null));
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeWidth(1.0f);
            if (this.mIsFull.booleanValue()) {
                float f8 = width / 2.0f;
                canvas.drawCircle(f8, f8, f8 - 0.2f, this.mPaint);
            } else {
                float f9 = width;
                float f10 = f9 / 2.0f;
                canvas.drawCircle(f10, f10, f10 - (f9 / 9.0f), this.mPaint);
            }
        }
        if (this.isGrayedOut) {
            this.mPaint.reset();
            this.mPaint.setColor(getResources().getColor(R.color.color_picker_picker_filter));
            if (this.mCheck) {
                float f11 = width / 2.0f;
                canvas.drawCircle(f11, f11, f11, this.mPaint);
            } else {
                float f12 = width;
                float f13 = f12 / 2.0f;
                canvas.drawCircle(f13, f13, f13 - (f12 / 9.0f), this.mPaint);
            }
        }
        super.onDraw(canvas);
    }
}
