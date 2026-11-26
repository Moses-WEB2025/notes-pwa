package com.miui.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.miui.notes.R;
import miuix.smooth.internal.SmoothDrawHelper;

/* JADX INFO: loaded from: classes2.dex */
public class RoundClipLayout extends FrameLayout {
    private SmoothDrawHelper mHelper;
    private Rect mLayer;
    private RectF mSavedLayer;
    private static final PorterDuffXfermode XFERMODE_SRC_OUT = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    private static final PorterDuffXfermode XFERMODE_DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public RoundClipLayout(Context context) {
        this(context, null);
    }

    public RoundClipLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundClipLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLayer = new Rect();
        this.mSavedLayer = new RectF();
        this.mHelper = new SmoothDrawHelper();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundClipLayout);
        setCornerRadius(typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0));
        if (typedArrayObtainStyledAttributes.hasValue(1) || typedArrayObtainStyledAttributes.hasValue(2) || typedArrayObtainStyledAttributes.hasValue(4) || typedArrayObtainStyledAttributes.hasValue(3)) {
            float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
            float dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, 0);
            float dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 0);
            float dimensionPixelSize4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(3, 0);
            setCornerRadii(new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize4});
        }
        setStrokeWidth(typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0));
        setStrokeColor(typedArrayObtainStyledAttributes.getColor(5, 0));
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setStrokeWidth(int i) {
        if (this.mHelper.getStrokeWidth() != i) {
            this.mHelper.setStrokeWidth(i);
            updateBackground();
        }
    }

    public int getStrokeWidth() {
        return this.mHelper.getStrokeWidth();
    }

    public void setStrokeColor(int i) {
        if (this.mHelper.getStrokeColor() != i) {
            this.mHelper.setStrokeColor(i);
            updateBackground();
        }
    }

    public int getStrokeColor() {
        return this.mHelper.getStrokeColor();
    }

    public void setCornerRadii(float[] fArr) {
        this.mHelper.setRadii(fArr);
        if (fArr == null) {
            this.mHelper.setRadius(0.0f);
        }
        updateBackground();
    }

    public float[] getCornerRadii() {
        if (this.mHelper.getRadii() == null) {
            return null;
        }
        return (float[]) this.mHelper.getRadii().clone();
    }

    public void setCornerRadius(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mHelper.setRadius(f);
        this.mHelper.setRadii(null);
        updateBackground();
    }

    public float getCornerRadius() {
        return this.mHelper.getRadius();
    }

    private void updateBackground() {
        updateBounds();
        invalidateOutline();
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mLayer.set(0, 0, i, i2);
        this.mSavedLayer.set(0.0f, 0.0f, i, i2);
        updateBounds();
    }

    private void updateBounds() {
        this.mHelper.onBoundsChange(this.mLayer);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (getClipChildren()) {
            int iSaveLayer = canvas.saveLayer(this.mSavedLayer, null, 31);
            super.dispatchDraw(canvas);
            this.mHelper.drawMask(canvas, XFERMODE_DST_OUT);
            canvas.restoreToCount(iSaveLayer);
            this.mHelper.drawStroke(canvas);
            return;
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (getClipChildren()) {
            int iSaveLayer = canvas.saveLayer(this.mSavedLayer, null, 31);
            super.draw(canvas);
            this.mHelper.drawMask(canvas, XFERMODE_SRC_OUT);
            canvas.restoreToCount(iSaveLayer);
            this.mHelper.drawStroke(canvas);
            return;
        }
        super.draw(canvas);
    }
}
