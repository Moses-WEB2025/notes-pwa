package com.miui.todo.cloudservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import miui.accounts.ExtraAccountManager;
import miui.cloud.CloudPushConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PushReceiver extends BroadcastReceiver {
    private static final String MICLOUD_TODO_SYNC = "micloud.todo.sync";
    private static final String TAG = "PushReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logger.INSTANCE.i(TAG, "PushReceiver onReceive：" + action);
        if (action == null) {
            return;
        }
        String stringExtra = intent.getStringExtra("pushType");
        String stringExtra2 = intent.getStringExtra("pushName");
        String stringExtra3 = intent.getStringExtra(CloudPushConstants.PUSH_DATA);
        Logger.INSTANCE.i(TAG, "PushReceiver pushType：" + stringExtra + " rawPushName:" + stringExtra2 + " pushData:" + stringExtra3);
        if (!"watermark".equals(stringExtra) || ExtraAccountManager.getXiaomiAccount(context) == null) {
            return;
        }
        int iIndexOf = stringExtra2.indexOf(95);
        if (iIndexOf != -1) {
            stringExtra2 = stringExtra2.substring(0, iIndexOf);
        }
        if (TextUtils.equals(stringExtra2, MICLOUD_TODO_SYNC)) {
            Bundle bundle = new Bundle();
            bundle.putInt("push_type", 2);
            if (TextUtils.isEmpty(stringExtra3)) {
                return;
            }
            long j = Long.parseLong(stringExtra3);
            Logger.INSTANCE.i(TAG, "PushReceiver onReceive" + PreferenceUtils.getWaterMarker() + StringUtils.SPACE + j + StringUtils.SPACE + (PreferenceUtils.getWaterMarker() < j));
            if (PreferenceUtils.getWaterMarker() < j) {
                PreferenceUtils.setWaterMarker(j);
                bundle.putString(CloudPushConstants.PUSH_DATA, stringExtra3);
                SyncUtils.requestSync(NoteApp.getInstance(), bundle, false);
            }
        }
    }
}
