package com.xiaomi.micloudsdk.stat;

import android.content.SyncResult;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.SyncLocalException;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudErrorStatReason {
    public static final String ACTIVATED_FAIL = "activated_fail";
    public static final String AUTH_TOKEN_ERROR = "auth_token_error";
    public static final String BAD_REQUEST = "sever_error_bad_request";
    public static final String CLOUD_SPACE_FULL = "cloud_space_full";
    public static final String CTA_ERROR = "cta_error";
    public static final String FORBIDDEN = "sever_error_forbidden";
    public static final String GDPR_DENY = "gdpr_deny";
    public static final String MASTERKEY_EXPIRED = "masterkey_expired";
    public static final String MASTERKEY_NOT_EXIST = "masterkey_not_exist";
    public static final String NOT_ACCEPTABLE = "sever_error_not-acceptable";
    public static final String PAUSE_LIMIT = "pause_limit";
    public static final String PERMISSION_LIMIT = "permission_limit";
    public static final String SECURE_SPACE_LIMIT = "secure_space_limit";
    public static final String SERVER_5XX_ERROR = "server_5xx_error";
    public static final String SWITCH_OFF = "switch_off";
    public static final String SYNC_HARD_ERROR = "sync_hard_error";
    public static final String SYNC_SOFT_ERROR = "sync_soft_error";
    public static final String SYNC_UNKNOWN_ERROR = "sync_unknown_error";
    public static final String UNAUTHORIZED = "sever_error_unauthorized";
    public static final String WLAN_ONLY_ERROR = "wlan_only_error";

    public static String getServerExceptionReasonForStats(CloudServerException cloudServerException) {
        int i = cloudServerException.statusCode;
        if (i == -10001) {
            return getLocalExceptionReasonForStatsByCode(cloudServerException.code);
        }
        if (i == 403) {
            return FORBIDDEN;
        }
        if (i == 406) {
            return NOT_ACCEPTABLE;
        }
        if (i == 400) {
            return BAD_REQUEST;
        }
        if (i == 401) {
            return UNAUTHORIZED;
        }
        if (cloudServerException.code == 52003) {
            return GDPR_DENY;
        }
        if (cloudServerException.is5xxServerException()) {
            return SERVER_5XX_ERROR;
        }
        return SYNC_UNKNOWN_ERROR;
    }

    private static String getLocalExceptionReasonForStatsByCode(int i) {
        if (i == 3000) {
            return MASTERKEY_EXPIRED;
        }
        if (i != 3001) {
            switch (i) {
                case 1000:
                    return CTA_ERROR;
                case 1001:
                    return ACTIVATED_FAIL;
                case 1002:
                    return "permission_limit";
                case 1003:
                    return "secure_space_limit";
                default:
                    switch (i) {
                        case 2000:
                            return GDPR_DENY;
                        case 2001:
                            return CLOUD_SPACE_FULL;
                        case 2002:
                            return WLAN_ONLY_ERROR;
                        default:
                            return SYNC_UNKNOWN_ERROR;
                    }
            }
        }
        return MASTERKEY_NOT_EXIST;
    }

    public static String getLocalExceptionReasonForStats(SyncLocalException syncLocalException) {
        return getLocalExceptionReasonForStatsByCode(syncLocalException.getErrorCode());
    }

    public static String getSyncResultErrorReasonForStats(SyncResult syncResult) {
        return syncResult.hasHardError() ? SYNC_HARD_ERROR : SYNC_SOFT_ERROR;
    }
}
