package com.miui.richeditor.share;

import android.content.Context;
import android.content.res.Resources;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class RenderConfig {
    private static final float CONTENT_LINE_GAP = 1.0f;
    private static final int LINE_COLOR = -2565928;
    public static final float TITLE_LINE_GAP = 1.14f;
    protected int mContentTextSize;
    protected int mFooterDividerMarginBottom;
    protected int mFooterDividerMarginHorizontal;
    protected int mFooterTextSize;
    protected int mHeaderMarginBottom;
    protected int mImageRadius;
    protected int mLinkCardRadius;
    protected int mMaxTitleTextSize;
    protected int mMinTitleTextSize;
    protected int mParagraphSpacing;
    protected int mWidth;

    public float getContentLineGap() {
        return 1.0f;
    }

    public int getLineColor() {
        return LINE_COLOR;
    }

    public float getTitleLineGap() {
        return 1.14f;
    }

    public static RenderConfig getConfig(Context context) {
        return new RenderConfig(context);
    }

    protected RenderConfig(Context context) {
        initRenderSize(context);
    }

    protected void initRenderSize(Context context) {
        Logger.INSTANCE.d("RenderConfig", "initRenderSize");
        Resources resources = context.getResources();
        Logger.INSTANCE.d("RenderConfig", "getResources");
        this.mWidth = resources.getDimensionPixelSize(R.dimen.sns_content_width);
        Logger.INSTANCE.d("RenderConfig", "resources.getDimensionPixelSize mWidth = " + this.mWidth);
        if (this.mWidth == 0) {
            Logger.INSTANCE.d("RenderConfig", "resources.getDisplayMetrics");
            this.mWidth = resources.getDisplayMetrics().widthPixels;
        }
        this.mMaxTitleTextSize = resources.getDimensionPixelSize(R.dimen.sns_title_font_size_max);
        this.mMinTitleTextSize = resources.getDimensionPixelSize(R.dimen.sns_title_font_size_min);
        Logger.INSTANCE.d("RenderConfig", "RomUtils.isPadMode");
        if (RomUtils.isPadMode()) {
            Logger.INSTANCE.d("RenderConfig", "RomUtils.isPadMode true");
            this.mContentTextSize = resources.getDimensionPixelSize(R.dimen.sns_content_font_size_pad);
        } else {
            Logger.INSTANCE.d("RenderConfig", "RomUtils.isPadMode false");
            this.mContentTextSize = resources.getDimensionPixelSize(R.dimen.sns_content_font_size);
        }
        this.mFooterTextSize = resources.getDimensionPixelSize(R.dimen.sns_footer_font_size);
        this.mParagraphSpacing = resources.getDimensionPixelSize(R.dimen.v8_theme_share_paragraph_space);
        this.mHeaderMarginBottom = resources.getDimensionPixelOffset(R.dimen.sns_title_layout_margin_bottom);
        this.mFooterDividerMarginHorizontal = resources.getDimensionPixelOffset(R.dimen.sns_footer_divider_margin_horizontal);
        this.mFooterDividerMarginBottom = resources.getDimensionPixelOffset(R.dimen.sns_footer_divider_margin_bottom);
        this.mImageRadius = resources.getDimensionPixelSize(R.dimen.v12_grid_item_shape_radius);
        this.mLinkCardRadius = resources.getDimensionPixelSize(R.dimen.link_card_span_radius);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getMaxTitleTextSize() {
        return this.mMaxTitleTextSize;
    }

    @Deprecated
    public int getMinTitleTextSize() {
        return this.mMinTitleTextSize;
    }

    public int getContentTextSize() {
        return this.mContentTextSize;
    }

    public int getFooterTextSize() {
        return this.mFooterTextSize;
    }

    public int getParagraphSpacing() {
        return this.mParagraphSpacing;
    }

    public int getHeaderMarginBottom() {
        return this.mHeaderMarginBottom;
    }

    public int getFooterDividerMarginHorizontal() {
        return this.mFooterDividerMarginHorizontal;
    }

    public int getFooterDividerMarginBottom() {
        return this.mFooterDividerMarginBottom;
    }

    public int getImageRadius() {
        return this.mImageRadius;
    }

    public int getLinkCardRadius() {
        return this.mLinkCardRadius;
    }
}
