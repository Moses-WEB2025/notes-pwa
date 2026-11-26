package com.miui.pc.richeditor.style;

import com.miui.notes.R;
import com.miui.richeditor.style.LinkCardSpanHelper;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PcLinkCardSpanHelper extends LinkCardSpanHelper {
    public PcLinkCardSpanHelper(Theme theme) {
        super(theme);
    }

    @Override // com.miui.richeditor.style.LinkCardSpanHelper
    protected void initDrawSize() {
        this.mLinkCardWebTitlePaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_title_text_size));
        this.mLinkCardWebLinkPaint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_link_text_size));
        this.mSpanPadding = getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_padding);
        this.mLinkMarginStart = getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_link_margin_start);
        this.mLinkMarginTop = getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_link_margin_top);
        this.mBorderWidth = 2.0f;
        this.mRadius = getContext().getResources().getDimensionPixelSize(R.dimen.pc_link_card_span_radius);
        this.mImageSelectBorderPaint.setStrokeWidth(this.mBorderWidth);
    }
}
