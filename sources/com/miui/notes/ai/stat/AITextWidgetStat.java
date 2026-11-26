package com.miui.notes.ai.stat;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.base.utils.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class AITextWidgetStat {
    private static final String AIGLOBAL_EMOJI_COUNT = "aiglobal_emoji_count";
    private static final String AIGLOBAL_EMOJI_TYPE_SET = "ai_global_emoji_type_set";
    private static final String AI_ACTION_LOCATION = "ai_action_location";
    private static final String AI_ACTION_WAY = "ai_action_way";
    private static final String AI_APK_NAME = "apk";
    public static final String AI_BACK_CLOSE = "back_close";
    public static final String AI_BLANK_AREA_CLOSE = "blank_area_close";
    public static final String AI_BTN_CLOSE = "btn_close";
    public static final String AI_CANCEL = "ai_cancel";
    public static final String AI_CANCELED = "729.5.24.1.39610";
    private static final String AI_CANCELED_EVENT = "ai_canceled";
    public static final String AI_CLOUD_TYPE = "ai_cloud_type";
    public static final String AI_COPY = "ai_copy";
    public static final String AI_DEEPSEEK_CLICK = "729.5.23.1.42136";
    public static final String AI_DEEPSEEK_STATUS = "ai_deepseek_status";
    public static final String AI_DEEP_SEEK_COUNT = "ai_deepseek_result_count";
    public static final String AI_DEEP_SEEK_LOCAL_STATUS = "ai_deepseek_local_status";
    public static final String AI_DEEP_SEEK_MODEL_DURATION = "ai_deepseek_model_result_duration";
    public static final String AI_DEEP_SEEK_SELECTED = "ai_deepseek_status";
    private static final String AI_DETAIL = "ai_detail";
    public static final String AI_DETAIL_NONE = "detail_none";
    public static final String AI_DONE = "ai_done";
    public static final String AI_END = "729.5.24.1.38429";
    private static final String AI_END_EVENT = "ai_end";
    private static final String AI_END_WAY = "ai_end_way";
    public static final String AI_EditBar_ENTER = "729.5.1.1.38427";
    private static final String AI_GLOBAL_CONTENT_TYPE_SET = "ai_global_content_type_set";
    private static final String AI_GLOBAL_COUNT_TYPE_SET = "ai_global_count_type_set";
    public static final String AI_INPUT_BOX = "ai_input_box";
    public static final String AI_INSERT = "ai_insert";
    public static final String AI_Identify_Refuse = "identify_refuse";
    public static final String AI_LOCAL_SETTING_CLICK = "729.37.0.1.42137";
    public static final String AI_LOCAL_TYPE = "ai_local_type";
    public static final String AI_MODEL_RESULT_DURATION = "ai_model_result_duration";
    public static final String AI_PANEL_DURATION = "ai_panel_duration";
    private static final String AI_RESULT_COUNT = "ai_result_count";
    private static final String AI_RESULT_DURATION = "ai_result_duration";
    private static final String AI_RESULT_REASON = "ai_result_reason";
    private static final String AI_RESULT_STATUS = "ai_result_status";
    private static final String AI_SCENE = "ai_scene";
    private static final String AI_SESSION_ID = "ai_session_id";
    private static final String AI_SHOW_TYPE = "ai_show_type";
    public static final String AI_START = "729.5.23.1.38428";
    private static final String AI_START_EVENT = "ai_start";
    private static final String AI_TEXT_SOURCE = "ai_text_source";
    public static final String AI_TEXT_SOURCE_INPUT = "text_source_input";
    public static final String AI_TEXT_SOURCE_RECOMMEND = "text_source_recommend";
    public static final String AI_TOOL_ENTER = "729.5.1.1.38426";
    private static final String AI_TRACE_TYPE = "ai_trace_type";
    public static final String AI_UP_LEFT_BACK_CLOSE = "up_left_back_close";
    public static final String AI_USED = "729.5.24.1.38430";
    public static final String AI_USED_CLOSE = "ai_used";
    private static final String AI_USED_EVENT = "ai_used";
    public static final String CHOOSE_TEXT = "choose_text";
    public static final String CLICK = "729.5.23.1.39609";
    public static final String CLICK_EDIT_ENTER = "click_edit";
    public static final String CLOSURE_WINDOW = "closure_window";
    private static final String CONTENT_STATE = "content_status";
    private static final String CONTENT_STYLE = "content_style";
    private static final String CONTENT_TEXT_COUNT = "content_text_count";
    private static final String CONTENT_TYPE = "content_type";
    private static String DEVICE_LEVEL = "device_level";
    private static final String EDIT_BAR_ENTER_WAY = "enter_way";
    private static final String ELEMENT_NAME = "element_name";
    public static final String EMPTY_STATUS = "empty_status";
    private static final String EVENT_CLICK = "click";
    private static final String EVENT_EXPOSE = "expose";
    private static final String EVENT_SETTING = "setting";
    public static final String EXPOSE = "729.5.23.1.39608";
    public static final String FAIL = "fail";
    public static final String FALSE = "false";
    public static final String GENERATE_IN_FLOATING_WINDOW = "generate_in_floating_window";
    public static final String GENERATE_IN_NOTE = "generate_in_note";
    public static final String GENERATE_STOP = "generate_stop";
    public static final String GLOBAL_AI_FEEDBACK_CLICK = "729.5.24.1.45161";
    public static final String GLOBAL_AI_PANEL_COPY_PUBLISH = "copy_publish";
    public static final String GLOBAL_AI_TEXT_AI_SCENE = "TYPE_INPUT";
    public static final String GLOBAL_INPOUT_BOX = "global_input_box";
    public static final String IS_GLOBAL = "is_global";
    private static final String IS_RECOMMEND_SHOW = "is_recommend_show";
    public static final String LONG_TOUCH_CURSOR = "long_touch_cursor";
    private static String MODEL_TYPE = "model_type";
    public static final String NEW_GENERATE = "new_generate";
    public static final String NEW_NOTE = "new_note";
    public static final String NO_TITLE_CONTENT = "noTitle_content";
    public static final String PATE_TYPE = "page_type";
    public static final String REFRESH_GENERATE = "refresh_generate";
    private static String SCREEN_DIRECTION = "screen_direction";
    private static String SCREEN_TYPE = "screen_type";
    private static final String SELECT_CONTENT_TEXT_COUNT = "select_content_text_count";
    private static final String SELECT_CONTENT_TEXT_LOCATION = "select_content_text_location";
    public static final String SELECT_CONTENT_TEXT_LOCATION_CONTENT = "content";
    public static final String SELECT_CONTENT_TEXT_LOCATION_TITTLE = "title";
    private static final String SELECT_CONTENT_TEXT_SCENE = "select_content_text_scene";
    public static final String SUCCESS = "success";
    public static final String TAG = "AITextWidgetStat";
    public static final String TITLE_AND_CONTENT = "title_and_content";
    public static final String TITLE_NO_CONTENT = "title_noContent";
    private static final String TRACE_ID = "trace_id";
    private static String TRACK_ID = "track_id";
    public static final String TRUE = "true";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_CHECK_QUANTITY = "type_check_quantity";
    public static final String TYPE_COMMON_QUANTITY = "type_common_quantity";
    public static final String TYPE_EASY_QUANTITY = "type_easy_quantity";
    public static final String TYPE_EXTENSION_QUANTITY = "type_extension_quantity";
    public static final String TYPE_FRIENDLY_QUANTITY = "type_friendly_quantity";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_INPUT_QUANTITY = "type_input_quantity";
    public static final String TYPE_PROFESSIONAL_QUANTITY = "type_professional_quantity";
    public static final String TYPE_RECOMMEND_QUANTITY = "type_recommend_quantity";
    public static final String TYPE_SUMMARY_QUANTITY = "type_summary_quantity";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_UNKN0WN = "unknown";
    private static AITextStatListener mAiTextStatListener;
    private static volatile IOneTrackStat mStatImplInstance;
    private static AITextWidgetStat mStatInstance;
    private String mDeviceLevel;
    private String mModelType;
    private String mUUID;

    public interface AITextStatListener {
        void onAiTextStat(String str, String str2, Map<String, Object> map);
    }

    public void setAITextWidgetStat(AITextStatListener aITextStatListener) {
        mAiTextStatListener = aITextStatListener;
    }

    public static void reportAiNoteToolWidgetClick(String str, String str2, int i, String str3, String str4, String str5, String str6, int i2, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put("content_status", str2);
        map.put("content_text_count", Integer.valueOf(i));
        map.put("content_style", str4);
        map.put("enter_way", str5);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str6);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        reportClickEvent(AI_TOOL_ENTER, map, context);
        Logger.INSTANCE.d(TAG, "reportAiEditorBarClick: " + map);
    }

    public static void reportAiEditorBarClick(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put("content_status", str2);
        map.put("content_text_count", str3);
        map.put("enter_way", str4);
        map.put("content_style", str6);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str7);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i));
        map.put("apk", str5);
        reportClickEvent(AI_EditBar_ENTER, map, context);
        Logger.INSTANCE.d(TAG, "reportAiEditorBarClick: " + map);
    }

    public static void reportAiStart(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, String str8, String str9, String str10, int i2, Context context) {
        Logger.INSTANCE.d(TAG, "reportAiStart");
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put(AI_SCENE, str2);
        map.put(AI_ACTION_WAY, str3);
        map.put(AI_ACTION_LOCATION, str4);
        map.put(AI_DETAIL, str5);
        map.put("apk", str6);
        map.put("content_status", str7);
        map.put("content_text_count", Integer.valueOf(i));
        map.put(AI_TEXT_SOURCE, str8);
        map.put(AI_SESSION_ID, str9);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str10);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        reportAiStartEvent(AI_START, map, context);
    }

    public static void reportGlobalAiOpen(String str, String str2, int i, String str3, String str4, int i2, boolean z, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(IS_GLOBAL, true);
        map.put(AI_SHOW_TYPE, GLOBAL_INPOUT_BOX);
        map.put("content_status", str3);
        map.put("content_text_count", Integer.valueOf(i));
        map.put("content_style", Integer.valueOf(i));
        map.put(SELECT_CONTENT_TEXT_LOCATION, str4);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        map.put(IS_RECOMMEND_SHOW, Boolean.valueOf(z));
        reportAiOpenEvent(EXPOSE, map, context);
        Logger.INSTANCE.d(TAG, "reportGlobalAiOpen: " + map);
    }

    public static void reportGlobalAiClose(String str, String str2, String str3, int i, String str4, String str5, int i2, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(IS_GLOBAL, true);
        map.put("content_text_count", Integer.valueOf(i));
        map.put(AI_TEXT_SOURCE, str4);
        map.put(AI_DETAIL, str3);
        map.put(AI_END_WAY, str5);
        map.put(AI_PANEL_DURATION, Integer.valueOf(i2));
        reportClickEvent(CLICK, map, context);
        Logger.INSTANCE.d(TAG, "reportGlobalAiClose: " + map);
    }

    public static void reportGlobalAiCanceled(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, int i2, String str11, String str12, String str13, int i3, int i4, String str14, String str15, String str16, String str17, String str18, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(AI_TEXT_SOURCE, str10);
        map.put(AI_DETAIL, str3);
        map.put(IS_GLOBAL, true);
        map.put(AI_GLOBAL_CONTENT_TYPE_SET, str11);
        map.put(AI_GLOBAL_COUNT_TYPE_SET, str12);
        if (TextUtils.equals("copy_publish", str2)) {
            map.put(AIGLOBAL_EMOJI_TYPE_SET, str13);
        }
        map.put("content_text_count", 0);
        map.put(ELEMENT_NAME, AI_INSERT);
        map.put(AI_SCENE, str4);
        map.put(AI_ACTION_WAY, str5);
        map.put(AI_ACTION_LOCATION, str6);
        map.put(AI_SESSION_ID, str8);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put(AI_RESULT_STATUS, str14);
        map.put(TRACE_ID, str16);
        map.put(AI_TRACE_TYPE, str17);
        map.put("content_status", str7);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str9);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        reportAiCanceledEvent(AI_CANCELED, map, context);
        Logger.INSTANCE.d(TAG, "reportGlobalAiCanceled: " + map);
    }

    public static void reportGlobalAiUsed(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, int i2, String str11, String str12, String str13, int i3, int i4, String str14, String str15, String str16, String str17, String str18, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(AI_TEXT_SOURCE, str10);
        map.put(AI_DETAIL, str3);
        map.put(IS_GLOBAL, true);
        map.put(AI_GLOBAL_CONTENT_TYPE_SET, str11);
        map.put(AI_GLOBAL_COUNT_TYPE_SET, str12);
        if (TextUtils.equals("copy_publish", str2)) {
            map.put(AIGLOBAL_EMOJI_TYPE_SET, str13);
        }
        map.put("content_text_count", 0);
        map.put(ELEMENT_NAME, AI_INSERT);
        map.put(AI_SCENE, str4);
        map.put(AI_ACTION_WAY, str5);
        map.put(AI_ACTION_LOCATION, str6);
        map.put(AI_SESSION_ID, str8);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put(AI_RESULT_STATUS, str14);
        map.put(TRACE_ID, str16);
        map.put(AI_TRACE_TYPE, str17);
        map.put("content_status", str7);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str9);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        reportAiUsedEvent(AI_USED, map, context);
        Logger.INSTANCE.d(TAG, "reportGlobalAiUsed: " + map);
    }

    public static void reportGlobalAiStart(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, int i2, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(IS_GLOBAL, true);
        map.put(AI_DETAIL, str3);
        map.put(AI_SCENE, str4);
        map.put(AI_ACTION_WAY, str5);
        map.put(AI_ACTION_LOCATION, str6);
        map.put("content_text_count", Integer.valueOf(i));
        map.put("content_status", str7);
        map.put(AI_SESSION_ID, str8);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str9);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        map.put(AI_TEXT_SOURCE, str10);
        reportAiStartEvent(AI_START, map, context);
        Logger.INSTANCE.d(TAG, "reportGlobalAiStart: " + map);
    }

    public static void reportGlobalAiEnd(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, int i2, String str11, String str12, String str13, int i3, int i4, String str14, String str15, String str16, String str17, String str18, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(IS_GLOBAL, true);
        map.put(AI_DETAIL, str3);
        map.put(AI_SCENE, str4);
        map.put(AI_ACTION_WAY, str5);
        map.put(AI_ACTION_LOCATION, str6);
        map.put("content_text_count", 0);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put("content_status", str7);
        map.put(AI_SESSION_ID, str8);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str9);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        map.put(AI_TEXT_SOURCE, str10);
        map.put(AI_GLOBAL_CONTENT_TYPE_SET, str11);
        map.put(AI_GLOBAL_COUNT_TYPE_SET, str12);
        if (TextUtils.equals("copy_publish", str2)) {
            map.put(AIGLOBAL_EMOJI_TYPE_SET, str13);
        }
        map.put(AI_RESULT_DURATION, Integer.valueOf(i3));
        map.put(AI_MODEL_RESULT_DURATION, Integer.valueOf(i4));
        map.put(AI_RESULT_STATUS, str14);
        map.put(AI_RESULT_REASON, str15);
        map.put(TRACE_ID, str16);
        map.put(AI_TRACE_TYPE, str17);
        map.put(AI_END_WAY, str18);
        reportAiEndEvent(AI_END, map, context);
    }

    public static void reportGlobalAiFeekbackClick(String str, String str2, String str3, String str4, String str5, String str6, int i, String str7, String str8, String str9, String str10, int i2, String str11, String str12, String str13, String str14, String str15, String str16, String str17, Context context) {
        HashMap map = new HashMap();
        map.put("apk", str);
        map.put(PATE_TYPE, str2);
        map.put(AI_TEXT_SOURCE, str10);
        map.put(AI_DETAIL, str3);
        map.put(IS_GLOBAL, true);
        map.put(AI_GLOBAL_CONTENT_TYPE_SET, str11);
        map.put(AI_GLOBAL_COUNT_TYPE_SET, str12);
        if (TextUtils.equals("copy_publish", str2)) {
            map.put(AIGLOBAL_EMOJI_TYPE_SET, str13);
        }
        map.put("content_text_count", Integer.valueOf(i));
        map.put(ELEMENT_NAME, str17);
        map.put(AI_SCENE, str4);
        map.put(AI_ACTION_WAY, str5);
        map.put(AI_ACTION_LOCATION, str6);
        map.put(AI_SESSION_ID, str8);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put(AI_RESULT_STATUS, str14);
        map.put(TRACE_ID, str15);
        map.put(AI_TRACE_TYPE, str16);
        map.put("content_status", str7);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str9);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        Log.d(TAG, "reportGlobalAiFeekbackClick:" + map);
        reportClickEvent(GLOBAL_AI_FEEDBACK_CLICK, map, context);
    }

    public static void reportAiDeepSeekClick(String str, String str2, int i, String str3, String str4, int i2, String str5, String str6, String str7, Context context, String str8) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put("content_status", str2);
        map.put("content_text_count", Integer.valueOf(i));
        map.put("apk", str3);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str4);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        map.put(AI_SHOW_TYPE, str5);
        map.put(IS_RECOMMEND_SHOW, str6);
        map.put("ai_deepseek_status", str7);
        map.put(AI_TRACE_TYPE, str8);
        Log.d(TAG, "type: " + str + ", state: " + str2 + ", count: " + i + ", apkName: " + str3 + ", location: " + str4 + ", contentCount: " + i2 + " showType: " + str5 + ", recommendShow: " + str6 + " aiDeepSeekStatus: " + str7 + ", aiTraceType: " + str8);
        reportClickEvent(AI_DEEPSEEK_CLICK, map, context);
    }

    public static void reportAiEnd(String str, String str2, String str3, String str4, String str5, int i, String str6, int i2, String str7, String str8, String str9, String str10, String str11, int i3, String str12, String str13, String str14, int i4, long j, int i5, long j2, boolean z, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put(AI_SCENE, str2);
        map.put(AI_ACTION_WAY, str3);
        map.put(AI_ACTION_LOCATION, str4);
        map.put(AI_DETAIL, str5);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put(AI_RESULT_STATUS, str6);
        map.put(AI_RESULT_DURATION, Integer.valueOf(i2));
        map.put(AI_RESULT_REASON, str7);
        map.put("apk", str8);
        map.put(TRACE_ID, str9);
        map.put(AI_TRACE_TYPE, str10);
        map.put(AI_DEEP_SEEK_COUNT, Integer.valueOf(i5));
        map.put(AI_DEEP_SEEK_MODEL_DURATION, Long.valueOf(j2));
        map.put("ai_deepseek_status", z ? "true" : "false");
        map.put("content_status", str11);
        map.put("content_text_count", Integer.valueOf(i3));
        map.put(AI_SESSION_ID, str12);
        map.put(AI_END_WAY, str13);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str14);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i4));
        map.put(AI_MODEL_RESULT_DURATION, Long.valueOf(j));
        reportAiEndEvent(AI_END, map, context);
    }

    public static void reportAiPanelOpen(String str, String str2, int i, String str3, String str4, String str5, int i2, String str6, String str7, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put("content_status", str2);
        map.put("content_text_count", Integer.valueOf(i));
        map.put("apk", str3);
        map.put("content_style", str4);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str5);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        map.put(AI_SHOW_TYPE, str6);
        map.put(IS_RECOMMEND_SHOW, str7);
        reportAiOpenEvent(EXPOSE, map, context);
        Logger.INSTANCE.d(TAG, "reportAiPanelOpen: " + map);
    }

    public static void reportAiPanelClose(String str, String str2, int i, String str3, String str4, String str5, int i2, String str6, String str7, String str8, String str9, String str10, long j, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put("content_status", str2);
        map.put("content_text_count", Integer.valueOf(i));
        map.put("apk", str3);
        map.put("content_style", str4);
        map.put(SELECT_CONTENT_TEXT_LOCATION, str5);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i2));
        map.put(IS_RECOMMEND_SHOW, str6);
        map.put(AI_SHOW_TYPE, str7);
        map.put(AI_TEXT_SOURCE, str8);
        map.put(AI_DETAIL, str9);
        map.put(AI_END_WAY, str10);
        map.put(AI_PANEL_DURATION, Long.valueOf(j));
        reportClickEvent(CLICK, map, context);
        Logger.INSTANCE.d(TAG, "reportAiPanelClose: " + map);
    }

    public static void reportAiUsed(String str, String str2, String str3, String str4, String str5, int i, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, int i2, String str14, int i3, int i4, long j, boolean z, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put(AI_SCENE, str2);
        map.put(AI_ACTION_WAY, str3);
        map.put(AI_ACTION_LOCATION, str4);
        map.put(AI_DETAIL, str5);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put(AI_RESULT_STATUS, str6);
        map.put(ELEMENT_NAME, str7);
        map.put("apk", str8);
        map.put(TRACE_ID, str9);
        map.put(AI_TRACE_TYPE, str10);
        map.put(AI_DEEP_SEEK_COUNT, Integer.valueOf(i4));
        map.put(AI_DEEP_SEEK_MODEL_DURATION, Long.valueOf(j));
        map.put("ai_deepseek_status", z ? "true" : "false");
        map.put(AI_TEXT_SOURCE, str11);
        map.put(AI_SESSION_ID, str13);
        map.put("content_status", str12);
        map.put("content_text_count", Integer.valueOf(i2));
        map.put(SELECT_CONTENT_TEXT_LOCATION, str14);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i3));
        reportAiUsedEvent(AI_USED, map, context);
    }

    public static void reportAiCanceled(String str, String str2, String str3, String str4, String str5, int i, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, int i2, String str14, int i3, Context context) {
        HashMap map = new HashMap();
        map.put("content_type", str);
        map.put(AI_SCENE, str2);
        map.put(AI_ACTION_WAY, str3);
        map.put(AI_ACTION_LOCATION, str4);
        map.put(AI_DETAIL, str5);
        map.put(AI_RESULT_COUNT, Integer.valueOf(i));
        map.put(AI_RESULT_STATUS, str6);
        map.put(ELEMENT_NAME, str7);
        map.put("apk", str8);
        map.put(TRACE_ID, str9);
        map.put(AI_TRACE_TYPE, str10);
        map.put(AI_TEXT_SOURCE, str11);
        map.put(AI_SESSION_ID, str13);
        map.put("content_status", str12);
        map.put("content_text_count", Integer.valueOf(i2));
        map.put(SELECT_CONTENT_TEXT_LOCATION, str14);
        map.put(SELECT_CONTENT_TEXT_COUNT, Integer.valueOf(i3));
        reportAiCanceledEvent(AI_CANCELED, map, context);
        Logger.INSTANCE.d(TAG, "reportAiCanceled: " + map);
    }

    public static void reportAiStartEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        AITextStatListener aITextStatListener = mAiTextStatListener;
        if (aITextStatListener != null) {
            aITextStatListener.onAiTextStat(str, AI_START_EVENT, map);
        } else {
            if (RomUtils.isInternationalBuild() || context == null || !PreferenceUtils.isCTAAccepted(context)) {
                return;
            }
            doInitialize(context);
            track(str, AI_START_EVENT, map, context);
        }
    }

    public static void reportAiOpenEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        AITextStatListener aITextStatListener = mAiTextStatListener;
        if (aITextStatListener != null) {
            aITextStatListener.onAiTextStat(str, EVENT_EXPOSE, map);
        } else {
            if (RomUtils.isInternationalBuild() || context == null || !PreferenceUtils.isCTAAccepted(context)) {
                return;
            }
            doInitialize(context);
            track(str, EVENT_EXPOSE, map, context);
        }
    }

    public static void reportAiLocalSettingClick(String str, Context context) {
        HashMap map = new HashMap();
        map.put(AI_DEEP_SEEK_LOCAL_STATUS, str);
        reportSettingEvent(AI_LOCAL_SETTING_CLICK, map, context);
    }

    public static void reportAiEndEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        AITextStatListener aITextStatListener = mAiTextStatListener;
        if (aITextStatListener != null) {
            aITextStatListener.onAiTextStat(str, AI_END_EVENT, map);
        } else {
            if (context == null || !PreferenceUtils.isCTAAccepted(context)) {
                return;
            }
            doInitialize(context);
            track(str, AI_END_EVENT, map, context);
        }
    }

    public static void reportAiUsedEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        AITextStatListener aITextStatListener = mAiTextStatListener;
        if (aITextStatListener != null) {
            aITextStatListener.onAiTextStat(str, "ai_used", map);
        } else {
            if (context == null || !PreferenceUtils.isCTAAccepted(context)) {
                return;
            }
            doInitialize(context);
            track(str, "ai_used", map, context);
        }
    }

    public static void reportAiCanceledEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        AITextStatListener aITextStatListener = mAiTextStatListener;
        if (aITextStatListener != null) {
            aITextStatListener.onAiTextStat(str, AI_CANCELED_EVENT, map);
        } else {
            if (context == null || !PreferenceUtils.isCTAAccepted(context)) {
                return;
            }
            doInitialize(context);
            track(str, AI_CANCELED_EVENT, map, context);
        }
    }

    public static void reportClickEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        AITextStatListener aITextStatListener = mAiTextStatListener;
        if (aITextStatListener != null) {
            aITextStatListener.onAiTextStat(str, "click", map);
        } else {
            if (context == null || !PreferenceUtils.isCTAAccepted(context)) {
                return;
            }
            doInitialize(context);
            track(str, "click", map, context);
        }
    }

    public static void reportSettingEvent(String str, Map<String, Object> map, Context context) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (context == null || !PreferenceUtils.isCTAAccepted(context)) {
            return;
        }
        doInitialize(context);
        track(str, "setting", map, context);
    }

    public AITextWidgetStat() {
    }

    private AITextWidgetStat(Context context) {
        try {
            getStatImpInstance().init(context);
            this.mUUID = UUID.randomUUID().toString();
            this.mModelType = RomUtils.getModelType();
            this.mDeviceLevel = RomUtils.getDeviceLevel();
        } catch (Exception unused) {
            Logger.INSTANCE.e(TAG, "OneTrack init error $e");
        }
        Logger.INSTANCE.i(TAG, "OneTrack has been initialized");
    }

    private static void doInitialize(Context context) {
        if (mStatInstance == null) {
            mStatInstance = new AITextWidgetStat(context);
        }
    }

    private void setCommonParams(Map<String, Object> map) {
        if (map != null) {
            map.put(TRACK_ID, this.mUUID);
            map.put(MODEL_TYPE, this.mModelType);
            map.put(DEVICE_LEVEL, this.mDeviceLevel);
        }
    }

    public static IOneTrackStat getStatImpInstance() {
        if (mStatImplInstance == null) {
            mStatImplInstance = new OneTrackStatImp();
        }
        return mStatImplInstance;
    }

    public static void track(String str, String str2, Map<String, Object> map, Context context) {
        AITextWidgetStat aITextWidgetStat;
        if (context == null || !PreferenceUtils.isCTAAccepted(context) || TextUtils.isEmpty(str) || (aITextWidgetStat = mStatInstance) == null || map == null) {
            return;
        }
        aITextWidgetStat.setCommonParams(map);
        getStatImpInstance().track(str, str2, map);
    }
}
