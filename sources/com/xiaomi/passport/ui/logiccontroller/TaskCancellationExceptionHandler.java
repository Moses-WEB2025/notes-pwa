package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import java.util.concurrent.CancellationException;

/* JADX INFO: loaded from: classes4.dex */
public class TaskCancellationExceptionHandler extends ExceptionHandler {
    public TaskCancellationExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        return (th instanceof InterruptedException) || (th instanceof CancellationException);
    }
}
