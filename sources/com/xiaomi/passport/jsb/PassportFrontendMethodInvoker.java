package com.xiaomi.passport.jsb;

import android.webkit.ValueCallback;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportFrontendMethodInvoker {
    private static final String TAG = "PassportFrontendMethodInvoker";

    public static void invokeCallback(PassportJsbWebView passportJsbWebView, String str) {
        invokeCallback(passportJsbWebView, str, null);
    }

    public static void invokeCallback(final PassportJsbWebView passportJsbWebView, final String str, JSONObject jSONObject) {
        AccountLogger.log(TAG, String.format("invoke callback %s with params %s", str, jSONObject));
        final String string = jSONObject == null ? "" : jSONObject.toString();
        passportJsbWebView.post(new Runnable() { // from class: com.xiaomi.passport.jsb.PassportFrontendMethodInvoker.1
            @Override // java.lang.Runnable
            public void run() {
                passportJsbWebView.loadUrl(String.format("javascript: invokePassportCallback('%s', '%s');", str, PassportFrontendMethodInvoker.encodeJavascriptParams(string)));
            }
        });
    }

    public static void invokeMethod(PassportJsbWebView passportJsbWebView, String str) {
        invokeMethod(passportJsbWebView, str, null);
    }

    public static void invokeMethod(final PassportJsbWebView passportJsbWebView, final String str, JSONObject jSONObject) {
        AccountLogger.log(TAG, String.format("invoke method %s with params %s", str, jSONObject));
        final String string = jSONObject == null ? "" : jSONObject.toString();
        passportJsbWebView.post(new Runnable() { // from class: com.xiaomi.passport.jsb.PassportFrontendMethodInvoker.2
            @Override // java.lang.Runnable
            public void run() {
                passportJsbWebView.evaluateJavascript(String.format("invokePassportMethod('%s', '%s')", str, PassportFrontendMethodInvoker.encodeJavascriptParams(string)), new ValueCallback<String>() { // from class: com.xiaomi.passport.jsb.PassportFrontendMethodInvoker.2.1
                    @Override // android.webkit.ValueCallback
                    public void onReceiveValue(String str2) {
                    }
                });
            }
        });
    }

    public static String encodeJavascriptParams(String str) {
        return str.replace("'", "\\'");
    }
}
