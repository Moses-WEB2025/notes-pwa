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

/* JADX INFO: loaded from: classes4.dex */
public class RectBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private int mBitmapHeight;
    private BitmapState mBitmapState;
    private int mBitmapWidth;
    private final Rect mDstRect;
    private boolean mDstRectAndInsetsDirty;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mTargetDensity;

    public RectBitmapDrawable(Resources resources, Bitmap[] bitmapArr, Bitmap[] bitmapArr2) {
        this(new BitmapState(bitmapArr, bitmapArr2), resources);
        this.mBitmapState.mTargetDensity = this.mTargetDensity;
    }

    public RectBitmapDrawable(Resources resources, int[] iArr, int[] iArr2) {
        this(new BitmapState(decodeBitmaps(resources, iArr), decodeBitmaps(resources, iArr2)), resources);
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
        Bitmap[] bitmapArr = this.mBitmapState.mHLines;
        if (bitmapArr != null && bitmapArr.length > 0) {
            Bitmap bitmap = bitmapArr[0];
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.mPaddingLeft = i;
        this.mPaddingTop = i2;
        this.mPaddingRight = i3;
        this.mPaddingBottom = i4;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        rect.set(this.mPaddingLeft, this.mPaddingTop, this.mPaddingRight, this.mPaddingBottom);
        return true;
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
            if (this.mBitmapState.mHLines != null && this.mBitmapState.mHLines.length > 0) {
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

    public void setTileMode(int[] iArr, int[] iArr2) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTileModeX == iArr && bitmapState.mTileModeY == iArr2) {
            return;
        }
        bitmapState.mTileModeX = iArr;
        bitmapState.mTileModeY = iArr2;
        this.mDstRectAndInsetsDirty = true;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mBitmapState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.mDstRectAndInsetsDirty = true;
    }

    private void drawHorizontalLine(Canvas canvas, Paint paint, int i, int i2, int i3) {
        Bitmap[] bitmapArr = this.mBitmapState.mHLines;
        int i4 = i3 - i2;
        int[] iArr = this.mBitmapState.mTileModeX;
        int width = 0;
        int i5 = 0;
        while (width < i4) {
            Bitmap bitmap = bitmapArr[iArr[i5]];
            canvas.drawBitmap(bitmap, i2 + width, i, paint);
            width += bitmap.getWidth();
            i5++;
            if (i5 >= iArr.length) {
                i5 = 0;
            }
        }
    }

    private void drawVerticalLine(Canvas canvas, Paint paint, int i, int i2, int i3) {
        Bitmap[] bitmapArr = this.mBitmapState.mVLines;
        int i4 = i3 - i2;
        int[] iArr = this.mBitmapState.mTileModeY;
        int height = 0;
        int i5 = 0;
        while (height < i4) {
            Bitmap bitmap = bitmapArr[iArr[i5]];
            canvas.drawBitmap(bitmap, i, i2 + height, paint);
            height += bitmap.getHeight();
            i5++;
            if (i5 >= iArr.length) {
                i5 = 0;
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int alpha;
        Bitmap[] bitmapArr = this.mBitmapState.mHLines;
        Bitmap[] bitmapArr2 = this.mBitmapState.mVLines;
        if (bitmapArr == null || bitmapArr2 == null) {
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
        int i = alpha;
        updateDstRectAndInsetsIfDirty();
        int iSave = canvas.save();
        canvas.clipRect(this.mDstRect);
        drawHorizontalLine(canvas, paint, this.mDstRect.top, this.mDstRect.left, this.mDstRect.right);
        drawVerticalLine(canvas, paint, this.mDstRect.left, this.mDstRect.top + this.mBitmapState.mHLineHeight, this.mDstRect.bottom);
        drawVerticalLine(canvas, paint, this.mDstRect.right - this.mBitmapState.mVLineWidth, this.mDstRect.top - this.mBitmapState.mHLineHeight, this.mDstRect.bottom);
        drawHorizontalLine(canvas, paint, this.mDstRect.bottom - this.mBitmapState.mHLineHeight, this.mDstRect.left, this.mDstRect.right);
        canvas.restoreToCount(iSave);
        if (i >= 0) {
            paint.setAlpha(i);
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
        int mChangingConfigurations;
        Bitmap mFixedDecoration1;
        Bitmap mFixedDecoration2;
        Bitmap[] mFloatDecorations;
        int mHLineHeight;
        Bitmap[] mHLines;
        final Paint mPaint;
        int mTargetDensity;
        int[] mThemeAttrs;
        int[] mTileModeX;
        int[] mTileModeY;
        int mVLineWidth;
        Bitmap[] mVLines;

        BitmapState(Bitmap[] bitmapArr, Bitmap[] bitmapArr2) {
            this.mThemeAttrs = null;
            this.mFixedDecoration1 = null;
            this.mFixedDecoration2 = null;
            this.mFloatDecorations = null;
            this.mBaseAlpha = 1.0f;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mHLines = bitmapArr;
            this.mVLines = bitmapArr2;
            this.mHLineHeight = bitmapArr[0].getHeight();
            this.mVLineWidth = bitmapArr2[0].getWidth();
            this.mPaint = new Paint(6);
        }

        BitmapState(BitmapState bitmapState) {
            this.mThemeAttrs = null;
            this.mHLines = null;
            this.mVLines = null;
            this.mFixedDecoration1 = null;
            this.mFixedDecoration2 = null;
            this.mFloatDecorations = null;
            this.mBaseAlpha = 1.0f;
            this.mTileModeX = null;
            this.mTileModeY = null;
            this.mTargetDensity = 160;
            this.mAutoMirrored = false;
            this.mHLines = bitmapState.mHLines;
            this.mVLines = bitmapState.mVLines;
            this.mThemeAttrs = bitmapState.mThemeAttrs;
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
            this.mTileModeX = bitmapState.mTileModeX;
            this.mTileModeY = bitmapState.mTileModeY;
            this.mTargetDensity = bitmapState.mTargetDensity;
            this.mBaseAlpha = bitmapState.mBaseAlpha;
            this.mPaint = new Paint(bitmapState.mPaint);
            this.mAutoMirrored = bitmapState.mAutoMirrored;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new RectBitmapDrawable(this, (Resources) null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new RectBitmapDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    private RectBitmapDrawable(BitmapState bitmapState, Resources resources) {
        this.mDstRect = new Rect();
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
