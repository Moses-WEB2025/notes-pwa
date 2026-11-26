package com.miui.floatwindow.view;

import android.content.Context;
import android.graphics.BlendMode;
import android.util.AttributeSet;
import com.miui.blur.sdk.backdrop.BackdropBlurFrameLayout;
import com.miui.blur.sdk.backdrop.BlurStyle;

/* JADX INFO: loaded from: classes2.dex */
public class BlurFloatRootFrameLayout extends BackdropBlurFrameLayout {
    public static final BlurStyle MY_DARK;
    private static final BlurStyle MY_LIGHT;

    static {
        BlurStyle blurStyleBuild = new BlurStyle.Builder().setBlurRadius(10).addBlendLayer(1970500467, BlendMode.DARKEN).addBlendLayer(-1977211354, null).build();
        MY_DARK = blurStyleBuild;
        MY_LIGHT = new BlurStyle.Builder(blurStyleBuild).setBlurRadius(12).build();
    }

    public BlurFloatRootFrameLayout(Context context) {
        super(context);
    }

    public BlurFloatRootFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BlurFloatRootFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo
    public BlurStyle getBlurStyleDayMode() {
        return MY_DARK;
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo
    public BlurStyle getBlurStyleNightMode() {
        return MY_LIGHT;
    }
}
