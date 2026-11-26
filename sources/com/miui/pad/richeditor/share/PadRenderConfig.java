package com.miui.pad.richeditor.share;

import android.content.Context;
import android.content.res.Resources;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import com.miui.richeditor.share.RenderConfig;

/* JADX INFO: loaded from: classes4.dex */
public class PadRenderConfig extends RenderConfig {
    public static RenderConfig getConfig(Context context) {
        return new PadRenderConfig(context);
    }

    protected PadRenderConfig(Context context) {
        super(context);
    }

    @Override // com.miui.richeditor.share.RenderConfig
    protected void initRenderSize(Context context) {
        Resources resources = context.getResources();
        this.mWidth = resources.getDimensionPixelSize(R.dimen.sns_content_width);
        if (RomUtils.isFoldDevice()) {
            this.mWidth = resources.getDimensionPixelSize(R.dimen.sns_content_width_fold);
        }
        if (this.mWidth == 0) {
            this.mWidth = Math.min(resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().heightPixels);
        }
        this.mMaxTitleTextSize = resources.getDimensionPixelSize(R.dimen.sns_title_font_size_max);
        this.mMinTitleTextSize = resources.getDimensionPixelSize(R.dimen.sns_title_font_size_min);
        this.mContentTextSize = resources.getDimensionPixelSize(R.dimen.sns_content_font_size);
        this.mFooterTextSize = resources.getDimensionPixelSize(R.dimen.sns_footer_font_size);
        this.mParagraphSpacing = resources.getDimensionPixelSize(R.dimen.v8_theme_share_paragraph_space);
        this.mHeaderMarginBottom = resources.getDimensionPixelOffset(R.dimen.sns_title_layout_margin_bottom);
        this.mFooterDividerMarginHorizontal = resources.getDimensionPixelOffset(R.dimen.sns_footer_divider_margin_horizontal);
        this.mFooterDividerMarginBottom = resources.getDimensionPixelOffset(R.dimen.sns_footer_divider_margin_bottom);
        this.mImageRadius = resources.getDimensionPixelSize(R.dimen.pad_note_edit_image_shape_radius);
        this.mLinkCardRadius = resources.getDimensionPixelSize(R.dimen.link_card_span_radius);
    }
}
