package miui.cloud.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes5.dex */
public class PkgInfoUtil {
    private static final String TAG = "PkgInfoUtil";

    private PkgInfoUtil() {
    }

    public static boolean isPkgExist(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isPkgEnable(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "isPkgEnable ", e);
            return false;
        }
    }

    public static long getPackageFileSize(PackageInfo packageInfo) {
        File file = new File(packageInfo.applicationInfo.sourceDir);
        long length = file.isFile() ? file.length() : 0L;
        String[] strArr = packageInfo.applicationInfo.splitSourceDirs;
        if (strArr != null) {
            for (String str : strArr) {
                File file2 = new File(str);
                if (file2.isFile()) {
                    length += file2.length();
                }
            }
        }
        return length;
    }
}
