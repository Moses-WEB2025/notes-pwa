package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.callback.LoginIdPasswordCallback;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;
import com.xiaomi.passport.ui.utils.PassportPageIntent;
import com.xiaomi.passport.ui.view.AgreementView;
import com.xiaomi.passport.ui.view.EditTextGroupView;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;

/* JADX INFO: loaded from: classes4.dex */
public class PasswordLoginFragment extends BaseLoginFragment implements View.OnClickListener {
    public static final String KEY_INT_LOGIN_COUNTRY_CODE = "login_country_code";
    public static final String KEY_STRING_LOGIN_PHONE_NUMBER = "login_phone_number";
    private AgreementView mAgreementView;
    private Button mBtnLogin;
    private int mCountryCode;
    private EditTextGroupView mEtgvLoginId;
    private EditTextGroupView mEtgvPassword;
    private long mLastClickLoginBtn = 0;
    private SimpleFutureTask<AccountInfo> mLoginTask;
    private TextView mTvFindPassword;
    private TextView mTvGotoH5Register;
    private TextView mTvVerifyCodeLogin;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_fragment_password_login, viewGroup, false);
        initViews(viewInflate);
        return viewInflate;
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initSettings();
        initVars();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        releaseVars();
        super.onDestroyView();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mTvVerifyCodeLogin) {
            statClick(R.string.passport_stat_tip_password_login_page_click_phone_ticket_login);
            this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, false, false);
            return;
        }
        if (view == this.mTvFindPassword) {
            statClick(R.string.passport_stat_tip_password_login_page_click_forget_password);
            startActivity(PassportPageIntent.getFindPasswordPageIntent(getContext(), this.mExtBannerBiz));
            return;
        }
        if (view == this.mTvGotoH5Register) {
            statClick(R.string.passport_stat_tip_password_login_page_click_register);
            startActivity(PassportPageIntent.getRegisterPageIntent(getContext(), this.mSid, null, this.mExtBannerBiz));
            return;
        }
        if (view == this.mBtnLogin && checkLoginContinuousClick()) {
            statClick(R.string.passport_stat_tip_password_login_page_click_login);
            if (TextUtils.isEmpty(this.mEtgvLoginId.getInputText())) {
                AccountToast.showToastMessage(getActivity(), R.string.passport_error_empty_user_id);
                return;
            }
            if (TextUtils.isEmpty(this.mEtgvPassword.getInputText())) {
                AccountToast.showToastMessage(getActivity(), R.string.passport_error_empty_password);
            } else if (!this.mAgreementView.isUserAgreedProtocol()) {
                onCallAgreementConfirm(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PasswordLoginFragment.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        PasswordLoginFragment.this.mAgreementView.setUserAgreementSelected(true);
                        PasswordLoginFragment.this.login();
                    }
                });
            } else {
                login();
            }
        }
    }

    private boolean checkLoginContinuousClick() {
        if (this.mLastClickLoginBtn <= 0) {
            this.mLastClickLoginBtn = System.currentTimeMillis();
            return true;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.mLastClickLoginBtn < 1000) {
            return false;
        }
        this.mLastClickLoginBtn = jCurrentTimeMillis;
        return true;
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public boolean isUserAgreementSelected() {
        return this.mAgreementView.isUserAgreedProtocol();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public String getAgreements() {
        return this.mAgreementView.getAppAgreement();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public void setUserAgreementState(boolean z) {
        this.mAgreementView.setUserAgreementSelected(z);
    }

    private void initViews(View view) {
        this.mEtgvLoginId = (EditTextGroupView) view.findViewById(R.id.login_id);
        this.mEtgvPassword = (EditTextGroupView) view.findViewById(R.id.password);
        this.mTvFindPassword = (TextView) view.findViewById(R.id.find_password);
        this.mBtnLogin = (Button) view.findViewById(R.id.login);
        this.mAgreementView = (AgreementView) view.findViewById(R.id.agreement_view);
        this.mTvVerifyCodeLogin = (TextView) view.findViewById(R.id.verify_code_login);
        this.mTvGotoH5Register = (TextView) view.findViewById(R.id.goto_h5_register);
        this.mTvFindPassword.setOnClickListener(this);
        this.mBtnLogin.setOnClickListener(this);
        this.mTvVerifyCodeLogin.setOnClickListener(this);
        this.mTvGotoH5Register.setOnClickListener(this);
    }

    private void initSettings() {
        this.mLoginUIAction.showSNSLoginFragment(true);
        this.mAgreementView.setLoginAgreementAndPrivacy(this.mLoginAgreementAndPrivacy);
        this.mAgreementView.updateUserAgreement(null);
        this.mAgreementView.setVisibility(this.mNeedShowUserAgreement ? 0 : 8);
    }

    private void initVars() {
        Bundle fullArguments = getFullArguments();
        if (fullArguments.getString(KEY_STRING_LOGIN_PHONE_NUMBER, null) != null) {
            this.mCountryCode = fullArguments.getInt(KEY_INT_LOGIN_COUNTRY_CODE);
            this.mEtgvLoginId.setInputText(fullArguments.getString(KEY_STRING_LOGIN_PHONE_NUMBER));
            this.mEtgvLoginId.setCountryCode(this.mCountryCode);
            this.mEtgvLoginId.setEnabled(false);
        }
    }

    private void releaseVars() {
        SimpleFutureTask<AccountInfo> simpleFutureTask = this.mLoginTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mLoginTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void login() {
        this.mLoginLoadingDialog.show(R.string.passport_dialog_doing_login);
        SimpleFutureTask<AccountInfo> simpleFutureTask = this.mLoginTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
        }
        FragmentActivity activity = getActivity();
        String loginId = getLoginId();
        String inputText = this.mEtgvPassword.getInputText();
        int i = this.mCountryCode;
        this.mLoginTask = LoginAndRegisterController.loginIdPassword(activity, loginId, inputText, i == 0 ? "" : CountryCodePhoneNumber.getCountryCodeAsString(i), this.mSid, null, null, new _LoginIdPasswordCallback(getContext()));
    }

    private class _LoginIdPasswordCallback extends LoginIdPasswordCallback {
        private _LoginIdPasswordCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.LoginIdPasswordCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
        public void onLoginSuccess(AccountInfo accountInfo) {
            if (PasswordLoginFragment.this.isActivityAlive()) {
                LoginAndRegisterController.storePassToken(PasswordLoginFragment.this.getActivity(), accountInfo);
                LoginAndRegisterController.finishWithLoginResult(PasswordLoginFragment.this.getActivity(), accountInfo, PasswordLoginFragment.this.mLoginAgreementAndPrivacy);
                PasswordLoginFragment.this.mLoginLoadingDialog.dismiss();
            }
        }

        @Override // com.xiaomi.passport.callback.LoginIdPasswordCallback
        public void onLoginFailed(String str) {
            if (PasswordLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(str);
                PasswordLoginFragment.this.mLoginLoadingDialog.dismiss();
                PasswordLoginFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginIdPasswordCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
        public void onNeedCaptchaCode(boolean z, String str) {
            if (PasswordLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set("NeedCaptchaCode");
                PasswordLoginFragment.this.mLoginLoadingDialog.dismiss();
                PasswordLoginFragment.this.showCaptcha(str, new BaseLoginFragment.OnCaptchaCallback() { // from class: com.xiaomi.passport.ui.page.PasswordLoginFragment._LoginIdPasswordCallback.1
                    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment.OnCaptchaCallback
                    public void onCaptcha(String str2, String str3) {
                        if (PasswordLoginFragment.this.isActivityAlive()) {
                            PasswordLoginFragment.this.mLoginLoadingDialog.show(R.string.passport_dialog_doing_login);
                            if (PasswordLoginFragment.this.mLoginTask != null) {
                                PasswordLoginFragment.this.mLoginTask.cancel(true);
                            }
                            PasswordLoginFragment.this.mLoginTask = LoginAndRegisterController.loginIdPassword(PasswordLoginFragment.this.getActivity(), PasswordLoginFragment.this.getLoginId(), PasswordLoginFragment.this.mEtgvPassword.getInputText(), PasswordLoginFragment.this.mCountryCode == 0 ? "" : CountryCodePhoneNumber.getCountryCodeAsString(PasswordLoginFragment.this.mCountryCode), PasswordLoginFragment.this.mSid, str2, str3, new _LoginIdPasswordCallback(PasswordLoginFragment.this.getContext()));
                        }
                    }
                });
            }
        }

        @Override // com.xiaomi.passport.callback.LoginIdPasswordCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
        public void onNeedNotification(String str, String str2) {
            if (PasswordLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set("NeedNotification");
                PasswordLoginFragment.this.mLoginLoadingDialog.dismiss();
                PasswordLoginFragment.this.startActivity(XiaomiAccountManager.get(this.context).getAccountNotificationActivityIntent("passportapi", str2, null, null));
            }
        }

        @Override // com.xiaomi.passport.callback.LoginIdPasswordCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PasswordLoginCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
            if (PasswordLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(passThroughErrorInfo.getTips(), LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
                PasswordLoginFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, passThroughErrorInfo);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getLoginId() {
        if (this.mCountryCode != 0) {
            return CountryCodePhoneNumber.getCountryCodeAsString(this.mCountryCode) + this.mEtgvLoginId.getInputText();
        }
        return this.mEtgvLoginId.getInputText();
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment
    protected BaseFragment.StatParams getStatParams() {
        return new BaseFragment.StatParams("密码登录页面", getString(R.string.passport_stat_tip_password_login_page_browse));
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    protected String getLoginTypeStatName() {
        if (getFullArguments().getString(KEY_STRING_LOGIN_PHONE_NUMBER, null) != null) {
            return "手机号-密码";
        }
        return "ID-密码";
    }
}
