package com.xiaomi.ai.streamplayer;

/* JADX INFO: loaded from: classes4.dex */
class c extends Thread {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.a.d();
    }
}
