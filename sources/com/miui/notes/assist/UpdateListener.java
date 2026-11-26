package com.miui.notes.assist;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.CheckBox;
import com.market.sdk.UpdateResponse;
import com.market.sdk.XiaomiUpdateAgent;
import com.market.sdk.XiaomiUpdateListener;
import com.miui.common.tool.Logger;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.tool.PreferenceUtils;
import com.miui.todo.utils.CalenderUtils;
import java.lang.ref.WeakReference;
import java.util.Locale;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateListener implements XiaomiUpdateListener {
    private final String TAG = "UpdateListener";
    private AlertDialog mUpdateDialog;
    private final WeakReference<Activity> reference;

    public UpdateListener(Activity activity) {
        this.reference = new WeakReference<>(activity);
    }

    @Override // com.market.sdk.XiaomiUpdateListener
    public void onUpdateReturned(int i, UpdateResponse updateResponse) {
        Logger.INSTANCE.d("UpdateListener", "updateStatus:" + i);
        if (updateResponse == null || updateResponse.versionCode == 0) {
            return;
        }
        int i2 = updateResponse.versionCode;
        String str = updateResponse.versionName;
        Logger.INSTANCE.d("UpdateListener", "curMarketCode:" + i2 + " versionName:" + str);
        if (str == null) {
            return;
        }
        int length = str.length();
        if (length <= 0 || str.charAt(length - 1) == 'L') {
            int marketCode = PreferenceUtils.getMarketCode(NoteApp.getInstance());
            int packageCode = Utils.getPackageCode();
            final long jCurrentTimeMillis = System.currentTimeMillis();
            if (CalenderUtils.isSameDay(jCurrentTimeMillis, PreferenceUtils.getPopDialogTime())) {
                return;
            }
            if ((i2 <= packageCode || i2 == marketCode) && !(i2 == marketCode && PreferenceUtils.isUpdateRemindAgain(this.reference.get()))) {
                return;
            }
            AlertDialog alertDialog = this.mUpdateDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            if (this.reference.get() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.reference.get());
                builder.setCancelable(false);
                builder.setTitle(R.string.dlg_update_title);
                if (updateResponse.matchLanguage && "zh".equals(Locale.getDefault().getLanguage())) {
                    builder.setMessage(updateResponse.updateLog);
                } else {
                    builder.setMessage(R.string.default_update_documents);
                }
                builder.setCheckBox(false, NoteApp.getInstance().getString(R.string.dlg_update_checkbox_title));
                builder.setPositiveButton(R.string.dlg_update_update_now, new DialogInterface.OnClickListener() { // from class: com.miui.notes.assist.UpdateListener$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        this.f$0.lambda$onUpdateReturned$0(jCurrentTimeMillis, dialogInterface, i3);
                    }
                });
                builder.setNegativeButton(R.string.dlg_update_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.notes.assist.UpdateListener$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        this.f$0.lambda$onUpdateReturned$1(jCurrentTimeMillis, dialogInterface, i3);
                    }
                });
                this.mUpdateDialog = builder.show();
                Logger.INSTANCE.d("UpdateListener", "show update dialog");
                PreferenceUtils.setMarketCode(NoteApp.getInstance(), i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateReturned$0(long j, DialogInterface dialogInterface, int i) {
        XiaomiUpdateAgent.openMarketOrArrange();
        updateInfo(j);
        Logger.INSTANCE.d("UpdateListener", "openMarketOrArrange");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateReturned$1(long j, DialogInterface dialogInterface, int i) {
        updateInfo(j);
    }

    private void updateInfo(long j) {
        PreferenceUtils.setPopDialogTime(j);
        try {
            PreferenceUtils.setUpdateRemindAgain(!((CheckBox) this.mUpdateDialog.getWindow().getDecorView().findViewById(android.R.id.checkbox)).isChecked(), this.reference.get());
        } catch (Exception e) {
            Logger.INSTANCE.e("UpdateListener", "updateInfo error: " + e);
        }
    }
}
