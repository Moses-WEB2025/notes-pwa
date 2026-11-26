package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.HSLColor;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.quickMenu.utils.QuickMenuScale;
import java.util.Locale;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class ColorPickerSlider extends LinearLayout {
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mChildColorChangeListener;
    private ColorPickerHueView mColorPickerHueView;
    private ColorPickerLightnessView mColorPickerLightnessView;
    private ColorPickerSaturationView mColorPickerSaturationView;
    private float mHue;
    private QuickMenuScale mLayoutScaleType;
    private float mLightness;
    private ColorPickerPopupWindow.OnColorPickerColorChangeListener mParentColorChangeListener;
    private float mSaturation;

    public ColorPickerSlider(Context context) {
        super(context);
        this.mSaturation = 1.0f;
        this.mLightness = 0.5f;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public ColorPickerSlider(Context context, QuickMenuScale quickMenuScale) {
        super(context);
        this.mSaturation = 1.0f;
        this.mLightness = 0.5f;
        QuickMenuScale quickMenuScale2 = QuickMenuScale.ONE;
        this.mLayoutScaleType = quickMenuScale;
        init(context);
    }

    public ColorPickerSlider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSaturation = 1.0f;
        this.mLightness = 0.5f;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public ColorPickerSlider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSaturation = 1.0f;
        this.mLightness = 0.5f;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public ColorPickerSlider(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSaturation = 1.0f;
        this.mLightness = 0.5f;
        this.mLayoutScaleType = QuickMenuScale.ONE;
        init(context);
    }

    public void init(Context context) {
        if (this.mLayoutScaleType == QuickMenuScale.ZERO_POINT_NINE) {
            LayoutInflater.from(context).inflate(R.layout.popup_color_picker_slider_zero_point_nine, (ViewGroup) this, true);
        } else if (this.mLayoutScaleType == QuickMenuScale.ZERO_POINT_EIGHT) {
            LayoutInflater.from(context).inflate(R.layout.popup_color_picker_slider_zero_point_eight, (ViewGroup) this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.popup_color_picker_slider, (ViewGroup) this, true);
        }
        this.mColorPickerHueView = (ColorPickerHueView) findViewById(R.id.slider_hue);
        this.mColorPickerSaturationView = (ColorPickerSaturationView) findViewById(R.id.slider_saturation);
        this.mColorPickerLightnessView = (ColorPickerLightnessView) findViewById(R.id.slider_lightness);
        String language = Locale.getDefault().getLanguage();
        if (context.getResources().getConfiguration().fontScale > DisplayUtils.DEFAULT_FONT_SIZE && language.equals("bo")) {
            TextView textView = (TextView) findViewById(R.id.slider_hue_text);
            TextView textView2 = (TextView) findViewById(R.id.slider_saturation_text);
            TextView textView3 = (TextView) findViewById(R.id.slider_lightness_text);
            textView.setTextSize(1, 13.0f);
            textView2.setTextSize(1, 13.0f);
            textView3.setTextSize(1, 13.0f);
        }
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.colorpicker.ColorPickerSlider.1
            @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
            public void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                ColorPickerSlider.this.colorChange(colorPickerChangeEvent);
            }
        };
        this.mChildColorChangeListener = onColorPickerColorChangeListener;
        this.mColorPickerHueView.addOnColorChangeListener(onColorPickerColorChangeListener);
        this.mColorPickerSaturationView.addOnColorChangeListener(this.mChildColorChangeListener);
        this.mColorPickerLightnessView.addOnColorChangeListener(this.mChildColorChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void colorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
        int mode = colorPickerChangeEvent.getMode();
        if (mode == 2) {
            int iCalculateHueColor = ColorUtils.calculateHueColor(colorPickerChangeEvent.getColor());
            this.mColorPickerSaturationView.updateCurrentBackground(iCalculateHueColor);
            this.mColorPickerLightnessView.updateCurrentBackground(iCalculateHueColor);
            this.mHue = colorPickerChangeEvent.getColor();
        } else if (mode == 3) {
            this.mSaturation = colorPickerChangeEvent.getColor() / this.mColorPickerSaturationView.getMax();
        } else if (mode == 4) {
            this.mLightness = colorPickerChangeEvent.getColor() / this.mColorPickerLightnessView.getMax();
        }
        ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener = this.mParentColorChangeListener;
        if (onColorPickerColorChangeListener != null) {
            onColorPickerColorChangeListener.onColorChange(new ColorPickerPopupWindow.ColorPickerChangeEvent(0, new HSLColor(this.mHue, this.mSaturation, this.mLightness), colorPickerChangeEvent.getNeedUpdate()));
        }
    }

    public void addOnColorChangeListener(ColorPickerPopupWindow.OnColorPickerColorChangeListener onColorPickerColorChangeListener) {
        this.mParentColorChangeListener = onColorPickerColorChangeListener;
    }

    public void updateCurrentColor(float[] fArr) {
        ColorPickerHueView colorPickerHueView = this.mColorPickerHueView;
        if (colorPickerHueView != null) {
            colorPickerHueView.setProgress((int) fArr[0]);
            this.mHue = (int) fArr[0];
        }
        ColorPickerSaturationView colorPickerSaturationView = this.mColorPickerSaturationView;
        if (colorPickerSaturationView != null) {
            colorPickerSaturationView.setProgress((int) (fArr[1] * 100.0f));
            this.mSaturation = fArr[1];
        }
        ColorPickerLightnessView colorPickerLightnessView = this.mColorPickerLightnessView;
        if (colorPickerLightnessView != null) {
            colorPickerLightnessView.setProgress((int) (fArr[2] * 100.0f));
            this.mLightness = fArr[2];
        }
        int iCalculateHueColor = ColorUtils.calculateHueColor(this.mHue);
        this.mColorPickerSaturationView.updateCurrentBackground(iCalculateHueColor);
        this.mColorPickerLightnessView.updateCurrentBackground(iCalculateHueColor);
        invalidate();
    }

    public void updateCurrentColor(HSLColor hSLColor) {
        ColorPickerHueView colorPickerHueView = this.mColorPickerHueView;
        if (colorPickerHueView != null) {
            colorPickerHueView.setProgress((int) hSLColor.getHue());
            this.mHue = (int) hSLColor.getHue();
        }
        ColorPickerSaturationView colorPickerSaturationView = this.mColorPickerSaturationView;
        if (colorPickerSaturationView != null) {
            colorPickerSaturationView.setProgress((int) (hSLColor.getSaturation() * 100.0f));
            this.mSaturation = hSLColor.getSaturation();
        }
        ColorPickerLightnessView colorPickerLightnessView = this.mColorPickerLightnessView;
        if (colorPickerLightnessView != null) {
            colorPickerLightnessView.setProgress((int) (hSLColor.getLightness() * 100.0f));
            this.mLightness = hSLColor.getLightness();
        }
        int iCalculateHueColor = ColorUtils.calculateHueColor(this.mHue);
        this.mColorPickerSaturationView.updateCurrentBackground(iCalculateHueColor);
        this.mColorPickerLightnessView.updateCurrentBackground(iCalculateHueColor);
        invalidate();
    }
}
