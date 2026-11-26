package com.xiaomi.accountsdk.guestaccount;

import com.xiaomi.passport.StatConstants;

/* JADX INFO: loaded from: classes4.dex */
public class GuestAccountErrorCodeUtils {
    public static String descript(int i, String str) {
        if (i < 100) {
            switch (i) {
                case -1:
                    return f(i, "UNKNOWN_ERROR");
                case 0:
                    return f(i, "NO_ERROR");
                case 1:
                    return f(i, "CANNOT_JUDGE_APP_PERMISSION_ERROR");
                case 2:
                    return f(i, "NO_PERMISSION_ERROR");
                case 3:
                    return f(i, "LOCAL_INTERRUPTED");
                case 4:
                    return f(i, "LOCAL_CTA_ERROR");
                case 5:
                    return f(i, "SERVER_UNKNOWN_ERROR");
                case 6:
                    return f(i, "IO_ERROR");
                case 7:
                    return f(i, "Local parameter error:" + str);
                default:
                    return f(i, "unknown_error");
            }
        }
        if (i < 1000) {
            return f(i, "http_error");
        }
        if (i < 100000) {
            return f(i, "unknown_error");
        }
        if (i < 101000) {
            return f(i, "server_fatal_error");
        }
        if (i < 102000) {
            return f(i, "server_argument_error");
        }
        if (i < 103000) {
            return f(i, "server_database_error");
        }
        if (i < 104000) {
            return f(i, StatConstants.ERROR_SERVER);
        }
        if (i < 105000) {
            return f(i, "server_coder_error");
        }
        if (i < 106000) {
            return f(i, "server_domain_error");
        }
        if (i < 107000) {
            return f(i, "server_token_error");
        }
        if (i < 108000) {
            return f(i, "server_json_error");
        }
        return f(i, "unknown_error");
    }

    private static String f(int i, String str) {
        return String.format("code:%d(%s)", Integer.valueOf(i), str);
    }
}
