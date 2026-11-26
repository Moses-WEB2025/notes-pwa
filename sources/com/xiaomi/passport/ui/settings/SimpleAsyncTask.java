package com.xiaomi.passport.ui.settings;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.utils.AsyncTestMarker;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleAsyncTask<T> extends AsyncTask<Void, Void, T> {
    private boolean dialogCancelable;
    private final String dialogMessage;
    private final DoInBackgroundRunnable<T> doInBackgroundRunnable;
    private final FragmentManager fragmentManager;
    private SimpleDialogFragment mProgressDialog;
    private final OnPostExecuteRunnable<T> onPostExecuteRunnable;

    public interface DoInBackgroundRunnable<T> {
        T run();
    }

    public interface OnPostExecuteRunnable<T> {
        void run(T t);
    }

    private SimpleAsyncTask(Builder<T> builder) {
        this.fragmentManager = ((Builder) builder).fragmentManager;
        this.dialogMessage = ((Builder) builder).dialogMessage;
        this.dialogCancelable = ((Builder) builder).dialogCancelable;
        this.doInBackgroundRunnable = ((Builder) builder).doInBackgroundRunnable;
        this.onPostExecuteRunnable = ((Builder) builder).onPostExecuteRunnable;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public T doInBackground(Void... voidArr) {
        DoInBackgroundRunnable<T> doInBackgroundRunnable = this.doInBackgroundRunnable;
        if (doInBackgroundRunnable != null) {
            return doInBackgroundRunnable.run();
        }
        return null;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
        AsyncTestMarker.increment();
    }

    @Override // android.os.AsyncTask
    protected void onPostExecute(T t) {
        dismissProgressDialog();
        super.onPostExecute(t);
        OnPostExecuteRunnable<T> onPostExecuteRunnable = this.onPostExecuteRunnable;
        if (onPostExecuteRunnable != null) {
            onPostExecuteRunnable.run(t);
        }
        AsyncTestMarker.decrement();
    }

    @Override // android.os.AsyncTask
    protected void onCancelled(T t) {
        super.onCancelled(t);
        dismissProgressDialog();
        AsyncTestMarker.decrement();
    }

    public boolean isRunning() {
        return getStatus() != AsyncTask.Status.FINISHED;
    }

    private void showProgressDialog() {
        if (this.fragmentManager == null || TextUtils.isEmpty(this.dialogMessage)) {
            return;
        }
        SimpleDialogFragment simpleDialogFragmentCreate = new SimpleDialogFragment.AlertDialogFragmentBuilder(2).setMessage(this.dialogMessage).create();
        this.mProgressDialog = simpleDialogFragmentCreate;
        simpleDialogFragmentCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                SimpleAsyncTask.this.cancel(true);
            }
        });
        this.mProgressDialog.setCancelable(this.dialogCancelable);
        this.mProgressDialog.showAllowingStateLoss(this.fragmentManager, "SimpleAsyncTask");
    }

    private void dismissProgressDialog() {
        SimpleDialogFragment simpleDialogFragment = this.mProgressDialog;
        if (simpleDialogFragment == null || simpleDialogFragment.getActivity() == null || this.mProgressDialog.getActivity().isFinishing()) {
            return;
        }
        this.mProgressDialog.dismissAllowingStateLoss();
        this.mProgressDialog = null;
    }

    public static class Builder<T> {
        private boolean dialogCancelable = true;
        private String dialogMessage;
        private DoInBackgroundRunnable<T> doInBackgroundRunnable;
        private FragmentManager fragmentManager;
        private OnPostExecuteRunnable<T> onPostExecuteRunnable;

        public Builder<T> setProgressDialogMessage(FragmentManager fragmentManager, String str) {
            this.fragmentManager = fragmentManager;
            this.dialogMessage = str;
            return this;
        }

        public Builder<T> setProgressDialogCancelable(boolean z) {
            this.dialogCancelable = z;
            return this;
        }

        public Builder<T> setDoInBackgroundRunnable(DoInBackgroundRunnable<T> doInBackgroundRunnable) {
            this.doInBackgroundRunnable = doInBackgroundRunnable;
            return this;
        }

        public Builder<T> setOnPostExecuteRunnable(OnPostExecuteRunnable<T> onPostExecuteRunnable) {
            this.onPostExecuteRunnable = onPostExecuteRunnable;
            return this;
        }

        public SimpleAsyncTask<T> build() {
            return new SimpleAsyncTask<>(this);
        }
    }

    public enum ResultType {
        SUCCESS { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.1
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return 0;
            }
        },
        ERROR_PASSWORD { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.2
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_bad_authentication;
            }
        },
        ERROR_AUTH_FAIL { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.3
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_auth_fail_warning;
            }
        },
        ERROR_NETWORK { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.4
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_error_network;
            }
        },
        ERROR_SERVER { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.5
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_error_server;
            }
        },
        ERROR_ACCESS_DENIED { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.6
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_access_denied;
            }
        },
        ERROR_CAPTCHA { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.7
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_wrong_captcha;
            }
        },
        ERROR_DEVICE_ID { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.8
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_error_device_id;
            }
        },
        ERROR_VERIFY_CODE { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.9
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_wrong_vcode;
            }
        },
        ERROR_USER_ACTION_RESTRICTED { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.10
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_service_error;
            }
        },
        ERROR_GET_PHONE_VERIFY_CODE_OVER_LIMIT { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.11
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_get_phone_verifycode_exceed_limit;
            }
        },
        ERROR_SAME_NEW_AND_OLD_PASS { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.12
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_same_new_and_old_pwd;
            }
        },
        ERROR_UNKNOWN { // from class: com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType.13
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.ResultType
            public int getErrorMessageResId() {
                return R.string.passport_error_unknown;
            }
        };

        public abstract int getErrorMessageResId();

        public boolean success() {
            return this == SUCCESS;
        }
    }
}
