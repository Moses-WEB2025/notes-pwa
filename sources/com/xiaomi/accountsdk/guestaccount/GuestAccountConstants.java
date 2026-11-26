package com.xiaomi.accountsdk.guestaccount;

import com.xiaomi.accountsdk.guestaccount.data.GuestAccountResult;

/* JADX INFO: loaded from: classes4.dex */
public class GuestAccountConstants {
    public static final String CHARSET_NAME = "UTF-8";
    public static final int ERROR_CANNOT_JUDGE_APP_PERMISSION = 1;
    public static final int ERROR_CTA_ERROR = 4;
    public static final int ERROR_ILLEGAL_ARGUMENT = 7;
    public static final int ERROR_INTERNATIONAL_NOT_SUPPORT = 8;
    public static final int ERROR_INTERRUPTED = 3;
    public static final int ERROR_IO_ERROR = 6;
    public static final int ERROR_NO_APP_PERMISSION = 2;
    public static final int ERROR_NO_ERROR = 0;
    public static final int ERROR_SERVER_ERROR = 5;
    public static final int ERROR_UNKNOWN_ERROR = -1;
    public static final String GUEST_ACCOUNT_SDK_VERSION = "0.0.4";
    public static final GuestAccountResult RESULT_OF_UNKNOWN_ERROR = new GuestAccountResult().setErrorCode(-1);
    public static final GuestAccountResult RESULT_OF_CANNOT_JUDGE_APP_PERMISSION = new GuestAccountResult().setErrorCode(1);
    public static final GuestAccountResult RESULT_OF_NO_PERMISSION = new GuestAccountResult().setErrorCode(2);
    public static final GuestAccountResult RESULT_OF_INTERRUPTED = new GuestAccountResult().setErrorCode(3);
    public static final GuestAccountResult RESULT_OF_CTA_ERROR = new GuestAccountResult().setErrorCode(4);
    public static final GuestAccountResult RESULT_OF_SERVER_ERROR = new GuestAccountResult().setErrorCode(5);
    public static final GuestAccountResult RESULT_OF_IO_ERROR = new GuestAccountResult().setErrorCode(6);
    public static final GuestAccountResult RESULT_OF_ILLEGAL_ARGUMENT = new GuestAccountResult().setErrorCode(7);
    public static final GuestAccountResult RESULT_OF_INTERNATIONAL_NOT_SUPPORT = new GuestAccountResult().setErrorCode(8);
}
