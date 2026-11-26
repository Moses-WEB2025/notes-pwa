package com.xiaomi.passport.ui.page;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.accountmanager.XiaomiAccountManagerCallback;
import com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture;
import com.xiaomi.passport.ui.BaseActivity;
import com.xiaomi.passport.ui.internal.LayoutWrapperActivity;
import com.xiaomi.passport.ui.utils.AccountToast;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes4.dex */
public class UserInfoActivity extends LayoutWrapperActivity {
    private static final int REQUEST_CODE_CONFIRM_CREDENTIAL = 1000;
    private static final int REQUEST_CODE_LOGOUT = 1001;
    private static final String TAG = "UserInfoActivity";
    private Account mAccount;
    private XiaomiAccountManager mAccountManager;
    private AccountManagerFuture mConfirmCredentialFuture;

    @Override // com.xiaomi.passport.ui.internal.LayoutWrapperActivity
    public void onCreateContentView(ViewGroup viewGroup) {
        LayoutInflater.from(this).inflate(R.layout.passport_activity_user_info, viewGroup);
    }

    @Override // com.xiaomi.passport.ui.internal.LayoutWrapperActivity
    public void onCreateFooterView(ViewGroup viewGroup) {
        View.inflate(this, R.layout.passport_layout_user_info_footer, viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.passport.ui.internal.LayoutWrapperActivity, com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHeaderTitle(getString(R.string.passport_account_user_details));
        XiaomiAccountManager xiaomiAccountManager = XiaomiAccountManager.get(this);
        this.mAccountManager = xiaomiAccountManager;
        Account xiaomiAccount = xiaomiAccountManager.getXiaomiAccount();
        this.mAccount = xiaomiAccount;
        if (xiaomiAccount == null) {
            finish();
            AccountToast.showToastMessage(this, R.string.passport_no_account);
        } else if (TextUtils.isEmpty(this.mAccountManager.getPassToken(xiaomiAccount))) {
            this.mConfirmCredentialFuture = this.mAccountManager.confirmCredentials(this.mAccount, null, new AccountManagerCallback<Bundle>() { // from class: com.xiaomi.passport.ui.page.UserInfoActivity.1
                @Override // android.accounts.AccountManagerCallback
                public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                    try {
                        Intent intent = (Intent) accountManagerFuture.getResult().getParcelable("intent");
                        if (intent != null) {
                            intent.putExtra("ref", UserInfoActivity.this.getStatParams().mActivityRefName);
                            UserInfoActivity.this.startActivityForResult(intent, 1000);
                        }
                    } catch (AuthenticatorException e) {
                        AccountLogger.log(UserInfoActivity.TAG, "auth", e);
                    } catch (OperationCanceledException e2) {
                        AccountLogger.log(UserInfoActivity.TAG, ToDoStat.CANCEL, e2);
                    } catch (IOException e3) {
                        AccountLogger.log(UserInfoActivity.TAG, "io", e3);
                    }
                }
            }, null);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        AccountManagerFuture accountManagerFuture = this.mConfirmCredentialFuture;
        if (accountManagerFuture != null) {
            accountManagerFuture.cancel(true);
            this.mConfirmCredentialFuture = null;
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1000) {
            if (i2 != -1) {
                finish();
            }
        } else if (i == 1001 && i2 == -1) {
            finish();
        }
    }

    public void onLogoutClicked(View view) {
        AccountStatInterface.getInstance().statEventWithTip(StatConstants.Event.LOGIN_OUT, getString(R.string.passport_stat_tip_user_info_page_click_login_out), "ref", getStatParams().mActivityRefName);
        this.mAccountManager.removeXiaomiAccount(new _RemoveAccountCallback(this), null);
    }

    public static class _RemoveAccountCallback implements XiaomiAccountManagerCallback<Bundle> {
        public final WeakReference<Activity> activityRef;

        public _RemoveAccountCallback(Activity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        @Override // com.xiaomi.passport.accountmanager.XiaomiAccountManagerCallback
        public void run(XiaomiAccountManagerFuture<Bundle> xiaomiAccountManagerFuture) {
            Activity activity = this.activityRef.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            try {
                Bundle result = xiaomiAccountManagerFuture.getResult();
                if (result.getBoolean("booleanResult")) {
                    activity.finish();
                    return;
                }
                Intent intent = (Intent) result.getParcelable("intent");
                if (intent == null) {
                    AccountLogger.log(UserInfoActivity.TAG, "get null intent when logout");
                    AccountToast.showToastMessage(activity, R.string.passport_request_error_unknown);
                } else {
                    activity.startActivityForResult(intent, 1001);
                }
            } catch (AuthenticatorException | OperationCanceledException | IOException e) {
                AccountLogger.log(UserInfoActivity.TAG, "logout failed", e);
                AccountToast.showToastMessage(activity, R.string.passport_request_error_unknown);
            }
        }
    }

    @Override // com.xiaomi.passport.ui.BaseActivity
    protected BaseActivity.StatParams getStatParams() {
        return new BaseActivity.StatParams("用户中心页面", null);
    }
}
