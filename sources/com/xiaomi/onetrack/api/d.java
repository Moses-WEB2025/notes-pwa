package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.CrashAnalysis;

/* JADX INFO: loaded from: classes4.dex */
class d implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ c b;

    d(c cVar, Context context) {
        this.b = cVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (CrashAnalysis.getInstance(this.a, this.b).isSupport()) {
            return;
        }
        this.b.e = new al(this.b);
        this.b.e.a();
    }
}
