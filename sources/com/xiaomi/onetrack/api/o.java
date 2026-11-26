package com.xiaomi.onetrack.api;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes4.dex */
class o implements Runnable {
    final /* synthetic */ c a;

    o(c cVar) {
        this.a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.xiaomi.onetrack.util.r.a();
            com.xiaomi.onetrack.util.s.a();
            com.xiaomi.onetrack.b.a.a().a(this.a.f.getAppId());
            if (com.xiaomi.onetrack.util.ab.b(this.a.f.getAdEventAppId())) {
                ad.a().c();
                com.xiaomi.onetrack.b.a.a().a(this.a.f.getAdEventAppId());
            }
            if (com.xiaomi.onetrack.util.ac.B() == 0) {
                com.xiaomi.onetrack.util.ac.n(System.currentTimeMillis());
            }
            if (!TextUtils.isEmpty(this.a.f.getInstanceId())) {
                com.xiaomi.onetrack.util.q.a().a(this.a.f.getInstanceId());
            }
            this.a.g();
            com.xiaomi.onetrack.util.e.a();
            com.xiaomi.onetrack.c.j.c(false);
            com.xiaomi.onetrack.c.s.a().b(this.a.f.getAppId());
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "init WorkerExecutor execute throwable:" + th.getMessage());
        }
    }
}
