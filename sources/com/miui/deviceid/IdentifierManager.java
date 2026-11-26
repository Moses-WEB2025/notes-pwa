package com.miui.deviceid;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class IdentifierManager {
    private static final String TAG = "IdentifierManager";
    private static Class<?> sClass;
    private static Method sGetAAID;
    private static Method sGetOAID;
    private static Method sGetUDID;
    private static Method sGetVAID;
    private static Object sIdProivderImpl;

    static {
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            sClass = cls;
            sIdProivderImpl = cls.newInstance();
            sGetUDID = sClass.getMethod("getUDID", Context.class);
            sGetOAID = sClass.getMethod("getOAID", Context.class);
            sGetVAID = sClass.getMethod("getVAID", Context.class);
            sGetAAID = sClass.getMethod("getAAID", Context.class);
        } catch (Exception e) {
            Log.e(TAG, "reflect exception!", e);
        }
    }

    public static boolean isSupported() {
        return (sClass == null || sIdProivderImpl == null) ? false : true;
    }

    public static String getUDID(Context context) {
        return invokeMethod(context, sGetUDID);
    }

    public static String getOAID(Context context) {
        return invokeMethod(context, sGetOAID);
    }

    public static String getVAID(Context context) {
        return invokeMethod(context, sGetVAID);
    }

    public static String getAAID(Context context) {
        return invokeMethod(context, sGetAAID);
    }

    private static String invokeMethod(Context context, Method method) {
        Object obj = sIdProivderImpl;
        if (obj == null || method == null) {
            return null;
        }
        try {
            Object objInvoke = method.invoke(obj, context);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "invoke exception!", e);
            return null;
        }
    }
}
