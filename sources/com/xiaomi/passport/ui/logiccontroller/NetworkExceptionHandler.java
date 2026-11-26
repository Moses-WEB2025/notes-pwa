package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.ui.utils.AccountToast;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkExceptionHandler extends ExceptionHandler {
    public NetworkExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    public boolean handle(Context context, Throwable th) {
        if (!(th instanceof IOException)) {
            return false;
        }
        AccountToast.showToastMessage(context, R.string.passport_request_error_network);
        return true;
    }
}
