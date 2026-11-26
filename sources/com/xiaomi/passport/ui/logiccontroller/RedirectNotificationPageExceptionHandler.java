package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;

/* JADX INFO: loaded from: classes4.dex */
public class RedirectNotificationPageExceptionHandler extends ExceptionHandler {
    public RedirectNotificationPageExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof NeedNotificationException)) {
            return false;
        }
        context.startActivity(XiaomiAccountManager.get(context).getAccountNotificationActivityIntent("passportapi", ((NeedNotificationException) th).getNotificationUrl(), null, null));
        return true;
    }
}
