package com.xiaomi.passport.ui.onetrack;

import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class SystemInfoUtils {
    public static String getMIUISystemVersion() {
        return getSystemProperty("ro.miui.ui.version.name", "others");
    }

    public static String getMiOSVersion() {
        return getSystemProperty("ro.mi.os.version.name", "others");
    }

    private static String getSystemProperty(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(CallMethod.METHOD_GET, String.class, String.class).invoke(null, str, str2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return str2;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return str2;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return str2;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return str2;
        }
    }
}
