package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class ap implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ ao b;

    ap(ao aoVar, int i) {
        this.b = aoVar;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.onetrack.c.j.b() && this.a == 2) {
            com.xiaomi.onetrack.c.ah.a().a(0, true);
            com.xiaomi.onetrack.c.ah.a().a(1, true);
            com.xiaomi.onetrack.a.c.b.a().b();
        }
    }
}
