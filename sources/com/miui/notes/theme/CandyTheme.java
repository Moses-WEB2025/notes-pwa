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
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class CandyTheme extends SimpleTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_candy_bg_highlight_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_candy;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_candy;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_candy;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 120;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_candy_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_candy_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_candy_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_candy;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_candy_primary_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_candy_editor_hint_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginBottom(Context context) {
        return 1;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return R.style.V8_TextAppearance_Grid_Tertiary_Candy;
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

    public CandyTheme(int i) {
        super(i, R.color.theme_candy_primary_color, R.color.theme_candy_secondary_color, R.color.theme_candy_headinfo_color, R.color.theme_icon_color_light);
        this.mUseDarkCheckBox = false;
        this.mThemeStyle = R.style.NoteTheme_Edit_Candy;
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_thumb_candy);
    }

    @Override // com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_candy);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_candy_shadow);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_candy, R.color.link_card_link_text_color_candy});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Resources resources = context.getResources();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) resources.getDrawable(R.drawable.v8_theme_candy_bg);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bitmapDrawable, resources.getDrawable(R.drawable.v8_theme_candy_bg_top), resources.getDrawable(R.drawable.v8_theme_candy_bg_bottom_left), resources.getDrawable(R.drawable.v8_theme_candy_bg_bottom_right)});
        layerDrawable.setLayerGravity(1, 53);
        layerDrawable.setLayerGravity(2, 83);
        layerDrawable.setLayerGravity(3, 85);
        return layerDrawable;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        return context.getResources().getDrawable(R.drawable.v8_theme_candy_frame);
    }

    @Override // com.miui.richeditor.theme.Theme
    public Rect getForegroundPadding(Context context) {
        if (this.mFgPadding == null) {
            Resources resources = context.getResources();
            this.mFgPadding = new Rect();
            resources.getDrawable(R.drawable.v8_theme_candy_frame).getPadding(this.mFgPadding);
        }
        return this.mFgPadding;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.v8_theme_candy_bg);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return new LayerDrawable(new Drawable[]{bitmapDrawable, context.getResources().getDrawable(R.drawable.v8_theme_candy_grid_fg)});
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Candy : R.style.V8_TextAppearance_Grid_Primary_Candy;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Candy : R.style.V8_TextAppearance_Grid_Secondary_Candy;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        if (this.mActionBarStyle == null) {
            this.mActionBarStyle = new Theme.ActionBarStyle(ACTION_BAR_STYLE_ARRAY, getResTheme(context));
            this.mActionBarStyle.setHeaderInfoColor(this.mHeaderInfoTextColor);
        }
        return this.mActionBarStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        if (this.mToolBarStyle == null) {
            this.mToolBarStyle = new Theme.ToolBarStyle(TOOL_BAR_STYLE_ARRAY, getResTheme(context));
        }
        return this.mToolBarStyle;
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
        return context.getResources().getDimensionPixelSize(R.dimen.candy_theme_end_signature_top);
    }
}
