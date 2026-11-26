package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class m implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ c b;

    m(c cVar, boolean z) {
        this.b = cVar;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.b.h()) {
                return;
            }
            if (!this.b.f.isAutoTrackActivityAction()) {
                com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "config.autoTrackActivityAction is false, ignore onetrack_pa pause event");
                return;
            }
            String strZ = com.xiaomi.onetrack.util.ac.z();
            if (TextUtils.isEmpty(strZ)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(strZ);
            this.b.i().a(ah.g, jSONObject.put("B", jSONObject.optJSONObject("B").put(ah.u, this.a)).toString());
            if (com.xiaomi.onetrack.util.r.a) {
                com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "trackPageEndAuto");
            }
            com.xiaomi.onetrack.util.ac.i("");
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "trackPageEndAuto error:" + e.toString());
        }
    }
}
