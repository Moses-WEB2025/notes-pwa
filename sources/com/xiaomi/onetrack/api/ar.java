package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.OneTrack;

/* JADX INFO: loaded from: classes4.dex */
class ar implements Runnable {
    final /* synthetic */ aq a;

    ar(aq aqVar) {
        this.a = aqVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.onetrack.util.q.a().a((Boolean) true);
        if (OneTrack.isRestrictGetNetworkInfo()) {
            ad.a().f();
        }
    }
}
