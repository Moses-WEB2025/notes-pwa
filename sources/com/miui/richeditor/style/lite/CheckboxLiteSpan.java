package com.miui.richeditor.style.lite;

import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class CheckboxLiteSpan implements HtmlParser.IElementSpan {
    private HtmlParser.CheckBoxElement mElement;

    public CheckboxLiteSpan(HtmlParser.CheckBoxElement checkBoxElement) {
        this.mElement = checkBoxElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.CheckBoxElement getElement() {
        return this.mElement;
    }
}
