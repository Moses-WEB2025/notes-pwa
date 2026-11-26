package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.SeekBarBaseView;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerAlphaView extends SeekBarBaseView {
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mOnColorChangeListener;
    private float mThumbScale;

    public ColorPickerAlphaView(Context context) {
        super(context);
        this.mThumbScale = 1.0f;
    }

    public ColorPickerAlphaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mThumbScale = 1.0f;
        getAttrs(context, attributeSet);
    }

    public ColorPickerAlphaView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mThumbScale = 1.0f;
        getAttrs(context, attributeSet);
    }

    private void getAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBarBaseView);
        try {
            this.mThumbScale = typedArrayObtainStyledAttributes.getFloat(R.styleable.SeekBarBaseView_thumb_scale, 1.0f);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void init() {
        setMax(255);
        setProgress(getMax());
        setMin(3);
        this.mColors = new int[]{this.mLeftColor, this.mRightColor};
        super.init();
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(i, View.MeasureSpec.makeMeasureSpec((int) (getMeasuredHeight() * this.mThumbScale), View.MeasureSpec.getMode(i2)));
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    public void notifyColorChange(int i) {
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = this.mOnColorChangeListener;
        if (onColorPickerColorChangeListener != null) {
            onColorPickerColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(1, i, false));
        }
    }

    @Override // com.miui.handwriting.common.view.popup.SeekBarBaseView
    protected void notifyColorChangeOnStopTouch(int i) {
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = this.mOnColorChangeListener;
        if (onColorPickerColorChangeListener != null) {
            onColorPickerColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(1, i));
        }
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mOnColorChangeListener = onColorPickerColorChangeListener;
    }

    public void updateCurrentBackground(int i) {
        this.mRightColor = ColorUtils.calculateARGBColor(255, i);
        this.mColors[1] = this.mRightColor;
        setupBackground();
    }

    public void updateProgress(int i) {
        setProgress(i);
        invalidate();
    }
}
