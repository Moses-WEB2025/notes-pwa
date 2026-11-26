package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;
import com.xiaomi.passport.ui.utils.PassportPageIntent;

/* JADX INFO: loaded from: classes4.dex */
public class QQAuthProvider extends SNSAuthProvider {
    public QQAuthProvider() {
        super("qq");
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.passport_ic_sns_qq;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public String getAppId(Context context) {
        return "100284651";
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void startLogin(Activity activity) {
        activity.startActivityForResult(PassportPageIntent.getSNSBindPageIntent(activity, "https://graph.qq.com/oauth2.0/authorize?response_type=code&display=wap&redirect_uri=https://account.xiaomi.com/pass/sns/login/load&client_id=" + getAppId(activity)), getRequestCode());
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        if (i == getRequestCode() && i2 == -1) {
            storeSnsCode(activity, intent.getStringExtra("code"));
        } else {
            LoginFailedReasonHolder.set("QQAuthFail");
        }
    }
}
