package com.miui.richeditor.style.lite;

import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class SplitLineSpan implements HtmlParser.IElementSpan {
    private HtmlParser.SplitLineElement mElement;

    public SplitLineSpan(HtmlParser.SplitLineElement splitLineElement) {
        this.mElement = splitLineElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.SplitLineElement getElement() {
        return this.mElement;
    }
}
