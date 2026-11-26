package com.xiaomi.ai.streamplayer;

/* JADX INFO: loaded from: classes4.dex */
class h implements Runnable {
    final /* synthetic */ f a;

    h(f fVar) {
        this.a = fVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.f();
        synchronized (this) {
            this.a.b.release();
            this.a.f = true;
        }
    }
}
