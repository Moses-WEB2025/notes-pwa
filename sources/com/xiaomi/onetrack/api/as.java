package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class as implements Runnable {
    final /* synthetic */ aq a;

    as(aq aqVar) {
        this.a = aqVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.onetrack.c.j.b()) {
            this.a.b();
        }
    }
}
