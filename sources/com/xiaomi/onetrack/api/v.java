package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class v implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ c b;

    v(c cVar, boolean z) {
        this.b = cVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.i.a(this.a);
        com.xiaomi.onetrack.util.ac.a(com.xiaomi.onetrack.util.t.a(this.b.f), this.a);
    }
}
