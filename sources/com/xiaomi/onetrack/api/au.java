package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;

/* JADX INFO: loaded from: classes4.dex */
class au implements Runnable {
    final /* synthetic */ Configuration a;
    final /* synthetic */ at b;

    au(at atVar, Configuration configuration) {
        this.b = atVar;
        this.a = configuration;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.b.p.a().a(this.a.getProjectId(), com.xiaomi.onetrack.b.p.a().e(this.a.getAppId()), this.a.getPrivateKeyId(), this.a.getAppId());
        com.xiaomi.onetrack.util.q.a().a((Boolean) true);
        if (OneTrack.isRestrictGetNetworkInfo()) {
            ad.a().f();
        }
    }
}
