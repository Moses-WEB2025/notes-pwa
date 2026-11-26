package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class q implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    q(c cVar, String str) {
        this.b = cVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            String strA = com.xiaomi.onetrack.util.m.a(com.xiaomi.onetrack.util.t.a(this.b.f));
            if (TextUtils.isEmpty(strA)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strA);
            jSONObject.remove(this.a);
            com.xiaomi.onetrack.util.m.a(com.xiaomi.onetrack.util.t.a(this.b.f), jSONObject.toString());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "removeCommonProperty error:" + e.toString());
        }
    }
}
