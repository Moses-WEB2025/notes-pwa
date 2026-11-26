package com.xiaomi.passport.jsb.method_impl;

import android.app.Activity;
import android.content.Context;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodClosePage extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context context = passportJsbWebView.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            String strOptString = jSONObject.optString("reason");
            if ("success".equals(strOptString)) {
                activity.setResult(-1);
            } else if (ToDoStat.CANCEL.equals(strOptString)) {
                activity.setResult(0);
            }
            activity.finish();
            return new PassportJsbMethodResult(true);
        }
        throw new PassportJsbMethodException(105, "WebView is not attached to valid page");
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "closePage";
    }
}
