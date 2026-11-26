package com.miui.notes.tool;

import android.content.Context;
import android.content.SharedPreferences;
import com.miui.common.stat.GlobalStat;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.preference.DatabaseSharedPreferences;

/* JADX INFO: loaded from: classes4.dex */
public class PreferenceUtils {
    private static final String BUNDLE_KEY_TEMP_AUDIO = "temp_audio";
    private static final String CTA_SHA_PREF_NAME = "cta_sha_pref";
    private static final String FIRST_REQUEST_MIAI_PERMISSION = "first_request_miai_permission";
    private static final String LAST_CALL_NOTE_DATA = "last_call_note_data";
    private static final String LAST_MARKET_CODE = "last_market_code";
    private static final String LAST_SETTINGS_REPORT_TIME = "last_settings_report_time";
    private static final String MIAI_PACKAGE_CODE = "miai_package_code";
    private static final String NOTE_PACKAGE_CODE = "note_package_code";
    private static final String POP_DIALOG_TIME = "pop_dialog_time";
    public static final String PREFERENCE_ACCOUNT_ENTITY = "pref_account_entity";
    private static final String PREFERENCE_ACCOUNT_STRING = "account_string";
    private static final String PREFERENCE_ADD_INTRODUCTION = "com.miui.notes.introduction";
    private static final String PREFERENCE_ADD_TODO_INTRODUCTION = "com.miui.todo.introduction";
    private static final String PREFERENCE_ENABLE_EXCERPT_LINK_FLOAT = "pref_enable_excerpt_link_float";
    public static final String PREFERENCE_ENABLE_FLOAT_MODE = "pref_enable_float_mode";
    private static final String PREFERENCE_FIRST_CREATE_HAND_WRITE = "pref_first_create_hand_write";
    private static final String PREFERENCE_FONT_SIZE = "pref_font_size";
    private static final String PREFERENCE_FROM_CREATE = "pref_from_create";
    public static final String PREFERENCE_FULLWINDOWGESTUREY = "pref_full_window_y";
    private static final String PREFERENCE_GRID_MODE = "grid_mode";
    public static final String PREFERENCE_HAND_FULL_UPGRADE_WATER_ENTITY = "pref_hand_full_upgrade_water_entity";
    private static final String PREFERENCE_IS_FLOAT_NOTES_EXPAND = "is_float_notes_expand";
    public static final String PREFERENCE_IS_IN_FULL_WINDOW = "pref_is_in_floatwindow";
    private static final String PREFERENCE_IS_NEED_FULL_UPGRADE = "is_need_full_upgrade";
    private static final String PREFERENCE_IS_NEED_FULL_UPGRADE_WITH_HANDWRITE = "is_need_full_upgrade_with_handwrite";
    private static final String PREFERENCE_IS_NEED_FULL_UPGRADE_WITH_MULTI_HANDWRITE = "is_need_full_upgrade_with_multi_handwrite";
    private static final String PREFERENCE_IS_NEED_SYNC_TITLE = "is_need_sync_title";
    private static final String PREFERENCE_IS_PC_STOP_SYNC = "is_pc_stop_sync";
    public static final String PREFERENCE_MIND_FULL_UPGRADE_WATER_ENTITY = "pref_mind_full_upgrade_water_entity";
    public static final String PREFERENCE_MULTI_HAND_FULL_UPGRADE_WATER_ENTITY = "pref_multi_hand_full_upgrade_water_entity";
    public static final String PREFERENCE_NAVIGATIONBAR_Y = "pref_navigationbar_y";
    private static final String PREFERENCE_REMIND_TYPE = "pref_remind_type";
    private static final String PREFERENCE_SHOW_FINISH = "pref_todo_show_finish";
    private static final String PREFERENCE_UPDATE_ATTACHMENT_DIR = "update_attachment_dir";
    private static final String PREF_ALLOW_AI = "pref_allow_ai";
    private static final String PREF_ALLOW_NETWORK = "pref_allow_network";
    private static final String PREF_ALLOW_XIAOAI = "pref_allow_xiaoai";
    private static final String PREF_AUDIO_TIME_NAME = "audio_time_pref";
    public static final String PREF_CLEAN_ACCOUNT_CACHE = "pref_clean_account_cache";
    private static final String PREF_DELETE_TODO_GUIDE = "pref_delete_todo_guide_with_sync";
    private static final String PREF_FIRST_ACTIVE_TODO_ANIMATION = "pref_first_active_todo_animation";
    private static final String PREF_FIRST_ENABLE_TODO = "pref_first_enable_todo";
    private static final String PREF_FIRST_TODO_SYNC = "pref_first_to_sync";
    private static final String PREF_HANDLE_ALLOW_NETWORK = "pref_handle_allow_network";
    private static final String PREF_HANDLE_ALLOW_XIAOAI = "pref_handle_allow_xiaoai";
    private static final String PREF_IS_KEY_REGISTERED = "pref_is_key_registered";
    private static final String PREF_KEY_ACCEPTED = "cta.pref.accepted";
    private static final String PREF_KEY_ACCOUNT_CHECKED = "account_checked";
    private static final String PREF_KEY_FIRST_ENTER_MIND_NOTE = "first_enter_mind_note";
    private static final String PREF_KEY_FIRST_ENTER_NOTE = "first_enter_note";
    private static final String PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA = "first_enter_note_for_cta";
    private static final String PREF_KEY_GLOBAL_ACCEPTED = "global.cta.pref.accepted";
    private static final String PREF_KEY_HINT_PASSWORD_UPGRADE = "hint_privacy_password_upgrade";
    private static final String PREF_KEY_IS_DOWNGRADE = "pref_key_is_downgrade";
    private static final String PREF_KEY_IS_FIRST_CLICK_AUDIO = "key_is_first_click_cta_audio";
    private static final String PREF_KEY_IS_FIRST_HANDLE = "key_is_first_handle_cta_dialog";
    private static final String PREF_KEY_MAX_SORT_ID = "todo_max_sort_id";
    private static final String PREF_KEY_NOTE_HOMEPAGE_ARRAY = "pref_key_note_homepage_array";
    private static final String PREF_KEY_NOTE_SORT = "pref_key_note_sort";
    private static final String PREF_KEY_PERMISSION_INFO_FOR_KOREA_SHOWED = "permission_info_for_korea_showed";
    private static final String PREF_KEY_PERMISSION_INFO_FOR_POST_NOTIFICATIONS = "permission_info_for_post_notifications";
    private static final String PREF_KEY_SECRET_KEY_GENERATED = "secret_key_generated";
    public static final String PREF_KEY_SHOW_QUICK_NOTE_NEW_USER_GUIDE_BUBBLE = "is_show_quick_note_new_user_guide_bubble";
    private static final String PREF_KEY_SHOW_TOUCH_THROUGH_TIP = "show_touch_through_tip";
    private static final String PREF_KEY_SKIP_CONFIRM_FOR_QUICK_DELETE = "skip_confirm_for_quick_delete";
    private static final String PREF_KEY_SORT_ETAG = "todo_sort_etag";
    public static final String PREF_KEY_TEMP_SAVE_AUDIO_FILE = "temp_save_audio_file";
    private static final String PREF_KEY_TOUCH_EXPLORATION_ENABLED = "pref_key_touch_exploration_enabled";
    private static final String PREF_KEY_WATER_MARKER = "todo_water_marker";
    public static final String PREF_LAST_FOLDER_ID = "pref_last_folder_id";
    private static final String PREF_LAST_USE_MODULE = "pref_last_use_module";
    private static final String PREF_NAVIGATION_STATE = "pref_navigation_state";
    public static final String PREF_PAD_FIRST_CREATE_TIME = "pref_pad_first_create_time";
    private static final String PREF_SHOW_GESTURE_GUIDE = "pref_show_gesture_guide";
    private static final String PREF_SHOW_RECOVER_NOTE_HINT = "show_recover_note_hint";
    private static final String PREF_USE_SOUND_RECORD = "pref_use_sound_record";
    private static final String UPDATE_REMIND_AGAIN = "update_not_again";
    public static final int VALUE_FLOAT_INVALID_Y = -1;
    private static final boolean VALUE_IS_FIRST_TODO_SYNC = true;
    private static final boolean VALUE_IS_IN_FULL_WINDOW = false;
    private static final String VALUE_IS_IN_PRIVATE_NOTE = "is_in_private_note";
    private static final String VALUE_IS_IN_PRIVATE_NOTE_detail = "is_in_private_note_detail";

    public static void setFirstEnterNote(Context context) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA, false).apply();
    }

    public static void setFirstEnterNote(Context context, boolean z) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA, z).apply();
    }

    public static boolean isFirstEnterNote(Context context) {
        return getCTASharedPreferences(context).getBoolean(PREF_KEY_FIRST_ENTER_NOTE_FOR_CTA, true);
    }

    public static String getTemAudioPath(Context context) {
        return getStringPreference(context, BUNDLE_KEY_TEMP_AUDIO, "");
    }

    public static void setTemAudioPath(Context context, String str) {
        setStringPreference(context, BUNDLE_KEY_TEMP_AUDIO, str);
    }

    public static boolean getAddIntroduction(Context context) {
        return getBooleanPreference(context, PREFERENCE_ADD_INTRODUCTION, false);
    }

    public static void setAddIntroduction(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_ADD_INTRODUCTION, z);
    }

    public static boolean getAddTodoIntroduction(Context context) {
        return getBooleanPreference(context, PREFERENCE_ADD_TODO_INTRODUCTION, false);
    }

    public static void setAddTodoIntroduction(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_ADD_TODO_INTRODUCTION, z);
    }

    public static void setCleanAccountCachePref(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREF_CLEAN_ACCOUNT_CACHE, z);
    }

    public static boolean getCleanAccountCachePref() {
        return getBooleanPreference(NoteApp.getInstance(), PREF_CLEAN_ACCOUNT_CACHE, false);
    }

    public static boolean getGridMode(Context context) {
        if (RomUtils.isPadMode()) {
            return false;
        }
        return getBooleanPreference(context, PREFERENCE_GRID_MODE, true);
    }

    public static void setGridMode(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_GRID_MODE, z);
    }

    public static boolean getUpdateAttachmentDir(Context context) {
        return getBooleanPreference(context, PREFERENCE_UPDATE_ATTACHMENT_DIR, false);
    }

    public static void setUpdateAttachmentDir(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_UPDATE_ATTACHMENT_DIR, z);
    }

    public static int getFontSize(Context context, int i) {
        SharedPreferences defaultSharedPreferences = getDefaultSharedPreferences(context);
        int i2 = defaultSharedPreferences.getInt(PREFERENCE_FONT_SIZE, i);
        if (i2 >= ResourceParser.TextAppearanceResources.getResourcesSize()) {
            defaultSharedPreferences.edit().putInt(PREFERENCE_FONT_SIZE, i).apply();
        } else {
            i = i2;
        }
        GlobalStat.Status.fontSizeId = i;
        return i;
    }

    public static void setFontSize(Context context, int i) {
        setIntegerPreference(context, PREFERENCE_FONT_SIZE, i);
    }

    public static void setNoteSortWay(Context context, int i) {
        setIntegerPreference(context, PREF_KEY_NOTE_SORT, i);
    }

    public static int getNoteSortWay(Context context, int i) {
        int integerPreference = getIntegerPreference(context, PREF_KEY_NOTE_SORT, i);
        GlobalStat.Status.noteSortWayId = integerPreference;
        return integerPreference;
    }

    public static void setNoteHomePageArrayWay(Context context, int i) {
        setIntegerPreference(context, PREF_KEY_NOTE_HOMEPAGE_ARRAY, i);
    }

    public static int getNoteHomePageArrayWay(Context context, int i) {
        return getIntegerPreference(context, PREF_KEY_NOTE_HOMEPAGE_ARRAY, i);
    }

    public static boolean getTouchThroughTip(Context context) {
        return getBooleanPreference(context, PREF_KEY_SHOW_TOUCH_THROUGH_TIP, false);
    }

    public static void setTouchThroughTip(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_SHOW_TOUCH_THROUGH_TIP, z);
    }

    public static boolean getAccountChecked(Context context) {
        return getBooleanPreference(context, PREF_KEY_ACCOUNT_CHECKED, false);
    }

    public static void setAccountChecked(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_ACCOUNT_CHECKED, z);
    }

    public static boolean getSecretKeyGenerated(Context context) {
        return getBooleanPreference(context, PREF_KEY_SECRET_KEY_GENERATED, false);
    }

    public static void setSecretKeyGenerated(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_SECRET_KEY_GENERATED, z);
    }

    public static boolean getKoreaPermissionInfoShowed(Context context) {
        return getBooleanPreference(context, PREF_KEY_PERMISSION_INFO_FOR_KOREA_SHOWED, false);
    }

    public static void setKoreaPermissionInfoShowed(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_PERMISSION_INFO_FOR_KOREA_SHOWED, z);
    }

    public static boolean getPostNotificationsPermissionInfoShowed(Context context) {
        return getBooleanPreference(context, PREF_KEY_PERMISSION_INFO_FOR_POST_NOTIFICATIONS, false);
    }

    public static void setPostNotificationsPermissionInfoShowed(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_PERMISSION_INFO_FOR_POST_NOTIFICATIONS, z);
    }

    public static boolean getShowRecoverNotesHint(Context context) {
        return getBooleanPreference(context, PREF_SHOW_RECOVER_NOTE_HINT, false);
    }

    public static void setShowRecoverNotesHint(Context context, boolean z) {
        setBooleanPreference(context, PREF_SHOW_RECOVER_NOTE_HINT, z);
    }

    public static boolean getSkipConfirmForQuickDelete(Context context) {
        return getBooleanPreference(context, PREF_KEY_SKIP_CONFIRM_FOR_QUICK_DELETE, false);
    }

    public static void setSkipConfirmForQuickDelete(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_SKIP_CONFIRM_FOR_QUICK_DELETE, z);
    }

    public static boolean getHintPasswordUpgrade(Context context) {
        return getBooleanPreference(context, PREF_KEY_HINT_PASSWORD_UPGRADE, false);
    }

    public static void setHintPasswordUpgrade(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_HINT_PASSWORD_UPGRADE, z);
    }

    public static int getPrefLastUseModule(Context context) {
        return getIntegerPreference(context, PREF_LAST_USE_MODULE, 0);
    }

    public static void setPrefLastUseModule(Context context, int i) {
        setIntegerPreference(context, PREF_LAST_USE_MODULE, i);
    }

    public static boolean getRemindType(Context context) {
        return getBooleanPreference(context, PREFERENCE_REMIND_TYPE, false);
    }

    public static void setRemindType(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_REMIND_TYPE, z);
    }

    public static boolean getShowFinishedTodo(Context context) {
        return getBooleanPreference(context, PREFERENCE_SHOW_FINISH, true);
    }

    public static void setShowFinishedTodo(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_SHOW_FINISH, z);
    }

    public static boolean getEnableFloatMode(Context context) {
        return getBooleanPreference(context, PREFERENCE_ENABLE_FLOAT_MODE, false);
    }

    public static void setEnableFloatMode(Context context, boolean z) {
        GlobalStat.Status.isQuickNote = z;
        setBooleanPreference(context, PREFERENCE_ENABLE_FLOAT_MODE, z);
    }

    public static void setFullWindowGestureY(Context context, int i) {
        setIntegerPreference(context, PREFERENCE_FULLWINDOWGESTUREY, i);
    }

    public static int getFullWindowGestureY(Context context) {
        return getIntegerPreference(context, PREFERENCE_FULLWINDOWGESTUREY, -1);
    }

    public static void setNavigationbarY(Context context, int i) {
        setIntegerPreference(context, PREFERENCE_NAVIGATIONBAR_Y, i);
    }

    public static int getNavigationbarY(Context context) {
        return getIntegerPreference(context, PREFERENCE_NAVIGATIONBAR_Y, -1);
    }

    public static void setIsFullWindow(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_IS_IN_FULL_WINDOW, z);
    }

    public static boolean getIsFullWindow(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_IN_FULL_WINDOW, false);
    }

    public static boolean getEnableExcerptLinkFloat(Context context) {
        return getBooleanPreference(context, PREFERENCE_ENABLE_EXCERPT_LINK_FLOAT, false);
    }

    public static void setEnableExcerptLinkFloat(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_ENABLE_EXCERPT_LINK_FLOAT, z);
    }

    public static void setIsFirstTodoSync(Context context, boolean z) {
        setBooleanPreference(context, PREF_FIRST_TODO_SYNC, z);
    }

    public static boolean getIsFirstTodoSync(Context context) {
        return getBooleanPreference(context, PREF_FIRST_TODO_SYNC, true);
    }

    public static void setPrefDeleteTodoGuide(Context context, boolean z) {
        setBooleanPreference(context, PREF_DELETE_TODO_GUIDE, z);
    }

    public static boolean getPrefDeleteTodoGuide(Context context) {
        return getBooleanPreference(context, PREF_DELETE_TODO_GUIDE, false);
    }

    public static void setIsFirstEnableTodo(Context context, boolean z) {
        setBooleanPreference(context, PREF_FIRST_ENABLE_TODO, z);
    }

    public static boolean getIsFirstEnableTodo(Context context) {
        return getBooleanPreference(context, PREF_FIRST_ENABLE_TODO, true);
    }

    public static void setIsFirstActiveTodoAnimation(Context context, boolean z) {
        setBooleanPreference(context, PREF_FIRST_ACTIVE_TODO_ANIMATION, z);
    }

    public static boolean getIsFirstActiveTodoAnimation(Context context) {
        return getBooleanPreference(context, PREF_FIRST_ACTIVE_TODO_ANIMATION, true);
    }

    public static boolean getBooleanPreference(Context context, String str, boolean z) {
        return getDefaultSharedPreferences(context).getBoolean(str, z);
    }

    public static void setBooleanPreference(Context context, String str, boolean z) {
        Logger.INSTANCE.d("PreferenceUtil", "save sp " + str + ":" + z + ",ret:" + getDefaultSharedPreferences(context).edit().putBoolean(str, z).commit());
    }

    public static int getIntegerPreference(Context context, String str, int i) {
        return getDefaultSharedPreferences(context).getInt(str, i);
    }

    public static void setIntegerPreference(Context context, String str, int i) {
        getDefaultSharedPreferences(context).edit().putInt(str, i).apply();
    }

    public static long getLongPreference(Context context, String str, long j) {
        return getDefaultSharedPreferences(context).getLong(str, j);
    }

    public static void setLongPreference(Context context, String str, long j) {
        getDefaultSharedPreferences(context).edit().putLong(str, j).apply();
    }

    public static String getStringPreference(Context context, String str, String str2) {
        return getDefaultSharedPreferences(context).getString(str, str2);
    }

    public static void setStringPreference(Context context, String str, String str2) {
        getDefaultSharedPreferences(context).edit().putString(str, str2).apply();
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return DatabaseSharedPreferences.INSTANCE.getDefault();
    }

    public static void setDowngrade(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_IS_DOWNGRADE, z);
    }

    public static boolean getDowngrade(Context context) {
        return getBooleanPreference(context, PREF_KEY_IS_DOWNGRADE, false);
    }

    public static void setFirstEnterMindNote(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_FIRST_ENTER_MIND_NOTE, z);
    }

    public static boolean getFirstEnterMindNote(Context context) {
        return getBooleanPreference(context, PREF_KEY_FIRST_ENTER_MIND_NOTE, true);
    }

    public static void setCTAAccepted(boolean z, Context context) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_ACCEPTED, z).apply();
    }

    public static boolean isCTAAccepted(Context context) {
        return RomUtils.isInternationalBuild() || getCTASharedPreferences(context).getBoolean(PREF_KEY_ACCEPTED, false);
    }

    public static boolean isInternationalCTAAccepted(Context context) {
        return RomUtils.isInternationalBuild() && getCTASharedPreferences(context).getBoolean(PREF_KEY_GLOBAL_ACCEPTED, false);
    }

    public static boolean isAiCTAAccepted(Context context) {
        if (isInternationalCTAAccepted(context)) {
            return true;
        }
        return !RomUtils.isInternationalBuild() && getCTASharedPreferences(context).getBoolean(PREF_KEY_ACCEPTED, false);
    }

    public static void setGlobalPermissionAccepted(boolean z, Context context) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_GLOBAL_ACCEPTED, z).apply();
    }

    public static void setFirstHandle(boolean z, Context context) {
        getCTASharedPreferences(context).edit().putBoolean(PREF_KEY_IS_FIRST_HANDLE, z).apply();
    }

    public static boolean isFirstHandle(Context context) {
        return getCTASharedPreferences(context).getBoolean(PREF_KEY_IS_FIRST_HANDLE, true) && !isCTAAccepted(context);
    }

    public static SharedPreferences getAudioTimeSharedPref() {
        return NoteApp.getInstance().getSharedPreferences(PREF_AUDIO_TIME_NAME, 0);
    }

    public static void setAudioTimeByFileName(String str, int i) {
        getAudioTimeSharedPref().edit().putInt(str, i).apply();
    }

    public static int getAudioTimeByFileName(String str) {
        return getAudioTimeSharedPref().getInt(str, -1);
    }

    public static SharedPreferences getAccountSharedPref() {
        return NoteApp.getInstance().getSharedPreferences(PREFERENCE_ACCOUNT_STRING, 0);
    }

    public static void setAccountStringName(String str) {
        getAccountSharedPref().edit().putString(PREFERENCE_ACCOUNT_STRING, str).apply();
    }

    public static String getAccountStringName() {
        return getAccountSharedPref().getString(PREFERENCE_ACCOUNT_STRING, "");
    }

    public static void setAllowNetwork(Context context, boolean z) {
        setBooleanPreference(context, PREF_ALLOW_NETWORK, z);
    }

    public static boolean getAllowNetwork(Context context) {
        return getBooleanPreference(context, PREF_ALLOW_NETWORK, false);
    }

    public static void setHandleAllowNetwork(Context context, boolean z) {
        setBooleanPreference(context, PREF_HANDLE_ALLOW_NETWORK, z);
    }

    public static boolean getHandleAllowNetwork(Context context) {
        return getBooleanPreference(context, PREF_HANDLE_ALLOW_NETWORK, false);
    }

    public static void setAllowXiaoai(Context context, boolean z) {
        setBooleanPreference(context, PREF_ALLOW_XIAOAI, z);
    }

    public static boolean getAllowXiaoai(Context context) {
        return getBooleanPreference(context, PREF_ALLOW_XIAOAI, false);
    }

    public static void setHandleAllowXiaoai(Context context, boolean z) {
        setBooleanPreference(context, PREF_HANDLE_ALLOW_XIAOAI, z);
    }

    public static boolean getHandleAllowXiaoai(Context context) {
        return getBooleanPreference(context, PREF_HANDLE_ALLOW_XIAOAI, false);
    }

    public static void setPrefShowGestureGuide(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREF_SHOW_GESTURE_GUIDE, z);
    }

    public static boolean getPrefShowGestureGuide() {
        return getBooleanPreference(NoteApp.getInstance(), PREF_SHOW_GESTURE_GUIDE, false);
    }

    public static void setMaxSortID(long j) {
        setLongPreference(NoteApp.getInstance(), PREF_KEY_MAX_SORT_ID, j);
    }

    public static long getMaxSortID() {
        return getLongPreference(NoteApp.getInstance(), PREF_KEY_MAX_SORT_ID, 0L);
    }

    public static void setSortEtag(long j) {
        setLongPreference(NoteApp.getInstance(), PREF_KEY_SORT_ETAG, j);
    }

    public static long getSortEtag() {
        return getLongPreference(NoteApp.getInstance(), PREF_KEY_SORT_ETAG, 0L);
    }

    public static void setWaterMarker(long j) {
        setLongPreference(NoteApp.getInstance(), PREF_KEY_WATER_MARKER, j);
    }

    public static long getWaterMarker() {
        return getLongPreference(NoteApp.getInstance(), PREF_KEY_WATER_MARKER, 0L);
    }

    public static boolean getIsNeedSyncTitle(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_NEED_SYNC_TITLE, false);
    }

    public static void setIsNeedSyncTitle(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREFERENCE_IS_NEED_SYNC_TITLE, z);
    }

    public static void setMarketCode(Context context, int i) {
        setIntegerPreference(context, LAST_MARKET_CODE, i);
    }

    public static int getMarketCode(Context context) {
        return getIntegerPreference(context, LAST_MARKET_CODE, 0);
    }

    public static void setUpdateRemindAgain(boolean z, Context context) {
        getCTASharedPreferences(context).edit().putBoolean(UPDATE_REMIND_AGAIN, z).apply();
    }

    public static boolean isUpdateRemindAgain(Context context) {
        return getCTASharedPreferences(context).getBoolean(UPDATE_REMIND_AGAIN, true);
    }

    public static long getPopDialogTime() {
        return getLongPreference(NoteApp.getInstance(), POP_DIALOG_TIME, 0L);
    }

    public static void setPopDialogTime(long j) {
        setLongPreference(NoteApp.getInstance(), POP_DIALOG_TIME, j);
    }

    public static boolean getIsNeedFullUpgrade(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_NEED_FULL_UPGRADE, false);
    }

    public static void setIsNeedFullUpgrade(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREFERENCE_IS_NEED_FULL_UPGRADE, z);
    }

    public static boolean getIsNeedFullUpgradeWithHandwrite(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_NEED_FULL_UPGRADE_WITH_HANDWRITE, false);
    }

    public static void setIsNeedFullUpgradeWithHandwrite(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREFERENCE_IS_NEED_FULL_UPGRADE_WITH_HANDWRITE, z);
    }

    public static boolean getIsPcStopSync(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_PC_STOP_SYNC, false);
    }

    public static void setIsPcStopSync(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREFERENCE_IS_PC_STOP_SYNC, z);
    }

    public static boolean getIsFloatNotesExpand(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_FLOAT_NOTES_EXPAND, false);
    }

    public static void setFloatNotesExpand(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREFERENCE_IS_FLOAT_NOTES_EXPAND, z);
    }

    public static boolean hasTempAudioFile() {
        return NoteApp.getInstance().getSharedPreferences(null, 0).contains(PREF_KEY_TEMP_SAVE_AUDIO_FILE);
    }

    public static String getTempAudioFile() {
        return NoteApp.getInstance().getSharedPreferences(null, 0).getString(PREF_KEY_TEMP_SAVE_AUDIO_FILE, "");
    }

    public static void saveTempAudioFile(String str) {
        NoteApp.getInstance().getSharedPreferences(null, 0).edit().putString(PREF_KEY_TEMP_SAVE_AUDIO_FILE, str).apply();
    }

    public static void removeTempAudioFile() {
        NoteApp.getInstance().getSharedPreferences(null, 0).edit().remove(PREF_KEY_TEMP_SAVE_AUDIO_FILE).apply();
    }

    public static void setPadFirstCreateTime(long j) {
        setLongPreference(NoteApp.getInstance(), PREF_PAD_FIRST_CREATE_TIME, j);
    }

    public static long getPadFirstCreateTime() {
        return getLongPreference(NoteApp.getInstance(), PREF_PAD_FIRST_CREATE_TIME, 0L);
    }

    public static long getLastGlobalReportTime(Context context) {
        return getLongPreference(context, LAST_SETTINGS_REPORT_TIME, 0L);
    }

    public static void setLastGlobalReportTime(Context context, long j) {
        setLongPreference(context, LAST_SETTINGS_REPORT_TIME, j);
    }

    public static void setLastCallNoteData(long j) {
        setLongPreference(NoteApp.getInstance(), LAST_CALL_NOTE_DATA, j);
    }

    public static long getLastCallNoteData() {
        return getLongPreference(NoteApp.getInstance(), LAST_CALL_NOTE_DATA, 0L);
    }

    public static void setAllowAi(Context context, boolean z) {
        setBooleanPreference(context, PREF_ALLOW_AI, z);
    }

    public static boolean getAllowAi(Context context) {
        return getBooleanPreference(context, PREF_ALLOW_AI, false);
    }

    public static boolean getIsFirstRequestMIAIPermission() {
        return getBooleanPreference(NoteApp.getInstance(), FIRST_REQUEST_MIAI_PERMISSION, true);
    }

    public static void setIsFirstRequestMIAIPermission() {
        setBooleanPreference(NoteApp.getInstance(), FIRST_REQUEST_MIAI_PERMISSION, false);
    }

    public static int getMyPackageVersionCode() {
        return getIntegerPreference(NoteApp.getInstance(), NOTE_PACKAGE_CODE, -1);
    }

    public static void setMyPackageVersionCode(int i) {
        setIntegerPreference(NoteApp.getInstance(), NOTE_PACKAGE_CODE, i);
    }

    public static int getMIAIPackageVersionCode() {
        return getIntegerPreference(NoteApp.getInstance(), MIAI_PACKAGE_CODE, -1);
    }

    public static void setMIAIPackageVersionCode(int i) {
        setIntegerPreference(NoteApp.getInstance(), MIAI_PACKAGE_CODE, i);
    }

    public static void setFirstClickAudio(Context context, boolean z) {
        setBooleanPreference(context, PREF_KEY_IS_FIRST_CLICK_AUDIO, z);
    }

    public static boolean isFirstClickAudio(Context context) {
        return getBooleanPreference(context, PREF_KEY_IS_FIRST_CLICK_AUDIO, true);
    }

    public static void setUseSoundRecord(Context context, boolean z) {
        setBooleanPreference(context, PREF_USE_SOUND_RECORD, z);
    }

    public static boolean getUseSoundRecord(Context context) {
        return getBooleanPreference(context, PREF_USE_SOUND_RECORD, true);
    }

    public static boolean getIsNeedFullUpgradeWithMultiHandwrite(Context context) {
        return getBooleanPreference(context, PREFERENCE_IS_NEED_FULL_UPGRADE_WITH_MULTI_HANDWRITE, false);
    }

    public static void setIsNeedFullUpgradeWithMultiHandwrite(boolean z) {
        Logger.INSTANCE.d("PreferenceUtils", "setIsNeedFullUpgradeWithMultiHandwrite:" + z);
        setBooleanPreference(NoteApp.getInstance(), PREFERENCE_IS_NEED_FULL_UPGRADE_WITH_MULTI_HANDWRITE, z);
    }

    public static void setNavigationState(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREF_NAVIGATION_STATE, z);
    }

    public static boolean getNavigationState() {
        return getBooleanPreference(NoteApp.getInstance(), PREF_NAVIGATION_STATE, false);
    }

    private static SharedPreferences getCTASharedPreferences(Context context) {
        return context.getSharedPreferences(CTA_SHA_PREF_NAME, 0);
    }

    public static void setTouchExperienceState(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREF_KEY_TOUCH_EXPLORATION_ENABLED, z);
    }

    public static boolean getTouchExperienceState() {
        return getBooleanPreference(NoteApp.getInstance(), PREF_KEY_TOUCH_EXPLORATION_ENABLED, false);
    }

    public static boolean getIsKeyRegistered() {
        return getBooleanPreference(NoteApp.getInstance(), PREF_IS_KEY_REGISTERED, false);
    }

    public static void setIsKeyRegistered(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), PREF_IS_KEY_REGISTERED, z);
    }

    public static boolean getPreferenceFromCreate(Context context) {
        return getBooleanPreference(context, PREFERENCE_FROM_CREATE, false);
    }

    public static void setPreferenceFromCreate(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_FROM_CREATE, z);
    }

    public static boolean getPreferenceFirstCreateHandWrite(Context context) {
        return getBooleanPreference(context, PREFERENCE_FIRST_CREATE_HAND_WRITE, false);
    }

    public static void setPreferenceFirstCreateHandWrite(Context context, boolean z) {
        setBooleanPreference(context, PREFERENCE_FIRST_CREATE_HAND_WRITE, z);
    }

    public static void setIsInPrivate(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), VALUE_IS_IN_PRIVATE_NOTE, z);
    }

    public static boolean getIsInPrivate() {
        return getBooleanPreference(NoteApp.getInstance(), VALUE_IS_IN_PRIVATE_NOTE, false);
    }

    public static void setIsInPrivateDetail(boolean z) {
        setBooleanPreference(NoteApp.getInstance(), VALUE_IS_IN_PRIVATE_NOTE_detail, z);
    }

    public static boolean getIsInPrivateDetail() {
        return getBooleanPreference(NoteApp.getInstance(), VALUE_IS_IN_PRIVATE_NOTE_detail, false);
    }
}
