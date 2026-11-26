package com.xiaomi.passport.accountmanager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.xiaomi.passport.utils.LogoutInvalidateServiceTokenHelper;

/* JADX INFO: loaded from: classes4.dex */
class ManageSystemXiaomiAccountManager extends OwnSystemXiaomiAccountManager {
    public ManageSystemXiaomiAccountManager(Context context) {
        super(context);
    }

    @Override // com.xiaomi.passport.accountmanager.OwnSystemXiaomiAccountManager, com.xiaomi.passport.accountmanager.IXiaomiAccountManager
    public XiaomiAccountManagerFuture<Bundle> removeXiaomiAccount(XiaomiAccountManagerCallback<Bundle> xiaomiAccountManagerCallback, Handler handler) {
        return new XiaomiAccountManagerFuture<Bundle>(xiaomiAccountManagerCallback, handler) { // from class: com.xiaomi.passport.accountmanager.ManageSystemXiaomiAccountManager.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture
            public Bundle doWork() throws Exception {
                Bundle bundle = new Bundle();
                bundle.putBoolean("booleanResult", false);
                bundle.putParcelable("intent", ManageSystemXiaomiAccountManager.this.getAccountHomeActivityIntent());
                LogoutInvalidateServiceTokenHelper.logoutAccount(ManageSystemXiaomiAccountManager.this.mContext, LogoutInvalidateServiceTokenHelper.getServiceTokenSid(ManageSystemXiaomiAccountManager.this.mContext), true);
                return bundle;
            }
        }.start();
    }
}
