package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;

/* JADX INFO: loaded from: classes3.dex */
public class SelectAlphaView extends SeekBarBaseView {
    private boolean isAccessibilityProcess;

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    public void notifyColorChange(int i) {
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void notifyColorChangeOnStopTouch(int i) {
    }

    public SelectAlphaView(Context context) {
        super(context);
        this.isAccessibilityProcess = true;
    }

    public SelectAlphaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isAccessibilityProcess = true;
    }

    public SelectAlphaView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAccessibilityProcess = true;
    }

    public void setAccessibilityProcess(boolean z) {
        this.isAccessibilityProcess = z;
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void init() {
        setMax(255);
        setProgress(getMax());
        super.init();
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void setupBackground() {
        super.setupBackground();
    }

    public void updateCurrentBackground(int i) {
        this.mRightColor = i;
        this.mColors[1] = this.mRightColor;
        setupBackground();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.isAccessibilityProcess) {
            return;
        }
        accessibilityNodeInfo.setStateDescription(String.valueOf(getProgress()));
    }
}
