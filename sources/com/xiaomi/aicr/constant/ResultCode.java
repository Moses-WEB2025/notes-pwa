package com.xiaomi.aicr.constant;

/* JADX INFO: loaded from: classes4.dex */
public enum ResultCode {
    RESULT_OK(0, "success"),
    RESULT_SERVICE_CONNECT_FAILED(-1001, "connect service failed, please retry"),
    RESULT_UNKNOWN_ERROR(-1002, "unknown error, please retry"),
    RESULT_NOT_SUPPORT_DOMAIN(-2001, "server donnot support the domain type"),
    RESULT_NOT_SUPPORT_CAPABILITY(-2002, "service donnot support the capability type"),
    RESULT_SERVER_NO_PERMISSION(-2003, "service don't have permmison to use the capability"),
    RESULT_CLIENT_NO_PERMISSION(-4001, "client don't have permission to use the capability"),
    RESULT_CLIENT_ERROR_PARAMS(-4002, "input parameters has error"),
    RESULT_SWITCH_NOT_OPEN(-3001, "cognition switch in settings is closed");

    private int code;
    private String msg;

    ResultCode(int i, String str) {
        this.code = i;
        this.msg = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static String getMsgByCode(Integer num) {
        if (num == null) {
            return null;
        }
        ResultCode byCode = getByCode(num);
        if (byCode == null) {
            return "null";
        }
        return byCode.getMsg();
    }

    public static ResultCode getByCode(Integer num) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode() == num.intValue()) {
                return resultCode;
            }
        }
        return null;
    }
}
