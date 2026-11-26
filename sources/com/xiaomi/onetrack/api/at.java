package com.xiaomi.onetrack.api;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class at implements ak, az.a {
    private static final String a = "PubSubSystemImp";
    private static final int b = 102400;
    private static final int c = 512000;
    private static final int d = 2;
    private Configuration e;
    private az f;
    private com.xiaomi.onetrack.util.x g;

    public at(Configuration configuration, com.xiaomi.onetrack.util.x xVar) {
        this.e = configuration;
        this.g = xVar;
        az azVarA = az.a();
        this.f = azVarA;
        azVarA.a(this);
        com.xiaomi.onetrack.util.j.a(new au(this, configuration));
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(String str, String str2) {
        boolean zA = a(str2);
        com.xiaomi.onetrack.util.x xVar = this.g;
        if (xVar != null && !xVar.a(str) && !zA) {
            com.xiaomi.onetrack.util.r.a(a, "The privacy policy is not permitted, and the event is not basic or recommend event or custom dau event, skip it.");
            return;
        }
        if (a(str, str2, zA)) {
            if (!com.xiaomi.onetrack.c.j.b()) {
                if (!ah.i.equalsIgnoreCase(str)) {
                    com.xiaomi.onetrack.c.j.a(str, str2);
                    return;
                }
            } else {
                com.xiaomi.onetrack.c.j.a(this);
            }
            if (com.xiaomi.onetrack.util.r.a) {
                com.xiaomi.onetrack.util.r.a(a, "track name:" + str + " data :" + str2 + " tid" + Process.myTid());
            }
            String appId = this.e.getAppId();
            if (zA) {
                appId = this.e.getAdEventAppId();
            }
            String str3 = appId;
            String strE = com.xiaomi.onetrack.b.p.a().e(str3);
            String strA = com.xiaomi.onetrack.util.a.a();
            if (this.f.a(this.e.getProjectId(), strE, this.e.getPrivateKeyId(), str3, str, str2, strA)) {
                return;
            }
            com.xiaomi.onetrack.c.s.a().a(str3, str, str2, strA);
            if (com.xiaomi.onetrack.util.r.a) {
                com.xiaomi.onetrack.util.r.a(a, "track mIOneTrackService is null! SystemImpCacheManager cache data:" + str2);
            }
        }
    }

    private boolean a(String str, String str2, boolean z) {
        if (OneTrack.isDisable()) {
            com.xiaomi.onetrack.util.r.a(a, "isDisable");
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

    @Override // com.xiaomi.onetrack.api.ak
    public void a(int i) {
        this.f.a(i);
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(boolean z) {
        if (an.k) {
            return;
        }
        com.xiaomi.onetrack.c.j.a(this);
    }

    @Override // com.xiaomi.onetrack.api.az.a
    public void a() {
        com.xiaomi.onetrack.util.j.a(new av(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        try {
            com.xiaomi.onetrack.c.s.a().b(this.e.getProjectId(), com.xiaomi.onetrack.b.p.a().e(this.e.getAppId()), this.e.getPrivateKeyId(), this.e.getAppId());
            if (TextUtils.isEmpty(this.e.getAdEventAppId())) {
                return;
            }
            com.xiaomi.onetrack.c.s.a().b(this.e.getProjectId(), com.xiaomi.onetrack.b.p.a().e(this.e.getAdEventAppId()), this.e.getPrivateKeyId(), this.e.getAdEventAppId());
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.a(a, "trackCachedEvents: " + e.toString());
        }
    }

    private boolean a(String str) {
        try {
            return new JSONObject(str).optJSONObject(ai.b).optBoolean(com.xiaomi.onetrack.f.b.C0122b.D, false);
        } catch (Throwable unused) {
            com.xiaomi.onetrack.util.r.a(a, "");
            return false;
        }
    }
}
