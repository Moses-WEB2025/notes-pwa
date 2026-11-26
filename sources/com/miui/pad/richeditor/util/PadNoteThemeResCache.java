package com.miui.pad.richeditor.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import com.miui.notes.theme.util.NoteThemeResCache;
import com.miui.richeditor.theme.Theme;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteThemeResCache extends NoteThemeResCache {
    public PadNoteThemeResCache(int i) {
        super(i);
    }

    @Override // com.miui.notes.theme.util.NoteThemeResCache
    public void loadToolBarRes(Theme theme, Context context) {
        if (RomUtils.isSupportAi()) {
            this.toolBarAiIcon = getThemeDrawable(R.drawable.ic_tool_ai, theme, context);
        } else {
            this.toolBarAiIcon = getThemeDrawable(R.drawable.icon_ai, theme, context);
        }
        this.toolBarGalleryIcon = getThemeDrawable(R.drawable.ic_tool_gallery, theme, context);
        this.toolBarAudioIcon = getThemeDrawable(R.drawable.ic_tool_voice, theme, context);
        this.toolBarDoodleIcon = getThemeDrawable(R.drawable.ic_tool_doodle, theme, context);
        this.toolBarRichTextIcon = getThemeDrawable(R.drawable.ic_tool_richtext, theme, context);
        this.toolBarRichTextReverseIcon = getThemeDrawable(R.drawable.ic_tool_richtext_reverse, theme, context);
        this.toolBarDivideIcon = getThemeDrawable(R.drawable.ic_toolbar_divider, theme, context);
        this.toolBarFirstLevelIcon = getThemeDrawable(R.drawable.ic_tool_fontsize_level_first, theme, context);
        this.toolBarSecondLevelIcon = getThemeDrawable(R.drawable.ic_tool_fontsize_level_second, theme, context);
        this.toolBarThridLevelIcon = getThemeDrawable(R.drawable.ic_tool_fontsize_level_thrid, theme, context);
        this.toolBarStrikeThroughIcon = getThemeDrawable(R.drawable.ic_tool_strikethrough, theme, context);
        this.toolBarListBulletIcon = getThemeDrawable(R.drawable.ic_tool_listbullet, theme, context);
        this.toolBarSplitLineIcon = getThemeDrawable(R.drawable.ic_tool_split_line, theme, context);
        this.toolBarQuoteIcon = getThemeDrawable(R.drawable.ic_tool_quote, theme, context);
        this.toolBarIndentRightIcon = getThemeDrawable(R.drawable.ic_tool_indent_right, theme, context);
        this.toolBarIndentLeftIcon = getThemeDrawable(R.drawable.ic_tool_indent_left, theme, context);
        this.toolBarBoldIcon = getThemeDrawable(R.drawable.ic_tool_bold, theme, context);
        this.toolBarItalicIcon = getThemeDrawable(R.drawable.ic_tool_italic, theme, context);
        this.toolBarUnderlineIcon = getThemeDrawable(R.drawable.ic_tool_underline, theme, context);
        this.toolBarIncreaseSizeIcon = getThemeDrawable(R.drawable.ic_tool_increase_size, theme, context);
        this.toolBarDecreaseSizeIcon = getThemeDrawable(R.drawable.ic_tool_decrease_size, theme, context);
        this.toolBarLeftIcon = getThemeDrawable(R.drawable.ic_tool_left, theme, context);
        this.toolBarCenterIcon = getThemeDrawable(R.drawable.ic_tool_center, theme, context);
        this.toolBarRightIcon = getThemeDrawable(R.drawable.ic_tool_right, theme, context);
        this.toolBarBgHighLightIcon = getThemeDrawable(R.drawable.ic_tool_background_highlight, theme, context);
        this.toolBarCheckIcon = getThemeDrawable(R.drawable.ic_tool_checkbox, theme, context);
        this.toolBarBulletIcon = getThemeDrawable(R.drawable.ic_tool_bullet, theme, context);
    }

    private Drawable getThemeDrawable(int i, Theme theme, Context context) {
        return theme.getResTheme(context).getDrawable(i).mutate();
    }
}
