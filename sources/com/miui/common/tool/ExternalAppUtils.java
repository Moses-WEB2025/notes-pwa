package com.miui.common.tool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.hardware.DataSpace;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.util.CompatFileProvider;
import com.miui.notes.ui.view.SearchCallback;
import com.miui.pad.feature.notes.share.ShareAppsAdapter;
import java.io.File;
import java.util.ArrayList;
import miui.cloud.stat.MiCloudStatUtil;

/* JADX INFO: loaded from: classes2.dex */
public class ExternalAppUtils {
    private static final String ACTION_GALLERY_ALBUM = "com.miui.gallery.action.VIEW_ALBUM";
    private static final String CAMERA_PKG_NAME = "com.android.camera";
    private static final String GALLERY_PKG_NAME = "com.miui.gallery";
    public static final int KEY_PICK_LIMITED_NUMBER = 10;
    public static final String KEY_PICK_RESULT_DATA = "pick-result-data";
    public static final String KEY_PICK_UPPER_BOUND = "pick-upper-bound";
    private static final String MEDIAVIEWER_PKG_NAME = "com.miui.mediaviewer";
    public static final String MIME_TYPE_IMAGE_JPEG = "image/jpeg";
    public static final String MIME_TYPE_TEXT = "text/plain";
    private static final String TAG = "ExternalAppUtils";
    public static final int WEIBO_LENGTH_LIMIT = 140;

    public static float calculateWeiboLength(char c) {
        return (c < 0 || c > 127) ? 1.0f : 0.5f;
    }

    public static void selectImage(Fragment fragment, ActivityResultLauncher<Intent> activityResultLauncher, ActivityResultLauncher<PickVisualMediaRequest> activityResultLauncher2) {
        if (useSystemPhotoPicker(fragment.getActivity())) {
            activityResultLauncher2.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
        } else {
            selectImage(fragment, 10, activityResultLauncher, null);
        }
    }

    public static boolean useSystemPhotoPicker(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.miui.gallery", 128);
            return Build.VERSION.SDK_INT >= 36 && applicationInfo.metaData != null && applicationInfo.metaData.containsKey("os_gallery_cmp_version") && applicationInfo.metaData.getInt("os_gallery_cmp_version", 0) > 0;
        } catch (Exception unused) {
            return true;
        }
    }

    public static void selectImage(Fragment fragment, int i, ActivityResultLauncher<Intent> activityResultLauncher, Integer num) {
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

    public static void takePhoto(Fragment fragment, ActivityResultLauncher<Intent> activityResultLauncher) {
        File scrapFile;
        Uri compatUri;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.setFlags(524290);
        FragmentActivity activity = fragment.getActivity();
        if (activity == null || (scrapFile = NotesProvider.getScrapFile(activity)) == null || (compatUri = CompatUtils.getCompatUri(fragment.getActivity(), scrapFile)) == null) {
            return;
        }
        intent.putExtra("output", compatUri);
        intent.setClipData(ClipData.newUri(fragment.getActivity().getContentResolver(), "Camera", compatUri));
        intent.setPackage(CAMERA_PKG_NAME);
        if (!checkIntent(fragment.getActivity(), intent)) {
            intent.setPackage(null);
        }
        try {
            activityResultLauncher.launch(intent);
        } catch (ActivityNotFoundException unused) {
            Logger.INSTANCE.e(TAG, "can't start capture activity");
        }
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
                Logger.INSTANCE.e(TAG, e.getMessage());
                return;
            }
        }
        context.startActivity(intent);
    }

    public static void viewVideo(Context context, String str) {
        Uri uriForFile = CompatFileProvider.getUriForFile(context, "com.miui.notes", new File(str));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uriForFile, "video/*");
        intent.addFlags(1);
        intent.setPackage("com.miui.video");
        if (!checkIntent(context, intent)) {
            intent.setPackage(null);
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
                Logger.INSTANCE.e(TAG, e.getMessage());
                return;
            }
        }
        Logger.INSTANCE.e(TAG, "Fail to lunch system updater.");
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

    public static void viewCloud(Context context) {
        MiCloudStatUtil.startMiCloudMainActivity(context);
    }
}
