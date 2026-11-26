package com.xiaomi.passport.ui.settings;

import com.xiaomi.account.passportsdk.account_sso.R;

/* JADX INFO: loaded from: classes4.dex */
public class AsyncTaskResult {
    public static final int EXCEPTION_TYPE_BINDING_PHONE_RESTRICTED = 11;
    public static final int EXCEPTION_TYPE_ERROR_CAPTCHA = 12;
    public static final int EXCEPTION_TYPE_ERROR_VERIFY_CODE = 7;
    public static final int EXCEPTION_TYPE_FORBIDDEN = 4;
    public static final int EXCEPTION_TYPE_GET_PHONE_VCODE_EXCEED_LIMIT = 10;
    public static final int EXCEPTION_TYPE_ILLEGAL_DEVICE_ID = 14;
    public static final int EXCEPTION_TYPE_INVALID_BIND_ADDRESS = 9;
    public static final int EXCEPTION_TYPE_INVALID_PARAM = 16;
    public static final int EXCEPTION_TYPE_NEED_SMS_CODE = 15;
    public static final int EXCEPTION_TYPE_NETWORK = 2;
    public static final int EXCEPTION_TYPE_NO = 0;
    public static final int EXCEPTION_TYPE_PASSWORD = 1;
    public static final int EXCEPTION_TYPE_SEND_EMAIL_REACH_LIMIT = 13;
    public static final int EXCEPTION_TYPE_SERVER = 3;
    public static final int EXCEPTION_TYPE_TOKEN_EXPIRED = 6;
    public static final int EXCEPTION_TYPE_UNKNOWN = 5;
    public static final int EXCEPTION_TYPE_USED_EMAIL_ADDRESS = 8;
    public static final int EXCEPTION_TYPE_WRONG_PHONE_NUMBER_FORMAT = 17;
    private int mExceptionType;

    public AsyncTaskResult() {
        this.mExceptionType = 0;
    }

    public AsyncTaskResult(int i) {
        this.mExceptionType = i;
    }

    public boolean hasException() {
        return this.mExceptionType != 0;
    }

    public int getExceptionReason() {
        switch (this.mExceptionType) {
            case 0:
                return 0;
            case 1:
                return R.string.passport_bad_authentication;
            case 2:
                return R.string.passport_error_network;
            case 3:
                return R.string.passport_error_server;
            case 4:
                return R.string.passport_access_denied;
            case 5:
            case 15:
            case 16:
            default:
                return R.string.passport_error_unknown;
            case 6:
                return R.string.passport_sns_access_token_expired_warning;
            case 7:
                return R.string.passport_wrong_vcode;
            case 8:
                return R.string.passport_error_dup_binded_email;
            case 9:
                return R.string.passport_error_invalid_bind_address;
            case 10:
                return R.string.passport_get_phone_verifycode_exceed_limit;
            case 11:
                return R.string.passport_exceed_binded_phone_times_notice;
            case 12:
                return R.string.passport_wrong_captcha;
            case 13:
                return R.string.passport_resend_email_reach_limit_message;
            case 14:
                return R.string.passport_error_device_id;
            case 17:
                return R.string.passport_wrong_phone_number_format;
        }
    }

    public int getExceptionType() {
        return this.mExceptionType;
    }
}
