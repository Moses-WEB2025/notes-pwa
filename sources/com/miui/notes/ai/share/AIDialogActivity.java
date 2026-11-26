package com.miui.notes.ai.share;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.miui.ai.text.widget.R;
import com.miui.notes.base.utils.Logger;
import java.lang.ref.WeakReference;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: loaded from: classes4.dex */
public class AIDialogActivity extends Activity {
    public static final int DIALOG_TYPE_FAIL = 2;
    public static final int DIALOG_TYPE_PROGRESS = 1;
    public static final int DIALOG_TYPE_RETRY = 3;
    public static final String EXTRA_DIALOG_TYPE = "dialog_type";
    private static final String TAG = "NOTE:AIDialogActivity";
    private static WeakReference<DialogCancelListener> mDialogCancelListener;
    private static WeakReference<DialogCloseListener> mDialogCloseListener;
    private static WeakReference<DialogRetryListener> mDialogRetryListener;
    private static ProgressDialog progressDialog;
    private boolean isFailDialogShow = false;
    private boolean isRetryDialogShow = false;
    private Context mContext;

    public interface DialogCancelListener {
        void onCancelClicked();
    }

    public interface DialogCloseListener {
        void onCloseClicked();
    }

    public interface DialogRetryListener {
        void onRetryClicked();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        requestWindowFeature(1);
        setContentView(R.layout.ai_dialog_layout);
        int intExtra = getIntent().getIntExtra(EXTRA_DIALOG_TYPE, 0);
        Logger.INSTANCE.d(TAG, "onCreate dialogType :" + intExtra);
        if (intExtra == 1) {
            showProgressDialog();
            return;
        }
        if (intExtra == 2) {
            showFailDialog();
        } else if (intExtra == 3) {
            showRetryDialog();
        } else {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = intent.getIntExtra(EXTRA_DIALOG_TYPE, 0);
        Logger.INSTANCE.d(TAG, "onNewIntent dialogType :" + intExtra);
        if (intExtra == 1) {
            showProgressDialog();
            return;
        }
        if (intExtra == 2) {
            showFailDialog();
        } else if (intExtra == 3) {
            showRetryDialog();
        } else {
            finish();
        }
    }

    public static void setDialogCancelListener(DialogCancelListener dialogCancelListener) {
        mDialogCancelListener = new WeakReference<>(dialogCancelListener);
    }

    public static void setDialogCloseListener(DialogCloseListener dialogCloseListener) {
        mDialogCloseListener = new WeakReference<>(dialogCloseListener);
    }

    public static void setDialogRetryListener(DialogRetryListener dialogRetryListener) {
        mDialogRetryListener = new WeakReference<>(dialogRetryListener);
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            ProgressDialog progressDialog2 = new ProgressDialog(this);
            progressDialog = progressDialog2;
            progressDialog2.setButton(-2, this.mContext.getText(R.string.ai_button_cancel), new DialogInterface.OnClickListener() { // from class: com.miui.notes.ai.share.AIDialogActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$showProgressDialog$0(dialogInterface, i);
                }
            });
            progressDialog.setMessage(this.mContext.getString(R.string.ai_progress_dialog_message));
            progressDialog.setProgressStyle(0);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.notes.ai.share.AIDialogActivity$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$showProgressDialog$1(dialogInterface);
                }
            });
            if (progressDialog.isShowing()) {
                return;
            }
            progressDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProgressDialog$0(DialogInterface dialogInterface, int i) {
        Logger.INSTANCE.d(TAG, "showProgressDialog onCancelClicked");
        dismissProgressDialog();
        finish();
        DialogCancelListener dialogCancelListener = mDialogCancelListener.get();
        if (dialogCancelListener != null) {
            dialogCancelListener.onCancelClicked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProgressDialog$1(DialogInterface dialogInterface) {
        if (this.isFailDialogShow || this.isRetryDialogShow) {
            return;
        }
        finish();
    }

    public static void dismissProgressDialog() {
        ProgressDialog progressDialog2 = progressDialog;
        if (progressDialog2 == null) {
            return;
        }
        if (progressDialog2.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (IllegalArgumentException e) {
                Logger.INSTANCE.e(TAG, "ProgressDialog already dismissed " + e.getMessage());
            }
        }
        progressDialog = null;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        dismissProgressDialog();
        this.mContext = null;
        this.isFailDialogShow = false;
        this.isRetryDialogShow = false;
        super.onDestroy();
    }

    public void showFailDialog() {
        dismissProgressDialog();
        this.isFailDialogShow = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setButtonForceVertical(true);
        builder.setTitle(R.string.ai_dialog_title);
        builder.setMessage(R.string.ai_dialog_cancel_message);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.notes.ai.share.AIDialogActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showFailDialog$2(dialogInterface, i);
            }
        };
        builder.setNegativeButton(R.string.ai_button_cancel, onClickListener);
        builder.setPositiveButton(R.string.ai_button_close, onClickListener);
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFailDialog$2(DialogInterface dialogInterface, int i) {
        Logger.INSTANCE.d(TAG, "showFailDialog onClicked");
        dialogInterface.dismiss();
        this.isFailDialogShow = false;
        finish();
        if (i == -1) {
            DialogCloseListener dialogCloseListener = mDialogCloseListener.get();
            if (dialogCloseListener != null) {
                dialogCloseListener.onCloseClicked();
                return;
            }
            return;
        }
        DialogCancelListener dialogCancelListener = mDialogCancelListener.get();
        if (dialogCancelListener != null) {
            dialogCancelListener.onCancelClicked();
        }
    }

    public void showRetryDialog() {
        dismissProgressDialog();
        this.isRetryDialogShow = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.ai_dialog_title);
        builder.setMessage(R.string.ai_dialog_retry_message);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.notes.ai.share.AIDialogActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showRetryDialog$3(dialogInterface, i);
            }
        };
        builder.setNegativeButton(R.string.ai_button_cancel, onClickListener);
        builder.setPositiveButton(R.string.ai_button_retry, onClickListener);
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRetryDialog$3(DialogInterface dialogInterface, int i) {
        Logger.INSTANCE.d(TAG, "showRetryDialog onCancelClicked");
        dialogInterface.dismiss();
        this.isRetryDialogShow = false;
        if (i == -1) {
            DialogRetryListener dialogRetryListener = mDialogRetryListener.get();
            if (dialogRetryListener != null) {
                dialogRetryListener.onRetryClicked();
                return;
            }
            return;
        }
        DialogCancelListener dialogCancelListener = mDialogCancelListener.get();
        if (dialogCancelListener != null) {
            dialogCancelListener.onCancelClicked();
        }
        finish();
    }
}
