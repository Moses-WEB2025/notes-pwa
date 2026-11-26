package com.miui.richeditor.style.lite;

import android.text.TextUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class LinkCardLiteSpan implements HtmlParser.IElementSpan {
    protected final HtmlParser.GeneralElement mElement;

    public LinkCardLiteSpan(HtmlParser.GeneralElement generalElement) {
        this.mElement = generalElement;
    }

    @Override // com.miui.richeditor.schema.HtmlParser.IElementSpan
    public HtmlParser.GeneralElement getElement() {
        return this.mElement;
    }

    public String getUrl() {
        return ((HtmlParser.LinkCardElement) this.mElement).getLink();
    }

    public void setTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Logger.INSTANCE.d("LinkCardLiteSpan", "setTitle " + str);
        ((HtmlParser.LinkCardElement) getElement()).setTitle(str);
    }

    public String getTitle() {
        return ((HtmlParser.LinkCardElement) this.mElement).getTitle();
    }
}
