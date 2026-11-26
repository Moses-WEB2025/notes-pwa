package com.xiaomi.passport.ui.internal;

import androidx.fragment.app.Fragment;
import com.xiaomi.accountsdk.account.data.AccountInfo;

/* JADX INFO: loaded from: classes4.dex */
public interface AddAccountListener {
    void goBack(boolean z);

    void gotoFragment(Fragment fragment, boolean z);

    void loginCancelled();

    void loginSuccess(AccountInfo accountInfo);
}
