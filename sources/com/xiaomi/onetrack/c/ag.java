package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class ag implements Runnable {
    final /* synthetic */ ae a;

    ag(ae aeVar) {
        this.a = aeVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.i.get()) {
            com.xiaomi.onetrack.b.e.b();
        }
        this.a.i.set(true);
    }
}
