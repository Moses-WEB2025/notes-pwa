package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.task.BgTask;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AuthProvider {
    public final String name;
    private BgTask<AccountInfo> signInAndStoreTask;

    protected abstract AccountInfo signInWithAuthCredential(Context context, AuthCredential authCredential) throws Throwable;

    public AuthProvider(String str) {
        this.name = str;
    }

    public void signInAndStoreAccount(final Context context, final AuthCredential authCredential, BgTask.SuccessResultRunnable<AccountInfo> successResultRunnable, BgTask.ErrorResultRunnable errorResultRunnable) {
        BgTask<AccountInfo> bgTask = this.signInAndStoreTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.signInAndStoreTask = null;
        }
        BgTask<AccountInfo> bgTask2 = new BgTask<>(new BgTask.BgTaskRunnable<AccountInfo>() { // from class: com.xiaomi.passport.ui.internal.AuthProvider.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
            public AccountInfo run() throws Throwable {
                AccountInfo accountInfoSignInWithAuthCredential = AuthProvider.this.signInWithAuthCredential(context, authCredential);
                AuthProvider.this.storePassToken(context, accountInfoSignInWithAuthCredential);
                if (!SNSAuthProvider.isBindingSnsAccount()) {
                    return accountInfoSignInWithAuthCredential;
                }
                NeedBindSnsException needBindSnsException = new NeedBindSnsException(SNSAuthProvider.sSNSBindParameter);
                SNSAuthProvider.invalidBindParameter();
                throw needBindSnsException;
            }
        }, successResultRunnable, errorResultRunnable);
        this.signInAndStoreTask = bgTask2;
        bgTask2.execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void storePassToken(Context context, AccountInfo accountInfo) {
        if (accountInfo == null || TextUtils.isEmpty(accountInfo.passToken)) {
            return;
        }
        XiaomiAccountManager.get(context).addAccountOrUpdatePassToken(accountInfo);
    }

    public void onProviderRecycle() {
        BgTask<AccountInfo> bgTask = this.signInAndStoreTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.signInAndStoreTask = null;
        }
    }
}
