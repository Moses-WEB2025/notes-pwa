package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class am implements Runnable {
    final /* synthetic */ Thread a;
    final /* synthetic */ Throwable b;
    final /* synthetic */ al c;

    am(al alVar, Thread thread, Throwable th) {
        this.c = alVar;
        this.a = thread;
        this.b = th;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        this.c.a(this.a, this.b);
    }
}
