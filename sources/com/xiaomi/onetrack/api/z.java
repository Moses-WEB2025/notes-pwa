package com.xiaomi.onetrack.api;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class z implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;

    z(c cVar, String str) {
        this.b = cVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.b.h()) {
                return;
            }
            JSONArray jSONArray = new JSONArray(this.a);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject == null || !jSONObjectOptJSONObject.has(ai.b) || !jSONObjectOptJSONObject.has("B")) {
                    com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "h5 json is empty or has no \"H\" or \"B\" ");
                } else {
                    aj ajVar = new aj(jSONObjectOptJSONObject.optJSONObject(ai.b));
                    if (!this.b.e(ajVar.a())) {
                        this.b.i().a(ajVar.a(), ai.a(ajVar, jSONObjectOptJSONObject.optJSONObject("B"), this.b.f, this.b.h, this.b.f(ajVar.a()), this.b.i, this.b.j));
                    }
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("BaseOneTrackImp", "trackEventFromH5 error: " + e.toString());
        }
    }
}
