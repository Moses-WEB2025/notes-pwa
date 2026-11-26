package com.miui.pad.feature.notes.filemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
import com.miui.handwriting.common.tools.ExternalAppUtils;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.mishare.tapshare.Util.TapShareConstant;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ShareJumpHelper {
    private static final String TAG = "ShareJumpHelper";

    public static void updateToGallery(Activity activity, List<String> list) {
        Context applicationContext = activity.getApplicationContext();
        String[] strArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strArr[i] = list.get(i);
        }
        MediaScannerConnection.scanFile(applicationContext, strArr, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.miui.pad.feature.notes.filemanager.ShareJumpHelper$$ExternalSyntheticLambda0
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public final void onScanCompleted(String str, Uri uri) {
                Log.i(ShareJumpHelper.TAG, "Scanned " + str + " completely.");
            }
        });
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        for (int i2 = 0; i2 < list.size(); i2++) {
            intent.setData(Uri.fromFile(new File(list.get(i2))));
            applicationContext.sendBroadcast(intent);
        }
        if (ExternalAppUtils.isGalleryExist(activity)) {
            ExternalAppUtils.openGallery(activity);
        } else {
            openDcim(activity);
        }
    }

    public static void openDownload(Activity activity) {
        try {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(Utils.obtainDownloadFileDir(activity.getApplicationContext()).getAbsolutePath())));
            activity.sendBroadcast(intent);
        } catch (Exception e) {
            Log.e(TAG, "refresh download error", e);
        }
        openFileDir(activity, Utils.obtainDownloadFileDir(activity.getApplicationContext()).getAbsolutePath());
    }

    public static void openFileDir(Activity activity, String str) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent();
        if (RomUtils.isInternationalBuild()) {
            intent.setClassName(TapShareConstant.FILE_EXPLORER_GLOBAL_PACKAGE_NAME, "com.android.fileexplorer.FileExplorerTabActivity");
        } else {
            intent.setClassName(TapShareConstant.FILE_EXPLORER_PACKAGE_NAME, "com.android.fileexplorer.MainActivity");
        }
        intent.setFlags(268468224);
        intent.putExtra("current_directory", str);
        activity.startActivity(intent);
    }

    public static void openDcim(Activity activity) {
        openFileDir(activity, Utils.obtainCameraFileDir(activity).getAbsolutePath());
    }
}
