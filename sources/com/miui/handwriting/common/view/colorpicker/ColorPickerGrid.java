package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.HSLColor;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.quickMenu.utils.QuickMenuScale;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerGrid extends LinearLayout {
    private int mAlpha;
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mChildColorChangeListener;
    private int mColor;
    private ColorPickPanelView mColorPickPanelView;
    private QuickMenuScale mLayoutScaleType;
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mParentColorChangeListener;

    public ColorPickerGrid(Context context) {
        super(context);
        this.mColor = 0;
        this.mAlpha = 255;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public ColorPickerGrid(Context context, QuickMenuScale quickMenuScale) {
        super(context);
        this.mColor = 0;
        this.mAlpha = 255;
        QuickMenuScale quickMenuScale2 = QuickMenuScale.ONE;
        this.mLayoutScaleType = quickMenuScale;
        init(context);
    }

    public ColorPickerGrid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mColor = 0;
        this.mAlpha = 255;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public ColorPickerGrid(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColor = 0;
        this.mAlpha = 255;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public ColorPickerGrid(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mColor = 0;
        this.mAlpha = 255;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public void init(Context context) {
        if (this.mLayoutScaleType == QuickMenuScale.ZERO_POINT_NINE) {
            LayoutInflater.from(context).inflate(R.layout.popup_color_picker_grid_zero_point_nine, (ViewGroup) this, true);
        } else if (this.mLayoutScaleType == QuickMenuScale.ZERO_POINT_EIGHT) {
            LayoutInflater.from(context).inflate(R.layout.popup_color_picker_grid_zero_point_eight, (ViewGroup) this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.popup_color_picker_grid, (ViewGroup) this, true);
        }
        this.mColorPickPanelView = (ColorPickPanelView) findViewById(R.id.color_picker_panel);
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.colorpicker.ColorPickerGrid.1
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                ColorPickerGrid.this.colorChange(colorPickerChangeEvent);
            }
        };
        this.mChildColorChangeListener = onColorPickerColorChangeListener;
        this.mColorPickPanelView.addOnColorChangeListener(onColorPickerColorChangeListener);
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mParentColorChangeListener = onColorPickerColorChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void colorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
        if (colorPickerChangeEvent.getMode() == 0) {
            this.mColor = colorPickerChangeEvent.getColor();
        }
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = this.mParentColorChangeListener;
        if (onColorPickerColorChangeListener != null) {
            onColorPickerColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(0, new HSLColor(ColorUtils.calculateARGBColor(255, this.mColor)), colorPickerChangeEvent.getNeedUpdate()));
        }
    }

    public void clearSelect() {
        this.mColorPickPanelView.clearSelect();
    }

    public void setPanelColor(int i) {
        this.mColorPickPanelView.setColor(i);
    }
}
