package com.miui.notes.theme.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

/* JADX INFO: loaded from: classes4.dex */
public class ArtyBackgroundDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private BitmapState mBitmapState;
    private final Rect mDstRect;
    private boolean mDstRectAndInsetsDirty;
    private int mTargetDensity;
    int[] mTileMode;
    private Drawable[] mTiles;
    private final Rect mTmpRect;

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return -1;
    }

    public ArtyBackgroundDrawable(Resources resources, int[] iArr) {
        this(new BitmapState(), resources);
        this.mTiles = getDrawables(resources, iArr);
        this.mBitmapState.mTargetDensity = this.mTargetDensity;
    }

    private static Drawable[] getDrawables(Resources resources, int[] iArr) {
        Drawable[] drawableArr = new Drawable[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            drawableArr[i] = resources.getDrawable(iArr[i]);
        }
        return drawableArr;
    }

    public final Paint getPaint() {
        return this.mBitmapState.mPaint;
    }

    public void setAntiAlias(boolean z) {
        this.mBitmapState.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public boolean hasAntiAlias() {
        return this.mBitmapState.mPaint.isAntiAlias();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mBitmapState.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isFilterBitmap() {
        return this.mBitmapState.mPaint.isFilterBitmap();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mBitmapState.mPaint.setDither(z);
        invalidateSelf();
    }

    public void setTileMode(int[] iArr) {
        if (this.mTileMode != iArr) {
            this.mTileMode = iArr;
            this.mDstRectAndInsetsDirty = true;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mBitmapState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.mDstRectAndInsetsDirty = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int alpha;
        Drawable[] drawableArr = this.mTiles;
        if (drawableArr == null) {
            return;
        }
        BitmapState bitmapState = this.mBitmapState;
        Paint paint = bitmapState.mPaint;
        if (bitmapState.mBaseAlpha != 1.0f) {
            Paint paint2 = getPaint();
            alpha = paint2.getAlpha();
            paint2.setAlpha((int) ((alpha * bitmapState.mBaseAlpha) + 0.5f));
        } else {
            alpha = -1;
        }
        updateDstRectAndInsetsIfDirty();
        int iHeight = this.mDstRect.height();
        int[] iArr = this.mTileMode;
        Rect rect = this.mTmpRect;
        int intrinsicHeight = 0;
        int i = 0;
        while (intrinsicHeight < iHeight) {
            Drawable drawable = drawableArr[iArr[i]];
            if (iHeight - intrinsicHeight < drawable.getIntrinsicHeight()) {
                break;
            }
            Gravity.apply(iArr[i + 1] | 48, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), this.mDstRect, rect);
            rect.offset(0, intrinsicHeight);
            drawable.setBounds(rect);
            drawable.draw(canvas);
            intrinsicHeight += drawable.getIntrinsicHeight();
            i += 2;
            if (i >= iArr.length) {
                i = 0;
            }
        }
        if (alpha >= 0) {
            paint.setAlpha(alpha);
        }
    }

    private void updateDstRectAndInsetsIfDirty() {
        if (this.mDstRectAndInsetsDirty) {
            copyBounds(this.mDstRect);
        }
        this.mDstRectAndInsetsDirty = false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mBitmapState.mPaint.getAlpha()) {
            this.mBitmapState.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mBitmapState.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mBitmapState.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mBitmapState.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mBitmapState.mPaint.getAlpha() < 255 ? -3 : -1;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        this.mBitmapState.mChangingConfigurations |= getChangingConfigurations();
        return this.mBitmapState;
    }

    static final class BitmapState extends Drawable.ConstantState {
        boolean mAutoMirrored;
        float mBaseAlpha;
        int mChangingConfigurations;
        final Paint mPaint;
        int mTargetDensity;
        int[] mThemeAttrs;

        BitmapState() {
            this.mThemeAttrs = null;
            this.mBaseAlpha = 1.0f;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mPaint = new Paint(6);
        }

        BitmapState(BitmapState bitmapState) {
            this.mThemeAttrs = null;
            this.mBaseAlpha = 1.0f;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mThemeAttrs = bitmapState.mThemeAttrs;
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
            this.mTargetDensity = bitmapState.mTargetDensity;
            this.mBaseAlpha = bitmapState.mBaseAlpha;
            this.mPaint = new Paint(bitmapState.mPaint);
            this.mAutoMirrored = bitmapState.mAutoMirrored;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ArtyBackgroundDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new ArtyBackgroundDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    private ArtyBackgroundDrawable(BitmapState bitmapState, Resources resources) {
        this.mDstRect = new Rect();
        this.mTmpRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mTileMode = null;
        this.mBitmapState = bitmapState;
    }
}
