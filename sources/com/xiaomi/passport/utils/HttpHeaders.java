package com.xiaomi.passport.utils;

import android.text.TextUtils;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class HttpHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

    public static String getContentType(Map<String, String> map, String str) {
        if (map != null) {
            String str2 = map.get("Content-Type");
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
        }
        return str;
    }
}
