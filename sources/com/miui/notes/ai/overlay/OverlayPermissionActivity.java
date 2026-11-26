package com.miui.notes.ai.overlay;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.ai.text.widget.R;
import com.miui.notes.ai.utils.PermissionUtils;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes4.dex */
public class OverlayPermissionActivity extends AppCompatActivity {
    private Dialog mGuideDialog;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mGuideDialog = showOverlayPermissionDialog(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        Dialog dialog = this.mGuideDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.mGuideDialog.dismiss();
        finish();
    }

    public static Dialog showOverlayPermissionDialog(final Activity activity) {
        View viewInflate = activity.getLayoutInflater().inflate(R.layout.new_custom_guide_dialog, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.main_purpose);
        textView.setAutoLinkMask(15);
        textView.setText(R.string.xiaomi_overlay_agree_desc);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return new AlertDialog.Builder(activity).setTitle(R.string.xiaomi_overlay_agree_title).setView(viewInflate).setPositiveButton(R.string.xiaomi_overlay_agree_confirm_ok, new DialogInterface.OnClickListener() { // from class: com.miui.notes.ai.overlay.OverlayPermissionActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                PermissionUtils.applyOverlayPermission(activity);
                activity.finish();
            }
        }).setNegativeButton(R.string.ai_text_confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.ai.overlay.OverlayPermissionActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                activity.finish();
            }
        }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.notes.ai.overlay.OverlayPermissionActivity.1
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                activity.finish();
            }
        }).show();
    }
}
