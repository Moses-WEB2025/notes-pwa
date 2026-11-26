package com.miui.notes.theme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LightColorTheme extends ColorTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.title_primary_text_light;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return R.style.V8_TextAppearance_Grid_Tertiary_Light;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return R.style.V8_TextAppearance_Grid_Primary_Light;
    }

    public LightColorTheme(int i, int i2) {
        super(i, i2, 0, 0, R.color.note_edit_extra_info_text_color_light, 0);
        this.mThemeStyle = R.style.NoteTheme_Edit_Light;
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
        return TOOL_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return context.getResources().getDrawable(R.drawable.v8_ic_grid_item_frame_light);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Light : R.style.V8_TextAppearance_Grid_Secondary_Light;
    }
}
