package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class av implements Runnable {
    final /* synthetic */ at a;

    av(at atVar) {
        this.a = atVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.onetrack.c.j.b()) {
            this.a.b();
        }
    }
}
