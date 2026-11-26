package com.miui.ai.text.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.miui.ai.text.widget.download.AIcrDownloadHelper;
import com.miui.notes.basefeature.NotesNormalConstants;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes2.dex */
public class AiLocalDownloadActivity extends AppCompatActivity {
    private static final String TAG = "AiLocalDownloadActivity";
    private Dialog mLocalDownloadDialog;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mLocalDownloadDialog = showLocalDownloadOrUpdateDialog(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        Dialog dialog = this.mLocalDownloadDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.mLocalDownloadDialog.dismiss();
        finish();
    }

    public static Dialog showLocalDownloadOrUpdateDialog(final Activity activity) {
        int i = R.string.aicr_update_title;
        int i2 = R.string.aicr_update_content;
        int i3 = R.string.aicr_update_button;
        if (!AIcrDownloadHelper.getModelStatus(activity)) {
            i = R.string.aicr_down_title;
            i2 = R.string.aicr_down_content;
            i3 = R.string.aicr_download_confirm;
        }
        return new AlertDialog.Builder(activity).setTitle(i).setMessage(i2).setPositiveButton(i3, new DialogInterface.OnClickListener() { // from class: com.miui.ai.text.widget.AiLocalDownloadActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i4) {
                Intent intent = new Intent();
                intent.setClassName(NotesNormalConstants.AICR_PACKAGE, "com.xiaomi.aicr.common.download.ModelActivity");
                activity.startActivity(intent);
                dialogInterface.dismiss();
                activity.finish();
            }
        }).setNegativeButton(R.string.ai_text_confirm_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.ai.text.widget.AiLocalDownloadActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i4) {
                dialogInterface.dismiss();
                activity.finish();
            }
        }).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.miui.ai.text.widget.AiLocalDownloadActivity.1
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                activity.finish();
            }
        }).show();
    }
}
