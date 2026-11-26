package com.xiaomi.passport.ui.uicontroller;

import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.SNSAuthCredential;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;

/* JADX INFO: loaded from: classes4.dex */
public interface AccountLoginController {
    boolean isUserAgreedProtocol();

    void onQueryPhoneAccountFailed();

    void onRequestPhoneLogin(PhoneAccount phoneAccount);

    void onRequestPhoneRegisterAndLogin(PhoneAccount phoneAccount);

    void onRequestSendTicketAndLogin(PhoneAccount phoneAccount);

    void onRequestSnsLogin(SNSAuthProvider sNSAuthProvider, SNSAuthCredential sNSAuthCredential);

    void onSkipQueryPhoneAccount();

    void onSnsLoginFailed();

    void onUseManualInputLogin();
}
