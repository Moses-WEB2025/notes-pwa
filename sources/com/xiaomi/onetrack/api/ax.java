package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;

/* JADX INFO: loaded from: classes4.dex */
class ax implements Runnable {
    final /* synthetic */ Configuration a;
    final /* synthetic */ aw b;

    ax(aw awVar, Configuration configuration) {
        this.b = awVar;
        this.a = configuration;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.c.a.a();
        ad.a().d();
        ad.a().f();
        com.xiaomi.onetrack.b.p.a().a(this.a.getProjectId(), com.xiaomi.onetrack.b.p.a().e(this.a.getAppId()), this.a.getPrivateKeyId(), this.a.getAppId());
        com.xiaomi.onetrack.util.q.a().a((Boolean) false);
        com.xiaomi.onetrack.c.n.b();
    }
}
