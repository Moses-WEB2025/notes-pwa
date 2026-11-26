package com.xiaomi.accountsdk.logger;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class LogSenderFactory {
    private static final String DUMP_LOG_PARENT_DIR = "dump/%s";
    private static final String ENCRYPT_KEY_LABEL = "account";
    private static final String ENCRYPT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/rvLw8ixslouHZwYrLrj6cp3mLRbCVQytRE8d0BskwvsZOvZnAFXIwC4BGErnxKmnyRFmVuDsoKLqbrx3IYDO/g2O5KZJjS2t+9xOZNm7FQwsoflJm+TfhFEg1gxSabWZP8GJHtqa7WDN2xGYSP48QqHv26wPBbHiOBZURDlXEQIDAQAB";
    private static final int MAX_LOG_FILE_COUNT = 2;
    private static final int MAX_LOG_FILE_SIZE_IN_BYTE = 2621440;

    public static XLogger.LogSender createSimpleLogSender() {
        return new XLogger.LogSender() { // from class: com.xiaomi.accountsdk.logger.LogSenderFactory.1
            @Override // com.xiaomi.accountsdk.logger.XLogger.LogSender
            public void sendLog(int i, String str, String str2) {
                Log.println(i, str, str2);
            }
        };
    }

    public static EncryptLogSender createEncryptLogSender(XLogger.LogSender logSender) {
        return new EncryptLogSender(logSender, ENCRYPT_PUBLIC_KEY, "account", false);
    }
}
