package com.xiaomi.account.authenticator;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class ExtraTokensManager {
    private static final String SP_KEY_USER_ID = "userId";
    private static final String SP_NAME = "extra_tokens";
    private static final String TAG = "ExtraTokensManager";

    private ExtraTokensManager() {
    }

    private static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(SP_NAME, 0);
    }

    public static void save(Context context, String str, Map<String, String> map) {
        AccountLogger.log(TAG, "Save into SharedPreferences");
        SharedPreferences sharedPrefs = getSharedPrefs(context);
        String string = sharedPrefs.getString("userId", null);
        SharedPreferences.Editor editorEdit = sharedPrefs.edit();
        if (!TextUtils.equals(str, string)) {
            AccountLogger.log(TAG, "clear all old user token data");
            editorEdit.clear();
            editorEdit.putString("userId", str);
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editorEdit.putString(entry.getKey(), entry.getValue());
        }
        editorEdit.commit();
    }

    public static void restoreIfNotExists(Context context, Account account) {
        AccountLogger.log(TAG, "Restore to UserData");
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(context);
        SharedPreferences sharedPrefs = getSharedPrefs(context);
        if (!TextUtils.equals(account.name, sharedPrefs.getString("userId", null))) {
            AccountLogger.log(TAG, "ignore restore: userId not match");
            return;
        }
        Map<String, ?> all = sharedPrefs.getAll();
        all.remove("userId");
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String key = entry.getKey();
            if (!TextUtils.isEmpty(key)) {
                Object value = entry.getValue();
                if (value instanceof String) {
                    String str = (String) value;
                    if (xiaomiAccountManager.getUserData(account, key) == null) {
                        xiaomiAccountManager.setUserData(account, key, str);
                    }
                }
            }
        }
    }
}
