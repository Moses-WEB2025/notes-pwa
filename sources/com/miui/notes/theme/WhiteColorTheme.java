package com.miui.notes.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.drawable.LayerDrawable;
import com.miui.notes.theme.drawable.TileDrawable;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class WhiteColorTheme extends LightColorTheme {
    private Theme.StatusBarStyle mStatusBarStyle;

    @Override // com.miui.notes.theme.LightColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_color_white_t_color;
    }

    @Override // com.miui.notes.theme.LightColorTheme, com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return R.style.V8_DefaultTheme_TextAppearance_Grid_Primary_Light;
    }

    public WhiteColorTheme(int i, int i2) {
        super(i, i2);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Drawable getIcon(Context context) {
        return context.getResources().getDrawable(R.drawable.v8_ic_theme_default);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public String getName(Context context) {
        return context.getResources().getString(R.string.theme_title_default);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Drawable getIconBg(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.v12_theme_thumb_default_shadow);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        if (this.mStatusBarStyle == null) {
            Theme.StatusBarStyle statusBarStyle = new Theme.StatusBarStyle();
            this.mStatusBarStyle = statusBarStyle;
            statusBarStyle.mFixed = false;
            this.mStatusBarStyle.mStatusBar = 1;
        }
        return this.mStatusBarStyle;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.ShareStyle getShareStyle(Context context, long j) {
        Theme.ShareStyle shareStyle = super.getShareStyle(context, j);
        shareStyle.mTextColor = R.color.v8_theme_white_share_text_color;
        shareStyle.mShowFooterDivider = false;
        return shareStyle;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected Drawable getShareBackground(Context context, long j) {
        Resources resources = context.getResources();
        TileDrawable tileDrawable = new TileDrawable(resources, new int[]{R.drawable.v8_theme_white_share_bg_1, R.drawable.v8_theme_white_share_bg_2});
        tileDrawable.setTileMode(new int[][]{new int[]{0, 1}});
        return new LayerDrawable(new Drawable[]{tileDrawable, resources.getDrawable(R.drawable.theme_white_share_frame), resources.getDrawable(R.drawable.v8_theme_white_share_bg)});
    }

    @Override // com.miui.notes.theme.LightColorTheme, com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V11_DefaultTheme_TextAppearance_Grid_Secondary_Light : R.style.V8_DefaultTheme_TextAppearance_Grid_Secondary_Light;
    }
}
