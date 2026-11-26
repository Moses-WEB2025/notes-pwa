package com.miui.richeditor.style;

import android.text.style.ImageSpan;
import com.miui.notes.NoteApp;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardSnippetSpan extends ImageSpan implements HtmlParser.IElementSpan {
    protected final HtmlParser.GeneralElement mElement;
    protected HtmlParser.LinkCardElement mShareElement;

    public LinkCardSnippetSpan(HtmlParser.GeneralElement generalElement) {
        super(NoteApp.getInstance(), 0, 1);
        this.mElement = generalElement;
    }

    public void setShareElement(HtmlParser.LinkCardElement linkCardElement) {
        this.mShareElement = linkCardElement;
    }

    public HtmlParser.LinkCardElement getShareElement() {
        return this.mShareElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.GeneralElement getElement() {
        return this.mElement;
    }
}
