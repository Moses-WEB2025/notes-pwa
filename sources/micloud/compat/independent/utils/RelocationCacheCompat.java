package micloud.compat.independent.utils;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.micloudsdk.request.utils.RomCountryUtil;
import com.xiaomi.micloudsdk.utils.MiCloudSDKDependencyUtil;

/* JADX INFO: loaded from: classes5.dex */
public class RelocationCacheCompat {
    private static IRelocationCacheCompat sRelocationCacheCompatImpl;

    private RelocationCacheCompat() {
    }

    static {
        if (MiCloudSDKDependencyUtil.SDKEnvironment >= 18) {
            if (!TextUtils.isEmpty(RomCountryUtil.getRomCountry())) {
                sRelocationCacheCompatImpl = new RelocationCacheCompat_V18();
                return;
            } else {
                sRelocationCacheCompatImpl = new RelocationCacheCompat_Base();
                return;
            }
        }
        sRelocationCacheCompatImpl = new RelocationCacheCompat_Base();
    }

    public static void setsRelocationCacheCompatImpl(IRelocationCacheCompat iRelocationCacheCompat) {
        sRelocationCacheCompatImpl = iRelocationCacheCompat;
    }

    public static String getCachedXiaomiAccountName(Context context) {
        return sRelocationCacheCompatImpl.getCachedXiaomiAccountName(context);
    }

    public static void cacheXiaomiAccountName(Context context, String str) {
        sRelocationCacheCompatImpl.cacheXiaomiAccountName(context, str);
    }

    public static String getCachedHostList(Context context) {
        return sRelocationCacheCompatImpl.getCachedHostList(context);
    }

    public static void cacheHostList(Context context, String str) {
        sRelocationCacheCompatImpl.cacheHostList(context, str);
    }
}
