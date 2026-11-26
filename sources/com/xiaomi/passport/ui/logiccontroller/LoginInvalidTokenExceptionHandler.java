package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.uicontroller.AccountLoginController;
import com.xiaomi.passport.ui.utils.AccountToast;

/* JADX INFO: loaded from: classes4.dex */
public class LoginInvalidTokenExceptionHandler extends ExceptionHandler {
    private final AccountLoginController loginController;
    private final PhoneAccount phoneAccount;

    public LoginInvalidTokenExceptionHandler(AccountLoginController accountLoginController, PhoneAccount phoneAccount, ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.loginController = accountLoginController;
        this.phoneAccount = phoneAccount;
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof InvalidVerifyCodeException)) {
            return false;
        }
        AccountToast.showToastMessage(context, R.string.passport_request_error_invalid_token);
        this.loginController.onRequestSendTicketAndLogin(this.phoneAccount);
        return true;
    }
}
