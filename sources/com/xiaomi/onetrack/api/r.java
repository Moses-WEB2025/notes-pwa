package com.xiaomi.onetrack.api;

/* JADX INFO: loaded from: classes4.dex */
class r implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    r(c cVar, String str) {
        this.b = cVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.xiaomi.onetrack.util.q.a().a(this.a);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "setInstanceId error: " + e.toString());
        }
    }
}
