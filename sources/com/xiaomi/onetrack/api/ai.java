package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.ServiceQualityEvent;
import com.xiaomi.onetrack.util.DeviceUtil;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ai {
    public static final String a = "B";
    public static final String b = "H";
    private static final String c = "EventDataBuilder";

    public static String a(String str, JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(str, configuration, iEventHook, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(aj ajVar, JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(ajVar, configuration, iEventHook, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(String str, String str2, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, boolean z, com.xiaomi.onetrack.util.x xVar, boolean z2) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, com.xiaomi.onetrack.f.b.a(str2, configuration, iEventHook, xVar, z2, false));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("class", str);
        jSONObject3.put("type", 1);
        jSONObject3.put(ah.t, z);
        jSONObject2.put("B", com.xiaomi.onetrack.util.t.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, long j, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, com.xiaomi.onetrack.f.b.a(str2, configuration, iEventHook, xVar, z, false));
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("class", str);
        jSONObject3.put("type", 2);
        jSONObject3.put("duration", j);
        jSONObject2.put("B", com.xiaomi.onetrack.util.t.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(String str, String str2, String str3, String str4, String str5, long j, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObjectA = com.xiaomi.onetrack.f.b.a("onetrack_bug_report", configuration, iEventHook, xVar, z, false);
        if (str5 != null) {
            jSONObjectA.put(com.xiaomi.onetrack.f.b.C0122b.m, str5);
        }
        jSONObject2.put(b, jSONObjectA);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("exception", str);
        jSONObject3.put("type", str3);
        jSONObject3.put("message", str2);
        jSONObject3.put("feature", str4);
        jSONObject3.put(ah.o, j);
        jSONObject2.put("B", com.xiaomi.onetrack.util.t.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(b, com.xiaomi.onetrack.f.b.a("onetrack_dau", configuration, iEventHook, xVar, z, false));
        JSONObject jSONObject3 = new JSONObject();
        Context contextB = com.xiaomi.onetrack.f.a.b();
        boolean zS = com.xiaomi.onetrack.util.ac.s();
        if (zS) {
            com.xiaomi.onetrack.util.ac.c(false);
        }
        jSONObject3.put(ah.x, zS);
        if (!(com.xiaomi.onetrack.util.s.b() ? com.xiaomi.onetrack.util.s.k() : configuration.isInternational())) {
            if (configuration.isIMEIEnable()) {
                jSONObject3.put(ah.y, DeviceUtil.c(contextB));
            }
            if (configuration.isIMSIEnable()) {
                jSONObject3.put(ah.z, DeviceUtil.d(contextB));
            }
        }
        jSONObject3.put(ah.B, ag.a(configuration));
        jSONObject2.put("B", com.xiaomi.onetrack.util.t.a(jSONObject3, jSONObject));
        return jSONObject2.toString();
    }

    public static String a(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(ah.c, configuration, iEventHook, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String b(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(ah.d, configuration, iEventHook, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(String str, String str2, JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a(str2, configuration, iEventHook, str, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(Configuration configuration, OneTrack.IEventHook iEventHook, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(ah.i, configuration, iEventHook, xVar, z, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("value", com.xiaomi.onetrack.c.j.b());
        jSONObject.put("B", jSONObject2);
        return jSONObject.toString();
    }

    public static String c(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a("ot_login", configuration, iEventHook, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String d(JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(b, com.xiaomi.onetrack.f.b.a("ot_logout", configuration, iEventHook, xVar, z, false));
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }

    public static String a(ServiceQualityEvent serviceQualityEvent, Configuration configuration, OneTrack.IEventHook iEventHook, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(ah.e, configuration, iEventHook, xVar, z, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(ah.C, serviceQualityEvent.getScheme());
        jSONObject2.put("host", serviceQualityEvent.getHost());
        jSONObject2.put(ah.E, serviceQualityEvent.getPort());
        jSONObject2.put("path", serviceQualityEvent.getPath());
        jSONObject2.put(ah.G, serviceQualityEvent.getIps());
        jSONObject2.put(ah.H, serviceQualityEvent.getResponseCode());
        jSONObject2.put("status", serviceQualityEvent.getStatusCode());
        jSONObject2.put("exception", serviceQualityEvent.getExceptionTag());
        jSONObject2.put("result", serviceQualityEvent.getResultType());
        jSONObject2.put(ah.L, serviceQualityEvent.getRetryCount());
        jSONObject2.put(ah.M, serviceQualityEvent.getRequestTimestamp());
        jSONObject2.put(ah.N, serviceQualityEvent.getRequestNetType());
        jSONObject2.put("dns", serviceQualityEvent.getDnsLookupTime());
        jSONObject2.put(ah.P, serviceQualityEvent.getTcpConnectTime());
        jSONObject2.put(ah.R, serviceQualityEvent.getHandshakeTime());
        jSONObject2.put(ah.S, serviceQualityEvent.getReceiveFirstByteTime());
        jSONObject2.put(ah.T, serviceQualityEvent.getReceiveAllByteTime());
        jSONObject2.put(ah.Q, serviceQualityEvent.getRequestDataSendTime());
        jSONObject2.put("duration", serviceQualityEvent.getDuration());
        jSONObject2.put(ah.V, serviceQualityEvent.getNetSdkVersion());
        Map<String, Object> extraParams = serviceQualityEvent.getExtraParams();
        if (extraParams != null && extraParams.size() > 0) {
            for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (com.xiaomi.onetrack.util.t.b(value)) {
                    jSONObject2.put(key, value);
                }
            }
        }
        jSONObject.put("B", jSONObject2);
        return jSONObject.toString();
    }

    public static String a(long j, String str, long j2, long j3, Configuration configuration, OneTrack.IEventHook iEventHook, com.xiaomi.onetrack.util.x xVar, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(b, com.xiaomi.onetrack.f.b.a(ah.j, configuration, iEventHook, xVar, z, false));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(ah.W, j);
        jSONObject2.put(ah.X, str);
        jSONObject2.put(ah.Y, j2);
        jSONObject2.put(ah.Z, j3);
        jSONObject.put("B", jSONObject2);
        return jSONObject.toString();
    }

    public static String a(String str, JSONObject jSONObject, Configuration configuration, OneTrack.IEventHook iEventHook, JSONObject jSONObject2, com.xiaomi.onetrack.util.x xVar, JSONArray jSONArray, boolean z) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        JSONObject jSONObjectA = com.xiaomi.onetrack.f.b.a(str, configuration, iEventHook, xVar, z, true);
        if (jSONArray != null && jSONArray.length() > 0) {
            jSONObjectA.put(com.xiaomi.onetrack.f.b.C0122b.F, jSONArray);
        }
        jSONObject3.put(b, jSONObjectA);
        jSONObject3.put("B", com.xiaomi.onetrack.util.t.a(jSONObject, jSONObject2));
        return jSONObject3.toString();
    }
}
