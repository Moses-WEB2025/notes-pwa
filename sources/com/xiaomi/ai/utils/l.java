package com.xiaomi.ai.utils;

import com.xiaomi.ai.HTTPCallback;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class l implements Runnable {
    final /* synthetic */ HTTPCallback a;
    final /* synthetic */ String b;
    final /* synthetic */ Map c;
    final /* synthetic */ String d;
    final /* synthetic */ k e;

    l(k kVar, HTTPCallback hTTPCallback, String str, Map map, String str2) {
        this.e = kVar;
        this.a = hTTPCallback;
        this.b = str;
        this.c = map;
        this.d = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        f.a(this.a, this.b, this.c, this.d);
    }
}
