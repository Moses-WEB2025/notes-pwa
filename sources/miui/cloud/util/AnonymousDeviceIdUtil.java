package miui.cloud.util;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import miui.cloud.os.SystemProperties;
import miui.os.Build;

/* JADX INFO: loaded from: classes5.dex */
public class AnonymousDeviceIdUtil {
    private static final String TAG = "AnonymousDeviceIdUtil";
    private static Method sGetAAID;
    private static Method sGetOAID;
    private static Method sGetUDID;
    private static Method sGetVAID;
    private static Object sIdProivderImpl;
    private static Set<String> sOAIDDeviceSet;

    static {
        HashSet hashSet = new HashSet();
        sOAIDDeviceSet = hashSet;
        hashSet.add("cmi");
        sOAIDDeviceSet.add("umi");
        sOAIDDeviceSet.add("lmi");
        sOAIDDeviceSet.add("picasso");
        sOAIDDeviceSet.add("phoenix");
        sOAIDDeviceSet.add("phoenixin");
        sOAIDDeviceSet.add("vangogh");
        sOAIDDeviceSet.add("monet");
        sOAIDDeviceSet.add("toco");
        sOAIDDeviceSet.add("merlin");
        sOAIDDeviceSet.add("curtana");
        sOAIDDeviceSet.add("durandal");
        sOAIDDeviceSet.add("excalibur");
        sOAIDDeviceSet.add("joyeuse");
        sOAIDDeviceSet.add("gram");
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            sIdProivderImpl = cls.newInstance();
            try {
                sGetUDID = cls.getMethod("getUDID", Context.class);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "getUDID not avaliable", e);
            }
            try {
                sGetOAID = cls.getMethod("getOAID", Context.class);
            } catch (NoSuchMethodException e2) {
                Log.e(TAG, "getOAID not avaliable", e2);
            }
            try {
                sGetVAID = cls.getMethod("getVAID", Context.class);
            } catch (NoSuchMethodException e3) {
                Log.e(TAG, "getVAID not avaliable", e3);
            }
            try {
                sGetAAID = cls.getMethod("getAAID", Context.class);
            } catch (NoSuchMethodException e4) {
                Log.e(TAG, "getAAID not avaliable", e4);
            }
        } catch (ClassNotFoundException e5) {
            Log.e(TAG, "provider not avaliable", e5);
        } catch (IllegalAccessException e6) {
            Log.e(TAG, "provider not avaliable", e6);
        } catch (InstantiationException e7) {
            Log.e(TAG, "provider not avaliable", e7);
        }
    }

    private AnonymousDeviceIdUtil() {
    }

    public static boolean isSupported(Context context) {
        return sIdProivderImpl != null;
    }

    public static boolean isEnforced(Context context) {
        if ("1".equals(SystemProperties.get("ro.miui.restrict_imei"))) {
            Log.i(TAG, "enforced");
            return true;
        }
        if (Build.IS_TABLET && android.os.Build.VERSION.SDK_INT >= 30) {
            Log.i(TAG, "new pad, enforced");
            return true;
        }
        if (android.os.Build.VERSION.SDK_INT >= 31) {
            Log.i(TAG, "greater than S, enforced");
            return true;
        }
        Log.i(TAG, "not enforced");
        return false;
    }

    public static boolean useOAID() {
        return sOAIDDeviceSet.contains(android.os.Build.DEVICE.toLowerCase());
    }

    public static String getAndroidId(Context context) {
        Log.i(TAG, "android id");
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getUDID(Context context) {
        return getId(context, sGetUDID);
    }

    public static String getOAID(Context context) {
        return getId(context, sGetOAID);
    }

    public static String getVAID(Context context) {
        return getId(context, sGetVAID);
    }

    public static String getAAID(Context context) {
        return getId(context, sGetAAID);
    }

    private static String getId(Context context, Method method) {
        Object obj = sIdProivderImpl;
        if (obj == null || method == null) {
            return null;
        }
        try {
            return (String) method.invoke(obj, context);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "exception invoking " + method, e);
            return null;
        } catch (InvocationTargetException e2) {
            Log.e(TAG, "exception invoking " + method, e2);
            return null;
        }
    }
}
