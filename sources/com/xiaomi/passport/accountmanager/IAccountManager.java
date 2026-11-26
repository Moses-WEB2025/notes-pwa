package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OnAccountsUpdateListener;
import android.os.Bundle;
import android.os.Handler;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public interface IAccountManager {
    boolean addAccountExplicitly(AccountInfo accountInfo, Bundle bundle);

    void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z);

    void clearPassword(Account account);

    AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    int getAccountVisibility(Account account, String str);

    String getPassword(Account account);

    String getUserData(Account account, String str);

    Map<String, String> getUserData(Account account, Set<String> set);

    void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener);

    boolean setAccountVisibility(Account account, String str, int i);

    void setPassword(Account account, String str);

    void setUserData(Account account, String str, String str2);

    void setUserData(Account account, Map<String, String> map);
}
