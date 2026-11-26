package com.xiaomi.accountsdk.account.exception;

import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.passport.accountmanager.PrimarySecondaryDeviceAccountHelper;

/* JADX INFO: loaded from: classes4.dex */
public class RequestSecondaryDeviceLoginDataException extends Exception {
    public static final int SERVER_CODE_RETRY = 10050;
    public final Code code;
    public final Intent handleIntent;
    public final int serverCode;

    public RequestSecondaryDeviceLoginDataException(Throwable th) {
        this(null, Code.UNKNOWN, null, -1, th);
    }

    public RequestSecondaryDeviceLoginDataException(String str) {
        this(str, Code.UNKNOWN, null, -1, null);
    }

    public RequestSecondaryDeviceLoginDataException(String str, Code code) {
        this(str, code, null, -1, null);
    }

    public RequestSecondaryDeviceLoginDataException(String str, Code code, int i) {
        this(str, code, null, i, null);
    }

    public RequestSecondaryDeviceLoginDataException(String str, Code code, Intent intent) {
        this(str, code, intent, -1, null);
    }

    public RequestSecondaryDeviceLoginDataException(String str, Code code, Intent intent, int i, Throwable th) {
        super(TextUtils.isEmpty(str) ? th == null ? "empty err message" : th.getMessage() : str, th);
        this.code = code;
        this.handleIntent = intent;
        this.serverCode = i;
    }

    public String createGetSecondaryDeviceLoginDataFailInfo() {
        return PrimarySecondaryDeviceAccountHelper.createGetSecondaryDeviceLoginDataFailInfo(this.code.value, this.serverCode, getMessage());
    }

    public enum Code {
        UNKNOWN(1),
        INVALID_PASS_TOKEN(2),
        UN_SUPPORT(3),
        NO_PERMISSION(4),
        SERVER_ERR(5);

        public final int value;

        Code(int i) {
            this.value = i;
        }

        public static Code fromValue(int i) {
            for (Code code : values()) {
                if (code.value == i) {
                    return code;
                }
            }
            return UNKNOWN;
        }
    }
}
