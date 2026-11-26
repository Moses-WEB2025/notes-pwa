package com.miui.notes.tool;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import com.miui.common.tool.Logger;
import com.xiaomi.verificationsdk.internal.Constants;

/* JADX INFO: loaded from: classes4.dex */
public class SyncUtils {
    private static final String TAG = "Notes:SyncUtils";
    private static boolean reRequestSynced = false;

    public static void setReSynced(boolean z) {
        Logger.INSTANCE.d(TAG, "setReSynced " + z);
        reRequestSynced = z;
    }

    public static void requestSync(Context context, Bundle bundle, boolean z) {
        Account account = AccountCache.getInstance().getAccount(context);
        if (account == null) {
            Logger.INSTANCE.d(TAG, "requestSync cancel for account is null");
            return;
        }
        if (z && bundle != null) {
            bundle.putBoolean(Constants.FORCE, true);
        }
        if (!z && ContentResolver.isSyncActive(account, "notes")) {
            Logger.INSTANCE.d(TAG, "requestSync skip, sync is active");
            return;
        }
        setReSynced(false);
        ContentResolver.requestSync(account, "notes", bundle);
        Logger.INSTANCE.d(TAG, "requestSync done");
    }

    public static void pcStopSync(Account account) {
        if (account == null || !ContentResolver.getSyncAutomatically(account, "notes")) {
            return;
        }
        ContentResolver.setSyncAutomatically(account, "notes", false);
        PreferenceUtils.setIsPcStopSync(true);
    }

    public static void recoverSyncIfPcStop(Context context, Account account) {
        if (account == null || ContentResolver.getSyncAutomatically(account, "notes") || !PreferenceUtils.getIsPcStopSync(context)) {
            return;
        }
        ContentResolver.setSyncAutomatically(account, "notes", true);
        PreferenceUtils.setIsPcStopSync(false);
    }
}
