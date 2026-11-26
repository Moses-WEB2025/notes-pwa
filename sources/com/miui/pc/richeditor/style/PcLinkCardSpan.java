package com.miui.pc.richeditor.style;

import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.LinkCardSpan;
import com.miui.richeditor.style.SelectableSpanListener;

/* JADX INFO: loaded from: classes4.dex */
public class PcLinkCardSpan extends LinkCardSpan {
    public PcLinkCardSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement) {
        this(iEditorContext, generalElement, iEditorContext.getSelectableSpanListener());
    }

    public PcLinkCardSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement, SelectableSpanListener selectableSpanListener) {
        super(iEditorContext, generalElement, selectableSpanListener);
    }

    @Override // com.miui.richeditor.style.LinkCardSpan, com.miui.richeditor.style.BaseImageSpan
    protected void initMargin() {
        this.mMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_margin_top);
        this.mMarginBottom = getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_margin_bottom);
    }
}
