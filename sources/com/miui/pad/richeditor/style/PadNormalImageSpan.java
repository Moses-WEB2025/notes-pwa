package com.miui.pad.richeditor.style;

import com.miui.notes.R;
import com.miui.richeditor.IEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.NormalImageSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PadNormalImageSpan extends NormalImageSpan {
    public PadNormalImageSpan(IEditorContext iEditorContext, HtmlParser.ImageElement imageElement) {
        super(iEditorContext, imageElement, iEditorContext.getSelectableSpanListener());
    }

    @Override // com.miui.richeditor.style.NormalImageSpan, com.miui.richeditor.style.BaseImageSpan
    protected void initMargin() {
        this.mMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.pad_image_content_margin_top);
        this.mMarginBottom = getContext().getResources().getDimensionPixelSize(R.dimen.pad_image_content_margin_bottom);
    }
}
