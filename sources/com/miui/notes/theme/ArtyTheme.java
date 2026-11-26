package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.ArtyBackgroundDrawable;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class ArtyTheme extends SimpleTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.note_edit_bg_highlight_arty;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_arty;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_arty;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_arty;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_arty_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_arty_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_arty_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_arty;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_dark_text_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_arty_editor_hint_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBorderWidth(Context context) {
        return 1;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return R.style.V8_TextAppearance_Grid_Tertiary_Arty;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getIconColor() {
        return 0;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getShareLineTop(Context context) {
        return 0;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTouchDeleteForegroundColorRes() {
        return R.color.touch_delete_operation_default_foreground_color;
    }

    public ArtyTheme(int i) {
        super(i, R.color.theme_common_primary_color_light, R.color.theme_common_secondary_color_light, R.color.theme_common_secondary_color_light, R.color.theme_icon_color_light);
        this.mUseDarkCheckBox = false;
        this.mThemeStyle = R.style.NoteTheme_Edit_Arty;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_thumb_arty);
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_arty);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_arty, R.color.link_card_link_text_color_arty});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Resources resources = context.getResources();
        ArtyBackgroundDrawable artyBackgroundDrawable = new ArtyBackgroundDrawable(resources, new int[]{R.drawable.v8_theme_arty_bg_left_inset, R.drawable.v8_theme_arty_bg_right_inset});
        artyBackgroundDrawable.setTileMode(new int[]{0, 3, 1, 5});
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new ColorDrawable(resources.getColor(R.color.v8_theme_arty_bg_color)), resources.getDrawable(R.drawable.v8_theme_arty_bg_top_left), resources.getDrawable(R.drawable.v8_theme_arty_bg_top_right), resources.getDrawable(R.drawable.v8_theme_arty_bg_bottom_left), resources.getDrawable(R.drawable.v8_theme_arty_bg_bottom_right), artyBackgroundDrawable});
        layerDrawable.setLayerGravity(1, 51);
        layerDrawable.setLayerGravity(2, 53);
        layerDrawable.setLayerGravity(3, 83);
        layerDrawable.setLayerGravity(4, 85);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.v8_theme_arty_bg_inset_bottom);
        layerDrawable.setLayerInset(2, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_arty_bg_inset_right_top), 0, 0);
        layerDrawable.setLayerInset(5, 0, 0, 0, dimensionPixelSize);
        return layerDrawable;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        Resources resources = context.getResources();
        this.mFgBorderPadding = new Rect();
        Rect rect = this.mFgBorderPadding;
        Rect rect2 = this.mFgBorderPadding;
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.theme_arty_inset_padding_left_right);
        rect2.right = dimensionPixelSize;
        rect.left = dimensionPixelSize;
        this.mFgBorderPadding.top = 0;
        this.mFgBorderPadding.bottom = resources.getDimensionPixelSize(R.dimen.v8_theme_arty_fg_inset_bottom);
        return resources.getDrawable(R.drawable.v12_theme_arty_bg_frame_inset);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getShareBackground(Context context, long j) {
        Resources resources = context.getResources();
        new ArtyBackgroundDrawable(resources, new int[]{R.drawable.v8_theme_arty_bg_left_inset, R.drawable.v8_theme_arty_bg_right_inset}).setTileMode(new int[]{0, 3, 1, 5});
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new ColorDrawable(resources.getColor(R.color.v8_theme_arty_bg_color)), resources.getDrawable(R.drawable.v8_theme_arty_bg_top_left), resources.getDrawable(R.drawable.v8_theme_arty_bg_top_right), resources.getDrawable(R.drawable.v8_theme_arty_bg_bottom_left), resources.getDrawable(R.drawable.v8_theme_arty_bg_bottom_right)});
        layerDrawable.setLayerGravity(1, 51);
        layerDrawable.setLayerGravity(2, 53);
        layerDrawable.setLayerGravity(3, 83);
        layerDrawable.setLayerGravity(4, 85);
        resources.getDimensionPixelSize(R.dimen.v8_theme_arty_bg_inset_top);
        resources.getDimensionPixelSize(R.dimen.v8_theme_arty_bg_inset_bottom);
        layerDrawable.setLayerInset(2, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_arty_bg_inset_right_top), 0, 0);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_share_inset);
        return new LayerDrawable(new Drawable[]{layerDrawable, new InsetDrawable(getForegroundDrawable(context, j), 0, dimensionPixelSize, 0, dimensionPixelSize)});
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            Resources resources = context.getResources();
            this.mFgPadding = new Rect();
            this.mFgPadding = new Rect(resources.getDimensionPixelSize(R.dimen.theme_arty_title_fg_inset_padding), resources.getDimensionPixelSize(R.dimen.v8_theme_arty_fg_inset_top), resources.getDimensionPixelSize(R.dimen.v8_theme_arty_fg_inset_right), resources.getDimensionPixelSize(R.dimen.v8_theme_arty_fg_inset_bottom));
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBorderColor(Context context) {
        return context.getResources().getColor(R.color.theme_main_color);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        return context.getResources().getDrawable(R.drawable.v8_theme_arty_grid_bg);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Light : R.style.V8_TextAppearance_Grid_Primary_Light;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Light : R.style.V8_TextAppearance_Grid_Secondary_Light;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_default_shadow);
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        this.mActionBarStyle = super.getActionBarStyle(context);
        return this.mActionBarStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_bottom_art);
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_top_art);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.art_theme_end_signature_top);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.art_theme_end_signature_bottom);
    }
}
