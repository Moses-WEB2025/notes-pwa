package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorConstants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.callback.LoginPhoneUserInfoCallback;
import com.xiaomi.passport.callback.QueryPhoneUserInfoCallback;
import com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.CaptchaCode;
import com.xiaomi.passport.ui.internal.VerificationCode;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;
import com.xiaomi.passport.ui.utils.PassportPageIntent;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.passport.ui.view.VerifyCodeEditText;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;
import com.xiaomi.passport.utils.MultiLangTextFormatter;
import com.xiaomi.verificationsdk.VerificationManager;
import com.xiaomi.verificationsdk.internal.ErrorInfo;
import com.xiaomi.verificationsdk.internal.VerifyError;
import com.xiaomi.verificationsdk.internal.VerifyResult;
import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class VerifyCodeLoginFragment extends BaseLoginFragment implements View.OnClickListener {
    private static final int DEFAULT_COUNTRY_CODE = 86;
    public static final String KEY_INT_PHONE_COUNTRY_CODE = "extra_build_country_info";
    public static final String KEY_INT_VERIFY_CODE_LENGTH = "verify_code_length";
    public static final String KEY_PARCEL_PHONE_ACCOUNT = "phone_account";
    public static final String KEY_STRING_PHONE_NUMBER = "extra_phone";
    public static final String KEY_VERIFY_CODE_TYPE = "extra_ticket_type";
    private static final String TAG = "VerifyCodeLoginFragment";
    private static final String TICKET_TYPE_SMS = "sms";
    private static final String TICKET_TYPE_WHATSAPP = "whatsapp";
    private final String KEY_RESTORE_TIME = "restore_time";
    private final String KEY_SHOW_PHONE = "show_phone";
    private final String KEY_USER_INFO = "user_info";
    private long mCurrentTime = 0;
    private VerifyCodeEditText mEtVerifyCode;
    private LoginPhoneUserInfoCallback mLoginPhoneUserInfoCallback;
    private SimpleFutureTask<AccountInfo> mLoginPhoneUserInfoTask;
    private QueryPhoneUserInfoCallback mQueryPhoneUserInfoCallback;
    private SimpleFutureTask<AccountInfo> mRegisterPhoneUserInfoTask;
    private RequestPhoneVerifyCodeCallback mRequestPhoneVerifyCodeCallback;
    private SimpleFutureTask<Integer> mRequestPhoneVerifyCodeTask;
    private SimpleFutureTask<RegisterUserInfo> mRequestUserInfoTask;
    private CountDownTimer mResendVerifyCodeTimer;
    private int mSendPhoneCountryCode;
    private String mSendPhoneNumber;
    private String mShowPhone;
    private String mTicketType;
    private TextView mTvCannotReceiveCode;
    private TextView mTvResendVerifyCode;
    private TextView mTvSendPhone;
    private RegisterUserInfo mUserInfo;
    private PassportDialog registerDialog;

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mRequestPhoneVerifyCodeCallback = new _RequestPhoneVerifyCodeCallback(context);
        this.mQueryPhoneUserInfoCallback = new _QueryPhoneUserInfoCallback(context);
        this.mLoginPhoneUserInfoCallback = new _LoginPhoneUserInfoCallback(context);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_fragment_verify_code_login, viewGroup, false);
        initViews(viewInflate);
        return viewInflate;
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initVars(bundle);
        initSettings();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        releaseViews();
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong("restore_time", this.mCurrentTime);
        PassportDialog passportDialog = this.registerDialog;
        if (passportDialog == null || !passportDialog.isShowing()) {
            return;
        }
        bundle.putString("show_phone", this.mShowPhone);
        bundle.putParcelable("user_info", this.mUserInfo);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mTvCannotReceiveCode) {
            statClick(R.string.passport_stat_tip_input_login_ticket_page_click_un_receive_ticket);
            startActivity(PassportPageIntent.getFAQCanNotReceiveSMS(getContext()));
        } else if (view == this.mTvResendVerifyCode) {
            requestVerifyCode();
        }
    }

    private void initViews(View view) {
        TextView textView = (TextView) view.findViewById(R.id.resend_timer);
        this.mTvResendVerifyCode = textView;
        textView.setOnClickListener(this);
        this.mTvResendVerifyCode.setEnabled(false);
        this.mTvSendPhone = (TextView) view.findViewById(R.id.send_phone);
        TextView textView2 = (TextView) view.findViewById(R.id.can_not_receive_verify_code);
        this.mTvCannotReceiveCode = textView2;
        textView2.setOnClickListener(this);
        VerifyCodeEditText verifyCodeEditText = (VerifyCodeEditText) view.findViewById(R.id.verify_code);
        this.mEtVerifyCode = verifyCodeEditText;
        verifyCodeEditText.setInputCompleteListener(new VerifyCodeEditText.InputCompletedListener() { // from class: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment.1
            @Override // com.xiaomi.passport.ui.view.VerifyCodeEditText.InputCompletedListener
            public void onInputCompleted(String str) {
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.show(R.string.passport_dialog_loading);
                if (VerifyCodeLoginFragment.this.mRequestUserInfoTask != null) {
                    VerifyCodeLoginFragment.this.mRequestUserInfoTask.cancel(true);
                }
                VerifyCodeLoginFragment verifyCodeLoginFragment = VerifyCodeLoginFragment.this;
                verifyCodeLoginFragment.mRequestUserInfoTask = LoginAndRegisterController.requestPhoneUserInfo(verifyCodeLoginFragment.getActivity(), VerifyCodeLoginFragment.this.mSid, VerifyCodeLoginFragment.this.mSendPhoneNumber, CountryCodePhoneNumber.getCountryCodeAsString(VerifyCodeLoginFragment.this.mSendPhoneCountryCode), VerifyCodeLoginFragment.this.mTicketType, str, VerifyCodeLoginFragment.this.mQueryPhoneUserInfoCallback);
            }
        });
    }

    private void initVars(Bundle bundle) {
        Bundle fullArguments = getFullArguments();
        PhoneAccount phoneAccount = (PhoneAccount) fullArguments.getParcelable(KEY_PARCEL_PHONE_ACCOUNT);
        this.mTicketType = fullArguments.getString("extra_ticket_type", TICKET_TYPE_SMS);
        this.mSendPhoneNumber = fullArguments.getString("extra_phone");
        this.mSendPhoneCountryCode = fullArguments.getInt(KEY_INT_PHONE_COUNTRY_CODE, 86);
        boolean z = fullArguments.getBoolean(ServerPassThroughErrorConstants.PARAM_BOOLEAN_IS_FROM_PASS_THROUGH_ERROR_JUMP, false);
        String strForceLTR = MultiLangTextFormatter.forceLTR(CountryCodePhoneNumber.addPhoneWithCountyCode(this.mSendPhoneNumber));
        if (TICKET_TYPE_WHATSAPP.equals(this.mTicketType)) {
            this.mTvSendPhone.setText(Html.fromHtml(String.format(getString(R.string.passport_vcode_what_app_send_prompt), strForceLTR)));
        } else if (phoneAccount != null) {
            this.mTvSendPhone.setText(MultiLangTextFormatter.forceLTR(phoneAccount.registerUserInfo.phone));
            this.mSendPhoneNumber = phoneAccount.accountCertification.hashedPhoneNumber;
            this.mSendPhoneCountryCode = 0;
        } else {
            this.mTvSendPhone.setText(getString(R.string.passport_send_sms_to, strForceLTR));
        }
        int i = fullArguments.getInt(KEY_INT_VERIFY_CODE_LENGTH, 6);
        if (i <= 0) {
            throw new IllegalStateException("verify code length need > 0");
        }
        this.mEtVerifyCode.setVerifyCodeLength(i);
        this.mCurrentTime = 60000L;
        if (bundle != null) {
            this.mCurrentTime = bundle.getLong("restore_time");
            this.mShowPhone = bundle.getString("show_phone");
            RegisterUserInfo registerUserInfo = (RegisterUserInfo) bundle.getParcelable("user_info");
            this.mUserInfo = registerUserInfo;
            String str = this.mShowPhone;
            if (str != null && registerUserInfo != null) {
                showIsRegisterDialog(str);
            }
        }
        if (z) {
            requestVerifyCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.xiaomi.passport.ui.page.VerifyCodeLoginFragment$2] */
    public void countdown() {
        boolean z = this.mCurrentTime <= 0;
        updateSendVerifyCodeTv(z);
        if (z) {
            return;
        }
        this.mResendVerifyCodeTimer = new CountDownTimer(this.mCurrentTime, 1000L) { // from class: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment.2
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                VerifyCodeLoginFragment.this.mCurrentTime = j;
                VerifyCodeLoginFragment.this.mTvResendVerifyCode.setText(VerifyCodeLoginFragment.this.getString(R.string.passport_ticket_send_by_second, Long.valueOf(VerifyCodeLoginFragment.this.mCurrentTime / 1000)));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                VerifyCodeLoginFragment.this.updateSendVerifyCodeTv(true);
                VerifyCodeLoginFragment.this.mCurrentTime = -1L;
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSendVerifyCodeTv(boolean z) {
        this.mTvResendVerifyCode.setEnabled(z);
        if (z) {
            this.mTvResendVerifyCode.setText(R.string.passport_resend_ticket);
        }
    }

    private void initSettings() {
        countdown();
        this.mLoginUIAction.showSNSLoginFragment(false);
    }

    private void releaseViews() {
        CountDownTimer countDownTimer = this.mResendVerifyCodeTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mResendVerifyCodeTimer = null;
        }
        SimpleFutureTask<Integer> simpleFutureTask = this.mRequestPhoneVerifyCodeTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mRequestPhoneVerifyCodeTask = null;
        }
        SimpleFutureTask<RegisterUserInfo> simpleFutureTask2 = this.mRequestUserInfoTask;
        if (simpleFutureTask2 != null) {
            simpleFutureTask2.cancel(true);
            this.mRequestUserInfoTask = null;
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask3 = this.mLoginPhoneUserInfoTask;
        if (simpleFutureTask3 != null) {
            simpleFutureTask3.cancel(true);
            this.mLoginPhoneUserInfoTask = null;
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask4 = this.mRegisterPhoneUserInfoTask;
        if (simpleFutureTask4 != null) {
            simpleFutureTask4.cancel(true);
            this.mRegisterPhoneUserInfoTask = null;
        }
        PassportDialog passportDialog = this.registerDialog;
        if (passportDialog != null) {
            passportDialog.dismiss();
            this.registerDialog = null;
        }
    }

    private void requestVerifyCode() {
        this.mLoginLoadingDialog.show(R.string.passport_dialog_loading);
        SimpleFutureTask<Integer> simpleFutureTask = this.mRequestPhoneVerifyCodeTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
        }
        this.mRequestPhoneVerifyCodeTask = LoginAndRegisterController.requestPhoneNumberVerifyCode(getActivity(), this.mSid, this.mSendPhoneNumber, CountryCodePhoneNumber.getCountryCodeAsString(this.mSendPhoneCountryCode), this.mTicketType, null, null, this.mRequestPhoneVerifyCodeCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginPhoneUserInfo(RegisterUserInfo registerUserInfo) {
        this.mLoginLoadingDialog.show(R.string.passport_dialog_doing_login);
        SimpleFutureTask<AccountInfo> simpleFutureTask = this.mLoginPhoneUserInfoTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
        }
        this.mLoginPhoneUserInfoTask = LoginAndRegisterController.loginPhoneUserInfo(getActivity(), this.mSid, this.mSendPhoneNumber, CountryCodePhoneNumber.getCountryCodeAsString(this.mSendPhoneCountryCode), registerUserInfo, this.mLoginPhoneUserInfoCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerPhoneUserInfo() {
        AccountLogger.log(TAG, "start to set password");
        SysHelper.replaceToFragment(getActivity(), InputRegisterPasswordFragment.newInstance(createPhoneTokenRegisterParams(), getArguments(), false), false, true, ((ViewGroup) getView().getParent()).getId());
    }

    protected void showIsRegisterDialog(String str) {
        PassportDialog passportDialog = new PassportDialog(getContext());
        this.registerDialog = passportDialog;
        passportDialog.setTitle(getString(R.string.passport_register_dialog_title)).setMessage(String.format(getString(R.string.pasport_register_dialog_message), str)).setPositiveButton(getString(R.string.passport_register_dialog_positive), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VerifyCodeLoginFragment.this.registerPhoneUserInfo();
            }
        }).setNegativeButton(getString(android.R.string.cancel), null).show();
        this.registerDialog.setCancelable(false);
    }

    private PhoneTokenRegisterParams createPhoneTokenRegisterParams() {
        String strInvalidate = CountryCodePhoneNumber.invalidate(this.mSendPhoneNumber, CountryCodePhoneNumber.getCountryCodeAsString(this.mSendPhoneCountryCode));
        PhoneTokenRegisterParams.Builder builder = new PhoneTokenRegisterParams.Builder();
        builder.serviceId(this.mSid);
        builder.phoneTicketToken(strInvalidate, this.mUserInfo.ticketToken);
        return builder.build();
    }

    private class _RequestPhoneVerifyCodeCallback extends RequestPhoneVerifyCodeCallback {
        public _RequestPhoneVerifyCodeCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback
        public void onRequestSuccessful(int i) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                VerifyCodeLoginFragment.this.mTvResendVerifyCode.setEnabled(false);
                VerifyCodeLoginFragment.this.mCurrentTime = 60000L;
                VerifyCodeLoginFragment.this.countdown();
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback
        public void onRequestFailed(String str) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                VerifyCodeLoginFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.RequestPhoneVerifyCodeCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketExtensionCallback
        public void onNeedCaptchaCode(final String str, String str2) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                VerifyCodeLoginFragment.this.showVerification(Constants.VERIFICATION_TICKET_LOGIN_ACTION, new VerificationManager.VerifyResultCallback() { // from class: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment._RequestPhoneVerifyCodeCallback.1
                    @Override // com.xiaomi.verificationsdk.VerificationManager.VerifyResultCallback
                    public void onVerifyCancel() {
                    }

                    @Override // com.xiaomi.verificationsdk.VerificationManager.VerifyResultCallback
                    public void onVerifySucess(VerifyResult verifyResult) {
                        if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                            VerifyCodeLoginFragment.this.mLoginLoadingDialog.show(R.string.passport_dialog_loading);
                            if (VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeTask != null) {
                                VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeTask.cancel(true);
                            }
                            VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeTask = LoginAndRegisterController.requestPhoneNumberVerifyCode(VerifyCodeLoginFragment.this.getActivity(), VerifyCodeLoginFragment.this.mSid, VerifyCodeLoginFragment.this.mSendPhoneNumber, CountryCodePhoneNumber.getCountryCodeAsString(VerifyCodeLoginFragment.this.mSendPhoneCountryCode), VerifyCodeLoginFragment.this.mTicketType, null, new VerificationCode(verifyResult.getToken(), Constants.VERIFICATION_TICKET_LOGIN_ACTION), VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeCallback);
                        }
                    }

                    @Override // com.xiaomi.verificationsdk.VerificationManager.VerifyResultCallback
                    public void onVerifyFail(VerifyError verifyError) {
                        if (VerifyCodeLoginFragment.this.isActivityAlive() && verifyError.getCode() == ErrorInfo.ErrorCode.ERROR_VERIFY_SERVER.getCode()) {
                            VerifyCodeLoginFragment.this.showCaptcha(str, new BaseLoginFragment.OnCaptchaCallback() { // from class: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment._RequestPhoneVerifyCodeCallback.1.1
                                @Override // com.xiaomi.passport.ui.page.BaseLoginFragment.OnCaptchaCallback
                                public void onCaptcha(String str3, String str4) {
                                    if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                                        if (VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeTask != null) {
                                            VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeTask.cancel(true);
                                        }
                                        VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeTask = LoginAndRegisterController.requestPhoneNumberVerifyCode(VerifyCodeLoginFragment.this.getActivity(), VerifyCodeLoginFragment.this.mSid, VerifyCodeLoginFragment.this.mSendPhoneNumber, CountryCodePhoneNumber.getCountryCodeAsString(VerifyCodeLoginFragment.this.mSendPhoneCountryCode), VerifyCodeLoginFragment.this.mTicketType, new CaptchaCode(str3, str4), null, VerifyCodeLoginFragment.this.mRequestPhoneVerifyCodeCallback);
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
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, str, passThroughErrorInfo);
            }
        }
    }

    private class _QueryPhoneUserInfoCallback extends QueryPhoneUserInfoCallback {
        public _QueryPhoneUserInfoCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.QueryPhoneUserInfoCallback
        public void onQuerySuccessful(final RegisterUserInfo registerUserInfo) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                int i = AnonymousClass4.$SwitchMap$com$xiaomi$accountsdk$account$data$RegisterUserInfo$RegisterStatus[((RegisterUserInfo.RegisterStatus) Objects.requireNonNull(registerUserInfo.status)).ordinal()];
                if (i == 1) {
                    VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                    VerifyCodeLoginFragment verifyCodeLoginFragment = VerifyCodeLoginFragment.this;
                    verifyCodeLoginFragment.mShowPhone = MultiLangTextFormatter.forceLTR(CountryCodePhoneNumber.addPhoneWithCountyCode(verifyCodeLoginFragment.mSendPhoneNumber));
                    VerifyCodeLoginFragment.this.mUserInfo = registerUserInfo;
                    VerifyCodeLoginFragment verifyCodeLoginFragment2 = VerifyCodeLoginFragment.this;
                    verifyCodeLoginFragment2.showIsRegisterDialog(verifyCodeLoginFragment2.mShowPhone);
                    return;
                }
                if (i == 2) {
                    VerifyCodeLoginFragment.this.loginPhoneUserInfo(registerUserInfo);
                } else {
                    if (i != 3) {
                        return;
                    }
                    VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                    VerifyCodeLoginFragment verifyCodeLoginFragment3 = VerifyCodeLoginFragment.this;
                    verifyCodeLoginFragment3.showChooseToLogin(verifyCodeLoginFragment3.getActivity(), VerifyCodeLoginFragment.this.getLayoutInflater(), registerUserInfo, new BaseLoginFragment.OnChooseLogin() { // from class: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment._QueryPhoneUserInfoCallback.1
                        @Override // com.xiaomi.passport.ui.page.BaseLoginFragment.OnChooseLogin
                        public void onRegister() {
                            VerifyCodeLoginFragment.this.registerPhoneUserInfo();
                        }

                        @Override // com.xiaomi.passport.ui.page.BaseLoginFragment.OnChooseLogin
                        public void onTicketLogin() {
                            VerifyCodeLoginFragment.this.loginPhoneUserInfo(registerUserInfo);
                        }
                    });
                }
            }
        }

        @Override // com.xiaomi.passport.callback.QueryPhoneUserInfoCallback
        public void onQueryFailed(String str) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(str);
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                VerifyCodeLoginFragment.this.mEtVerifyCode.animateErrorAndReset();
                VerifyCodeLoginFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.QueryPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneUserInfoCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(passThroughErrorInfo.getTips(), LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, passThroughErrorInfo);
            }
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.passport.ui.page.VerifyCodeLoginFragment$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$data$RegisterUserInfo$RegisterStatus;

        static {
            int[] iArr = new int[RegisterUserInfo.RegisterStatus.values().length];
            $SwitchMap$com$xiaomi$accountsdk$account$data$RegisterUserInfo$RegisterStatus = iArr;
            try {
                iArr[RegisterUserInfo.RegisterStatus.STATUS_NOT_REGISTERED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$RegisterUserInfo$RegisterStatus[RegisterUserInfo.RegisterStatus.STATUS_REGISTERED_NOT_RECYCLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$RegisterUserInfo$RegisterStatus[RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private class _LoginPhoneUserInfoCallback extends LoginPhoneUserInfoCallback {
        protected _LoginPhoneUserInfoCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneUserInfoCallback
        public void onLoginFailed(String str) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(str);
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                VerifyCodeLoginFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onLoginSuccess(AccountInfo accountInfo) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                LoginAndRegisterController.storePassToken(VerifyCodeLoginFragment.this.getActivity(), accountInfo);
                LoginAndRegisterController.finishWithLoginResult(VerifyCodeLoginFragment.this.getActivity(), accountInfo, VerifyCodeLoginFragment.this.mLoginAgreementAndPrivacy);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onNeedNotification(String str, String str2) {
            super.onNeedNotification(str, str2);
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set("NeedNotification");
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
            if (VerifyCodeLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(passThroughErrorInfo.getTips(), LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
                VerifyCodeLoginFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, passThroughErrorInfo);
            }
        }
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    protected String getLoginTypeStatName() {
        return "手机号验证码";
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment
    protected BaseFragment.StatParams getStatParams() {
        return new BaseFragment.StatParams("验证码登录页面", getString(R.string.passport_stat_tip_input_login_ticket_page_browse));
    }
}
