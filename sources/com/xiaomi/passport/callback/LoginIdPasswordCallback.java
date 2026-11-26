package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LoginIdPasswordCallback implements PhoneLoginController.PasswordLoginCallback {
    public final Context context;

    public abstract void onLoginFailed(String str);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
    public abstract void onLoginSuccess(AccountInfo accountInfo);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
    public abstract void onNeedCaptchaCode(boolean z, String str);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
    public abstract void onNeedNotification(String str, String str2);

    public LoginIdPasswordCallback(Context context) {
        this.context = context;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
    public void onLoginByStep2(Step2LoginParams step2LoginParams) {
        throw new IllegalStateException("should never happens");
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
    public void onLoginFailed(PhoneLoginController.ErrorCode errorCode, String str, boolean z) {
        onLoginFailed(LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
    public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
        Context context = this.context;
        if (context instanceof Activity) {
            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context, passThroughErrorInfo);
        } else {
            onLoginFailed(LoginAndRegisterController.convertErrorCodeToMessage(context, errorCode));
        }
    }
}
