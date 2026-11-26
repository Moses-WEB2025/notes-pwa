package com.xiaomi.miai.utils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes4.dex */
public class RequestUtil {
    public static final MediaType JSON = MediaType.parse("application/json");

    public static RequestBody jsonBody(String str) {
        return RequestBody.create(JSON, str);
    }
}
