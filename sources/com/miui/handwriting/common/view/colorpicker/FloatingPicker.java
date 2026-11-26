package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.miui.creation.common.R;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class FloatingPicker extends View {
    private float borderWidth;
    private Path clipPath;
    private int mBorderColor;
    private Paint mBorderInnerLinePaint;
    private float mBorderInnerLineStroke;
    private int mBorderLineColor;
    private Paint mBorderOuterLinePaint;
    private float mBorderOuterLineStroke;
    private Paint mBorderPaint;
    private float mBorderStroke;
    private int[] mColors;
    private int mGridColor;
    private Paint mGridColorPaint;
    private Paint mGridPaint;
    private float mGridStroke;
    private int mHeight;
    private int mPickerBorder;
    private int mPickerCenterOffset;
    private int mPickerCenterOffsetX;
    private int mPickerCenterOffsetY;
    private int mPickerHeight;
    private int mPickerWidth;
    private int mSquareColor;
    private Paint mSquarePaint;
    private float mSquareStroke;
    private int mWidth;
    private int shadowColor;
    private float shadowDx;
    private float shadowDy;
    private Paint shadowPaint;
    private float shadowRadius;

    public FloatingPicker(Context context) {
        super(context);
        this.borderWidth = 5.0f;
        this.shadowColor = -7829368;
        this.shadowRadius = 20.0f;
        this.shadowDx = 0.0f;
        this.shadowDy = 0.0f;
        init();
    }

    public FloatingPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.borderWidth = 5.0f;
        this.shadowColor = -7829368;
        this.shadowRadius = 20.0f;
        this.shadowDx = 0.0f;
        this.shadowDy = 0.0f;
        init();
    }

    public FloatingPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.borderWidth = 5.0f;
        this.shadowColor = -7829368;
        this.shadowRadius = 20.0f;
        this.shadowDx = 0.0f;
        this.shadowDy = 0.0f;
        init();
    }

    public FloatingPicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.borderWidth = 5.0f;
        this.shadowColor = -7829368;
        this.shadowRadius = 20.0f;
        this.shadowDx = 0.0f;
        this.shadowDy = 0.0f;
        init();
    }

    private void init() {
        this.mBorderStroke = getContext().getResources().getDimension(R.dimen.creation_color_picker_floating_picker_border_stroke);
        this.mBorderOuterLineStroke = getContext().getResources().getDimension(R.dimen.creation_color_picker_floating_picker_border_outer_line_stroke);
        this.mBorderInnerLineStroke = getContext().getResources().getDimension(R.dimen.creation_color_picker_floating_picker_border_inner_line_stroke);
        this.mGridStroke = getContext().getResources().getDimension(R.dimen.creation_color_picker_floating_picker_grid_stroke);
        this.mSquareStroke = getContext().getResources().getDimension(R.dimen.creation_color_picker_floating_picker_square_stroke);
        this.mBorderColor = 0;
        int color = getContext().getResources().getColor(R.color.color_picker_picker_grid);
        this.mGridColor = color;
        this.mSquareColor = color;
        this.mBorderLineColor = color;
        Paint paint = new Paint();
        this.mBorderPaint = paint;
        paint.setColor(this.mBorderColor);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setStrokeWidth(this.mBorderStroke);
        Paint paint2 = new Paint();
        this.mBorderOuterLinePaint = paint2;
        paint2.setColor(this.mBorderLineColor);
        this.mBorderOuterLinePaint.setStyle(Paint.Style.STROKE);
        this.mBorderOuterLinePaint.setStrokeWidth(this.mBorderOuterLineStroke);
        Paint paint3 = new Paint();
        this.mBorderInnerLinePaint = paint3;
        paint3.setColor(this.mBorderLineColor);
        this.mBorderInnerLinePaint.setStyle(Paint.Style.STROKE);
        this.mBorderInnerLinePaint.setStrokeWidth(this.mBorderInnerLineStroke);
        Paint paint4 = new Paint();
        this.mGridPaint = paint4;
        paint4.setColor(this.mGridColor);
        this.mGridPaint.setStyle(Paint.Style.STROKE);
        this.mGridPaint.setStrokeWidth(this.mGridStroke);
        Paint paint5 = new Paint();
        this.mGridColorPaint = paint5;
        paint5.setColor(this.mBorderColor);
        this.mGridColorPaint.setStyle(Paint.Style.FILL);
        this.mGridColorPaint.setStrokeWidth(this.mGridStroke);
        Paint paint6 = new Paint();
        this.mSquarePaint = paint6;
        paint6.setColor(this.mSquareColor);
        this.mSquarePaint.setStyle(Paint.Style.STROKE);
        this.mSquarePaint.setStrokeWidth(this.mSquareStroke);
        this.clipPath = new Path();
        int[] iArr = new int[25];
        this.mColors = iArr;
        Arrays.fill(iArr, 16777215);
        this.mPickerBorder = getContext().getResources().getDimensionPixelOffset(R.dimen.creation_color_picker_floating_picker_border_width_height) - getContext().getResources().getDimensionPixelOffset(R.dimen.creation_color_picker_floating_picker_width_height);
        this.shadowColor = getContext().getResources().getColor(R.color.color_picker_picker_shadow_light, null);
        Paint paint7 = new Paint();
        this.shadowPaint = paint7;
        paint7.setColor(0);
        this.shadowPaint.setStyle(Paint.Style.STROKE);
        this.shadowPaint.setStrokeWidth(this.borderWidth);
        this.shadowPaint.setAntiAlias(true);
        this.shadowPaint.setShadowLayer(this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor);
        setLayerType(1, this.shadowPaint);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mWidth = View.MeasureSpec.getSize(i);
        this.mHeight = View.MeasureSpec.getSize(i2);
        this.mPickerWidth = View.MeasureSpec.getSize(i) - this.mPickerBorder;
        int size = View.MeasureSpec.getSize(i2);
        int i3 = this.mPickerBorder;
        this.mPickerHeight = size - i3;
        this.mPickerCenterOffset = (int) ((i3 / 2) + this.mBorderStroke);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = i;
        this.mHeight = i2;
        int i5 = this.mPickerBorder;
        this.mPickerWidth = i - i5;
        this.mPickerHeight = i2 - i5;
        this.mPickerCenterOffset = (int) ((i5 / 2) + this.mBorderStroke);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        changeLineColor(getCenterColor());
        float f = this.mGridStroke;
        canvas2.drawCircle(this.mWidth / 2.0f, this.mHeight / 2.0f, this.mPickerWidth / 2.0f, this.shadowPaint);
        this.clipPath.reset();
        this.clipPath.addCircle(this.mWidth / 2.0f, this.mHeight / 2.0f, this.mPickerWidth / 2.0f, Path.Direction.CW);
        canvas2.clipPath(this.clipPath);
        float f2 = ((this.mPickerWidth - (this.mBorderStroke * 2.0f)) - (this.mGridStroke * 6.0f)) / 5.0f;
        for (int i = 0; i < 5; i++) {
            int i2 = 0;
            while (i2 < 5) {
                int i3 = this.mPickerCenterOffset;
                float f3 = (i + 1) * f;
                int i4 = ((int) ((i * f2) + f3)) + i3;
                int i5 = i2 + 1;
                float f4 = i5 * f;
                int i6 = i3 + ((int) ((i2 * f2) + f4));
                this.mGridColorPaint.setColor(this.mColors[i2 + (i * 5)]);
                canvas2.drawRect(new Rect(i4, i6, (int) (i4 + f2 + (f3 * 2.0f)), (int) (i6 + f2 + (f4 * 2.0f))), this.mGridColorPaint);
                i2 = i5;
            }
        }
        int i7 = 0;
        while (i7 < 6) {
            int i8 = this.mPickerCenterOffset;
            int i9 = i7 + 1;
            float f5 = i9;
            float f6 = f5 * f;
            float f7 = i7 * f2;
            float f8 = f2 * 5.0f;
            canvas2.drawLine(i8 + f, i8 + f6 + f7, i8 + f + f8 + (f * 5.0f), i8 + f6 + f7, this.mGridPaint);
            int i10 = this.mPickerCenterOffset;
            float f9 = f5 * f;
            canvas2 = canvas;
            canvas2.drawLine(i10 + f9 + f7, i10 + f, i10 + f9 + f7, (f * 5.0f) + i10 + f + f8, this.mGridPaint);
            i7 = i9;
        }
        int i11 = (int) f2;
        int i12 = i11 / 2;
        int i13 = (this.mWidth / 2) - i12;
        int i14 = (this.mHeight / 2) - i12;
        float f10 = f / 2.0f;
        canvas2.drawRect(new Rect(i13, i14, (int) (i13 + i11 + f10), (int) (i11 + i14 + f10)), this.mSquarePaint);
        canvas2.drawCircle(this.mWidth / 2.0f, this.mHeight / 2.0f, (this.mPickerWidth / 2.0f) - (this.mBorderStroke / 2.0f), this.mBorderPaint);
        canvas2.drawCircle(this.mWidth / 2.0f, this.mHeight / 2.0f, ((this.mPickerWidth / 2.0f) - this.mBorderStroke) - (this.mBorderInnerLineStroke / 2.0f), this.mBorderInnerLinePaint);
        canvas2.drawCircle(this.mWidth / 2.0f, this.mHeight / 2.0f, (this.mPickerWidth / 2.0f) - (this.mBorderOuterLineStroke / 2.0f), this.mBorderOuterLinePaint);
    }

    public void setBorderColor(int i) {
        this.mBorderColor = i;
        this.mBorderPaint.setColor(i);
        invalidate();
    }

    public void setGridColor(int i) {
        this.mGridColor = i;
        this.mGridPaint.setColor(i);
        invalidate();
    }

    public void setSquareColor(int i) {
        this.mSquareColor = i;
        this.mSquarePaint.setColor(i);
        invalidate();
    }

    public void setPixelColor(int[] iArr) {
        this.mColors = iArr;
        setBorderColor(iArr[12]);
    }

    public int getCenterColor() {
        return this.mColors[12];
    }

    public int getGridWidth() {
        return (int) (((this.mPickerWidth - (this.mBorderStroke * 2.0f)) - (this.mGridStroke * 6.0f)) / 5.0f);
    }

    private void changeLineColor(int i) {
        int color = getContext().getResources().getColor(R.color.color_picker_picker_grid);
        this.shadowColor = getContext().getResources().getColor(R.color.color_picker_picker_shadow_light);
        if (!isLightColor(i)) {
            color = getContext().getResources().getColor(R.color.color_picker_picker_grid_dark);
            this.shadowColor = getContext().getResources().getColor(R.color.color_picker_picker_shadow_dark);
        }
        this.mBorderOuterLinePaint.setColor(color);
        this.mBorderInnerLinePaint.setColor(color);
        this.mGridPaint.setColor(color);
        this.mSquarePaint.setColor(color);
        this.shadowPaint.setShadowLayer(this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor);
    }

    private boolean isLightColor(int i) {
        return (((((double) Color.red(i)) * 0.299d) + (((double) Color.green(i)) * 0.587d)) + (((double) Color.blue(i)) * 0.114d)) / 255.0d >= 0.5d;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClickable(true);
    }
}
