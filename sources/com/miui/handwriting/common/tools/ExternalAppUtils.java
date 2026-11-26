package com.miui.handwriting.common.tools;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.hardware.DataSpace;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class ExternalAppUtils {
    private static final String ACTION_GALLERY_ALBUM = "com.miui.gallery.action.VIEW_ALBUM";
    private static final String CAMERA_PKG_NAME = "com.android.camera";
    private static final String GALLERY_PKG_NAME = "com.miui.gallery";
    public static final int KEY_PICK_LIMITED_NUMBER = 10;
    public static final String KEY_PICK_RESULT_DATA = "pick-result-data";
    public static final String KEY_PICK_UPPER_BOUND = "pick-upper-bound";
    private static final String MEDIAVIEWER_PKG_NAME = "com.miui.mediaviewer";
    public static final String SEND_TYPE_IMAGE_JPEG = "image/jpeg";
    public static final String SEND_TYPE_TEXT = "text/plain";
    private static final String TAG = "ExternalAppUtils";
    public static final int WEIBO_LENGTH_LIMIT = 140;

    public static float calculateWeiboLength(char c) {
        return (c < 0 || c > 127) ? 1.0f : 0.5f;
    }

    public static void selectImage(Fragment fragment, int i) {
        selectImage(fragment, 10, i, null);
    }

    public static void selectImage(Fragment fragment, int i, int i2, Integer num) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.putExtra("pick-upper-bound", i);
        intent.setType(ShareAppsAdapter.TYPE_IMAGE);
        intent.setPackage("com.miui.gallery");
        if (fragment.getActivity() == null) {
            return;
        }
        if (!checkIntent(fragment.getActivity(), intent)) {
            intent.setPackage(null);
        }
        if (num != null) {
            fragment.startActivityForResult(intent, i2, ActivityOptions.makeBasic().setLaunchDisplayId(0).toBundle());
        } else {
            fragment.startActivityForResult(intent, i2);
        }
    }

    public static void selectImage(Context context, ActivityResultLauncher activityResultLauncher, int i) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.putExtra("pick-upper-bound", i);
        intent.setType(ShareAppsAdapter.TYPE_IMAGE);
        intent.setPackage("com.miui.gallery");
        if (!checkIntent(context, intent)) {
            intent.setPackage(null);
        }
        activityResultLauncher.launch(intent);
    }

    public static void openGallery(Activity activity) {
        Intent intent = new Intent(ACTION_GALLERY_ALBUM);
        intent.setFlags(DataSpace.RANGE_LIMITED);
        intent.setPackage("com.miui.gallery");
        if (!checkIntent(activity, intent)) {
            intent.setPackage(null);
            intent.setAction(null);
            intent.addCategory("android.intent.category.OPENABLE");
            return;
        }
        activity.startActivity(intent);
    }

    public static boolean isGalleryExist(Context context) {
        Intent intent = new Intent(ACTION_GALLERY_ALBUM);
        intent.setFlags(DataSpace.RANGE_LIMITED);
        intent.setPackage("com.miui.gallery");
        return checkIntent(context, intent);
    }

    public static boolean checkIntent(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 0).size() > 0;
    }

    public static Intent getShareIntentAsImage(Uri uri, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(str);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("android.intent.extra.STREAM", uri);
        return intent;
    }

    public static void viewImage(Context context, Uri uri, String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(1);
        intent.setDataAndType(uri, str);
        intent.setPackage("com.miui.gallery");
        if (!checkIntent(context, intent)) {
            intent.setPackage(MEDIAVIEWER_PKG_NAME);
            intent.setDataAndType(uri, ShareAppsAdapter.TYPE_IMAGE);
            ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
            arrayList.add(uri);
            intent.putParcelableArrayListExtra("com.miui.gallery.extra.photo_items", arrayList);
            if (!checkIntent(context, intent)) {
                intent.setPackage(null);
            }
            try {
                context.startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, e.getMessage());
                return;
            }
        }
        context.startActivity(intent);
    }

    public static void launchSystemUpdater(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName("com.android.updater", "com.android.updater.MainActivity"));
        intent.setFlags(335544320);
        if (checkIntent(context, intent)) {
            try {
                context.startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, e.getMessage());
                return;
            }
        }
        Log.e(TAG, "Fail to lunch system updater.");
    }

    public static float calculateWeiboLength(CharSequence charSequence, int i, int i2) {
        float fCalculateWeiboLength = 0.0f;
        while (i < i2) {
            fCalculateWeiboLength += calculateWeiboLength(charSequence.charAt(i));
            i++;
        }
        return fCalculateWeiboLength;
    }

    public static Intent createSystemPhoneIntent(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.setPackage(SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY);
        if (!checkIntent(context, intent)) {
            intent.setComponent(null);
        }
        return intent;
    }

    public static boolean isLaunchedFromHome(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getAction() != null && intent.getCategories() != null && intent.getAction().equals("android.intent.action.MAIN") && intent.getCategories().contains("android.intent.category.LAUNCHER");
    }
}
