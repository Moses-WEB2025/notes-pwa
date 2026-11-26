package com.miui.common.tool;

import android.view.View;
import com.miui.blur.sdk.drawable.BlurDrawable;
import defpackage.R2;

/* JADX INFO: loaded from: classes2.dex */
public class BlurHelper {
    public static boolean ENABLE_BLUR = false;

    public static void setBlurBackground(View view, BlurDrawable blurDrawable, int i, int i2) {
        setBlurBackground(view, blurDrawable, i, i2, R2.array.passport_alphabet_table);
    }

    public static void setBlurBackground(View view, BlurDrawable blurDrawable, int i, int i2, int i3) {
        if (i == 0 || i2 == 0) {
            return;
        }
        try {
            setBlurBackgroundByColor(view, blurDrawable, view.getContext().getColor(i), view.getContext().getColor(i2) - 100663296, i3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBlurBackgroundByColor(View view, BlurDrawable blurDrawable, int i, int i2) {
        setBlurBackgroundByColor(view, blurDrawable, i, i2, R2.array.passport_alphabet_table);
    }

    public static void setBlurBackgroundByColor(View view, BlurDrawable blurDrawable, int i, int i2, int i3) {
        if (isEnableBlur() && blurDrawable != null) {
            blurDrawable.setBlurRatio((i3 * 1.0f) / 255.0f);
            blurDrawable.clearMixColor();
            blurDrawable.addMixColor(i);
            view.setBackground(blurDrawable);
            return;
        }
        view.setBackgroundColor(i2);
    }

    public static boolean isEnableBlur() {
        return ENABLE_BLUR;
    }
}
