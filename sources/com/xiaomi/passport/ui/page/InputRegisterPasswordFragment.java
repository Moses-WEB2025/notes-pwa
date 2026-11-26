package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.callback.RegisterPhoneUserInfoCallback;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;
import com.xiaomi.passport.ui.view.EditTextGroupView;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;

/* JADX INFO: loaded from: classes4.dex */
public class InputRegisterPasswordFragment extends BaseLoginFragment implements View.OnClickListener {
    private static final String KEY_IS_ONE_STEP_LOGIN = "is_one_step_login";
    private static final String KEY_PHONE_REGISTER_PARAMS = "activator_phone_register_params";
    private static final String TAG = "InputRegisterPasswordFragment";
    private Button mConfirmBtn;
    private EditTextGroupView mConfirmPasswordView;
    private boolean mIsOneStepLogin;
    private TextView mPasswordInvalidPromptMsg;
    private EditTextGroupView mPasswordView;
    private PhoneTokenRegisterParams mPhoneTokenRegisterParams;
    private SimpleFutureTask<AccountInfo> mRegisterFutureTask;
    private RegisterPhoneUserInfoCallback mRegisterPhoneUserInfoCallback;

    public static InputRegisterPasswordFragment newInstance(PhoneTokenRegisterParams phoneTokenRegisterParams, Bundle bundle, boolean z) {
        InputRegisterPasswordFragment inputRegisterPasswordFragment = new InputRegisterPasswordFragment();
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            bundle2.putAll(bundle);
        }
        bundle2.putParcelable(KEY_PHONE_REGISTER_PARAMS, phoneTokenRegisterParams);
        bundle2.putBoolean(KEY_IS_ONE_STEP_LOGIN, z);
        inputRegisterPasswordFragment.setArguments(bundle2);
        return inputRegisterPasswordFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        ActionBar supportActionBar;
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mPhoneTokenRegisterParams = (PhoneTokenRegisterParams) arguments.getParcelable(KEY_PHONE_REGISTER_PARAMS);
            this.mIsOneStepLogin = arguments.getBoolean(KEY_IS_ONE_STEP_LOGIN, false);
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null && (supportActionBar = appCompatActivity.getSupportActionBar()) != null) {
            supportActionBar.setTitle(R.string.passport_set_password_title);
            supportActionBar.show();
        }
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) { // from class: com.xiaomi.passport.ui.page.InputRegisterPasswordFragment.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                InputRegisterPasswordFragment inputRegisterPasswordFragment = InputRegisterPasswordFragment.this;
                inputRegisterPasswordFragment.setPwdStat(StatConstants.Event.CLICK, inputRegisterPasswordFragment.getString(R.string.passport_stat_tip_register_set_pwd_click_back), null);
                InputRegisterPasswordFragment.this.statClick(R.string.passport_stat_tip_register_set_pwd_click_back);
                if (getIsEnabled()) {
                    setEnabled(false);
                    InputRegisterPasswordFragment.this.requireActivity().onBackPressed();
                }
            }
        });
        setPwdStat("view", getString(R.string.passport_stat_tip_register_set_pwd), null);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_register_set_password, viewGroup, false);
        EditTextGroupView editTextGroupView = (EditTextGroupView) viewInflate.findViewById(R.id.password_layout);
        this.mPasswordView = editTextGroupView;
        addPasswordTextListener(editTextGroupView);
        EditTextGroupView editTextGroupView2 = (EditTextGroupView) viewInflate.findViewById(R.id.confirm_password_layout);
        this.mConfirmPasswordView = editTextGroupView2;
        addPasswordTextListener(editTextGroupView2);
        this.mPasswordInvalidPromptMsg = (TextView) viewInflate.findViewById(R.id.password_invalid_prompt);
        Button button = (Button) viewInflate.findViewById(R.id.btn_password_confirm);
        this.mConfirmBtn = button;
        button.setOnClickListener(this);
        ((TextView) viewInflate.findViewById(R.id.password_rules)).setText(String.format(getResources().getString(R.string.passport_password_req_notice), 8, 16));
        return viewInflate;
    }

    private void addPasswordTextListener(EditTextGroupView editTextGroupView) {
        editTextGroupView.getInputTextView().addTextChangedListener(new TextWatcher() { // from class: com.xiaomi.passport.ui.page.InputRegisterPasswordFragment.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (InputRegisterPasswordFragment.this.mPasswordInvalidPromptMsg != null) {
                    InputRegisterPasswordFragment.this.mPasswordInvalidPromptMsg.setText("");
                }
            }
        });
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        SimpleFutureTask<AccountInfo> simpleFutureTask = this.mRegisterFutureTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mRegisterFutureTask = null;
        }
        if (this.mRegisterPhoneUserInfoCallback != null) {
            this.mRegisterPhoneUserInfoCallback = null;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mConfirmBtn && checkPassword()) {
            statClick(R.string.passport_stat_tip_register_set_pwd_click_finish);
            onRegisterByPhone(this.mPasswordView.getInputText());
        }
    }

    private void onRegisterByPhone(String str) {
        this.mLoginLoadingDialog.show(R.string.passport_dialog_doing_register);
        PhoneTokenRegisterParams phoneTokenRegisterParamsBuild = PhoneTokenRegisterParams.copyFrom(this.mPhoneTokenRegisterParams).password(str).build();
        PhoneLoginController phoneLoginController = new PhoneLoginController();
        _RegisterPhoneUserInfoCallback _registerphoneuserinfocallback = new _RegisterPhoneUserInfoCallback(getActivity(), this.mSid);
        this.mRegisterPhoneUserInfoCallback = _registerphoneuserinfocallback;
        this.mRegisterFutureTask = phoneLoginController.register(phoneTokenRegisterParamsBuild, _registerphoneuserinfocallback);
    }

    private boolean checkPassword() {
        String strCheckPassword = SysHelper.checkPassword(getContext(), this.mPasswordView.getInputText(), this.mConfirmPasswordView.getInputText());
        if (TextUtils.isEmpty(strCheckPassword)) {
            return true;
        }
        AccountLogger.log(TAG, "InputRegisterPasswordFragment>>set password invalid");
        this.mPasswordInvalidPromptMsg.setText(strCheckPassword);
        return false;
    }

    private class _RegisterPhoneUserInfoCallback extends RegisterPhoneUserInfoCallback {
        public _RegisterPhoneUserInfoCallback(Context context, String str) {
            super(context, str);
        }

        @Override // com.xiaomi.passport.callback.RegisterPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
        public void onRegisterSuccess(AccountInfo accountInfo) {
            if (InputRegisterPasswordFragment.this.isActivityAlive()) {
                InputRegisterPasswordFragment inputRegisterPasswordFragment = InputRegisterPasswordFragment.this;
                inputRegisterPasswordFragment.setPwdStat(StatConstants.Event.CLICK, inputRegisterPasswordFragment.getString(R.string.passport_stat_tip_register_set_pwd_click_finish), 1);
                InputRegisterPasswordFragment.this.mLoginLoadingDialog.dismiss();
                LoginAndRegisterController.storePassToken(InputRegisterPasswordFragment.this.getActivity(), accountInfo);
                LoginAndRegisterController.finishWithLoginResult(InputRegisterPasswordFragment.this.getActivity(), accountInfo, InputRegisterPasswordFragment.this.mLoginAgreementAndPrivacy);
            }
        }

        @Override // com.xiaomi.passport.callback.RegisterPhoneUserInfoCallback
        public void onRegisterFailed(String str) {
            if (InputRegisterPasswordFragment.this.isActivityAlive()) {
                InputRegisterPasswordFragment inputRegisterPasswordFragment = InputRegisterPasswordFragment.this;
                inputRegisterPasswordFragment.setPwdStat(StatConstants.Event.CLICK, inputRegisterPasswordFragment.getString(R.string.passport_stat_tip_register_set_pwd_click_finish), 0);
                LoginFailedReasonHolder.set(str);
                InputRegisterPasswordFragment.this.mLoginLoadingDialog.dismiss();
                InputRegisterPasswordFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.RegisterPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
        public void onTokenExpired() {
            if (InputRegisterPasswordFragment.this.mIsOneStepLogin) {
                onRegisterFailed(this.context.getString(R.string.passport_request_error_invalid_token));
                InputRegisterPasswordFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, true, false);
            } else {
                InputRegisterPasswordFragment inputRegisterPasswordFragment = InputRegisterPasswordFragment.this;
                inputRegisterPasswordFragment.setPwdStat(StatConstants.Event.CLICK, inputRegisterPasswordFragment.getString(R.string.passport_stat_tip_register_set_pwd_click_finish), 0);
                super.onTokenExpired();
            }
        }

        @Override // com.xiaomi.passport.callback.RegisterPhoneUserInfoCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.PhoneRegisterCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
            if (InputRegisterPasswordFragment.this.isActivityAlive()) {
                InputRegisterPasswordFragment inputRegisterPasswordFragment = InputRegisterPasswordFragment.this;
                inputRegisterPasswordFragment.setPwdStat(StatConstants.Event.CLICK, inputRegisterPasswordFragment.getString(R.string.passport_stat_tip_register_set_pwd_click_finish), 0);
                LoginFailedReasonHolder.set(passThroughErrorInfo.getTips(), LoginAndRegisterController.convertErrorCodeToMessage(this.context, errorCode));
                InputRegisterPasswordFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, passThroughErrorInfo);
            }
        }
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    protected String getLoginTypeStatName() {
        return "注册设置密码";
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment
    protected BaseFragment.StatParams getStatParams() {
        return new BaseFragment.StatParams("注册设置密码页面", null);
    }
}
