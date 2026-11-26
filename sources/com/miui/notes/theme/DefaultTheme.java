package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.notes.theme.drawable.TileDrawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultTheme extends DayNightColorTheme {
    @Override // com.miui.notes.theme.DayNightColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_common_primary_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getShareLineTop(Context context) {
        return 0;
    }

    public DefaultTheme(int i) {
        super(i, R.color.v8_theme_bg_color);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v12_theme_thumb_default);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_default);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_default_shadow);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ShareStyle getShareStyle(Context context, long j) {
        Theme.ShareStyle shareStyle = super.getShareStyle(context, j);
        shareStyle.mTextColor = R.color.v8_theme_share_text_color;
        shareStyle.mShowFooterDivider = false;
        return shareStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getShareBackground(Context context, long j) {
        Resources resources = context.getResources();
        TileDrawable tileDrawable = new TileDrawable(resources, new int[]{R.drawable.v8_theme_white_share_bg_1, R.drawable.v8_theme_white_share_bg_2});
        tileDrawable.setTileMode(new int[][]{new int[]{0, 1}});
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{tileDrawable, resources.getDrawable(R.drawable.v12_theme_white_share_frame), resources.getDrawable(R.drawable.v8_theme_white_share_bg)});
        int dimensionPixelSize = NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.theme_white_share_frame_top_space) + 1;
        int dimensionPixelSize2 = NoteApp.getInstance().getResources().getDimensionPixelSize(R.dimen.note_share_theme_default_frame_horizontal_space);
        layerDrawable.setPadding(dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2, 0);
        return layerDrawable;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.GridStyle getGridStyle(Context context) {
        Theme.GridStyle gridStyle = super.getGridStyle(context);
        gridStyle.mIconColor = 0;
        return gridStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginTop(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.v8_theme_share_signature_margin_top);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getShareTitleAddMarginTop(Context context) {
        return (int) context.getResources().getDimension(R.dimen.default_theme_title_add_margin_top);
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEndSignatureMarginBottom(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.sns_footer_text_margin_bottom_default);
    }
}
