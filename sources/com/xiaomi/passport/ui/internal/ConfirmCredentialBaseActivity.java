package com.xiaomi.passport.ui.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.accountmanager.XiaomiAccountManager;
import com.xiaomi.passport.ui.BaseActivity;
import com.xiaomi.passport.utils.AccountHelper;

/* JADX INFO: loaded from: classes4.dex */
public class ConfirmCredentialBaseActivity extends BaseActivity implements OnBackNotifier {
    OnBackListener mOnBackListener;

    protected boolean needTranslucentStatusBar() {
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        OnBackListener onBackListener = this.mOnBackListener;
        if (onBackListener == null || !onBackListener.onBackPressed()) {
            try {
                super.onBackPressed();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        XMPassportSettings.ensureApplicationContext(getApplication());
        isTranslucentStatusBar();
    }

    @Override // com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        setContentView(LayoutInflater.from(this).inflate(i, (ViewGroup) null));
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(View view) {
        if (isTranslucentStatusBar()) {
            view.setFitsSystemWindows(true);
        }
        super.setContentView(view);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (isTranslucentStatusBar()) {
            view.setFitsSystemWindows(true);
        }
        super.setContentView(view, layoutParams);
    }

    public boolean isTranslucentStatusBar() {
        return needTranslucentStatusBar();
    }

    protected boolean finishIfAccountExist() {
        Account xiaomiAccount = XiaomiAccountManager.get(this).getXiaomiAccount();
        if (xiaomiAccount == null) {
            return false;
        }
        finishOnResult(-1, new AccountInfo.Builder().userId(xiaomiAccount.name).build());
        return true;
    }

    @Override // com.xiaomi.passport.ui.internal.OnBackNotifier
    public void setOnBackListener(OnBackListener onBackListener) {
        this.mOnBackListener = onBackListener;
    }

    @Override // com.xiaomi.passport.ui.internal.OnBackNotifier
    public OnBackListener getOnBackListener() {
        return this.mOnBackListener;
    }

    protected void finishOnResult(int i) {
        finishOnResult(i, null);
    }

    protected void finishOnResult(int i, AccountInfo accountInfo) {
        XiaomiAccountManager.get(this).handleAccountAuthenticatorResponse(getIntent().getParcelableExtra("accountAuthenticatorResponse"), AccountHelper.getAccountAuthenticatorResponseResult(i, accountInfo, getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false)));
        setResult(i);
        finish();
    }

    @Override // com.xiaomi.passport.ui.BaseActivity
    protected BaseActivity.StatParams getStatParams() {
        return new BaseActivity.StatParams("快速验证弹窗", getString(R.string.passport_stat_tip_confirm_credential_page_browse));
    }
}
