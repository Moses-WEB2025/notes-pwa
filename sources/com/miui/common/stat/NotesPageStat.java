package com.miui.common.stat;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.miui.notes.ai.stat.NoteContentStat;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.constant.NoteIntent;
import com.miui.todo.constant.TodoIntent;
import java.lang.reflect.Field;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class NotesPageStat extends BaseStat {
    public static final String DURATION = "duration";
    public static final String GO_DETAILS = "去下级页";
    public static final String GO_FOLDER = "去文件夹页";
    public static final String GO_NEW_NOTES = "去新建笔记";
    public static final String GO_NEW_TODO = "去新建待办";
    public static final String GO_SEARCH = "去搜索页";
    public static final String GO_SETTING = "去设置页";
    public static final String GO_TODO = "去待办页";
    public static final String INTERNAL_CHANGE = "内部切换";
    public static String QUIT_TYPE = "内部切换";
    public static final String QUIT_WAY = "quit_way";
    public static String SLIDE_SWITCH = "slide";
    public static final String SOURCE = "source";
    public static final String SUB_SOURCE = "sub_source";
    public static final String SYSTEM_BACK = "SystemBack";
    public static String TAB_SWITCH = "tab";
    public static final String TIP_HOMEPAGE_CARD_EXPOSE = "729.1.9.1.20013";
    public static final String TIP_HOMEPAGE_FILE_ICON_CLICK = "729.1.8.1.20012";
    public static final String TIP_HOMEPAGE_MICLOUD_CLICK = "729.1.4.1.20007";
    public static final String TIP_HOMEPAGE_MOVE_FILE_CLICK = "729.1.7.1.20011";
    public static final String TIP_HOMEPAGE_NEW_NOTE_CLICK = "729.1.10.1.20015";
    public static final String TIP_HOMEPAGE_PASS_WORD_EXPOSE = "729.1.0.1.20008";
    public static final String TIP_HOMEPAGE_PULL = "729.1.0.1.20006";
    public static final String TIP_HOMEPAGE_SEARCH_CLICK = "729.1.11.1.20016";
    public static final String TIP_HOMEPAGE_SETTING_CLICK = "729.1.16.1.20623";
    public static final String TIP_HOMEPAGE_TOOLBAR_CLICK = "729.1.1.1.20017";
    public static final String TIP_HOMEPAGE_TOOLBAR_DELETE_CLICK = "729.1.2.1.20018";
    public static final String TIP_HOMEPAGE_TOOLBAR_GROUP_CLICK = "729.1.13.1.20020";
    public static final String TIP_HOMEPAGE_TOOLBAR_MOVE_CLICK = "729.1.12.1.20019";
    public static final String TIP_NOTE_ENTER = "729.1.0.1.20003";
    public static final String TIP_NOTE_QUIT = "729.1.0.1.20004";
    public static final String TIP_SWITCH_TO_TODO = "729.1.0.1.20340";
    public static final String TIP_TODO_ENTER = "729.31.10.1.39604";
    public static final String TIP_TODO_QUIT = "729.31.10.1.39605";
    public static long mFolderId = 0;
    public static String openSource = "其他";

    public static void reportSwitchEvent(boolean z) {
        HashMap map = new HashMap();
        map.put(ToDoStat.SWITCH_MODE, z ? TAB_SWITCH : SLIDE_SWITCH);
        reportSwitchEvent(TIP_SWITCH_TO_TODO, map);
    }

    public static void reportNewNote() {
        reportClickEvent(TIP_HOMEPAGE_NEW_NOTE_CLICK, null);
    }

    public static void reportSearch() {
        reportClickEvent(TIP_HOMEPAGE_SEARCH_CLICK, null);
    }

    public static void reportClickFolderEvent(String str) {
        HashMap map = new HashMap();
        map.put("click_element_type", str);
        reportClickEvent(TIP_HOMEPAGE_MOVE_FILE_CLICK, map);
    }

    public static void reportClickSelectNumberEvent(String str) {
        HashMap map = new HashMap();
        map.put("selected_quantity", str);
        reportClickEvent(TIP_HOMEPAGE_TOOLBAR_GROUP_CLICK, map);
    }

    public static void reportNotesEnter(Activity activity, long j, boolean z) {
        HashMap map = new HashMap();
        String openSource2 = getOpenSource(activity);
        openSource = openSource2;
        String str = j != -4097 ? NoteContentStat.NOTES_TYPE_NAME : NoteContentStat.NOTES_ALL_NAME;
        map.put("source", openSource2);
        if (z) {
            map.put("sub_source", str);
        }
        reportEnterEvent(z ? TIP_NOTE_ENTER : TIP_TODO_ENTER, map);
        Logger.INSTANCE.d("NoteAppStat", "reportNotesEnter: ==== " + map);
    }

    public static void reportNotesQuit(Activity activity, long j, long j2, String str, boolean z) {
        HashMap map = new HashMap();
        String openSource2 = getOpenSource(activity);
        openSource = openSource2;
        String str2 = j != -4097 ? NoteContentStat.NOTES_TYPE_NAME : NoteContentStat.NOTES_ALL_NAME;
        map.put("source", openSource2);
        map.put("duration", Long.valueOf(j2));
        map.put("quit_way", str);
        if (z) {
            map.put("sub_source", str2);
        }
        reportQuitEvent(z ? TIP_NOTE_QUIT : TIP_TODO_QUIT, map);
        Logger.INSTANCE.d("NoteAppStat", "reportNotesQuit: ==== " + map);
    }

    private static String reflectGetReferrer(Activity activity) {
        try {
            Field declaredField = Activity.class.getDeclaredField("mReferrer");
            declaredField.setAccessible(true);
            return (String) declaredField.get(activity);
        } catch (Exception e) {
            Log.e("NotesPageStat", "reflectGetReferrer error:", e);
            return "";
        }
    }

    private static String getOpenSource(Activity activity) {
        Intent intent;
        String strReflectGetReferrer = reflectGetReferrer(activity);
        if ((!NoteAppStat.APP_PKG.desktop.equals(strReflectGetReferrer) && !"com.miui.notes".equals(strReflectGetReferrer)) || (intent = activity.getIntent()) == null) {
            return NoteAppStat.NOTE_APP_OPEN_SOURCE_MAP.containsKey(strReflectGetReferrer) ? NoteAppStat.NOTE_APP_OPEN_SOURCE_MAP.get(strReflectGetReferrer) : NoteAppStat.APP_NAME.other;
        }
        String action = intent.getAction();
        Logger.INSTANCE.d("NotesPageStat", "action " + action);
        if (TextUtils.equals(action, TodoIntent.ACTION_VIEW)) {
            return NoteAppStat.APP_NAME.todoList;
        }
        if (TextUtils.equals(action, NoteIntent.ACTION_BIND_WIDGET)) {
            return NoteAppStat.APP_NAME.noteList;
        }
        if ("android.intent.action.SEND".equals(action)) {
            return NoteAppStat.APP_NAME.search;
        }
        if (intent.getIntExtra("com.miui.notes.widget_type", -1) != -1) {
            return NoteAppStat.APP_NAME.noteCard;
        }
        if (intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false)) {
            return "全局速记";
        }
        if ("android.intent.extra.PHONE_NUMBER".equals(action)) {
            return "通话笔记";
        }
        return NoteAppStat.APP_PKG.desktop.equals(strReflectGetReferrer) ? NoteAppStat.APP_NAME.desktop : NoteAppStat.APP_NAME.other;
    }
}
