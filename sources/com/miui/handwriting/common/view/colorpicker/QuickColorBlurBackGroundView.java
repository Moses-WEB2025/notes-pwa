package com.miui.handwriting.common.view.colorpicker;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes3.dex */
public class QuickColorBlurBackGroundView extends BlurBackGroundView {
    private void init() {
    }

    public QuickColorBlurBackGroundView(Context context) {
        super(context);
        init();
    }

    public QuickColorBlurBackGroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public QuickColorBlurBackGroundView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @Override // com.miui.blur.sdk.backdrop.ViewBlurDrawInfo, com.miui.blur.sdk.backdrop.BlurDrawInfo
    public void getBlurOutline(Outline outline) {
        outline.setRect(new Rect(0, 0, 1, 1));
    }
}
