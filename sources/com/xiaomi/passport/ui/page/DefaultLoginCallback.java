package com.xiaomi.passport.ui.page;

import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.presenter.BaseLoginPresenter;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DefaultLoginCallback implements BaseLoginPresenter.OnBaseLoginListener {
    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onError(String str) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onGetConfigSuccess(LoginPreference loginPreference) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onLoginSuccess(AccountInfo accountInfo) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onNeedCaptchaCode(String str, String str2) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onNeedChooseLogin(RegisterUserInfo registerUserInfo) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onNeedWebAuth(String str) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onQueryUserInfo(RegisterUserInfo registerUserInfo) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onSendTicketSuccessWithPhoneAccount(PhoneAccount phoneAccount, int i) {
    }

    @Override // com.xiaomi.passport.ui.presenter.BaseLoginPresenter.OnBaseLoginListener
    public void onSentSuccessWithPhoneNum(int i) {
    }
}
