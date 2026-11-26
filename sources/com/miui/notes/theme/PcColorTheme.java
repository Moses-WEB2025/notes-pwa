package com.miui.notes.theme;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PcColorTheme extends ColorTheme {
    @Override // com.miui.notes.theme.SimpleTheme
    protected int getBgHighLightColor(Context context) {
        return R.color.theme_light_bg_highlight_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getCursorColor() {
        return R.color.theme_light_cursor_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditTextColor(Context context) {
        return R.color.theme_common_primary_color_light;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getEditorHintColor() {
        return R.color.note_title_hint_color_light;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridBorder(Context context) {
        return null;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTitleStyle(Context context) {
        return R.style.V12_PC_DefaultTheme_TextAppearance_Grid_Primary_DayNight;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getHandleColor() {
        return R.color.theme_light_cursor_handle_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getHighLightColor() {
        return R.color.theme_light_high_light_color;
    }

    @Override // com.miui.notes.theme.SimpleTheme
    protected int getLinkedColor() {
        return R.color.theme_light_linked_color;
    }

    public PcColorTheme(int i) {
        super(i, R.color.v8_theme_bg_color_light, R.color.theme_common_primary_color_light, R.color.theme_common_secondary_color_light, R.color.divider_line_light, 0);
        this.mThemeStyle = R.style.NoteTheme_Light_PcEdit;
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
        return STATUS_BAR_STYLE_LIGHT;
    }

    @Override // com.miui.notes.theme.SimpleTheme, com.miui.richeditor.theme.Theme
    public Theme.LinkCardStyle getLinkCardStyle(Context context) {
        if (this.mLinkCardStyle == null) {
            this.mLinkCardStyle = new Theme.LinkCardStyle(getResTheme(context), new int[]{R.color.link_card_title_text_color_light, R.color.link_card_link_text_color_light});
        }
        return this.mLinkCardStyle;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getForegroundDrawable(Context context, long j) {
        return context.getResources().getDrawable(R.drawable.theme_pc_fg);
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridBodyStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_PC_DefaultTheme_TextAppearance_Grid_Secondary_DayNight : R.style.V8_TextAppearance_Grid_Secondary_Light;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected int getGridTimeStyle(Context context) {
        return UIUtils.isMiproFontSupported() ? R.style.V12_TextAppearance_Grid_Tertiary_Light : R.style.V8_TextAppearance_Grid_Secondary_Light;
    }

    @Override // com.miui.notes.theme.ColorTheme, com.miui.notes.theme.SimpleTheme
    protected Drawable getGridContentBackground(Context context) {
        Drawable drawableMutate = context.getResources().getDrawable(R.drawable.v12_grid_item_bg).mutate();
        drawableMutate.setColorFilter(context.getResources().getColor(R.color.v12_theme_content_bg_color), PorterDuff.Mode.SRC_ATOP);
        return drawableMutate;
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
}
