package com.xiaomi.accountsdk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.text.StringSubstitutor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ObjectUtils {
    public static Map<String, Object> jsonToMap(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        HashMap map = new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, convertObj(jSONObject.opt(next)));
        }
        return map;
    }

    public static Object convertObjectToJson(Object obj) {
        if (obj instanceof List) {
            JSONArray jSONArray = new JSONArray();
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                jSONArray.put(convertObjectToJson(it.next()));
            }
            return jSONArray;
        }
        if (!(obj instanceof Map)) {
            return obj;
        }
        JSONObject jSONObject = new JSONObject();
        Map map = (Map) obj;
        for (Object obj2 : map.keySet()) {
            try {
                jSONObject.put((String) obj2, convertObjectToJson(map.get(obj2)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    public static JSONArray jsonArrayStrToJsonArray(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new JSONArray(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Object> jsonArrayStringToList(String str) {
        JSONArray jSONArrayJsonArrayStrToJsonArray = jsonArrayStrToJsonArray(str);
        if (jSONArrayJsonArrayStrToJsonArray == null) {
            return null;
        }
        return convertJsonArrayToList(jSONArrayJsonArrayStrToJsonArray);
    }

    public static List<Object> convertJsonArrayToList(JSONArray jSONArray) {
        Object objConvertObj = convertObj(jSONArray);
        if (objConvertObj instanceof List) {
            return (List) objConvertObj;
        }
        return null;
    }

    public static Map<String, String> listToMap(Map<String, List<String>> map) {
        HashMap map2 = new HashMap();
        if (map != null) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (key != null && value != null && value.size() > 0) {
                    map2.put(key, value.get(0));
                }
            }
        }
        return map2;
    }

    public static String flattenMap(Map<?, ?> map) {
        if (map == null) {
            return "null";
        }
        Set<Map.Entry<?, ?>> setEntrySet = map.entrySet();
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<?, ?> entry : setEntrySet) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append("(");
            sb.append(key);
            sb.append(",");
            sb.append(value);
            sb.append("),");
        }
        sb.append(StringSubstitutor.DEFAULT_VAR_END);
        return sb.toString();
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
