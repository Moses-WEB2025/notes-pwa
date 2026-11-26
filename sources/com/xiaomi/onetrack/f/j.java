package com.xiaomi.onetrack.f;

import android.content.Context;
import com.xiaomi.onetrack.api.ad;
import com.xiaomi.onetrack.util.q;

/* JADX INFO: loaded from: classes4.dex */
class j implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ i b;

    j(i iVar, Context context) {
        this.b = iVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        q.a().a((Boolean) false);
        com.xiaomi.onetrack.c.a.a();
        ad.a().d();
        ad.a().f();
        com.xiaomi.onetrack.c.d.a(this.a);
    }
}
