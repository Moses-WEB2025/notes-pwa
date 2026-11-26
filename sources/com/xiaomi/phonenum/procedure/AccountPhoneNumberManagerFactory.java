package com.xiaomi.phonenum.procedure;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class AccountPhoneNumberManagerFactory {
    public static IAccountPhoneNumberManager createProperManager(Context context) {
        if (MiuiAccountPhoneNumberManager.isAccountPhoneNumberManagerServiceExists(context)) {
            return new MiuiAccountPhoneNumberManager();
        }
        return new OtherOsAccountPhoneNumberManager();
    }
}
