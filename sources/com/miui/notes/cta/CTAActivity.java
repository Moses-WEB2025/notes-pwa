package com.miui.notes.cta;

import android.content.DialogInterface;
import android.os.Bundle;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.base.BaseActivity;
import com.miui.common.tool.Logger;
import com.miui.notes.R;
import com.miui.notes.tool.PermissionUtils;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.notes.tool.SyncUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: compiled from: CTAActivity.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\rH\u0014J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u0011\u001a\u00020\u000bH\u0014J\b\u0010\u0012\u001a\u00020\u000bH\u0014J\u0006\u0010\u0013\u001a\u00020\u000bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\u00078\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/cta/CTAActivity;", "Lcom/miui/common/base/BaseActivity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mCtaDialog", "Lmiuix/appcompat/app/AlertDialog;", "isShowedCtaDialog", "", "isShowedCtaDialogV12", "needShowCtaDialog", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onSaveInstanceState", "outState", "onRestoreInstanceState", "onPause", "onDestroy", "showCtaDialog", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class CTAActivity extends BaseActivity {
    private static final String TAG = "CTAActivity";
    private boolean isShowedCtaDialog;
    protected boolean isShowedCtaDialogV12;
    private AlertDialog mCtaDialog;
    protected boolean needShowCtaDialog;

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!PermissionUtils.supportNewPermissionStyle()) {
            if (PreferenceUtils.isCTAAccepted(getApplicationContext()) || !PreferenceUtils.isFirstHandle(getApplicationContext())) {
                return;
            }
            showCtaDialog();
            return;
        }
        this.needShowCtaDialog = PreferenceUtils.isFirstHandle(getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onSaveInstanceState");
        outState.putBoolean("show_cta_v12", this.isShowedCtaDialogV12);
        outState.putBoolean("need_show_cta", this.needShowCtaDialog);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, android.app.Activity
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(savedInstanceState, "savedInstanceState");
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        this.isShowedCtaDialogV12 = savedInstanceState.getBoolean("show_cta_v12", false);
        this.needShowCtaDialog = savedInstanceState.getBoolean("need_show_cta", true);
    }

    @Override // com.miui.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onPause");
        this.isShowedCtaDialog = false;
        super.onPause();
    }

    @Override // com.miui.common.base.BaseActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Logger logger = Logger.INSTANCE;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        logger.d(simpleName, "onDestroy");
        CTAManager.getInstance().removeAllListener();
        AlertDialog alertDialog = this.mCtaDialog;
        if (alertDialog != null) {
            Intrinsics.checkNotNull(alertDialog);
            if (alertDialog.isShowing()) {
                AlertDialog alertDialog2 = this.mCtaDialog;
                Intrinsics.checkNotNull(alertDialog2);
                alertDialog2.dismiss();
                this.mCtaDialog = null;
            }
        }
        super.onDestroy();
    }

    public final void showCtaDialog() {
        AlertDialog alertDialog = this.mCtaDialog;
        if (alertDialog != null) {
            Intrinsics.checkNotNull(alertDialog);
            if (alertDialog.isShowing()) {
                return;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.cta_dialog_title);
        builder.setMessage(R.string.cta_dialog_message);
        builder.setPositiveButton(R.string.cta_confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.cta.CTAActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CTAActivity.showCtaDialog$lambda$0(this.f$0, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.cta_confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.cta.CTAActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CTAActivity.showCtaDialog$lambda$1(this.f$0, dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        this.mCtaDialog = alertDialogCreate;
        Intrinsics.checkNotNull(alertDialogCreate);
        alertDialogCreate.show();
        this.isShowedCtaDialog = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCtaDialog$lambda$0(CTAActivity cTAActivity, DialogInterface dialogInterface, int i) {
        Logger.INSTANCE.i(TAG, "user agree to authorization");
        PreferenceUtils.setFirstHandle(false, cTAActivity.getApplicationContext());
        PreferenceUtils.setCTAAccepted(true, cTAActivity.getApplicationContext());
        PreferenceUtils.setAllowNetwork(BaseNoteApp.INSTANCE.getInstance(), true);
        PreferenceUtils.setAllowXiaoai(BaseNoteApp.INSTANCE.getInstance(), true);
        CTAManager.getInstance().notifyAccept();
        Bundle bundle = new Bundle();
        bundle.putInt("push_type", 0);
        SyncUtils.requestSync(BaseNoteApp.INSTANCE.getInstance(), bundle, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showCtaDialog$lambda$1(CTAActivity cTAActivity, DialogInterface dialogInterface, int i) {
        Logger.INSTANCE.i(TAG, "user deny to authorization");
        PreferenceUtils.setFirstHandle(false, cTAActivity.getApplicationContext());
        PreferenceUtils.setCTAAccepted(false, cTAActivity.getApplicationContext());
        PreferenceUtils.setAllowNetwork(BaseNoteApp.INSTANCE.getInstance(), false);
        PreferenceUtils.setAllowXiaoai(BaseNoteApp.INSTANCE.getInstance(), false);
        CTAManager.getInstance().notifyReject();
    }
}
