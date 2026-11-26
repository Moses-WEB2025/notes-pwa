package com.xiaomi.onetrack.c;

import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class o implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ com.xiaomi.onetrack.f.b c;
    final /* synthetic */ Map d;
    final /* synthetic */ boolean e;
    final /* synthetic */ n f;

    o(n nVar, String str, String str2, com.xiaomi.onetrack.f.b bVar, Map map, boolean z) {
        this.f = nVar;
        this.a = str;
        this.b = str2;
        this.c = bVar;
        this.d = map;
        this.e = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f.b(this.a, this.b, this.c, this.d, this.e);
            if (this.e) {
                com.xiaomi.onetrack.a.a.a().a(this.c);
            }
            com.xiaomi.onetrack.a.c.b.a().b();
            ah.a().a(this.c.f(), false);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("PubSubEventManager", "EventManager.addTrackMessage exception: ", e);
        }
    }
}
