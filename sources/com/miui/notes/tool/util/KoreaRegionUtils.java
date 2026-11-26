package com.miui.notes.tool.util;

import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import com.miui.common.tool.Utils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.LogUtils;
import com.miui.notes.tool.PreferenceUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class KoreaRegionUtils {
    private static final String FLAG_NECESSARY = "1";
    private static final String KOREA_AUTHORIZE_ACTION = "miui.intent.action.APP_PERMISSION_USE";
    private static final int KOREA_AUTH_REQUEST_CODE = 2307;
    public static final int KOREA_AUTH_RESULT_CODE = 2308;
    private static final String NULL = "null";
    private static final String PERMISSION_CONTACT = "android.permission-group.CONTACT";
    private static final String PERMISSION_MICROPHONE = "android.permission-group.MICROPHONE";
    private static final String PERMISSION_PHONE = "android.permission-group.PHONE";
    private static final String PERMISSION_STORAGE = "android.permission-group.STORAGE";
    public static final String REGION_KOREA = "KR";
    private static final String SEPARATOR = "@";
    private static final String TAG = "KoreaRegionUtils";

    public static boolean showPermissionsForKorea(Activity activity) {
        if (!Utils.isInThisCountry(REGION_KOREA) || PreferenceUtils.getKoreaPermissionInfoShowed(NoteApp.getInstance())) {
            return true;
        }
        Intent intent = new Intent(KOREA_AUTHORIZE_ACTION);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("android.permission-group.STORAGE@1@null@" + activity.getString(R.string.permission_intro_storage));
        arrayList.add("android.permission-group.MICROPHONE@1@null@" + activity.getString(R.string.permission_intro_microphone));
        arrayList.add("android.permission-group.PHONE@1@null@" + activity.getString(R.string.permission_intro_phone));
        arrayList.add("android.permission-group.CONTACT@1@null@" + activity.getString(R.string.permission_intro_contact));
        intent.putStringArrayListExtra("extra_main_permission_groups", arrayList);
        intent.putExtra("extra_pkgname", "com.miui.notes");
        intent.addFlags(67108864);
        try {
            activity.startActivityForResult(intent, 2307);
            return false;
        } catch (Exception e) {
            LogUtils.d(TAG, "korea authorize error：" + e.toString());
            return true;
        }
    }

    public static void showPermissionsForKorea(ActivityResultLauncher<Intent> activityResultLauncher) {
        if (Utils.isInThisCountry(REGION_KOREA) && !PreferenceUtils.getKoreaPermissionInfoShowed(NoteApp.getInstance())) {
            Intent intent = new Intent(KOREA_AUTHORIZE_ACTION);
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("android.permission-group.STORAGE@1@null@" + NoteApp.getInstance().getString(R.string.permission_intro_storage));
            arrayList.add("android.permission-group.MICROPHONE@1@null@" + NoteApp.getInstance().getString(R.string.permission_intro_microphone));
            arrayList.add("android.permission-group.PHONE@1@null@" + NoteApp.getInstance().getString(R.string.permission_intro_phone));
            arrayList.add("android.permission-group.CONTACT@1@null@" + NoteApp.getInstance().getString(R.string.permission_intro_contact));
            intent.putStringArrayListExtra("extra_main_permission_groups", arrayList);
            intent.putExtra("extra_pkgname", "com.miui.notes");
            intent.addFlags(67108864);
            try {
                activityResultLauncher.launch(intent);
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "korea authorize error：" + e.toString());
            }
        }
    }

    public static boolean processCallBack(int i, int i2) {
        if (i != 2307 || i2 != 2308) {
            return false;
        }
        PreferenceUtils.setKoreaPermissionInfoShowed(NoteApp.getInstance(), true);
        return true;
    }
}
