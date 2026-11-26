package com.xiaomi.passport.utils;

import android.accounts.Account;
import android.content.Context;
import android.os.Looper;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.MiuiCUserIdUtil;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class CUserIdUtil {
    private static final String TAG = "CUserIdUtil";
    private final Context mContext;

    public CUserIdUtil(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        this.mContext = context.getApplicationContext();
    }

    public final String getCUserId() {
        if (isInMainThread()) {
            throw new IllegalStateException("CUserIdUtil#getCUserId() should NOT be called on main thread!");
        }
        XiaomiAccountManager am = getAM();
        Account xiaomiAccount = am.getXiaomiAccount();
        if (xiaomiAccount == null) {
            return null;
        }
        try {
            return am.getUserData(xiaomiAccount, "encrypted_user_id");
        } catch (SecurityException unused) {
            AccountLogger.log(TAG, "failed to getUserData");
            if (!XiaomiAccountManager.isSystemAccountInstalled(this.mContext)) {
                throw new IllegalStateException("not supposed to be here");
            }
            return getFromXiaomiAccountApp(xiaomiAccount);
        }
    }

    boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    String getFromXiaomiAccountApp(Account account) {
        return new MiuiCUserIdUtil(this.mContext, account).getCUserId();
    }

    XiaomiAccountManager getAM() {
        return XiaomiAccountManager.get(this.mContext);
    }
}
