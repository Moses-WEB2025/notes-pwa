package com.xiaomi.onetrack.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

/* JADX INFO: loaded from: classes4.dex */
class af extends BroadcastReceiver {
    final /* synthetic */ ad a;

    af(ad adVar) {
        this.a = adVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.a.d != null) {
            Intent intent2 = new Intent();
            intent2.putExtras(intent);
            Message messageObtain = Message.obtain();
            messageObtain.what = 10;
            messageObtain.obj = intent2;
            this.a.d.sendMessage(messageObtain);
            com.xiaomi.onetrack.util.r.a("BroadcastManager", "netReceiver");
        }
    }
}
