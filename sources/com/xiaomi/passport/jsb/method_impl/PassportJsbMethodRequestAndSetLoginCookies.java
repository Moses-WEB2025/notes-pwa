package com.xiaomi.passport.jsb.method_impl;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.jsb.PassportFrontendMethodInvoker;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.utils.HttpCookies;
import com.xiaomi.passport.webview.PassportJsbWebView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodRequestAndSetLoginCookies extends PassportJsbMethod {
    private BgTask<JSONObject> mRequestAndSetLoginCookiesTask;

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(final PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        if (XiaomiAccountManager.get(passportJsbWebView.getContext()).getXiaomiAccount() == null) {
            return new PassportJsbMethodResult(false);
        }
        final String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "callbackId");
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("serviceIdCookieDomains");
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    arrayList.add(new Pair(jSONObject2.getString("serviceId"), jSONObject2.optString("cookieDomain")));
                } catch (JSONException e) {
                    throw new PassportJsbMethodException(104, "error get service id", e);
                }
            }
            BgTask<JSONObject> bgTask = this.mRequestAndSetLoginCookiesTask;
            if (bgTask != null) {
                bgTask.cancelAndRelease();
            }
            BgTask<JSONObject> bgTask2 = new BgTask<>(new RequestAndSetLoginCookiesBgRunnable(passportJsbWebView.getContext(), arrayList), new BgTask.SuccessResultRunnable<JSONObject>() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodRequestAndSetLoginCookies.1
                @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
                public void run(JSONObject jSONObject3) {
                    JSONObject jSONObject4 = new JSONObject();
                    try {
                        jSONObject4.put("result", true);
                        jSONObject4.put("data", jSONObject3);
                        PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, paramsStringFieldOrThrow, jSONObject4);
                    } catch (JSONException e2) {
                        throw new IllegalStateException("never happen", e2);
                    }
                }
            }, new BgTask.ErrorResultRunnable() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodRequestAndSetLoginCookies.2
                @Override // com.xiaomi.passport.task.BgTask.ErrorResultRunnable
                public void run(Throwable th) {
                    JSONObject jSONObject3 = new JSONObject();
                    try {
                        jSONObject3.put("result", false);
                        PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, paramsStringFieldOrThrow, jSONObject3);
                    } catch (JSONException e2) {
                        throw new IllegalStateException("never happen", e2);
                    }
                }
            });
            this.mRequestAndSetLoginCookiesTask = bgTask2;
            bgTask2.execute();
            return new PassportJsbMethodResult(true);
        } catch (JSONException e2) {
            throw new PassportJsbMethodException(104, "no serviceIdCookieDomains array", e2);
        }
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "requestAndSetLoginCookies";
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public void release(PassportJsbWebView passportJsbWebView) {
        super.release(passportJsbWebView);
        BgTask<JSONObject> bgTask = this.mRequestAndSetLoginCookiesTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mRequestAndSetLoginCookiesTask = null;
        }
    }

    private static class RequestAndSetLoginCookiesBgRunnable implements BgTask.BgTaskRunnable<JSONObject> {
        public final Context context;
        public final List<Pair<String, String>> serviceIdCookiesDomains;

        public RequestAndSetLoginCookiesBgRunnable(Context context, List<Pair<String, String>> list) {
            this.context = context;
            this.serviceIdCookiesDomains = new ArrayList(list);
        }

        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public JSONObject run() throws Throwable {
            XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(this.context);
            Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
            HashSet<String> hashSet = new HashSet();
            Iterator<Pair<String, String>> it = this.serviceIdCookiesDomains.iterator();
            while (it.hasNext()) {
                hashSet.add((String) it.next().first);
            }
            HashMap map = new HashMap();
            for (String str : hashSet) {
                ServiceTokenResult serviceTokenResultPeekServiceToken = xiaomiAccountManager.peekServiceToken(xiaomiAccount, str, null);
                if (serviceTokenResultPeekServiceToken != null) {
                    xiaomiAccountManager.invalidateServiceToken(serviceTokenResultPeekServiceToken).get();
                }
                map.put(str, xiaomiAccountManager.getServiceToken(xiaomiAccount, str, null).get().stsCookies);
            }
            for (Pair<String, String> pair : this.serviceIdCookiesDomains) {
                if (!TextUtils.isEmpty((CharSequence) pair.second)) {
                    HttpCookies.set((String) pair.second, HttpCookies.parse((String) map.get(pair.first)));
                }
            }
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            try {
                for (Pair<String, String> pair2 : this.serviceIdCookiesDomains) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("serviceId", pair2.first);
                    jSONObject2.put("cookieDomain", pair2.second);
                    jSONObject2.put("cookieString", map.get(pair2.first));
                    jSONArray.put(jSONObject2);
                }
                jSONObject.put("userId", xiaomiAccount.name);
                jSONObject.put("cookies", jSONArray);
                return jSONObject;
            } catch (JSONException e) {
                throw new IllegalStateException("should never happen", e);
            }
        }
    }
}
