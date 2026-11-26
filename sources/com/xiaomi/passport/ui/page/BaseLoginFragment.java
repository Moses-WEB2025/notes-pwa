package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.Constants;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.license.LoginAgreementAndPrivacy;
import com.xiaomi.passport.ui.view.EditTextGroupView;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.verificationsdk.VerificationManager;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseLoginFragment extends BaseFragment {
    private static final String EXTRA_KEY_LONG_START_TS_MS = "start_ts_ms";
    private PassportDialog mAgreementDialog;
    private BgTask<Bitmap> mAvatarTask;
    private PassportDialog mCaptchaDialog;
    private PassportDialog mChooseLoginDialog;
    protected String mExtBannerBiz;
    protected boolean mExtraUserAgreementSelected;
    protected LoginAgreementAndPrivacy mLoginAgreementAndPrivacy;
    protected PassportDialog mLoginLoadingDialog;
    protected LoginUIAction mLoginUIAction;
    protected boolean mNeedShowUserAgreement;
    private long mPageStartTsMs;
    protected String mSid;
    private VerificationManager mVerificationManager;

    public enum LoginFragmentType {
        VERIFY_CODE_LOGIN,
        PASSWORD_LOGIN,
        INPUT_PHONE_NUMBER,
        PHONE_ACCOUNT_LOGIN,
        PHONE_ACCOUNT_QUICK_LOGIN,
        SNS_LOGIN
    }

    public interface OnCaptchaCallback {
        void onCaptcha(String str, String str2);
    }

    public interface OnChooseLogin {
        void onRegister();

        void onTicketLogin();
    }

    public interface OnDynamicLoginDialogCallback {
        void onDialogCheck(String str, boolean z);
    }

    protected abstract String getLoginTypeStatName();

    public boolean isUserAgreementSelected() {
        return false;
    }

    public void setUserAgreementState(boolean z) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof LoginUIAction)) {
            throw new IllegalArgumentException("the context must be a impl of LoginUIController!");
        }
        this.mLoginUIAction = (LoginUIAction) context;
    }

    public Long getPageStartTsMs() {
        return Long.valueOf(this.mPageStartTsMs);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Intent intent = getActivity().getIntent();
        Bundle bundle2 = new Bundle();
        if (intent != null && intent.getExtras() != null) {
            bundle2.putAll(intent.getExtras());
        }
        if (getArguments() != null) {
            bundle2.putAll(getArguments());
        }
        long j = bundle2.getLong(EXTRA_KEY_LONG_START_TS_MS, -1L);
        this.mPageStartTsMs = j;
        if (j > 0) {
            return;
        }
        this.mPageStartTsMs = SystemClock.elapsedRealtime();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initVars();
        initSettings();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        releaseVars();
        super.onDestroyView();
    }

    public void fillOriginalPageStartTsMs(Bundle bundle) {
        bundle.putLong(EXTRA_KEY_LONG_START_TS_MS, this.mPageStartTsMs);
    }

    private void initVars() {
        Bundle fullArguments = getFullArguments();
        String string = fullArguments.getString("service_id");
        this.mSid = string;
        AccountStatInterface.setSidCommonProperty(string);
        this.mExtBannerBiz = fullArguments.getString(BaseConstants.EXTRA_BANNER_BIZ);
        PassportDialog passportDialog = new PassportDialog(getActivity());
        this.mLoginLoadingDialog = passportDialog;
        passportDialog.setLoadingProgressVisible(true);
        this.mLoginLoadingDialog.setMessage(getString(R.string.passport_dialog_loading));
        this.mLoginLoadingDialog.setCancelable(false);
        VerificationManager verificationManager = new VerificationManager(getActivity());
        this.mVerificationManager = verificationManager;
        verificationManager.setDomain("https://verify.sec.xiaomi.com");
        this.mVerificationManager.setKey(Constants.VERIFICATION_KEY);
        this.mVerificationManager.setErrorAction(true);
        this.mVerificationManager.init();
        LoginAgreementAndPrivacy loginAgreementAndPrivacy = (LoginAgreementAndPrivacy) fullArguments.getParcelable(AccountLoginActivity.EXTRA_PARCEL_LOGIN_AGREEMENT_AND_PRIVACY);
        this.mLoginAgreementAndPrivacy = loginAgreementAndPrivacy;
        if (loginAgreementAndPrivacy == null) {
            this.mLoginAgreementAndPrivacy = new LoginAgreementAndPrivacy.Builder(LoginAgreementAndPrivacy.Type.DEF).build();
        }
        this.mNeedShowUserAgreement = fullArguments.getBoolean(AccountLoginActivity.EXTRA_BOOL_SHOW_USER_AGREEMENT, true);
        this.mExtraUserAgreementSelected = fullArguments.getBoolean(AccountLoginActivity.EXTRA_BOOL_USER_AGREEMENT_INIT_SELECTED, false);
    }

    private void initSettings() {
        if (!this.mNeedShowUserAgreement) {
            setUserAgreementState(true);
        } else {
            setUserAgreementState(this.mExtraUserAgreementSelected);
        }
    }

    private void releaseVars() {
        dismissSafely(this.mLoginLoadingDialog);
        dismissSafely(this.mAgreementDialog);
        dismissSafely(this.mCaptchaDialog);
        dismissSafely(this.mChooseLoginDialog);
        BgTask<Bitmap> bgTask = this.mAvatarTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mAvatarTask = null;
        }
    }

    private void dismissSafely(PassportDialog passportDialog) {
        if (passportDialog == null || !passportDialog.isShowing()) {
            return;
        }
        passportDialog.dismiss();
    }

    public String getAgreements() {
        return "";
    }

    public void onCallAgreementConfirm(View.OnClickListener onClickListener) {
        showAgreementDialog(onClickListener);
    }

    protected void showAgreementDialog(final View.OnClickListener onClickListener) {
        dismissSafely(this.mAgreementDialog);
        PassportDialog passportDialog = new PassportDialog(getContext());
        this.mAgreementDialog = passportDialog;
        passportDialog.setTitle(getString(R.string.passport_attention)).setPositiveButton(getString(R.string.passport_agree), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.BaseLoginFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaseLoginFragment.this.setUserAgreementState(true);
                onClickListener.onClick(view);
            }
        }).setNegativeButton(getString(android.R.string.cancel), null).setMessage(getAgreements()).show();
    }

    protected void showVerification(String str, VerificationManager.VerifyResultCallback verifyResultCallback) {
        this.mVerificationManager.setAction(str).setVerifyResultCallback(verifyResultCallback).startVerify();
    }

    protected void showCaptcha(String str, final OnCaptchaCallback onCaptchaCallback) {
        View viewInflate = View.inflate(getContext(), R.layout.passport_layout_dialog_captcha_input, null);
        final EditTextGroupView editTextGroupView = (EditTextGroupView) viewInflate.findViewById(R.id.captcha);
        editTextGroupView.setupCaptcha(XMPassport.ACCOUNT_DOMAIN + str);
        dismissSafely(this.mCaptchaDialog);
        PassportDialog passportDialog = new PassportDialog(getContext());
        this.mCaptchaDialog = passportDialog;
        passportDialog.setTitle(getString(R.string.passport_dialog_captcha_title)).setView(viewInflate).setPositiveButton(getString(android.R.string.ok), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.BaseLoginFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onCaptchaCallback.onCaptcha(editTextGroupView.getInputText(), editTextGroupView.getCaptchaIck());
            }
        });
        this.mCaptchaDialog.show();
    }

    protected void showChooseToLogin(Context context, LayoutInflater layoutInflater, final RegisterUserInfo registerUserInfo, final OnChooseLogin onChooseLogin) {
        final View viewInflate = layoutInflater.inflate(R.layout.passport_layout_choose_to_signin_signup_dialog, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.text_view_user_info)).setText(String.format("%s\n%s", getString(R.string.passport_dialog_nick_name_prefix, TextUtils.isEmpty(registerUserInfo.userName) ? registerUserInfo.maskedUserId : registerUserInfo.userName), getString(R.string.passport_dialog_phone_number_prefix, registerUserInfo.phone)));
        if (!TextUtils.isEmpty(registerUserInfo.avatarAddress)) {
            BgTask<Bitmap> bgTask = this.mAvatarTask;
            if (bgTask != null) {
                bgTask.cancelAndRelease();
                this.mAvatarTask = null;
            }
            BgTask<Bitmap> bgTask2 = new BgTask<>(new BgTask.BgTaskRunnable<Bitmap>() { // from class: com.xiaomi.passport.ui.page.BaseLoginFragment.3
                /* JADX WARN: Can't rename method to resolve collision */
                /* JADX WARN: Code duplicated, block: B:20:0x002a  */
                @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
                public Bitmap run() throws Throwable {
                    Throwable th;
                    SimpleRequest.StreamContent asStream;
                    InputStream stream = null;
                    try {
                        asStream = SimpleRequestForAccount.getAsStream(registerUserInfo.avatarAddress, null, null);
                        try {
                            try {
                                stream = asStream.getStream();
                                if (asStream != null) {
                                    asStream.closeStream();
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (asStream != null) {
                                    asStream.closeStream();
                                }
                                throw th;
                            }
                        } catch (AccessDeniedException | AuthenticationFailureException | IOException e) {
                            e = e;
                            e.printStackTrace();
                            if (asStream != null) {
                            }
                            return BitmapFactory.decodeStream(stream);
                        }
                    } catch (AccessDeniedException | AuthenticationFailureException | IOException e2) {
                        e = e2;
                        asStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        asStream = null;
                        if (asStream != null) {
                            asStream.closeStream();
                        }
                        throw th;
                    }
                    return BitmapFactory.decodeStream(stream);
                }
            }, new BgTask.SuccessResultRunnable<Bitmap>() { // from class: com.xiaomi.passport.ui.page.BaseLoginFragment.4
                @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
                public void run(Bitmap bitmap) {
                    if (BaseLoginFragment.this.isActivityAlive()) {
                        ((ImageView) viewInflate.findViewById(R.id.image_user_avatar)).setImageBitmap(bitmap);
                    }
                }
            }, null);
            this.mAvatarTask = bgTask2;
            bgTask2.execute();
        }
        dismissSafely(this.mChooseLoginDialog);
        PassportDialog passportDialog = new PassportDialog(context);
        this.mChooseLoginDialog = passportDialog;
        passportDialog.setTitle(R.string.passport_dialog_is_your_mi_account);
        this.mChooseLoginDialog.setView(viewInflate);
        this.mChooseLoginDialog.setNegativeButton(getString(R.string.passport_dialog_register_other), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.BaseLoginFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onChooseLogin.onRegister();
            }
        });
        this.mChooseLoginDialog.setPositiveButton(getString(R.string.passport_dialog_is_my_account), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.BaseLoginFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onChooseLogin.onTicketLogin();
            }
        });
        this.mChooseLoginDialog.show();
    }

    /* JADX INFO: renamed from: com.xiaomi.passport.ui.page.BaseLoginFragment$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType;

        static {
            int[] iArr = new int[LoginFragmentType.values().length];
            $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType = iArr;
            try {
                iArr[LoginFragmentType.VERIFY_CODE_LOGIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType[LoginFragmentType.PASSWORD_LOGIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType[LoginFragmentType.INPUT_PHONE_NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType[LoginFragmentType.PHONE_ACCOUNT_LOGIN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType[LoginFragmentType.PHONE_ACCOUNT_QUICK_LOGIN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType[LoginFragmentType.SNS_LOGIN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static BaseLoginFragment newInstance(Bundle bundle, LoginFragmentType loginFragmentType) {
        BaseLoginFragment verifyCodeLoginFragment;
        switch (AnonymousClass7.$SwitchMap$com$xiaomi$passport$ui$page$BaseLoginFragment$LoginFragmentType[loginFragmentType.ordinal()]) {
            case 1:
                verifyCodeLoginFragment = new VerifyCodeLoginFragment();
                break;
            case 2:
                verifyCodeLoginFragment = new PasswordLoginFragment();
                break;
            case 3:
                verifyCodeLoginFragment = new InputPhoneNumberFragment();
                break;
            case 4:
                verifyCodeLoginFragment = new PhoneAccountLoginFragment();
                break;
            case 5:
                verifyCodeLoginFragment = new PhoneAccountQuickLoginFragment();
                break;
            case 6:
                verifyCodeLoginFragment = new SNSLoginFragment();
                break;
            default:
                throw new IllegalArgumentException("unsupported fragment type");
        }
        if (bundle != null) {
            verifyCodeLoginFragment.setArguments(bundle);
        }
        return verifyCodeLoginFragment;
    }
}
