package com.xiaomi.onetrack.api;

import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class ab implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ c b;

    ab(c cVar, Map map) {
        this.b = cVar;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.b.h()) {
                return;
            }
            this.b.i().a(ah.c, ai.a(com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.a, false), this.b.f, this.b.h, this.b.f(ah.c), this.b.i, this.b.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "setUserProfile map error:" + e.toString());
        }
    }
}
