package com.miui.libhandwritingpage.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import ch.qos.logback.classic.spi.CallerData;
import java.io.File;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Marker;
import xc.engine.api.CreationPageSDK;
import xc.engine.utils.FileUtil;

/* JADX INFO: loaded from: classes3.dex */
public class Utils {
    private static final String DIR_NAME = "Creation";
    public static final int IME_MASK = 8;
    private static final String TAG = "Utils";
    private static int lastButtonId;
    public static long lastClickTime;

    public static Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        if (context instanceof ContextThemeWrapper) {
            return getActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        Log.e(TAG, "Didn't find Activity!");
        return null;
    }

    public static Path getRectPath(float[] fArr) {
        Path path = new Path();
        path.moveTo(fArr[0], fArr[1]);
        path.lineTo(fArr[2], fArr[3]);
        path.lineTo(fArr[4], fArr[5]);
        path.lineTo(fArr[6], fArr[7]);
        path.close();
        return path;
    }

    public static boolean isFastClick(int i, long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = lastClickTime;
        long j3 = jCurrentTimeMillis - j2;
        if (i == -1 && j2 > 0 && Math.abs(j3) < j) {
            return true;
        }
        if (lastButtonId == i && lastClickTime > 0 && Math.abs(j3) < j) {
            return true;
        }
        lastClickTime = jCurrentTimeMillis;
        lastButtonId = i;
        return false;
    }

    public static boolean isFastClick(int i) {
        return isFastClick(i, 300L);
    }

    public static boolean isFastClick() {
        return isFastClick(-1, 300L);
    }

    public static String getTempDir() {
        File filesDir = CreationPageSDK.sApplication.getFilesDir();
        if (filesDir == null) {
            return null;
        }
        String str = filesDir.getAbsolutePath() + "/temp";
        if (FileUtil.createOrExistsDir(str)) {
            return str;
        }
        return null;
    }

    public static void hideSoftInput(View view) {
        InputMethodManager inputMethodManager;
        if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getCopyPath(Context context, Uri uri) {
        if (uri == null) {
            return "";
        }
        String str = context.getCacheDir() + "/import_tmp_copy";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
        }
        String path = uri.getPath();
        String str2 = str + path.substring(path.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR)).replace(InternalZipConstants.ZIP_FILE_SEPARATOR, InternalZipConstants.ZIP_FILE_SEPARATOR + System.currentTimeMillis() + "_");
        try {
            FileUtil.copyUriToFile(context, uri, new File(str2));
        } catch (Exception e) {
            Log.e(TAG, "copyUriToFile error", e);
        }
        Log.e(TAG, "targetPath:" + str2);
        return str2;
    }

    public static String escapeExprSpecialWord(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArr = {"\\", "$", "(", ")", "*", Marker.ANY_NON_NULL_MARKER, ".", "[", "]", CallerData.NA, "^", "{", StringSubstitutor.DEFAULT_VAR_END, "|"};
            for (int i = 0; i < 14; i++) {
                String str2 = strArr[i];
                if (str.contains(str2)) {
                    str = str.replace(str2, "\\" + str2);
                }
            }
        }
        return str;
    }

    public static void showSoftInput(View view) {
        InputMethodManager inputMethodManager;
        if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.showSoftInput(view, 0);
    }

    public static File obtainDownloadFileDir(Context context) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (externalStoragePublicDirectory == null) {
            Log.e(TAG, "Fail to getExternalCacheDir.");
            return null;
        }
        File file = new File(externalStoragePublicDirectory, DIR_NAME);
        if (file.exists() || file.mkdirs() || file.exists()) {
            return file;
        }
        Log.e(TAG, "Fail to create CameraDir:" + file);
        return null;
    }

    public static File obtainCameraFileDir(Context context) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (externalStoragePublicDirectory == null) {
            Log.e(TAG, "Fail to getExternalCacheDir.");
            return null;
        }
        File file = new File(externalStoragePublicDirectory, DIR_NAME);
        if (file.exists() || file.mkdirs() || file.exists()) {
            return file;
        }
        Log.e(TAG, "Fail to create CameraDir:" + file);
        return null;
    }
}
