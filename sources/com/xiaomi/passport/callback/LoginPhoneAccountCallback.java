package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LoginPhoneAccountCallback implements PhoneLoginController.TicketLoginCallback {
    public final Context context;

    public abstract void onLoginFailed(String str);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public abstract void onLoginSuccess(AccountInfo accountInfo);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public abstract void onNeedNotification(String str, String str2);

    protected LoginPhoneAccountCallback(Context context) {
        this.context = context;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public void onPhoneNumInvalid() {
        onLoginFailed(this.context.getString(R.string.passport_error_phone));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public void onTicketOrTokenInvalid() {
        onLoginFailed(this.context.getString(R.string.passport_error_ticket_invalid));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public void onTzSignInvalid() {
        onLoginFailed(this.context.getString(R.string.passport_unknown_error));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public void onLoginFailed(PhoneLoginController.ErrorCode errorCode, String str, boolean z) {
        onLoginFailed(LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
        Context context = this.context;
        if (context instanceof Activity) {
            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context, passThroughErrorInfo);
        } else {
            onLoginFailed(LoginAndRegisterController.convertErrorCodeToMessage(context, errorCode));
        }
    }
}
