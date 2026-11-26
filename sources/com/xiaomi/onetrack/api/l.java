package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class l implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ long b;
    final /* synthetic */ c c;

    l(c cVar, String str, long j) {
        this.c = cVar;
        this.a = str;
        this.b = j;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.h()) {
                return;
            }
            if (this.c.f.isAutoTrackActivityAction()) {
                com.xiaomi.onetrack.util.ac.i(ai.a(this.a, ah.g, this.b, this.c.f, this.c.h, this.c.f(ah.g), this.c.i, this.c.j));
            } else {
                com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "config.autoTrackActivityAction is false, ignore onetrack_pa pause event");
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "savePageEndData error:" + e.toString());
        }
    }
}
