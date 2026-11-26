package com.miui.notes.theme;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import com.miui.common.tool.DisplayUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DayNightColorTheme extends ColorTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_common_primary_color;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    public DayNightColorTheme(int i, int i2) {
        super(i, i2, R.color.theme_common_primary_color, R.color.theme_common_secondary_color, R.color.note_edit_extra_info_text_color, 0);
        this.mThemeStyle = R.style.NoteTheme_Edit;
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

    @Override // com.miui.notes.theme.ColorTheme, com.miui.richeditor.theme.Theme
    public Theme.StatusBarStyle getStatusBarStyle(Context context) {
        if (DisplayUtils.isNightMode()) {
            return STATUS_BAR_STYLE_DARK;
        }
        return STATUS_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_DefaultTheme_TextAppearance_Grid_Primary_DayNight : R.style.V8_TextAppearance_Grid_Primary_DayNight;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_DefaultTheme_TextAppearance_Grid_Secondary_DayNight : R.style.V8_TextAppearance_Grid_Secondary_DayNight;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Tertiary_DayNight : R.style.V8_TextAppearance_Grid_Tertiary_DayNight;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        Drawable drawableMutate = context.getDrawable(R.drawable.v12_grid_item_bg).mutate();
        drawableMutate.setColorFilter(context.getResources().getColor(R.color.v12_theme_content_bg_color), PorterDuff.Mode.SRC_ATOP);
        return drawableMutate;
    }
}
