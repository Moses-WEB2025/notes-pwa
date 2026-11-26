package com.xiaomi.passport.share;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomi.passport.share.weixin.WeiXinApiHelper;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class WeixinShareTool {
    private static final String TAG = "WeixinShareTool";
    private WeakReference<Activity> mActivityRef;
    private IWXAPI mWeixinApi;
    private String mWeixinAppID;

    public WeixinShareTool(Activity activity, String str) {
        this.mWeixinAppID = str;
        this.mActivityRef = new WeakReference<>(activity);
        if (TextUtils.isEmpty(this.mWeixinAppID)) {
            throw new IllegalArgumentException("weixin app id is empty , set up  first !");
        }
        IWXAPI iwxapiCreateWXAPI = WXAPIFactory.createWXAPI(activity, this.mWeixinAppID, true);
        this.mWeixinApi = iwxapiCreateWXAPI;
        iwxapiCreateWXAPI.registerApp(this.mWeixinAppID);
    }

    protected Activity getActivityFromWeakRef() {
        return this.mActivityRef.get();
    }

    public void shareTo(final int i, final String str, final String str2) {
        final IWXAPI iwxapi = this.mWeixinApi;
        if (iwxapi == null) {
            throw new IllegalStateException("mWeixinApi null");
        }
        if (iwxapi.isWXAppInstalled() && this.mWeixinApi.registerApp(this.mWeixinAppID)) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.xiaomi.passport.share.WeixinShareTool.1
                @Override // java.lang.Runnable
                public void run() {
                    new WeiXinApiHelper(iwxapi).shareMediaMessageJsonTo(i, str, str2);
                }
            });
        }
    }
}
