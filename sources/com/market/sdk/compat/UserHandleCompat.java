package com.market.sdk.compat;

import android.os.UserHandle;
import com.market.sdk.reflect.Field;
import com.market.sdk.utils.ReflectUtils;

/* JADX INFO: loaded from: classes2.dex */
public class UserHandleCompat {
    public static final int USER_CURRENT;
    public static final int USER_OWNER;
    private static Class<?> sUserHandleClazz;

    static {
        Integer num = (Integer) ReflectUtils.getFieldValue(UserHandle.class, UserHandle.class, "USER_CURRENT", Field.INT_SIGNATURE_PRIMITIVE);
        USER_CURRENT = num != null ? num.intValue() : -2;
        Integer num2 = (Integer) ReflectUtils.getFieldValue(UserHandle.class, UserHandle.class, "USER_OWNER", Field.INT_SIGNATURE_PRIMITIVE);
        USER_OWNER = num2 != null ? num2.intValue() : 0;
        sUserHandleClazz = ReflectUtils.getClass("android.os.UserHandle");
    }

    public static int myUserId() {
        Class<?> cls = sUserHandleClazz;
        return ((Integer) ReflectUtils.invokeObject(cls, cls, "myUserId", ReflectUtils.getMethodSignature(Integer.TYPE, new Class[0]), new Object[0])).intValue();
    }

    public static int getXSpaceUserId() {
        Integer num;
        Class<?> cls = ReflectUtils.getClass("miui.securityspace.XSpaceUserHandle");
        if (cls == null || (num = (Integer) ReflectUtils.getFieldValue(cls, cls, "USER_XSPACE", Field.INT_SIGNATURE_PRIMITIVE)) == null) {
            return 999;
        }
        return num.intValue();
    }
}
