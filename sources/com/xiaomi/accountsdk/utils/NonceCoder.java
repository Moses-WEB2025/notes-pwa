package com.xiaomi.accountsdk.utils;

/* JADX INFO: loaded from: classes4.dex */
public class NonceCoder {
    public static String generateNonce() {
        return generateNonce(SyncServerTimeExecutor.getInstance().getCurrentServerTimeMillis());
    }

    public static String generateNonce(long j) {
        return Coder.generateNonce(j);
    }
}
