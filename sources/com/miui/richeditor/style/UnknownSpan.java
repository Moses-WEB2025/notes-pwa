package com.miui.richeditor.style;

import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class UnknownSpan extends BaseImageSpan {
    public UnknownSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement) {
        super(iEditorContext, iEditorContext.getImageRender().getDefaultImageDrawable().getBitmap(), generalElement);
    }
}
