package com.xiaomi.passport.utils;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AnyUrl {
    public static final String PARAM_KEY_USER_ID = "userId";

    public static String make(String str, Map<String, String> map) {
        HashMap map2 = new HashMap();
        Uri uri = Uri.parse(str);
        try {
            for (String str2 : uri.getQueryParameterNames()) {
                map2.put(str2, uri.getQueryParameter(str2));
            }
            if (map != null) {
                map2.putAll(map);
            }
            Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
            builderBuildUpon.clearQuery();
            for (Map.Entry entry : map2.entrySet()) {
                builderBuildUpon.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            return builderBuildUpon.build().toString();
        } catch (UnsupportedOperationException unused) {
            return str;
        }
    }
}
