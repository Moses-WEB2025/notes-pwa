package com.xiaomi.onetrack.c;

import com.xiaomi.onetrack.api.ak;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
final class l implements Runnable {
    final /* synthetic */ ak a;

    l(ak akVar) {
        this.a = akVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            List<JSONObject> listC = j.c();
            if (listC != null && listC.size() > 0) {
                com.xiaomi.onetrack.util.r.a("NetworkAccessManager", "cache file has " + listC.size() + " events, start to upload");
                for (JSONObject jSONObject : listC) {
                    this.a.a(jSONObject.optString(j.b), jSONObject.optString("data"));
                }
            }
            j.c(true);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b("NetworkAccessManager", "cta event error: " + e.getMessage());
        }
        boolean unused = j.k = false;
    }
}
