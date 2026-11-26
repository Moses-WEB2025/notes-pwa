package com.miui.notes.theme.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;

/* JADX INFO: loaded from: classes4.dex */
public class DecorationDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private int mBitmapHeight;
    private BitmapState mBitmapState;
    private int mBitmapWidth;
    private final Rect mDstRect;
    private boolean mDstRectAndInsetsDirty;
    private int mTargetDensity;
    private final Rect mTmpRect;

    public DecorationDrawable(Resources resources, Bitmap[] bitmapArr) {
        this(new BitmapState(bitmapArr), resources);
        this.mBitmapState.mTargetDensity = this.mTargetDensity;
    }

    public DecorationDrawable(Resources resources, int[] iArr) {
        this(new BitmapState(decodeBitmaps(resources, iArr)), resources);
        this.mBitmapState.mTargetDensity = this.mTargetDensity;
    }

    private static Bitmap[] decodeBitmaps(Resources resources, int[] iArr) {
        Bitmap[] bitmapArr = new Bitmap[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            bitmapArr[i] = BitmapFactory.decodeResource(resources, iArr[i]);
        }
        return bitmapArr;
    }

    public final Paint getPaint() {
        return this.mBitmapState.mPaint;
    }

    private void computeBitmapSize() {
        Bitmap[] bitmapArr = this.mBitmapState.mBitmaps;
        if (bitmapArr != null && bitmapArr.length > 0) {
            Bitmap bitmap = bitmapArr[0];
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
        }
    }

    public void setTargetDensity(Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTargetDensity(int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = 160;
            }
            this.mTargetDensity = i;
            if (this.mBitmapState.mBitmaps != null && this.mBitmapState.mBitmaps.length > 0) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
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
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTileMode != iArr) {
            bitmapState.mTileMode = iArr;
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
        Bitmap[] bitmapArr = this.mBitmapState.mBitmaps;
        if (bitmapArr == null) {
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
        int[] iArr = this.mBitmapState.mTileMode;
        for (int i = 0; i < iArr.length; i += 4) {
            Bitmap bitmap = bitmapArr[iArr[i]];
            int i2 = iArr[i + 1];
            if (i2 == 0) {
                canvas.drawBitmap(bitmap, this.mDstRect.left + ((this.mDstRect.width() * iArr[i + 2]) / 100), this.mDstRect.top + ((this.mDstRect.height() * iArr[i + 3]) / 100), paint);
            } else {
                Gravity.apply(i2, bitmap.getWidth(), bitmap.getHeight(), this.mDstRect, this.mTmpRect);
                this.mTmpRect.offset(-iArr[i + 2], iArr[i + 3]);
                canvas.drawBitmap(bitmap, this.mTmpRect.left, this.mTmpRect.top, paint);
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
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
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
        Bitmap[] mBitmaps;
        int mChangingConfigurations;
        final Paint mPaint;
        int mTargetDensity;
        int[] mThemeAttrs;
        int[] mTileMode;

        BitmapState(Bitmap[] bitmapArr) {
            this.mThemeAttrs = null;
            this.mBaseAlpha = 1.0f;
            this.mTileMode = null;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mBitmaps = bitmapArr;
            this.mPaint = new Paint(6);
        }

        BitmapState(BitmapState bitmapState) {
            this.mThemeAttrs = null;
            this.mBitmaps = null;
            this.mBaseAlpha = 1.0f;
            this.mTileMode = null;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mThemeAttrs = bitmapState.mThemeAttrs;
            this.mBitmaps = bitmapState.mBitmaps;
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
            this.mTileMode = bitmapState.mTileMode;
            this.mTargetDensity = bitmapState.mTargetDensity;
            this.mBaseAlpha = bitmapState.mBaseAlpha;
            this.mPaint = new Paint(bitmapState.mPaint);
            this.mAutoMirrored = bitmapState.mAutoMirrored;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new DecorationDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new DecorationDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    private DecorationDrawable(BitmapState bitmapState, Resources resources) {
        this.mDstRect = new Rect();
        this.mTmpRect = new Rect();
        this.mTargetDensity = 160;
        this.mDstRectAndInsetsDirty = true;
        this.mBitmapState = bitmapState;
        updateLocalState(resources);
    }

    private void updateLocalState(Resources resources) {
        if (resources != null) {
            int i = resources.getDisplayMetrics().densityDpi;
            if (i == 0) {
                i = 160;
            }
            this.mTargetDensity = i;
        } else {
            this.mTargetDensity = this.mBitmapState.mTargetDensity;
        }
        computeBitmapSize();
    }
}
