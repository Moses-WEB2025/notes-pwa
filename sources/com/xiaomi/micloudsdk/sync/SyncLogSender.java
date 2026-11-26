package com.xiaomi.micloudsdk.sync;

/* JADX INFO: loaded from: classes4.dex */
abstract class SyncLogSender {
    protected static final String TAG = "SyncLogSender";

    abstract void openSyncLog();

    abstract void release();

    abstract void sendLog(String str, String str2);

    SyncLogSender() {
    }
}
