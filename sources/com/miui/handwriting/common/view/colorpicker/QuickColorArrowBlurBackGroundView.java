package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.miui.creation.common.R;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class QuickColorArrowBlurBackGroundView extends BlurBackGroundView {
    private Paint mPaint;
    private int mode;
    private int offset;

    public QuickColorArrowBlurBackGroundView(Context context) {
        super(context);
        this.mode = -1;
        this.offset = 0;
        init();
    }

    public QuickColorArrowBlurBackGroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mode = -1;
        this.offset = 0;
        init();
    }

    public QuickColorArrowBlurBackGroundView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mode = -1;
        this.offset = 0;
        init();
    }

    public void setOffset(int i) {
        this.offset = i;
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(getResources().getColor(R.color.color_picker_panel_stroke, null));
        this.mPaint.setStrokeWidth(0.7f);
    }

    @Override // android.view.View
    public Drawable getBackground() {
        return new ColorDrawable(0);
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo, com.miui.blur.sdk.backdrop.BlurDrawInfo
    public void getBlurOutline(Outline outline) {
        outline.setPath(getArrowPath());
    }

    public void setMode(int i) {
        this.mode = i;
    }

    private Path getArrowPath() {
        int iDip2px = DisplayUtils.dip2px(getContext(), 13.0f);
        float f = iDip2px;
        RectF rectF = new RectF(f, f, getMeasuredWidth() - iDip2px, getMeasuredHeight() - iDip2px);
        float f2 = getContext().getResources().getDisplayMetrics().density;
        int i = this.mode;
        if (i == 8) {
            return getTopArrowPath(rectF, f2);
        }
        if (i == 16) {
            return getBottomArrowPath(rectF, f2);
        }
        if (i == 32) {
            return getLeftArrowPath(rectF, f2);
        }
        if (i == 64) {
            return getRightArrowPath(rectF, f2);
        }
        return new Path();
    }

    private Path getTopArrowPath(RectF rectF, float f) {
        Path path = new Path();
        PointF pointF = new PointF(rectF.centerX() + this.offset, rectF.top);
        float f2 = rectF.top;
        PointF pointF2 = new PointF(pointF.x - (14.0f * f), pointF.y);
        PointF pointF3 = new PointF(pointF2.x + (2.2988f * f), f2);
        float f3 = f2 - (0.8772f * f);
        PointF pointF4 = new PointF(pointF2.x + (4.5169f * f), f3);
        float f4 = f2 - (2.4636f * f);
        PointF pointF5 = new PointF(pointF2.x + (6.2295f * f), f4);
        float f5 = f2 - (8.5073f * f);
        PointF pointF6 = new PointF(pointF2.x + (12.7541f * f), f5);
        float f6 = f2 - (9.1642f * f);
        PointF pointF7 = new PointF(pointF2.x + (13.4633f * f), f6);
        PointF pointF8 = new PointF(pointF2.x + (14.5367f * f), f6);
        PointF pointF9 = new PointF(pointF2.x + (15.2459f * f), f5);
        PointF pointF10 = new PointF(pointF2.x + (21.7705f * f), f4);
        PointF pointF11 = new PointF(pointF2.x + (23.4831f * f), f3);
        PointF pointF12 = new PointF(pointF2.x + (25.7012f * f), f2);
        PointF pointF13 = new PointF(pointF2.x + (28.0f * f), f2);
        path.addRoundRect(rectF, this.mRadius, this.mRadius, Path.Direction.CW);
        Path path2 = new Path();
        path2.moveTo(pointF2.x, pointF2.y);
        path2.cubicTo(pointF3.x, pointF3.y, pointF4.x, pointF4.y, pointF5.x, pointF5.y);
        path2.lineTo(pointF6.x, pointF6.y);
        path2.cubicTo(pointF7.x, pointF7.y, pointF8.x, pointF8.y, pointF9.x, pointF9.y);
        path2.lineTo(pointF10.x, pointF10.y);
        path2.cubicTo(pointF11.x, pointF11.y, pointF12.x, pointF12.y, pointF13.x, pointF13.y);
        path2.close();
        path.op(path2, Path.Op.UNION);
        return path;
    }

    private Path getLeftArrowPath(RectF rectF, float f) {
        Path path = new Path();
        float f2 = rectF.bottom;
        float f3 = rectF.top;
        float f4 = rectF.left;
        PointF pointF = new PointF(rectF.left, f3 + ((f2 - f3) / 2.0f) + this.offset);
        PointF pointF2 = new PointF(pointF.x, pointF.y + (14.0f * f));
        PointF pointF3 = new PointF(f4, pointF2.y - (2.2988f * f));
        float f5 = 0.8772f * f;
        PointF pointF4 = new PointF(pointF2.x - f5, pointF2.y - (4.5169f * f));
        float f6 = 2.4636f * f;
        PointF pointF5 = new PointF(pointF2.x - f6, pointF2.y - (6.2295f * f));
        PointF pointF6 = new PointF(pointF2.x - (8.0f * f), pointF2.y - (12.0f * f));
        float f7 = 9.1642f * f;
        PointF pointF7 = new PointF(pointF2.x - f7, pointF2.y - (13.4633f * f));
        PointF pointF8 = new PointF(pointF2.x - f7, pointF2.y - (14.5367f * f));
        PointF pointF9 = new PointF(pointF2.x - (8.5073f * f), pointF2.y - (15.2459f * f));
        PointF pointF10 = new PointF(pointF2.x - f6, pointF2.y - (21.7705f * f));
        PointF pointF11 = new PointF(pointF2.x - f5, pointF2.y - (23.4831f * f));
        float f8 = 0.0f * f;
        PointF pointF12 = new PointF(pointF2.x - f8, pointF2.y - (25.7012f * f));
        PointF pointF13 = new PointF(pointF2.x - f8, pointF2.y - (28.0f * f));
        path.addRoundRect(rectF, this.mRadius, this.mRadius, Path.Direction.CW);
        Path path2 = new Path();
        path2.moveTo(pointF2.x, pointF2.y);
        path2.cubicTo(pointF3.x, pointF3.y, pointF4.x, pointF4.y, pointF5.x, pointF5.y);
        path2.lineTo(pointF6.x, pointF6.y);
        path2.cubicTo(pointF7.x, pointF7.y, pointF8.x, pointF8.y, pointF9.x, pointF9.y);
        path2.lineTo(pointF10.x, pointF10.y);
        path2.cubicTo(pointF11.x, pointF11.y, pointF12.x, pointF12.y, pointF13.x, pointF13.y);
        path2.close();
        path.op(path2, Path.Op.UNION);
        return path;
    }

    private Path getRightArrowPath(RectF rectF, float f) {
        Path path = new Path();
        float f2 = rectF.bottom;
        float f3 = rectF.top;
        float f4 = rectF.right;
        PointF pointF = new PointF(rectF.right, f3 + ((f2 - f3) / 2.0f) + this.offset);
        PointF pointF2 = new PointF(pointF.x, pointF.y - (14.0f * f));
        PointF pointF3 = new PointF(f4, pointF2.y + (2.2988f * f));
        float f5 = 0.8772f * f;
        PointF pointF4 = new PointF(pointF2.x + f5, pointF2.y + (4.5169f * f));
        float f6 = 2.4636f * f;
        PointF pointF5 = new PointF(pointF2.x + f6, pointF2.y + (6.2295f * f));
        float f7 = 8.5073f * f;
        PointF pointF6 = new PointF(pointF2.x + f7, pointF2.y + (12.7541f * f));
        float f8 = 9.1642f * f;
        PointF pointF7 = new PointF(pointF2.x + f8, pointF2.y + (13.4633f * f));
        PointF pointF8 = new PointF(pointF2.x + f8, pointF2.y + (14.5367f * f));
        PointF pointF9 = new PointF(pointF2.x + f7, pointF2.y + (15.2459f * f));
        PointF pointF10 = new PointF(pointF2.x + f6, pointF2.y + (21.7705f * f));
        PointF pointF11 = new PointF(pointF2.x + f5, pointF2.y + (23.4831f * f));
        float f9 = 0.0f * f;
        PointF pointF12 = new PointF(pointF2.x + f9, pointF2.y + (25.7012f * f));
        PointF pointF13 = new PointF(pointF2.x + f9, pointF2.y + (28.0f * f));
        path.addRoundRect(rectF, this.mRadius, this.mRadius, Path.Direction.CW);
        Path path2 = new Path();
        path2.moveTo(pointF2.x, pointF2.y);
        path2.cubicTo(pointF3.x, pointF3.y, pointF4.x, pointF4.y, pointF5.x, pointF5.y);
        path2.lineTo(pointF6.x, pointF6.y);
        path2.cubicTo(pointF7.x, pointF7.y, pointF8.x, pointF8.y, pointF9.x, pointF9.y);
        path2.lineTo(pointF10.x, pointF10.y);
        path2.cubicTo(pointF11.x, pointF11.y, pointF12.x, pointF12.y, pointF13.x, pointF13.y);
        path2.close();
        path.op(path2, Path.Op.UNION);
        return path;
    }

    private Path getBottomArrowPath(RectF rectF, float f) {
        Path path = new Path();
        float f2 = rectF.bottom;
        float f3 = rectF.left;
        PointF pointF = new PointF(f3 + ((rectF.right - f3) / 2.0f) + this.offset, f2);
        PointF pointF2 = new PointF(pointF.x + (14.0f * f), pointF.y);
        PointF pointF3 = new PointF(pointF2.x - (2.2988f * f), pointF2.y);
        float f4 = 0.8772f * f;
        PointF pointF4 = new PointF(pointF2.x - (4.5169f * f), pointF2.y + f4);
        float f5 = 2.4636f * f;
        PointF pointF5 = new PointF(pointF2.x - (6.2295f * f), pointF2.y + f5);
        PointF pointF6 = new PointF(pointF2.x - (12.0f * f), pointF2.y + (8.0f * f));
        float f6 = 9.1642f * f;
        PointF pointF7 = new PointF(pointF2.x - (13.4633f * f), pointF2.y + f6);
        PointF pointF8 = new PointF(pointF2.x - (14.5367f * f), pointF2.y + f6);
        PointF pointF9 = new PointF(pointF2.x - (15.2459f * f), pointF2.y + (8.5073f * f));
        PointF pointF10 = new PointF(pointF2.x - (21.7705f * f), pointF2.y + f5);
        PointF pointF11 = new PointF(pointF2.x - (23.4831f * f), pointF2.y + f4);
        float f7 = 0.0f * f;
        PointF pointF12 = new PointF(pointF2.x - (25.7012f * f), pointF2.y + f7);
        PointF pointF13 = new PointF(pointF2.x - (28.0f * f), pointF2.y + f7);
        path.addRoundRect(rectF, this.mRadius, this.mRadius, Path.Direction.CW);
        Path path2 = new Path();
        path2.moveTo(pointF2.x, pointF2.y);
        path2.cubicTo(pointF3.x, pointF3.y, pointF4.x, pointF4.y, pointF5.x, pointF5.y);
        path2.lineTo(pointF6.x, pointF6.y);
        path2.cubicTo(pointF7.x, pointF7.y, pointF8.x, pointF8.y, pointF9.x, pointF9.y);
        path2.lineTo(pointF10.x, pointF10.y);
        path2.cubicTo(pointF11.x, pointF11.y, pointF12.x, pointF12.y, pointF13.x, pointF13.y);
        path2.close();
        path.op(path2, Path.Op.UNION);
        return path;
    }

    @Override // com.miui.handwriting.common.view.colorpicker.BlurBackGroundView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(getArrowPath(), this.mPaint);
    }
}
