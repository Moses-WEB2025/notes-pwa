package com.xiaomi.onetrack.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes4.dex */
final class e extends BroadcastReceiver {
    e() {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        a.a(new f(this, intent));
    }
}
