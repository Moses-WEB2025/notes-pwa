package com.miui.handwriting.common.tools;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import com.miui.common.stat.NoteAppStat;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class PermissionUtils {
    public static final String ACTION_SYSTEM_PERMISSION_DECLARE = "miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW";
    private static final String TAG = "PermissionUtil";

    public static void requestAlertWindowPermission(Activity activity) {
        try {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            Toast.makeText(activity, activity.getResources().getText(R.string.request_alert_window_permission), 1).show();
            activity.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void showCtaDialog(ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW");
        intent.putExtra("all_purpose", HandWritingViewApplication.INSTANCE.getApplication().getResources().getString(R.string.app_cta_purpose));
        intent.putExtra("mandatory_permission", false);
        intent.putExtra("use_network", true);
        intent.putExtra("user_agreement", getUserAgreement());
        intent.putExtra("privacy_policy", getPrivacyUri());
        intent.setPackage(NoteAppStat.APP_PKG.securitycenter);
        intent.addFlags(603979776);
        activityResultLauncher.launch(intent);
    }

    public static String getPrivacyUri() {
        return String.format("https://privacy.mi.com/all/%1$s", Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry());
    }

    public static String getUserAgreement() {
        return String.format("https://terms.miui.com/doc/eula/%1$s.html", Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry());
    }
}
