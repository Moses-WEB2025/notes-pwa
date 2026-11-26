package com.xiaomi.passport.snscorelib;

import android.content.Context;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.verificationsdk.internal.CommonUtils;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class PassportSNSController {
    public static SimpleFutureTask<Boolean> checkAppIsCanUseSnsLogin(Context context, SNSAuthProvider sNSAuthProvider, final PhoneLoginController.CheckAppIsCanUseSnsLoginCallback checkAppIsCanUseSnsLoginCallback) {
        if (checkAppIsCanUseSnsLoginCallback == null) {
            throw new IllegalArgumentException("should implements check app is can use sns login callback");
        }
        if (sNSAuthProvider == null) {
            throw new IllegalArgumentException(sNSAuthProvider + "must not be null");
        }
        return PhoneLoginController.checkAppIsCanUseSnsLogin(Constants.PASSPORT_SID, sNSAuthProvider.getAppId(context), CommonUtils.getLocaleString(Locale.getDefault()), new PhoneLoginController.CheckAppIsCanUseSnsLoginCallback() { // from class: com.xiaomi.passport.snscorelib.PassportSNSController.1
            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.CheckAppIsCanUseSnsLoginCallback
            public void onSuccess(boolean z) {
                checkAppIsCanUseSnsLoginCallback.onSuccess(z);
            }

            @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.CheckAppIsCanUseSnsLoginCallback
            public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
                checkAppIsCanUseSnsLoginCallback.onServerError(errorCode, passThroughErrorInfo);
            }
        });
    }
}
