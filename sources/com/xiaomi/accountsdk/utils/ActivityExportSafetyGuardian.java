package com.xiaomi.accountsdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class ActivityExportSafetyGuardian {
    private static final String EXTRA_KEY_UUID = "UUID";
    private static final ActivityExportSafetyGuardian INSTANCE = new ActivityExportSafetyGuardian();
    private static final String SP_KEY_UUID_PREFIX = "UUID_";
    private static final String SP_NAME = "PassportNotificationGuardian";

    public static ActivityExportSafetyGuardian getInstance() {
        return INSTANCE;
    }

    public void sign(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        String string = UUID.randomUUID().toString();
        intent.putExtra(EXTRA_KEY_UUID, string);
        getSp(context).edit().putBoolean(getSpKey(string), true).commit();
    }

    public boolean checkSign(Context context, Intent intent) {
        if (context != null && intent != null) {
            String stringExtra = intent.getStringExtra(EXTRA_KEY_UUID);
            if (TextUtils.isEmpty(stringExtra)) {
                return false;
            }
            SharedPreferences sp = getSp(context);
            String spKey = getSpKey(stringExtra);
            if (sp.getBoolean(spKey, false)) {
                sp.edit().remove(spKey).commit();
                return true;
            }
        }
        return false;
    }

    private SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(SP_NAME, 4);
    }

    private String getSpKey(String str) {
        return SP_KEY_UUID_PREFIX + str;
    }
}
