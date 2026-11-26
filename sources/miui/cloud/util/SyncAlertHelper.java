package miui.cloud.util;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import miui.accounts.ExtraAccountManager;
import miui.cloud.sync.SyncInfoHelper;
import miui.cloud.sync.SyncInfoUnavailableException;

/* JADX INFO: loaded from: classes5.dex */
public class SyncAlertHelper {
    private static final long DAY = 86400000;
    private static final long HOUR = 3600000;
    private static final long MINUTE = 60000;
    private static final long SECOND = 1000;
    private static final long TWO_WEEK = 1209600000;
    private static final long WEEK = 604800000;

    public static boolean isNeedAlert(Context context, String str) {
        if (SyncAlertRecordHelper.within(TWO_WEEK, context, str)) {
            return false;
        }
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(context);
        boolean zIsSyncAutomatically = isSyncAutomatically(xiaomiAccount, str);
        if (xiaomiAccount == null || !zIsSyncAutomatically) {
            return hasUnsyncedData(context, str);
        }
        SyncAlertRecordHelper.recordTime(context, str, -9223372036854775807L);
        return false;
    }

    private static boolean isSyncAutomatically(Account account, String str) {
        return SyncAutoSettingUtil.getXiaomiGlobalSyncAutomatically() && ContentResolver.getSyncAutomatically(account, str);
    }

    private static boolean hasUnsyncedData(Context context, String str) {
        int unsyncedDataCount;
        try {
            unsyncedDataCount = SyncInfoHelper.getUnsyncedDataCount(context, str);
        } catch (SyncInfoUnavailableException e) {
            e.printStackTrace();
            unsyncedDataCount = -1;
        }
        return unsyncedDataCount != 0;
    }

    public static void handleSyncAlert(Context context, String str) {
        startSyncAlertDialog(context, str);
        SyncAlertRecordHelper.recordTime(context, str);
    }

    public static void startSyncAlertDialog(Context context, String str) {
        Intent intent = new Intent("action_sync_alert");
        intent.setPackage("com.miui.cloudservice");
        context.startActivity(intent);
    }

    public static void recordTime(Context context, String str) {
        SyncAlertRecordHelper.recordTime(context, str);
    }
}
