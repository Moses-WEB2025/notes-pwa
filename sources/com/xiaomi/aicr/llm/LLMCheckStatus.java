package com.xiaomi.aicr.llm;

/* JADX INFO: loaded from: classes4.dex */
public @interface LLMCheckStatus {
    public static final byte STATUS_ACCOUNT_BANNED = 13;
    public static final byte STATUS_BUSY = 9;
    public static final byte STATUS_DEVICE_DISCONNECT = 14;
    public static final byte STATUS_ERROR_FORMAT = 7;
    public static final byte STATUS_ERROR_PARAMETERS = 16;
    public static final byte STATUS_ERROR_SESSION = 15;
    public static final byte STATUS_HF_BANNED = 12;
    public static final byte STATUS_LOWBATTERY = 6;
    public static final byte STATUS_NETWORK_ERROR = 11;
    public static final byte STATUS_NO_REMAINING_TIME = 8;
    public static final byte STATUS_OVERHEAT = 5;
    public static final byte STATUS_PASS = 0;
    public static final byte STATUS_TIMEOUT = 10;
    public static final byte STATUS_UNCONNECTED = 3;
    public static final byte STATUS_UNDOWNLOADED = 1;
    public static final byte STATUS_UNKNOWN_ERROR = 4;
    public static final byte STATUS_UNSUPPORTED = 2;
}
