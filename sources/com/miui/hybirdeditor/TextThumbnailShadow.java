package com.miui.hybirdeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;
import defpackage.R2;

/* JADX INFO: loaded from: classes3.dex */
public class TextThumbnailShadow extends View.DragShadowBuilder {
    private boolean isDefaultTheme;
    private RectF mBound;
    private RectF mBoundBorder;
    private float mBoundHeight;
    private Paint mBoundPaint;
    private final float mBoundRadius;
    private float mBoundWidth;
    private float mHeight;
    private final float mPaddingStart;
    private final float mPaddingTop;
    private final float mShadowLayerRadius;
    private final float mShadowLayerX;
    private final float mShadowLayerY;
    private final float mShadowWidth;
    private StaticLayout mStaticLayout;
    private Paint mStrokePaint;
    private final float mStrokeWidth;
    private TextPaint mTextPaint;
    private final float mTextSizePx;
    private int mTextWidth;
    private float mWidth;
    private final float maxStaticLayoutWidth;

    public TextThumbnailShadow(Context context, String str, Theme theme) {
        float dimension = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_padding_start);
        this.mPaddingStart = dimension;
        float dimension2 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_padding_top);
        this.mPaddingTop = dimension2;
        float dimension3 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_shadow_layer_x);
        this.mShadowLayerX = dimension3;
        float dimension4 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_shadow_layer_y);
        this.mShadowLayerY = dimension4;
        float dimension5 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_text_size);
        this.mTextSizePx = dimension5;
        float dimension6 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_static_layout_max_width);
        this.maxStaticLayoutWidth = dimension6;
        this.mBoundRadius = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_radius);
        float dimension7 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_stroke_width);
        this.mStrokeWidth = dimension7;
        float dimension8 = NoteApp.getInstance().getResources().getDimension(R.dimen.text_drag_shadow_shadow_layer_radius);
        this.mShadowLayerRadius = dimension8;
        float f = 1.5f * dimension8;
        this.mShadowWidth = f;
        this.isDefaultTheme = false;
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setTextSize(dimension5);
        this.mTextPaint.setAlpha(204);
        if (theme.getId() == 0) {
            this.isDefaultTheme = true;
            this.mTextPaint.setColor(context.getColor(R.color.text_drag_shadow_text_color));
        } else {
            this.isDefaultTheme = false;
            this.mTextPaint.setColor(context.getColor(theme.getEditorStyle(context).mTextColor));
        }
        Paint paint = new Paint();
        this.mBoundPaint = paint;
        if (this.isDefaultTheme) {
            paint.setColor(context.getColor(R.color.text_drag_shadow_background));
        }
        this.mBoundPaint.setStyle(Paint.Style.FILL);
        this.mBoundPaint.setShadowLayer(dimension8, dimension3, dimension4, context.getColor(R.color.text_drag_shadow_shadow_layer_color));
        Paint paint2 = new Paint();
        this.mStrokePaint = paint2;
        if (this.isDefaultTheme) {
            paint2.setColor(context.getColor(R.color.text_drag_shadow_stroke_color));
        }
        this.mStrokePaint.setColor(context.getColor(R.color.text_drag_shadow_stroke_color));
        this.mStrokePaint.setStrokeWidth(dimension7);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mTextWidth = (int) Math.min(this.mTextPaint.measureText(str, 0, str.length()), dimension6);
        StaticLayout staticLayoutBuild = StaticLayout.Builder.obtain(str, 0, str.length(), this.mTextPaint, this.mTextWidth).setMaxLines(3).setEllipsize(TextUtils.TruncateAt.END).build();
        this.mStaticLayout = staticLayoutBuild;
        this.mBoundWidth = this.mTextWidth + (dimension * 2.0f);
        this.mBoundHeight = staticLayoutBuild.getHeight() + (dimension2 * 2.0f);
        RectF rectF = new RectF();
        this.mBound = rectF;
        rectF.set(0.0f, 0.0f, this.mBoundWidth, this.mBoundHeight);
        RectF rectF2 = new RectF();
        this.mBoundBorder = rectF2;
        rectF2.set(dimension7 / 2.0f, dimension7 / 2.0f, this.mBoundWidth - (dimension7 / 2.0f), this.mBoundHeight - (dimension7 / 2.0f));
        this.mWidth = this.mBoundWidth + (f * 2.0f);
        this.mHeight = this.mBoundHeight + (f * 2.0f);
    }

    @Override // android.view.View.DragShadowBuilder
    public void onProvideShadowMetrics(Point point, Point point2) {
        point.set((int) this.mWidth, (int) this.mHeight);
        point2.set((int) ((this.mWidth / 2.0f) - this.mShadowLayerX), (int) ((this.mHeight / 2.0f) - this.mShadowLayerY));
    }

    @Override // android.view.View.DragShadowBuilder
    public void onDrawShadow(Canvas canvas) {
        canvas.save();
        float f = this.mShadowWidth;
        canvas.translate(f - this.mShadowLayerX, f - this.mShadowLayerY);
        if (this.isDefaultTheme) {
            RectF rectF = this.mBound;
            float f2 = this.mBoundRadius;
            canvas.drawRoundRect(rectF, f2, f2, this.mBoundPaint);
            RectF rectF2 = this.mBoundBorder;
            float f3 = this.mBoundRadius;
            float f4 = this.mStrokeWidth;
            canvas.drawRoundRect(rectF2, f3 - (f4 / 2.0f), f3 - (f4 / 2.0f), this.mStrokePaint);
        }
        canvas.translate(this.mPaddingStart, this.mPaddingTop);
        this.mStaticLayout.draw(canvas);
        canvas.restore();
    }

    public void setAlpha() {
        if (this.isDefaultTheme) {
            this.mBoundPaint.setAlpha(R2.array.calc_handwrite_device_whitelist);
            this.mStrokePaint.setAlpha(R2.array.calc_handwrite_device_whitelist);
        }
        this.mTextPaint.setAlpha(127);
    }
}
