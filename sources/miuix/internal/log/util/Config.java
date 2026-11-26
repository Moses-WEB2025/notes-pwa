package miuix.internal.log.util;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import com.xiaomi.verificationsdk.internal.Constants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Locale;

/* JADX INFO: loaded from: classes5.dex */
public class Config {
    private static final String DEBUG_LOG_DIR = "/debug_log";
    private static final String DUMP_SUB_DIR = "/dump";
    private static File EXTERNAL_STORAGE_MIUI_DIRECTORY = null;
    public static String LOG_NAME = getProcessNameByPid(Process.myPid());
    private static final String MIUI_DIRECTORY_NAME = "MIUI";
    private static final String RELATIVE_LOG_DIR = "/debug_log/";
    private static final String TAG = "Config";

    public static String getDefaultCacheLogDir(Context context) {
        return getApplicationCacheLogDir(context);
    }

    public static String getDefaultExternalLogDir(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            return externalCacheDir.getPath() + DEBUG_LOG_DIR;
        }
        Log.e(TAG, "Fail to getExternalCacheDir");
        return null;
    }

    public static String getDefaultSdcardLogDir(Context context) {
        String packageName = context.getPackageName();
        File externalStorageMiuiDirectory = getExternalStorageMiuiDirectory();
        if (externalStorageMiuiDirectory != null) {
            return externalStorageMiuiDirectory.getPath() + RELATIVE_LOG_DIR + packageName + DUMP_SUB_DIR;
        }
        Log.e(TAG, "Fail to getExternalStorageMiuiDirectory");
        return null;
    }

    private static String getApplicationCacheLogDir(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            return cacheDir.getPath() + RELATIVE_LOG_DIR;
        }
        Log.e(TAG, "Fail to getCacheDir");
        return null;
    }

    private static File getExternalStorageMiuiDirectory() {
        try {
            if (EXTERNAL_STORAGE_MIUI_DIRECTORY == null) {
                EXTERNAL_STORAGE_MIUI_DIRECTORY = new File(Environment.getExternalStorageDirectory(), "MIUI");
            }
            if (!EXTERNAL_STORAGE_MIUI_DIRECTORY.exists() && Environment.getExternalStorageDirectory().exists()) {
                EXTERNAL_STORAGE_MIUI_DIRECTORY.mkdir();
            }
            return EXTERNAL_STORAGE_MIUI_DIRECTORY;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getProcessNameByPid(int i) {
        String str = String.format(Locale.US, "/proc/%d/cmdline", Integer.valueOf(i));
        try {
            String fileAsString = readFileAsString(str);
            if (fileAsString == null) {
                return null;
            }
            int iIndexOf = fileAsString.indexOf(0);
            return iIndexOf >= 0 ? fileAsString.substring(0, iIndexOf) : fileAsString;
        } catch (IOException e) {
            Log.e(TAG, "Fail to read cmdline: " + str, e);
            return null;
        }
    }

    private static String readFileAsString(String str) throws IOException {
        return new String(readFileAsBytes(str), Charset.forName("UTF-8"));
    }

    private static byte[] readFileAsBytes(String str) throws Throwable {
        RandomAccessFile randomAccessFile = null;
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(str, Constants.RANDOM_LONG);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) randomAccessFile2.length());
                byte[] bArr = new byte[8192];
                while (true) {
                    int i = randomAccessFile2.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    randomAccessFile2.close();
                } catch (IOException unused) {
                }
                return byteArray;
            } catch (Throwable th) {
                th = th;
                randomAccessFile = randomAccessFile2;
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
