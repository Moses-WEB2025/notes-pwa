package com.xiaomi.onetrack.b;

import android.text.TextUtils;
import com.xiaomi.onetrack.BuildConfig;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.ab;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.z;
import defpackage.R2;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class e {
    public static final String a = "l";
    private static final String b = "CommonConfigUpdater";
    private static final String c = "t";
    private static final String d = "levels";
    private static final String e = "Android";
    private static ConcurrentHashMap<Integer, Integer> f = new ConcurrentHashMap<>();
    private static final long g = 172800000;

    public static void a() {
        com.xiaomi.onetrack.util.j.a(new f());
    }

    public static void b() {
        if (e()) {
            f();
        } else {
            r.a(b, "CommonConfigUpdater Does not meet prerequisites for request");
        }
    }

    private static boolean e() {
        if (!com.xiaomi.onetrack.g.c.a()) {
            r.b(b, "net is not connected!");
            return false;
        }
        if (TextUtils.isEmpty(ac.l())) {
            return true;
        }
        long j = ac.j();
        return j < System.currentTimeMillis() || j - System.currentTimeMillis() > g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void f() {
        if (s.a(b)) {
            return;
        }
        HashMap map = new HashMap();
        try {
            if (OneTrack.getGlobalBasicModeEnable() != 2) {
                String strA = com.xiaomi.onetrack.util.oaid.a.a().a(com.xiaomi.onetrack.f.a.b());
                if (ab.b(strA)) {
                    strA = com.xiaomi.onetrack.d.d.c(strA);
                }
                map.put(m.a, strA);
            }
            map.put(m.b, s.e());
            map.put(m.c, s.d());
            map.put(m.d, s.k() ? "1" : "0");
            map.put(m.e, BuildConfig.SDK_VERSION);
            map.put(m.m, com.xiaomi.onetrack.f.a.c());
            map.put(m.f, s.g());
            map.put(m.g, DeviceUtil.b());
            map.put(m.h, s.l());
            map.put("platform", e);
            String strD = z.a().d();
            String strB = com.xiaomi.onetrack.g.b.b(strD, map, true);
            r.a(b, "url:" + strD + " response:" + strB);
            a(strB);
        } catch (IOException e2) {
            r.a(b, "requestCloudData: " + e2.toString());
        }
    }

    private static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("code") == 0) {
                String strOptString = jSONObject.optString(com.xiaomi.onetrack.g.a.e);
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                if (jSONObjectOptJSONObject != null) {
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("regionUrl");
                    if (jSONObjectOptJSONObject2 != null) {
                        z.a().a(jSONObjectOptJSONObject2);
                    }
                    ac.d(jSONObjectOptJSONObject.toString());
                    ac.c(strOptString);
                }
                ac.j(System.currentTimeMillis() + 86400000 + ((long) new Random().nextInt(86400000)));
            }
        } catch (JSONException e2) {
            r.a(b, "saveCommonCloudData: " + e2.toString());
        }
    }

    public static Map<Integer, Integer> c() {
        try {
            if (!f.isEmpty()) {
                return f;
            }
            String strL = ac.l();
            if (!TextUtils.isEmpty(strL)) {
                JSONArray jSONArrayOptJSONArray = new JSONObject(strL).optJSONArray(d);
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArrayOptJSONArray.getJSONObject(i);
                    int iOptInt = jSONObject.optInt(a);
                    int iOptInt2 = jSONObject.optInt("t");
                    if (iOptInt > 0 && iOptInt2 > 0) {
                        f.put(Integer.valueOf(iOptInt), Integer.valueOf(iOptInt2));
                    }
                }
            }
            return f.isEmpty() ? g() : f;
        } catch (Exception e2) {
            r.a(b, "getLevelIntervalConfig: " + e2.toString());
        }
    }

    private static HashMap<Integer, Integer> g() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 1000);
        map.put(2, Integer.valueOf(R2.layout.passport_fragment_phone_account_login));
        map.put(3, 900000);
        return map;
    }
}
