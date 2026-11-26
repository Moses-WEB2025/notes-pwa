package com.miui.notes.ai.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.hardware.DataSpace;
import com.miui.ai.text.widget.R;
import com.miui.common.stat.NoteAppStat;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionUtils {
    public static final String ACTION_SYSTEM_PERMISSION_DECLARE = "miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW";
    public static final int CTA_RESULT_AGREE = 1;
    public static final int CTA_RESULT_LOCAL_CHANGE = -3;
    public static final int CTA_RESULT_REJECT = 666;
    private static final String TAG = "PermissionUtils";

    public static boolean hasOverlayPermission(Context context) {
        if (context != null) {
            return Settings.canDrawOverlays(context);
        }
        return false;
    }

    public static void applyOverlayPermission(Context context) {
        if (context != null) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getApplicationContext().getPackageName()));
            intent.addFlags(DataSpace.RANGE_LIMITED);
            context.startActivity(intent);
        }
    }

    public static boolean supportNewPermissionStyle(Context context) {
        try {
            return context.getPackageManager().queryIntentActivities(new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW"), 0).size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showCtaDialog(Context context, ActivityResultLauncher<Intent> activityResultLauncher) {
        String string;
        String[] strArr;
        String[] strArr2;
        if (RomUtils.isPadDevice()) {
            string = context.getString(R.string.permission_storage_desc_pad_new_v12);
        } else {
            string = context.getString(R.string.permission_storage_desc_new_v12);
        }
        if (LiteUtils.isSuperLite()) {
            strArr2 = new String[]{"android.permission.READ_CONTACTS"};
            strArr = new String[]{context.getString(R.string.permission_read_contact_desc)};
        } else {
            strArr = new String[]{context.getString(R.string.permission_read_contact_desc), context.getString(R.string.permission_microphone_desc), string};
            strArr2 = new String[]{"android.permission.READ_CONTACTS", "android.permission-group.MICROPHONE", "android.permission-group.STORAGE"};
        }
        Intent intent = new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW");
        intent.putExtra("all_purpose", context.getResources().getString(LiteUtils.isSuperLite() ? R.string.cta_v2_app_purpose_v12_lite : R.string.cta_v2_app_purpose_v12));
        intent.putExtra("mandatory_permission", false);
        intent.putExtra("use_network", true);
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        String str = "https://terms.miui.com/doc/AiWriting.html?lang=" + language + "_" + country;
        intent.putExtra("user_agreement", str);
        intent.putExtra("privacy_policy", "https://privacy.mi.com/notes-share/" + language + "_" + country);
        if (UIUtils.isMiuiXIISdkSupported()) {
            intent.putExtra("optional_perm", strArr2);
            intent.putExtra("optional_perm_desc", strArr);
            intent.putExtra("optional_perm_show", false);
            intent.putExtra("agree_desc", context.getResources().getString(R.string.cta_agree_desc));
        } else {
            intent.putExtra("runtime_perm", strArr2);
            intent.putExtra("runtime_perm_desc", strArr);
        }
        intent.setPackage(NoteAppStat.APP_PKG.securitycenter);
        activityResultLauncher.launch(intent);
    }

    public static void requestGlobalPermissionDialog(Context context, ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent("miui.intent.action.PRIVACY_DECLARE");
        intent.putExtra("purpose", context.getResources().getString(R.string.global_cta_v2_app_purpose_v12));
        intent.putExtra("style", 1);
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        String str = "https://terms.miui.com/doc/AiWriting.html?lang=" + language + "_" + country;
        intent.putExtra("user_agreement", str);
        intent.putExtra("privacy_policy", "https://privacy.mi.com/notes-share-Global/" + language + "_" + country);
        intent.putExtra("mandatory", false);
        intent.setPackage(NoteAppStat.APP_PKG.securitycenter);
        activityResultLauncher.launch(intent);
    }
}
