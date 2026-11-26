package com.xiaomi.passport.share.weixin;

import android.app.Activity;
import android.content.Intent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomi.accountsdk.utils.PackageUtils;

/* JADX INFO: loaded from: classes4.dex */
public class WeixinShareHandler {
    public static IWXAPIEventHandler sWXAPIEventHandler;

    public static boolean handleWxIntent(Activity activity, Intent intent) {
        String weiXinAppIDFromMetaData = PackageUtils.getWeiXinAppIDFromMetaData(activity);
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(activity, weiXinAppIDFromMetaData, true);
        iwxapiCreateWXAPI.registerApp(weiXinAppIDFromMetaData);
        return iwxapiCreateWXAPI.handleIntent(intent, new IWXAPIEventHandler() { // from class: com.xiaomi.passport.share.weixin.WeixinShareHandler.1
            public void onReq(BaseReq baseReq) {
                if (getWXAPIEventHandler() != null) {
                    getWXAPIEventHandler().onReq(baseReq);
                }
            }

            public void onResp(BaseResp baseResp) {
                if (getWXAPIEventHandler() != null) {
                    getWXAPIEventHandler().onResp(baseResp);
                }
            }

            private IWXAPIEventHandler getWXAPIEventHandler() {
                return WeixinShareHandler.sWXAPIEventHandler;
            }
        });
    }
}
