package com.miui.common.stat;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.miui.common.tool.Logger;
import com.miui.notes.constant.NoteIntent;
import com.miui.todo.constant.TodoIntent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class NoteAppStat extends BaseStat {
    public static final String NOTE_APP_ENTER = "729.0.0.0.17392";
    public static final Map<String, String> NOTE_APP_OPEN_SOURCE_MAP = new HashMap<String, String>() { // from class: com.miui.common.stat.NoteAppStat.1
        {
            put(APP_PKG.desktop, APP_NAME.desktop);
            put("com.xiaomi.market", APP_NAME.market);
            put(APP_PKG.search, APP_NAME.search);
            put(APP_PKG.securitycenter, APP_NAME.securityCenter);
            put(APP_PKG.securitycenter, APP_NAME.securityCenter);
            put(APP_PKG.securitycenter, APP_NAME.securityCenter);
        }
    };
    public static final String NOTE_APP_QUIT = "729.0.0.0.17394";
    public static final String PARAM_NOTE_DURATION = "duration";
    public static final String PARAM_NOTE_OPEN_SOURCE = "note_open_source";
    public static final String QUIT_WAY = "quit_way";
    public static final String SOURCE = "source";
    public static final String SUB_AOURCE = "sub_aource";
    private static final String TAG = "NoteAppStat";
    public static String openSource = "其他";
    public static long openTime = -1;

    public interface APP_NAME {
        public static final String callNote = "通话笔记";
        public static final String desktop = "桌面icon";
        public static final String market = "应用商店";
        public static final String noteCard = "笔记卡片小组件";
        public static final String noteList = "笔记列表小组件";
        public static final String other = "其他";
        public static final String search = "全局搜索_应用";
        public static final String securityCenter = "侧边栏";
        public static final String shortHand = "全局速记";
        public static final String todoList = "待办列表小组件";
    }

    public interface APP_PKG {
        public static final String desktop = "com.miui.home";
        public static final String market = "com.xiaomi.market";
        public static final String notes = "com.miui.notes";
        public static final String search = "com.android.quicksearchbox";
        public static final String securitycenter = "com.miui.securitycenter";
    }

    public static void reportNoteAppEnterEvent(Activity activity) {
        HashMap map = new HashMap();
        openSource = getOpenSource(activity);
        if (openTime == -1) {
            openTime = System.currentTimeMillis();
        }
        map.put("note_open_source", openSource);
        reportEnterEvent("729.0.0.0.17392", map);
        Logger.INSTANCE.d(TAG, "reportNoteAppEnterEvent: " + map);
    }

    public static void reportNoteAppQuitEvent() {
        HashMap map = new HashMap();
        long jCurrentTimeMillis = System.currentTimeMillis() - openTime;
        openTime = -1L;
        map.put("note_open_source", openSource);
        map.put("duration", Long.valueOf(jCurrentTimeMillis));
        reportQuitEvent("729.0.0.0.17394", map);
        Logger.INSTANCE.d(TAG, "reportNoteAppQuitEvent: " + map);
    }

    private static String reflectGetReferrer(Activity activity) {
        try {
            Field declaredField = Activity.class.getDeclaredField("mReferrer");
            declaredField.setAccessible(true);
            return (String) declaredField.get(activity);
        } catch (Exception e) {
            Log.e(TAG, "reflectGetReferrer error:", e);
            return "";
        }
    }

    private static String getOpenSource(Activity activity) {
        Intent intent;
        String strReflectGetReferrer = reflectGetReferrer(activity);
        if ((APP_PKG.desktop.equals(strReflectGetReferrer) || "com.miui.notes".equals(strReflectGetReferrer)) && (intent = activity.getIntent()) != null) {
            String action = intent.getAction();
            Logger.INSTANCE.d(TAG, "action " + action);
            if (TextUtils.equals(action, TodoIntent.ACTION_VIEW)) {
                return APP_NAME.todoList;
            }
            if (TextUtils.equals(action, NoteIntent.ACTION_BIND_WIDGET)) {
                return APP_NAME.noteList;
            }
            if ("android.intent.action.SEND".equals(action)) {
                return APP_NAME.search;
            }
            if (intent.getIntExtra("com.miui.notes.widget_type", -1) != -1) {
                return APP_NAME.noteCard;
            }
            if (intent.getBooleanExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, false)) {
                return "全局速记";
            }
            if ("android.intent.extra.PHONE_NUMBER".equals(action)) {
                return "通话笔记";
            }
            return APP_PKG.desktop.equals(strReflectGetReferrer) ? APP_NAME.desktop : APP_NAME.other;
        }
        Map<String, String> map = NOTE_APP_OPEN_SOURCE_MAP;
        return map.containsKey(strReflectGetReferrer) ? map.get(strReflectGetReferrer) : APP_NAME.other;
    }
}
