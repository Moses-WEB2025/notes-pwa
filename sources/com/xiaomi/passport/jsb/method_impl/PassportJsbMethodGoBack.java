package com.xiaomi.passport.jsb.method_impl;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGoBack extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        if (passportJsbWebView.canGoBack()) {
            passportJsbWebView.goBack();
        } else {
            Context context = passportJsbWebView.getContext();
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
        return new PassportJsbMethodResult(true);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "goBack";
    }
}
