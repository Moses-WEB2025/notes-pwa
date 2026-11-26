package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;

/* JADX INFO: loaded from: classes4.dex */
public class WeChatAuthProvider extends SNSAuthProvider {
    public static final String WX_API_STATE_PASSPORT = "wx_api_passport";
    private static final String WX_PACKAGE_NAME1 = "com.";
    private static final String WX_PACKAGE_NAME2 = "tencent.";
    private static final String WX_PACKAGE_NAME3 = "mm";
    public static volatile Object sWXAPIEventHandler;

    public WeChatAuthProvider() {
        super("weixin");
    }

    public String getScope() {
        return "snsapi_userinfo";
    }

    public String getState() {
        return WX_API_STATE_PASSPORT;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.passport_ic_sns_wechat;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public String getAppId(Context context) {
        return context.getString(R.string.wechat_application_id);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void startLogin(Activity activity) {
        String appId = getAppId(activity);
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(activity, appId, true);
        iwxapiCreateWXAPI.registerApp(appId);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = getScope();
        req.state = getState();
        iwxapiCreateWXAPI.sendReq(req);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public boolean isServiceAvailable(Context context) {
        return isPackageInstalled(context, WX_PACKAGE_NAME1, WX_PACKAGE_NAME2, WX_PACKAGE_NAME3);
    }

    public static boolean handleWxIntent(final Activity activity, Intent intent) {
        WeChatAuthProvider weChatAuthProvider = new WeChatAuthProvider();
        String appId = weChatAuthProvider.getAppId(activity);
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(activity, appId, true);
        iwxapiCreateWXAPI.registerApp(appId);
        return iwxapiCreateWXAPI.handleIntent(intent, new IWXAPIEventHandler() { // from class: com.xiaomi.passport.ui.internal.WeChatAuthProvider.1
            public void onReq(BaseReq baseReq) {
                if (getWXAPIEventHandler() != null) {
                    getWXAPIEventHandler().onReq(baseReq);
                }
            }

            public void onResp(BaseResp baseResp) {
                if (baseResp instanceof SendAuth.Resp) {
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    if (TextUtils.equals(resp.state, WeChatAuthProvider.WX_API_STATE_PASSPORT) || baseResp.errCode != 0) {
                        LoginFailedReasonHolder.set("WeixinAuthFail");
                        if (baseResp.errCode == 0) {
                            WeChatAuthProvider.this.storeSnsCode(activity, resp.code);
                            WeChatAuthProvider.this.dispatchResult(activity, "ok");
                            return;
                        } else {
                            WeChatAuthProvider.this.dispatchResult(activity, SNSAuthProvider.VALUE_SNS_ERROR);
                            return;
                        }
                    }
                }
                if (getWXAPIEventHandler() != null) {
                    getWXAPIEventHandler().onResp(baseResp);
                }
            }

            private IWXAPIEventHandler getWXAPIEventHandler() {
                return (IWXAPIEventHandler) WeChatAuthProvider.sWXAPIEventHandler;
            }
        });
    }

    private boolean isPackageInstalled(Context context, String str, String str2, String str3) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str + str2 + str3, 0);
            return packageInfo != null && packageInfo.applicationInfo.enabled;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
