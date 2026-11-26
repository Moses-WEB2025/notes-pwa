package com.xiaomi.passport.ui.settings.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import com.xiaomi.mirror.RemoteDeviceInfo;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class PermissionUtils {
    private static final String MANUFACTURER = Build.MANUFACTURER.toLowerCase();

    /* JADX WARN: Code duplicated, block: B:25:0x007c  */
    public static void openPermissionSetting(Activity activity, int i) {
        Intent intent;
        String str = MANUFACTURER;
        if (str.contains(RemoteDeviceInfo.MANUFACTURER_XIAOMI)) {
            String modDevice = getModDevice();
            if (modDevice == null || modDevice.contains("_global")) {
                intent = null;
            } else {
                intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.putExtra("extra_pkgname", activity.getPackageName());
            }
        } else if (str.contains("huawei") || str.contains("oppo")) {
            intent = null;
        } else if (str.contains("vivo")) {
            intent = new Intent();
            intent.putExtra("packagename", activity.getPackageName());
            intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
        } else {
            if (!str.contains("samsung") && !str.contains("meizu")) {
                str.contains("smartisan");
            }
            intent = null;
        }
        if (intent != null) {
            try {
                activity.startActivityForResult(intent, i);
            } catch (ActivityNotFoundException | SecurityException unused) {
                intent = null;
            }
        }
        if (intent == null) {
            Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(Uri.fromParts("package", activity.getPackageName(), null));
            activity.startActivityForResult(intent2, i);
        }
    }

    private static String getModDevice() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod(CallMethod.METHOD_GET, String.class).invoke(cls, "ro.product.mod_device");
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
