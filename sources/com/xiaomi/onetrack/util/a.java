package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import com.market.sdk.utils.Region;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    public static final String a = "onetrack_active";
    public static final String b = "onetrack_bug_report";
    public static final String c = "";
    public static final String d = "region";
    public static final String e = "area";
    public static final String f = "EU";
    public static final String g = "pub_gzipencrypt";
    public static final String h = "pub_sid";
    public static final String i = "true";
    public static final Set<String> j = new HashSet(Arrays.asList("AT", "BE", "BG", "HR", "CY", "CZ", "DK", "EE", "FI", "FR", "DE", "EL", "GR", "HU", "IS", "IE", "IT", "LV", "LI", "LT", "LU", "MT", "NL", "NO", "PL", "PT", "RO", "SK", "SI", Region.ES, "SE", "UK", "GB"));
    public static final Set<String> k = new HashSet(Arrays.asList("CN", Region.RU, "CU", "IR", "KP", "SD", "SY", "TR"));
    private static final String l = "Constants";

    public static String a() {
        try {
            String strL = s.l();
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(strL)) {
                jSONObject.put("region", strL);
                if (j.contains(strL.toUpperCase())) {
                    jSONObject.put(e, f);
                }
            }
            return jSONObject.toString();
        } catch (Exception e2) {
            r.a(l, "getDefaultAttributes exception:" + e2.getMessage());
            return "";
        }
    }

    public static Map<String, String> b() {
        HashMap map = new HashMap();
        try {
            String strL = s.l();
            if (!TextUtils.isEmpty(strL)) {
                map.put("region", strL);
                if (j.contains(strL.toUpperCase())) {
                    map.put(e, f);
                }
            }
            return map;
        } catch (Exception e2) {
            r.a(l, "getDefaultAttributes exception:" + e2.getMessage());
            return map;
        }
    }
}
