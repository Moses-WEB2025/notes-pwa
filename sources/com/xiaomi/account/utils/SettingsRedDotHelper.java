package com.xiaomi.account.utils;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class SettingsRedDotHelper {
    private static final String ACTION_SETTINGS_NOTIFICATION_RECEIVER = "miui.intent.action.SETTINGS_NOTIFICATION_RECEIVER";
    private static final String SETTINGS_NOTIFICATION_STATE = "settings_notification_state";
    private static final String SETTINGS_PACKAGE_NAME = "com.android.settings";
    private static final String TAG = "SettingsRedDotHelper";

    public static void addRedDot(Context context) {
        AccountLogger.log(TAG, "Push red dot added");
        setRedDot(true, context);
    }

    public static void removeRedDot(Context context) {
        AccountLogger.log(TAG, "Push red dot removed");
        setRedDot(false, context);
    }

    private static void setRedDot(Boolean bool, Context context) {
        Intent intent = new Intent(ACTION_SETTINGS_NOTIFICATION_RECEIVER);
        intent.setPackage("com.android.settings");
        intent.putExtra(SETTINGS_NOTIFICATION_STATE, bool);
        context.sendBroadcast(intent);
    }
}
