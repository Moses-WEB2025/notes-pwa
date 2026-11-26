package com.xiaomi.micloudsdk.sync;

import android.net.Uri;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudStatConstantsV2 {
    public static final String DEFAULT_ERROR = "default_error";
    public static final String NO_ERROR = "no_error";
    public static final String PATH_OPEN_SYNC_PHONE_STATE = "open_sync_phone_state";
    public static final String PATH_OPEN_SYNC_RESULT = "open_sync_result";
    public static final String PATH_OPEN_SYNC_TIME_CONSUME = "open_sync_time_consume";
    public static final String STAT_KEY_SYNC_AUTHORITY = "stat_key_sync_authority";
    public static final String STAT_KEY_SYNC_IS_SUCCESSFUL = "stat_key_sync_successful";
    public static final String STAT_KEY_SYNC_REASON = "stat_key_sync_reason";
    public static final String STAT_KEY_SYNC_RETRY = "stat_key_sync_retry";
    public static final String STAT_KEY_SYNC_START = "stat_key_sync_start";
    public static final String STAT_KEY_SYNC_TIME_CONSUME = "stat_key_sync_time_consume";
    public static final String STAT_KEY_SYNC_TIME_END_IN_MILLIS = "stat_key_sync_time_end_in_millis";
    public static final String STAT_KEY_SYNC_TIME_START_IN_MILLIS = "stat_key_sync_time_start_in_millis";
    public static final String STAT_KEY_UNSYNCED_COUNT_AFTER_SYNC = "stat_key_unsynced_count_after_sync";
    public static final String STAT_KEY_UNSYNCED_COUNT_BEFORE_SYNC = "stat_key_unsynced_count_before_sync";
    public static final String STAT_PROVIDER_AUTHORITY = "com.miui.cloudservice.StatProvider";
    public static final String SYNC_EXTRAS_FORCE = "micloud_force";
    public static final String SYNC_EXTRAS_IGNORE_BATTERY_LOW = "micloud_ignore_battery_low";
    public static final String SYNC_EXTRAS_IGNORE_TEMPERATURE = "micloud_ignore_temperature";
    public static final String SYNC_EXTRAS_IGNORE_WIFI_SETTINGS = "micloud_ignore_wifi_settings";
    public static final Uri OPEN_SYNC_RESULT_URI = Uri.parse("content://com.miui.cloudservice.StatProvider/open_sync_result");
    public static final Uri OPEN_SYNC_PHONE_STATE = Uri.parse("content://com.miui.cloudservice.StatProvider/open_sync_phone_state");
    public static final Uri OPEN_SYNC_TIME_CONSUME = Uri.parse("content://com.miui.cloudservice.StatProvider/open_sync_time_consume");
}
