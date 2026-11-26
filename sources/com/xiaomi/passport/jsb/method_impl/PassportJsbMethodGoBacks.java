package com.xiaomi.passport.jsb.method_impl;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGoBacks extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        int i = -jSONObject.optInt("steps");
        if (passportJsbWebView.canGoBackOrForward(i)) {
            passportJsbWebView.goBackOrForward(i);
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
        return "goBacks";
    }
}
