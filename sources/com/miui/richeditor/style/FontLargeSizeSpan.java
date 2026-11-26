package com.miui.richeditor.style;

import android.text.style.RelativeSizeSpan;
import com.miui.richeditor.IEditorContext;

/* JADX INFO: loaded from: classes4.dex */
public class FontLargeSizeSpan extends RelativeSizeSpan {
    public FontLargeSizeSpan(IEditorContext iEditorContext) {
        this(iEditorContext.getFontSizesByLevel()[2]);
    }

    public FontLargeSizeSpan(float f) {
        super(f);
    }
}
