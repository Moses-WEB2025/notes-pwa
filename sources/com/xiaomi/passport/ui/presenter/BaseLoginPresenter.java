package com.xiaomi.passport.ui.presenter;

import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.ui.data.PhoneAccount;

/* JADX INFO: loaded from: classes4.dex */
public class BaseLoginPresenter {

    public interface OnBaseLoginListener {
        void onError(String str);

        void onGetConfigSuccess(LoginPreference loginPreference);

        void onLoginSuccess(AccountInfo accountInfo);

        void onNeedCaptchaCode(String str, String str2);

        void onNeedChooseLogin(RegisterUserInfo registerUserInfo);

        void onNeedWebAuth(String str);

        void onQueryUserInfo(RegisterUserInfo registerUserInfo);

        void onSendTicketSuccessWithPhoneAccount(PhoneAccount phoneAccount, int i);

        void onSentSuccessWithPhoneNum(int i);
    }
}
