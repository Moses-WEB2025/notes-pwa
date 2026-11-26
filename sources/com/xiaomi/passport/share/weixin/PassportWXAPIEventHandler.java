package com.xiaomi.passport.share.weixin;

import android.app.Activity;
import android.content.Intent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShareMiniProgramToFriend;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class PassportWXAPIEventHandler implements IWXAPIEventHandler {
    WeakReference<Activity> mActivityRef;

    public void onReq(BaseReq baseReq) {
    }

    public PassportWXAPIEventHandler(Activity activity) {
        this.mActivityRef = new WeakReference<>(activity);
    }

    public void onResp(BaseResp baseResp) {
        Activity activity = this.mActivityRef.get();
        if (activity != null) {
            Intent intent = new Intent(PassportJsbMethodShareMiniProgramToFriend.WEIXIN_SHARE_RESULT);
            intent.putExtra(PassportJsbMethodShareMiniProgramToFriend.WEIXIN_SHARE_RESULT_ERR_CODE, baseResp.errCode);
            intent.putExtra(PassportJsbMethodShareMiniProgramToFriend.WEIXIN_SHARE_RESULT_ERR_STR, baseResp.errStr);
            activity.sendBroadcast(intent);
        }
    }
}
