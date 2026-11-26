package com.xiaomi.passport.jsb;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodException extends Exception {
    public static final int ERROR_CODE_INVALID_STATE = 105;
    public static final int ERROR_CODE_INVOKE_INTERRUPTED = 101;
    public static final int ERROR_CODE_METHOD_NOT_FOUND = 100;
    public static final int ERROR_CODE_PAGE_NOT_FOUND = 106;
    public static final int ERROR_CODE_PARAMS_JSON_ERROR_FIELD = 104;
    public static final int ERROR_CODE_PARAMS_JSON_ERROR_FORMAT = 102;
    public static final int ERROR_CODE_RESULT_NULL = 150;
    public static final int ERROR_CODE_UNKNOWN = 200;
    public static final int ERROR_CODE_WEBVIEW_NULL = 201;
    public final int errorCode;
    public final String errorMessage;

    public PassportJsbMethodException(int i, String str) {
        this.errorCode = i;
        this.errorMessage = str;
    }

    public PassportJsbMethodException(int i, String str, Throwable th) {
        super(str, th);
        this.errorCode = i;
        this.errorMessage = str;
    }
}
