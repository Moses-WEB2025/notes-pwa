package com.xiaomi.onetrack.api;

import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class x implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ c c;

    x(c cVar, String str, Map map) {
        this.c = cVar;
        this.a = str;
        this.b = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (!this.c.h() && !this.c.d(this.a)) {
                this.c.i().a(this.a, ai.a(this.a, com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.b, true), this.c.f, this.c.h, this.c.f(this.a), this.c.i, this.c.j));
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "track map error: " + e.toString());
        }
    }
}
