package com.xiaomi.passport.compat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;

/* JADX INFO: loaded from: classes4.dex */
public class ContextCompat {
    public static final int ANDROID_T = 33;
    public static final int RECEIVER_EXPORTED = 2;
    public static final int RECEIVER_NOT_EXPORTED = 4;

    public static void registerReceiver(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, boolean z) {
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(broadcastReceiver, intentFilter, z ? 2 : 4);
        } else {
            context.registerReceiver(broadcastReceiver, intentFilter);
        }
    }
}
