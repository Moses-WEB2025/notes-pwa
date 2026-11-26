package com.xiaomi.onetrack.h;

import android.text.TextUtils;
import com.xiaomi.onetrack.util.r;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    public static final int h = 0;
    public static final int i = -10;
    public int a;
    public String b;
    public String c;
    public String d;
    public int e;
    public long f;
    public String g;

    public static a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        a aVar = new a();
        try {
            JSONObject jSONObject = new JSONObject(str);
            aVar.a = jSONObject.optInt("code");
            String strOptString = jSONObject.optString("msg");
            aVar.b = strOptString;
            if (aVar.a != 0) {
                if (!TextUtils.isEmpty(strOptString)) {
                    r.b("TokenBean", "token exception response :" + aVar.b);
                }
                return aVar;
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (jSONObjectOptJSONObject != null) {
                String strOptString2 = jSONObjectOptJSONObject.optString("accessToken");
                if (!TextUtils.isEmpty(strOptString2)) {
                    aVar.d = com.xiaomi.onetrack.d.a.c(strOptString2, com.xiaomi.onetrack.d.a.a);
                    aVar.e = jSONObjectOptJSONObject.optInt("expires");
                    long jOptLong = jSONObjectOptJSONObject.optLong("local_time", 0L);
                    if (jOptLong == 0) {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        aVar.f = jCurrentTimeMillis;
                        jSONObjectOptJSONObject.put("local_time", jCurrentTimeMillis);
                    } else {
                        aVar.f = jOptLong;
                    }
                }
            }
            aVar.g = jSONObject.toString();
            return aVar;
        } catch (Exception unused) {
        }
    }

    public String toString() {
        return "TokenBean{code=" + this.a + ", msg='" + this.b + "', data='" + this.c + "', access_token='" + this.d + "', expires=" + this.e + ", local_time=" + this.f + ", response='" + this.g + "'}";
    }
}
