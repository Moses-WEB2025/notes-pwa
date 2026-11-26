package com.xiaomi.passport.jsb.method_impl;

import android.content.Context;
import com.xiaomi.passport.SecurityDeviceSignManager;
import com.xiaomi.passport.jsb.PassportFrontendMethodInvoker;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodGetTZSign extends PassportJsbMethod {
    private BgTask<String> mTZSignTask;

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(final PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        Context context = passportJsbWebView.getContext();
        final String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "callbackId");
        try {
            long jOptLong = jSONObject.optLong("timeoutMs", 15000L);
            JSONArray jSONArray = jSONObject.getJSONArray("tzSignParts");
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                strArr[i] = jSONArray.getString(i);
            }
            BgTask<String> bgTask = this.mTZSignTask;
            if (bgTask != null) {
                bgTask.cancelAndRelease();
            }
            BgTask<String> bgTask2 = new BgTask<>(new TZSignBgRunnable(context, strArr, jOptLong), new BgTask.SuccessResultRunnable<String>() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetTZSign.1
                @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
                public void run(String str) {
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("result", str);
                        PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, paramsStringFieldOrThrow, jSONObject2);
                    } catch (JSONException e) {
                        throw new IllegalStateException("should never happen", e);
                    }
                }
            }, new BgTask.ErrorResultRunnable() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodGetTZSign.2
                @Override // com.xiaomi.passport.task.BgTask.ErrorResultRunnable
                public void run(Throwable th) {
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put(SNSAuthProvider.VALUE_SNS_ERROR, th.getMessage());
                        PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, paramsStringFieldOrThrow, jSONObject2);
                    } catch (JSONException e) {
                        throw new IllegalStateException("should never happen", e);
                    }
                }
            });
            this.mTZSignTask = bgTask2;
            bgTask2.execute();
            return new PassportJsbMethodResult(true);
        } catch (JSONException e) {
            throw new PassportJsbMethodException(104, e.getMessage(), e);
        }
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "getTZSign";
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public void release(PassportJsbWebView passportJsbWebView) {
        super.release(passportJsbWebView);
        BgTask<String> bgTask = this.mTZSignTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
        }
    }

    private static class TZSignBgRunnable implements BgTask.BgTaskRunnable<String> {
        private final Context mContext;
        private final String[] mTZSignParts;
        private final long mTimeoutMs;

        public TZSignBgRunnable(Context context, String[] strArr, long j) {
            this.mContext = context;
            this.mTZSignParts = strArr;
            this.mTimeoutMs = j;
        }

        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public String run() throws Throwable {
            return SecurityDeviceSignManager.syncSignStringArray(this.mContext, this.mTZSignParts, null, this.mTimeoutMs);
        }
    }
}
