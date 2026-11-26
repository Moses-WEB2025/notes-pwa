package com.xiaomi.onetrack.util;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.Field;
import com.xiaomi.mirror.synergy.CallMethod;
import com.xiaomi.onetrack.OneTrack;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;
import miuix.provider.ExtraSettings;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes4.dex */
public class s {
    public static final int a = 33;
    public static final int b = 29;
    public static final int c = 25;
    public static final int d = 24;
    public static final int e = 23;
    public static final int f = 22;
    public static final int g = 21;
    public static final int h = 19;
    public static final int i = 17;
    public static final int j = 28;
    public static final int k = 2;
    public static final int l = 4;
    private static final String m = "OsUtil";
    private static Class n = null;
    private static Method o = null;
    private static Boolean p = null;
    private static final String q = "";
    private static Method r = null;
    private static boolean s = false;
    private static String t = null;
    private static boolean u = false;
    private static int v = 0;
    private static boolean w = false;

    static {
        try {
            r = Class.forName("android.os.SystemProperties").getMethod(CallMethod.METHOD_GET, String.class);
        } catch (Throwable th) {
            r.b(m, "sGetProp init failed ex: " + th.getMessage());
        }
    }

    public static void a() {
        if (w) {
            return;
        }
        w = true;
        try {
            n = Class.forName("miui.os.Build");
        } catch (Throwable th) {
            r.b(m, "sMiuiBuild init failed ex: " + th.getMessage());
        }
        try {
            Method declaredMethod = Class.forName("android.provider.MiuiSettings$Secure").getDeclaredMethod("isUserExperienceProgramEnable", ContentResolver.class);
            o = declaredMethod;
            declaredMethod.setAccessible(true);
        } catch (Throwable th2) {
            r.b(m, "sMiuiUEPMethod init failed ex: " + th2.getMessage());
        }
    }

    private static String c(String str) {
        try {
            Method method = r;
            if (method != null) {
                return String.valueOf(method.invoke(null, str));
            }
        } catch (Throwable th) {
            r.b(m, "getProp failed ex: " + th.getMessage());
        }
        return null;
    }

    public static boolean b() {
        Boolean bool = p;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (!TextUtils.isEmpty(c("ro.miui.ui.version.code"))) {
            p = true;
        } else {
            p = false;
        }
        return p.booleanValue();
    }

    public static String c() {
        return a(TimeZone.getDefault().getRawOffset());
    }

    public static String a(int i2) {
        char c2;
        try {
            int i3 = i2 / 60000;
            if (i3 < 0) {
                i3 = -i3;
                c2 = CoreConstants.DASH_CHAR;
            } else {
                c2 = '+';
            }
            StringBuilder sb = new StringBuilder(9);
            sb.append(TimeZones.GMT_ID);
            sb.append(c2);
            a(sb, i3 / 60);
            sb.append(CoreConstants.COLON_CHAR);
            a(sb, i3 % 60);
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private static void a(StringBuilder sb, int i2) {
        String string = Integer.toString(i2);
        for (int i3 = 0; i3 < 2 - string.length(); i3++) {
            sb.append('0');
        }
        sb.append(string);
    }

    public static String d() {
        Class cls = n;
        if (cls != null) {
            try {
                if (((Boolean) cls.getField("IS_ALPHA_BUILD").get(null)).booleanValue()) {
                    return ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
                }
                if (((Boolean) n.getField("IS_STABLE_VERSION").get(null)).booleanValue()) {
                    return "S";
                }
                boolean zContains = Build.VERSION.INCREMENTAL.contains(".DEV");
                boolean zBooleanValue = ((Boolean) n.getField("IS_DEVELOPMENT_VERSION").get(null)).booleanValue();
                if (zBooleanValue && !zContains) {
                    return Field.DOUBLE_SIGNATURE_PRIMITIVE;
                }
                if (zBooleanValue && zContains) {
                    return "X";
                }
                return "";
            } catch (Throwable th) {
                Log.e(m, "getRomBuildCode failed: " + th.getMessage());
                return "";
            }
        }
        return "";
    }

    public static boolean a(Context context) {
        if (o == null) {
            try {
                if (b()) {
                    int i2 = Settings.Secure.getInt(context.getContentResolver(), ExtraSettings.Secure.UPLOAD_LOG, -1);
                    r.a(m, "isUserExperiencePlanEnabled upload_log_value: " + i2);
                    if (i2 != 1 && i2 == 0) {
                        return false;
                    }
                }
                return true;
            } catch (Throwable th) {
                r.a(m, "Settings failed: " + th.toString());
            }
        }
        try {
            return ((Boolean) o.invoke(null, context.getContentResolver())).booleanValue();
        } catch (Throwable th2) {
            Log.d(m, "isUserExperiencePlanEnabled failed: " + th2.getMessage());
            return true;
        }
    }

    public static boolean a(String str) {
        if (OneTrack.isDisable() || OneTrack.isUseSystemNetTrafficOnly()) {
            r.c(str, "should not access network or location, cta");
            return true;
        }
        if (!i()) {
            r.c(str, "should not access network or location, not provisioned");
            return true;
        }
        if (com.xiaomi.onetrack.c.j.b()) {
            return false;
        }
        r.c(str, "should not access network or location, cta");
        return true;
    }

    public static String e() {
        return Build.VERSION.INCREMENTAL;
    }

    public static String f() {
        return ad.a("ro.mi.os.version.incremental", "");
    }

    private static String n() {
        try {
            String strA = ad.a("ro.miui.region", "");
            if (TextUtils.isEmpty(strA)) {
                strA = ad.a("ro.product.locale.region", "");
            }
            if (TextUtils.isEmpty(strA)) {
                Object objInvoke = Class.forName("android.os.LocaleList").getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
                Object objInvoke2 = objInvoke.getClass().getMethod("size", new Class[0]).invoke(objInvoke, new Object[0]);
                if ((objInvoke2 instanceof Integer) && ((Integer) objInvoke2).intValue() > 0) {
                    Object objInvoke3 = objInvoke.getClass().getMethod(CallMethod.METHOD_GET, Integer.TYPE).invoke(objInvoke, 0);
                    Object objInvoke4 = objInvoke3.getClass().getMethod("getCountry", new Class[0]).invoke(objInvoke3, new Object[0]);
                    if (objInvoke4 instanceof String) {
                        strA = (String) objInvoke4;
                    }
                }
            }
            if (TextUtils.isEmpty(strA)) {
                strA = Locale.getDefault().getCountry();
            }
            if (!TextUtils.isEmpty(strA)) {
                return strA.trim();
            }
        } catch (Throwable th) {
            r.b(m, "getRegion Exception: " + th.getMessage());
        }
        return "";
    }

    public static String g() {
        return Build.VERSION.RELEASE;
    }

    public static int h() {
        Integer num;
        Throwable th;
        try {
            Method declaredMethod = Class.forName("android.os.UserHandle").getDeclaredMethod("getUserId", Integer.TYPE);
            declaredMethod.setAccessible(true);
            int iMyUid = Process.myUid();
            num = (Integer) declaredMethod.invoke(null, Integer.valueOf(iMyUid));
            try {
                r.a(m, String.format("getUserId, uid:%d, userId:%d", Integer.valueOf(iMyUid), num));
            } catch (Throwable th2) {
                th = th2;
                Log.e(r.a(m), "getUserId exception: " + th.getMessage());
            }
        } catch (Throwable th3) {
            num = null;
            th = th3;
        }
        if (num == null) {
            num = 0;
        }
        return num.intValue();
    }

    public static boolean i() {
        try {
            boolean z = Settings.Global.getInt(com.xiaomi.onetrack.f.a.b().getContentResolver(), "device_provisioned", 0) != 0;
            if (!z) {
                r.c(m, "Provisioned: " + z);
            }
            return z;
        } catch (Throwable th) {
            r.b(m, "isDeviceProvisioned exception：" + th.getMessage());
            return true;
        }
    }

    public static boolean j() {
        Class cls = n;
        if (cls != null) {
            try {
                return ((Boolean) cls.getField("IS_INTERNATIONAL_BUILD").get(null)).booleanValue();
            } catch (Throwable unused) {
            }
        }
        String strN = n();
        if (TextUtils.isEmpty(strN)) {
            return false;
        }
        return !TextUtils.equals("CN", strN.toUpperCase());
    }

    public static boolean k() {
        if (b() && !u) {
            return j();
        }
        return s;
    }

    public static String l() {
        if (!b() || u) {
            return !TextUtils.isEmpty(t) ? t : n();
        }
        return n();
    }

    public static void a(boolean z, String str, OneTrack.Mode mode) {
        int i2;
        if (mode == OneTrack.Mode.APP) {
            i2 = 3;
        } else if (mode == OneTrack.Mode.PLUGIN) {
            i2 = 2;
        } else {
            i2 = mode == OneTrack.Mode.SDK ? 1 : 0;
        }
        if (v <= i2) {
            s = z;
            t = str;
            v = i2;
        }
    }

    public static void a(boolean z) {
        u = z;
    }

    public static String m() {
        return n();
    }

    public static boolean b(String str) {
        if (!ab.b(str)) {
            str = l();
        }
        return a.k.contains(str.toUpperCase());
    }
}
