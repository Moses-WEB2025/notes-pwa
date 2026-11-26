package com.miui.notes.theme.util;

import android.util.SparseArray;
import com.miui.common.stat.NotesTextStat;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.Logger;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.theme.AntiqueTheme;
import com.miui.notes.theme.ArtyTheme;
import com.miui.notes.theme.CandyTheme;
import com.miui.notes.theme.DarkColorTheme;
import com.miui.notes.theme.DarkTheme;
import com.miui.notes.theme.DefaultTheme;
import com.miui.notes.theme.GeometryTheme;
import com.miui.notes.theme.LeafTheme;
import com.miui.notes.theme.NightskyTheme;
import com.miui.notes.theme.PcColorTheme;
import com.miui.notes.theme.ShadowTheme;
import com.miui.notes.theme.StarTheme;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class ResourceManager {
    public static final int COLOR_0 = 0;
    public static final int COLOR_1 = 1;
    public static final int COLOR_10 = 10;
    public static final int COLOR_11 = 11;
    public static final int COLOR_12 = 12;
    public static final int COLOR_13 = 13;
    public static final int COLOR_14 = 14;
    public static final int COLOR_15 = 15;
    public static final int COLOR_2 = 2;
    public static final int COLOR_3 = 3;
    public static final int COLOR_4 = 4;
    public static final int COLOR_5 = 5;
    public static final int COLOR_6 = 6;
    public static final int COLOR_7 = 7;
    public static final int COLOR_8 = 8;
    public static final int COLOR_9 = 9;
    public static final int COLOR_ANTIQUE = 7;
    public static final int COLOR_ARTY = 8;
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_CANDY = 5;
    public static final int COLOR_DARK = 6;
    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_GEOMETRY = 14;
    public static final int COLOR_GREEN = 3;
    public static final int COLOR_LEAF = 12;
    public static final int COLOR_NIGHTSKY = 13;
    public static final int COLOR_PC = 10;
    public static final int COLOR_RED = 4;
    public static final int COLOR_SHADOW = 11;
    public static final int COLOR_STAR = 9;
    public static final int COLOR_WHITE = 10;
    public static final int COLOR_YELLOW = 2;
    public static final int INVALID_COLOR = -1;
    private static final String TAG = "ResourceManager";
    public static boolean isNightMode = false;
    public static final String[] THEME_NAMES = {"default", NotesTextStat.BLUE, NotesTextStat.YELLOW, NotesTextStat.GREEN, NotesTextStat.RED, NotesTextStat.CANDY, "dark", NotesTextStat.ANITQUE, NotesTextStat.ARTY, NotesTextStat.STARS, "white", NotesTextStat.SHADOW, NotesTextStat.LEAF, "nightsky", NotesTextStat.GEOMETRY};
    private static final int[] INDICATOR_DRAWABLES = {0, R.drawable.v8_ic_indicator_blue, R.drawable.v8_ic_indicator_yellow, R.drawable.v8_ic_indicator_green, R.drawable.v8_ic_indicator_red};
    private static final int[] WIDGET_BG_RESOURCES = {R.drawable.v12_note_widget_head_bg_white, R.drawable.v12_note_widget_head_bg_blue, R.drawable.v12_note_widget_head_bg_yellow, R.drawable.v12_note_widget_head_bg_green, R.drawable.v12_note_widget_head_bg_red};
    public static NoteThemeResCache pPreLoadResCache = null;
    private static final SparseArray<Theme> sThemeMap = new SparseArray<>();

    public static boolean isShowTimeTheme(int i) {
        return i == 6 || i == 7;
    }

    public static int getIndicatorResource(int i) {
        return INDICATOR_DRAWABLES[checkColor(i)];
    }

    private static int getWidgetTitleBgResource(int i) {
        return WIDGET_BG_RESOURCES[checkColor(i)];
    }

    private static int checkColor(int i) {
        if (i < 0 || i >= INDICATOR_DRAWABLES.length) {
            return 0;
        }
        return i;
    }

    private static void initThemes() {
        logDebug("initThemes");
        Theme.initStyles();
        SparseArray<Theme> sparseArray = sThemeMap;
        sparseArray.put(0, new DefaultTheme(0));
        sparseArray.put(1, new DarkColorTheme(1, R.style.NoteTheme_Edit_Blue, R.drawable.v12_theme_thumb_blue, R.string.theme_title_blue, R.drawable.v12_theme_thumb_blue_shadow, R.color.theme_blue_primary_color, R.color.theme_blue_secondary_color, R.color.theme_blue_grid_tertiary_color, R.color.theme_blue_icon_color_n, R.color.theme_blue_text_bg_color, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Primary_Blue : R.style.V8_TextAppearance_Grid_Primary_Blue, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Blue : R.style.V8_TextAppearance_Grid_Secondary_Blue, R.style.V8_TextAppearance_Grid_Tertiary_Blue, R.color.theme_blue_grid_bg_color, R.color.theme_blue_grid_border_color, getWidgetTitleBgResource(1), R.color.theme_blue_cursor_color, R.color.theme_blue_cursor_handle_color, R.color.theme_blue_high_light_color, R.color.theme_blue_linked_color, R.color.theme_blue_done_text_color, R.color.note_edit_blur_mix_color_blue, R.color.note_edit_blur_mix_color2_blue, R.color.note_edit_blur_mix_color3_blue, R.color.theme_blue_editor_hint_color, R.color.theme_blue_dir_indicator_bg_color, R.color.theme_blue_dir_indicator_icon_color, R.color.theme_blue_bg_highlight_color, R.color.annotation_edit_bg_color_blue, R.color.link_card_title_text_color_blue, R.color.link_card_link_text_color_blue));
        sparseArray.put(2, new DarkColorTheme(2, R.style.NoteTheme_Edit_Yellow, R.drawable.v12_theme_thumb_yellow, R.string.theme_title_yellow, R.drawable.v12_theme_thumb_yellow_shadow, R.color.theme_yellow_primary_color, R.color.theme_yellow_secondary_color, R.color.theme_yellow_grid_tertiary_color, R.color.theme_yellow_icon_color_n, R.color.theme_yellow_text_bg_color, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Primary_Yellow : R.style.V8_TextAppearance_Grid_Primary_Yellow, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Yellow : R.style.V8_TextAppearance_Grid_Secondary_Yellow, R.style.V8_TextAppearance_Grid_Tertiary_Yellow, R.color.theme_yellow_grid_bg_color, R.color.theme_yellow_grid_border_color, getWidgetTitleBgResource(2), R.color.theme_yellow_cursor_color, R.color.theme_yellow_cursor_handle_color, R.color.theme_yellow_high_light_color, R.color.theme_yellow_linked_color, R.color.theme_yellow_done_text_color, R.color.note_edit_blur_mix_color_yellow, R.color.note_edit_blur_mix_color2_yellow, R.color.note_edit_blur_mix_color3_yellow, R.color.theme_yellow_editor_hint_color, R.color.theme_yellow_dir_indicator_bg_color, R.color.theme_yellow_dir_indicator_icon_color, R.color.theme_yellow_bg_highlight_color, R.color.annotation_edit_bg_color_yellow, R.color.link_card_title_text_color_yellow, R.color.link_card_link_text_color_yellow));
        sparseArray.put(3, new DarkColorTheme(3, R.style.NoteTheme_Edit_Green, R.drawable.v12_theme_thumb_green, R.string.theme_title_green, R.drawable.v12_theme_thumb_green_shadow, R.color.theme_green_primary_color, R.color.theme_green_secondary_color, R.color.theme_green_grid_tertiary_color, R.color.theme_green_icon_color_n, R.color.theme_green_text_bg_color, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Primary_Green : R.style.V8_TextAppearance_Grid_Primary_Green, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Green : R.style.V8_TextAppearance_Grid_Secondary_Green, R.style.V8_TextAppearance_Grid_Tertiary_Green, R.color.theme_green_grid_bg_color, R.color.theme_green_grid_border_color, getWidgetTitleBgResource(3), R.color.theme_green_cursor_color, R.color.theme_green_cursor_handle_color, R.color.theme_green_high_light_color, R.color.theme_green_linked_color, R.color.theme_green_done_text_color, R.color.note_edit_blur_mix_color_green, R.color.note_edit_blur_mix_color2_green, R.color.note_edit_blur_mix_color3_green, R.color.theme_green_editor_hint_color, R.color.theme_green_dir_indicator_bg_color, R.color.theme_green_dir_indicator_icon_color, R.color.theme_green_bg_highlight_color, R.color.annotation_edit_bg_color_green, R.color.link_card_title_text_color_green, R.color.link_card_link_text_color_green));
        sparseArray.put(4, new DarkColorTheme(4, R.style.NoteTheme_Edit_Red, R.drawable.v12_theme_thumb_red, R.string.theme_title_red, R.drawable.v12_theme_thumb_red_shadow, R.color.theme_red_primary_color, R.color.theme_red_secondary_color, R.color.theme_red_grid_tertiary_color, R.color.theme_red_icon_color_n, R.color.theme_red_text_bg_color, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Primary_Red : R.style.V8_TextAppearance_Grid_Primary_Red, UIUtils.isMiproFontSupported() ? R.style.V11_TextAppearance_Grid_Secondary_Red : R.style.V8_TextAppearance_Grid_Secondary_Red, R.style.V8_TextAppearance_Grid_Tertiary_Red, R.color.theme_red_grid_bg_color, R.color.theme_red_grid_border_color, getWidgetTitleBgResource(4), R.color.theme_red_cursor_color, R.color.theme_red_cursor_handle_color, R.color.theme_red_high_light_color, R.color.theme_red_linked_color, R.color.theme_red_done_text_color, R.color.note_edit_blur_mix_color_red, R.color.note_edit_blur_mix_color2_red, R.color.note_edit_blur_mix_color3_red, R.color.theme_red_editor_hint_color, R.color.theme_red_dir_indicator_bg_color, R.color.theme_red_dir_indicator_icon_color, R.color.theme_red_bg_highlight_color, R.color.annotation_edit_bg_color_red, R.color.link_card_title_text_color_red, R.color.link_card_link_text_color_red));
        sparseArray.put(5, new CandyTheme(5));
        sparseArray.put(6, new DarkTheme(6));
        sparseArray.put(7, new AntiqueTheme(7));
        sparseArray.put(8, new ArtyTheme(8));
        sparseArray.put(9, new StarTheme(9));
        sparseArray.put(11, new ShadowTheme(11));
        sparseArray.put(12, new LeafTheme(12));
        sparseArray.put(13, new NightskyTheme(13));
        sparseArray.put(14, new GeometryTheme(14));
        sparseArray.put(10, new PcColorTheme(10));
        logDebug("initThemes finished");
    }

    public static synchronized Theme getTheme(int i) {
        logDebug("getTheme " + i);
        SparseArray<Theme> sparseArray = sThemeMap;
        if (sparseArray.size() == 0) {
            initThemes();
        }
        if (i == -1) {
            return null;
        }
        if (LiteUtils.isLiteOrMiddle() && i != 0) {
            i = 0;
        }
        Theme theme = sparseArray.get(i);
        if (theme == null) {
            theme = sparseArray.get(0);
        }
        return theme;
    }

    public static synchronized void clearThemesEditorStyle() {
        logDebug("clearThemesEditorStyle");
        int i = 0;
        while (true) {
            SparseArray<Theme> sparseArray = sThemeMap;
            if (i < sparseArray.size()) {
                sparseArray.valueAt(i).clearEditContentStyle();
                i++;
            }
        }
    }

    public static synchronized void clearThemes() {
        logDebug("clearThemes");
        int i = 0;
        while (true) {
            SparseArray<Theme> sparseArray = sThemeMap;
            if (i < sparseArray.size()) {
                sparseArray.valueAt(i).onDestroyView();
                i++;
            } else {
                sparseArray.clear();
            }
        }
    }

    private static void logDebug(String str) {
        Logger.INSTANCE.d(TAG, str);
    }

    public static void destroyUnusedAnimThemeView(int i) {
        logDebug("destroyUnusedAnimThemeView");
        SparseArray<Theme> sparseArray = sThemeMap;
        if (sparseArray.size() == 0) {
            return;
        }
        if (i != 11) {
            sparseArray.get(11).onDestroyView();
        }
        if (i != 12) {
            sparseArray.get(12).onDestroyView();
        }
        if (i != 13) {
            sparseArray.get(13).onDestroyView();
        }
        if (i != 14) {
            sparseArray.get(14).onDestroyView();
        }
    }

    public static void clearPreloadResCache() {
        pPreLoadResCache = null;
    }
}
