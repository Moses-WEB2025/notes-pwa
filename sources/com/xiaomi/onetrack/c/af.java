package com.xiaomi.onetrack.c;

/* JADX INFO: loaded from: classes4.dex */
class af implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ ae b;

    af(ae aeVar, boolean z) {
        this.b = aeVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a();
        try {
            if (this.a) {
                int[] iArr = {0, 1, 2};
                for (int i = 0; i < 3; i++) {
                    int i2 = iArr[i];
                    int iA = com.xiaomi.onetrack.b.n.a(i2);
                    if (!this.b.hasMessages(i2)) {
                        this.b.sendEmptyMessageDelayed(i2, iA);
                    }
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.a("UploadTimer", "netReceiver error: " + e);
        }
    }
}
