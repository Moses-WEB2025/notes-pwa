package com.miui.common.view.richtext;

import android.content.Context;
import android.text.style.TextAppearanceSpan;
import com.miui.richeditor.schema.HtmlBuilder;

/* JADX INFO: loaded from: classes2.dex */
public class HighLightSpan extends TextAppearanceSpan implements HtmlBuilder.IIgnorable {
    public HighLightSpan(Context context, int i) {
        super(context, i);
    }
}
