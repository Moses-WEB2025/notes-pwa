package com.miui.maml;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class MamlDrawable extends Drawable {
    private static WeakReference<Drawable> sLayerBadgeDrawableBmpRef;
    protected Drawable mBadgeDrawable;
    protected Rect mBadgeLocation;
    protected ColorFilter mColorFilter;
    protected int mHeight;
    protected int mIntrinsicHeight;
    protected int mIntrinsicWidth;
    protected Runnable mInvalidateSelf = new Runnable() { // from class: com.miui.maml.MamlDrawable.1
        @Override // java.lang.Runnable
        public void run() {
            MamlDrawable.this.invalidateSelf();
        }
    };
    protected MamlDrawableState mState;
    protected int mWidth;

    public void cleanUp() {
    }

    protected void drawIcon(Canvas canvas) {
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public void setBadgeInfo(Drawable drawable, Rect rect) {
        if (rect != null && (rect.left < 0 || rect.top < 0 || rect.width() > this.mIntrinsicWidth || rect.height() > this.mIntrinsicHeight)) {
            throw new IllegalArgumentException("Badge location " + rect + " not in badged drawable bounds " + new Rect(0, 0, this.mIntrinsicWidth, this.mIntrinsicHeight));
        }
        if (drawable instanceof LayerDrawable) {
            WeakReference<Drawable> weakReference = sLayerBadgeDrawableBmpRef;
            Drawable drawable2 = weakReference != null ? weakReference.get() : null;
            if (drawable2 != null) {
                drawable = drawable2.mutate();
            } else {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                drawable = new BitmapDrawable(bitmapCreateBitmap);
                sLayerBadgeDrawableBmpRef = new WeakReference<>(drawable);
            }
        }
        ColorFilter colorFilter = this.mColorFilter;
        if (colorFilter != null && drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        this.mBadgeDrawable = drawable;
        this.mBadgeLocation = rect;
        this.mState.mStateBadgeDrawable = drawable;
        this.mState.mStateBadgeLocation = rect;
    }

    public void setIntrinsicSize(int i, int i2) {
        this.mIntrinsicWidth = i;
        this.mIntrinsicHeight = i2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.mWidth = i3 - i;
        this.mHeight = i4 - i2;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        drawIcon(canvas);
        try {
            Drawable drawable = this.mBadgeDrawable;
            if (drawable != null) {
                Rect rect = this.mBadgeLocation;
                if (rect != null) {
                    drawable.setBounds(0, 0, rect.width(), this.mBadgeLocation.height());
                    canvas.save();
                    canvas.translate(this.mBadgeLocation.left, this.mBadgeLocation.top);
                    this.mBadgeDrawable.draw(canvas);
                    canvas.restore();
                    return;
                }
                drawable.setBounds(0, 0, this.mIntrinsicWidth, this.mIntrinsicHeight);
                this.mBadgeDrawable.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
    }

    protected void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    public static class MamlDrawableState extends Drawable.ConstantState {
        protected Drawable mStateBadgeDrawable;
        protected Rect mStateBadgeLocation;

        protected MamlDrawable createDrawable() {
            return null;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            MamlDrawable mamlDrawableCreateDrawable = createDrawable();
            if (mamlDrawableCreateDrawable == null) {
                return null;
            }
            Drawable drawable = this.mStateBadgeDrawable;
            mamlDrawableCreateDrawable.setBadgeInfo(drawable != null ? drawable.mutate() : null, this.mStateBadgeLocation != null ? new Rect(this.mStateBadgeLocation.left, this.mStateBadgeLocation.top, this.mStateBadgeLocation.right, this.mStateBadgeLocation.bottom) : null);
            return mamlDrawableCreateDrawable;
        }
    }
}
