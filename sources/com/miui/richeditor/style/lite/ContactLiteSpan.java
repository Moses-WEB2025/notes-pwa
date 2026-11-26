package com.miui.richeditor.style.lite;

import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class ContactLiteSpan implements HtmlParser.IElementSpan {
    protected final HtmlParser.GeneralElement mElement;

    public ContactLiteSpan(HtmlParser.GeneralElement generalElement) {
        this.mElement = generalElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.GeneralElement getElement() {
        return this.mElement;
    }
}
