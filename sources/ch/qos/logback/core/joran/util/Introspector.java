package ch.qos.logback.core.joran.util;

import com.xiaomi.mirror.synergy.CallMethod;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class Introspector {
    public static String decapitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        String lowerCase = str.substring(0, 1).toLowerCase(Locale.US);
        return str.length() > 1 ? lowerCase + str.substring(1) : lowerCase;
    }

    public static MethodDescriptor[] getMethodDescriptors(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        for (Method method : cls.getMethods()) {
            arrayList.add(new MethodDescriptor(method.getName(), method));
        }
        return (MethodDescriptor[]) arrayList.toArray(new MethodDescriptor[0]);
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> cls) {
        Class<?> returnType;
        int length = "set".length();
        HashMap map = new HashMap();
        for (Method method : cls.getMethods()) {
            String name = method.getName();
            boolean z = name.startsWith(CallMethod.METHOD_GET) && name.length() > length;
            boolean z2 = name.startsWith("set") && name.length() > length;
            if (z || z2) {
                String strDecapitalize = decapitalize(name.substring(length));
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) map.get(strDecapitalize);
                if (propertyDescriptor == null) {
                    propertyDescriptor = new PropertyDescriptor(strDecapitalize);
                    map.put(strDecapitalize, propertyDescriptor);
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (z2) {
                    if (parameterTypes.length == 1) {
                        propertyDescriptor.setWriteMethod(method);
                        returnType = parameterTypes[0];
                        propertyDescriptor.setPropertyType(returnType);
                    }
                } else if (z && parameterTypes.length == 0) {
                    propertyDescriptor.setReadMethod(method);
                    if (propertyDescriptor.getPropertyType() == null) {
                        returnType = method.getReturnType();
                        propertyDescriptor.setPropertyType(returnType);
                    }
                }
            }
        }
        return (PropertyDescriptor[]) map.values().toArray(new PropertyDescriptor[0]);
    }
}
