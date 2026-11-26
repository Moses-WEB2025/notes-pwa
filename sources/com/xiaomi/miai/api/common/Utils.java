package com.xiaomi.miai.api.common;

import com.xiaomi.common.Optional;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public class Utils {
    private static Set<Class<?>> wrapperTypes = getWrapperTypes();

    private static Set<Class<?>> getWrapperTypes() {
        HashSet hashSet = new HashSet();
        hashSet.add(Boolean.class);
        hashSet.add(Character.class);
        hashSet.add(Byte.class);
        hashSet.add(Short.class);
        hashSet.add(Integer.class);
        hashSet.add(Long.class);
        hashSet.add(Float.class);
        hashSet.add(Double.class);
        hashSet.add(Void.class);
        return hashSet;
    }

    public static void dumpValue(Object obj, StringBuilder sb) {
        if (obj != null) {
            try {
                sb.append(StringUtils.LF).append(obj.getClass().getName());
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
        dumpValue(obj, "", sb, -1);
    }

    private static void dumpValue(Object obj, String str, StringBuilder sb, int i) throws IllegalAccessException {
        if (obj == null) {
            sb.append("null");
            return;
        }
        String name = obj.getClass().getName();
        if (name.contains("jackson") || name.contains("gson")) {
            sb.append(obj);
            return;
        }
        String str2 = str + "  ";
        Class<?> cls = obj.getClass();
        if (cls.isPrimitive() || wrapperTypes.contains(cls) || cls.isEnum()) {
            sb.append(obj);
            return;
        }
        if (cls == String.class) {
            sb.append("\"").append(obj).append("\"");
            return;
        }
        if (cls.equals(Optional.class)) {
            Optional optional = (Optional) obj;
            if (optional.isPresent()) {
                dumpValue(optional.get(), str, sb, -1);
                return;
            } else {
                sb.append(" // [Optional, Not Present] ");
                return;
            }
        }
        int i2 = 0;
        if (cls.isArray()) {
            sb.append("[");
            int length = Array.getLength(obj);
            while (i2 < length) {
                dumpValue(Array.get(obj, i2), str2, sb, i2);
                if (i2 != length - 1) {
                    sb.append(",");
                }
                i2++;
            }
            sb.append(StringUtils.LF).append(str).append("]");
            return;
        }
        if (Map.class.isAssignableFrom(cls)) {
            sb.append("{");
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                sb.append(str2).append(entry.getKey()).append(": ");
                dumpValue(entry.getValue(), str2, sb, i2);
                i2++;
            }
            sb.append(StringUtils.LF).append(str).append(StringSubstitutor.DEFAULT_VAR_END);
            return;
        }
        if (Collection.class.isAssignableFrom(cls)) {
            sb.append("[");
            Collection collection = (Collection) obj;
            int size = collection.size();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                dumpValue(it.next(), str2, sb, i2);
                if (i2 != size - 1) {
                    sb.append(",");
                }
                i2++;
            }
            sb.append(StringUtils.LF).append(str).append("]");
            return;
        }
        if (i >= 0) {
            sb.append(StringUtils.LF).append(str).append("{ // ").append(i);
        }
        for (Field field : getAllFields(cls)) {
            sb.append(StringUtils.LF);
            field.setAccessible(true);
            sb.append(str2).append(field.getName()).append(": ");
            dumpValue(field.get(obj), str2, sb, -1);
            field.setAccessible(false);
        }
        if (i >= 0) {
            sb.append(StringUtils.LF).append(str).append(StringSubstitutor.DEFAULT_VAR_END);
        }
    }

    private static List<Field> getAllFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        while (cls != null && !cls.getName().startsWith("java")) {
            for (Field field : cls.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    arrayList.add(field);
                }
            }
            cls = cls.getSuperclass();
        }
        return arrayList;
    }
}
