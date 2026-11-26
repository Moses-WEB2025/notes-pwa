package com.xiaomi.micloudsdk.request.utils;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class RequestJsonHelper {
    public static JSONObject putInt(JSONObject jSONObject, String[] strArr, int[] iArr) throws JSONException {
        for (int i = 0; i < strArr.length; i++) {
            if (ParamChecker.checkParamNeed(iArr[i])) {
                jSONObject.put(strArr[i], iArr[i]);
            }
        }
        return jSONObject;
    }

    public static JSONObject putLong(JSONObject jSONObject, String[] strArr, long[] jArr) throws JSONException {
        for (int i = 0; i < strArr.length; i++) {
            if (ParamChecker.checkParamNeed(jArr[i])) {
                jSONObject.put(strArr[i], jArr[i]);
            }
        }
        return jSONObject;
    }

    public static JSONObject putString(JSONObject jSONObject, String[] strArr, String[] strArr2) throws JSONException {
        for (int i = 0; i < strArr.length; i++) {
            if (ParamChecker.checkParamNeed(strArr2[i])) {
                jSONObject.put(strArr[i], strArr2[i]);
            }
        }
        return jSONObject;
    }
}
