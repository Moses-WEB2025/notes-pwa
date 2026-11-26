package com.xiaomi.passport.jsb.method_impl;

import android.content.Context;
import com.xiaomi.accountsdk.utils.PackageUtils;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGetAppInfo extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context context = passportJsbWebView.getContext();
        String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "packageName");
        if (PackageUtils.isPkgExist(context, paramsStringFieldOrThrow)) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("versionCode", PackageUtils.getPkgVersionCode(context, paramsStringFieldOrThrow));
                jSONObject2.put("result", "installed");
                return new PassportJsbMethodResult(jSONObject2);
            } catch (JSONException e) {
                throw new IllegalStateException("should never happen", e);
            }
        }
        throw new PassportJsbMethodException(150, "package " + paramsStringFieldOrThrow + " not installed");
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "getAppInfo";
    }
}
