package com.miui.notes.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.common.tool.CompatUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.data.provider.TodoProviderAccessUtils;
import com.miui.todo.utils.SerializableUtils;
import com.xiaomi.accountsdk.account.AccountIntent;

/* JADX INFO: loaded from: classes4.dex */
public class AccountReceiver extends BroadcastReceiver {
    private static final String TAG = "AccountReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            if (TextUtils.equals(CompatUtils.invokeGetSender(intent), AccountIntent.PACKAGE_XIAOMI_ACCOUNT)) {
                int i = 0;
                int intExtra = intent.getIntExtra("extra_update_type", 0);
                if (intExtra != 1) {
                    if (intExtra == 2) {
                        PreferenceUtils.setCleanAccountCachePref(true);
                        return;
                    } else {
                        Logger.INSTANCE.w(TAG, "Not supported account changed type " + intExtra);
                        return;
                    }
                }
                Bundle bundleExtra = intent.getBundleExtra("extra_bundle");
                if (bundleExtra == null || !bundleExtra.getBoolean("extra_wipe_data")) {
                    PreferenceUtils.setWaterMarker(0L);
                    SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY, null);
                    TodoProviderAccessUtils.clearItemSyncInfo();
                    i = 2;
                } else if (bundleExtra.getBoolean("extra_wipe_synced_data", true)) {
                    TodoProviderAccessUtils.clearCloudItemSync();
                    PreferenceUtils.setWaterMarker(0L);
                    SerializableUtils.writeObject(PreferenceUtils.PREFERENCE_ACCOUNT_ENTITY, null);
                    i = 1;
                }
                Notes.Utils.clearAccount(context, i);
                PreferenceUtils.setCleanAccountCachePref(true);
            }
        } catch (Exception e) {
            Logger.INSTANCE.d(TAG, "AccountReceiver:" + e);
        }
    }
}
