package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseWeiboAuthProvider extends SNSAuthProvider {
    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        return 32973;
    }

    public BaseWeiboAuthProvider() {
        super(SNSAuthProvider.SNS_TYPE_WEIBO);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.passport_ic_sns_weibo;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public String getAppId(Context context) {
        return context.getString(R.string.weibo_application_id);
    }
}
