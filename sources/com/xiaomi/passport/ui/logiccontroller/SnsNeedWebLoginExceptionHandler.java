package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.ui.utils.PassportPageIntent;

/* JADX INFO: loaded from: classes4.dex */
public class SnsNeedWebLoginExceptionHandler extends ExceptionHandler {
    public SnsNeedWebLoginExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof SNSRequest.RedirectToWebLoginException)) {
            return false;
        }
        context.startActivity(PassportPageIntent.getSNSBindPageIntent(context, ((SNSRequest.RedirectToWebLoginException) th).getSNSBindParameter()));
        return true;
    }
}
