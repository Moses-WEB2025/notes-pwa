package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class t implements Runnable {
    final /* synthetic */ c a;

    t(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.a.h()) {
                return;
            }
            this.a.i().a(ah.i, ai.a(this.a.f, this.a.h, this.a.i, this.a.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "cta event error: " + e.toString());
        }
    }
}
