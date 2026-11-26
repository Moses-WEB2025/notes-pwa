package com.miui.handwriting.common.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.os.updater.DigestUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import ch.qos.logback.classic.spi.CallerData;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.xiaomi.miai.SDKConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Marker;
import xc.engine.utils.FileUtil;

/* JADX INFO: loaded from: classes3.dex */
public class Utils {
    private static final String DIR_NAME = "Creation";
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

    public static void showSoftInput(View view) {
        InputMethodManager inputMethodManager;
        if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void hideSoftInput(View view) {
        InputMethodManager inputMethodManager;
        if (view == null || (inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    public static String formattedKeyWords(String str) {
        return str.replaceAll("'", "''");
    }

    public static boolean isFastClick(int i) {
        return isFastClick(i, 300L);
    }

    public static boolean isFastClick() {
        return isFastClick(-1, 300L);
    }

    public static String getProcessName(Context context) {
        String str = null;
        if (context != null) {
            int iMyPid = Process.myPid();
            try {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                    if (runningAppProcessInfo.pid == iMyPid) {
                        str = runningAppProcessInfo.processName;
                    }
                }
                return str;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getCurrentForegroundActivity(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager == null) {
            return null;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(1).get(0);
        if (runningTaskInfo.topActivity != null) {
            return runningTaskInfo.topActivity.getClassName();
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        Network activeNetwork;
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetwork = connectivityManager.getActiveNetwork()) == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null || !networkCapabilities.hasCapability(16)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static String getFileSha1Digest(String str) throws Throwable {
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            try {
                try {
                    String hexReadable = DigestUtils.toHexReadable(DigestUtils.get(bufferedInputStream, SDKConstants.DigestAlgorithm.SHA_256));
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused) {
                    }
                    return hexReadable;
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream2 = bufferedInputStream;
                    if (bufferedInputStream2 != null) {
                        try {
                            bufferedInputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e) {
                e = e;
                Log.e(TAG, "Fail to getFileSha256Digest", e);
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                return null;
            }
        } catch (Exception e2) {
            e = e2;
            bufferedInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedInputStream2 != null) {
                bufferedInputStream2.close();
            }
            throw th;
        }
    }

    public static String getTempDir() {
        File filesDir = HandWritingViewApplication.INSTANCE.getApplication().getFilesDir();
        if (filesDir == null) {
            return null;
        }
        String str = filesDir.getAbsolutePath() + "/temp";
        if (createOrExistsDir(getFileByPath(str))) {
            return str;
        }
        return null;
    }

    public static File getFileByPath(String str) {
        if ("".equals(str) || str == null) {
            return null;
        }
        return new File(str);
    }

    public static boolean createOrExistsDir(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdirs();
    }

    public static byte[] getImageByteArray(String str) {
        File file = new File(str);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[(int) file.length()];
            Log.d("getImageByteArray", "success:" + fileInputStream.read(bArr));
            fileInputStream.close();
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File saveFile(byte[] bArr) {
        String tempDir = getTempDir();
        if (tempDir == null) {
            return null;
        }
        File file = new File(tempDir, String.valueOf(System.currentTimeMillis()));
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr, 0, bArr.length);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return file;
        } catch (IOException e2) {
            e2.printStackTrace();
            return file;
        }
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

    public static String getPathFromUri(Context context, Uri uri) {
        Cursor cursorQuery;
        String path = uri != null ? uri.getPath() : null;
        if (path != null || (cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null)) == null || !cursorQuery.moveToFirst()) {
            return path;
        }
        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
        cursorQuery.close();
        return string;
    }

    public static byte[] getImageByteUriPath(Context context, String str) {
        File file = new File(getPathFromUri(context, Uri.parse(str)));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[(int) file.length()];
            Log.d("getImageByteArray", "success:" + fileInputStream.read(bArr));
            fileInputStream.close();
            return bArr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    public static boolean isAllSpaces(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i)) && str.charAt(i) != 160) {
                return false;
            }
        }
        return true;
    }
}
