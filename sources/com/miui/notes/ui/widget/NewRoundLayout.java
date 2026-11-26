package com.miui.notes.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* JADX INFO: loaded from: classes4.dex */
public class NewRoundLayout extends LinearLayout {
    private boolean mIsClip;
    private float[] mRadii;
    private RectF mRectF;
    private Path mRoundPath;
    private float mRoundRadius;

    public NewRoundLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundRadius = 36.0f;
    }

    private void initRoundPath() {
        if (this.mRoundPath == null) {
            Path path = new Path();
            this.mRoundPath = path;
            float[] fArr = this.mRadii;
            if (fArr != null) {
                if (fArr.length == 1) {
                    RectF rectF = this.mRectF;
                    float f = fArr[0];
                    path.addRoundRect(rectF, f, f, Path.Direction.CW);
                    this.mIsClip = true;
                    return;
                }
                if (fArr.length == 8) {
                    path.addRoundRect(this.mRectF, fArr, Path.Direction.CW);
                    this.mIsClip = true;
                    return;
                } else {
                    this.mIsClip = false;
                    return;
                }
            }
            RectF rectF2 = this.mRectF;
            float f2 = this.mRoundRadius;
            path.addRoundRect(rectF2, f2, f2, Path.Direction.CW);
            this.mIsClip = true;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        RectF rectF = this.mRectF;
        if (rectF == null) {
            RectF rectF2 = new RectF();
            this.mRectF = rectF2;
            rectF2.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
        } else {
            if (rectF.bottom == getMeasuredHeight() && this.mRectF.right == getMeasuredWidth()) {
                return;
            }
            this.mRectF.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
            this.mRoundPath = null;
        }
    }

    public void setRoundRadius(float[] fArr) {
        if (fArr != null) {
            this.mRadii = fArr;
            this.mRoundPath = null;
            invalidate();
            return;
        }
        this.mRadii = new float[0];
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        initRoundPath();
        super.draw(canvas);
    }
}
