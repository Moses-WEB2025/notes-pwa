package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.notes.theme.drawable.TileDrawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class StarTheme extends SimpleTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.note_edit_bg_highlight_star;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_star;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_star;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_star;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 120;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_star_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_star_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_star_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_star;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.title_primary_text_dark;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_star_editor_hint_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
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

    public StarTheme(int i) {
        super(i, R.color.title_primary_text_dark, R.color.title_secondary_text_dark, R.color.header_info_text_color_dark, R.color.title_secondary_text_dark);
        this.mThemeStyle = R.style.NoteTheme_Edit_Star;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_thumb_star);
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_star);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_star_shadow);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_DARK;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        if (this.mToolBarStyle == null) {
            this.mToolBarStyle = new Theme.ToolBarStyle(TOOL_BAR_STYLE_ARRAY, getResTheme(context));
        }
        return this.mToolBarStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_star, R.color.link_card_link_text_color_star});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Resources resources = context.getResources();
        TileDrawable tileDrawable = new TileDrawable(context.getResources(), new int[]{R.drawable.v8_theme_star_bg_1, R.drawable.v8_theme_star_bg_2, R.drawable.v8_theme_star_bg_3});
        tileDrawable.setTileMode(new int[][]{new int[]{0}, new int[]{1}, new int[]{2}});
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{resources.getDrawable(R.drawable.v8_theme_star_bg), tileDrawable, resources.getDrawable(R.drawable.v8_theme_star_bg_top), resources.getDrawable(R.drawable.v8_theme_star_bg_bottom)});
        layerDrawable.setLayerGravity(2, 49);
        layerDrawable.setLayerGravity(3, 88);
        return layerDrawable;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        return context.getResources().getDrawable(R.drawable.v12_theme_star_fg);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            this.mFgPadding = new Rect();
            context.getResources().getDrawable(R.drawable.v12_theme_star_fg).getPadding(this.mFgPadding);
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        Drawable ninePatchDrawable;
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.v12_theme_star_grid_bg);
        Rect rect = new Rect();
        rect.left = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_grid_padding_left);
        rect.right = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_grid_padding_left);
        rect.top = context.getResources().getDimensionPixelSize(R.dimen.v11_theme_dark_grid_padding_top);
        rect.bottom = context.getResources().getDimensionPixelSize(R.dimen.v8_theme_dark_grid_padding_bottom);
        if (NinePatch.isNinePatchChunk(bitmapDecodeResource.getNinePatchChunk())) {
            ninePatchDrawable = new NinePatchDrawable(context.getResources(), bitmapDecodeResource, bitmapDecodeResource.getNinePatchChunk(), rect, null);
        } else {
            TileDrawable tileDrawable = new TileDrawable(context.getResources(), new int[]{R.drawable.v12_theme_star_grid_bg});
            tileDrawable.setTileMode(new int[][]{new int[]{0}});
            tileDrawable.setPadding(rect.left, rect.top, rect.right, rect.bottom);
            ninePatchDrawable = tileDrawable;
        }
        TileDrawable tileDrawable2 = new TileDrawable(context.getResources(), new int[]{R.drawable.v8_theme_star_grid_fg});
        tileDrawable2.setTileMode(new int[][]{new int[]{0}});
        return new LayerDrawable(new Drawable[]{ninePatchDrawable, tileDrawable2});
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

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.star_theme_end_signature_top);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginBottom(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.star_theme_end_signature_bottom);
    }
}
