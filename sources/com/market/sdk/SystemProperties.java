package com.market.sdk;

import android.text.TextUtils;
import com.market.sdk.utils.Log;
import com.market.sdk.utils.Utils;
import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class SystemProperties {
    private static Class<?> sClazz;
    private static Method sMethodGet;

    static {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            sClazz = cls;
            sMethodGet = cls.getDeclaredMethod(CallMethod.METHOD_GET, String.class, String.class);
        } catch (Exception e) {
            Log.e(Utils.TAG, e.getMessage(), e);
        }
    }

    public static String getString(String str, String str2) {
        try {
            String str3 = (String) sMethodGet.invoke(sClazz, str, str2);
            return !TextUtils.isEmpty(str3) ? str3 : str2;
        } catch (Exception e) {
            Log.e(Utils.TAG, e.getMessage(), e);
            return str2;
        }
    }
}
