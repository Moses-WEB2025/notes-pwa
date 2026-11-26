package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.SeekBarBaseView;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerLightnessView extends SeekBarBaseView {
    private int mMidColor;

    public ColorPickerLightnessView(Context context) {
        super(context);
    }

    public ColorPickerLightnessView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ColorPickerLightnessView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void init() {
        this.mLeftColor = -16777216;
        this.mMidColor = -16777216;
        this.mRightColor = -1;
        this.mColors = new int[]{this.mLeftColor, this.mMidColor, this.mRightColor};
        setProgress(getMax());
        super.init();
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    public void notifyColorChange(int i) {
        if (this.mOnColorChangeListener != null) {
            this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(4, i, false));
        }
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void notifyColorChangeOnStopTouch(int i) {
        if (this.mOnColorChangeListener != null) {
            this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(4, i));
        }
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mOnColorChangeListener = onColorPickerColorChangeListener;
    }

    public void updateCurrentBackground(int i) {
        this.mMidColor = i;
        this.mColors[1] = this.mMidColor;
        setupBackground();
    }
}
