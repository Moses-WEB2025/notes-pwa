package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.passport.ui.utils.AccountToast;

/* JADX INFO: loaded from: classes4.dex */
public class RegisterUserRestrictedExceptionHandler extends ExceptionHandler {
    public RegisterUserRestrictedExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof UserRestrictedException)) {
            return false;
        }
        AccountToast.showToastMessage(context, R.string.passport_register_restricted);
        return true;
    }
}
