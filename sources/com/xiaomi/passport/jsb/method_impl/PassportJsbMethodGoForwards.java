package com.xiaomi.passport.jsb.method_impl;

import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGoForwards extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        int iOptInt = jSONObject.optInt("steps");
        if (passportJsbWebView.canGoBackOrForward(iOptInt)) {
            passportJsbWebView.goBackOrForward(iOptInt);
            return new PassportJsbMethodResult(true);
        }
        return new PassportJsbMethodResult(false);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "goForwards";
    }
}
