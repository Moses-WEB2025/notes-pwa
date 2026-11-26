package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.view.InputDeviceCompat;
import com.miui.handwriting.common.data.dataset.PenSet;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.SeekBarBaseView;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerHueView extends SeekBarBaseView {
    public ColorPickerHueView(Context context) {
        super(context);
    }

    public ColorPickerHueView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ColorPickerHueView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void init() {
        setMax(360);
        setProgress(getMax());
        this.mColors = new int[]{-65536, InputDeviceCompat.SOURCE_ANY, -16711936, PenSet.Color.BABY_BLUE, -16776961, -65281, -65536};
        super.init();
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    public void notifyColorChange(int i) {
        if (this.mOnColorChangeListener != null) {
            this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(2, i, false));
        }
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void notifyColorChangeOnStopTouch(int i) {
        if (this.mOnColorChangeListener != null) {
            this.mOnColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(2, i));
        }
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mOnColorChangeListener = onColorPickerColorChangeListener;
    }

    public void updateCurrentBackground(int[] iArr) {
        this.mColors = iArr;
        setupBackground();
    }
}
