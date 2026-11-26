package com.xiaomi.micloudsdk.exception;

/* JADX INFO: loaded from: classes4.dex */
public class SyncLocalException extends Exception {
    public static final int CODE_ACTIVATED_FAIL = 1001;
    public static final int CODE_ANDROID_SYNC_RESULT_HARD_ERROR = 2;
    public static final int CODE_ANDROID_SYNC_RESULT_SOFT_ERROR = 1;
    public static final int CODE_AUTH_TOKEN_ERROR = 100;
    public static final int CODE_CLOUD_SPACE_FULL = 2001;
    public static final int CODE_CTA_ERROR = 1000;
    public static final int CODE_GDPR_DENY = 2000;
    public static final int CODE_MASTERKEY_EXPIRED = 3000;
    public static final int CODE_MASTERKEY_NOT_EXIST = 3001;
    public static final int CODE_PAUSE_LIMIT = 2004;
    public static final int CODE_PERMISSION_LIMIT = 1002;
    public static final int CODE_SECURE_SPACE_LIMIT = 1003;
    public static final int CODE_SWITCH_OFF = 2003;
    public static final int CODE_WLAN_ONLY_ERROR = 2002;
    private final int mErrorCode;

    public SyncLocalException(int i) {
        super("errorCode: " + i);
        this.mErrorCode = i;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }
}
