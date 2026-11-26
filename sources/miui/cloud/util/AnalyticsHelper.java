package miui.cloud.util;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.util.Log;
import java.util.HashMap;
import miui.accounts.ExtraAccountManager;
import miui.cloud.Constants;

/* JADX INFO: loaded from: classes5.dex */
public class AnalyticsHelper {
    private static final String TAG = "AnalyticsHelper";

    private static void sendAnalyticsRequest(Context context, String str, HashMap<String, String> map) {
        context.sendBroadcast(createAnalyticsIntent(context, str, map));
    }

    private static Intent createAnalyticsIntent(Context context, String str, HashMap<String, String> map) {
        Intent intent = new Intent(Constants.Analytics.ACTION);
        intent.putExtra(Constants.Analytics.EXTRA_ANALYTICS_EVENT_ID, "event_id_micloud_activate_source");
        intent.putExtra(Constants.Analytics.EXTRA_ANALYTICS_EVENT_PARAMETERS, map);
        intent.setPackage("com.miui.cloudservice");
        return intent;
    }

    public static void analyseActivateSource(Context context, String str, boolean z) {
        analyseActivateSource(context, str, z, "");
    }

    public static void analyseActivateSource(Context context, String str, boolean z, String str2) {
        analyseActivateSource(context, str, z, str2, "");
    }

    public static void analyseActivateSource(Context context, String str, boolean z, String str2, String str3) {
        HashMap map = new HashMap();
        map.put("event_key_micloud_activate_source", str);
        map.put("event_key_micloud_activate_init", String.valueOf(isAllMiCloudSyncOff(context)));
        map.put("event_key_micloud_activate_result", String.valueOf(z));
        map.put("event_key_micloud_activate_package_name", str2);
        map.put("event_key_micloud_activate_authority", str3);
        sendAnalyticsRequest(context, "event_id_micloud_activate_source", map);
    }

    public static void analyseMiCloudLoginEnable(Context context, boolean z, boolean z2, boolean z3, boolean z4) {
        HashMap map = new HashMap();
        map.put("event_key_micloud_setup_guide", String.valueOf(z));
        map.put("event_key_micloud_sync_enable", String.valueOf(z2));
        map.put("event_key_micloud_find_device_enable", String.valueOf(z3));
        map.put("event_key_micloud_mx_enable", String.valueOf(z4));
        sendAnalyticsRequest(context, "event_id_micloud_login_enable", map);
    }

    public static void analyseMiCloudLoginEnable(Context context, boolean z, boolean z2, boolean z3) {
        HashMap map = new HashMap();
        map.put("event_key_micloud_setup_guide", String.valueOf(z));
        map.put("event_key_micloud_sync_enable", String.valueOf(z2));
        map.put("event_key_micloud_find_device_enable", String.valueOf(z3));
        sendAnalyticsRequest(context, "event_id_micloud_login_enable", map);
    }

    private static boolean isAllMiCloudSyncOff(Context context) {
        Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(context);
        if (xiaomiAccount == null) {
            Log.d(TAG, "account is null in isAllMiCloudSyncOff()");
            return true;
        }
        for (SyncAdapterType syncAdapterType : ContentResolver.getSyncAdapterTypes()) {
            if (syncAdapterType.accountType.equals("com.xiaomi") && ContentResolver.getSyncAutomatically(xiaomiAccount, syncAdapterType.authority)) {
                return false;
            }
        }
        return true;
    }
}
