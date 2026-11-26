package com.xiaomi.onetrack.api;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class aq implements ak, az.a {
    private static final String a = "OneTrackSystemImp";
    private static final int b = 102400;
    private static final int c = 512000;
    private static final int d = 2;
    private final ConcurrentHashMap<String, String> e = new ConcurrentHashMap<>();
    private Configuration f;
    private az g;
    private com.xiaomi.onetrack.util.x h;

    public aq(Configuration configuration, com.xiaomi.onetrack.util.x xVar) {
        this.f = configuration;
        this.h = xVar;
        az azVarA = az.a();
        this.g = azVarA;
        azVarA.a(this);
        com.xiaomi.onetrack.util.j.a(new ar(this));
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(String str, String str2) {
        boolean zA = a(str2);
        com.xiaomi.onetrack.util.x xVar = this.h;
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
            String appId = this.f.getAppId();
            if (zA) {
                appId = this.f.getAdEventAppId();
            }
            if (this.g.a(str, str2, appId)) {
                return;
            }
            com.xiaomi.onetrack.c.s.a().a(appId, str, str2);
            if (com.xiaomi.onetrack.util.r.a) {
                com.xiaomi.onetrack.util.r.a(a, "track mIOneTrackService is null! SystemImpCacheManager cache data:" + str2);
            }
        }
    }

    private boolean a(String str, String str2, boolean z) {
        if (OneTrack.isDisable()) {
            return false;
        }
        boolean z2 = str != null && str.equals("onetrack_bug_report");
        if (z || z2) {
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
        this.g.a(i);
    }

    @Override // com.xiaomi.onetrack.api.ak
    public void a(boolean z) {
        if (an.k) {
            com.xiaomi.onetrack.c.j.a(this);
        }
    }

    @Override // com.xiaomi.onetrack.api.az.a
    public void a() {
        com.xiaomi.onetrack.util.j.a(new as(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        try {
            com.xiaomi.onetrack.c.s.a().c(this.f.getAppId());
            if (TextUtils.isEmpty(this.f.getAdEventAppId())) {
                return;
            }
            com.xiaomi.onetrack.c.s.a().c(this.f.getAdEventAppId());
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
