package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.passport.PassportSDK;
import com.xiaomi.passport.callback.RequestPhoneLoginConfigCallback;
import com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback;
import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.ui.internal.CaptchaCode;
import com.xiaomi.passport.ui.internal.PickCountryCodeActivity;
import com.xiaomi.passport.ui.internal.VerificationCode;
import com.xiaomi.passport.ui.utils.AccountToast;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.ui.view.AgreementView;
import com.xiaomi.passport.ui.view.EditTextGroupView;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;
import com.xiaomi.passport.utils.LoginPreferenceConfig;
import com.xiaomi.verificationsdk.VerificationManager;
import com.xiaomi.verificationsdk.internal.ErrorInfo;
import com.xiaomi.verificationsdk.internal.VerifyError;
import com.xiaomi.verificationsdk.internal.VerifyResult;

/* JADX INFO: loaded from: classes4.dex */
public class InputPhoneNumberFragment extends BaseLoginFragment implements View.OnClickListener {
    private final int REQUEST_AREA_CODE = 3001;
    private AgreementView mAgreementView;
    private Button mBtnGetLoginType;
    private Button mBtnPasswordLogin;
    private EditTextGroupView mEtgvPhone;
    private SimpleFutureTask<LoginPreference> mLoginConfigTask;
    private RequestPhoneLoginConfigCallback mRequestPhoneLoginConfigCallback;
    private RequestPhoneVerifyCodeCallback mRequestPhoneVerifyCodeCallback;
    private SimpleFutureTask<Integer> mSendVerifyCodeTask;

    private void initVars() {
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mRequestPhoneVerifyCodeCallback = new _RequestPhoneVerifyCodeCallback(context);
        this.mRequestPhoneLoginConfigCallback = new _RequestPhoneLoginConfigCallback(context);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_fragment_input_phone_number, viewGroup, false);
        initViews(viewInflate);
        return viewInflate;
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initVars();
        initSettings();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        releaseVars();
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 3001 && i2 == -1) {
            updateCountryCode(intent.getIntExtra(PickCountryCodeActivity.KEY_INT_COUNTRY_CODE, 86));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mBtnGetLoginType) {
            statClick(R.string.passport_stat_tip_phone_ticket_login_page_click_next_step);
            if (!isUserAgreementSelected()) {
                showAgreementDialog(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.InputPhoneNumberFragment.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        InputPhoneNumberFragment.this.mAgreementView.setUserAgreementSelected(true);
                        InputPhoneNumberFragment.this.getLoginType();
                    }
                });
                return;
            } else {
                getLoginType();
                return;
            }
        }
        if (view == this.mBtnPasswordLogin) {
            statClick(R.string.passport_stat_tip_phone_ticket_login_page_click_password_login);
            this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.PASSWORD_LOGIN, null, false, false);
        }
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
        this.mAgreementView = (AgreementView) view.findViewById(R.id.agreement_view);
        this.mEtgvPhone = (EditTextGroupView) view.findViewById(R.id.phone);
        this.mBtnGetLoginType = (Button) view.findViewById(R.id.get_login_type);
        this.mBtnPasswordLogin = (Button) view.findViewById(R.id.password_login);
        this.mBtnGetLoginType.setOnClickListener(this);
        this.mBtnPasswordLogin.setOnClickListener(this);
    }

    private void initSettings() {
        this.mLoginUIAction.showSNSLoginFragment(true);
        this.mAgreementView.setLoginAgreementAndPrivacy(this.mLoginAgreementAndPrivacy);
        this.mAgreementView.updateUserAgreement(null);
        this.mAgreementView.setVisibility(this.mNeedShowUserAgreement ? 0 : 8);
        updateCountryCode(CountryCodePhoneNumber.getDefaultCountryCodeAsInt());
    }

    private void releaseVars() {
        SimpleFutureTask<LoginPreference> simpleFutureTask = this.mLoginConfigTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mLoginConfigTask = null;
        }
        SimpleFutureTask<Integer> simpleFutureTask2 = this.mSendVerifyCodeTask;
        if (simpleFutureTask2 != null) {
            simpleFutureTask2.cancel(true);
            this.mSendVerifyCodeTask = null;
        }
    }

    private void updateCountryCode(int i) {
        this.mEtgvPhone.initCountryCode(i, new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.InputPhoneNumberFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(InputPhoneNumberFragment.this.getActivity(), PickCountryCodeActivity.class);
                InputPhoneNumberFragment.this.startActivityForResult(intent, 3001);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLoginType() {
        String inputText = this.mEtgvPhone.getInputText();
        if (TextUtils.isEmpty(inputText)) {
            AccountToast.showToastMessage(getActivity(), R.string.passport_error_phone);
            return;
        }
        SimpleFutureTask<LoginPreference> simpleFutureTask = this.mLoginConfigTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
        }
        this.mLoginConfigTask = LoginAndRegisterController.requestPhoneLoginConfig(getActivity(), inputText, CountryCodePhoneNumber.getCountryCodeAsString(this.mEtgvPhone.getCountryCode()), PassportSDK.isInternational() ? "login" : LoginPreferenceConfig.TYPE_LOGIN_OR_REGISTER, this.mSid, this.mRequestPhoneLoginConfigCallback);
    }

    private class _RequestPhoneVerifyCodeCallback extends RequestPhoneVerifyCodeCallback {
        public _RequestPhoneVerifyCodeCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback
        public void onRequestSuccessful(int i) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                InputPhoneNumberFragment.this.mLoginLoadingDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("extra_phone", InputPhoneNumberFragment.this.mEtgvPhone.getInputText());
                bundle.putInt(VerifyCodeLoginFragment.KEY_INT_PHONE_COUNTRY_CODE, InputPhoneNumberFragment.this.mEtgvPhone.getCountryCode());
                bundle.putInt(VerifyCodeLoginFragment.KEY_INT_VERIFY_CODE_LENGTH, i);
                InputPhoneNumberFragment.this.fillOriginalPageStartTsMs(bundle);
                InputPhoneNumberFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.VERIFY_CODE_LOGIN, bundle, false, false);
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback
        public void onRequestFailed(String str) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                InputPhoneNumberFragment.this.mLoginLoadingDialog.dismiss();
                AccountToast.showToastMessage(InputPhoneNumberFragment.this.getActivity(), str);
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
        public void onNeedCaptchaCode(final String str, String str2) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                InputPhoneNumberFragment.this.showVerification(Constants.VERIFICATION_TICKET_LOGIN_ACTION, new VerificationManager.VerifyResultCallback() { // from class: com.xiaomi.passport.ui.page.InputPhoneNumberFragment._RequestPhoneVerifyCodeCallback.1
                    @Override // com.xiaomi.verificationsdk.VerificationManager.VerifyResultCallback
                    public void onVerifySucess(VerifyResult verifyResult) {
                        if (InputPhoneNumberFragment.this.isActivityAlive()) {
                            if (InputPhoneNumberFragment.this.mSendVerifyCodeTask != null) {
                                InputPhoneNumberFragment.this.mSendVerifyCodeTask.cancel(true);
                            }
                            InputPhoneNumberFragment.this.mSendVerifyCodeTask = LoginAndRegisterController.requestPhoneNumberVerifyCode(InputPhoneNumberFragment.this.getActivity(), InputPhoneNumberFragment.this.mSid, InputPhoneNumberFragment.this.mEtgvPhone.getInputText(), CountryCodePhoneNumber.getCountryCodeAsString(InputPhoneNumberFragment.this.mEtgvPhone.getCountryCode()), null, null, new VerificationCode(verifyResult.getToken(), Constants.VERIFICATION_TICKET_LOGIN_ACTION), InputPhoneNumberFragment.this.mRequestPhoneVerifyCodeCallback);
                        }
                    }

                    @Override // com.xiaomi.verificationsdk.VerificationManager.VerifyResultCallback
                    public void onVerifyCancel() {
                        if (InputPhoneNumberFragment.this.isActivityAlive()) {
                            InputPhoneNumberFragment.this.mLoginLoadingDialog.dismiss();
                        }
                    }

                    @Override // com.xiaomi.verificationsdk.VerificationManager.VerifyResultCallback
                    public void onVerifyFail(VerifyError verifyError) {
                        if (InputPhoneNumberFragment.this.isActivityAlive() && verifyError.getCode() == ErrorInfo.ErrorCode.ERROR_VERIFY_SERVER.getCode()) {
                            InputPhoneNumberFragment.this.showCaptcha(str, new BaseLoginFragment.OnCaptchaCallback() { // from class: com.xiaomi.passport.ui.page.InputPhoneNumberFragment._RequestPhoneVerifyCodeCallback.1.1
                                @Override // com.xiaomi.passport.ui.page.BaseLoginFragment.OnCaptchaCallback
                                public void onCaptcha(String str3, String str4) {
                                    if (InputPhoneNumberFragment.this.isActivityAlive()) {
                                        if (InputPhoneNumberFragment.this.mSendVerifyCodeTask != null) {
                                            InputPhoneNumberFragment.this.mSendVerifyCodeTask.cancel(true);
                                        }
                                        InputPhoneNumberFragment.this.mSendVerifyCodeTask = LoginAndRegisterController.requestPhoneNumberVerifyCode(InputPhoneNumberFragment.this.getActivity(), InputPhoneNumberFragment.this.mSid, InputPhoneNumberFragment.this.mEtgvPhone.getInputText(), CountryCodePhoneNumber.getCountryCodeAsString(InputPhoneNumberFragment.this.mEtgvPhone.getCountryCode()), null, new CaptchaCode(str3, str4), null, InputPhoneNumberFragment.this.mRequestPhoneVerifyCodeCallback);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                InputPhoneNumberFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, str, passThroughErrorInfo);
            }
        }
    }

    private class _RequestPhoneLoginConfigCallback extends RequestPhoneLoginConfigCallback {
        public _RequestPhoneLoginConfigCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneLoginConfigCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneLoginConfigExtensionCallback
        public void onSuccess(LoginPreference loginPreference) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                int i = AnonymousClass3.$SwitchMap$com$xiaomi$passport$data$LoginPreference$PhoneLoginType[loginPreference.phoneLoginType.ordinal()];
                if (i == 1) {
                    InputPhoneNumberFragment.this.mLoginLoadingDialog.show();
                    if (InputPhoneNumberFragment.this.mSendVerifyCodeTask != null) {
                        InputPhoneNumberFragment.this.mSendVerifyCodeTask.cancel(true);
                    }
                    InputPhoneNumberFragment inputPhoneNumberFragment = InputPhoneNumberFragment.this;
                    inputPhoneNumberFragment.mSendVerifyCodeTask = LoginAndRegisterController.requestPhoneNumberVerifyCode(inputPhoneNumberFragment.getActivity(), InputPhoneNumberFragment.this.mSid, InputPhoneNumberFragment.this.mEtgvPhone.getInputText(), CountryCodePhoneNumber.getCountryCodeAsString(InputPhoneNumberFragment.this.mEtgvPhone.getCountryCode()), null, null, null, InputPhoneNumberFragment.this.mRequestPhoneVerifyCodeCallback);
                    return;
                }
                if (i != 2) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(PasswordLoginFragment.KEY_STRING_LOGIN_PHONE_NUMBER, InputPhoneNumberFragment.this.mEtgvPhone.getInputText());
                bundle.putInt(PasswordLoginFragment.KEY_INT_LOGIN_COUNTRY_CODE, InputPhoneNumberFragment.this.mEtgvPhone.getCountryCode());
                InputPhoneNumberFragment.this.fillOriginalPageStartTsMs(bundle);
                InputPhoneNumberFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.PASSWORD_LOGIN, bundle, false, true);
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneLoginConfigCallback
        public void onFailed(String str) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                InputPhoneNumberFragment.this.mLoginLoadingDialog.dismiss();
                InputPhoneNumberFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneLoginConfigCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneLoginConfigExtensionCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, String str, PassThroughErrorInfo passThroughErrorInfo) {
            if (InputPhoneNumberFragment.this.isActivityAlive()) {
                InputPhoneNumberFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, str, passThroughErrorInfo);
            }
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.passport.ui.page.InputPhoneNumberFragment$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$passport$data$LoginPreference$PhoneLoginType;

        static {
            int[] iArr = new int[LoginPreference.PhoneLoginType.values().length];
            $SwitchMap$com$xiaomi$passport$data$LoginPreference$PhoneLoginType = iArr;
            try {
                iArr[LoginPreference.PhoneLoginType.ticket.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$data$LoginPreference$PhoneLoginType[LoginPreference.PhoneLoginType.password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    protected String getLoginTypeStatName() {
        return "手机号登录";
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment
    protected BaseFragment.StatParams getStatParams() {
        return new BaseFragment.StatParams("手机号登录页面", getString(R.string.passport_stat_tip_phone_ticket_login_page_browse));
    }
}
