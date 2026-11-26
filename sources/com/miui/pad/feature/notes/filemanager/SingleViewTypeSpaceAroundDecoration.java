package com.miui.pad.feature.notes.filemanager;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class SingleViewTypeSpaceAroundDecoration extends BaseSpaceAroundDecoration {
    protected int mHeadMarginTop;
    protected int mMarginBottom;
    protected int mMarginTop;
    protected int mTailMarginBottom;
    protected int spanCount;

    @Override // com.miui.pad.feature.notes.filemanager.BaseSpaceAroundDecoration
    public boolean isTargetView(View view, RecyclerView recyclerView, int i) {
        return true;
    }

    public SingleViewTypeSpaceAroundDecoration(int i, int i2, int i3) {
        super(i, i2, i3);
        this.spanCount = i3;
    }

    @Override // com.miui.pad.feature.notes.filemanager.BaseSpaceAroundDecoration
    public int getSpanIndex(View view, RecyclerView recyclerView, int i) {
        return i % this.spanCount;
    }

    @Override // com.miui.pad.feature.notes.filemanager.BaseSpaceAroundDecoration
    public void onGetItemOffsetsFinished(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state, int i, int i2) {
        int iCeil = (int) Math.ceil((((double) recyclerView.getAdapter().getItemCount()) * 1.0d) / ((double) this.mSpanCount));
        int iCeil2 = (int) Math.ceil((((double) (i + 1)) * 1.0d) / ((double) this.mSpanCount));
        if (iCeil2 == 1) {
            rect.top = this.mHeadMarginTop;
            if (iCeil2 == iCeil) {
                rect.bottom = this.mTailMarginBottom;
                return;
            } else {
                rect.bottom = this.mMarginBottom;
                return;
            }
        }
        if (iCeil2 == iCeil) {
            rect.top = this.mMarginTop;
            rect.bottom = this.mTailMarginBottom;
        } else {
            rect.top = this.mMarginTop;
            rect.bottom = this.mMarginBottom;
        }
    }

    public void setSpanCount(int i) {
        this.spanCount = i;
        this.mEndInset = new int[i];
    }
}
