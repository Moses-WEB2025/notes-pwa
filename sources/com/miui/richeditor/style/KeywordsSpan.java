package com.miui.richeditor.style;

import android.content.Context;
import android.text.style.TextAppearanceSpan;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlBuilder;

/* JADX INFO: loaded from: classes4.dex */
public class KeywordsSpan extends TextAppearanceSpan implements HtmlBuilder.IIgnorable {
    public KeywordsSpan(IEditorContext iEditorContext) {
        this(iEditorContext.getContext(), iEditorContext.getKeywordStyle());
    }

    public KeywordsSpan(Context context, int i) {
        super(context, i);
    }
}
