package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.accountsdk.utils.AccountLogger;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ExceptionHandler {
    public final ExceptionHandler nextHandler;

    protected abstract boolean handle(Context context, Throwable th);

    public ExceptionHandler(ExceptionHandler exceptionHandler) {
        this.nextHandler = exceptionHandler;
    }

    public void handleException(Context context, Throwable th) {
        AccountLogger.log("ExceptionHandler", "handle exception", th);
        while (this != null) {
            if (this.handle(context, th)) {
                return;
            } else {
                this = this.nextHandler;
            }
        }
        throw new IllegalStateException("can not handle exception: " + th);
    }
}
