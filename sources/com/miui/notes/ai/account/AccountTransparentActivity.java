package com.miui.notes.ai.account;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.ai.text.widget.R;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.notes.base.utils.Logger;
import java.lang.ref.WeakReference;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: compiled from: AccountTransparentActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00132\u00020\u0001:\u0004\u0010\u0011\u0012\u0013B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\f\u001a\u00020\tH\u0014J\b\u0010\r\u001a\u00020\tH\u0016J\b\u0010\u000e\u001a\u00020\tH\u0014J\b\u0010\u000f\u001a\u00020\u0007H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/account/AccountTransparentActivity;", "Lmiuix/appcompat/app/AppCompatActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "tag", "", "mGuideDialog", "Landroid/app/Dialog;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "onBackPressed", "onDestroy", "showXiaoMiAcceptDialog", "AccountMgrCallbackImpl", "OnDialogBtnClickCall", "DialogCancelListener", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AccountTransparentActivity extends AppCompatActivity {
    private static final String AI_SID = "hyperos_sec_common";
    private Dialog mGuideDialog;
    private final String tag = "AccountTransparentActivity";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.INSTANCE.d("NoteApp", "AccountTransparentActivity/onCreate");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (!ExtraAccountManager.isLogin(this)) {
            this.mGuideDialog = showXiaoMiAcceptDialog();
        } else {
            finish();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        Dialog dialog = this.mGuideDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            if (dialog.isShowing()) {
                Dialog dialog2 = this.mGuideDialog;
                Intrinsics.checkNotNull(dialog2);
                dialog2.dismiss();
                finish();
            }
        }
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Dialog dialog = this.mGuideDialog;
        if (dialog != null) {
            if (!dialog.isShowing()) {
                dialog = null;
            }
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        super.onDestroy();
    }

    /* JADX INFO: compiled from: AccountTransparentActivity.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0019\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\u0010\u001a\u00020\u00112\u0010\u0010\u0012\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0013H\u0016R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001f\u0010\u000b\u001a\u0010\u0012\f\u0012\n \r*\u0004\u0018\u00010\u00060\u00060\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/account/AccountTransparentActivity$AccountMgrCallbackImpl;", "Landroid/accounts/AccountManagerCallback;", "Landroid/os/Bundle;", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/content/DialogInterface;", "act", "Lcom/miui/notes/ai/account/AccountTransparentActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/DialogInterface;Lcom/miui/notes/ai/account/AccountTransparentActivity;)V", "getDialog", "()Landroid/content/DialogInterface;", "activityRef", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "getActivityRef", "()Ljava/lang/ref/WeakReference;", "run", "", "future", "Landroid/accounts/AccountManagerFuture;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class AccountMgrCallbackImpl implements AccountManagerCallback<Bundle> {
        private final WeakReference<AccountTransparentActivity> activityRef;
        private final DialogInterface dialog;

        public AccountMgrCallbackImpl(DialogInterface dialogInterface, AccountTransparentActivity act) {
            Intrinsics.checkNotNullParameter(act, "act");
            this.dialog = dialogInterface;
            this.activityRef = new WeakReference<>(act);
        }

        public final DialogInterface getDialog() {
            return this.dialog;
        }

        public final WeakReference<AccountTransparentActivity> getActivityRef() {
            return this.activityRef;
        }

        @Override // android.accounts.AccountManagerCallback
        public void run(AccountManagerFuture<Bundle> future) {
            Bundle result;
            if (future != null) {
                try {
                    result = future.getResult();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.INSTANCE.e("AccountTransparentActivity", "showXiaoaiAcceptDialog/toLogin " + ExceptionsKt.stackTraceToString(e));
                    return;
                }
            } else {
                result = null;
            }
            if (result == null || !result.getBoolean("booleanResult")) {
                return;
            }
            DialogInterface dialogInterface = this.dialog;
            if (dialogInterface != null) {
                dialogInterface.dismiss();
            }
            AccountTransparentActivity accountTransparentActivity = this.activityRef.get();
            if (accountTransparentActivity != null) {
                accountTransparentActivity.finish();
            }
            if (AccountListenerHolder.getInstance() != null) {
                AccountListenerHolder.getInstance().loginSuccess();
            }
        }
    }

    /* JADX INFO: compiled from: AccountTransparentActivity.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001f\u0010\n\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u00030\u00030\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lcom/miui/notes/ai/account/AccountTransparentActivity$OnDialogBtnClickCall;", "Landroid/content/DialogInterface$OnClickListener;", "activity", "Lcom/miui/notes/ai/account/AccountTransparentActivity;", "type", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ai/account/AccountTransparentActivity;I)V", "getType", "()I", "activityRef", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "getActivityRef", "()Ljava/lang/ref/WeakReference;", "onClick", "", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/content/DialogInterface;", "which", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class OnDialogBtnClickCall implements DialogInterface.OnClickListener {
        private final WeakReference<AccountTransparentActivity> activityRef;
        private final int type;

        public OnDialogBtnClickCall(AccountTransparentActivity activity, int i) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            this.type = i;
            this.activityRef = new WeakReference<>(activity);
        }

        public final int getType() {
            return this.type;
        }

        public final WeakReference<AccountTransparentActivity> getActivityRef() {
            return this.activityRef;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            if (this.type == 0) {
                if (this.activityRef.get() == null) {
                    return;
                }
                AccountManager accountManager = AccountManager.get(this.activityRef.get());
                AccountTransparentActivity accountTransparentActivity = this.activityRef.get();
                AccountTransparentActivity accountTransparentActivity2 = this.activityRef.get();
                Intrinsics.checkNotNull(accountTransparentActivity2);
                accountManager.addAccount("com.xiaomi", "hyperos_sec_common", null, null, accountTransparentActivity, new AccountMgrCallbackImpl(dialog, accountTransparentActivity2), null);
                return;
            }
            Logger.INSTANCE.e("AccountTransparentActivity", "showXiaoaiAcceptDialog/ cancel ");
            if (dialog != null) {
                dialog.dismiss();
            }
            AccountTransparentActivity accountTransparentActivity3 = this.activityRef.get();
            if (accountTransparentActivity3 != null) {
                accountTransparentActivity3.finish();
            }
            if (AccountListenerHolder.getInstance() != null) {
                AccountListenerHolder.getInstance().cancel();
            }
        }
    }

    /* JADX INFO: compiled from: AccountTransparentActivity.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R\u001f\u0010\u0006\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00030\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lcom/miui/notes/ai/account/AccountTransparentActivity$DialogCancelListener;", "Landroid/content/DialogInterface$OnCancelListener;", "activity", "Lcom/miui/notes/ai/account/AccountTransparentActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/ai/account/AccountTransparentActivity;)V", "activityRef", "Ljava/lang/ref/WeakReference;", "kotlin.jvm.PlatformType", "getActivityRef", "()Ljava/lang/ref/WeakReference;", "onCancel", "", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "Landroid/content/DialogInterface;", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class DialogCancelListener implements DialogInterface.OnCancelListener {
        private final WeakReference<AccountTransparentActivity> activityRef;

        public DialogCancelListener(AccountTransparentActivity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            this.activityRef = new WeakReference<>(activity);
        }

        public final WeakReference<AccountTransparentActivity> getActivityRef() {
            return this.activityRef;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialog) {
            AccountTransparentActivity accountTransparentActivity = this.activityRef.get();
            if (accountTransparentActivity != null) {
                accountTransparentActivity.finish();
            }
            if (AccountListenerHolder.getInstance() != null) {
                AccountListenerHolder.getInstance().cancel();
            }
        }
    }

    private final Dialog showXiaoMiAcceptDialog() {
        Logger.INSTANCE.d(this.tag, "showXiaoMiAcceptDialog");
        View viewInflate = getLayoutInflater().inflate(R.layout.new_custom_guide_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.main_purpose);
        textView.setAutoLinkMask(15);
        textView.setText(R.string.xiaomi_account_agree_desc);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        AlertDialog alertDialogShow = new AlertDialog.Builder(this).setTitle(R.string.xiaomi_account_agree_title).setView(viewInflate).setPositiveButton(R.string.xiaomi_account_agree_confirm_ok, new OnDialogBtnClickCall(this, 0)).setNegativeButton(R.string.ai_text_confirm_cancel, new OnDialogBtnClickCall(this, 1)).setCancelable(true).setOnCancelListener(new DialogCancelListener(this)).show();
        Intrinsics.checkNotNullExpressionValue(alertDialogShow, "show(...)");
        return alertDialogShow;
    }
}
