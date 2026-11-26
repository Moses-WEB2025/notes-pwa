package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class t implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ s d;

    t(s sVar, String str, String str2, String str3) {
        this.d = sVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.d.c(this.a, this.b, this.c, null);
    }
}
