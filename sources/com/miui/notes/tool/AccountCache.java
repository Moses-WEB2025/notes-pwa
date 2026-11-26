package com.miui.notes.tool;

import android.accounts.Account;
import android.content.Context;
import com.miui.notes.base.utils.Logger;
import miui.accounts.ExtraAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class AccountCache {
    private static final String TAG = "AccountCache";
    private volatile Account mAccount;
    private volatile boolean mCached;

    private static class Holder {
        static final AccountCache INSTANCE = new AccountCache();

        private Holder() {
        }
    }

    public static AccountCache getInstance() {
        return Holder.INSTANCE;
    }

    private AccountCache() {
    }

    public void clear() {
        this.mAccount = null;
        this.mCached = false;
    }

    public Account getAccount(Context context) {
        if (PreferenceUtils.getCleanAccountCachePref()) {
            PreferenceUtils.setCleanAccountCachePref(false);
            clear();
        }
        if (!this.mCached) {
            try {
                this.mAccount = ExtraAccountManager.getXiaomiAccount(context);
                this.mCached = true;
            } catch (SecurityException e) {
                Logger.INSTANCE.e(TAG, "Fail to get xiaomi account,exception:" + e);
            }
        }
        return this.mAccount;
    }

    public boolean noAccountExists(Context context) {
        return getAccount(context) == null && this.mCached;
    }
}
