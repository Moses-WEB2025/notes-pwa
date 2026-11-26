package com.xiaomi.passport.ui.utils;

import android.content.Context;
import android.widget.Toast;

/* JADX INFO: loaded from: classes4.dex */
public class AccountToast {
    public static void showToastMessage(Context context, int i) {
        showToastMessage(context, i, 0);
    }

    public static void showToastMessage(Context context, int i, int i2) {
        if (context != null) {
            showToastMessage(context, context.getApplicationContext().getResources().getString(i), i2);
        }
    }

    public static void showToastMessage(Context context, String str) {
        showToastMessage(context, str, 0);
    }

    public static void showToastMessage(Context context, String str, int i) {
        if (context != null) {
            Toast.makeText(context.getApplicationContext(), str, i).show();
        }
    }
}
