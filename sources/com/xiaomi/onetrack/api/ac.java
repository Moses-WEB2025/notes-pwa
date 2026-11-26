package com.xiaomi.onetrack.api;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class ac implements Runnable {
    final /* synthetic */ Object a;
    final /* synthetic */ String b;
    final /* synthetic */ c c;

    ac(c cVar, Object obj, String str) {
        this.c = cVar;
        this.a = obj;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.h()) {
                return;
            }
            if (com.xiaomi.onetrack.util.t.b(this.a)) {
                this.c.i().a(ah.c, ai.a(new JSONObject().put(this.b, this.a), this.c.f, this.c.h, this.c.f(ah.c), this.c.i, this.c.j));
                return;
            }
            com.xiaomi.onetrack.util.t.a("BaseOneTrackImp", this.b);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "setUserProfile single error:" + e.toString());
        }
    }
}
