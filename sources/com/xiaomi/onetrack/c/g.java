package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class g implements Runnable {
    final /* synthetic */ com.xiaomi.onetrack.f.b a;
    final /* synthetic */ boolean b;
    final /* synthetic */ d c;

    g(d dVar, com.xiaomi.onetrack.f.b bVar, boolean z) {
        this.c = dVar;
        this.a = bVar;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.c.b(this.a, this.b);
            if (this.b) {
                com.xiaomi.onetrack.a.a.a().a(this.a);
            }
            com.xiaomi.onetrack.a.c.b.a().b();
            com.xiaomi.onetrack.util.r.a("EventManager", "addEvent: " + this.a.e() + ", data: " + this.a.g().toString());
            ah.a().a(this.a.f(), false);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("EventManager", "EventManager.addEvent exception: ", e);
        }
    }
}
