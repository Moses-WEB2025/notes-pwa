package com.miui.notes.ai.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes4.dex */
public class RenderViewLayout extends ViewGroup {
    float mChildScale;

    public RenderViewLayout(Context context) {
        super(context);
        this.mChildScale = 0.5f;
    }

    public RenderViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mChildScale = 0.5f;
    }

    public RenderViewLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildScale = 0.5f;
    }

    public RenderViewLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChildScale = 0.5f;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        int iCeil = (int) Math.ceil(getWidth() * this.mChildScale);
        int iCeil2 = (int) Math.ceil(getHeight() * this.mChildScale);
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                childAt.setScaleX(1.0f / this.mChildScale);
                childAt.setScaleY(1.0f / this.mChildScale);
                int width = (int) ((getWidth() - iCeil) * 0.5f);
                int height = (int) ((getHeight() - iCeil2) * 0.5f);
                childAt.layout(width, height, width + iCeil, height + iCeil2);
            }
        }
    }

    public void attachView(View view, float f) {
        this.mChildScale = f;
        addView(view);
        if (view instanceof TextureView) {
            return;
        }
        view.setBackgroundColor(0);
    }

    public int getInternalWidth() {
        return (int) (getWidth() * this.mChildScale);
    }

    public int getInternalHeight() {
        return (int) (getHeight() * this.mChildScale);
    }
}
