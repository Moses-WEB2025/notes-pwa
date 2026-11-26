package com.miui.common.anim;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.miui.common.tool.Utils;

/* JADX INFO: loaded from: classes2.dex */
public class AudioCreatePanelOutlineProvider extends ViewOutlineProvider {
    private float mProgress;
    private int mCenterX = 0;
    private int mCenterY = 0;
    private int mStartRadius = 0;
    private int mEndRadius = 0;
    private int mDifRadius = 0;
    private int mStartSize = 0;
    private final Rect mDrawRect = new Rect();

    public void setProgress(float f) {
        this.mProgress = f;
    }

    public void setStartPoint(int i, int i2) {
        this.mCenterX = i;
        this.mCenterY = i2;
    }

    public void setRadius(int i, int i2) {
        this.mStartRadius = i;
        this.mEndRadius = i2;
        this.mDifRadius = i2 - i;
        this.mStartSize = i;
    }

    public Rect getDrawRect() {
        return this.mDrawRect;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004f A[PHI: r10
  0x004f: PHI (r10v18 int) = (r10v13 int), (r10v21 int) binds: [B:18:0x004c, B:15:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        int i;
        int width = view.getWidth();
        int height = view.getHeight();
        int i2 = this.mCenterY;
        float f = this.mProgress;
        int i3 = this.mStartSize;
        int i4 = ((int) (i2 * (1.0f - f))) - i3;
        if (i4 < 0) {
            i4 = 0;
        }
        int i5 = (i2 + i2) - i4;
        int i6 = this.mEndRadius;
        if (i5 > height + i6) {
            i5 = height + i6;
        }
        int i7 = ((int) (this.mCenterX * (1.0f - f))) - i3;
        int i8 = i7 >= 0 ? i7 : 0;
        if (!Utils.isRTL()) {
            int i9 = this.mCenterX;
            i = (i9 + i9) - i8;
            if (i <= width) {
                width = i;
            }
        } else {
            int i10 = this.mCenterX;
            int i11 = this.mStartSize;
            i = (int) (i10 + i11 + (((width - i10) - i11) * this.mProgress));
            if (i <= width) {
                width = i;
            }
        }
        this.mDrawRect.set(i8, i4, width, i5);
        outline.setRoundRect(this.mDrawRect, this.mStartRadius + ((int) (this.mDifRadius * this.mProgress)));
    }
}
