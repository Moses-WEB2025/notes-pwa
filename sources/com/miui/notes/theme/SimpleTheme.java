package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import androidx.core.view.GravityCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public abstract class SimpleTheme extends Theme {
    protected Theme.ActionBarStyle mActionBarStyle;
    protected int mCursorColor;
    protected int mDoneTextColor;
    private volatile Theme.EditorStyle mEditorStyle;
    protected Rect mFgBorderPadding;
    protected Rect mFgPadding;
    protected int mHandleColor;
    protected int mHeaderInfoTextColor;
    protected int mHighLightColor;
    protected int mIconColor;
    protected Theme.LinkCardStyle mLinkCardStyle;
    protected int mLinkedColor;
    protected int mPrimaryColor;
    protected int mSecondaryColor;
    private Theme.ShareStyle mShareStyle;
    protected int mThemeStyle;
    protected Theme.ToolBarStyle mToolBarStyle;
    private Theme.WidgetStyle mWidgetStyle;

    protected int getAlertTimeIcon(Context context) {
        return R.drawable.ic_data_item_clock;
    }

    protected abstract Drawable getBackgroundDrawable(Context context);

    protected int getBgHighLightColor(Context context) {
        return R.color.theme_default_bg_highlight_color;
    }

    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_default;
    }

    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_default;
    }

    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_default;
    }

    public int getBlurMixColor4() {
        return R.color.note_edit_blur_mix_color4_default;
    }

    protected int getBlurRadius() {
        return 150;
    }

    protected int getCursorColor() {
        return R.color.theme_default_cursor_color;
    }

    protected int getDirIndicatorBgColor() {
        return R.color.common_note_dir_indicator_bg_color_default;
    }

    protected int getDirIndicatorBorderColor() {
        return R.color.note_dir_indicator_border_color_default;
    }

    protected int getDirIndicatorIconColor() {
        return R.color.note_dir_indicator_text_color_default;
    }

    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_default;
    }

    protected int getEditTextGravity(Context context) {
        return GravityCompat.START;
    }

    protected int getEditorHintColor() {
        return R.color.note_title_hint_color_default;
    }

    protected abstract Drawable getForegroundDrawable(Context context, long j);

    protected abstract int getGridBodyStyle(Context context);

    protected abstract Drawable getGridBorder(Context context);

    protected int getGridBorderColor(Context context) {
        return 0;
    }

    protected int getGridBorderWidth(Context context) {
        return 0;
    }

    protected abstract Drawable getGridContentBackground(Context context);

    protected abstract int getGridTimeStyle(Context context);

    protected abstract int getGridTitleStyle(Context context);

    protected int getHandleColor() {
        return R.color.theme_default_cursor_handle_color;
    }

    protected int getHighLightColor() {
        return R.color.theme_default_high_light_color;
    }

    protected int getLinkedColor() {
        return R.color.theme_default_linked_color;
    }

    protected int getStickIcon() {
        return R.drawable.ic_data_item_stick;
    }

    protected int getVoiceIcon() {
        return R.drawable.ic_data_item_audio;
    }

    protected int getWidgetTitleColor() {
        return R.color.widget_title_primary_text;
    }

    protected boolean showGridItemImage(Context context) {
        return false;
    }

    public SimpleTheme(int i, int i2, int i3, int i4, int i5) {
        super(i);
        this.mFgPadding = null;
        this.mFgBorderPadding = null;
        this.mPrimaryColor = i2;
        this.mSecondaryColor = i3;
        this.mHeaderInfoTextColor = i4;
        this.mIconColor = i5;
    }

    public SimpleTheme(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this(i, i3, i4, i5, i6);
        this.mThemeStyle = i2;
        this.mCursorColor = i7;
        this.mHandleColor = i8;
        this.mHighLightColor = i9;
        this.mLinkedColor = i10;
        this.mDoneTextColor = i11;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Resources.Theme getResTheme(Context context) {
        if (this.mResTheme == null && context != null) {
            this.mResTheme = context.getResources().newTheme();
            this.mResTheme.applyStyle(this.mThemeStyle, true);
        }
        return this.mResTheme;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        if (this.mActionBarStyle == null) {
            Theme.ActionBarStyle actionBarStyle = new Theme.ActionBarStyle(ACTION_BAR_STYLE_ARRAY, getResTheme(context));
            this.mActionBarStyle = actionBarStyle;
            actionBarStyle.setHeaderInfoColor(this.mHeaderInfoTextColor);
        }
        return this.mActionBarStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        if (this.mToolBarStyle == null) {
            this.mToolBarStyle = new Theme.ToolBarStyle(TOOL_BAR_STYLE_ARRAY, getResTheme(context));
        }
        return this.mToolBarStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getCheckboxResource(Context context) {
        return getThemeDrawable(R.drawable.ic_richtext_check, getResTheme(context));
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.EditorStyle getEditorStyle(Context context) {
        Theme.EditorStyle editorStyle;
        Theme.EditorStyle editorStyle2 = this.mEditorStyle;
        if (editorStyle2 == null) {
            synchronized (ResourceManager.class) {
                if (this.mEditorStyle == null) {
                    editorStyle = new Theme.EditorStyle();
                    editorStyle.mTextColor = getEditTextColor(context);
                    editorStyle.mBackground = getBackgroundDrawable(context);
                    editorStyle.mTextGravity = getEditTextGravity(context);
                    editorStyle.mCursorColor = getCursorColor();
                    editorStyle.mHighLightColor = getHighLightColor();
                    editorStyle.mLinkedColor = getLinkedColor();
                    editorStyle.mHandleColor = getHandleColor();
                    editorStyle.mBlurMixColor = getBlurMixColor();
                    editorStyle.mBlurMixColor2 = getBlurMixColor2();
                    editorStyle.mBlurMixColor3 = getBlurMixColor3();
                    editorStyle.mBlurRadius = getBlurMixColor();
                    editorStyle.mEditorHintColor = getEditorHintColor();
                    editorStyle.mDirIndicatorBgColor = getDirIndicatorBgColor();
                    editorStyle.mDirIndicatorBorderColor = getDirIndicatorBorderColor();
                    editorStyle.mDirIndicatorIconColor = getDirIndicatorIconColor();
                    editorStyle.mBgHighLightColor = getBgHighLightColor(context);
                    editorStyle.mEditInputBgColor = getEditInputBgColor();
                    editorStyle.mForeground = getForegroundDrawable(context, 0L);
                    this.mEditorStyle = editorStyle;
                } else {
                    editorStyle = this.mEditorStyle;
                }
            }
            return editorStyle;
        }
        editorStyle2.mBackground = getBackgroundDrawable(context);
        return editorStyle2;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.EditorStyle getEditorStyle(Context context, long j) {
        Theme.EditorStyle editorStyle;
        Theme.EditorStyle editorStyle2 = this.mEditorStyle;
        if (editorStyle2 == null) {
            synchronized (ResourceManager.class) {
                if (this.mEditorStyle == null) {
                    editorStyle = new Theme.EditorStyle();
                    editorStyle.mTextColor = getEditTextColor(context);
                    editorStyle.mTextGravity = getEditTextGravity(context);
                    editorStyle.mCursorColor = getCursorColor();
                    editorStyle.mBackground = getBackgroundDrawable(context);
                    editorStyle.mForeground = getForegroundDrawable(context, j);
                    editorStyle.mHighLightColor = getHighLightColor();
                    editorStyle.mLinkedColor = getLinkedColor();
                    editorStyle.mHandleColor = getHandleColor();
                    editorStyle.mBlurMixColor = getBlurMixColor();
                    editorStyle.mBlurMixColor2 = getBlurMixColor2();
                    editorStyle.mBlurMixColor3 = getBlurMixColor3();
                    editorStyle.mBlurRadius = getBlurMixColor();
                    editorStyle.mEditorHintColor = getEditorHintColor();
                    editorStyle.mDirIndicatorBgColor = getDirIndicatorBgColor();
                    editorStyle.mDirIndicatorBorderColor = getDirIndicatorBorderColor();
                    editorStyle.mDirIndicatorIconColor = getDirIndicatorIconColor();
                    editorStyle.mBgHighLightColor = getBgHighLightColor(context);
                    editorStyle.mEditInputBgColor = getEditInputBgColor();
                    this.mEditorStyle = editorStyle;
                } else {
                    editorStyle = this.mEditorStyle;
                }
            }
            return editorStyle;
        }
        editorStyle2.mBackground = getBackgroundDrawable(context);
        editorStyle2.mForeground = getForegroundDrawable(context, j);
        return editorStyle2;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.ShareStyle getShareStyle(Context context, long j) {
        Theme.ShareStyle shareStyle = this.mShareStyle;
        if (shareStyle == null) {
            Theme.ShareStyle shareStyle2 = new Theme.ShareStyle();
            this.mShareStyle = shareStyle2;
            shareStyle2.mTextColor = getEditTextColor(context);
            this.mShareStyle.mBackground = getShareBackground(context, j);
            this.mShareStyle.mTextGravity = getEditTextGravity(context);
            this.mShareStyle.mFooterLineMarginTop = getShareLineTop(context);
            this.mShareStyle.mFooterMarginTop = getEndSignatureMarginTop(context);
            this.mShareStyle.mFooterMarginBottom = getEndSignatureMarginBottom(context);
            this.mShareStyle.mHeaderMarginTop = getShareTitleAddMarginTop(context);
            this.mShareStyle.mIconColor = getIconColor();
        } else {
            shareStyle.mBackground = getShareBackground(context, j);
        }
        return this.mShareStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.GridStyle getGridStyle(Context context) {
        Theme.GridStyle gridStyle = new Theme.GridStyle();
        gridStyle.mBorder = getGridBorder(context);
        gridStyle.mBorderColor = getGridBorderColor(context);
        gridStyle.mBorderWidth = getGridBorderWidth(context);
        gridStyle.mBackground = getGridContentBackground(context);
        gridStyle.mTitleStyle = getGridTitleStyle(context);
        gridStyle.mBodyStyle = getGridBodyStyle(context);
        gridStyle.mTimeStyle = getGridTimeStyle(context);
        gridStyle.mShowImage = showGridItemImage(context);
        gridStyle.mAlertIconRes = getAlertTimeIcon(context);
        gridStyle.mStickIconRes = getStickIcon();
        gridStyle.mVoiceIconRes = getVoiceIcon();
        gridStyle.mIconColor = this.mPrimaryColor;
        return gridStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.WidgetStyle getWidgetStyle(Context context) {
        if (this.mWidgetStyle == null) {
            Theme.WidgetStyle widgetStyle = new Theme.WidgetStyle();
            this.mWidgetStyle = widgetStyle;
            widgetStyle.mTitleBackground = getWidgetTitleBackground();
            this.mWidgetStyle.mTitleColor = getWidgetTitleColor();
        }
        return this.mWidgetStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_default, R.color.link_card_link_text_color_default});
        }
        return this.mLinkCardStyle;
    }

    protected Drawable getShareBackground(Context context, long j) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_share_inset);
        return new LayerDrawable(new Drawable[]{getBackgroundDrawable(context), new InsetDrawable(getForegroundDrawable(context, j), 0, dimensionPixelSize, 0, dimensionPixelSize)});
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundBorderPadding(Context context) {
        if (this.mFgBorderPadding == null) {
            this.mFgBorderPadding = new Rect();
        }
        return this.mFgBorderPadding;
    }

    protected int getEditTextColor(Context context) {
        return this.mPrimaryColor;
    }

    protected int getShareTitleAddMarginTop(Context context) {
        return (int) context.getResources().getDimension(R.dimen.simple_theme_title_add_margin_top);
    }

    protected int getShareLineTop(Context context) {
        return (int) context.getResources().getDimension(R.dimen.simple_theme_share_line_top);
    }

    protected int getEndSignatureMarginTop(Context context) {
        return (int) context.getResources().getDimension(R.dimen.simple_theme_end_signature_top);
    }

    protected int getEndSignatureMarginBottom(Context context) {
        return (int) context.getResources().getDimension(R.dimen.simple_theme_end_signature_bottom);
    }

    public int getIconColor() {
        return this.mIconColor;
    }

    @Override // com.miui.richeditor.theme.Theme
    public void clearEditContentStyle() {
        this.mEditorStyle = null;
    }

    protected int getWidgetTitleBackground() {
        return UIUtils.isNightMode(NoteApp.getInstance()) ? R.drawable.v12_note_widget_head_bg_dark : R.drawable.v12_note_widget_head_bg;
    }
}
