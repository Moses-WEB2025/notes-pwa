package miuix.internal.webkit;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import miuix.appcompat.app.ProgressDialog;
import miuix.hybrid.R;
import miuix.internal.hybrid.provider.AbsWebView;

/* JADX INFO: loaded from: classes5.dex */
public class DefaultDeviceAccountLogin extends DeviceAccountLogin {
    private static final String DIALOG_FRAGMENT_TAG = "dialog";
    private static final int MSG_LOGIN_FINISH = 1;
    private static final int MSG_SHOW_DIALOG = 0;
    private static final int SHOW_DIALOG_DELAY = 500;
    private ProgressDialogFragment mDialogFragment;
    private Handler mHandler;
    private AbsWebView mWebView;

    public DefaultDeviceAccountLogin(Activity activity, AbsWebView absWebView) {
        super(activity);
        this.mWebView = absWebView;
        this.mHandler = new Handler() { // from class: miuix.internal.webkit.DefaultDeviceAccountLogin.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    DefaultDeviceAccountLogin.this.showLoginProgress();
                } else if (message.what == 1) {
                    DefaultDeviceAccountLogin.this.dismissDialog();
                    DefaultDeviceAccountLogin.this.mWebView.setVisibility(0);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoginProgress() {
        dismissDialog();
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        this.mDialogFragment = progressDialogFragment;
        try {
            progressDialogFragment.show(this.mActivity.getFragmentManager(), "dialog");
        } catch (IllegalStateException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        this.mHandler.removeMessages(0);
        ProgressDialogFragment progressDialogFragment = this.mDialogFragment;
        if (progressDialogFragment != null && progressDialogFragment.isAdded()) {
            this.mDialogFragment.dismissAllowingStateLoss();
        }
        this.mDialogFragment = null;
    }

    @Override // miuix.internal.webkit.DeviceAccountLogin
    public void onLoginStart() {
        this.mHandler.sendEmptyMessageDelayed(0, 500L);
    }

    @Override // miuix.internal.webkit.DeviceAccountLogin
    public void onLoginSuccess(String str) {
        this.mWebView.loadUrl(str);
    }

    @Override // miuix.internal.webkit.DeviceAccountLogin
    public void onLoginFail() {
        dismissDialog();
        this.mWebView.setVisibility(0);
        Toast.makeText(this.mActivity, R.string.web_sso_login_fail, 0).show();
    }

    @Override // miuix.internal.webkit.DeviceAccountLogin
    public void onLoginCancel() {
        dismissDialog();
        this.mWebView.setVisibility(0);
    }

    @Override // miuix.internal.webkit.DeviceAccountLogin
    public void onLoginPageFinished() {
        this.mHandler.sendEmptyMessageDelayed(1, 500L);
    }

    public static class ProgressDialogFragment extends DialogFragment {
        @Override // android.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.web_sso_login_message));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            return progressDialog;
        }
    }
}
