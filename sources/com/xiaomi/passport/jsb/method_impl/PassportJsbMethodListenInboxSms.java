package com.xiaomi.passport.jsb.method_impl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.compat.ContextCompat;
import com.xiaomi.passport.jsb.PassportFrontendMethodInvoker;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.sim.SIMInfo;
import com.xiaomi.passport.webview.PassportJsbWebView;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodListenInboxSms extends PassportJsbMethod {
    private static final String TAG = "JsbListenInboxSms";
    private InboxSmsReceiver mInboxSmsReceiver;

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "callbackId");
        Context context = passportJsbWebView.getContext();
        if (context.checkSelfPermission("android.permission.RECEIVE_SMS") != 0 && (context instanceof Activity)) {
            ((Activity) context).requestPermissions(new String[]{"android.permission.RECEIVE_SMS"}, 0);
        }
        if (this.mInboxSmsReceiver == null) {
            this.mInboxSmsReceiver = new InboxSmsReceiver(context.getApplicationContext());
        }
        this.mInboxSmsReceiver.register(paramsStringFieldOrThrow, passportJsbWebView);
        return new PassportJsbMethodResult(true);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "listenInboxSms";
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public void release(PassportJsbWebView passportJsbWebView) {
        InboxSmsReceiver inboxSmsReceiver = this.mInboxSmsReceiver;
        if (inboxSmsReceiver != null) {
            inboxSmsReceiver.unregister();
        }
    }

    private static class InboxSmsReceiver extends BroadcastReceiver {
        private Context mAppContext;
        private boolean mIsRegistered = false;
        private Map<String, PassportJsbWebView> mWebViewCallbackIds = new HashMap();

        public InboxSmsReceiver(Context context) {
            this.mAppContext = context;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            JSONObject jSONObjectGenInboxSmsResult = genInboxSmsResult(intent);
            if (jSONObjectGenInboxSmsResult == null) {
                AccountLogger.log(PassportJsbMethodListenInboxSms.TAG, "null message");
                return;
            }
            for (String str : this.mWebViewCallbackIds.keySet()) {
                PassportFrontendMethodInvoker.invokeCallback(this.mWebViewCallbackIds.get(str), str, jSONObjectGenInboxSmsResult);
            }
        }

        JSONObject genInboxSmsResult(Intent intent) {
            if (intent == null || intent.getExtras() == null) {
                AccountLogger.log(PassportJsbMethodListenInboxSms.TAG, "bundle is null");
                return null;
            }
            Bundle extras = intent.getExtras();
            JSONObject jSONObject = new JSONObject();
            Object[] objArr = (Object[]) extras.get("pdus");
            try {
                jSONObject.put(SIMInfo.SIM_INFO_TYPE_SUB_ID, Integer.toString(extras.getInt("subscription_id")));
                JSONArray jSONArray = new JSONArray();
                for (Object obj : objArr) {
                    String messageBody = SmsMessage.createFromPdu((byte[]) obj).getMessageBody();
                    if (!TextUtils.isEmpty(messageBody)) {
                        AccountLogger.log(PassportJsbMethodListenInboxSms.TAG, "receive sms body=" + messageBody);
                        jSONArray.put(messageBody);
                    }
                }
                try {
                    jSONObject.put("inboxSms", jSONArray);
                    return jSONObject;
                } catch (JSONException e) {
                    throw new IllegalStateException("should never happen", e);
                }
            } catch (JSONException e2) {
                throw new IllegalStateException("should never happen", e2);
            }
        }

        public void register(String str, PassportJsbWebView passportJsbWebView) {
            this.mWebViewCallbackIds.put(str, passportJsbWebView);
            if (this.mIsRegistered) {
                return;
            }
            ContextCompat.registerReceiver(this.mAppContext, this, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"), true);
            this.mIsRegistered = true;
        }

        public void unregister() {
            if (this.mIsRegistered) {
                this.mAppContext.unregisterReceiver(this);
            }
            this.mIsRegistered = false;
        }
    }
}
