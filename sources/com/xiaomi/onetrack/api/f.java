package com.xiaomi.onetrack.api;

import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class f implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ c b;

    f(c cVar, Map map) {
        this.b = cVar;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.b.h()) {
                return;
            }
            this.b.i().a(ah.d, ai.b(new JSONObject(this.a), this.b.f, this.b.h, this.b.f(ah.d), this.b.i, this.b.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "userProfileIncrement map error:" + e.toString());
        }
    }
}
