package com.miui.richeditor.style;

import android.text.style.RelativeSizeSpan;
import com.miui.richeditor.IEditorContext;

/* JADX INFO: loaded from: classes4.dex */
public class FontNormalSizeSpan extends RelativeSizeSpan {
    public FontNormalSizeSpan(IEditorContext iEditorContext) {
        this(iEditorContext.getFontSizesByLevel()[0]);
    }

    public FontNormalSizeSpan(float f) {
        super(f);
    }
}
