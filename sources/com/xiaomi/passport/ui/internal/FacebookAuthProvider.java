package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FacebookAuthProvider extends SNSAuthProvider implements FacebookCallback<LoginResult> {
    private static final String EMAIL = "email";
    public static final String FACEBOOK_AUTH_TAG = "FacebookAuthProvider";
    private static final String PUBLIC_PROFILE = "public_profile";
    private Context context;
    private List<String> mScopes;
    private CallbackManager sCallbackManager;

    public FacebookAuthProvider() {
        super("facebook");
        this.mScopes = new ArrayList();
        this.sCallbackManager = null;
    }

    public void onSuccess(LoginResult loginResult) {
        storeSnsToken(this.context, loginResult.getAccessToken().getToken());
    }

    public void onCancel() {
        AccountLogger.log(FACEBOOK_AUTH_TAG, "onCancel");
    }

    public void onError(FacebookException facebookException) {
        if (facebookException != null) {
            AccountToast.showToastMessage(this.context, facebookException.getMessage());
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.passport_ic_sns_facebook;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public String getAppId(Context context) {
        return context.getString(R.string.facebook_application_id);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void startLogin(Activity activity) {
        this.context = activity.getApplicationContext();
        this.sCallbackManager = CallbackManager.Factory.create();
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(this.sCallbackManager, this);
        ArrayList arrayList = new ArrayList(this.mScopes);
        if (!arrayList.contains("email")) {
            arrayList.add("email");
        }
        if (!arrayList.contains(PUBLIC_PROFILE)) {
            arrayList.add(PUBLIC_PROFILE);
        }
        loginManager.logInWithReadPermissions(activity, arrayList);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        if (!FacebookSdk.isInitialized()) {
            return super.getRequestCode();
        }
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        CallbackManager callbackManager = this.sCallbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        } else {
            LoginFailedReasonHolder.set("FacebookAuthFail");
        }
    }
}
