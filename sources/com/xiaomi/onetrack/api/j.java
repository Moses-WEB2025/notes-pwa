package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class j implements Runnable {
    final /* synthetic */ c a;

    j(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.a.h() || com.xiaomi.onetrack.util.ae.d(com.xiaomi.onetrack.util.ac.t())) {
                return;
            }
            com.xiaomi.onetrack.util.ac.m(System.currentTimeMillis());
            this.a.i().a("onetrack_dau", ai.a(this.a.f, this.a.h, this.a.f("onetrack_dau"), this.a.i, this.a.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "trackDau error  e:" + e.toString());
        }
    }
}
