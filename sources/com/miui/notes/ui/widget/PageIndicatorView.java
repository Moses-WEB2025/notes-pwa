package com.miui.notes.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class PageIndicatorView extends LinearLayout {
    private Context mContext;
    private int mMargins;

    public PageIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMargins = 3;
        this.mContext = context;
        this.mMargins = (int) (this.mMargins * getResources().getDisplayMetrics().density);
    }

    public void setIndicatorCount(int i) {
        removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        int i2 = this.mMargins;
        layoutParams.setMargins(i2, i2, i2, i2);
        for (int i3 = 0; i3 < i; i3++) {
            ImageView imageView = new ImageView(this.mContext);
            if (i3 == 0) {
                imageView.setImageResource(R.drawable.selected_point);
            } else {
                imageView.setImageResource(R.drawable.unselected_point);
            }
            addView(imageView, layoutParams);
        }
    }

    public void setSelectedPage(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            ImageView imageView = (ImageView) getChildAt(i2);
            if (i2 == i) {
                imageView.setImageResource(R.drawable.selected_point);
            } else {
                imageView.setImageResource(R.drawable.unselected_point);
            }
        }
    }
}
