package org.apache.commons.lang3;

import com.market.sdk.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes6.dex */
public class ClassUtils {
    private static final Map<String, String> ABBREVIATION_MAP;
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    private static final int MAX_DIMENSIONS = 255;
    private static final Map<String, Class<?>> NAME_PRIMITIVE_MAP;
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP;
    private static final Map<String, String> REVERSE_ABBREVIATION_MAP;
    private static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP;
    private static final Comparator<Class<?>> COMPARATOR = new Comparator() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Objects.compare(ClassUtils.getName((Class<?>) obj), ClassUtils.getName((Class<?>) obj2), new Comparator() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda9
                @Override // java.util.Comparator
                public final int compare(Object obj3, Object obj4) {
                    return ((String) obj3).compareTo((String) obj4);
                }
            });
        }
    };
    public static final String PACKAGE_SEPARATOR = String.valueOf('.');
    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');

    public enum Interfaces {
        INCLUDE,
        EXCLUDE
    }

    private static boolean useFull(int i, int i2, int i3, int i4) {
        return i2 >= i3 || (i + i3) - i2 <= i4;
    }

    static {
        HashMap map = new HashMap();
        NAME_PRIMITIVE_MAP = map;
        map.put(Boolean.TYPE.getName(), Boolean.TYPE);
        map.put(Byte.TYPE.getName(), Byte.TYPE);
        map.put(Character.TYPE.getName(), Character.TYPE);
        map.put(Double.TYPE.getName(), Double.TYPE);
        map.put(Float.TYPE.getName(), Float.TYPE);
        map.put(Integer.TYPE.getName(), Integer.TYPE);
        map.put(Long.TYPE.getName(), Long.TYPE);
        map.put(Short.TYPE.getName(), Short.TYPE);
        map.put(Void.TYPE.getName(), Void.TYPE);
        HashMap map2 = new HashMap();
        PRIMITIVE_WRAPPER_MAP = map2;
        map2.put(Boolean.TYPE, Boolean.class);
        map2.put(Byte.TYPE, Byte.class);
        map2.put(Character.TYPE, Character.class);
        map2.put(Short.TYPE, Short.class);
        map2.put(Integer.TYPE, Integer.class);
        map2.put(Long.TYPE, Long.class);
        map2.put(Double.TYPE, Double.class);
        map2.put(Float.TYPE, Float.class);
        map2.put(Void.TYPE, Void.TYPE);
        WRAPPER_PRIMITIVE_MAP = new HashMap();
        map2.forEach(new BiConsumer() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ClassUtils.lambda$static$1((Class) obj, (Class) obj2);
            }
        });
        HashMap map3 = new HashMap();
        map3.put(Integer.TYPE.getName(), Field.INT_SIGNATURE_PRIMITIVE);
        map3.put(Boolean.TYPE.getName(), Field.BOOLEAN_SIGNATURE_PRIMITIVE);
        map3.put(Float.TYPE.getName(), Field.FLOAT_SIGNATURE_PRIMITIVE);
        map3.put(Long.TYPE.getName(), Field.LONG_SIGNATURE_PRIMITIVE);
        map3.put(Short.TYPE.getName(), "S");
        map3.put(Byte.TYPE.getName(), "B");
        map3.put(Double.TYPE.getName(), Field.DOUBLE_SIGNATURE_PRIMITIVE);
        map3.put(Character.TYPE.getName(), Field.CHAR_SIGNATURE_PRIMITIVE);
        ABBREVIATION_MAP = Collections.unmodifiableMap(map3);
        REVERSE_ABBREVIATION_MAP = Collections.unmodifiableMap((Map) map3.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) ((Map.Entry) obj).getValue();
            }
        }, new Function() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) ((Map.Entry) obj).getKey();
            }
        })));
    }

    static /* synthetic */ void lambda$static$1(Class cls, Class cls2) {
        if (cls.equals(cls2)) {
            return;
        }
        WRAPPER_PRIMITIVE_MAP.put(cls2, cls);
    }

    public static Comparator<Class<?>> comparator() {
        return COMPARATOR;
    }

    public static List<String> convertClassesToClassNames(List<Class<?>> list) {
        if (list == null) {
            return null;
        }
        return (List) list.stream().map(new Function() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ClassUtils.getName((Class<?>) obj, (String) null);
            }
        }).collect(Collectors.toList());
    }

    public static List<Class<?>> convertClassNamesToClasses(List<String> list) {
        if (list == null) {
            return null;
        }
        final ArrayList arrayList = new ArrayList(list.size());
        list.forEach(new Consumer() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ClassUtils.lambda$convertClassNamesToClasses$3(arrayList, (String) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ void lambda$convertClassNamesToClasses$3(List list, String str) {
        try {
            list.add(Class.forName(str));
        } catch (Exception unused) {
            list.add(null);
        }
    }

    public static String getAbbreviatedName(Class<?> cls, int i) {
        if (cls == null) {
            return "";
        }
        return getAbbreviatedName(cls.getName(), i);
    }

    public static String getAbbreviatedName(String str, int i) {
        char c;
        if (i <= 0) {
            throw new IllegalArgumentException("len must be > 0");
        }
        if (str == null) {
            return "";
        }
        if (str.length() <= i) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int i2 = 0;
        int i3 = 0;
        while (i2 < charArray.length) {
            int i4 = i3;
            while (i2 < charArray.length && (c = charArray[i2]) != '.') {
                i2++;
                charArray[i4] = c;
                i4++;
            }
            int i5 = i3 + 1;
            if (!useFull(i4, i2, charArray.length, i) && i5 <= i4) {
                i4 = i5;
            }
            if (i2 < charArray.length) {
                i3 = i4 + 1;
                charArray[i4] = charArray[i2];
                i2++;
            } else {
                i3 = i4;
            }
        }
        return new String(charArray, 0, i3);
    }

    public static List<Class<?>> getAllInterfaces(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        getAllInterfaces(cls, linkedHashSet);
        return new ArrayList(linkedHashSet);
    }

    private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> hashSet) {
        while (cls != null) {
            for (Class<?> cls2 : cls.getInterfaces()) {
                if (hashSet.add(cls2)) {
                    getAllInterfaces(cls2, hashSet);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Class<? super Object> superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            arrayList.add(superclass);
        }
        return arrayList;
    }

    public static String getCanonicalName(Class<?> cls) {
        return getCanonicalName(cls, "");
    }

    public static String getCanonicalName(Class<?> cls, String str) {
        String canonicalName;
        return (cls == null || (canonicalName = cls.getCanonicalName()) == null) ? str : canonicalName;
    }

    public static String getCanonicalName(Object obj) {
        return getCanonicalName(obj, "");
    }

    public static String getCanonicalName(Object obj, String str) {
        String canonicalName;
        return (obj == null || (canonicalName = obj.getClass().getCanonicalName()) == null) ? str : canonicalName;
    }

    private static String getCanonicalName(String str) {
        String strDeleteWhitespace = StringUtils.deleteWhitespace(str);
        if (strDeleteWhitespace == null) {
            return null;
        }
        int i = 0;
        while (strDeleteWhitespace.charAt(i) == '[') {
            i++;
            if (i > 255) {
                throw new IllegalArgumentException(String.format("Maximum array dimension %d exceeded", 255));
            }
        }
        if (i < 1) {
            return strDeleteWhitespace;
        }
        String strSubstring = strDeleteWhitespace.substring(i);
        if (strSubstring.startsWith("L")) {
            strSubstring = strSubstring.substring(1, strSubstring.endsWith(";") ? strSubstring.length() - 1 : strSubstring.length());
        } else if (!strSubstring.isEmpty()) {
            strSubstring = REVERSE_ABBREVIATION_MAP.get(strSubstring.substring(0, 1));
        }
        StringBuilder sb = new StringBuilder(strSubstring.length() + (i * 2));
        sb.append(strSubstring);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI);
        }
        return sb.toString();
    }

    public static Class<?> getClass(ClassLoader classLoader, String str) throws ClassNotFoundException {
        return getClass(classLoader, str, true);
    }

    public static Class<?> getClass(ClassLoader classLoader, String str, boolean z) throws ClassNotFoundException {
        int iLastIndexOf;
        do {
            try {
                Class<?> primitiveClass = getPrimitiveClass(str);
                if (primitiveClass == null) {
                    primitiveClass = Class.forName(toCanonicalName(str), z, classLoader);
                }
                return primitiveClass;
            } catch (ClassNotFoundException unused) {
                iLastIndexOf = str.lastIndexOf(46);
                if (iLastIndexOf != -1) {
                    str = str.substring(0, iLastIndexOf) + '$' + str.substring(iLastIndexOf + 1);
                }
            }
        } while (iLastIndexOf != -1);
        throw new ClassNotFoundException(str);
    }

    public static Class<?> getClass(String str) throws ClassNotFoundException {
        return getClass(str, true);
    }

    public static Class<?> getClass(String str, boolean z) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader == null) {
            contextClassLoader = ClassUtils.class.getClassLoader();
        }
        return getClass(contextClassLoader, str, z);
    }

    public static <T> Class<T> getComponentType(Class<T[]> cls) {
        if (cls == null) {
            return null;
        }
        return (Class<T>) cls.getComponentType();
    }

    public static String getName(Class<?> cls) {
        return getName(cls, "");
    }

    public static String getName(Class<?> cls, String str) {
        return cls == null ? str : cls.getName();
    }

    public static String getName(Object obj) {
        return getName(obj, "");
    }

    public static String getName(Object obj, String str) {
        return obj == null ? str : obj.getClass().getName();
    }

    public static String getPackageCanonicalName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return getPackageCanonicalName(cls.getName());
    }

    public static String getPackageCanonicalName(Object obj, String str) {
        return obj == null ? str : getPackageCanonicalName(obj.getClass().getName());
    }

    public static String getPackageCanonicalName(String str) {
        return getPackageName(getCanonicalName(str));
    }

    public static String getPackageName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return getPackageName(cls.getName());
    }

    public static String getPackageName(Object obj, String str) {
        return obj == null ? str : getPackageName(obj.getClass());
    }

    public static String getPackageName(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        while (str.charAt(0) == '[') {
            str = str.substring(1);
        }
        if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
            str = str.substring(1);
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf);
    }

    static Class<?> getPrimitiveClass(String str) {
        return NAME_PRIMITIVE_MAP.get(str);
    }

    public static Method getPublicMethod(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException {
        Method method = cls.getMethod(str, clsArr);
        if (isPublic(method.getDeclaringClass())) {
            return method;
        }
        ArrayList<Class> arrayList = new ArrayList(getAllInterfaces(cls));
        arrayList.addAll(getAllSuperclasses(cls));
        for (Class cls2 : arrayList) {
            if (isPublic(cls2)) {
                try {
                    Method method2 = cls2.getMethod(str, clsArr);
                    if (Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
                        return method2;
                    }
                } catch (NoSuchMethodException unused) {
                    continue;
                }
            }
        }
        throw new NoSuchMethodException("Can't find a public method for " + str + StringUtils.SPACE + ArrayUtils.toString(clsArr));
    }

    public static String getShortCanonicalName(Class<?> cls) {
        return cls == null ? "" : getShortCanonicalName(cls.getCanonicalName());
    }

    public static String getShortCanonicalName(Object obj, String str) {
        return obj == null ? str : getShortCanonicalName(obj.getClass().getCanonicalName());
    }

    public static String getShortCanonicalName(String str) {
        return getShortClassName(getCanonicalName(str));
    }

    public static String getShortClassName(Class<?> cls) {
        if (cls == null) {
            return "";
        }
        return getShortClassName(cls.getName());
    }

    public static String getShortClassName(Object obj, String str) {
        return obj == null ? str : getShortClassName(obj.getClass());
    }

    public static String getShortClassName(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (str.startsWith("[")) {
            while (str.charAt(0) == '[') {
                str = str.substring(1);
                sb.append(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI);
            }
            if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
                str = str.substring(1, str.length() - 1);
            }
            Map<String, String> map = REVERSE_ABBREVIATION_MAP;
            if (map.containsKey(str)) {
                str = map.get(str);
            }
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iIndexOf = str.indexOf(36, iLastIndexOf != -1 ? iLastIndexOf + 1 : 0);
        String strSubstring = str.substring(iLastIndexOf + 1);
        if (iIndexOf != -1) {
            strSubstring = strSubstring.replace('$', '.');
        }
        return strSubstring + ((Object) sb);
    }

    public static String getSimpleName(Class<?> cls) {
        return getSimpleName(cls, "");
    }

    public static String getSimpleName(Class<?> cls, String str) {
        return cls == null ? str : cls.getSimpleName();
    }

    public static String getSimpleName(Object obj) {
        return getSimpleName(obj, "");
    }

    public static String getSimpleName(Object obj, String str) {
        return obj == null ? str : obj.getClass().getSimpleName();
    }

    public static Iterable<Class<?>> hierarchy(Class<?> cls) {
        return hierarchy(cls, Interfaces.EXCLUDE);
    }

    public static Iterable<Class<?>> hierarchy(final Class<?> cls, Interfaces interfaces) {
        final Iterable<Class<?>> iterable = new Iterable() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda7
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return ClassUtils.lambda$hierarchy$4(cls);
            }
        };
        return interfaces != Interfaces.INCLUDE ? iterable : new Iterable() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda8
            @Override // java.lang.Iterable
            public final Iterator iterator() {
                return ClassUtils.lambda$hierarchy$5(iterable);
            }
        };
    }

    /* JADX INFO: renamed from: org.apache.commons.lang3.ClassUtils$1, reason: invalid class name */
    class AnonymousClass1 implements Iterator<Class<?>> {
        final /* synthetic */ AtomicReference val$next;

        AnonymousClass1(AtomicReference atomicReference) {
            this.val$next = atomicReference;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.val$next.get() != null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public Class<?> next() {
            return (Class) this.val$next.getAndUpdate(new UnaryOperator() { // from class: org.apache.commons.lang3.ClassUtils$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Class) obj).getSuperclass();
                }
            });
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    static /* synthetic */ Iterator lambda$hierarchy$4(Class cls) {
        return new AnonymousClass1(new AtomicReference(cls));
    }

    static /* synthetic */ Iterator lambda$hierarchy$5(Iterable iterable) {
        final HashSet hashSet = new HashSet();
        final Iterator it = iterable.iterator();
        return new Iterator<Class<?>>() { // from class: org.apache.commons.lang3.ClassUtils.2
            Iterator<Class<?>> interfaces = Collections.emptyIterator();

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.interfaces.hasNext() || it.hasNext();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public Class<?> next() {
                if (this.interfaces.hasNext()) {
                    Class<?> next = this.interfaces.next();
                    hashSet.add(next);
                    return next;
                }
                Class<?> cls = (Class) it.next();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                walkInterfaces(linkedHashSet, cls);
                this.interfaces = linkedHashSet.iterator();
                return cls;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void walkInterfaces(Set<Class<?>> set, Class<?> cls) {
                for (Class<?> cls2 : cls.getInterfaces()) {
                    if (!hashSet.contains(cls2)) {
                        set.add(cls2);
                    }
                    walkInterfaces(set, cls2);
                }
            }
        };
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2) {
        return isAssignable(cls, cls2, true);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2, boolean z) {
        if (cls2 == null) {
            return false;
        }
        if (cls == null) {
            return !cls2.isPrimitive();
        }
        if (z) {
            if (cls.isPrimitive() && !cls2.isPrimitive() && (cls = primitiveToWrapper(cls)) == null) {
                return false;
            }
            if (cls2.isPrimitive() && !cls.isPrimitive() && (cls = wrapperToPrimitive(cls)) == null) {
                return false;
            }
        }
        if (cls.equals(cls2)) {
            return true;
        }
        if (cls.isPrimitive()) {
            if (!cls2.isPrimitive()) {
                return false;
            }
            if (Integer.TYPE.equals(cls)) {
                return Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Long.TYPE.equals(cls)) {
                return Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Boolean.TYPE.equals(cls) || Double.TYPE.equals(cls)) {
                return false;
            }
            if (Float.TYPE.equals(cls)) {
                return Double.TYPE.equals(cls2);
            }
            if (Character.TYPE.equals(cls) || Short.TYPE.equals(cls)) {
                return Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Byte.TYPE.equals(cls)) {
                return Short.TYPE.equals(cls2) || Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            return false;
        }
        return cls2.isAssignableFrom(cls);
    }

    public static boolean isAssignable(Class<?>[] clsArr, Class<?>... clsArr2) {
        return isAssignable(clsArr, clsArr2, true);
    }

    public static boolean isAssignable(Class<?>[] clsArr, Class<?>[] clsArr2, boolean z) {
        if (!ArrayUtils.isSameLength((Object[]) clsArr, (Object[]) clsArr2)) {
            return false;
        }
        Class<?>[] clsArrNullToEmpty = ArrayUtils.nullToEmpty(clsArr);
        Class<?>[] clsArrNullToEmpty2 = ArrayUtils.nullToEmpty(clsArr2);
        for (int i = 0; i < clsArrNullToEmpty.length; i++) {
            if (!isAssignable(clsArrNullToEmpty[i], clsArrNullToEmpty2[i], z)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInnerClass(Class<?> cls) {
        return (cls == null || cls.getEnclosingClass() == null) ? false : true;
    }

    public static boolean isPrimitiveOrWrapper(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return cls.isPrimitive() || isPrimitiveWrapper(cls);
    }

    public static boolean isPrimitiveWrapper(Class<?> cls) {
        return WRAPPER_PRIMITIVE_MAP.containsKey(cls);
    }

    public static boolean isPublic(Class<?> cls) {
        return Modifier.isPublic(cls.getModifiers());
    }

    public static Class<?>[] primitivesToWrappers(final Class<?>... clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class<?>[] clsArr2 = new Class[clsArr.length];
        Arrays.setAll(clsArr2, new IntFunction() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ClassUtils.primitiveToWrapper(clsArr[i]);
            }
        });
        return clsArr2;
    }

    public static Class<?> primitiveToWrapper(Class<?> cls) {
        return (cls == null || !cls.isPrimitive()) ? cls : PRIMITIVE_WRAPPER_MAP.get(cls);
    }

    private static String toCanonicalName(String str) {
        String strDeleteWhitespace = StringUtils.deleteWhitespace(str);
        Objects.requireNonNull(strDeleteWhitespace, "className");
        if (!strDeleteWhitespace.endsWith(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI)) {
            return strDeleteWhitespace;
        }
        StringBuilder sb = new StringBuilder();
        while (strDeleteWhitespace.endsWith(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI)) {
            strDeleteWhitespace = strDeleteWhitespace.substring(0, strDeleteWhitespace.length() - 2);
            sb.append("[");
        }
        String str2 = ABBREVIATION_MAP.get(strDeleteWhitespace);
        if (str2 != null) {
            sb.append(str2);
        } else {
            sb.append("L").append(strDeleteWhitespace).append(";");
        }
        return sb.toString();
    }

    public static Class<?>[] toClass(final Object... objArr) {
        if (objArr == null) {
            return null;
        }
        if (objArr.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Class<?>[] clsArr = new Class[objArr.length];
        Arrays.setAll(clsArr, new IntFunction() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda11
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ClassUtils.lambda$toClass$7(objArr, i);
            }
        });
        return clsArr;
    }

    static /* synthetic */ Class lambda$toClass$7(Object[] objArr, int i) {
        Object obj = objArr[i];
        if (obj == null) {
            return null;
        }
        return obj.getClass();
    }

    public static Class<?>[] wrappersToPrimitives(final Class<?>... clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class<?>[] clsArr2 = new Class[clsArr.length];
        Arrays.setAll(clsArr2, new IntFunction() { // from class: org.apache.commons.lang3.ClassUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ClassUtils.wrapperToPrimitive(clsArr[i]);
            }
        });
        return clsArr2;
    }

    public static Class<?> wrapperToPrimitive(Class<?> cls) {
        return WRAPPER_PRIMITIVE_MAP.get(cls);
    }

    @Deprecated
    public ClassUtils() {
    }
}
