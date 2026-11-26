package com.xiaomi.ai.streamplayer;

/* JADX INFO: loaded from: classes4.dex */
abstract class a {

    /* JADX INFO: renamed from: com.xiaomi.ai.streamplayer.a$a, reason: collision with other inner class name */
    static class C0118a {
        int a;
        int b;
        int c;
        boolean d;
        byte[] e;

        C0118a() {
        }

        boolean a(C0118a c0118a) {
            return c0118a != null && c0118a.a == this.a && c0118a.c == this.c && c0118a.b == this.b;
        }
    }

    a() {
    }

    abstract void a();

    abstract void a(byte[] bArr, int i, int i2);

    abstract void b();

    abstract void c();

    abstract void d();

    abstract C0118a e();
}
