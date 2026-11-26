package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class SelectPropertyView extends SelectAlphaView {
    private boolean isAccessibilityProcess;
    protected int mLeftColor;
    protected int mRightColor;

    @Override // com.miui.handwriting.common.view.popup.SelectAlphaView, com.miui.handwriting.common.view.popup.SeekBarBaseView
    public void notifyColorChange(int i) {
    }

    public SelectPropertyView(Context context) {
        super(context);
        this.isAccessibilityProcess = true;
        this.mRightColor = 16777215;
        this.mLeftColor = 16777215;
    }

    public SelectPropertyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isAccessibilityProcess = true;
        this.mRightColor = 16777215;
        this.mLeftColor = 16777215;
    }

    public SelectPropertyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAccessibilityProcess = true;
        this.mRightColor = 16777215;
        this.mLeftColor = 16777215;
    }

    @Override // com.miui.handwriting.common.view.popup.SelectAlphaView
    public void setAccessibilityProcess(boolean z) {
        this.isAccessibilityProcess = z;
    }

    @Override // com.miui.handwriting.common.view.popup.SelectAlphaView, com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void init() {
        setMax(255);
        setProgress(getMax());
        this.mColors = new int[]{this.mLeftColor, this.mRightColor};
        super.init();
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void setupBackground(GradientDrawable gradientDrawable) {
        this.BackGroundGrad.setColor(ContextCompat.getColor(getContext(), R.color.creation_property_seekbar_bg));
        this.BackGroundGrad.setShape(0);
        this.BackGroundGrad.setCornerRadius(DisplayUtils.dip2px(getContext(), 36.0f));
        this.BackGroundGrad.setStroke(DisplayUtils.dip2px(getContext(), 1.0f), Color.parseColor("#0D000000"));
        setBackground(this.BackGroundGrad);
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void initBackgroundColor() {
        super.initBackgroundColor();
        this.isSolidColor = true;
    }

    @Override // com.miui.handwriting.common.view.popup.SelectAlphaView, android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.isAccessibilityProcess) {
            return;
        }
        accessibilityNodeInfo.setStateDescription(String.valueOf(getProgress()));
    }
}
