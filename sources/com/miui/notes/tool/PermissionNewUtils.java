package com.miui.notes.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import com.miui.common.stat.NoteAppStat;
import com.miui.common.tool.LiteUtils;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionNewUtils {
    public static boolean isFirstShowCTADialog = false;

    public interface AcceptListener {
        void accept();

        void reject();
    }

    public static void showCtaDialog(ActivityResultLauncher<Intent> activityResultLauncher) {
        String string;
        String[] strArr;
        String[] strArr2;
        com.miui.notes.ai.utils.PreferenceUtils.setFirstEnterNote(NoteApp.getInstance());
        if (RomUtils.isPadDevice()) {
            string = NoteApp.getInstance().getString(R.string.permission_storage_desc_pad_new_v12);
        } else {
            string = NoteApp.getInstance().getString(R.string.permission_storage_desc_new_v12);
        }
        if (LiteUtils.isSuperLite()) {
            strArr2 = new String[]{"android.permission.READ_CONTACTS"};
            strArr = new String[]{NoteApp.getInstance().getString(R.string.permission_read_contact_desc)};
        } else {
            strArr = new String[]{NoteApp.getInstance().getString(R.string.permission_read_contact_desc), NoteApp.getInstance().getString(R.string.permission_microphone_desc), string};
            strArr2 = new String[]{"android.permission.READ_CONTACTS", "android.permission-group.MICROPHONE", "android.permission-group.STORAGE"};
        }
        Intent intent = new Intent("miui.intent.action.SYSTEM_PERMISSION_DECLARE_NEW");
        intent.putExtra("all_purpose", NoteApp.getInstance().getResources().getString(LiteUtils.isSuperLite() ? R.string.cta_v2_app_purpose_v12_lite : R.string.cta_v2_app_purpose_v12));
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
            intent.putExtra("agree_desc", NoteApp.getInstance().getResources().getString(R.string.cta_agree_desc));
        } else {
            intent.putExtra("runtime_perm", strArr2);
            intent.putExtra("runtime_perm_desc", strArr);
        }
        intent.setPackage(NoteAppStat.APP_PKG.securitycenter);
        activityResultLauncher.launch(intent);
        isFirstShowCTADialog = true;
    }

    public static void requestOnAudioNote(ActivityResultLauncher<String[]> activityResultLauncher) {
        activityResultLauncher.launch(new String[]{"android.permission.RECORD_AUDIO", NoteApp.getInstance().getString(R.string.permission_microphone_desc)});
    }

    public static void requestOnReadContacts(ActivityResultLauncher<String[]> activityResultLauncher) {
        activityResultLauncher.launch(new String[]{"android.permission.READ_CONTACTS", NoteApp.getInstance().getString(R.string.permission_read_contact_desc)});
    }

    public static void requestOnStoragePermission(ActivityResultLauncher<String[]> activityResultLauncher) {
        String[] strArr;
        if (Utils.isAboveSDK33()) {
            strArr = new String[2];
            strArr[0] = "android.permission.READ_MEDIA_IMAGES";
            strArr[1] = RomUtils.isPadDevice() ? NoteApp.getInstance().getString(R.string.permission_photos_desc_pad) : NoteApp.getInstance().getString(R.string.permission_photos_desc);
        } else {
            strArr = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", NoteApp.getInstance().getString(R.string.permission_storage_desc_new_v12)};
        }
        activityResultLauncher.launch(strArr);
    }

    public static boolean isPermissionGranted(Context context, String str) {
        return ActivityCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean hasOverlayPermission(Context context) {
        if (context != null) {
            return Settings.canDrawOverlays(context);
        }
        return false;
    }

    public static void applyOverlayPermission(Activity activity) {
        activity.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + activity.getApplicationContext().getPackageName())));
    }

    public static void requestGlobalPermissionDialog(ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent("miui.intent.action.PRIVACY_DECLARE");
        intent.putExtra("purpose", NoteApp.getInstance().getResources().getString(R.string.global_cta_v2_app_purpose_v12));
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
