package com.miui.richeditor.style;

import android.text.style.ImageSpan;
import com.miui.notes.NoteApp;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.share.element.LinkCardElement;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardShareSpan extends ImageSpan implements HtmlParser.IElementSpan {
    protected final HtmlParser.GeneralElement mElement;
    protected LinkCardElement mShareElement;

    public LinkCardShareSpan(HtmlParser.GeneralElement generalElement) {
        super(NoteApp.getInstance(), 0, 1);
        this.mElement = generalElement;
    }

    public void setShareElement(LinkCardElement linkCardElement) {
        this.mShareElement = linkCardElement;
    }

    public LinkCardElement getShareElement() {
        return this.mShareElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.GeneralElement getElement() {
        return this.mElement;
    }
}
