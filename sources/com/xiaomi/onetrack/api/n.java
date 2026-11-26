package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class n implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ c b;

    n(c cVar, Map map) {
        this.b = cVar;
        this.a = map;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            JSONObject jSONObjectA = com.xiaomi.onetrack.util.t.a((Map<String, Object>) this.a, true);
            String strA = com.xiaomi.onetrack.util.m.a(com.xiaomi.onetrack.util.t.a(this.b.f));
            com.xiaomi.onetrack.util.m.a(com.xiaomi.onetrack.util.t.a(this.b.f), com.xiaomi.onetrack.util.t.a(jSONObjectA, !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null).toString());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", StringUtils.SPACE + e.toString());
        }
    }
}
