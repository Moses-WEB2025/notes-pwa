package com.miui.notes.ai.stat;

import com.miui.notes.ai.utils.RomUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NoteContentStat {
    public static String ACTION_BROADCAST_MIAI_DISMISS = "com.miui.notes.action.miai.dismiss";
    public static String ENTER_EDIT_WAY = "new_note";
    public static boolean IS_BTN_BACK = false;
    public static boolean IS_CLICKED = false;
    public static boolean IS_NOTE_BTN_BACK = false;
    public static final String IS_SETTING_FALSE = "否";
    public static final String IS_SETTING_TRUE = "是";
    public static boolean IS_SHARE = false;
    public static boolean IS_STICK = false;
    public static final String MULTI_HANDWRITE_ACTIVITY = "com.miui.pad.feature.notes.multihandwrite.CreationEditActivity";
    public static final String NOTES_ALL_NAME = "首页全部";
    public static final String NOTES_TYPE_NAME = "首页文件夹";
    public static String QUIT_EDIT_WAY = "SystemBack";
    public static String QUIT_WAY = "SystemBack";
    public static final String QUIT_WAY_BTN_BACK = "BtnBack";
    public static final String QUIT_WAY_MARK_DOWN_EXPORT = "MarkDownExportBack";
    public static final String QUIT_WAY_SHARE = "ShareBack";
    public static final String QUIT_WAY_SYSTEM = "SystemBack";
    public static final String TYPE_CALL = "通话笔记";
    public static final String TYPE_CARD_WIDGET = "卡片小部件";
    public static final String TYPE_GLOBAL_SEARCH = "全局搜索";
    public static final String TYPE_GLOBAL_SHORTHAND = "全局速记";
    public static final String TYPE_NOTES_HOME = "笔记首页";
    public static final String TYPE_NOTES_SEARCH = "笔记搜索";
    public static final String TYPE_NOTES_UNKNOWN = "unknown";
    public static boolean mOldStick = false;

    public static void cancel() {
        if (RomUtils.isPhoneDevice()) {
            IS_STICK = false;
        }
        IS_SHARE = false;
    }
}
