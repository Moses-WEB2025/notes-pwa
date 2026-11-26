package com.miui.common.tool;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.miui.notes.R;
import java.util.ArrayList;
import java.util.Arrays;
import miuix.core.util.SystemProperties;
import miuix.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class AIRecorderDeviceUtil {
    private static final boolean IS_GLOBAL = Build.IS_INTERNATIONAL_BUILD;
    private static final String TAG = "AIRecorderDeviceUtil";

    public static boolean isAIRecorderSupportedDevice(Context context) {
        String[] stringArray;
        String str = android.os.Build.DEVICE;
        Resources resources = context.getResources();
        if (!new ArrayList(Arrays.asList(resources.getStringArray(R.array.support_ai_devices_special))).contains(str)) {
            if (IS_GLOBAL) {
                stringArray = resources.getStringArray(R.array.support_ai_devices_global);
            } else {
                stringArray = resources.getStringArray(R.array.support_ai_devices_cn);
            }
            return new ArrayList(Arrays.asList(stringArray)).contains(str);
        }
        return isIsSupportInSpecialList(str);
    }

    private static boolean isIsSupportInSpecialList(String str) {
        boolean z = false;
        if (TextUtils.equals(str, "flourite") && "Redmi".equalsIgnoreCase(SystemProperties.get("ro.product.brand")) && IS_GLOBAL) {
            z = true;
        }
        Logger.INSTANCE.d(TAG, "isIsSupportInSpecialList devicesName = " + str + ", isSupportAi = " + z);
        return z;
    }
}
