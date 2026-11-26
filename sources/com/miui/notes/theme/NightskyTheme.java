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
import defpackage.R2;
import miuix.graphics.BitmapFactory;

/* JADX INFO: loaded from: classes4.dex */
public class NightskyTheme extends AnimTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_nightsky_bg_highlight_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_nightsky;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_nightsky;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_nightsky;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 120;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_nightsky_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_nightsky_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_nightsky_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_nightsky;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_nightsky_editor_hint_color;
    }

    public NightskyTheme(int i) {
        super(i, R.drawable.v12_theme_thumb_nightsky, R.string.theme_title_nightsky, R.color.theme_nightsky_primary_color, R.color.theme_nightsky_secondary_color, R.color.theme_nightsky_headinfo_color, R.color.theme_nightsky_icon_color_n, UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Nightsky : R.style.V8_TextAppearance_Grid_Primary_Nightsky, R.style.V12_TextAppearance_Grid_Secondary_Nightsky, R.style.V12_TextAppearance_Grid_Tertiary_Nightsky, R.color.theme_nightsky_cursor_color, R.color.theme_nightsky_cursor_handle_color, R.color.theme_nightsky_high_light_color, R.color.theme_nightsky_linked_color, R.color.theme_nightsky_done_text_color);
        this.mResName = "theme_nightsky";
        this.mThemeStyle = R.style.NoteTheme_Edit_NightSky;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_grid_nightsky);
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_nightsky_shadow);
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
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_nightsky, R.color.link_card_link_text_color_nightsky});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Drawable drawable = context.getDrawable(R.drawable.note_theme_nightsky_bg);
        int i = context.getResources().getDisplayMetrics().widthPixels;
        int i2 = context.getResources().getDisplayMetrics().heightPixels;
        float f = (context.getResources().getDisplayMetrics().density * 2.0f) / 2.75f;
        drawable.setBounds(0, 0, i, i2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Bitmap imageFromAssetsFile = getImageFromAssetsFile(context, "animtheme/theme_nightsky/star1.png", f);
        Bitmap imageFromAssetsFile2 = getImageFromAssetsFile(context, "animtheme/theme_nightsky/star2.png", f);
        Bitmap imageFromAssetsFile3 = getImageFromAssetsFile(context, "animtheme/theme_nightsky/star3.png", f);
        DecorationDrawable decorationDrawable = new DecorationDrawable(context.getResources(), new Bitmap[]{getImageFromAssetsFile(context, "animtheme/theme_nightsky/star4.png", f), imageFromAssetsFile3, imageFromAssetsFile2, imageFromAssetsFile});
        decorationDrawable.setTileMode(new int[]{0, 51, 72, R2.attr.actionIconDisabledAlpha, 1, 51, 38, 108, 2, 51, R2.attr.buttonInlineAddBackground, R2.attr.actionBarDivider, 3, 51, 64, R2.attr.actionBarDivider});
        Bitmap bitmapComposeBitmap = BitmapFactory.composeBitmap(bitmapCreateBitmap, null, null, decorationDrawable, drawable);
        bitmapCreateBitmap.recycle();
        return new BitmapDrawable(context.getResources(), bitmapComposeBitmap);
    }
}
