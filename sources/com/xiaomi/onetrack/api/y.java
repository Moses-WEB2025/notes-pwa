package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
class y implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Map b;
    final /* synthetic */ List c;
    final /* synthetic */ c d;

    y(c cVar, String str, Map map, List list) {
        this.d = cVar;
        this.a = str;
        this.b = map;
        this.c = list;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (TextUtils.isEmpty(this.d.f.getAdEventAppId())) {
                com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "adEventAppId is null,Please configure,event name:" + this.a);
                return;
            }
            if (!this.d.h() && !this.d.d(this.a)) {
                this.d.i().a(this.a, ai.a(this.a, com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.b, true), this.d.f, this.d.h, this.d.f(this.a), this.d.i, com.xiaomi.onetrack.util.t.a(this.c), this.d.j));
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "track map error: " + e.toString());
        }
    }
}
