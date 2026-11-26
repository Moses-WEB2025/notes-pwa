package com.xiaomi.micloudsdk.utils;

import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class JsonUtils {
    public static Map<String, Object> jsonToMap(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        ArrayMap arrayMap = new ArrayMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            arrayMap.put(next, convertObj(jSONObject.opt(next)));
        }
        return arrayMap;
    }

    public static long getLongAttributeFromJson(JSONObject jSONObject, String str) throws JSONException {
        return Long.parseLong(jSONObject.getString(str));
    }

    private static Object convertObj(Object obj) {
        if (obj instanceof JSONObject) {
            return jsonToMap((JSONObject) obj);
        }
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                arrayList.add(convertObj(jSONArray.opt(i)));
            }
            return arrayList;
        }
        if (obj == JSONObject.NULL) {
            return null;
        }
        return obj;
    }
}
