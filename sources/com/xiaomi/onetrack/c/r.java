package com.xiaomi.onetrack.c;

import android.text.TextUtils;
import com.google.android.appfunctions.schema.messages.v1.MessagesKt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class r {
    private static final String a = "%s/v1/projects/%s/topics/%s:publish";
    private static String b = "";
    private static final byte[] c = {104, 116, 116, 112, 115, 58, 47, 47, 112, 117, 98, 115, 117, 98, 46, 103, 111, 111, 103, 108, 101, 97, 112, 105, 115, 46, 99, 111, 109};
    private static final String d = "PSUploadDispense";

    public static void a(q qVar) {
        try {
            for (Map.Entry<String, List<m>> entry : c(qVar.a).entrySet()) {
                String key = entry.getKey();
                List<m> value = entry.getValue();
                if (value != null && value.size() != 0) {
                    for (Map.Entry<String, List<m>> entry2 : d(value).entrySet()) {
                        String key2 = entry2.getKey();
                        List<m> value2 = entry2.getValue();
                        if (value2 != null && value2.size() != 0) {
                            a(key, key2, value2);
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    private static void a(String str, String str2, List<m> list) throws Throwable {
        if (list == null || list.size() <= 0) {
            return;
        }
        a aVarA = a(com.xiaomi.onetrack.b.p.a().c(str), str, str2, b(list));
        com.xiaomi.onetrack.util.r.a(d, "uploadGzipEncryptData success:" + aVarA.a);
        if (aVarA.a) {
            com.xiaomi.onetrack.util.r.a(d, "uploadGzipEncryptData deleted:" + n.a().a(list));
        } else if (aVarA.b) {
            com.xiaomi.onetrack.util.r.a(d, "uploadGzipEncryptData deleted:" + n.a().b(list));
        }
    }

    private static String a(List<m> list) {
        if (list == null) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (m mVar : list) {
                JSONObject jSONObject2 = new JSONObject();
                Map<String, String> mapF = mVar.f();
                if (mapF != null) {
                    JSONObject jSONObject3 = new JSONObject();
                    for (Map.Entry<String, String> entry : mapF.entrySet()) {
                        if (!TextUtils.isEmpty(entry.getKey())) {
                            jSONObject3.put(entry.getKey(), entry.getValue());
                        }
                    }
                    jSONObject2.put("attributes", jSONObject3);
                }
                jSONObject2.put("data", mVar.e());
                jSONArray.put(jSONObject2);
            }
            jSONObject.put(MessagesKt.APP_FUNCTION_SCHEMA_CATEGORY_MESSAGES, jSONArray);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private static List<m> b(List<m> list) throws Throwable {
        if (list == null || list.size() == 0) {
            com.xiaomi.onetrack.util.r.a(d, "messages is empty, return null");
            return null;
        }
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        String str = com.xiaomi.onetrack.d.f.a().b()[1];
        String str2 = com.xiaomi.onetrack.d.f.a().b()[0];
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            for (m mVar : list) {
                Map<String, String> mapF = mVar.f();
                if (mapF != null) {
                    if (map.get(mapF) == null) {
                        map.put(mapF, new JSONArray());
                    }
                    ((JSONArray) map.get(mapF)).put(mVar.e());
                }
            }
            for (Map.Entry entry : map.entrySet()) {
                Map map2 = (Map) entry.getKey();
                map2.put(com.xiaomi.onetrack.util.a.g, "true");
                map2.put(com.xiaomi.onetrack.util.a.h, str);
                JSONArray jSONArray = (JSONArray) entry.getValue();
                com.xiaomi.onetrack.util.r.a(d, "zip before : " + jSONArray.toString().length());
                byte[] bArrA = ah.a(jSONArray.toString());
                com.xiaomi.onetrack.util.r.a(d, "zip after : " + bArrA.length);
                arrayList.add(new m(-1L, null, null, com.xiaomi.onetrack.d.c.a(ah.a(bArrA, str2)), map2, -1L));
            }
            return arrayList;
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.a(d, "sortByAttribute error: " + e.getMessage());
            return arrayList;
        }
    }

    private static Map<String, List<m>> c(List<m> list) {
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            m mVar = list.get(i);
            String strC = mVar.c();
            if (map.get(strC) == null) {
                map.put(strC, new ArrayList());
            }
            ((List) map.get(strC)).add(mVar);
        }
        return map;
    }

    private static Map<String, List<m>> d(List<m> list) {
        HashMap map = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            m mVar = list.get(i);
            String strD = mVar.d();
            if (map.get(strD) == null) {
                map.put(strD, new ArrayList());
            }
            ((List) map.get(strD)).add(mVar);
        }
        return map;
    }

    private static a a(String str, String str2, String str3, List<m> list) throws Throwable {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && list != null && list.size() != 0) {
                com.xiaomi.onetrack.g.d dVarA = com.xiaomi.onetrack.g.b.a(String.format(a, a(), str2, str3), a(list), str);
                if (dVarA != null) {
                    if (dVarA.d == 401) {
                        com.xiaomi.onetrack.b.o oVarA = com.xiaomi.onetrack.b.p.a().a(str2);
                        com.xiaomi.onetrack.h.a aVarA = com.xiaomi.onetrack.b.p.a().a(str2, oVarA);
                        if (com.xiaomi.onetrack.b.p.a().b(oVarA)) {
                            return a(aVarA.d, str2, str3, list);
                        }
                    } else if (dVarA.d == 404) {
                        if ("NOT_FOUND".equals(dVarA.g) && !TextUtils.isEmpty(dVarA.f) && (dVarA.f.contains(str2) || dVarA.f.contains(str3))) {
                            return new a(false, true);
                        }
                    } else if (dVarA.d == 200) {
                        return new a(true, false);
                    }
                }
                return new a(false, false);
            }
            com.xiaomi.onetrack.util.r.a(d, "token or data is null,return false");
            return new a(false, false);
        } catch (Exception e) {
            com.xiaomi.onetrack.util.r.b(d, "Exception while uploading ", e);
        }
    }

    private static String a() {
        if (TextUtils.isEmpty(b)) {
            b = com.xiaomi.onetrack.util.z.a().a(c);
        }
        return b;
    }

    static class a {
        public boolean a;
        public boolean b;

        a(boolean z, boolean z2) {
            this.a = z;
            this.b = z2;
        }
    }
}
