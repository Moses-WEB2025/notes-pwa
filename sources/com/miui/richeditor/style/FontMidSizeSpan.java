package com.miui.richeditor.style;

import android.text.style.RelativeSizeSpan;
import com.miui.richeditor.IEditorContext;

/* JADX INFO: loaded from: classes4.dex */
public class FontMidSizeSpan extends RelativeSizeSpan {
    public FontMidSizeSpan(IEditorContext iEditorContext) {
        this(iEditorContext.getFontSizesByLevel()[1]);
    }

    public FontMidSizeSpan(float f) {
        super(f);
    }
}
