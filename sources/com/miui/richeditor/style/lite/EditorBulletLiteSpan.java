package com.miui.richeditor.style.lite;

import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class EditorBulletLiteSpan implements HtmlParser.IElementSpan {
    private HtmlParser.BulletElement mElement;

    public EditorBulletLiteSpan(HtmlParser.BulletElement bulletElement) {
        this.mElement = bulletElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.BulletElement getElement() {
        return this.mElement;
    }
}
