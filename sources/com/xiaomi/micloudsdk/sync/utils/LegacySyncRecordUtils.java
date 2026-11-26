package com.xiaomi.micloudsdk.sync.utils;

import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.util.Log;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.SyncLocalException;

/* JADX INFO: loaded from: classes4.dex */
public class LegacySyncRecordUtils {
    private static final int CODE_ACTIVATED_FAIL = 1001;
    private static final int CODE_AUTH_TOKEN_ERROR = 100;
    private static final int CODE_NETWORK_DISALLOWED = 1000;
    private static final int CODE_PERMISSION_LIMIT = 1002;
    private static final int CODE_SECURE_SPACE_LIMIT = 1003;
    private static final int CODE_SYNC_HARD_ERROR = 2;
    private static final int CODE_SYNC_SOFT_ERROR = 1;
    private static final int CODE_SYNC_SUCCESS = 0;
    private static final int CODE_SYNC_UNKNOWN = -1;
    private static final String PARAM_SYNC_RESULT_AUTHORITY = "authority";
    private static final String PARAM_SYNC_RESULT_CODE = "code";
    private static final Uri SYNC_RESULT_URI = Uri.parse("content://com.miui.micloud/sync_result");
    private static final String TAG = "SyncUtils";

    public static int getAuthTokenErrorLegacyCode() {
        return 100;
    }

    public static int getPauseLimitErrorLegacyCode() {
        return -1;
    }

    public static int getSyncSwitchOffErrorLegacyCode() {
        return -1;
    }

    public static void recordSyncResultSuccess(Context context, String str) {
        Log.d("SyncUtils", "recordSyncResultSuccess");
        recordSyncResult(context, str, 0);
    }

    public static void recordSyncResult(Context context, String str, int i) {
        SyncContentUtils.recordSyncResult(context, str, i);
    }

    public static int getErrorCodeByServerException(CloudServerException cloudServerException) {
        return cloudServerException.statusCode != -10001 ? cloudServerException.statusCode : cloudServerException.code;
    }

    public static int getErrorCodeByLocalException(SyncLocalException syncLocalException) {
        switch (syncLocalException.getErrorCode()) {
            case 1000:
                return 1000;
            case 1001:
                return 1001;
            case 1002:
                return 1002;
            case 1003:
                return 1003;
            default:
                return -1;
        }
    }

    public static int getErrorCodeBySyncResult(SyncResult syncResult) {
        return syncResult.hasHardError() ? 2 : 1;
    }
}
