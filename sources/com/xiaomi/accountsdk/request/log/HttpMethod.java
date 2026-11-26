package com.xiaomi.accountsdk.request.log;

import com.xiaomi.accountsdk.request.PassportSimpleRequest;

/* JADX INFO: loaded from: classes4.dex */
public class HttpMethod {
    public static final HttpMethod GET = new HttpMethod(PassportSimpleRequest.HTTP_METHOD_GET);
    public static final HttpMethod POST = new HttpMethod(PassportSimpleRequest.HTTP_METHOD_POST);
    private final String name;

    private HttpMethod(String str) {
        this.name = str;
    }

    public String toString() {
        return this.name;
    }
}
