package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tencent.tauth.Tencent;
import com.xiaomi.account.passportsdk.account_sso.R;
import defpackage.R2;

/* JADX INFO: loaded from: classes4.dex */
public class QQSSOAuthProvider extends QQAuthProvider {
    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        return R2.drawable.miuix_appcompat_btn_bg_dialog_foreground_dark_p1;
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        Tencent.onActivityResultData(i, i2, intent, new QQUiListener(activity.getApplicationContext(), this));
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void startLogin(Activity activity) {
        Tencent.createInstance(getAppId(activity), activity).login(activity, "", new QQUiListener(activity.getApplicationContext(), this));
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public String getAppId(Context context) {
        return context.getString(R.string.qq_application_id);
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.passport_ic_sns_qq;
    }
}
