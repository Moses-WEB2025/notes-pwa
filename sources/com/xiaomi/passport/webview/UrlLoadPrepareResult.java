package com.xiaomi.passport.webview;

import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class UrlLoadPrepareResult {
    public final Map<String, String> headers;
    public final Map<String, String> urlParams;

    public UrlLoadPrepareResult(Map<String, String> map, Map<String, String> map2) {
        this.headers = map;
        this.urlParams = map2;
    }
}
