package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RequestPhoneVerifyCodeCallback implements PhoneLoginController.SendPhoneTicketExtensionCallback {
    public final Context context;

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public abstract void onNeedCaptchaCode(String str, String str2);

    public abstract void onRequestFailed(String str);

    public abstract void onRequestSuccessful(int i);

    public RequestPhoneVerifyCodeCallback(Context context) {
        this.context = context;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public void onSentSuccess(int i) {
        onRequestSuccessful(i);
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public void onActivatorTokenExpired() {
        onRequestFailed(this.context.getString(R.string.passport_error_token_expired));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public void onSMSReachLimit() {
        onRequestFailed(this.context.getString(R.string.passport_error_sms_limit));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public void onPhoneNumInvalid() {
        onRequestFailed(this.context.getString(R.string.passport_error_phone));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public void onSentFailed(PhoneLoginController.ErrorCode errorCode, String str) {
        onRequestFailed(LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
    public void onServerError(PhoneLoginController.ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo) {
        Context context = this.context;
        if (context instanceof Activity) {
            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context, passThroughErrorInfo);
        } else {
            onRequestFailed(LoginAndRegisterController.convertErrorCodeToMessage(context, errorCode));
        }
    }
}
