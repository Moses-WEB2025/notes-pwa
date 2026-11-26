package com.miui.pad.feature.notes.filemanager;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseSpaceAroundDecoration extends RecyclerView.ItemDecoration {
    private int mAvgUsedWidth;
    protected int[] mEndInset;
    private int mMarginMiddle;
    private int mMarginSide;
    protected int mSpanCount;

    public abstract int getSpanIndex(View view, RecyclerView recyclerView, int i);

    public abstract boolean isTargetView(View view, RecyclerView recyclerView, int i);

    public void onGetItemOffsetsFinished(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state, int i, int i2) {
    }

    public BaseSpaceAroundDecoration(int i, int i2, int i3) {
        this.mMarginSide = i;
        this.mMarginMiddle = i2;
        this.mSpanCount = i3;
        this.mEndInset = new int[i3];
        this.mAvgUsedWidth = ((i2 * (i3 - 1)) + (i * 2)) / i3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        if (childAdapterPosition == -1) {
            Object tag = view.getTag();
            if (tag instanceof Rect) {
                rect.set((Rect) tag);
                return;
            }
            return;
        }
        if (isTargetView(view, recyclerView, childAdapterPosition)) {
            boolean z = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
            int spanIndex = getSpanIndex(view, recyclerView, childAdapterPosition);
            if (spanIndex == 0) {
                if (z) {
                    rect.right = this.mMarginSide;
                    rect.left = this.mAvgUsedWidth - this.mMarginSide;
                    this.mEndInset[spanIndex] = rect.left;
                } else {
                    rect.left = this.mMarginSide;
                    rect.right = this.mAvgUsedWidth - rect.left;
                    this.mEndInset[spanIndex] = rect.right;
                }
            } else if (z) {
                rect.right = this.mMarginMiddle - this.mEndInset[spanIndex - 1];
                rect.left = this.mAvgUsedWidth - rect.right;
                this.mEndInset[spanIndex] = rect.left;
            } else {
                rect.left = this.mMarginMiddle - this.mEndInset[spanIndex - 1];
                rect.right = this.mAvgUsedWidth - rect.left;
                this.mEndInset[spanIndex] = rect.right;
            }
            onGetItemOffsetsFinished(rect, view, recyclerView, state, childAdapterPosition, spanIndex);
            view.setTag(new Rect(rect));
        }
    }
}
