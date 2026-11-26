package com.xiaomi.passport.accountmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public class OwnAppXiaomiAccountAuthenticatorService extends Service {
    private static final String TAG = "OwnAppXiaomiAccountAuthenticatorService";
    private OwnAppXiaomiAccountAuthenticator mAuthenticator;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mAuthenticator = new OwnAppXiaomiAccountAuthenticator(this);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        AccountLogger.log(TAG, "return the AccountAuthenticator binder of package: " + getPackageName());
        return this.mAuthenticator.getIBinder();
    }
}
