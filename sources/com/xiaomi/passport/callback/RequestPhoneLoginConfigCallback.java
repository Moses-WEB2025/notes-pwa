package com.xiaomi.passport.callback;

import android.app.Activity;
import android.content.Context;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.uicontroller.PhoneLoginController;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RequestPhoneLoginConfigCallback implements PhoneLoginController.PhoneLoginConfigExtensionCallback {
    public final Context context;

    public abstract void onFailed(String str);

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneLoginConfigExtensionCallback
    public abstract void onSuccess(LoginPreference loginPreference);

    public RequestPhoneLoginConfigCallback(Context context) {
        this.context = context;
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneLoginConfigExtensionCallback
    public void onPhoneNumInvalid() {
        onFailed(this.context.getString(R.string.passport_error_phone));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneLoginConfigExtensionCallback
    public void onError(PhoneLoginController.ErrorCode errorCode, String str) {
        onFailed(LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
    }

    @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneLoginConfigExtensionCallback
    public void onServerError(PhoneLoginController.ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo) {
        Context context = this.context;
        if (context instanceof Activity) {
            ServerPassThroughErrorHandler.handleServerPassThroughError((Activity) context, passThroughErrorInfo);
        } else {
            onFailed(LoginAndRegisterController.convertErrorCodeToMessage(context, errorCode));
        }
    }
}
