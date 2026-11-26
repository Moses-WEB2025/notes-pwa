package com.xiaomi.onetrack.g;

import android.text.TextUtils;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class d {
    public static final int a = 200;
    public static final int b = 401;
    public static final int c = 404;
    public int d;
    public JSONArray e;
    public String f;
    public String g;

    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (this.d == 200) {
                this.e = jSONObject.optJSONArray("messageIds");
                return;
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(SNSAuthProvider.VALUE_SNS_ERROR);
            this.f = jSONObjectOptJSONObject.optString("message");
            this.g = jSONObjectOptJSONObject.optString("status");
        } catch (Exception unused) {
        }
    }

    public String toString() {
        return "PublishResponse{code=" + this.d + ", messageIds=" + this.e + ", message='" + this.f + "', status='" + this.g + "'}";
    }
}
