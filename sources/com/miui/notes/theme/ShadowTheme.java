package com.miui.notes.theme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.DecorationDrawable;
import com.miui.richeditor.theme.Theme;
import miuix.graphics.BitmapFactory;

/* JADX INFO: loaded from: classes4.dex */
public class ShadowTheme extends AnimTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_shadow_bg_highlight_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_shadow;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_shadow;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_shadow;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 150;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_shadow_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_shadow_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_shadow_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_shadow;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_shadow_editor_hint_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBorderWidth(Context context) {
        return 1;
    }

    public ShadowTheme(int i) {
        super(i, R.drawable.v12_theme_thumb_shadow, R.string.theme_title_shadow, R.color.theme_shadow_primary_color, R.color.theme_shadow_secondary_color, R.color.theme_shadow_headinfo_color, R.color.theme_shadow_icon_color_n, UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Shadow : R.style.V8_TextAppearance_Grid_Primary_Shadow, R.style.V12_TextAppearance_Grid_Secondary_Shadow, R.style.V12_TextAppearance_Grid_Tertiary_Shadow, R.color.theme_shadow_cursor_color, R.color.theme_shadow_cursor_handle_color, R.color.theme_shadow_high_light_color, R.color.theme_shadow_linked_color, R.color.theme_shadow_done_text_color);
        this.mResName = "theme_shadow";
        this.mThemeStyle = R.style.NoteTheme_Edit_Shadow;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_grid_shadow);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getGridBorderColor(Context context) {
        return context.getResources().getColor(R.color.theme_shadow_grid_border_color);
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_shadow_shadow);
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        return STATUS_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ActionBarStyle getActionBarStyle(Context context) {
        if (this.mActionBarStyle == null) {
            this.mActionBarStyle = new Theme.ActionBarStyle(ACTION_BAR_STYLE_ARRAY, getResTheme(context));
            this.mActionBarStyle.setHeaderInfoColor(this.mHeaderInfoTextColor);
        }
        return this.mActionBarStyle;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        if (this.mToolBarStyle == null) {
            this.mToolBarStyle = new Theme.ToolBarStyle(TOOL_BAR_STYLE_ARRAY, getResTheme(context));
        }
        return this.mToolBarStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_shadow, R.color.link_card_link_text_color_shadow});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Drawable drawable = context.getDrawable(R.drawable.note_theme_shadow_bg);
        int i = context.getResources().getDisplayMetrics().widthPixels;
        int i2 = context.getResources().getDisplayMetrics().heightPixels;
        drawable.setBounds(0, 0, i, i2);
        float f = (context.getResources().getDisplayMetrics().density * 2.0f) / 2.75f;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        DecorationDrawable decorationDrawable = new DecorationDrawable(context.getResources(), new Bitmap[]{getImageFromAssetsFile(context, "animtheme/theme_shadow/leaf1.png", f), getImageFromAssetsFile(context, "animtheme/theme_shadow/leaf2.png", f, -20.0f), getImageFromAssetsFile(context, "animtheme/theme_shadow/leaf3.png", f)});
        decorationDrawable.setTileMode(new int[]{0, 51, 250, -12, 1, 51, 0, -250, 2, 53, -100, 0});
        Bitmap bitmapComposeBitmap = BitmapFactory.composeBitmap(bitmapCreateBitmap, null, null, decorationDrawable, drawable);
        bitmapCreateBitmap.recycle();
        return new BitmapDrawable(context.getResources(), bitmapComposeBitmap);
    }
}
