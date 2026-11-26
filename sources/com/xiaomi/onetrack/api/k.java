package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class k implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ boolean b;
    final /* synthetic */ c c;

    k(c cVar, String str, boolean z) {
        this.c = cVar;
        this.a = str;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.c.h()) {
                return;
            }
            if (this.c.f.isAutoTrackActivityAction()) {
                this.c.i().a(ah.g, ai.a(this.a, ah.g, this.c.f, this.c.h, this.c.f(ah.g), this.b, this.c.i, this.c.j));
                if (com.xiaomi.onetrack.util.r.a) {
                    com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "trackPageStartAuto");
                    return;
                }
                return;
            }
            com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "config.autoTrackActivityAction is false, ignore onetrack_pa resume event");
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "auto trackPageStartAuto error: " + e.toString());
        }
    }
}
