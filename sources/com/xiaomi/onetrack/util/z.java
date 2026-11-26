package com.xiaomi.onetrack.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class z {
    private static final String A = "/track/key_get";
    private static final String B = "/api/v4/detail/config";
    private static final String C = "/api/v4/detail/config_common";
    private static final String D = "/api/v1/token";
    private static final String a = "RegionDomainManager";
    private static final String b = "CN";
    private static final String c = "INTL";
    private static final String d = "IN";
    private static final String e = "RU";
    private static final String f = "http://";
    private static final String g = "https://";
    private static String h = "";
    private static String i = "";
    private static String j = "";
    private static String k = "";
    private static String l = "";
    private static String m = "";
    private static String n = "";
    private static String o = "";
    private static String p = "";
    private static final String z = "/track/v4";
    private Context E;
    private static final byte[] q = {116, 114, 97, 99, 107, 105, 110, 103, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] r = {116, 114, 97, 99, 107, 105, 110, 103, 46, 105, 110, 116, 108, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] s = {116, 114, 97, 99, 107, 105, 110, 103, 46, 114, 117, 115, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] t = {116, 114, 97, 99, 107, 105, 110, 103, 46, 105, 110, 100, 105, 97, 46, 109, 105, 117, 105, 46, 99, 111, 109};
    private static final byte[] u = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] v = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 105, 110, 116, 108, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] w = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 105, 110, 100, 105, 97, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] x = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 114, 117, 115, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static final byte[] y = {115, 100, 107, 99, 111, 110, 102, 105, 103, 46, 101, 117, 46, 120, 105, 97, 111, 109, 105, 46, 99, 111, 109};
    private static ConcurrentHashMap<String, String> F = new ConcurrentHashMap<>();

    private z() {
        g();
        F.put("IN", k);
        F.put("RU", j);
        h();
    }

    private void g() {
        h = a(q);
        i = a(r);
        j = a(s);
        k = a(t);
        l = a(u);
        m = a(v);
        n = a(w);
        o = a(x);
        p = a(y);
    }

    public String a(byte[] bArr) {
        String str = "";
        try {
            String str2 = new String(bArr, "UTF-8");
            try {
                r.a(a, "transmitToString host:".concat(str2));
                return str2;
            } catch (Exception e2) {
                e = e2;
                str = str2;
                r.b(a, e.getMessage());
                return str;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private static class a {
        private static final z a = new z();

        private a() {
        }
    }

    public static z a() {
        return a.a;
    }

    private void h() {
        try {
            String strH = ac.h();
            if (TextUtils.isEmpty(strH)) {
                return;
            }
            a(new JSONObject(strH));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public synchronized void a(JSONObject jSONObject) {
        r.a(a, "updateHostMap:" + jSONObject.toString());
        if (jSONObject == null) {
            return;
        }
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String strOptString = jSONObject.optString(next);
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(strOptString)) {
                    F.put(next, strOptString);
                }
            }
            ac.b(new JSONObject(F).toString());
        } catch (Exception e2) {
            r.a(a, "updateHostMap: " + e2.toString());
        }
        r.a(a, "merge config:" + new JSONObject(F).toString());
    }

    public String b() {
        try {
            if (TextUtils.isEmpty(ac.l())) {
                com.xiaomi.onetrack.b.e.b();
            }
        } catch (Exception e2) {
            r.a(a, "getTrackingUrl: " + e2.toString());
        }
        return a(i(), j(), z);
    }

    public String c() {
        return a(i(), k(), B);
    }

    public String d() {
        return a(i(), k(), C);
    }

    public String e() {
        return a(i(), j(), A);
    }

    public String a(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    private String i() {
        return g;
    }

    private String j() {
        return a(s.k(), s.l());
    }

    public String f() {
        try {
            if (TextUtils.isEmpty(ac.l())) {
                com.xiaomi.onetrack.b.e.b();
            }
        } catch (Exception e2) {
            r.a(a, "getTrackingUrl: " + e2.toString());
        }
        return a(i(), k(), D);
    }

    private String a(boolean z2, String str) {
        if (!z2) {
            return h;
        }
        String str2 = F.get(str);
        return TextUtils.isEmpty(str2) ? i : str2;
    }

    private String k() {
        boolean zK = s.k();
        String strL = s.l();
        if (!zK) {
            return l;
        }
        if (TextUtils.equals(strL, "IN")) {
            return n;
        }
        if (TextUtils.equals(strL, "RU")) {
            return o;
        }
        try {
            if (com.xiaomi.onetrack.util.a.j.contains(strL.toUpperCase())) {
                return p;
            }
            return m;
        } catch (Exception e2) {
            r.b(a, "getSdkConfigHost error: ", e2);
            return m;
        }
    }
}
