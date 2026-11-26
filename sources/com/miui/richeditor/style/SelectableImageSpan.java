package com.miui.richeditor.style;

import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;

/* JADX INFO: loaded from: classes4.dex */
public class SelectableImageSpan extends BaseImageSpan implements HtmlParser.IElementSpan, ClickableElement {
    protected SelectableSpanListener mSelectSpanListener;
    protected boolean mSelected;

    public SelectableImageSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement) {
        this(iEditorContext, generalElement, iEditorContext.getSelectableSpanListener());
    }

    public SelectableImageSpan(IEditorContext iEditorContext, HtmlParser.GeneralElement generalElement, SelectableSpanListener selectableSpanListener) {
        super(iEditorContext, generalElement);
        this.mSelected = false;
        this.mSelectSpanListener = selectableSpanListener;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void setSelected(boolean z) {
        onSelected(z);
        SelectableSpanListener selectableSpanListener = this.mSelectSpanListener;
        if (selectableSpanListener != null) {
            selectableSpanListener.onSelectChanged(this, z);
        }
    }

    @Override // com.miui.richeditor.style.BaseImageSpan, com.miui.richeditor.style.ClickableElement
    public void onSelected(boolean z) {
        this.mSelected = z;
    }

    @Override // com.miui.richeditor.style.BaseImageSpan
    public void onDelete() {
        setSelected(false);
    }
}
