package com.xiaomi.passport.webview;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;

/* JADX INFO: loaded from: classes4.dex */
public interface UrlLoadListener {
    void onLoadMainFrameError(PassportJsbWebView passportJsbWebView, WebResourceRequest webResourceRequest);

    void onLoadMainFrameFinish(PassportJsbWebView passportJsbWebView, String str);

    void onLoadMainFrameStart(PassportJsbWebView passportJsbWebView, String str, Bitmap bitmap);

    void onLoadResourceError(PassportJsbWebView passportJsbWebView, WebResourceRequest webResourceRequest);

    void onReceiveUrlTitle(PassportJsbWebView passportJsbWebView, String str);
}
