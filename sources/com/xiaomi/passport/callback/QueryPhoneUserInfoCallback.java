package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class QueryPhoneUserInfoCallback implements PhoneLoginController.PhoneUserInfoCallback {
    public final Context context;

    public abstract void onQueryFailed(String str);

    public abstract void onQuerySuccessful(RegisterUserInfo registerUserInfo);

    public QueryPhoneUserInfoCallback(Context context) {
        this.context = context;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onRecycledOrNotRegisteredPhone(RegisterUserInfo registerUserInfo) {
        onQuerySuccessful(registerUserInfo);
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onNotRecycledRegisteredPhone(RegisterUserInfo registerUserInfo) {
        onQuerySuccessful(registerUserInfo);
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onProbablyRecycleRegisteredPhone(RegisterUserInfo registerUserInfo) {
        onQuerySuccessful(registerUserInfo);
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onPhoneNumInvalid() {
        onQueryFailed(this.context.getString(R.string.passport_error_phone));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onTicketOrTokenInvalid() {
        onQueryFailed(this.context.getString(R.string.passport_error_ticket_invalid));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onQueryFailed(PhoneLoginController.ErrorCode errorCode, String str) {
        onQueryFailed(LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
    public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
        Context context = this.context;
        if (context instanceof Activity) {
            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context, passThroughErrorInfo);
        } else {
            onQueryFailed(LoginAndRegisterController.convertErrorCodeToMessage(context, errorCode));
        }
    }
}
