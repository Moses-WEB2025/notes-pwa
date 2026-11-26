package com.miui.notes.tool;

import android.content.Context;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class KeyboardShortcutUtils {
    public static KeyboardShortcutGroup createNoteEditShortcut(Context context) {
        KeyboardShortcutGroup keyboardShortcutGroup = new KeyboardShortcutGroup(context.getString(R.string.app_name));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.pc_action_create_new_note) + StringUtils.SPACE + context.getString(R.string.pc_action_create_new_normal_note) + " / " + context.getString(R.string.todo_menu_add_description), 42, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.pc_action_create_new_note) + StringUtils.SPACE + context.getString(R.string.pc_action_create_new_mind_note), 41, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.undo), 54, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.redo), 53, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_bold_description), 30, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_italic_description), 37, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_underline_description), 49, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_hightlight_description), 36, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_tasklist_description), 39, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_left_description), 40, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_center_description), 33, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_right_description), 46, 4096));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.toolbar_voice_description), 50, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.pad_insert_image_menu_take_photo_by_pad), 31, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.pad_insert_image_menu_select_from_pad_gallery), 44, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.doodle_feature), 32, 4098));
        if (!RomUtils.isNewTablet()) {
            keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_unorderlist_description), 35, 4096));
            keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_font_increase_description), 72, 4096));
            keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_font_decrease_description), 71, 4096));
            return keyboardShortcutGroup;
        }
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_strikethrough_description), 52, 4097));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_indent_right_description), 61, 0));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_indent_left_description), 61, 1));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_font_first_level_description), 8, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_font_second_level_description), 9, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_font_third_level_description), 10, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_orderlist_description), 14, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_unorderlist_description), 15, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_quote_description), 22, 4098));
        keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(context.getString(R.string.rich_text_split_description), 47, 4098));
        return keyboardShortcutGroup;
    }
}
