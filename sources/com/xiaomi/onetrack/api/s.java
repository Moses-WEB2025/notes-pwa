package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.ServiceQualityEvent;

/* JADX INFO: loaded from: classes4.dex */
class s implements Runnable {
    final /* synthetic */ ServiceQualityEvent a;
    final /* synthetic */ c b;

    s(c cVar, ServiceQualityEvent serviceQualityEvent) {
        this.b = cVar;
        this.a = serviceQualityEvent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.b.h()) {
                return;
            }
            this.b.i().a(ah.e, ai.a(this.a, this.b.f, this.b.h, this.b.i, this.b.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "trackNetAvailableEvent error: " + e.toString());
        }
    }
}
