package com.miui.common.stat;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.miui.ai.text.widget.download.AIcrDownloadHelper;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.ai.utils.AIPreferencesUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.component.datafolder.FolderItemEntity;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.Todo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class GlobalStat extends BaseStat {
    private static int ACTIVITY_COUNT = 0;
    private static final String AI_CLOUD_STATUS = "ai_cloud_status";
    private static final String AI_DEEP_SEEK_LOCAL_STATUS = "ai_deepseek_local_status";
    private static final String AI_DEEP_SEEK_STATUS = "ai_deepseek_status";
    private static final String AI_STATUS_FALSE = "false";
    private static final String AI_STATUS_NOT_SUPPORT = "not support";
    private static final String AI_STATUS_TRUE = "true";
    private static final String BRAIN_NOTE_QUANTITY = "brain_note_quantity";
    private static final String BY_CREATION_TIME = "by_creation_time";
    private static final String BY_MODIFICATION_TIME = "by_modification_time";
    private static final String COLUMNS_MODE = "columns";
    private static final String CUSTOM_FOLDER_QUANTITY = "custom_folder_quantity";
    public static final String EVENT_NAME_SETTING_STATUS = "setting";
    private static final String FLAT_MODE = "flat";
    private static final String FOLDER_QUANTITY = "folder_quantity";
    private static final String FONT_SIZE = "font_size";
    private static final String FONT_SIZE_BIG = "big";
    private static final String FONT_SIZE_DEFAULT = "default";
    private static final String FONT_SIZE_SMALL = "small";
    private static final String FONT_SIZE_SUPER_BIG = "super_big";
    private static final String GENERAL_TODO_NOTE_QUANTITY = "general_todo_note_quantity";
    private static final String GRID_MODE = "grid";
    private static final String HANDWRITTEN_NOTE_QUANTITY = "handwritten_note_quantity";
    private static final String IS_CALL_NOTE_FOLDER = "is_call_note_folder";
    private static final String IS_EXCERPT_FOLDER = "is_excerpt_folder";
    private static final String IS_HEAR_FOLDER = "is_hear_folder";
    private static final String IS_SMART_BLACKBOARD = "is_smart_blackboard";
    private static final String IS_UNCLASSIFIED_FOLDER = "is_unclassified_folder";
    private static final String LAST_GLOBAL_REPORT_TIME = "last_global_report_time";
    private static final String LIST_MODE = "list";
    private static final String LIST_TODO_NOTE_QUANTITY = "list_todo_note_quantity";
    private static final String MIND_NOTE_DETAIL_LIST = "mind_note_detail_list";
    private static final String MIND_NOTE_NODE_QUANTITY = "mind_note_node_quantity";
    private static final String MIND_NOTE_QUANTITY = "mind_note_quantity";
    private static final String NO = "no";
    private static final String NOTE_LIST_LAYOUT = "note_list_layout";
    public static final String NOTE_OPEN_SOURCE = "note_open_source";
    private static final String NOTE_QUANTITY = "note_quantity";
    private static final String NOTE_WIDE_LAYOUT = "note_wide_layout";
    private static final String OFF_MODE = "off";
    private static final String ON_MODE = "on";
    private static final String OUTLINE_NOTE_QUANTITY = "outline_note_quantity";
    private static final String PRIVATE_NOTE_QUANTITY = "private_note_quantity";
    private static final String SHORTHAND_HOME_COMPLETED_EXPAND_STATUS = "shorthand_home_completed_expand_status";
    private static final String SHORT_HAND_OPEN_STATUS = "shorthand_open_status";
    private static final String SORT_BY = "sort_by";
    private static final String STICKY_FOLDER_QUANTITY = "sticky_folder_quantity";
    private static final String STICKY_NOTE_QUANTITY = "sticky_note_quantity";
    private static final String STRONG_REMINDER_OPEN_STATE = "strong_reminder_open_status";
    private static final String SUMMARY_OPEN_STATUS = "summary_open_status";
    private static final String TAG = "Notes:GlobalStat";
    private static final String TEXT_NOTE_QUANTITY = "text_note_quantity";
    public static final String TIP_ENTER_NOTES = "729.0.0.0.17392";
    public static final String TIP_FOLDER_STATUS = "";
    public static final String TIP_NOTES_STATUS = "";
    public static final String TIP_PC_EXPOSE = "729.36.0.1.25551";
    public static final String TIP_QUIT_NOTES = "729.0.0.0.17394";
    public static final String TIP_SETTING_STATUS = "729.0.0.0.20137";
    public static final String TIP_TODO_STATUS = "";
    private static final String TODO_HONE_COMPLETED_EXPAND_STATUS = "todo_home_completed_expand_status";
    private static final String TODO_NOTE_QUANTITY = "todo_note_quantity";
    private static final String UNCLASSIFIED_NOTE_QUANTITY = "unclassified_note_quantity";
    private static final String UNDONE_TODO_LIST_WITHOUT_REMIND_QUANTITY = "undone_todo_list_without_remind_quantity";
    private static final String UNDONE_TODO_LIST_WITH_REMIND_QUANTITY = "undone_todo_list_with_remind_quantity";
    private static final String UNDONE_TODO_NOTE_QUANTITY = "undone_todo_note_quantity";
    private static final String UNDONE_TODO_NOTE_WITHOUT_REMIND_QUANTITY = "undone_todo_note_without_remind_quantity";
    private static final String UNDONE_TODO_NOTE_WITH_REMIND_QUANTITY = "undone_todo_note_with_remind_quantity";
    private static final String YES = "yes";
    private static AIPreferencesUtils aiPreferencesUtils;

    public static class Status {
        public static int allFolderCount = -1;
        public static boolean arrayWayId = false;
        public static int definedFolderCount = 0;
        public static int finishedTodoCount = -1;
        public static List<FolderItemEntity> folderList = null;
        public static int fontSizeId = -1;
        public static boolean hasBlackBoardFolder = false;
        public static boolean hasCallFolder = false;
        public static boolean hasExcerptFolder = false;
        public static boolean hasHearFolder = false;
        public static boolean hasUnclassifiedFolder = false;
        public static boolean idGrid = false;
        public static boolean isQuickNote = false;
        public static int noteSortWayId = -1;
        public static int stickyFolderQuantity = 0;
        public static int todoCount = -1;
        public static int unfinishedTodoCount = -1;
    }

    public static void onStart() {
    }

    public static void onStop() {
        int i = ACTIVITY_COUNT - 1;
        ACTIVITY_COUNT = i;
        if (i != 0 || StatHelper.getStatInstance() == null) {
            return;
        }
        reportSettingStatus();
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.miui.common.stat.GlobalStat$1] */
    private static void reportSettingStatus() {
        if (System.currentTimeMillis() / 86400000 <= getLastGlobalReportTime()) {
            return;
        }
        setLastGlobalReportTime();
        new Thread() { // from class: com.miui.common.stat.GlobalStat.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                super.run();
                HashMap map = new HashMap();
                GlobalStat.getSettingStatus(NoteApp.getInstance(), map);
                GlobalStat.calculateNotesCount(NoteApp.getInstance(), map);
                GlobalStat.calculateFolderCount(map);
                GlobalStat.calculateTodoCount(NoteApp.getInstance(), map);
                GlobalStat.aiUseCount(NoteApp.getInstance(), map);
                GlobalStat.getAiStatus(NoteApp.getInstance(), map);
                GlobalStat.reportSettingEvent(GlobalStat.TIP_SETTING_STATUS, map);
                Logger.INSTANCE.d(GlobalStat.TAG, "run: reportSettingStatus === " + map);
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void aiUseCount(Context context, Map<String, Object> map) {
        if (context == null) {
            Logger.INSTANCE.d(TAG, "aiUseCount:  context return");
            return;
        }
        if (aiPreferencesUtils == null) {
            aiPreferencesUtils = new AIPreferencesUtils(context);
            Logger.INSTANCE.d(TAG, "aiUseCount:  aiPreferencesUtils");
        }
        Map<String, ?> all = aiPreferencesUtils.getAll();
        if (all == null) {
            Logger.INSTANCE.d(TAG, "aiAll:  return");
            return;
        }
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Logger.INSTANCE.d(TAG, "aiUseCount: Key: " + key + ", Value: " + value);
            map.put(key, value);
        }
        aiPreferencesUtils.clearAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void reportSettingEvent(String str, Map<String, Object> map) {
        if (map == null) {
            return;
        }
        StatHelper.track(str, "setting", map);
    }

    private static void setLastGlobalReportTime() {
        PreferenceUtils.setLastGlobalReportTime(NoteApp.getInstance(), System.currentTimeMillis() / 86400000);
    }

    private static long getLastGlobalReportTime() {
        return PreferenceUtils.getLastGlobalReportTime(NoteApp.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void getSettingStatus(Context context, Map<String, Object> map) {
        if (context == null) {
            return;
        }
        map.put(FONT_SIZE, getFontSize(Status.fontSizeId));
        map.put(SORT_BY, Status.noteSortWayId == 0 ? BY_CREATION_TIME : BY_MODIFICATION_TIME);
        if (RomUtils.getModelType().equals(RomUtils.DEVICE_PHONE)) {
            map.put(NOTE_LIST_LAYOUT, Status.idGrid ? "grid" : "list");
        } else if (RomUtils.getModelType().equals(RomUtils.DEVICE_FOLD)) {
            map.put(NOTE_WIDE_LAYOUT, Status.arrayWayId ? COLUMNS_MODE : FLAT_MODE);
        }
        map.put(SHORT_HAND_OPEN_STATUS, Status.isQuickNote ? "on" : "off");
        map.put(STRONG_REMINDER_OPEN_STATE, PreferenceUtils.getRemindType(context) ? "on" : "off");
        map.put(SUMMARY_OPEN_STATUS, PreferenceUtils.getEnableExcerptLinkFloat(context) ? "on" : "off");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void getAiStatus(Context context, Map<String, Object> map) {
        Object obj;
        if (context == null) {
            return;
        }
        String str = "false";
        if (AIcrDownloadHelper.getCotConfig(context)) {
            obj = com.miui.notes.ai.utils.PreferenceUtils.getAiModelSwitch(context) ? "true" : "false";
        } else {
            obj = AI_STATUS_NOT_SUPPORT;
        }
        map.put("ai_deepseek_status", obj);
        if (!com.miui.notes.ai.utils.RomUtils.isSupportLocalAI(context)) {
            str = AI_STATUS_NOT_SUPPORT;
        } else if (com.miui.notes.ai.utils.PreferenceUtils.isAcceptedMode(context)) {
            str = "true";
        }
        map.put("ai_deepseek_local_status", str);
        map.put(AI_CLOUD_STATUS, RomUtils.isSupportAiText(context) ? "true" : AI_STATUS_NOT_SUPPORT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:105:0x01c2 A[Catch: Exception -> 0x01be, TryCatch #12 {Exception -> 0x01be, blocks: (B:101:0x01ba, B:105:0x01c2, B:107:0x01c7, B:109:0x01cc, B:111:0x01d1, B:113:0x01d6, B:115:0x01db), top: B:143:0x01ba }] */
    /* JADX WARN: Code duplicated, block: B:107:0x01c7 A[Catch: Exception -> 0x01be, TryCatch #12 {Exception -> 0x01be, blocks: (B:101:0x01ba, B:105:0x01c2, B:107:0x01c7, B:109:0x01cc, B:111:0x01d1, B:113:0x01d6, B:115:0x01db), top: B:143:0x01ba }] */
    /* JADX WARN: Code duplicated, block: B:109:0x01cc A[Catch: Exception -> 0x01be, TryCatch #12 {Exception -> 0x01be, blocks: (B:101:0x01ba, B:105:0x01c2, B:107:0x01c7, B:109:0x01cc, B:111:0x01d1, B:113:0x01d6, B:115:0x01db), top: B:143:0x01ba }] */
    /* JADX WARN: Code duplicated, block: B:111:0x01d1 A[Catch: Exception -> 0x01be, TryCatch #12 {Exception -> 0x01be, blocks: (B:101:0x01ba, B:105:0x01c2, B:107:0x01c7, B:109:0x01cc, B:111:0x01d1, B:113:0x01d6, B:115:0x01db), top: B:143:0x01ba }] */
    /* JADX WARN: Code duplicated, block: B:113:0x01d6 A[Catch: Exception -> 0x01be, TryCatch #12 {Exception -> 0x01be, blocks: (B:101:0x01ba, B:105:0x01c2, B:107:0x01c7, B:109:0x01cc, B:111:0x01d1, B:113:0x01d6, B:115:0x01db), top: B:143:0x01ba }] */
    /* JADX WARN: Code duplicated, block: B:115:0x01db A[Catch: Exception -> 0x01be, TRY_LEAVE, TryCatch #12 {Exception -> 0x01be, blocks: (B:101:0x01ba, B:105:0x01c2, B:107:0x01c7, B:109:0x01cc, B:111:0x01d1, B:113:0x01d6, B:115:0x01db), top: B:143:0x01ba }] */
    /* JADX WARN: Code duplicated, block: B:127:0x0202 A[Catch: Exception -> 0x01fe, TryCatch #14 {Exception -> 0x01fe, blocks: (B:123:0x01fa, B:127:0x0202, B:129:0x0207, B:131:0x020c, B:133:0x0211, B:135:0x0216, B:137:0x021b), top: B:147:0x01fa }] */
    /* JADX WARN: Code duplicated, block: B:129:0x0207 A[Catch: Exception -> 0x01fe, TryCatch #14 {Exception -> 0x01fe, blocks: (B:123:0x01fa, B:127:0x0202, B:129:0x0207, B:131:0x020c, B:133:0x0211, B:135:0x0216, B:137:0x021b), top: B:147:0x01fa }] */
    /* JADX WARN: Code duplicated, block: B:131:0x020c A[Catch: Exception -> 0x01fe, TryCatch #14 {Exception -> 0x01fe, blocks: (B:123:0x01fa, B:127:0x0202, B:129:0x0207, B:131:0x020c, B:133:0x0211, B:135:0x0216, B:137:0x021b), top: B:147:0x01fa }] */
    /* JADX WARN: Code duplicated, block: B:133:0x0211 A[Catch: Exception -> 0x01fe, TryCatch #14 {Exception -> 0x01fe, blocks: (B:123:0x01fa, B:127:0x0202, B:129:0x0207, B:131:0x020c, B:133:0x0211, B:135:0x0216, B:137:0x021b), top: B:147:0x01fa }] */
    /* JADX WARN: Code duplicated, block: B:135:0x0216 A[Catch: Exception -> 0x01fe, TryCatch #14 {Exception -> 0x01fe, blocks: (B:123:0x01fa, B:127:0x0202, B:129:0x0207, B:131:0x020c, B:133:0x0211, B:135:0x0216, B:137:0x021b), top: B:147:0x01fa }] */
    /* JADX WARN: Code duplicated, block: B:137:0x021b A[Catch: Exception -> 0x01fe, TRY_LEAVE, TryCatch #14 {Exception -> 0x01fe, blocks: (B:123:0x01fa, B:127:0x0202, B:129:0x0207, B:131:0x020c, B:133:0x0211, B:135:0x0216, B:137:0x021b), top: B:147:0x01fa }] */
    /* JADX WARN: Code duplicated, block: B:143:0x01ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x01fa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:167:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:170:? A[SYNTHETIC] */
    public static void calculateNotesCount(Context context, Map<String, Object> map) throws Throwable {
        Throwable th;
        Cursor cursor;
        Cursor cursorQuery;
        Cursor cursorQuery2;
        Cursor cursorQuery3;
        Cursor cursorQuery4;
        Cursor cursorQuery5;
        Cursor cursor2;
        Logger logger;
        StringBuilder sb;
        int count;
        int count2;
        int count3;
        int count4;
        int count5;
        int count6;
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            return;
        }
        Cursor cursorQuery6 = null;
        try {
            Uri uri = Notes.Note.CONTENT_URI;
            int count7 = 0;
            Cursor cursorQuery7 = contentResolver.query(uri, new String[]{"_id"}, "type=0 AND deletion_tag <> 1", null, null);
            if (cursorQuery7 == null) {
                count = 0;
            } else {
                try {
                    count = cursorQuery7.getCount();
                } catch (Exception e) {
                    e = e;
                    cursor2 = null;
                    cursorQuery = null;
                    cursorQuery2 = null;
                    cursorQuery3 = cursorQuery2;
                    cursorQuery4 = cursorQuery3;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    try {
                        Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                        e.printStackTrace();
                        if (cursorQuery6 != null) {
                            try {
                                cursorQuery6.close();
                            } catch (Exception e2) {
                                e = e2;
                                logger = Logger.INSTANCE;
                                sb = new StringBuilder("Cursor close error: ");
                                logger.e(TAG, sb.append(e).toString());
                                return;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                        if (cursorQuery3 != null) {
                            cursorQuery3.close();
                        }
                        if (cursorQuery5 != null) {
                            cursorQuery5.close();
                        }
                        if (cursorQuery4 != null) {
                            cursorQuery4.close();
                        }
                        if (cursor2 != null) {
                            cursor2.close();
                            return;
                        }
                        return;
                    } catch (Throwable th2) {
                        Cursor cursor3 = cursor2;
                        th = th2;
                        cursor = cursor3;
                        if (cursorQuery6 != null) {
                            try {
                                cursorQuery6.close();
                            } catch (Exception e3) {
                                Logger.INSTANCE.e(TAG, "Cursor close error: " + e3);
                                throw th;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                        if (cursorQuery3 != null) {
                            cursorQuery3.close();
                        }
                        if (cursorQuery5 != null) {
                            cursorQuery5.close();
                        }
                        if (cursorQuery4 != null) {
                            cursorQuery4.close();
                        }
                        if (cursor == null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor = null;
                    cursorQuery = null;
                    cursorQuery2 = null;
                    cursorQuery3 = cursorQuery2;
                    cursorQuery4 = cursorQuery3;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor == null) {
                        throw th;
                    }
                    cursor.close();
                    throw th;
                }
            }
            map.put(NOTE_QUANTITY, Integer.valueOf(count));
            if (count >= 4000) {
                if (cursorQuery7 != null) {
                    try {
                        cursorQuery7.close();
                        return;
                    } catch (Exception e4) {
                        Logger.INSTANCE.e(TAG, "Cursor close error: " + e4);
                        return;
                    }
                }
                return;
            }
            cursorQuery = contentResolver.query(uri, new String[]{"_id"}, "type=0 AND deletion_tag <> 1 AND note_content_type is 'mind'", null, null);
            if (cursorQuery == null) {
                count2 = 0;
            } else {
                try {
                    count2 = cursorQuery.getCount();
                } catch (Exception e5) {
                    e = e5;
                    cursor2 = null;
                    cursorQuery2 = null;
                    cursorQuery3 = cursorQuery2;
                    cursorQuery4 = cursorQuery3;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                    e.printStackTrace();
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                        return;
                    }
                    return;
                } catch (Throwable th4) {
                    th = th4;
                    cursor = null;
                    cursorQuery2 = null;
                    cursorQuery3 = cursorQuery2;
                    cursorQuery4 = cursorQuery3;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor == null) {
                        throw th;
                    }
                    cursor.close();
                    throw th;
                }
            }
            map.put(MIND_NOTE_QUANTITY, Integer.valueOf(count2));
            cursorQuery2 = contentResolver.query(uri, new String[]{"_id"}, "type=0 AND deletion_tag <> 1 AND note_content_type is 'common'", null, null);
            if (cursorQuery2 == null) {
                count3 = 0;
            } else {
                try {
                    count3 = cursorQuery2.getCount();
                } catch (Exception e6) {
                    e = e6;
                    cursor2 = null;
                    cursorQuery3 = null;
                    cursorQuery4 = cursorQuery3;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                    e.printStackTrace();
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                        return;
                    }
                    return;
                } catch (Throwable th5) {
                    th = th5;
                    cursor = null;
                    cursorQuery3 = null;
                    cursorQuery4 = cursorQuery3;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor == null) {
                        throw th;
                    }
                    cursor.close();
                    throw th;
                }
            }
            map.put(TEXT_NOTE_QUANTITY, Integer.valueOf(count3));
            cursorQuery3 = contentResolver.query(uri, new String[]{"_id"}, "type=0 AND deletion_tag <> 1 AND stick_date >0", null, null);
            if (cursorQuery3 == null) {
                count4 = 0;
            } else {
                try {
                    count4 = cursorQuery3.getCount();
                } catch (Exception e7) {
                    e = e7;
                    cursor2 = null;
                    cursorQuery4 = null;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                    e.printStackTrace();
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                        return;
                    }
                    return;
                } catch (Throwable th6) {
                    th = th6;
                    cursor = null;
                    cursorQuery4 = null;
                    cursorQuery5 = cursorQuery4;
                    cursorQuery6 = cursorQuery7;
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor == null) {
                        throw th;
                    }
                    cursor.close();
                    throw th;
                }
            }
            map.put(STICKY_NOTE_QUANTITY, Integer.valueOf(count4));
            cursorQuery5 = contentResolver.query(NotesProvider.appendFlag(Notes.Note.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY), new String[]{"_id"}, "type=0 AND deletion_tag <> 1 AND parent_id = -4", null, null);
            if (cursorQuery5 == null) {
                count5 = 0;
            } else {
                try {
                    count5 = cursorQuery5.getCount();
                } catch (Exception e8) {
                    e = e8;
                    cursor2 = null;
                    cursorQuery4 = null;
                    cursorQuery6 = cursorQuery7;
                    Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                    e.printStackTrace();
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                        return;
                    }
                    return;
                } catch (Throwable th7) {
                    th = th7;
                    cursor = null;
                    cursorQuery4 = null;
                    cursorQuery6 = cursorQuery7;
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor == null) {
                        throw th;
                    }
                    cursor.close();
                    throw th;
                }
            }
            map.put(PRIVATE_NOTE_QUANTITY, Integer.valueOf(count5));
            cursorQuery4 = contentResolver.query(uri, new String[]{"_id"}, "type=0 AND deletion_tag <> 1 AND note_content_type is 'handwrite'", null, null);
            if (cursorQuery4 == null) {
                count6 = 0;
            } else {
                try {
                    count6 = cursorQuery4.getCount();
                } catch (Exception e9) {
                    e = e9;
                    cursor2 = cursorQuery6;
                    cursorQuery6 = cursorQuery7;
                    Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                    e.printStackTrace();
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                        return;
                    }
                    return;
                } catch (Throwable th8) {
                    th = th8;
                    cursor = cursorQuery6;
                    cursorQuery6 = cursorQuery7;
                    if (cursorQuery6 != null) {
                        cursorQuery6.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    if (cursorQuery3 != null) {
                        cursorQuery3.close();
                    }
                    if (cursorQuery5 != null) {
                        cursorQuery5.close();
                    }
                    if (cursorQuery4 != null) {
                        cursorQuery4.close();
                    }
                    if (cursor == null) {
                        throw th;
                    }
                    cursor.close();
                    throw th;
                }
            }
            map.put(HANDWRITTEN_NOTE_QUANTITY, Integer.valueOf(count6));
            cursorQuery6 = contentResolver.query(uri, new String[]{"_id"}, "type=0 AND deletion_tag <> 1 AND parent_id == 0", null, null);
            if (cursorQuery6 != null) {
                count7 = cursorQuery6.getCount();
            }
            map.put(UNCLASSIFIED_NOTE_QUANTITY, Integer.valueOf(count7));
            if (cursorQuery7 != null) {
                try {
                    cursorQuery7.close();
                } catch (Exception e10) {
                    e = e10;
                    logger = Logger.INSTANCE;
                    sb = new StringBuilder("Cursor close error: ");
                    logger.e(TAG, sb.append(e).toString());
                    return;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            if (cursorQuery2 != null) {
                cursorQuery2.close();
            }
            if (cursorQuery3 != null) {
                cursorQuery3.close();
            }
            if (cursorQuery5 != null) {
                cursorQuery5.close();
            }
            if (cursorQuery4 != null) {
                cursorQuery4.close();
            }
            if (cursorQuery6 != null) {
                cursorQuery6.close();
            }
        } catch (Exception e11) {
            e = e11;
            cursor2 = null;
            cursorQuery = null;
            cursorQuery2 = null;
            cursorQuery3 = null;
            cursorQuery4 = null;
            cursorQuery5 = null;
        } catch (Throwable th9) {
            th = th9;
            cursor = null;
            cursorQuery = null;
            cursorQuery2 = null;
            cursorQuery3 = null;
            cursorQuery4 = null;
            cursorQuery5 = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void calculateFolderCount(Map<String, Object> map) {
        recordFolderCount(Status.folderList);
        if (Status.allFolderCount != -1) {
            map.put(FOLDER_QUANTITY, Integer.valueOf(Status.allFolderCount));
            map.put(CUSTOM_FOLDER_QUANTITY, Integer.valueOf(Status.definedFolderCount));
            map.put(IS_EXCERPT_FOLDER, Status.hasExcerptFolder ? "yes" : "no");
            map.put(IS_SMART_BLACKBOARD, Status.hasBlackBoardFolder ? "yes" : "no");
            map.put(IS_HEAR_FOLDER, Status.hasHearFolder ? "yes" : "no");
            map.put(IS_CALL_NOTE_FOLDER, Status.hasCallFolder ? "yes" : "no");
            map.put(IS_UNCLASSIFIED_FOLDER, Status.hasUnclassifiedFolder ? "yes" : "no");
            map.put(STICKY_FOLDER_QUANTITY, Integer.valueOf(Status.stickyFolderQuantity));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:53:0x00ef A[Catch: Exception -> 0x00eb, TRY_LEAVE, TryCatch #3 {Exception -> 0x00eb, blocks: (B:49:0x00e7, B:53:0x00ef), top: B:59:0x00e7 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00e7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:? A[SYNTHETIC] */
    public static void calculateTodoCount(Context context, Map<String, Object> map) throws Throwable {
        Throwable th;
        Cursor cursor;
        Cursor cursor2;
        Logger logger;
        StringBuilder sb;
        Cursor cursor3;
        int count;
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null) {
            return;
        }
        Cursor cursorQuery = null;
        try {
            int i = Status.todoCount;
            if (i < 1000) {
                map.put(TODO_NOTE_QUANTITY, Integer.valueOf(Status.finishedTodoCount));
                map.put(UNDONE_TODO_NOTE_QUANTITY, Integer.valueOf(Status.unfinishedTodoCount));
                int count2 = 0;
                Cursor cursorQuery2 = contentResolver.query(Uri.parse(Todo.URI_TODO_OUTER), new String[]{"id"}, "list_type == 1 AND local_status != 3 AND local_status != 4", null, null);
                if (cursorQuery2 == null) {
                    count = 0;
                } else {
                    try {
                        count = cursorQuery2.getCount();
                    } catch (Exception e) {
                        e = e;
                        cursor2 = cursorQuery;
                        cursorQuery = cursorQuery2;
                        try {
                            Logger.INSTANCE.e(TAG, "calculateNotesCount error:" + e);
                            if (cursorQuery != null) {
                                try {
                                    cursorQuery.close();
                                } catch (Exception e2) {
                                    e = e2;
                                    logger = Logger.INSTANCE;
                                    sb = new StringBuilder("Cursor close error: ");
                                    logger.e(TAG, sb.append(e).toString());
                                    return;
                                }
                            }
                            if (cursor2 != null) {
                                cursor2.close();
                                return;
                            }
                            return;
                        } catch (Throwable th2) {
                            Cursor cursor4 = cursor2;
                            th = th2;
                            cursor = cursor4;
                            if (cursorQuery != null) {
                                try {
                                    cursorQuery.close();
                                } catch (Exception e3) {
                                    Logger.INSTANCE.e(TAG, "Cursor close error: " + e3);
                                    throw th;
                                }
                            }
                            if (cursor != null) {
                                throw th;
                            }
                            cursor.close();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        cursor = cursorQuery;
                        cursorQuery = cursorQuery2;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        if (cursor != null) {
                            throw th;
                        }
                        cursor.close();
                        throw th;
                    }
                }
                map.put(LIST_TODO_NOTE_QUANTITY, Integer.valueOf(count));
                map.put(GENERAL_TODO_NOTE_QUANTITY, Integer.valueOf(i - count));
                cursorQuery = contentResolver.query(Uri.parse(Todo.URI_TODO_OUTER), new String[]{"id"}, "is_finish == 0 AND remind_type <> 0 AND local_status != 3 AND local_status != 4", null, null);
                if (cursorQuery != null) {
                    count2 = cursorQuery.getCount();
                }
                map.put(UNDONE_TODO_LIST_WITH_REMIND_QUANTITY, Integer.valueOf(count2));
                cursor3 = cursorQuery;
                cursorQuery = cursorQuery2;
            } else {
                cursor3 = null;
            }
            if (cursorQuery != null) {
                try {
                    cursorQuery.close();
                } catch (Exception e4) {
                    e = e4;
                    logger = Logger.INSTANCE;
                    sb = new StringBuilder("Cursor close error: ");
                    logger.e(TAG, sb.append(e).toString());
                    return;
                }
            }
            if (cursor3 != null) {
                cursor3.close();
            }
        } catch (Exception e5) {
            e = e5;
            cursor2 = null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
        }
    }

    private static String getFontSize(int i) {
        if (i == 0) {
            return "small";
        }
        if (i == 2) {
            return FONT_SIZE_BIG;
        }
        if (i == 3) {
            return FONT_SIZE_SUPER_BIG;
        }
        return "default";
    }

    public static void recordFolderCount(List<FolderItemEntity> list) {
        if (list == null) {
            return;
        }
        try {
            Status.allFolderCount = list.size();
            int i = 0;
            Status.definedFolderCount = 0;
            Status.stickyFolderQuantity = 0;
            for (FolderItemEntity folderItemEntity : list) {
                if (i >= 50) {
                    return;
                }
                long folderId = folderItemEntity.getFolderId();
                if (folderItemEntity.isStick()) {
                    Status.stickyFolderQuantity++;
                }
                if (folderItemEntity.getFolderId() > 0) {
                    Status.definedFolderCount++;
                } else if (folderId == -6) {
                    Status.hasExcerptFolder = true;
                } else if (folderId == -7) {
                    Status.hasBlackBoardFolder = true;
                } else if (folderId == -5) {
                    Status.hasHearFolder = true;
                } else if (folderId == -2) {
                    Status.hasCallFolder = true;
                } else if (folderId == 0) {
                    Status.hasUnclassifiedFolder = true;
                }
                i++;
            }
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "recordFolderCount error: " + e);
        }
    }
}
