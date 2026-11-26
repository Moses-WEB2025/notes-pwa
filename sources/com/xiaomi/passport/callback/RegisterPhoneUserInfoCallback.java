package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.ui.utils.PassportPageIntent;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RegisterPhoneUserInfoCallback implements PhoneLoginController.PhoneRegisterCallback {
    public final Context context;
    public final String sid;

    public abstract void onRegisterFailed(String str);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
    public abstract void onRegisterSuccess(AccountInfo accountInfo);

    public RegisterPhoneUserInfoCallback(Context context, String str) {
        this.context = context;
        this.sid = str;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
    public void onRegisterReachLimit() {
        onRegisterFailed(this.context.getString(R.string.passport_register_restricted));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
    public void onTokenExpired() {
        onRegisterFailed(this.context.getString(R.string.passport_request_error_invalid_token));
        Context context = this.context;
        context.startActivity(PassportPageIntent.getRegisterPageIntent(context, this.sid, null, null));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
    public void onRegisterFailed(PhoneLoginController.ErrorCode errorCode, String str) {
        onRegisterFailed(LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
    public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
        Context context = this.context;
        if (context instanceof Activity) {
            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context, passThroughErrorInfo);
        } else {
            onRegisterFailed(LoginAndRegisterController.convertErrorCodeToMessage(context, errorCode));
        }
    }
}
