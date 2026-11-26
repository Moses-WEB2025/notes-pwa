package com.xiaomi.micloudsdk.sync.utils;

import android.content.SyncResult;

/* JADX INFO: loaded from: classes4.dex */
public class SyncExceptionUtils {
    public static final String TAG = "SyncExceptionUtils";

    public static String getErrorStringForLog(SyncResult syncResult) {
        StringBuilder sb = new StringBuilder();
        if (syncResult.hasHardError()) {
            sb.append("HardError: tooManyDeletions: ").append(syncResult.tooManyDeletions).append(" tooManyRetries: ").append(syncResult.tooManyRetries).append(" databaseError: ").append(syncResult.databaseError).append(" numAuthExceptions: ").append(syncResult.stats.numAuthExceptions).append(" numConflictDetectedExceptions: ").append(syncResult.stats.numConflictDetectedExceptions);
        } else {
            sb.append("SoftError: syncAlreadyInProgress: ").append(syncResult.syncAlreadyInProgress).append("numIoException: ").append(syncResult.stats.numIoExceptions);
        }
        return sb.toString();
    }
}
