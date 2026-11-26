package com.xiaomi.micloudsdk.sync;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.sync.utils.SyncContentUtils;
import com.xiaomi.micloudsdk.utils.NetworkUtils;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudResolver {
    private static final String OPERATION_VALUE_ALARM = "alarm";
    public static final String PAUSE_AUTHORITY_VALUE_FOR_ALL = "_all";
    private static final String TAG = "MiCloudResolver";

    public static void requestSync(Context context, Account account, String str) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "requestSync: authority: " + str);
        if (isSyncPausing(context, account, str)) {
            Log.e(TAG, "requestSync: authority: " + str + "is paused. request sync fail");
        }
        ContentResolver.requestSync(account, str, new Bundle());
    }

    public static void requestForceSync(Context context, Account account, String str) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "requestForceSync: authority: " + str);
        if (isSyncPausing(context, account, str)) {
            Log.e(TAG, "requestSync: authority: " + str + "is paused. request sync fail");
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.FORCE, true);
        bundle.putBoolean("micloud_ignore_temperature", true);
        bundle.putBoolean("micloud_ignore_battery_low", true);
        if (NetworkUtils.isNetworkMetered(context)) {
            bundle.putBoolean("micloud_ignore_wifi_settings", true);
        }
        ContentResolver.requestSync(account, str, bundle);
    }

    public static void cancelSync(Context context, Account account, String str) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "cancelSync: authority: " + str);
        ContentResolver.cancelSync(account, str);
        sendCancelCommand(context, str);
    }

    public static void pauseSync(Context context, Account account, String str, long j) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        assertTime(j);
        Log.d(TAG, "pauseSync: authority: " + str + ", time: " + j);
        cancelSync(context, account, str);
        SyncContentUtils.savePauseTime(context, str, j);
        tryToScheduleResume(context);
    }

    public static void resumeSync(Context context, Account account, String str) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "resumeSync: authority: " + str);
        SyncContentUtils.insertPauseExceptAuthority(context, str);
        SyncContentUtils.savePauseTime(context, str, 0L);
        requestSync(context, account, str);
        tryToScheduleResume(context);
    }

    public static long getResumeTime(Context context, String str) {
        assertContext(context);
        assertAuthority(str);
        Log.d(TAG, "getResumeTime: authority: " + str);
        boolean zIsPauseExceptAuthority = SyncContentUtils.isPauseExceptAuthority(context, str);
        long jLoadResumeTime = SyncContentUtils.loadResumeTime(context, str);
        if (!zIsPauseExceptAuthority) {
            long jLoadResumeTime2 = SyncContentUtils.loadResumeTime(context, PAUSE_AUTHORITY_VALUE_FOR_ALL);
            if (jLoadResumeTime2 >= jLoadResumeTime) {
                return jLoadResumeTime2;
            }
        }
        return jLoadResumeTime;
    }

    public static void requestAllSyncs(Context context, Account account) {
        assertContext(context);
        assertAccount(account);
        Log.d(TAG, "requestAllSyncs");
        for (String str : getAuthorityList()) {
            if (!isSyncPausing(context, account, str)) {
                requestSync(context, account, str);
            }
        }
    }

    public static void cancelAllSyncs(Context context, Account account) {
        assertContext(context);
        assertAccount(account);
        Log.d(TAG, "cancelAllSyncs");
        Iterator<String> it = getAuthorityList().iterator();
        while (it.hasNext()) {
            cancelSync(context, account, it.next());
        }
    }

    public static void pauseAllSyncs(Context context, Account account, long j) {
        assertContext(context);
        assertAccount(account);
        assertTime(j);
        Log.d(TAG, "pauseAllSyncs: time: " + j);
        cancelAllSyncs(context, account);
        SyncContentUtils.deletePauseExceptAuthority(context);
        SyncContentUtils.savePauseTime(context, PAUSE_AUTHORITY_VALUE_FOR_ALL, j);
        tryToScheduleResume(context);
    }

    public static void resumeAllSyncs(Context context, Account account) {
        assertContext(context);
        assertAccount(account);
        Log.d(TAG, "resumeAllSyncs");
        SyncContentUtils.savePauseTime(context, PAUSE_AUTHORITY_VALUE_FOR_ALL, 0L);
        Iterator<String> it = getAuthorityList().iterator();
        while (it.hasNext()) {
            SyncContentUtils.savePauseTime(context, it.next(), 0L);
        }
        requestAllSyncs(context, account);
        tryToScheduleResume(context);
    }

    public static long getLastestResumeTime(Context context, Account account) {
        assertContext(context);
        assertAccount(account);
        Log.d(TAG, "getLastestResumeTime");
        Iterator<String> it = getAuthorityList().iterator();
        long j = Long.MAX_VALUE;
        while (it.hasNext()) {
            long jLoadResumeTime = SyncContentUtils.loadResumeTime(context, it.next());
            if (jLoadResumeTime < j) {
                j = jLoadResumeTime;
            }
        }
        long jLoadResumeTime2 = SyncContentUtils.loadResumeTime(context, PAUSE_AUTHORITY_VALUE_FOR_ALL);
        return j < jLoadResumeTime2 ? jLoadResumeTime2 : j;
    }

    public static long getResumeTimeForAll(Context context, Account account) {
        assertContext(context);
        assertAccount(account);
        Log.d(TAG, "getResumeTimeForAll");
        return SyncContentUtils.loadResumeTime(context, PAUSE_AUTHORITY_VALUE_FOR_ALL);
    }

    public static boolean isSyncActive(Account account, String str) {
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "isSyncActive: authority: " + str);
        return ContentResolver.isSyncActive(account, str);
    }

    public static boolean isSyncPending(Account account, String str) {
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "isSyncPending: authority: " + str);
        return ContentResolver.isSyncPending(account, str);
    }

    public static boolean isSyncPausing(Context context, Account account, String str) {
        assertContext(context);
        assertAccount(account);
        assertAuthority(str);
        Log.d(TAG, "isSyncPausing: authority: " + str);
        return System.currentTimeMillis() <= getResumeTime(context, str);
    }

    private static void assertContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
    }

    private static void assertAccount(Account account) {
        if (account == null || !"com.xiaomi".equals(account.type)) {
            throw new IllegalArgumentException("illegal account");
        }
    }

    private static void assertAuthority(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("illegal authority: empty");
        }
        if (!getAuthorityList().contains(str)) {
            throw new IllegalArgumentException("illegal authority: not registered authority: " + str);
        }
    }

    private static void assertTime(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("illegal time");
        }
    }

    public static List<String> getAuthorityList() {
        ArrayList arrayList = new ArrayList();
        for (SyncAdapterType syncAdapterType : ContentResolver.getSyncAdapterTypes()) {
            if ("com.xiaomi".equals(syncAdapterType.accountType)) {
                arrayList.add(syncAdapterType.authority);
            }
        }
        return arrayList;
    }

    private static void tryToScheduleResume(Context context) {
        Intent intent = new Intent("com.xiaomi.action.SYNC_RESUME");
        intent.setPackage("com.miui.cloudservice");
        intent.putExtra("extra_operation", "alarm");
        if (context.getPackageManager().resolveService(intent, 32) != null) {
            context.startService(intent);
        } else {
            context.sendBroadcast(intent);
        }
    }

    private static void sendCancelCommand(Context context, String str) {
        String str2 = SyncCommandConstant.SUPPORT_COMMAND_PKGS.get(str);
        if (TextUtils.isEmpty(str2)) {
            Log.e(TAG, "sendCancelCommand: " + str + " doesn't match pkgName");
            return;
        }
        Intent intent = new Intent("com.xiaomi.micloud.action.SYNC_COMMAND");
        intent.putExtra("key_command", "value_command_cancel_sync");
        intent.setPackage(str2);
        List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers != null && listQueryBroadcastReceivers.size() > 0) {
            context.sendBroadcast(intent);
            return;
        }
        try {
            context.startService(intent);
        } catch (IllegalStateException e) {
            Log.e(TAG, "sendCancelCommandFailed: ", e);
        }
    }
}
