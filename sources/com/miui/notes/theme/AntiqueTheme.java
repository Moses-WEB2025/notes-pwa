package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.DecorationDrawable;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.notes.theme.drawable.RectBitmapDrawable;
import com.miui.notes.theme.drawable.TextDrawable;
import com.miui.notes.theme.drawable.TileDrawable;
import com.miui.notes.tool.util.DateHelper;
import com.miui.richeditor.theme.Theme;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class AntiqueTheme extends SimpleTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.note_edit_bg_highlight_antique;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_antique;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_antique;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_antique;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 120;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_antique_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_antique_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_antique_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_antique;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_common_primary_color_light;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_antique_editor_hint_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginTop(Context context) {
        return -1;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return R.style.V8_TextAppearance_Grid_Tertiary_Antique;
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

    public AntiqueTheme(int i) {
        super(i, R.color.theme_common_primary_color_light, R.color.theme_common_secondary_color_light, R.color.theme_common_headinfo_color_light, R.color.theme_icon_color_light);
        this.mUseDarkCheckBox = false;
        this.mThemeStyle = R.style.NoteTheme_Edit_Antique;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_thumb_antique);
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_antique);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_antique_shadow);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_antique, R.color.link_card_link_text_color_antique});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Resources resources = context.getResources();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) resources.getDrawable(R.drawable.v8_theme_antique_bg);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        DecorationDrawable decorationDrawable = new DecorationDrawable(resources, new int[]{R.drawable.v8_theme_antique_decoration_2, R.drawable.v8_theme_antique_decoration_3, R.drawable.v8_theme_antique_decoration_4});
        decorationDrawable.setTileMode(new int[]{0, 53, resources.getDimensionPixelSize(R.dimen.v8_theme_antique_decoration_inset_right), resources.getDimensionPixelSize(R.dimen.v8_theme_antique_decoration_inset_top), 1, 0, 50, 50, 2, 0, 20, 60});
        return new LayerDrawable(new Drawable[]{bitmapDrawable, decorationDrawable});
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        LayerDrawable layerDrawable;
        Resources resources = context.getResources();
        RectBitmapDrawable rectBitmapDrawable = new RectBitmapDrawable(resources, new int[]{R.drawable.v8_theme_antique_h_1, R.drawable.v8_theme_antique_h_2, R.drawable.v8_theme_antique_h_3}, new int[]{R.drawable.v8_theme_antique_v});
        rectBitmapDrawable.setTileMode(new int[]{0, 1, 2, 2}, new int[]{0});
        rectBitmapDrawable.setPadding(resources.getDimensionPixelSize(R.dimen.theme_antique_bg_layer_padding_left), resources.getDimensionPixelSize(R.dimen.v8_theme_antique_title_bg_layer_padding_padding), resources.getDimensionPixelSize(R.dimen.theme_antique_bg_layer_padding_left), resources.getDimensionPixelSize(R.dimen.v8_theme_antique_bg_layer_padding_bottom));
        this.mFgBorderPadding = new Rect();
        rectBitmapDrawable.getPadding(this.mFgBorderPadding);
        if (!isChineseLanguage()) {
            layerDrawable = new LayerDrawable(new Drawable[]{rectBitmapDrawable, resources.getDrawable(R.drawable.v8_theme_antique_decoration_1)});
        } else {
            LayerDrawable layerDrawable2 = new LayerDrawable(new Drawable[]{rectBitmapDrawable, resources.getDrawable(R.drawable.v8_theme_antique_decoration_1), resources.getDrawable(R.drawable.theme_antique_watermark), new TextDrawable.Builder(context).setTexts(DateHelper.getDateAsChineseText(j)).setTextColor(resources.getColor(getEditTextColor(context))).setTextSize(resources.getDimensionPixelSize(R.dimen.sns_footer_font_size)).setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_dark_text_layer_padding)).setTextGravity(16).build()});
            layerDrawable2.setLayerGravity(2, 85);
            layerDrawable2.setLayerGravity(3, 80);
            layerDrawable2.setLayerInset(2, 0, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_antique_watermark_layer_inset_right), 0);
            layerDrawable2.setLayerInset(3, 0, 0, 0, (-resources.getDimensionPixelSize(R.dimen.v8_theme_antique_bg_layer_padding_bottom)) / 2);
            layerDrawable2.setLayerHeight(3, resources.getDimensionPixelOffset(R.dimen.v8_theme_antique_date_layer_height));
            layerDrawable = layerDrawable2;
        }
        layerDrawable.setLayerGravity(1, 53);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.v8_theme_antique_bg_layer_inset);
        layerDrawable.setLayerInset(0, dimensionPixelSize, resources.getDimensionPixelSize(R.dimen.v8_theme_antique_bg_layer_inset_top), dimensionPixelSize, dimensionPixelSize);
        layerDrawable.setLayerInset(1, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_antique_decoration_layer_inset_top), resources.getDimensionPixelSize(R.dimen.v8_theme_antique_decoration_layer_inset_right), 0);
        return layerDrawable;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            Resources resources = context.getResources();
            this.mFgPadding = new Rect(resources.getDimensionPixelSize(R.dimen.theme_antique_bg_layer_padding_left), resources.getDimensionPixelSize(R.dimen.v8_theme_antique_decoration_layer_inset_top), resources.getDimensionPixelSize(R.dimen.theme_antique_bg_layer_padding_left), resources.getDimensionPixelSize(R.dimen.v8_theme_share_inset));
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        Resources resources = context.getResources();
        TileDrawable tileDrawable = new TileDrawable(resources, new int[]{R.drawable.v8_theme_antique_bg});
        tileDrawable.setTileMode(new int[][]{new int[]{0}});
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.v8_theme_antique_grid_inset);
        tileDrawable.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        RectBitmapDrawable rectBitmapDrawable = new RectBitmapDrawable(resources, new int[]{R.drawable.v8_theme_antique_h_1, R.drawable.v8_theme_antique_h_2, R.drawable.v8_theme_antique_h_3}, new int[]{R.drawable.v8_theme_antique_v});
        rectBitmapDrawable.setTileMode(new int[]{0, 1, 2, 2}, new int[]{0});
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.v8_theme_antique_grid_padding);
        rectBitmapDrawable.setPadding(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, 0);
        if (!isChineseLanguage()) {
            return new LayerDrawable(new Drawable[]{tileDrawable, rectBitmapDrawable});
        }
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{tileDrawable, rectBitmapDrawable, resources.getDrawable(R.drawable.theme_antique_grid_watermark)});
        layerDrawable.setLayerGravity(2, 85);
        layerDrawable.setLayerInset(2, 0, 0, resources.getDimensionPixelSize(R.dimen.v8_theme_antique_watermark_grid_inset_right), resources.getDimensionPixelSize(R.dimen.v8_theme_antique_watermark_grid_inset_bottom));
        return layerDrawable;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Light : R.style.V8_TextAppearance_Grid_Primary_Light;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Light : R.style.V8_TextAppearance_Grid_Secondary_Light;
    }

    private boolean isChineseLanguage() {
        return Locale.getDefault().getLanguage().equals(Locale.SIMPLIFIED_CHINESE.getLanguage()) && Locale.getDefault().getCountry().equals(Locale.SIMPLIFIED_CHINESE.getCountry());
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        this.mActionBarStyle = super.getActionBarStyle(context);
        return this.mActionBarStyle;
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_bottom_ant);
    }

    @Override // com.miui.richeditor.theme.Theme
    public int getTitleMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.edit_margin_top_ant);
    }
}
