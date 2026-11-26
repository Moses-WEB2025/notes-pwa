package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.ui.uicontroller.AccountLoginController;

/* JADX INFO: loaded from: classes4.dex */
public class SnsLoginBeforeBindExceptionHandler extends ExceptionHandler {
    private final AccountLoginController loginController;

    public SnsLoginBeforeBindExceptionHandler(AccountLoginController accountLoginController, ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
        this.loginController = accountLoginController;
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof SNSRequest.NeedLoginForBindException)) {
            return false;
        }
        this.loginController.onUseManualInputLogin();
        return true;
    }
}
