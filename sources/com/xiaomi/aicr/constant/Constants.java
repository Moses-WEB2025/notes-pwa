package com.xiaomi.aicr.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class Constants {
    public static final String AICR_ENGINE_SERVICE_CHANNEL = "aicr_service_notification";
    public static final List<Integer> AIRECO_CAPABILITY_LIST;
    public static final String APP_SUGGEST_PLUGIN = "com.xiaomi.aicr.cognition.IAppSuggestInterface";
    public static final String BLUETOOTH_DEVICE = "bluetooth_device";
    public static final int CAP_DOWNLOAD = 100001;
    public static final String COGNITION_AUDIO_PLUGIN = "com.xiaomi.aicr.cognition.ICognitionAudioInterface";
    public static final String COGNITION_COMMON_PLUGIN = "com.xiaomi.aicr.cognition.ICognitionEngineInterface";
    public static final String COGNITION_ENGINE_STATUS = "cognition_engine_status";
    public static final String COGNITION_ENGINE_STATUS_CHANGE = "com.xiaomi.cognition.COGNITION_ENGINE_STATUS_CHANGE";
    public static final String COGNITION_PRIVACY_GRANT = "cognition_privacy_grant";
    public static final String COGNITION_SETTING_OPEN = "cognition_setting_open";
    public static final String COGNITION_SHARE_PREFERENCE_FILE = "cognition_setting";
    public static final String COGNITION_XIAOAI_PLUGIN = "com.xiaomi.aicr.cognition.ISceneRecognitionInterface";
    public static final int CRS_CAP_COMMUTING_TIME_COGNITION = 3003;
    public static final int CRS_CAP_FREQUENT_LOCATION = 3001;
    public static final int CRS_CAP_HOT_POI = 3007;
    public static final int CRS_CAP_INTENTION = 3100;
    public static final int CRS_CAP_INTENTION_NOTIFICATION = 3101;
    public static final int CRS_CAP_INTERNET = 10001;
    public static final int CRS_CAP_IOT_BLUETOOTH = 1203;
    public static final int CRS_CAP_IOT_COMMON = 1202;
    public static final int CRS_CAP_IOT_LOCATION = 1201;
    public static final int CRS_CAP_METRO = 3004;
    public static final int CRS_CAP_SCAN_QRCODE = 3006;
    public static final int CRS_CAP_SLEEP_PERIOD_COGNITION = 3002;
    public static final int CRS_CAP_TRAVEL_CAR = 3005;
    public static final int CRS_DEVICE_SLEEP_MONITOR = 2003;
    public static final int CRS_DOMAIN_DEVICE = 3;
    public static final int CRS_DOMAIN_INTELLIGENT_EVENT = 2;
    public static final int CRS_DOMAIN_INVALID = 0;
    public static final int CRS_DOMAIN_LOCATION = 1;
    public static final int CRS_DOMAIN_USER = 4;
    public static final int CRS_INTELLIGENT_ACD = 2005;
    public static final int CRS_INTELLIGENT_APP_SUGGEST = 4001;
    public static final int CRS_INTELLIGENT_GOHOME_MODE = 1012;
    public static final int CRS_INTELLIGENT_HYPER_MIND = 1010;
    public static final int CRS_INTELLIGENT_IOT_PROVIDER = 1200;
    public static final int CRS_INTELLIGENT_NFC_CARD = 2001;
    public static final int CRS_INTELLIGENT_NLS = 5001;
    public static final int CRS_INTELLIGENT_SLEEP_MODE = 1011;
    public static final int CRS_INTELLIGENT_WIDGET_SUGGEST = 2004;
    public static final int CRS_QR_GESTURE = 8008;
    public static final int EVENT_ALGO_CHECK_PERMISSION = 105;
    public static final int EVENT_ALGO_START = 1;
    public static final int EVENT_ALGO_START_LISTEN = 100;
    public static final int EVENT_ALGO_STOP = 2;
    public static final int EVENT_ALGO_STOP_LISTEN = 101;
    public static final int EVENT_APP_LIST_CHANGE = 4001;
    public static final int EVENT_BLUETOOTH_CHANGE = 3004;
    public static final int EVENT_CELL_LOCATION_CHANGE = 1001;
    public static final int EVENT_DELETE_GEOFENCE = 1006;
    public static final int EVENT_FOREGROUND_APP_CHANGE = 4004;
    public static final int EVENT_GEOFENCE_CHANGE = 1003;
    public static final int EVENT_GET_APP_LIST = 4006;
    public static final int EVENT_GET_BLUETOOTH = 3005;
    public static final int EVENT_GET_CELL = 3002;
    public static final int EVENT_GET_GEOFENCE = 1005;
    public static final int EVENT_GET_PERMISSION_STATE = 3005;
    public static final int EVENT_GET_POI_ID = 3003;
    public static final int EVENT_GET_POLARIS_GEOFENCE_SUPPORT = 1013;
    public static final int EVENT_GET_TASK_LIST = 1007;
    public static final int EVENT_GET_WIDGET_LIST = 4007;
    public static final int EVENT_LISTEN_FOREGROUND_APP_CHANGE = 4003;
    public static final int EVENT_LISTEN_WIDGET_LIST_CHANGE = 4005;
    public static final int EVENT_NFC_CARD_DELETE = 2003;
    public static final int EVENT_NFC_CARD_LEARN = 2001;
    public static final int EVENT_NFC_CARD_PREDICT = 2002;
    public static final int EVENT_PERMISSION_STATE_CHANGED = 3006;
    public static final int EVENT_PLUGIN_CAN_STOP = 106;
    public static final int EVENT_PLUGIN_GET_STATUS = 103;
    public static final int EVENT_PLUGIN_STATUS_CHANGED = 102;
    public static final int EVENT_SET_EXECUTE_TASK = 1011;
    public static final int EVENT_SET_FORGET_TASK = 1010;
    public static final int EVENT_SET_GEOFENCE = 1004;
    public static final int EVENT_SET_NECESSARY_PERMISSIONS = 1008;
    public static final int EVENT_SET_SCENE_DATA = 104;
    public static final int EVENT_SET_TASK_STATE = 1012;
    public static final int EVENT_SET_UNNECESSARY_PERMISSIONS = 1009;
    public static final int EVENT_START_GET_DEVICE_STATUS = 2004;
    public static final int EVENT_STOP_GET_DEVICE_STATUS = 2005;
    public static final int EVENT_USER_HABITS_CHANGE = 4008;
    public static final int EVENT_WIDGET_LIST_CHANGE = 4002;
    public static final String KEV_PROGRESS = "Progress";
    public static final String KEV_STATUS = "Status";
    public static final String KEY_ACTION = "Action";
    public static final String KEY_BIND_PACKAGE = "bind_package";
    public static final String KEY_BIND_TYPE = "bind_type";
    public static final String KEY_CAPABILITY = "Capability";
    public static final String KEY_CARD_ID = "card_id";
    public static final String KEY_DATA_SIZE = "data_size";
    public static final String KEY_DOMAIN = "Domain";
    public static final String KEY_EVENT = "Event";
    public static final String KEY_GEOFENCE = "key_geofence";
    public static final String KEY_LATITUDE = "key_latitude";
    public static final String KEY_LONGITUDE = "key_longitude";
    public static final String KEY_MODEL_ABLE = "modelAble";
    public static final String KEY_PARAMS = "SceneParams";
    public static final String KEY_PERMISSION_LIST = "key_permission_list";
    public static final String KEY_PERMISSION_STATE = "key_permission_state";
    public static final String KEY_SCENE = "scene";
    public static final String KEY_SCENE_STATUS = "Status";
    public static final String KEY_SUPPORT = "support";
    public static final String KEY_TARGET_ID = "target_id";
    public static final int LLM_CAPABILITY = 9000;
    public static final int LLM_TASK_EDITTEXT_CONTROL = 9002;
    public static final int LLM_TASK_MESSAGE_SUMMARIZE = 9001;
    public static final String NFC_INPUT_PLACEHOLDER = "NaN";
    public static final int PLUGIN_COG_APP_SUGGEST = 4000;
    public static final int PLUGIN_COG_AUDIO = 2000;
    public static final int PLUGIN_COG_COMMON = 1000;
    public static final int PLUGIN_COG_XIAOAI = 3000;
    public static final HashMap<Integer, String[]> PLUGIN_MAP = new HashMap<Integer, String[]>() { // from class: com.xiaomi.aicr.constant.Constants.1
        {
            put(2000, new String[]{Constants.COGNITION_AUDIO_PLUGIN, "V1", "V1"});
            put(2001, new String[]{Constants.COGNITION_AUDIO_PLUGIN, "V1", "V1"});
            put(2003, new String[]{Constants.COGNITION_AUDIO_PLUGIN, "V1", "V1"});
            put(2004, new String[]{Constants.COGNITION_AUDIO_PLUGIN, "V1", "V1"});
            put(2005, new String[]{Constants.COGNITION_AUDIO_PLUGIN, "V1", "V1"});
            put(1010, new String[]{Constants.COGNITION_COMMON_PLUGIN, "V1", "V1"});
            put(1200, new String[]{Constants.COGNITION_COMMON_PLUGIN, "V1", "V1"});
            put(5001, new String[]{Constants.COGNITION_COMMON_PLUGIN, "V1", "V1"});
            put(4000, new String[]{Constants.APP_SUGGEST_PLUGIN, "V1", "V1"});
            put(4001, new String[]{Constants.APP_SUGGEST_PLUGIN, "V1", "V1"});
            put(3000, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3001, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3002, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3003, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3100, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3101, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3004, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3005, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3006, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(3007, new String[]{Constants.COGNITION_XIAOAI_PLUGIN, "V1", "V1"});
            put(1201, new String[]{Constants.COGNITION_COMMON_PLUGIN, "V1", "V1"});
            put(1202, new String[]{Constants.COGNITION_COMMON_PLUGIN, "V1", "V1"});
            put(1203, new String[]{Constants.COGNITION_COMMON_PLUGIN, "V1", "V1"});
        }
    };
    public static final int PRIVACY_AGREED = 0;
    public static final int PRIVACY_INVALID_CODE = -3;
    public static final int PRIVACY_NEED_UPDATE = -2;
    public static final int PRIVACY_NOT_AGREED = -1;
    public static final String SCENE_CHANGE_ACTION = "com.xiaomi.cognition.SCENE_CHANGE";
    public static final int SCE_DEVICE_BLUETOOTH_SCAN = 1004;
    public static final int SCE_DEVICE_PERMISSION = 1008;
    public static final int SCE_DEVICE_WIFI_SCAN = 1003;
    public static final int SCE_LOCATION_CELL_LOCATION = 1001;
    public static final int SCE_LOCATION_GEOFENCE = 1002;
    public static final int SCE_USER_CLICK_APP = 1005;
    public static final int SCE_USER_CLICK_WIDGET = 1006;
    public static final int SCE_USER_SWITCH_APP = 1007;
    public static final String STR_NAME_NULL = "NULL";

    public enum STATUS_DEFINE {
        OK,
        DOWNLOADING_FIRST_USE,
        NEED_UPGRADE,
        SERVICE_NOT_CONNECTED,
        SERVICE_ERROR,
        NOT_FOUND,
        SET_IMAGE_FAIL,
        UNSUPPORTED_CAPABILITY,
        PERMISSION_DENIED
    }

    public static String getStatusInfo(int i) {
        if (i > STATUS_DEFINE.values().length || i < 0) {
            return "";
        }
        return STATUS_DEFINE.values()[i].name();
    }

    public static STATUS_DEFINE getStatusfromInt(int i) {
        if (i > STATUS_DEFINE.values().length || i < 0) {
            return STATUS_DEFINE.NOT_FOUND;
        }
        return STATUS_DEFINE.values()[i];
    }

    static {
        ArrayList arrayList = new ArrayList();
        AIRECO_CAPABILITY_LIST = arrayList;
        arrayList.add(3001);
        arrayList.add(3002);
        arrayList.add(3003);
        arrayList.add(3004);
        arrayList.add(3005);
        arrayList.add(3006);
        arrayList.add(3007);
        arrayList.add(3100);
        arrayList.add(3101);
    }
}
