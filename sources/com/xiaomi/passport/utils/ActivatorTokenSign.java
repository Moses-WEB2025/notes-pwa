package com.xiaomi.passport.utils;

import android.content.Context;
import android.os.Looper;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.xiaomi.passport.SecurityDeviceSignManager;
import com.xiaomi.phonenum.data.AccountCertification;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberManagerFactory;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberSourceFlag;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class ActivatorTokenSign {
    public static Map<String, String> fillHeaders(Context context, Map<String, String> map) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("can not call in main thread");
        }
        if (map == null) {
            map = new HashMap<>();
        }
        AccountCertification[] accountCertifications = AccountPhoneNumberManagerFactory.createProperManager(context).getAccountCertifications(context, "passportapi", AccountPhoneNumberSourceFlag.getFlag(1));
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        for (AccountCertification accountCertification : accountCertifications) {
            if (accountCertification != null) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(accountCertification.activatorToken);
                if (sb2.length() > 0) {
                    sb2.append(";");
                }
                sb2.append(accountCertification.hashedPhoneNumber);
                if (sb3.length() > 0) {
                    sb3.append(";");
                }
                String strSyncSignStringArray = SecurityDeviceSignManager.syncSignStringArray(context, new String[]{accountCertification.hashedPhoneNumber}, null, EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS);
                if (strSyncSignStringArray == null) {
                    strSyncSignStringArray = "";
                }
                sb3.append(strSyncSignStringArray);
            }
        }
        map.put("Activator-Tokens", sb.toString());
        map.put("Hashed-Phones", sb2.toString());
        map.put("Tz-Signs", sb3.toString());
        return map;
    }
}
