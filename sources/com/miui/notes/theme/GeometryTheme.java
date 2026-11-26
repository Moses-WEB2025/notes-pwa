package com.miui.notes.theme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.DecorationDrawable;
import com.miui.richeditor.theme.Theme;
import miuix.graphics.BitmapFactory;

/* JADX INFO: loaded from: classes4.dex */
public class GeometryTheme extends AnimTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_geometry_bg_highlight_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_geometry;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_geometry;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_geometry;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 150;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_geometry_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_geometry_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_geometry_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_geometry;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_geometry_editor_hint_color;
    }

    public GeometryTheme(int i) {
        super(i, R.drawable.v12_theme_thumb_geometry, R.string.theme_title_geometry, R.color.theme_geometry_primary_color, R.color.theme_geometry_secondary_color, R.color.theme_geometry_headinfo_color, R.color.theme_geometry_icon_color_n, UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Geometry : R.style.V8_TextAppearance_Grid_Primary_Geometry, R.style.V12_TextAppearance_Grid_Secondary_Geometry, R.style.V12_TextAppearance_Grid_Tertiary_Geometry, R.color.theme_geometry_cursor_color, R.color.theme_geometry_cursor_handle_color, R.color.theme_geometry_high_light_color, R.color.theme_geometry_linked_color, R.color.theme_geometry_done_text_color);
        this.mResName = "theme_geometry";
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_grid_geometry);
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ToolBarStyle getToolBarStyle(Context context) {
        return TOOL_BAR_STYLE_DARK;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_geometry, R.color.link_card_link_text_color_geometry});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        Bitmap imageFromAssetsFile = getImageFromAssetsFile(context, "animtheme/theme_geometry/bg.png", 1.0f);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), imageFromAssetsFile);
        Bitmap imageFromAssetsFile2 = getImageFromAssetsFile(context, "animtheme/theme_geometry/mask1.png", 1.0f);
        Bitmap imageFromAssetsFile3 = getImageFromAssetsFile(context, "animtheme/theme_geometry/mask2.png", 1.0f);
        float f = (context.getResources().getDisplayMetrics().density * 0.5f) / 2.75f;
        Bitmap imageFromAssetsFile4 = getImageFromAssetsFile(context, "animtheme/theme_geometry/geometry1.png", f);
        Bitmap imageFromAssetsFile5 = getImageFromAssetsFile(context, "animtheme/theme_geometry/geometry2.png", f);
        Bitmap imageFromAssetsFile6 = getImageFromAssetsFile(context, "animtheme/theme_geometry/geometry3.png", f);
        DecorationDrawable decorationDrawable = new DecorationDrawable(context.getResources(), new Bitmap[]{getImageFromAssetsFile(context, "animtheme/theme_geometry/geometry4.png", f), imageFromAssetsFile5, imageFromAssetsFile6, imageFromAssetsFile4, imageFromAssetsFile2, imageFromAssetsFile3});
        decorationDrawable.setTileMode(new int[]{0, 53, -265, 160, 1, 51, 80, 0, 2, 51, 80, 600, 3, 53, -15, 80, 4, 51, 0, 0, 5, 53, 0, 0});
        Bitmap bitmapComposeBitmap = BitmapFactory.composeBitmap(imageFromAssetsFile, null, null, decorationDrawable, bitmapDrawable);
        imageFromAssetsFile.recycle();
        return new BitmapDrawable(context.getResources(), bitmapComposeBitmap);
    }
}
