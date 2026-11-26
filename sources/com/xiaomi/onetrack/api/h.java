package com.xiaomi.onetrack.api;

import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class h implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Map b;
    final /* synthetic */ c c;

    h(c cVar, boolean z, Map map) {
        this.c = cVar;
        this.a = z;
        this.b = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.h()) {
                return;
            }
            if (!this.a) {
                JSONObject jSONObjectA = com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.b, false);
                if (!com.xiaomi.onetrack.f.b.a(this.c.j)) {
                    String strU = com.xiaomi.onetrack.util.ac.u();
                    String strW = com.xiaomi.onetrack.util.ac.w();
                    jSONObjectA.put("uid", strU);
                    jSONObjectA.put("uid_type", strW);
                }
                this.c.i().a("ot_logout", ai.d(jSONObjectA, this.c.f, this.c.h, this.c.f("ot_logout"), this.c.i, this.c.j));
            }
            com.xiaomi.onetrack.util.ac.v();
            com.xiaomi.onetrack.util.ac.x();
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "logout error:" + e.toString());
        }
    }
}
