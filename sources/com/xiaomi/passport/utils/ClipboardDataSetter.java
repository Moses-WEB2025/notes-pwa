package com.xiaomi.passport.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;

/* JADX INFO: loaded from: classes4.dex */
public class ClipboardDataSetter {
    public static void setClipboardData(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        ClipData clipDataNewPlainText = ClipData.newPlainText(charSequence, charSequence2);
        if (Build.VERSION.SDK_INT >= 33) {
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putBoolean("android.content.extra.IS_SENSITIVE", z);
            clipDataNewPlainText.getDescription().setExtras(persistableBundle);
        }
        clipboardManager.setPrimaryClip(clipDataNewPlainText);
    }
}
