package com.xiaomi.passport.jsb.method_impl;

import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGetLoadHistory extends PassportJsbMethod {
    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        WebBackForwardList webBackForwardListCopyBackForwardList = passportJsbWebView.copyBackForwardList();
        int size = webBackForwardListCopyBackForwardList.getSize();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("current", webBackForwardListCopyBackForwardList.getCurrentIndex());
            jSONObject2.put("total", size);
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < size; i++) {
                JSONObject jSONObject3 = new JSONObject();
                WebHistoryItem itemAtIndex = webBackForwardListCopyBackForwardList.getItemAtIndex(i);
                jSONObject3.put("originalUrl", itemAtIndex.getOriginalUrl());
                jSONObject3.put("url", itemAtIndex.getUrl());
                jSONObject3.put("title", itemAtIndex.getTitle());
                jSONArray.put(jSONObject3);
            }
            jSONObject2.put("history", jSONArray);
            return new PassportJsbMethodResult(jSONObject2);
        } catch (JSONException e) {
            throw new PassportJsbMethodException(105, "GetLoadHistory", e);
        }
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "getLoadHistory";
    }
}
