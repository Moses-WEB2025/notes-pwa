package com.miui.notes.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.miui.notes.R;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes4.dex */
public class RecordingProgressView extends View {
    private static final double AMP_DISPLAY_RATIO = 0.05d;
    private int mCount;
    private int mDefaultColor;
    private int mDrawingPositionX;
    private LinkedList<Integer> mElements;
    private int mFilterCount;
    private int mIndicatorColor;
    private int mIndicatorMaxHeight;
    private int mIndicatorNum;
    private int mIndicatorRadius;
    private int mIndicatorWidth;
    private int mInterval;
    private int mMaxIndicatorNum;
    Paint mPaint;

    private void initParams(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecordingProgressView);
        this.mInterval = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, 25);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 4);
        this.mIndicatorWidth = dimensionPixelSize;
        this.mIndicatorRadius = dimensionPixelSize / 2;
        this.mIndicatorMaxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 80);
        int i = typedArrayObtainStyledAttributes.getInt(3, 22);
        this.mIndicatorNum = i;
        this.mMaxIndicatorNum = i;
        this.mDefaultColor = typedArrayObtainStyledAttributes.getColor(0, -7829368);
        this.mIndicatorColor = typedArrayObtainStyledAttributes.getColor(1, -16776961);
        typedArrayObtainStyledAttributes.recycle();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public RecordingProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mElements = new LinkedList<>();
        this.mPaint = new Paint();
        this.mFilterCount = 10;
        initParams(context, attributeSet);
    }

    public RecordingProgressView(Context context) {
        super(context);
        this.mElements = new LinkedList<>();
        this.mPaint = new Paint();
        this.mFilterCount = 10;
    }

    public RecordingProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mElements = new LinkedList<>();
        this.mPaint = new Paint();
        this.mFilterCount = 10;
        initParams(context, attributeSet);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension((this.mInterval + this.mIndicatorWidth) * this.mIndicatorNum, getDefaultSize(getSuggestedMinimumHeight(), i2));
    }

    public void reset() {
        this.mElements.clear();
        update();
        this.mCount = 0;
    }

    private void drawVolumes(Canvas canvas) {
        this.mPaint.setColor(isEnabled() ? this.mIndicatorColor : this.mDefaultColor);
        int i = 0;
        for (Integer num : this.mElements) {
            if (this.mElements.size() - i > this.mIndicatorNum) {
                i++;
            } else {
                int iIntValue = num.intValue();
                int height = (getHeight() - iIntValue) / 2;
                int i2 = this.mDrawingPositionX;
                int i3 = this.mIndicatorRadius;
                float f = height;
                canvas.drawCircle(i2 + i3, f, i3, this.mPaint);
                int i4 = this.mDrawingPositionX;
                float f2 = height + iIntValue;
                Canvas canvas2 = canvas;
                canvas2.drawRect(i4, f, i4 + this.mIndicatorWidth, f2, this.mPaint);
                int i5 = this.mDrawingPositionX;
                int i6 = this.mIndicatorRadius;
                canvas2.drawCircle(i5 + i6, f2, i6, this.mPaint);
                this.mDrawingPositionX += this.mInterval + this.mIndicatorWidth;
                canvas = canvas2;
            }
        }
    }

    private void drawDots(Canvas canvas) {
        this.mPaint.setColor(this.mDefaultColor);
        this.mDrawingPositionX = 0;
        int height = (getHeight() / 2) - this.mIndicatorRadius;
        for (int i = 0; i < this.mIndicatorNum - this.mElements.size(); i++) {
            int i2 = this.mDrawingPositionX;
            int i3 = this.mIndicatorRadius;
            canvas.drawCircle(i2 + i3, height + i3, i3, this.mPaint);
            this.mDrawingPositionX += this.mInterval + this.mIndicatorWidth;
        }
    }

    public void addVolumes(double d) {
        if (this.mElements.size() == this.mMaxIndicatorNum) {
            this.mElements.removeFirst();
        }
        this.mElements.add(Integer.valueOf(volumeToPix(d)));
        update();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDots(canvas);
        drawVolumes(canvas);
    }

    private void update() {
        invalidate();
    }

    private int volumeToPix(double d) {
        double dSqrt = d * AMP_DISPLAY_RATIO;
        int i = this.mIndicatorMaxHeight;
        if (dSqrt > ((double) i) / 2.0d) {
            int i2 = this.mCount;
            this.mCount = i2 + 1;
            dSqrt = i2 < this.mFilterCount ? ((double) i) / 2.0d : (((double) i) / 2.0d) + (Math.sqrt(d - ((((double) i) / 2.0d) / AMP_DISPLAY_RATIO)) * AMP_DISPLAY_RATIO * 5.0d);
        }
        int i3 = this.mIndicatorMaxHeight;
        if (dSqrt > i3) {
            dSqrt = i3;
        }
        return (int) dSqrt;
    }
}
