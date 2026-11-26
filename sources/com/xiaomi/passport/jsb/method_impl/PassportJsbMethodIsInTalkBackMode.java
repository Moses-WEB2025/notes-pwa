package com.xiaomi.passport.jsb.method_impl;

import com.xiaomi.accountsdk.utils.TalkBack;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodIsInTalkBackMode extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        return new PassportJsbMethodResult(TalkBack.isActive(passportJsbWebView.getContext()));
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "isInTalkBackMode";
    }
}
