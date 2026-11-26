package miui.cloud.util;

import android.util.Log;
import java.util.Arrays;

/* JADX INFO: loaded from: classes5.dex */
public class DeviceFeatureUtils {
    public static final String FEATURE_EXEMPT_MASTER_SYNC_AUTO = "exempt_master_sync_auto";
    public static final String FEATURE_SUPPORT_FILE_CHANGE_CHECK = "support_file_change_check";
    public static final String FEATURE_SUPPORT_GOOGLE_CSP_SYNC = "support_google_csp_sync";
    private static final String TAG = "DeviceFeatureUtils";

    public static boolean hasDeviceFeature(String str) {
        String[] allDeviceFeaturesOrNull = getAllDeviceFeaturesOrNull();
        if (allDeviceFeaturesOrNull == null) {
            return false;
        }
        return Arrays.asList(allDeviceFeaturesOrNull).contains(str);
    }

    public static String[] getAllDeviceFeaturesOrNull() {
        try {
            Class<?> cls = Class.forName("miui.cloud.DeviceFeature");
            return (String[]) cls.getField("features").get(cls);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "failed to find features from miclousdk, " + e);
            return null;
        } catch (IllegalAccessException e2) {
            Log.e(TAG, "failed to find features from miclousdk, " + e2);
            return null;
        } catch (NoSuchFieldException e3) {
            Log.e(TAG, "failed to find features from miclousdk, " + e3);
            return null;
        }
    }
}
