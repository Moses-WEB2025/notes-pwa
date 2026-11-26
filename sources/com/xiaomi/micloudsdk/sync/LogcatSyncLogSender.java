package com.xiaomi.micloudsdk.sync;

import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
class LogcatSyncLogSender extends SyncLogSender {
    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    void openSyncLog() {
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    void release() {
    }

    LogcatSyncLogSender() {
    }

    @Override // com.xiaomi.micloudsdk.sync.SyncLogSender
    void sendLog(String str, String str2) {
        Log.i(str, str2);
    }
}
