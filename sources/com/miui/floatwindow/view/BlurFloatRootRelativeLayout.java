package com.miui.floatwindow.view;

import android.content.Context;
import android.graphics.BlendMode;
import android.util.AttributeSet;
import com.miui.blur.sdk.backdrop.BackdropBlurRelativeLayout;
import com.miui.blur.sdk.backdrop.BlurStyle;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes2.dex */
public class BlurFloatRootRelativeLayout extends BackdropBlurRelativeLayout {
    public static final BlurStyle MY_DARK;
    private static final BlurStyle MY_LIGHT;

    static {
        BlurStyle blurStyleBuild = new BlurStyle.Builder().setBlurRadius(10).addBlendLayer(1970500467, BlendMode.COLOR_BURN).addBlendLayer(-1977211354, null).build();
        MY_DARK = blurStyleBuild;
        MY_LIGHT = new BlurStyle.Builder(blurStyleBuild).setBlurRadius(12).build();
    }

    public BlurFloatRootRelativeLayout(Context context) {
        super(context);
        if (UIUtils.isAboveAndroidS()) {
            setBlurRadius(60);
            setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.pad_global_window_bg_radius));
        }
    }

    public BlurFloatRootRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (UIUtils.isAboveAndroidS()) {
            setBlurRadius(60);
            setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.pad_global_window_bg_radius));
        }
    }

    public BlurFloatRootRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (UIUtils.isAboveAndroidS()) {
            setBlurRadius(60);
            setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.pad_global_window_bg_radius));
        }
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo
    public BlurStyle getBlurStyleDayMode() {
        if (UIUtils.isAboveAndroidS()) {
            return null;
        }
        return MY_DARK;
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo
    public BlurStyle getBlurStyleNightMode() {
        if (UIUtils.isAboveAndroidS()) {
            return null;
        }
        return MY_LIGHT;
    }
}
