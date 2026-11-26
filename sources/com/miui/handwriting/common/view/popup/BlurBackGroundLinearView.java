package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.blur.sdk.backdrop.BackdropBlurLinearLayout;

/* JADX INFO: loaded from: classes3.dex */
public class BlurBackGroundLinearView extends BackdropBlurLinearLayout {
    public BlurBackGroundLinearView(Context context) {
        super(context);
        init();
    }

    public BlurBackGroundLinearView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BlurBackGroundLinearView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setBlurRadius(10);
        setCornerRadius(16.0f);
    }
}
