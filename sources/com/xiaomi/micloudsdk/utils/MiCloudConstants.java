package com.xiaomi.micloudsdk.utils;

import android.net.Uri;
import com.xiaomi.micloudsdk.sync.MiCloudStatConstantsV2;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudConstants {
    public static final int HTTP_URL_LENGTH_LIMIT = 1024;
    public static final String MICLOUD_TAG = "Micloud";
    public static final int SYNC_WITHOUT_ACTIVATE_SIM_INDEX = -1;
    public static final boolean USE_PREVIEW = new File("/data/system/xiaomi_account_preview").exists();
    public static final boolean USE_MEMBER_DAILY = new File("/data/system/micloud_member_daily").exists();

    private MiCloudConstants() {
    }

    public static class ANALYTICS {
        public static final boolean ENABLE_ANALYTICS = true;
        public static final String EVENT_ID_MICLOUD_ACTIVATE_SOURCE = "event_id_micloud_activate_source";
        public static final String EVENT_ID_MICLOUD_LOGIN_ENABLE = "event_id_micloud_login_enable";
        public static final String EVENT_KEY_MICLOUD_ACTIVATE_AUTHORITY = "event_key_micloud_activate_authority";
        public static final String EVENT_KEY_MICLOUD_ACTIVATE_INIT = "event_key_micloud_activate_init";
        public static final String EVENT_KEY_MICLOUD_ACTIVATE_PACKAGE_NAME = "event_key_micloud_activate_package_name";
        public static final String EVENT_KEY_MICLOUD_ACTIVATE_RESULT = "event_key_micloud_activate_result";
        public static final String EVENT_KEY_MICLOUD_ACTIVATE_SOURCE = "event_key_micloud_activate_source";
        public static final String EVENT_KEY_MICLOUD_FIND_DEVICE_ENABLE = "event_key_micloud_find_device_enable";
        public static final String EVENT_KEY_MICLOUD_MX_ENABLE = "event_key_micloud_mx_enable";
        public static final String EVENT_KEY_MICLOUD_SETUP_GUIDE = "event_key_micloud_setup_guide";
        public static final String EVENT_KEY_MICLOUD_SYNC_ENABLE = "event_key_micloud_sync_enable";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_ACCOUNT = "event_value_micloud_activate_source_account";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_ALERT = "event_value_micloud_activate_source_alert";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_ALERT_APP = "event_value_micloud_activate_source_alert_app";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_CALENDAR = "event_value_micloud_activate_source_calendar";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_CHECKBOX = "event_value_micloud_activate_source_checkbox";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_GALLERY = "event_value_micloud_activate_source_gallery";
        public static final String EVENT_VALUE_MICLOUD_ACTIVATE_SOURCE_NOTES = "event_value_micloud_activate_source_notes";

        private ANALYTICS() {
        }
    }

    public static class RICHMEDIA {
        public static final String SUPPORT_TYPE_ICO = "ico";
        public static final String SUPPORT_TYPE_MIXIN = "mixin";
        public static final String SUPPORT_TYPE_MIXIN2 = "mixin2";
        public static final String SUPPORT_TYPE_MMS = "mms";
        public static final String SUPPORT_TYPE_NOTE_ENT = "note_ent";
        public static final String SUPPORT_TYPE_NOTE_IMAGE = "note_img";
        public static final String SUPPORT_TYPE_NOTE_REC = "note_rec";
        public static final String SUPPORT_TYPE_REC = "rec";

        private RICHMEDIA() {
        }
    }

    public static class PDC {
        public static final String ASSET_GET_ASSET_URL_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/assets/%s/playurl";
        public static final String ASSET_REQUEST_COMMIT_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/assets/commit";
        public static final String ASSET_REQUEST_DOWNLOAD_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/assets/%s";
        public static final String ASSET_REQUEST_UPLOAD_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/assets/upload";
        public static final String BATCH_OPERATION_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/batch";
        public static final String CONFLICT_ETAG = "eTag";
        public static final String CONFLICT_ID = "id";
        public static final String CONFLICT_NONE = "none";
        public static final String CONFLICT_UNIQUE_KEY = "uniqueKey";
        public static final String CREATE_RECORD_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records";
        public static final String DELETE_RECORD_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/%s/delete";
        public static final int ERROR_CODE_ARGUMENT = 10008;
        public static final int ERROR_CODE_ASSET_NOT_EXIST = 40006;
        public static final int ERROR_CODE_BATCH_TOO_LARGE = 50017;
        public static final int ERROR_CODE_CHILD_EXIST = 40001;
        public static final int ERROR_CODE_ILLEGAL_RECORD_TYPE = 40002;
        public static final int ERROR_CODE_INVALID_BATCH_URL = 50003;
        public static final int ERROR_CODE_NODE_NOT_EXIST = 40003;
        public static final int ERROR_CODE_NODE_STATUS_INVALID = 40004;
        public static final int ERROR_CODE_OK = 0;
        public static final int ERROR_CODE_PARENT_NOT_EXIST = 40000;
        public static final int ERROR_CODE_SCHEMA_NOT_EXIST = 40005;
        public static final int ERROR_CODE_SHOULD_RESYNC = 52000;
        public static final int ERROR_CODE_SYSTEM_BATCH = 50002;
        public static final int ERROR_CODE_SYSTEM_BUSY = 10009;
        public static final int ERROR_CODE_SYSTEM_GENERIC = 10001;
        public static final int ERROR_CODE_SYSTEM_RPC = 10011;
        public static final int ERROR_CODE_SYSTEM_STORAGE = 53000;
        public static final String FETCH_CHILDREN_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/%s/children";
        public static final String FETCH_RECORDS_BY_INDEX_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/index";
        public static final String FETCH_RECORD_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/%s";
        public static final String J_APPKEY_VERSION = "appKeyVersion";
        public static final String J_ASSET_LIST = "assetList";
        public static final String J_BEGIN_KEY = "beginKey";
        public static final String J_CODE = "code";
        public static final String J_CONFLICT_RECORD = "conflictRecord";
        public static final String J_CONFLICT_TYPE = "conflictType";
        public static final String J_CONTENT_JSON = "contentJson";
        public static final String J_CURSOR = "cursor";
        public static final String J_DATA = "data";
        public static final String J_DESCRIPTION = "description";
        public static final String J_ENCRYPT_FIELDS = "encryptFields";
        public static final String J_ENCRYPT_INFO = "encryptInfo";
        public static final String J_ETAG = "eTag";
        public static final String J_HAS_MORE = "hasMore";
        public static final String J_ID = "id";
        public static final String J_LIST = "list";
        public static final String J_PARAMS = "params";
        public static final String J_PARENT_ID = "parentId";
        public static final String J_PATH = "path";
        public static final String J_REASON = "reason";
        public static final String J_RECORD = "record";
        public static final String J_RECORDS = "records";
        public static final String J_RECORD_IV = "recordIV";
        public static final String J_RESULT = "result";
        public static final String J_RETRIABLE = "retriable";
        public static final String J_STATUS = "status";
        public static final String J_SYNC_TOKEN = "syncToken";
        public static final String J_TYPE = "type";
        public static final String J_UNIQUE_KEY = "uniqueKey";
        public static final String J_WATERMARK = "watermark";
        public static final int MAX_RECORDS_FETCH_SIZE = 200;
        public static final String PARAM_BATCH_CONTENT = "batchContent";
        public static final String PARAM_BEGIN_KEY = "beginKey";
        public static final String PARAM_CURSOR = "cursor";
        public static final String PARAM_END_KEY = "endKey";
        public static final String PARAM_ETAG = "eTag";
        public static final String PARAM_ID = "id";
        public static final String PARAM_INDEX_NAME = "indexName";
        public static final String PARAM_IS_REVERSE = "isReverse";
        public static final String PARAM_LIMIT = "limit";
        public static final String PARAM_RECORD = "record";
        public static final String PARAM_SYNC_TOKEN = "syncToken";
        public static final String PARAM_TYPE = "type";
        public static final String STATUS_DELETED = "deleted";
        public static final String STATUS_NORMAL = "normal";
        public static final String SYNC_PATH_BASE = "/mic/pdc/v4.1/apps/%s/spaces/%s/records";
        public static final String UPDATE_RECORD_PATH = "/mic/pdc/v4.1/apps/%s/spaces/%s/records/%s/update";
        public static final String URL_SYNC_BASE;

        private PDC() {
        }

        static {
            URL_SYNC_BASE = MiCloudConstants.USE_PREVIEW ? "http://pdcapi.micloud.preview.n.xiaomi.net" : "https://pdc.micloud.xiaomi.net";
        }
    }

    public static class REQUEST {
        public static final int CODE_IS_INTERNATIONAL_ACCOUNT = 1;
        public static final int CODE_NOT_INTERNATIONAL_ACCOUNT = 0;
        public static final int CODE_UNKOWN_ERROR = 2;
        public static final String X_SUPPORT_HTTPS = "X-SUPPORT-HTTPS";
        public static final String X_XIAOMI_REDIRECT_COUNT = "X-XIAOMI-REDIRECT-COUNT";
        public static final String X_XIAOMI_SUPPORT_REDIRECT = "X-XIAOMI-SUPPORT-REDIRECT";

        private REQUEST() {
        }
    }

    public static class SYNC {
        public static final String SYNC_EXTRAS_FORCE = get_SYNC_EXTRAS_MICLOUD_FORCE();
        public static final String SYNC_EXTRAS_IGNORE_BATTERY_LOW = "micloud_ignore_battery_low";
        public static final String SYNC_EXTRAS_IGNORE_TEMPERATURE = "micloud_ignore_temperature";
        public static final String SYNC_EXTRAS_IGNORE_WIFI_SETTINGS = "micloud_ignore_wifi_settings";
        public static final String SYNC_EXTRAS_XIAOMI_REQUEST_TYPE = "xiaomi_request";
        public static final String XIAOMI_REQUEST_TYPE_INTELLIGENT = "intelligent";
        public static final String XIAOMI_REQUEST_TYPE_PUSH = "push";

        private SYNC() {
        }

        private static String get_SYNC_EXTRAS_MICLOUD_FORCE() {
            try {
                return (String) Class.forName("android.content.MiSyncPolicyResolver").getField("SYNC_EXTRAS_MICLOUD_FORCE").get(null);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
                return MiCloudStatConstantsV2.SYNC_EXTRAS_FORCE;
            }
        }
    }

    public static class SYNC_LOG {
        public static final String AUTHORITY = "com.miui.cloudservice.sync.SyncLogProvider";
        public static final String KEY_AUTHORITY = "sync_log_key_authority";
        public static final String KEY_REMOTE_LOGGER = "sync_log_key_remote_logger";
        public static final String METHOD_OPEN_SYNC_LOG = "OPEN_SYNC_LOG";
        public static final Uri URI_PROVIDER = Uri.parse("content://com.miui.cloudservice.sync.SyncLogProvider");

        private SYNC_LOG() {
        }
    }

    public static class URL {
        private static final String CURRENT_VERSION;
        public static final String URL_GALLERY_BASE;
        public static final String URL_MICLOUD_FAMILY_INFO;
        public static final String URL_MICLOUD_MEMBER_STATUS_QUERY;
        private static final String URL_MICLOUD_STATUS_BASE;
        public static final String URL_MICLOUD_STATUS_QUERY;
        public static final String URL_RELOCATION_BASE;
        public static final String URL_RICH_MEDIA_BASE;
        private static final String VERSION_PATH = "/mic/status/v2";

        private URL() {
        }

        static {
            String str = MiCloudConstants.USE_PREVIEW ? "http://statusapi.micloud.preview.n.xiaomi.net" : "https://statusapi.micloud.xiaomi.net";
            URL_MICLOUD_STATUS_BASE = str;
            String str2 = str + VERSION_PATH;
            CURRENT_VERSION = str2;
            URL_MICLOUD_STATUS_QUERY = str2 + "/user/overview";
            URL_MICLOUD_MEMBER_STATUS_QUERY = str2 + "/user/level";
            URL_MICLOUD_FAMILY_INFO = str2 + "/user/family/quota/used";
            URL_GALLERY_BASE = MiCloudConstants.USE_PREVIEW ? "http://galleryapi.micloud.preview.n.xiaomi.net" : "https://galleryapi.micloud.xiaomi.net";
            URL_RICH_MEDIA_BASE = MiCloudConstants.USE_PREVIEW ? "http://fileapi.micloud.preview.n.xiaomi.net" : "https://fileapi.micloud.xiaomi.net";
            URL_RELOCATION_BASE = MiCloudConstants.USE_PREVIEW ? "http://relocationapi.micloud.preview.n.xiaomi.net" : "https://relocationapi.micloud.xiaomi.net";
        }
    }
}
