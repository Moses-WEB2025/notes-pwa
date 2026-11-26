package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.utils.AccountToast;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultExceptionHandler extends ExceptionHandler {
    public DefaultExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        AccountToast.showToastMessage(context, R.string.passport_request_error_unknown);
        return true;
    }
}
