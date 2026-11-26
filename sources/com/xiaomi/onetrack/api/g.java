package com.xiaomi.onetrack.api;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class g implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Number b;
    final /* synthetic */ c c;

    g(c cVar, String str, Number number) {
        this.c = cVar;
        this.a = str;
        this.b = number;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.h()) {
                return;
            }
            this.c.i().a(ah.d, ai.b(new JSONObject().put(this.a, this.b), this.c.f, this.c.h, this.c.f(ah.d), this.c.i, this.c.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "userProfileIncrement single error:" + e.toString());
        }
    }
}
