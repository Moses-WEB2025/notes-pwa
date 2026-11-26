package com.xiaomi.passport.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.PhoneTokenRegisterParams;
import com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.callback.LoginPhoneAccountCallback;
import com.xiaomi.passport.task.BgTask;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.presenter.PhoneAutoLoginFragmentPresenter;
import com.xiaomi.passport.ui.utils.LoginAndRegisterController;
import com.xiaomi.passport.ui.utils.LoginFailedReasonHolder;
import com.xiaomi.passport.ui.view.AgreementView;
import com.xiaomi.passport.ui.view.PassportDialog;
import com.xiaomi.passport.ui.view.PhoneCard;
import com.xiaomi.passport.ui.view.QueryPhoneAccountViewHolder;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.CountryCodePhoneNumber;
import com.xiaomi.passport.utils.MultiLangTextFormatter;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberSourceFlag;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PhoneAccountLoginFragment extends BaseLoginFragment {
    private static final String TAG = "PhoneAccountLoginFragment";
    private int mAccountPhoneNumberSourceFlag;
    private AgreementView mAgreementView;
    private Button mLoginOtherButton;
    private SimpleFutureTask<AccountInfo> mLoginPhoneAccountTask;
    private PhoneAccount mPhoneAccount;
    private PhoneCard mPhoneAccountCard1;
    private PhoneCard mPhoneAccountCard2;
    private List<PhoneAccount> mPhoneAccountList;
    private BgTask<List<PhoneAccount>> mQueryPhoneAccountTask;
    private QueryPhoneAccountViewHolder mQueryPhoneAccountViewHolder;
    private SimpleFutureTask<AccountInfo> mRegisterPhoneAccountTask;
    private View mRootView;
    private String mShowPhone;
    private PassportDialog registerDialog;
    private final String KEY_SHOW_PHONE = "show_phone";
    private final String KEY_PHONE_ACCOUNT = VerifyCodeLoginFragment.KEY_PARCEL_PHONE_ACCOUNT;
    private String mStatLoginType = "一键登录";

    protected boolean canShowSNS() {
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.passport_fragment_phone_account_login, viewGroup, false);
        initViews(viewInflate);
        return viewInflate;
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        initVars();
        initSettings();
        updateViews();
        if (bundle != null) {
            this.mShowPhone = bundle.getString("show_phone");
            PhoneAccount phoneAccount = (PhoneAccount) bundle.getParcelable(VerifyCodeLoginFragment.KEY_PARCEL_PHONE_ACCOUNT);
            this.mPhoneAccount = phoneAccount;
            String str = this.mShowPhone;
            if (str == null || phoneAccount == null) {
                return;
            }
            showIsRegisterDialog(str, phoneAccount);
        }
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        releaseViews();
        super.onDestroyView();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public boolean isUserAgreementSelected() {
        AgreementView agreementView = this.mAgreementView;
        return agreementView == null || agreementView.isUserAgreedProtocol();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public String getAgreements() {
        return this.mAgreementView.getAppAgreement();
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public void onCallAgreementConfirm(final View.OnClickListener onClickListener) {
        showAgreementDialog(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        });
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    public void setUserAgreementState(boolean z) {
        AgreementView agreementView = this.mAgreementView;
        if (agreementView != null) {
            agreementView.setUserAgreementSelected(z);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PassportDialog passportDialog = this.registerDialog;
        if (passportDialog == null || !passportDialog.isShowing()) {
            return;
        }
        bundle.putString("show_phone", this.mShowPhone);
        bundle.putParcelable(VerifyCodeLoginFragment.KEY_PARCEL_PHONE_ACCOUNT, this.mPhoneAccount);
    }

    protected void initViews(View view) {
        this.mRootView = view;
        QueryPhoneAccountViewHolder queryPhoneAccountViewHolder = new QueryPhoneAccountViewHolder(view.findViewById(R.id.query_phone_account));
        this.mQueryPhoneAccountViewHolder = queryPhoneAccountViewHolder;
        queryPhoneAccountViewHolder.setOnActionClickListener(new QueryPhoneAccountViewHolder.OnActionClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.2
            @Override // com.xiaomi.passport.ui.view.QueryPhoneAccountViewHolder.OnActionClickListener
            public void onSkipClicked(View view2) {
                PhoneAccountLoginFragment.this.cancelQueryAccount();
                PhoneAccountLoginFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, true, false);
            }
        });
        Button button = (Button) view.findViewById(R.id.login_other);
        this.mLoginOtherButton = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PhoneAccountLoginFragment.this.getAccountNum() == 1) {
                    PhoneAccountLoginFragment.this.statClick(R.string.passport_stat_tip_single_sim_login_page_click_login_other);
                } else {
                    PhoneAccountLoginFragment.this.statClick(R.string.passport_stat_tip_double_sim_login_page_click_login_other);
                }
                PhoneAccountLoginFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, false, false);
            }
        });
    }

    private void initVars() {
        Bundle fullArguments = getFullArguments();
        this.mAccountPhoneNumberSourceFlag = fullArguments.getInt(AccountLoginActivity.EXTRA_INT_ACCOUNT_PHONE_NUMBER_SOURCE_FLAG, 0);
        List<PhoneAccount> parcelableArrayList = this.mPhoneAccountList;
        if (parcelableArrayList == null) {
            parcelableArrayList = fullArguments.getParcelableArrayList(AccountLoginActivity.EXTRA_PARCEL_LIST_PHONE_ACCOUNTS);
        }
        this.mPhoneAccountList = parcelableArrayList;
    }

    private void initSettings() {
        List<PhoneAccount> list = this.mPhoneAccountList;
        if (list == null || list.isEmpty()) {
            queryPhoneAccount();
        }
        this.mLoginUIAction.showSNSLoginFragment(false);
    }

    private void releaseViews() {
        cancelQueryAccount();
        this.mQueryPhoneAccountViewHolder.release();
        SimpleFutureTask<AccountInfo> simpleFutureTask = this.mLoginPhoneAccountTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mLoginPhoneAccountTask = null;
        }
        SimpleFutureTask<AccountInfo> simpleFutureTask2 = this.mRegisterPhoneAccountTask;
        if (simpleFutureTask2 != null) {
            simpleFutureTask2.cancel(true);
            this.mRegisterPhoneAccountTask = null;
        }
        PassportDialog passportDialog = this.registerDialog;
        if (passportDialog != null) {
            passportDialog.dismiss();
            this.registerDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViews() {
        if (this.mPhoneAccountList == null) {
            this.mQueryPhoneAccountViewHolder.setVisible(true);
            return;
        }
        this.mLoginUIAction.showSNSLoginFragment(canShowSNS());
        if (this.mPhoneAccountList.size() == 0) {
            this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, true, false);
            return;
        }
        this.mQueryPhoneAccountViewHolder.setVisible(false);
        View viewFindViewById = this.mRootView.findViewById(R.id.single_phone_account);
        View viewFindViewById2 = this.mRootView.findViewById(R.id.double_phone_account);
        if (this.mPhoneAccountList.size() == 1) {
            viewFindViewById.setVisibility(0);
            viewFindViewById2.setVisibility(8);
            this.mAgreementView = (AgreementView) viewFindViewById.findViewById(R.id.agreement_view_for_single);
            PhoneCard phoneCard = (PhoneCard) viewFindViewById.findViewById(R.id.phone_account_card);
            this.mPhoneAccountCard1 = phoneCard;
            phoneCard.update(this.mPhoneAccountList.get(0));
            viewFindViewById.findViewById(R.id.login_or_register).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PhoneAccountLoginFragment phoneAccountLoginFragment = PhoneAccountLoginFragment.this;
                    phoneAccountLoginFragment.onCardClicked(view, (PhoneAccount) phoneAccountLoginFragment.mPhoneAccountList.get(0));
                }
            });
        } else {
            viewFindViewById.setVisibility(8);
            viewFindViewById2.setVisibility(0);
            this.mAgreementView = (AgreementView) viewFindViewById2.findViewById(R.id.agreement_view_for_double);
            this.mPhoneAccountCard1 = (PhoneCard) viewFindViewById2.findViewById(R.id.phone_account_card_1);
            this.mPhoneAccountCard2 = (PhoneCard) viewFindViewById2.findViewById(R.id.phone_account_card_2);
            this.mPhoneAccountCard1.update(this.mPhoneAccountList.get(0));
            this.mPhoneAccountCard1.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PhoneAccountLoginFragment phoneAccountLoginFragment = PhoneAccountLoginFragment.this;
                    phoneAccountLoginFragment.onCardClicked(view, (PhoneAccount) phoneAccountLoginFragment.mPhoneAccountList.get(0));
                }
            });
            this.mPhoneAccountCard2.update(this.mPhoneAccountList.get(1));
            this.mPhoneAccountCard2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PhoneAccountLoginFragment phoneAccountLoginFragment = PhoneAccountLoginFragment.this;
                    phoneAccountLoginFragment.onCardClicked(view, (PhoneAccount) phoneAccountLoginFragment.mPhoneAccountList.get(1));
                }
            });
        }
        this.mAgreementView.setLoginAgreementAndPrivacy(this.mLoginAgreementAndPrivacy);
        this.mAgreementView.updateUserAgreement((PhoneAccount[]) this.mPhoneAccountList.toArray(new PhoneAccount[0]));
        this.mAgreementView.setVisibility(this.mNeedShowUserAgreement ? 0 : 8);
    }

    private void queryPhoneAccount() {
        cancelQueryAccount();
        BgTask<List<PhoneAccount>> bgTask = new BgTask<>(new QueryPhoneAccountBgRunnable(getContext().getApplicationContext(), this.mSid, this.mAccountPhoneNumberSourceFlag), new BgTask.SuccessResultRunnable<List<PhoneAccount>>() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.7
            @Override // com.xiaomi.passport.task.BgTask.SuccessResultRunnable
            public void run(List<PhoneAccount> list) {
                if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                    if (list == null) {
                        PhoneAccountLoginFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, true, false);
                    }
                    PhoneAccountLoginFragment.this.mPhoneAccountList = list;
                    PhoneAccountLoginFragment.this.updateViews();
                }
            }
        }, null);
        this.mQueryPhoneAccountTask = bgTask;
        bgTask.execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelQueryAccount() {
        BgTask<List<PhoneAccount>> bgTask = this.mQueryPhoneAccountTask;
        if (bgTask != null) {
            bgTask.cancelAndRelease();
            this.mQueryPhoneAccountTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCardClicked(View view, final PhoneAccount phoneAccount) {
        if (getAccountNum() == 1) {
            statClick(R.string.passport_stat_tip_single_sim_login_page_click_login_now);
        } else {
            statClick(R.string.passport_stat_tip_double_sim_login_page_click_sim_login);
        }
        if (!this.mAgreementView.isUserAgreedProtocol()) {
            showAgreementDialog(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    PhoneAccountLoginFragment.this.mAgreementView.setUserAgreementSelected(true);
                    PhoneAccountLoginFragment.this.onCardClicked(view2, phoneAccount);
                }
            });
            return;
        }
        if (phoneAccount.canLogin()) {
            this.mStatLoginType = "一键登录-已注册手机号";
            this.mLoginLoadingDialog.show(R.string.passport_dialog_doing_login);
            SimpleFutureTask<AccountInfo> simpleFutureTask = this.mLoginPhoneAccountTask;
            if (simpleFutureTask != null) {
                simpleFutureTask.cancel(true);
            }
            this.mLoginPhoneAccountTask = LoginAndRegisterController.loginPhoneAccount(getContext(), this.mSid, phoneAccount, new _LoginPhoneAccountCallback(getContext()));
            return;
        }
        this.mStatLoginType = "一键登录-未注册手机号";
        String strForceLTR = MultiLangTextFormatter.forceLTR(CountryCodePhoneNumber.addPhoneWithCountyCode(phoneAccount.registerUserInfo.phone));
        this.mShowPhone = strForceLTR;
        this.mPhoneAccount = phoneAccount;
        showIsRegisterDialog(strForceLTR, phoneAccount);
    }

    protected void showIsRegisterDialog(final String str, final PhoneAccount phoneAccount) {
        PassportDialog passportDialog = new PassportDialog(getContext());
        this.registerDialog = passportDialog;
        passportDialog.setTitle(getString(R.string.passport_register_dialog_title)).setMessage(String.format(getString(R.string.pasport_register_dialog_message), str)).setPositiveButton(getString(R.string.passport_register_dialog_positive), new View.OnClickListener() { // from class: com.xiaomi.passport.ui.page.PhoneAccountLoginFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PhoneAccountLoginFragment.this.registerPhoneUserInfo(str, phoneAccount);
            }
        }).setNegativeButton(getString(android.R.string.cancel), null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerPhoneUserInfo(String str, PhoneAccount phoneAccount) {
        AccountLogger.log(TAG, "start to set password");
        SysHelper.replaceToFragment(getActivity(), InputRegisterPasswordFragment.newInstance(new PhoneTokenRegisterParams.Builder().phoneTicketToken(str, phoneAccount.registerUserInfo.ticketToken).phoneHashActivatorToken(new ActivatorPhoneInfo.Builder().phoneHash(phoneAccount.accountCertification.hashedPhoneNumber).activatorToken(phoneAccount.accountCertification.activatorToken).build()).serviceId(this.mSid).build(), getArguments(), true), false, true, ((ViewGroup) getView().getParent()).getId());
    }

    private static class QueryPhoneAccountBgRunnable implements BgTask.BgTaskRunnable<List<PhoneAccount>> {
        public final Context appContext;
        public final int queryFlag;
        public final String sid;

        private QueryPhoneAccountBgRunnable(Context context, String str, int i) {
            this.appContext = context;
            this.sid = str;
            this.queryFlag = i;
        }

        @Override // com.xiaomi.passport.task.BgTask.BgTaskRunnable
        public List<PhoneAccount> run() {
            return PhoneAutoLoginFragmentPresenter.getPhoneAccount(this.appContext, this.sid, new AccountPhoneNumberSourceFlag(this.queryFlag));
        }
    }

    private class _LoginPhoneAccountCallback extends LoginPhoneAccountCallback {
        protected _LoginPhoneAccountCallback(Context context) {
            super(context);
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneAccountCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onLoginSuccess(AccountInfo accountInfo) {
            if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                PhoneAccountLoginFragment.this.mLoginLoadingDialog.dismiss();
                LoginAndRegisterController.storePassToken(PhoneAccountLoginFragment.this.getContext(), accountInfo);
                LoginAndRegisterController.finishWithLoginResult(PhoneAccountLoginFragment.this.getActivity(), accountInfo, PhoneAccountLoginFragment.this.mLoginAgreementAndPrivacy);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneAccountCallback
        public void onLoginFailed(String str) {
            if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set(str);
                PhoneAccountLoginFragment.this.mLoginLoadingDialog.dismiss();
                PhoneAccountLoginFragment.this.showToast(str);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneAccountCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onNeedNotification(String str, String str2) {
            if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                LoginFailedReasonHolder.set("NeedNotification");
                PhoneAccountLoginFragment.this.mLoginLoadingDialog.dismiss();
                this.context.startActivity(XiaomiAccountManager.get(this.context).getAccountNotificationActivityIntent("passportapi", str2, null, null));
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneAccountCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onTicketOrTokenInvalid() {
            super.onTicketOrTokenInvalid();
            if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                PhoneAccountLoginFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, true, false);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneAccountCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onTzSignInvalid() {
            super.onTzSignInvalid();
            if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                PhoneAccountLoginFragment.this.mLoginUIAction.gotoFragment(BaseLoginFragment.LoginFragmentType.INPUT_PHONE_NUMBER, null, true, false);
            }
        }

        @Override // com.xiaomi.passport.callback.LoginPhoneAccountCallback, com.xiaomi.passport.uicontroller.PhoneLoginController.TicketLoginCallback
        public void onServerError(PhoneLoginController.ErrorCode errorCode, PassThroughErrorInfo passThroughErrorInfo) {
            if (PhoneAccountLoginFragment.this.isActivityAlive()) {
                PhoneAccountLoginFragment.this.mLoginLoadingDialog.dismiss();
                super.onServerError(errorCode, passThroughErrorInfo);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getAccountNum() {
        List<PhoneAccount> list = this.mPhoneAccountList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.xiaomi.passport.ui.page.BaseFragment
    protected BaseFragment.StatParams getStatParams() {
        int accountNum = getAccountNum();
        if (accountNum == 1) {
            return new BaseFragment.StatParams("一键登录页面", getString(R.string.passport_stat_tip_single_sim_login_page_browse));
        }
        if (accountNum > 1) {
            return new BaseFragment.StatParams("一键登录页面", getString(R.string.passport_stat_tip_double_sim_login_page_browse));
        }
        return new BaseFragment.StatParams("一键登录页面", null);
    }

    @Override // com.xiaomi.passport.ui.page.BaseLoginFragment
    protected String getLoginTypeStatName() {
        return this.mStatLoginType;
    }
}
