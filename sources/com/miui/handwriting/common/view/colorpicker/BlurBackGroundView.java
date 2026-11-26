package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.miui.blur.sdk.backdrop.BackdropBlurView;
import com.miui.blur.sdk.backdrop.BlurStyle;
import com.miui.creation.common.R;

/* JADX INFO: loaded from: classes3.dex */
public class BlurBackGroundView extends BackdropBlurView {
    private static final int BULRRADIUS = 150;
    protected float mRadius;
    private Paint mStrokePaint;
    public static final BlurStyle BLUR_LIGHT = new BlurStyle.Builder().setBlurRadius(150).addBlendLayer(-1426063361, BlendMode.COLOR_DODGE).addBlendLayer(-1997344014, null).build();
    private static final BlurStyle DEFAULT_DARK = new BlurStyle.Builder().setBlurRadius(150).addBlendLayer(-1441722095, BlendMode.COLOR_BURN).addBlendLayer(1428563494, null).build();

    public BlurBackGroundView(Context context) {
        super(context);
        init(context);
    }

    public BlurBackGroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public BlurBackGroundView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mRadius = context.getResources().getDimension(R.dimen.creation_color_picker_background_radius);
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        paint.reset();
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setColor(getResources().getColor(R.color.color_picker_panel_stroke, null));
        this.mStrokePaint.setStrokeWidth(0.1f);
        setBlurRadius(150);
        setCornerRadius(this.mRadius);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPanelStroke(canvas, this.mStrokePaint);
    }

    private void drawPanelStroke(Canvas canvas, Paint paint) {
        RectF rectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
        float f = this.mRadius;
        canvas.drawRoundRect(rectF, f, f, paint);
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo
    public BlurStyle getBlurStyleDayMode() {
        return BLUR_LIGHT;
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo
    public BlurStyle getBlurStyleNightMode() {
        return DEFAULT_DARK;
    }
}
