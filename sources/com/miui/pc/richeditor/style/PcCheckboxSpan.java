package com.miui.pc.richeditor.style;

import android.content.Context;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.CheckboxSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PcCheckboxSpan extends CheckboxSpan {
    public PcCheckboxSpan(IEditorContext iEditorContext, boolean z) {
        this(iEditorContext.getContext(), iEditorContext, new HtmlParser.CheckBoxElement(z));
    }

    public PcCheckboxSpan(Context context, IEditorContext iEditorContext, boolean z) {
        this(context, iEditorContext, new HtmlParser.CheckBoxElement(z));
        this.mSpScale = 1.0f;
        this.mMarginEnd = 10;
        this.mDefaultSize = 24;
    }

    public PcCheckboxSpan(IEditorContext iEditorContext, HtmlParser.CheckBoxElement checkBoxElement) {
        this(iEditorContext.getContext(), iEditorContext, checkBoxElement);
    }

    public PcCheckboxSpan(Context context, IEditorContext iEditorContext, HtmlParser.CheckBoxElement checkBoxElement) {
        super(iEditorContext, checkBoxElement);
        this.mSpScale = 1.0f;
        this.mMarginEnd = 10;
        this.mDefaultSize = 24;
    }

    @Override // com.miui.richeditor.style.CheckboxSpan
    protected int getImageSize() {
        return (int) (this.mDefaultSize * this.mSpScale);
    }
}
