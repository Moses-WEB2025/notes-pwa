package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.notes.theme.drawable.TextDrawable;
import com.miui.notes.theme.drawable.TileDrawable;
import com.miui.notes.tool.ResourceParser;
import com.miui.richeditor.theme.Theme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class DarkTheme extends SimpleTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_common_bg_highlight_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 150;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_dark_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_dark_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_dark_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_common_primary_color_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextGravity(Context context) {
        return GravityCompat.START;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_dark_editor_hint_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginTop(Context context) {
        return -1;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return R.style.V8_TextAppearance_Grid_Tertiary_Dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getIconColor() {
        return R.color.note_item_checked_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getShareLineTop(Context context) {
        return 0;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTouchDeleteForegroundColorRes() {
        return R.color.touch_delete_operation_dark_foreground_color;
    }

    public DarkTheme(int i) {
        super(i, R.color.theme_common_primary_color_dark, R.color.theme_common_secondary_color_dark, R.color.theme_common_headinfo_color_dark, R.color.theme_common_secondary_color_dark);
        this.mThemeStyle = R.style.NoteTheme_Edit_Dark;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_thumb_dark);
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_dark);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_default_shadow);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_DARK;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        return TOOL_BAR_STYLE_DARK;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_dark, R.color.link_card_link_text_color_dark});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getShareBackground(Context context, long j) {
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.v8_theme_share_inset);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{getBackgroundDrawable(context), getSignatureDrawable(context), new InsetDrawable(getForegroundDrawable(context, j), 0, dimensionPixelSize, 0, dimensionPixelSize)});
        layerDrawable.setLayerInset(1, 0, 0, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_dark_share_signature_margin_bottom));
        layerDrawable.setLayerGravity(1, 81);
        return layerDrawable;
    }

    private Drawable getSignatureDrawable(Context context) {
        Resources resources = context.getResources();
        return new TextDrawable.Builder(context).setTextSize(resources.getDimensionPixelSize(R.dimen.sns_footer_font_size)).setTexts(resources.getString(R.string.sns_footer_text)).setTextColor(resources.getColor(R.color.v8_theme_dark_share_signature_text_color)).build();
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        TileDrawable tileDrawable = new TileDrawable(context.getResources(), new int[]{R.drawable.v8_theme_dark_bg_1, R.drawable.v8_theme_dark_bg_2});
        tileDrawable.setTileMode(new int[][]{new int[]{0, 1}, new int[]{1, 0}});
        return new LayerDrawable(new Drawable[]{tileDrawable, context.getResources().getDrawable(R.drawable.v8_theme_dark_shadow)});
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM.d", Locale.US);
        Date date = new Date(j);
        String str = simpleDateFormat.format(date);
        String str2 = new SimpleDateFormat("yyyy", Locale.US).format(date);
        float specialFontSize = ResourceParser.TextAppearanceResources.getSpecialFontSize(context, 1);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{context.getResources().getDrawable(R.drawable.v8_theme_dark_bg_frame_inset), context.getResources().getDrawable(R.drawable.v8_theme_dark_signed_point), new TextDrawable.Builder(context).setTexts(str, str2).setTextSizes(0.8f * specialFontSize, specialFontSize * 0.9f).setTypeface(Typeface.create("times", 0)).setTextColor(context.getResources().getColor(getEditTextColor(context))).setPadding(0, 0, 0, context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_text_layer_padding)).setTextGravity(1).build()});
        layerDrawable.setLayerGravity(1, 81);
        layerDrawable.setLayerGravity(2, 81);
        layerDrawable.setLayerInset(2, 0, 0, 0, context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_year_layer_inset));
        Resources resources = context.getResources();
        this.mFgBorderPadding = new Rect();
        this.mFgBorderPadding.left = resources.getDimensionPixelSize(R.dimen.theme_dark_out_padding_left);
        this.mFgBorderPadding.right = resources.getDimensionPixelSize(R.dimen.theme_dark_out_padding_right);
        this.mFgBorderPadding.top = resources.getDimensionPixelSize(R.dimen.v8_theme_dark_year_layer_inset);
        this.mFgBorderPadding.bottom = resources.getDimensionPixelSize(R.dimen.v8_theme_dark_padding_bottom);
        return layerDrawable;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            Resources resources = context.getResources();
            this.mFgPadding = new Rect(resources.getDimensionPixelSize(R.dimen.theme_dark_header_padding), resources.getDimensionPixelSize(R.dimen.v8_theme_dark_padding_top), resources.getDimensionPixelSize(R.dimen.v8_theme_dark_padding_top), resources.getDimensionPixelSize(R.dimen.v8_theme_dark_padding_bottom));
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_ic_grid_item_frame);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        TileDrawable tileDrawable = new TileDrawable(context.getResources(), new int[]{R.drawable.v8_theme_dark_bg_1, R.drawable.v8_theme_dark_bg_2});
        tileDrawable.setTileMode(new int[][]{new int[]{0, 1}, new int[]{1, 0}});
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_grid_padding_left);
        tileDrawable.setPadding(dimensionPixelSize, context.getResources().getDimensionPixelSize(R.dimen.v11_theme_dark_grid_padding_top), dimensionPixelSize, context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_grid_padding_bottom));
        return tileDrawable;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Dark : R.style.V8_TextAppearance_Grid_Primary_Dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Dark : R.style.V8_TextAppearance_Grid_Secondary_Dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        return ACTION_BAR_STYLE_DARK;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_bottom);
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_top);
    }
}
