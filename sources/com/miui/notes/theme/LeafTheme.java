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
public class LeafTheme extends AnimTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_leaf_bg_highlight_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor() {
        return R.color.note_edit_blur_mix_color_leaf;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor2() {
        return R.color.note_edit_blur_mix_color2_leaf;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    public int getBlurMixColor3() {
        return R.color.note_edit_blur_mix_color3_leaf;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBlurRadius() {
        return 150;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBgColor() {
        return R.color.theme_leaf_dir_indicator_bg_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorBorderColor() {
        return R.color.theme_leaf_dir_indicator_border_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getDirIndicatorIconColor() {
        return R.color.theme_leaf_dir_indicator_icon_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditInputBgColor() {
        return R.color.annotation_edit_bg_color_leaf;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.theme_leaf_editor_hint_color;
    }

    public LeafTheme(int i) {
        super(i, R.drawable.v12_theme_thumb_leaf, R.string.theme_title_leaf, R.color.theme_leaf_primary_color, R.color.theme_leaf_secondary_color, R.color.theme_leaf_headinfo_color, R.color.theme_leaf_icon_color_n, UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Primary_Leaf : R.style.V8_TextAppearance_Grid_Primary_Leaf, R.style.V12_TextAppearance_Grid_Secondary_Leaf, R.style.V12_TextAppearance_Grid_Tertiary_Leaf, R.color.theme_leaf_cursor_color, R.color.theme_leaf_cursor_handle_color, R.color.theme_leaf_high_light_color, R.color.theme_leaf_linked_color, R.color.theme_leaf_done_text_color);
        this.mResName = "theme_leaf";
        this.mThemeStyle = R.style.NoteTheme_Edit_Leaf;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_grid_leaf);
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_leaf_shadow);
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
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_leaf, R.color.link_card_link_text_color_leaf});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.AnimTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getBackgroundDrawable(Context context) {
        float f = (context.getResources().getDisplayMetrics().density * 1.8f) / 2.75f;
        Bitmap imageFromAssetsFile = getImageFromAssetsFile(context, "animtheme/theme_leaf/bg.webp", 1.0f);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), imageFromAssetsFile);
        DecorationDrawable decorationDrawable = new DecorationDrawable(context.getResources(), new Bitmap[]{getImageFromAssetsFile(context, "animtheme/theme_leaf/leaf1.png", f), getImageFromAssetsFile(context, "animtheme/theme_leaf/leaf2.png", f)});
        decorationDrawable.setTileMode(new int[]{0, 53, 0, 0, 1, 85, -50, 0});
        Bitmap bitmapComposeBitmap = BitmapFactory.composeBitmap(imageFromAssetsFile, null, null, decorationDrawable, bitmapDrawable);
        imageFromAssetsFile.recycle();
        return new BitmapDrawable(context.getResources(), bitmapComposeBitmap);
    }
}
