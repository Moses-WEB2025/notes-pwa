package com.xiaomi.passport.ui.internal;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.ui.internal.util.LoginUIController;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.PassportPageIntent;
import com.xiaomi.passport.ui.view.EditTextGroupView;
import com.xiaomi.passport.utils.AccountHelper;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes4.dex */
public class ConfirmCredentialActivity extends ConfirmCredentialBaseActivity {
    private static final int REQUEST_CODE_NOTIFICATION_LOGIN = 2;
    private static final int REQUEST_CODE_SET_PASSWORD = 1;
    private Account mAccount;
    private EditTextGroupView mEtgvCaptcha;
    private EditTextGroupView mEtgvPassword;
    private LoginUIController mLoginUIController;
    private boolean mReturnStsUrl;
    protected String mServiceId;
    private String mUserId;
    private final AtomicBoolean responseHandled = new AtomicBoolean(false);

    @Override // com.xiaomi.passport.ui.internal.ConfirmCredentialBaseActivity
    protected boolean needTranslucentStatusBar() {
        return false;
    }

    @Override // com.xiaomi.passport.ui.internal.ConfirmCredentialBaseActivity, com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        if (!getIntent().getBooleanExtra(AccountIntent.EXTRA_HAS_PASSWORD, true)) {
            Intent modifyPasswordPageIntent = PassportPageIntent.getModifyPasswordPageIntent(this);
            modifyPasswordPageIntent.putExtras(getIntent());
            modifyPasswordPageIntent.setPackage(getPackageName());
            startActivityForResult(modifyPasswordPageIntent, 1);
            return;
        }
        setContentView(R.layout.passport_activity_confirm_password);
        String stringExtra = getIntent().getStringExtra("userId");
        this.mUserId = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            finish();
            return;
        }
        ((TextView) findViewById(R.id.user_id)).setText(getString(R.string.passport_account_name, new Object[]{this.mUserId}));
        this.mAccount = new Account(this.mUserId, "com.xiaomi");
        String stringExtra2 = getIntent().getStringExtra("service_id");
        this.mServiceId = stringExtra2;
        if (TextUtils.isEmpty(stringExtra2)) {
            this.mServiceId = "passportapi";
        }
        this.mReturnStsUrl = getIntent().getBooleanExtra(AccountIntent.EXTRA_RETURN_STS_URL, false);
        this.mLoginUIController = new LoginUIController(this);
        this.mEtgvPassword = (EditTextGroupView) findViewById(R.id.password);
        this.mEtgvCaptcha = (EditTextGroupView) findViewById(R.id.captcha);
        getWindow().addFlags(8192);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            setResult(i2);
            finish();
        } else {
            if (i != 2) {
                return;
            }
            if (i2 == -1) {
                setResult(-1);
                finish();
            } else {
                AccountToast.showToastMessage(this, R.string.passport_relogin_notice);
            }
        }
    }

    @Override // android.app.Activity
    public void finish() {
        XiaomiAccountManager.get(this).handleAccountAuthenticatorResponse(getIntent().getParcelableExtra("accountAuthenticatorResponse"), null);
        super.finish();
    }

    public void onCancelClicked(View view) {
        statClick(R.string.passport_stat_tip_confirm_credential_page_click_cancel);
        finish();
    }

    public void onOkClicked(View view) {
        String inputText;
        statClick(R.string.passport_stat_tip_confirm_credential_page_click_ensure);
        String inputText2 = this.mEtgvPassword.getInputText();
        if (TextUtils.isEmpty(inputText2)) {
            AccountToast.showToastMessage(this, getString(R.string.passport_error_empty_pwd));
            return;
        }
        if (this.mEtgvCaptcha.getVisibility() == 0) {
            inputText = this.mEtgvCaptcha.getInputText();
            if (TextUtils.isEmpty(inputText)) {
                return;
            }
        } else {
            inputText = null;
        }
        loginByPassword(this.mUserId, inputText2, inputText, this.mEtgvCaptcha.getCaptchaIck(), this.mServiceId);
    }

    public void onForgetPasswordClicked(View view) {
        statClick(R.string.passport_stat_tip_confirm_credential_page_click_forget_password);
        startActivity(PassportPageIntent.getFindPasswordPageIntent(this, null));
    }

    protected void loginByPassword(String str, String str2, String str3, String str4, final String str5) {
        this.mLoginUIController.loginByPassword(new PasswordLoginParams.Builder().setUserId(str).setCaptCode(str3).setCaptIck(str4).setPassword(str2).setServiceId(str5).setIsReturnStsUrl(this.mReturnStsUrl).build(), new LoginUIController.PasswordLoginCallback() { // from class: com.xiaomi.passport.ui.internal.ConfirmCredentialActivity.1
            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onLoginByStep2(Step2LoginParams step2LoginParams) {
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onLoginSuccess(AccountInfo accountInfo) {
                XiaomiAccountManager.get(ConfirmCredentialActivity.this).addAccountOrUpdatePassToken(accountInfo);
                ConfirmCredentialActivity.this.processLoginSuccess(accountInfo);
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onNeedCaptchaCode(boolean z, String str6) {
                if (ConfirmCredentialActivity.this.mEtgvCaptcha.getVisibility() != 0) {
                    ConfirmCredentialActivity.this.applyCaptchaUrl(str6);
                } else {
                    ConfirmCredentialActivity.this.applyCaptchaUrl(str6);
                }
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onNeedNotification(String str6, String str7) {
                ConfirmCredentialActivity.this.startActivityForResult(XiaomiAccountManager.get(ConfirmCredentialActivity.this).getAccountNotificationActivityIntent(str5, str7, ConfirmCredentialActivity.this.getIntent().getExtras(), ConfirmCredentialActivity.this.getIntent().getParcelableExtra("accountAuthenticatorResponse")), 2);
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onLoginFailed(int i) {
                AccountToast.showToastMessage(ConfirmCredentialActivity.this, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processLoginSuccess(AccountInfo accountInfo) {
        XiaomiAccountManager.get(this).updateAccountInfo(this.mAccount, accountInfo);
        handleResponseIfNeeded(AccountHelper.getAuthenticatorResponseBundle(accountInfo, getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false)));
        setResult(-1);
        finish();
    }

    protected void handleResponseIfNeeded(Bundle bundle) {
        Bundle extras;
        if (this.responseHandled.compareAndSet(false, true) && (extras = getIntent().getExtras()) != null) {
            XiaomiAccountManager.get(this).handleAccountAuthenticatorResponse(extras.getParcelable("accountAuthenticatorResponse"), bundle);
        }
    }

    protected void applyCaptchaUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mEtgvCaptcha.setVisibility(8);
        } else {
            this.mEtgvCaptcha.setVisibility(0);
            this.mEtgvCaptcha.setupCaptcha(URLs.ACCOUNT_DOMAIN + str);
        }
    }
}
