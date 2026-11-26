package micloud.compat.independent.sync;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
public class GdprUtilsCompat {
    private static IGdprUtilsCompat sGdprUtilsCompatImpl = new GdprUtilsCompat_V24();

    private GdprUtilsCompat() {
    }

    public static void setGdprUtilsCompatImpl(IGdprUtilsCompat iGdprUtilsCompat) {
        sGdprUtilsCompatImpl = iGdprUtilsCompat;
    }

    public static boolean isGdprPermissionGranted(Context context) {
        return sGdprUtilsCompatImpl.isGdprPermissionGranted(context);
    }

    public static void notifyPrivacyDenied(Context context) {
        sGdprUtilsCompatImpl.notifyPrivacyDenied(context);
    }
}
