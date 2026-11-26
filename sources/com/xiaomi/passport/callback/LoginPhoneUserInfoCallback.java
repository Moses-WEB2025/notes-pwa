package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LoginPhoneUserInfoCallback implements PhoneLoginController.TicketLoginCallback {
    private static final String TAG = "LoginPhoneUserInfoCallback";
    public final Context context;

    public abstract void onLoginFailed(String str);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public abstract void onLoginSuccess(AccountInfo accountInfo);

    protected LoginPhoneUserInfoCallback(Context context) {
        this.context = context;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
    public void onNeedNotification(String str, String str2) {
        Context context = this.context;
        context.startActivity(XiaomiAccountManager.get(context).getAccountNotificationActivityIntent(str, str2, null, null));
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
        AccountLogger.log(TAG, "onTzSignInvalid");
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
