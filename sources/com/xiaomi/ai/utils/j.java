package com.xiaomi.ai.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes4.dex */
class j extends BroadcastReceiver {
    final /* synthetic */ i a;

    j(i iVar) {
        this.a = iVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.a.a();
    }
}
