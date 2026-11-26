package com.xiaomi.ai.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.ai.HTTPCallback;
import com.xiaomi.ai.TrackInfo;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class k {
    public static final String a = "aHR0cHM6Ly9hcGkuYWkueGlhb21pLmNvbS90cmFjay92Mg==";
    public static final String b = "aHR0cHM6Ly9hcGktcHJldmlldy5haS54aWFvbWkuY29tL3RyYWNrL3Yy";
    String c;
    String d;
    HTTPCallback e;
    int f;

    public k(String str, String str2) {
        this.c = str;
        this.d = str2;
    }

    private String a(int i, int i2) {
        if (i == 0) {
            return new String(Base64.decode(a, 0));
        }
        if (i == 1) {
            return new String(Base64.decode(b, 0));
        }
        return null;
    }

    public int a(HTTPCallback hTTPCallback, TrackInfo trackInfo, String str, String str2, int i) {
        String strA = a(trackInfo, str);
        if (TextUtils.isEmpty(strA)) {
            Log.MiLogW("Track", "generate Data failed");
            return -1;
        }
        if (TextUtils.isEmpty(str2)) {
            Log.MiLogW("Track", "Token is null");
            return -2;
        }
        Log.MiLogD("UploadTrack", strA);
        HashMap map = new HashMap();
        map.put("Content-Type", "application/json");
        map.put("Date", f.a());
        map.put("Authorization", str2);
        new Thread(new l(this, hTTPCallback, a(this.f, i), map, strA)).start();
        return 0;
    }

    public HTTPCallback a() {
        return this.e;
    }

    public String a(TrackInfo trackInfo, String str) {
        JSONObject jSONObjectA = a(trackInfo);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(com.xiaomi.onetrack.b.g.d, this.c);
            jSONObject.put("did", this.d);
            jSONObject.put("uid", str);
            if (trackInfo.getData() == null) {
                jSONObject.put("data", jSONObjectA);
            } else {
                jSONObject.put("data", trackInfo.getData());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public JSONObject a(TrackInfo trackInfo) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", trackInfo.getVersion());
            jSONObject.put("request_id", trackInfo.getRequestID());
            jSONObject.put("resource_id", trackInfo.getResourceID());
            jSONObject.put("cp", trackInfo.getCP());
            jSONObject.put("load_time", trackInfo.getLoadTime());
            jSONObject.put("start_time", trackInfo.getStartTime());
            jSONObject.put("end_time", trackInfo.getEndTime());
            jSONObject.put("position", trackInfo.getPosition());
            jSONObject.put("action_type", trackInfo.getActionType());
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(HTTPCallback hTTPCallback) {
        this.e = hTTPCallback;
    }
}
