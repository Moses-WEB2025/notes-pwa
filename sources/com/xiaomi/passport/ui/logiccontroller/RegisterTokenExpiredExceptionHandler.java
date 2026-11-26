package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.passport.ui.internal.AddAccountListener;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.PassportPageIntent;

/* JADX INFO: loaded from: classes4.dex */
public class RegisterTokenExpiredExceptionHandler extends ExceptionHandler {
    private final AddAccountListener addAccountListener;
    private final String sid;

    public RegisterTokenExpiredExceptionHandler(AddAccountListener addAccountListener, String str, ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.addAccountListener = addAccountListener;
        this.sid = str;
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof TokenExpiredException)) {
            return false;
        }
        AccountToast.showToastMessage(context, R.string.passport_request_error_invalid_token);
        context.startActivity(PassportPageIntent.getRegisterPageIntent(context, this.sid, null, null));
        return true;
    }
}
