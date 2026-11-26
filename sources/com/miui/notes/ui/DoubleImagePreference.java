package com.miui.notes.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceViewHolder;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class DoubleImagePreference extends ListPreference {
    private final int[] mImageResIds;
    private final int[] mInfoResIds;

    public DoubleImagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInfoResIds = new int[2];
        this.mImageResIds = new int[2];
        init();
    }

    protected void init() {
        setLayoutResource(R.layout.preference_double_image);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int dimensionPixelOffset;
        int dimensionPixelOffset2;
        int dimensionPixelOffset3;
        super.onBindViewHolder(preferenceViewHolder);
        AppCompatImageView appCompatImageView = (AppCompatImageView) preferenceViewHolder.itemView.findViewById(R.id.img_1);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.img_info_1);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) preferenceViewHolder.itemView.findViewById(R.id.img_2);
        TextView textView2 = (TextView) preferenceViewHolder.itemView.findViewById(R.id.img_info_2);
        appCompatImageView.setImageResource(this.mImageResIds[0]);
        appCompatImageView2.setImageResource(this.mImageResIds[1]);
        if (UIUtils.isInJ18NarrowScreen(getContext())) {
            dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.setting_layout_demo_ic_space_small);
            dimensionPixelOffset2 = getContext().getResources().getDimensionPixelOffset(R.dimen.setting_layout_demo_ic_width_small);
            dimensionPixelOffset3 = getContext().getResources().getDimensionPixelOffset(R.dimen.setting_layout_demo_ic_height_small);
        } else {
            dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.setting_layout_demo_ic_space);
            dimensionPixelOffset2 = getContext().getResources().getDimensionPixelOffset(R.dimen.setting_layout_demo_ic_width);
            dimensionPixelOffset3 = getContext().getResources().getDimensionPixelOffset(R.dimen.setting_layout_demo_ic_height);
        }
        ViewGroup.LayoutParams layoutParams = appCompatImageView.getLayoutParams();
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).setMarginEnd(dimensionPixelOffset);
            layoutParams.width = dimensionPixelOffset2;
            layoutParams.height = dimensionPixelOffset3;
        }
        ViewGroup.LayoutParams layoutParams2 = appCompatImageView2.getLayoutParams();
        if (layoutParams2 instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams2).setMarginStart(dimensionPixelOffset);
            layoutParams2.width = dimensionPixelOffset2;
            layoutParams2.height = dimensionPixelOffset3;
        }
        textView.setText(this.mInfoResIds[0]);
        textView2.setText(this.mInfoResIds[1]);
    }

    public void setImageAndInfo(int[] iArr, int[] iArr2) {
        int[] iArr3 = this.mInfoResIds;
        iArr3[0] = iArr2[0];
        iArr3[1] = iArr2[1];
        int[] iArr4 = this.mImageResIds;
        iArr4[0] = iArr[0];
        iArr4[1] = iArr[1];
        notifyChanged();
    }
}
