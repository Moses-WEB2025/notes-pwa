package com.xiaomi.passport.jsb.method_impl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.compat.ContextCompat;
import com.xiaomi.passport.jsb.ParcelablePassportJsbMethod;
import com.xiaomi.passport.jsb.PassportFrontendMethodInvoker;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.share.WeixinShareTool;
import com.xiaomi.passport.share.weixin.PassportWXAPIEventHandler;
import com.xiaomi.passport.share.weixin.WeixinShareHandler;
import com.xiaomi.passport.webview.PassportJsbWebView;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodShareMiniProgramToFriend extends ParcelablePassportJsbMethod {
    public static final Parcelable.Creator<ParcelablePassportJsbMethod> CREATOR = new Parcelable.Creator<ParcelablePassportJsbMethod>() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShareMiniProgramToFriend.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelablePassportJsbMethod createFromParcel(Parcel parcel) {
            return new PassportJsbMethodShareMiniProgramToFriend(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelablePassportJsbMethod[] newArray(int i) {
            return new PassportJsbMethodShareMiniProgramToFriend[i];
        }
    };
    public static final String TAG = "PassportJsbMethodShareMiniProgramToFriend";
    public static final String WEIXIN_SHARE_RESULT = "wei_xin_share_result";
    public static final String WEIXIN_SHARE_RESULT_ERR_CODE = "wei_xin_share_result_err_code";
    public static final String WEIXIN_SHARE_RESULT_ERR_STR = "wei_xin_share_result_err_str";
    private PassportWXAPIEventHandler mPassportWXAPIEventHandler;
    private ShareResultBroadcastReceiver mShareResultBroadcastReceiver;
    private String mWeiXinAppID;
    private WeixinShareTool mWeixinShareTool;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PassportJsbMethodShareMiniProgramToFriend(String str) {
        this.mWeiXinAppID = str;
    }

    public PassportJsbMethodShareMiniProgramToFriend(Parcel parcel) {
        this.mWeiXinAppID = parcel.readString();
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(PassportJsbWebView passportJsbWebView, JSONObject jSONObject) {
        Activity activity = (Activity) passportJsbWebView.getContext();
        if (this.mWeixinShareTool == null) {
            this.mWeixinShareTool = new WeixinShareTool(activity, this.mWeiXinAppID);
        }
        if (this.mPassportWXAPIEventHandler == null) {
            PassportWXAPIEventHandler passportWXAPIEventHandler = new PassportWXAPIEventHandler(activity);
            this.mPassportWXAPIEventHandler = passportWXAPIEventHandler;
            WeixinShareHandler.sWXAPIEventHandler = passportWXAPIEventHandler;
        }
        String strOptString = jSONObject.optString("mediaMessageJson");
        if (TextUtils.isEmpty(strOptString)) {
            return new PassportJsbMethodResult(false);
        }
        String strOptString2 = jSONObject.optString("callbackId");
        this.mWeixinShareTool.shareTo(0, "WXMiniProgramObject", strOptString);
        if (this.mShareResultBroadcastReceiver == null) {
            this.mShareResultBroadcastReceiver = new ShareResultBroadcastReceiver(passportJsbWebView.getContext());
        }
        this.mShareResultBroadcastReceiver.register(strOptString2, passportJsbWebView);
        return new PassportJsbMethodResult(true);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "shareMiniProgramToFriend";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mWeiXinAppID);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public void release(PassportJsbWebView passportJsbWebView) {
        ShareResultBroadcastReceiver shareResultBroadcastReceiver = this.mShareResultBroadcastReceiver;
        if (shareResultBroadcastReceiver != null) {
            shareResultBroadcastReceiver.unregister();
        }
        if (this.mPassportWXAPIEventHandler != null) {
            WeixinShareHandler.sWXAPIEventHandler = null;
        }
    }

    private static class ShareResultBroadcastReceiver extends BroadcastReceiver {
        private Context mAppContext;
        private Map<String, PassportJsbWebView> mWebViewCallbackIds = new HashMap();
        private boolean mIsRegistered = false;

        public ShareResultBroadcastReceiver(Context context) {
            this.mAppContext = context;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra(PassportJsbMethodShareMiniProgramToFriend.WEIXIN_SHARE_RESULT_ERR_CODE, -1);
            String stringExtra = intent.getStringExtra(PassportJsbMethodShareMiniProgramToFriend.WEIXIN_SHARE_RESULT_ERR_STR);
            JSONObject jSONObject = new JSONObject();
            if (intExtra == 0) {
                AccountLogger.log(PassportJsbMethodShareMiniProgramToFriend.TAG, "weixin share_success");
                try {
                    jSONObject.put("result", true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                AccountLogger.log(PassportJsbMethodShareMiniProgramToFriend.TAG, "weixin default error: " + stringExtra + " errorcode : " + intExtra);
                try {
                    jSONObject.put("result", false);
                } catch (JSONException e2) {
                    new IllegalStateException(e2);
                }
            }
            for (String str : this.mWebViewCallbackIds.keySet()) {
                PassportFrontendMethodInvoker.invokeCallback(this.mWebViewCallbackIds.get(str), str, jSONObject);
            }
        }

        public void register(String str, PassportJsbWebView passportJsbWebView) {
            this.mWebViewCallbackIds.put(str, passportJsbWebView);
            if (this.mIsRegistered) {
                return;
            }
            ContextCompat.registerReceiver(this.mAppContext, this, new IntentFilter(PassportJsbMethodShareMiniProgramToFriend.WEIXIN_SHARE_RESULT), false);
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
