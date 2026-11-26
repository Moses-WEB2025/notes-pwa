package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.OneTrack;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class e implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ OneTrack.UserIdType b;
    final /* synthetic */ boolean c;
    final /* synthetic */ Map d;
    final /* synthetic */ c e;

    e(c cVar, String str, OneTrack.UserIdType userIdType, boolean z, Map map) {
        this.e = cVar;
        this.a = str;
        this.b = userIdType;
        this.c = z;
        this.d = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.e.h()) {
                return;
            }
            com.xiaomi.onetrack.util.ac.g(this.a);
            com.xiaomi.onetrack.util.ac.h(this.b.getUserIdType());
            if (this.c) {
                return;
            }
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.d, false);
            if (!com.xiaomi.onetrack.f.b.a(this.e.j)) {
                jSONObjectA.put("uid", this.a);
                jSONObjectA.put("uid_type", this.b.getUserIdType());
            }
            this.e.i().a("ot_login", ai.c(jSONObjectA, this.e.f, this.e.h, this.e.f("ot_login"), this.e.i, this.e.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "login error:" + e.toString());
        }
    }
}
