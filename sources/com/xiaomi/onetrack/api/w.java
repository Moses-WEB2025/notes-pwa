package com.xiaomi.onetrack.api;

import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class w implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ String c;
    final /* synthetic */ c d;

    w(c cVar, String str, Map map, String str2) {
        this.d = cVar;
        this.a = str;
        this.b = map;
        this.c = str2;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (!this.d.h() && !this.d.d(this.a)) {
                this.d.i().a(this.a, ai.a(this.c, this.a, com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.b, true), this.d.f, this.d.h, this.d.f(this.a), this.d.i, this.d.j));
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "track json error:" + e.toString());
        }
    }
}
