package com.xiaomi.passport.ui.logiccontroller;

import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.ui.utils.AccountToast;

/* JADX INFO: loaded from: classes4.dex */
public class SnsBindLimitExceptionHandler extends ExceptionHandler {
    public SnsBindLimitExceptionHandler(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

    @Override // com.xiaomi.passport.ui.logiccontroller.ExceptionHandler
    protected boolean handle(Context context, Throwable th) {
        if (!(th instanceof SNSRequest.BindLimitException)) {
            return false;
        }
        AccountToast.showToastMessage(context, R.string.passport_sns_bind_limit);
        return true;
    }
}
