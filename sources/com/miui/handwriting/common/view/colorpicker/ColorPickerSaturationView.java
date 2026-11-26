package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.SeekBarBaseView;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerSaturationView extends SeekBarBaseView {
    public ColorPickerSaturationView(Context context) {
        super(context);
    }

    public ColorPickerSaturationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ColorPickerSaturationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void init() {
        this.mLeftColor = -8355712;
        this.mRightColor = -65536;
        this.mColors = new int[]{this.mLeftColor, this.mRightColor};
        setProgress(getMax());
        super.init();
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    public void notifyColorChange(int i) {
        if (this.mOnColorChangeListener != null) {
            this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(3, i, false));
        }
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void notifyColorChangeOnStopTouch(int i) {
        if (this.mOnColorChangeListener != null) {
            this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(3, i));
        }
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mOnColorChangeListener = onColorPickerColorChangeListener;
    }

    public void updateCurrentBackground(int i) {
        this.mRightColor = i;
        this.mColors[1] = this.mRightColor;
        setupBackground();
    }
}
