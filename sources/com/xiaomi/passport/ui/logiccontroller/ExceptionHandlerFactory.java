package com.xiaomi.passport.ui.logiccontroller;

import android.app.Activity;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.AddAccountListener;
import com.xiaomi.passport.ui.internal.SNSAuthCredential;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import com.xiaomi.passport.ui.uicontroller.AccountLoginController;

/* JADX INFO: loaded from: classes4.dex */
public class ExceptionHandlerFactory {
    public static ExceptionHandler buildForSnsLogin(Activity activity, AccountLoginController accountLoginController, AddAccountListener addAccountListener, SNSAuthProvider sNSAuthProvider, SNSAuthCredential sNSAuthCredential) {
        return new RedirectNotificationPageExceptionHandler(new SnsNeedWebLoginExceptionHandler(new SnsLoginBeforeBindExceptionHandler(accountLoginController, new SnsBindLimitExceptionHandler(new NetworkExceptionHandler(new DefaultExceptionHandler(null))))));
    }

    public static ExceptionHandler buildForPhoneNumberAutoRegisterAndLogin(Activity activity, AccountLoginController accountLoginController, AddAccountListener addAccountListener, String str, PhoneAccount phoneAccount) {
        return new RedirectNotificationPageExceptionHandler(new TaskCancellationExceptionHandler(new RegisterUserRestrictedExceptionHandler(new RegisterTokenExpiredExceptionHandler(addAccountListener, str, new RegisterReachLimitExceptionHandler(new LoginInvalidTokenExceptionHandler(accountLoginController, phoneAccount, new NetworkExceptionHandler(new DefaultExceptionHandler(null))))))));
    }
}
