package com.xiaomi.onetrack.f;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.ai;
import com.xiaomi.onetrack.api.aj;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.ab;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ae;
import com.xiaomi.onetrack.util.q;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.x;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class b {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;
    private static final String d = "Event";
    private long e;
    private String f;
    private String g;
    private String h;
    private int i;
    private JSONObject j;
    private long k;

    /* JADX INFO: renamed from: com.xiaomi.onetrack.f.b$b, reason: collision with other inner class name */
    public static class C0122b {
        public static String A = "ot_test_env";
        public static String B = "ot_privacy_policy";
        public static String C = "market_name";
        public static String D = "ot_ad";
        public static String E = "ot_basic_mode";
        public static String F = "ot_ad_monitor";
        public static String G = "ot_hybrid_type";
        public static String H = "ot_mi_os";
        public static String I = "ot_device_type";
        public static String a = "event";
        public static String b = "oaid";
        public static String c = "gaid";
        public static String d = "android_id";
        public static String e = "instance_id";
        public static String f = "mfrs";
        public static String g = "model";
        public static String h = "platform";
        public static String i = "miui";
        public static String j = "build";
        public static String k = "os_ver";
        public static String l = "app_id";
        public static String m = "app_ver";
        public static String n = "pkg";
        public static String o = "channel";
        public static String p = "e_ts";
        public static String q = "tz";
        public static String r = "net";
        public static String s = "region";
        public static String t = "plugin_id";
        public static String u = "sdk_ver";
        public static String v = "uid";
        public static String w = "uid_type";
        public static String x = "sid";
        public static String y = "sdk_mode";
        public static String z = "ot_first_day";
    }

    public b() {
    }

    public long b() {
        return this.e;
    }

    public void a(long j) {
        this.e = j;
    }

    public String c() {
        return this.f;
    }

    public void a(String str) {
        this.f = str;
    }

    public String d() {
        return this.g;
    }

    public void b(String str) {
        this.g = str;
    }

    public String e() {
        return this.h;
    }

    public void c(String str) {
        this.h = str;
    }

    public int f() {
        return this.i;
    }

    public void a(int i) {
        this.i = i;
    }

    public JSONObject g() {
        return this.j;
    }

    public void b(JSONObject jSONObject) {
        this.j = jSONObject;
    }

    public long h() {
        return this.k;
    }

    public void b(long j) {
        this.k = j;
    }

    public boolean i() {
        try {
            JSONObject jSONObject = this.j;
            return (jSONObject == null || !jSONObject.has(ai.b) || !this.j.has("B") || TextUtils.isEmpty(this.f) || TextUtils.isEmpty(this.g)) ? false : true;
        } catch (Exception e) {
            r.b("Event", "check event isValid error, ", e);
            return false;
        }
    }

    private b(a aVar) {
        this.e = aVar.a;
        this.f = aVar.b;
        this.g = aVar.c;
        this.h = aVar.d;
        this.i = aVar.e;
        this.j = aVar.f;
        this.k = aVar.g;
    }

    public static class a {
        private long a;
        private String b;
        private String c;
        private String d;
        private int e;
        private JSONObject f;
        private long g;

        public a a(long j) {
            this.a = this.a;
            return this;
        }

        public a a(String str) {
            this.b = str;
            return this;
        }

        public a b(String str) {
            this.c = str;
            return this;
        }

        public a c(String str) {
            this.d = str;
            return this;
        }

        public a a(int i) {
            this.e = i;
            return this;
        }

        public a a(JSONObject jSONObject) {
            this.f = jSONObject;
            return this;
        }

        public a b(long j) {
            this.g = j;
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    public static JSONObject a(String str, Configuration configuration, OneTrack.IEventHook iEventHook, x xVar, boolean z, boolean z2) throws JSONException {
        return a(str, configuration, iEventHook, "", xVar, z, z2);
    }

    public static JSONObject a(String str, Configuration configuration, OneTrack.IEventHook iEventHook, String str2, x xVar, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Context contextB = com.xiaomi.onetrack.f.a.b();
        jSONObject.put(C0122b.a, str);
        boolean zA = a(z);
        if (!zA) {
            if (s.b() ? s.k() : configuration.isInternational()) {
                if (iEventHook != null && iEventHook.isRecommendEvent(str)) {
                    String strB = DeviceUtil.b(contextB);
                    if (!TextUtils.isEmpty(strB)) {
                        jSONObject.put(C0122b.c, strB);
                    }
                }
            } else {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(contextB);
                jSONObject.put(C0122b.b, strA);
                if (!ab.b(strA)) {
                    jSONObject.put(C0122b.d, DeviceUtil.f(contextB));
                }
            }
            jSONObject.put(C0122b.e, q.a().b());
            a(jSONObject, configuration, str2);
            a(jSONObject, contextB);
            jSONObject.put(C0122b.x, s.h());
        }
        jSONObject.put(C0122b.f, DeviceUtil.d());
        jSONObject.put(C0122b.g, DeviceUtil.b());
        jSONObject.put(C0122b.h, "Android");
        jSONObject.put(C0122b.I, DeviceUtil.h());
        jSONObject.put(C0122b.i, s.e());
        jSONObject.put(C0122b.H, s.f());
        jSONObject.put(C0122b.j, s.d());
        jSONObject.put(C0122b.k, s.g());
        jSONObject.put(C0122b.m, com.xiaomi.onetrack.f.a.c());
        jSONObject.put(C0122b.p, System.currentTimeMillis());
        jSONObject.put(C0122b.q, s.c());
        jSONObject.put(C0122b.r, com.xiaomi.onetrack.g.c.a(contextB).toString());
        String strL = s.l();
        com.xiaomi.onetrack.b.a.a().d(strL);
        jSONObject.put(C0122b.s, strL);
        jSONObject.put(C0122b.u, BuildConfig.SDK_VERSION);
        if (z2) {
            jSONObject.put(C0122b.l, configuration.getAdEventAppId());
        } else {
            jSONObject.put(C0122b.l, configuration.getAppId());
        }
        jSONObject.put(C0122b.D, z2);
        jSONObject.put(C0122b.n, com.xiaomi.onetrack.f.a.e());
        jSONObject.put(C0122b.o, !TextUtils.isEmpty(configuration.getChannel()) ? configuration.getChannel() : "default");
        jSONObject.put(C0122b.y, (configuration.getMode() != null ? configuration.getMode() : OneTrack.Mode.APP).getType());
        jSONObject.put(C0122b.z, ae.d(ac.B()));
        if (r.c) {
            jSONObject.put(C0122b.A, true);
        }
        jSONObject.put(C0122b.B, xVar.a());
        jSONObject.put(C0122b.C, DeviceUtil.c());
        jSONObject.put(C0122b.E, zA);
        return jSONObject;
    }

    public static JSONObject a(aj ajVar, Configuration configuration, OneTrack.IEventHook iEventHook, x xVar, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Context contextB = com.xiaomi.onetrack.f.a.b();
        jSONObject.put(C0122b.a, ajVar.a());
        boolean zA = a(z);
        if (!zA) {
            if (s.b() ? s.k() : configuration.isInternational()) {
                if (iEventHook != null && iEventHook.isRecommendEvent(ajVar.a())) {
                    String strB = DeviceUtil.b(contextB);
                    if (!TextUtils.isEmpty(strB)) {
                        jSONObject.put(C0122b.c, strB);
                    }
                }
            } else {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(contextB);
                jSONObject.put(C0122b.b, strA);
                if (!ab.b(strA)) {
                    jSONObject.put(C0122b.d, DeviceUtil.f(contextB));
                }
            }
            jSONObject.put(C0122b.e, q.a().b());
            jSONObject.put(C0122b.t, configuration.getPluginId());
            if (!TextUtils.isEmpty(ajVar.e()) && !TextUtils.isEmpty(ajVar.f())) {
                jSONObject.put(C0122b.v, ajVar.e());
                jSONObject.put(C0122b.w, ajVar.f());
            }
            jSONObject.put(C0122b.x, s.h());
        }
        jSONObject.put(C0122b.D, z2);
        jSONObject.put(C0122b.f, DeviceUtil.d());
        jSONObject.put(C0122b.g, DeviceUtil.b());
        jSONObject.put(C0122b.h, "Android");
        jSONObject.put(C0122b.I, DeviceUtil.h());
        jSONObject.put(C0122b.i, s.e());
        jSONObject.put(C0122b.H, s.f());
        jSONObject.put(C0122b.j, s.d());
        jSONObject.put(C0122b.k, s.g());
        jSONObject.put(C0122b.m, com.xiaomi.onetrack.f.a.c());
        jSONObject.put(C0122b.p, ajVar.b());
        jSONObject.put(C0122b.q, s.c());
        jSONObject.put(C0122b.r, com.xiaomi.onetrack.g.c.a(contextB).toString());
        jSONObject.put(C0122b.s, s.l());
        jSONObject.put(C0122b.u, BuildConfig.SDK_VERSION);
        jSONObject.put(C0122b.l, ajVar.c());
        jSONObject.put(C0122b.n, com.xiaomi.onetrack.f.a.e());
        jSONObject.put(C0122b.o, !TextUtils.isEmpty(ajVar.d()) ? ajVar.d() : "default");
        jSONObject.put(C0122b.y, (configuration.getMode() != null ? configuration.getMode() : OneTrack.Mode.APP).getType());
        jSONObject.put(C0122b.z, ae.d(ac.B()));
        if (r.c) {
            jSONObject.put(C0122b.A, true);
        }
        jSONObject.put(C0122b.B, xVar.a());
        jSONObject.put(C0122b.C, DeviceUtil.c());
        jSONObject.put(C0122b.E, zA);
        jSONObject.put(C0122b.G, "JS");
        return jSONObject;
    }

    private static void a(JSONObject jSONObject, Context context) throws JSONException {
        String strU = ac.u();
        String strW = ac.w();
        if (TextUtils.isEmpty(strU) || TextUtils.isEmpty(strW)) {
            return;
        }
        jSONObject.put(C0122b.v, strU);
        jSONObject.put(C0122b.w, strW);
    }

    private static void a(JSONObject jSONObject, Configuration configuration, String str) throws JSONException {
        if (!TextUtils.isEmpty(str)) {
            jSONObject.put(C0122b.t, str);
        } else {
            jSONObject.put(C0122b.t, configuration.getPluginId());
        }
    }

    public static boolean a(boolean z) {
        if (OneTrack.getGlobalBasicModeEnable() == 0) {
            return z;
        }
        return OneTrack.getGlobalBasicModeEnable() != 1 && OneTrack.getGlobalBasicModeEnable() == 2;
    }
}
