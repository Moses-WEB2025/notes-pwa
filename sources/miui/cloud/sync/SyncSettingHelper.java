package miui.cloud.sync;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import miui.accounts.ExtraAccountManager;
import miui.cloud.Constants;

/* JADX INFO: loaded from: classes5.dex */
public class SyncSettingHelper {
    public static void openFindDeviceSettingUI(Activity activity) {
        if (ExtraAccountManager.getXiaomiAccount(activity) == null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("show_sync_settings", true);
            AccountManager.get(activity).addAccount("com.xiaomi", null, null, bundle, activity, null, null);
        } else {
            Intent intent = new Intent(Constants.Intents.ACTION_FIND_DEVICE_GUIDE);
            intent.putExtra("extra_micloud_find_device_guide_source", activity.getPackageName());
            intent.setPackage("com.miui.cloudservice");
            activity.startActivity(intent);
        }
    }
}
