package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class ay implements Runnable {
    final /* synthetic */ aw a;

    ay(aw awVar) {
        this.a = awVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.onetrack.c.j.b()) {
            com.xiaomi.onetrack.c.ah.a().a(0, true);
            com.xiaomi.onetrack.c.ah.a().a(1, true);
        }
    }
}
