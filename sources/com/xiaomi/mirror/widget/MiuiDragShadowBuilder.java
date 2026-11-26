package com.xiaomi.mirror.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.mirror.opensdk.R;

/* JADX INFO: loaded from: classes4.dex */
public class MiuiDragShadowBuilder extends View.DragShadowBuilder {
    public static String TAG = "MiuiDragShadowBuilder";
    public float mAlpha;
    public Attributes mAttrs;
    public Rect mBound;
    public Context mContext;
    public int mCount;
    public Rect mDest;
    public Bitmap mThumb;

    public static class Attributes {
        public int thumbnailCountInColor;
        public int thumbnailCountOutColor;
        public int thumbnailShadowLayerColor;
        public int thumbnailShadowLayerRadius;
        public int thumbnailShadowLayerX;
        public int thumbnailShadowLayerY;
    }

    public MiuiDragShadowBuilder(Context context) {
        this(context, null);
    }

    public MiuiDragShadowBuilder(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MiuiDragShadowBuilder(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.MiuiDragShadowStyle);
    }

    public MiuiDragShadowBuilder(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mAlpha = 1.0f;
        this.mContext = context;
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.MiuiDragShadow, i, i2);
        Attributes attributes = new Attributes();
        this.mAttrs = attributes;
        attributes.thumbnailShadowLayerRadius = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiDragShadow_thumbnailShadowLayerRadius, 150);
        this.mAttrs.thumbnailShadowLayerX = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiDragShadow_thumbnailShadowLayerX, 0);
        this.mAttrs.thumbnailShadowLayerY = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuiDragShadow_thumbnailShadowLayerY, 18);
        this.mAttrs.thumbnailShadowLayerColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MiuiDragShadow_thumbnailShadowLayerColor, 687865856);
        this.mAttrs.thumbnailCountOutColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MiuiDragShadow_thumbnailCountOutColor, -1);
        this.mAttrs.thumbnailCountInColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MiuiDragShadow_thumbnailCountInColor, -15891201);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void computeIfNeeded() {
        int iDp2px;
        int i;
        if (this.mBound == null || this.mDest == null) {
            this.mBound = new Rect();
            this.mDest = new Rect();
            float width = this.mThumb.getWidth() / this.mThumb.getHeight();
            if (width > 1.0f) {
                iDp2px = dp2px(this.mContext, 100.0f);
                i = (int) (iDp2px * width);
            } else {
                int iDp2px2 = dp2px(this.mContext, 100.0f);
                iDp2px = (int) (iDp2px2 / width);
                i = iDp2px2;
            }
            this.mDest.set(0, 0, i, iDp2px);
            Attributes attributes = this.mAttrs;
            int i2 = attributes.thumbnailShadowLayerRadius;
            if (i2 == 0) {
                this.mBound.set(0, 0, i, iDp2px);
                return;
            }
            Rect rect = this.mBound;
            int iMin = Math.min(0, attributes.thumbnailShadowLayerX - i2);
            Attributes attributes2 = this.mAttrs;
            int iMin2 = Math.min(0, attributes2.thumbnailShadowLayerY - attributes2.thumbnailShadowLayerRadius);
            Attributes attributes3 = this.mAttrs;
            int iMax = Math.max(i, attributes3.thumbnailShadowLayerX + i + attributes3.thumbnailShadowLayerRadius);
            Attributes attributes4 = this.mAttrs;
            rect.set(iMin, iMin2, iMax, Math.max(iDp2px, attributes4.thumbnailShadowLayerY + iDp2px + attributes4.thumbnailShadowLayerRadius));
        }
    }

    @Deprecated
    public void build() {
    }

    public int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View.DragShadowBuilder
    public void onDrawShadow(Canvas canvas) {
        RectF rectF;
        RectF rectF2;
        computeIfNeeded();
        canvas.save();
        Rect rect = this.mBound;
        canvas.translate(-rect.left, -rect.top);
        Paint paint = new Paint();
        paint.setColor(0);
        paint.setShadowLayer(dp2px(this.mContext, 32.0f), 0.0f, dp2px(this.mContext, 16.0f), this.mAttrs.thumbnailShadowLayerColor);
        canvas.drawRect(this.mDest, paint);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setAlpha((int) (this.mAlpha * 255.0f));
        canvas.drawBitmap(this.mThumb, (Rect) null, this.mDest, paint2);
        int i = this.mCount;
        if (i > 1) {
            String string = Integer.toString(i);
            int i2 = this.mCount;
            if (i2 < 10) {
                rectF = new RectF(0.0f, 0.0f, dp2px(this.mContext, 32.0f), dp2px(this.mContext, 32.0f));
                rectF2 = new RectF(dp2px(this.mContext, 2.0f), dp2px(this.mContext, 2.0f), dp2px(this.mContext, 30.0f), dp2px(this.mContext, 30.0f));
            } else if (i2 < 100) {
                rectF = new RectF(0.0f, 0.0f, dp2px(this.mContext, 46.0f), dp2px(this.mContext, 32.0f));
                rectF2 = new RectF(dp2px(this.mContext, 2.0f), dp2px(this.mContext, 2.0f), dp2px(this.mContext, 44.0f), dp2px(this.mContext, 30.0f));
            } else {
                rectF = new RectF(0.0f, 0.0f, dp2px(this.mContext, 56.0f), dp2px(this.mContext, 32.0f));
                rectF2 = new RectF(dp2px(this.mContext, 2.0f), dp2px(this.mContext, 2.0f), dp2px(this.mContext, 54.0f), dp2px(this.mContext, 30.0f));
                string = "99+";
            }
            canvas.translate(this.mDest.width() - (rectF.width() / 2.0f), (-rectF.height()) / 2.0f);
            Paint paint3 = new Paint();
            paint3.setColor(this.mAttrs.thumbnailCountOutColor);
            canvas.drawRoundRect(rectF, dp2px(this.mContext, 16.0f), dp2px(this.mContext, 16.0f), paint3);
            paint3.setColor(this.mAttrs.thumbnailCountInColor);
            canvas.drawRoundRect(rectF2, dp2px(this.mContext, 15.0f), dp2px(this.mContext, 15.0f), paint3);
            Rect rect2 = new Rect();
            Paint paint4 = new Paint();
            paint4.setColor(this.mAttrs.thumbnailCountOutColor);
            paint4.setTextSize(dp2px(this.mContext, 16.0f));
            paint4.getTextBounds(string, 0, string.length(), rect2);
            canvas.drawText(string, (rectF.centerX() - (rect2.width() / 2)) - 2.0f, rectF.centerY() + (rect2.height() / 2), paint4);
        }
        canvas.restore();
    }

    @Override // android.view.View.DragShadowBuilder
    public void onProvideShadowMetrics(Point point, Point point2) {
        computeIfNeeded();
        point.set(this.mBound.width() > 0 ? this.mBound.width() : 1, this.mBound.height() > 0 ? this.mBound.height() : 1);
        point2.set(this.mDest.centerX() - this.mBound.left, this.mDest.centerY() - this.mBound.top);
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public void setCount(int i) {
        this.mCount = i;
    }

    public void setThumb(Bitmap bitmap) {
        this.mThumb = bitmap;
    }
}
