package com.xiaomi.onetrack.api;

import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class aa implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;
    final /* synthetic */ String f;
    final /* synthetic */ long g;
    final /* synthetic */ c h;

    aa(c cVar, Map map, String str, String str2, String str3, String str4, String str5, long j) {
        this.h = cVar;
        this.a = map;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.f = str5;
        this.g = j;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.h.h()) {
                return;
            }
            this.h.i().a("onetrack_bug_report", ai.a(this.b, this.c, this.d, this.e, this.f, this.g, this.h.f, this.h.h, this.h.d((Map<String, Object>) this.a), this.h.i, this.h.j));
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "trackException error: " + e.toString());
        }
    }
}
