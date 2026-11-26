package com.miui.richeditor.style.lite;

import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class EditorQuoteLiteSpan implements HtmlParser.IElementSpan {
    private HtmlParser.QuoteElement mElement;

    public EditorQuoteLiteSpan(HtmlParser.QuoteElement quoteElement) {
        this.mElement = quoteElement;
    }

    public EditorQuoteLiteSpan() {
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.QuoteElement getElement() {
        return this.mElement;
    }
}
