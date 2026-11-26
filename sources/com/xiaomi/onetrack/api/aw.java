package com.xiaomi.onetrack.api;

import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.DeviceUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class aw implements ak {
    private static final String a = "PubSubTrackLocalImp";
    private static final int b = 102400;
    private static final int c = 512000;
    private static final int d = 2;
    private Configuration e;
    private com.xiaomi.onetrack.util.x f;

    public aw(Configuration configuration, com.xiaomi.onetrack.util.x xVar) {
        this.e = configuration;
        this.f = xVar;
        com.xiaomi.onetrack.util.j.a(new ax(this, configuration));
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(String str, String str2) {
        JSONObject jSONObject;
        JSONObject jSONObjectOptJSONObject;
        JSONObject jSONObjectOptJSONObject2 = null;
        try {
            jSONObject = new JSONObject(str2);
            try {
                jSONObjectOptJSONObject = jSONObject.optJSONObject(ai.b);
                try {
                    jSONObjectOptJSONObject2 = jSONObject.optJSONObject("B");
                } catch (JSONException e) {
                    e = e;
                    com.xiaomi.onetrack.util.r.a(a, " data JSONException e:" + e.getMessage());
                }
            } catch (JSONException e2) {
                e = e2;
                jSONObjectOptJSONObject = null;
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = null;
            jSONObjectOptJSONObject = null;
        }
        JSONObject jSONObject2 = jSONObjectOptJSONObject2;
        JSONObject jSONObject3 = jSONObject;
        JSONObject jSONObject4 = jSONObjectOptJSONObject;
        boolean zA = a(jSONObject4);
        com.xiaomi.onetrack.util.x xVar = this.f;
        if (xVar != null && !xVar.a(str) && !zA) {
            com.xiaomi.onetrack.util.r.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
            return;
        }
        if (a(str, str2, zA)) {
            if (!com.xiaomi.onetrack.c.j.b()) {
                com.xiaomi.onetrack.util.r.a(a, "track network is unaccessable, cta");
                com.xiaomi.onetrack.c.j.a(str, str2);
                return;
            }
            com.xiaomi.onetrack.c.j.a(this);
            if (com.xiaomi.onetrack.util.r.a && !str.equalsIgnoreCase("onetrack_bug_report")) {
                com.xiaomi.onetrack.util.r.a(a, "track data:" + str2);
            }
            if (zA) {
                com.xiaomi.onetrack.b.a.a().b(this.e.getAdEventAppId());
            }
            com.xiaomi.onetrack.b.a.a().b(this.e.getAppId());
            a(str, jSONObject3, jSONObject4, jSONObject2, zA);
        }
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(int i) {
        if (i == 2) {
            com.xiaomi.onetrack.c.a.a(new ay(this));
        }
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(boolean z) {
        if (an.k) {
            return;
        }
        com.xiaomi.onetrack.c.j.a(this);
    }

    public boolean a(String str, String str2, boolean z) {
        if (OneTrack.isDisable() || OneTrack.isUseSystemNetTrafficOnly()) {
            com.xiaomi.onetrack.util.r.a(a, "Tracking data is disabled or onetrack use system net traffic only, skip it.");
            return false;
        }
        Configuration configuration = this.e;
        if (configuration == null || TextUtils.isEmpty(configuration.getProjectId()) || TextUtils.isEmpty(this.e.getPrivateKeyId())) {
            com.xiaomi.onetrack.util.r.a(a, "projectId or pKeyId is empty, so return");
            return false;
        }
        if (str != null && str.equals("onetrack_bug_report")) {
            return true;
        }
        if (z) {
            if (str2 != null && str2.length() > c) {
                com.xiaomi.onetrack.util.r.a(a, "ad Event size exceed limitation!");
                return false;
            }
        } else if (str2 != null && str2.length() * 2 > b) {
            com.xiaomi.onetrack.util.r.a(a, "Event size exceed limitation!");
            return false;
        }
        return true;
    }

    private void a(String str, JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, boolean z) {
        String strA = "";
        String appId = this.e.getAppId();
        if (z) {
            appId = this.e.getAdEventAppId();
        }
        if (a(appId)) {
            com.xiaomi.onetrack.util.r.a(a, "This app disabled tracking data, skip it.");
            return;
        }
        try {
            String strA2 = com.xiaomi.onetrack.b.h.a().a(appId, str, com.xiaomi.onetrack.b.a.f, "");
            String strOptString = jSONObject3 == null ? "" : jSONObject3.optString(ah.ab);
            com.xiaomi.onetrack.util.r.a(a, "tip: " + strOptString + ", needIds: " + strA2);
            if (b(strOptString, strA2)) {
                if (c(appId, str)) {
                    com.xiaomi.onetrack.util.r.a(a, " This event disabled tracking data , skip it.");
                    return;
                } else {
                    if (d(appId, str)) {
                        com.xiaomi.onetrack.util.r.a(a, " This event should not upload by sampling , skip it.");
                        return;
                    }
                    strA = com.xiaomi.onetrack.b.h.a().a(appId, str, com.xiaomi.onetrack.b.a.g, "");
                }
            }
            String strC = com.xiaomi.onetrack.b.h.a().c(appId);
            com.xiaomi.onetrack.util.r.a(a, "bannedParamsForApp: " + strC + ", bannedParamsForEvent: " + strA);
            Set<String> setA = com.xiaomi.onetrack.util.ab.a(strC, strA, ",");
            a(jSONObject2, setA);
            a(jSONObject3, setA);
            String projectId = this.e.getProjectId();
            String strE = com.xiaomi.onetrack.b.p.a().e(appId);
            String str2 = appId;
            Map<String, String> mapB = com.xiaomi.onetrack.util.a.b();
            if (z) {
                com.xiaomi.onetrack.f.d.b(projectId, strE, str2, com.xiaomi.onetrack.f.a.e(), str, jSONObject.toString(), mapB);
            } else {
                com.xiaomi.onetrack.f.d.a(projectId, strE, str2, com.xiaomi.onetrack.f.a.e(), str, jSONObject.toString(), mapB);
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(a, "checkCloudControl error：" + e.getMessage());
        }
    }

    private boolean b(String str, String str2) {
        List<String> listA;
        if (TextUtils.isEmpty(str2)) {
            return true;
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            String[] strArrSplit = str.split(com.xiaomi.onetrack.util.ab.a);
            return strArrSplit != null && strArrSplit.length >= 5 && (listA = com.xiaomi.onetrack.util.ab.a(str2, ",")) != null && listA.contains(strArrSplit[4]);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(a, "isMatchId error：" + e.toString());
        }
    }

    private void a(JSONObject jSONObject, Set<String> set) {
        if (jSONObject == null || set == null || set.size() == 0) {
            com.xiaomi.onetrack.util.r.a(a, "jsonObject is null or bannedParams is empty");
            return;
        }
        com.xiaomi.onetrack.util.r.a(a, "jsonObject: " + jSONObject.toString() + ", bannedParams: " + set.toString());
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                if (set.contains(itKeys.next())) {
                    itKeys.remove();
                }
            }
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(a, "filterParams error：" + e.toString());
        }
    }

    private boolean a(String str) {
        try {
            return com.xiaomi.onetrack.b.h.a().a(str, com.xiaomi.onetrack.b.a.a);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(a, "isDisableTrackForApp error: " + e.toString());
            return false;
        }
    }

    private boolean c(String str, String str2) {
        try {
            return com.xiaomi.onetrack.b.h.a().a(str, str2, com.xiaomi.onetrack.b.a.a, false);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(a, "isDisableTrackForEvent error: " + e.toString());
            return false;
        }
    }

    private boolean d(String str, String str2) {
        long jB = com.xiaomi.onetrack.b.h.a().b(str, str2);
        long jAbs = Math.abs(DeviceUtil.b(com.xiaomi.onetrack.f.a.b()).hashCode()) % 100;
        boolean z = jB > jAbs;
        com.xiaomi.onetrack.util.r.a(a, "shouldUploadBySampling " + str2 + ",  shouldUpload=" + z + ", sample=" + jB + ", val=" + jAbs);
        return !z;
    }

    private boolean a(JSONObject jSONObject) {
        try {
            return jSONObject.optBoolean(com.xiaomi.onetrack.f.b.C0122b.D, false);
        } catch (Throwable unused) {
            com.xiaomi.onetrack.util.r.a(a, "");
            return false;
        }
    }
}
