package com.xiaomi.passport.jsb.method_impl;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodStartPage extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context context = passportJsbWebView.getContext();
        Intent intent = new Intent();
        String strOptString = jSONObject.optString("action");
        if (!TextUtils.isEmpty(strOptString)) {
            intent.setAction(strOptString);
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("categories");
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                String strOptString2 = jSONArrayOptJSONArray.optString(i);
                if (!TextUtils.isEmpty(strOptString2)) {
                    intent.addCategory(strOptString2);
                }
            }
        }
        String strOptString3 = jSONObject.optString("package");
        if (!TextUtils.isEmpty(strOptString3)) {
            intent.setPackage(strOptString3);
        }
        String strOptString4 = jSONObject.optString("data");
        if (!TextUtils.isEmpty(strOptString4)) {
            intent.setData(Uri.parse(strOptString4));
        }
        if (jSONObject.has("flags")) {
            try {
                intent.setFlags(jSONObject.getInt("flags"));
            } catch (JSONException e) {
                throw new IllegalStateException("should never happen", e);
            }
        }
        intent.putExtras(jsonToBundle(jSONObject.optJSONObject("bundle")));
        boolean zOptBoolean = jSONObject.optBoolean("closeCurrentPage");
        if ((context instanceof Activity) && zOptBoolean) {
            ((Activity) context).finish();
        }
        try {
            context.startActivity(intent);
            return new PassportJsbMethodResult(true);
        } catch (ActivityNotFoundException e2) {
            throw new PassportJsbMethodException(106, "can not find target page", e2);
        }
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "startPage";
    }
}
