package com.miui.pc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.miui.notes.R;
import com.miui.pc.frame.PcBaseNoteRecyclerView;

/* JADX INFO: loaded from: classes4.dex */
public class MaxHeightRecyclerView extends PcBaseNoteRecyclerView {
    private int mMaxHeight;

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context, attributeSet);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize(context, attributeSet);
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MaxHeightRecyclerView);
        this.mMaxHeight = typedArrayObtainStyledAttributes.getLayoutDimension(0, this.mMaxHeight);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3 = this.mMaxHeight;
        if (i3 > 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }
}
