package com.xiaomi.onetrack.c;

import android.content.Intent;

/* JADX INFO: loaded from: classes4.dex */
class f implements Runnable {
    final /* synthetic */ Intent a;
    final /* synthetic */ e b;

    f(e eVar, Intent intent) {
        this.b = eVar;
        this.a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        String action = this.a.getAction();
        if (action.equals("android.intent.action.SCREEN_OFF") || action.equals("android.intent.action.SCREEN_ON")) {
            com.xiaomi.onetrack.util.r.a("EventManager", "screen on/off");
            ah.a().a(action.equals("android.intent.action.SCREEN_ON") ? 0 : 2, false);
            com.xiaomi.onetrack.a.c.b.a().b();
        }
    }
}
