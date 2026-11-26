package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class u implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ s e;

    u(s sVar, String str, String str2, String str3, String str4) {
        this.e = sVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.e.c(this.a, this.b, this.c, this.d);
    }
}
