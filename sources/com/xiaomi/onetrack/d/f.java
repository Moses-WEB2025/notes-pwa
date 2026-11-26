package com.xiaomi.onetrack.d;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.z;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class f {
    public static final JSONObject a = new JSONObject();
    private static final String b = "SecretKeyManager";
    private static final String c = "secretKey";
    private static final String d = "sid";
    private static final String e = "key";
    private Context f;
    private JSONObject g;
    private String[] h;

    private f() {
        this.g = null;
        this.h = new String[2];
        this.f = com.xiaomi.onetrack.f.a.a();
    }

    private static final class a {
        private static final f a = new f();

        private a() {
        }
    }

    public static f a() {
        return a.a;
    }

    public synchronized String[] b() {
        JSONObject jSONObjectE = e();
        this.h[0] = jSONObjectE != null ? jSONObjectE.optString("key") : "";
        this.h[1] = jSONObjectE != null ? jSONObjectE.optString(d) : "";
        d();
        return this.h;
    }

    private void d() {
        if (r.a) {
            if (!TextUtils.isEmpty(this.h[0]) && !TextUtils.isEmpty(this.h[1])) {
                r.a(b, "key  and sid is valid! ");
            } else {
                r.a(b, "key or sid is invalid!");
            }
        }
    }

    public JSONObject c() {
        try {
            if (s.a(b)) {
                return a;
            }
            byte[] bArrA = com.xiaomi.onetrack.d.a.a();
            String strA = c.a(e.a(bArrA));
            HashMap map = new HashMap();
            map.put(c, strA);
            String strB = com.xiaomi.onetrack.g.b.b(z.a().e(), map, true);
            if (!TextUtils.isEmpty(strB)) {
                JSONObject jSONObject = new JSONObject(strB);
                int iOptInt = jSONObject.optInt("code");
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                if (iOptInt == 0 && jSONObjectOptJSONObject != null) {
                    String strOptString = jSONObjectOptJSONObject.optString("key");
                    String strOptString2 = jSONObjectOptJSONObject.optString(d);
                    if (!TextUtils.isEmpty(strOptString) && !TextUtils.isEmpty(strOptString2)) {
                        String strA2 = c.a(com.xiaomi.onetrack.d.a.b(c.a(strOptString), bArrA));
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("key", strA2);
                        jSONObject2.put(d, strOptString2);
                        this.g = jSONObject2;
                        ac.a(b.a(this.f, jSONObject2.toString()));
                        ac.i(System.currentTimeMillis());
                    }
                }
            }
            return this.g;
        } catch (Exception e2) {
            r.b(b, "requestSecretData: " + e2.toString());
        }
    }

    private JSONObject e() {
        JSONObject jSONObjectF = this.g;
        if (jSONObjectF == null && (jSONObjectF = f()) != null) {
            this.g = jSONObjectF;
        }
        return jSONObjectF == null ? c() : jSONObjectF;
    }

    private JSONObject f() {
        try {
            String strG = ac.g();
            if (TextUtils.isEmpty(strG)) {
                return null;
            }
            return new JSONObject(b.b(this.f, strG));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
