package com.miui.notes.ui.activity;

import android.app.Dialog;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.BaseAlarmAlertActivity;
import com.miui.notes.cta.CTAActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DialogManagedActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0005J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\b\u0010\r\u001a\u00020\u0007H\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\f¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/ui/activity/DialogManagedActivity;", "Lcom/miui/notes/cta/CTAActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mManagedDialog", "Landroid/app/Dialog;", "manageDialog", "", BaseAlarmAlertActivity.DIALOG_FRAGMENT_TAG, "releaseDialog", "isDialogShowing", "", "()Z", "onDestroy", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class DialogManagedActivity extends CTAActivity {
    private Dialog mManagedDialog;

    public final void manageDialog(Dialog dialog) {
        this.mManagedDialog = dialog;
    }

    public final void releaseDialog(Dialog dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        if (this.mManagedDialog == dialog) {
            this.mManagedDialog = null;
        }
    }

    public final boolean isDialogShowing() {
        Dialog dialog = this.mManagedDialog;
        if (dialog == null) {
            return false;
        }
        Intrinsics.checkNotNull(dialog);
        return dialog.isShowing();
    }

    @Override // com.miui.notes.cta.CTAActivity, com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Dialog dialog = this.mManagedDialog;
        if (dialog != null) {
            Intrinsics.checkNotNull(dialog);
            dialog.dismiss();
            this.mManagedDialog = null;
        }
        super.onDestroy();
    }
}
