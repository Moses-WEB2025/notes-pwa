package com.xiaomi.ai;

/* JADX INFO: loaded from: classes4.dex */
public interface HTTPCallback {
    void onHTTPRequestError(int i, String str);

    void onHTTPRequestResult(String str);

    void onHTTPRequestStart();
}
