package org.apache.commons.lang3;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes6.dex */
public class Validate {
    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
    private static final String DEFAULT_FINITE_EX_MESSAGE = "The value is invalid: %f";
    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_NOT_NAN_EX_MESSAGE = "The validated value is not a number";
    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";

    public static void exclusiveBetween(double d, double d2, double d3) {
        if (d3 <= d || d3 >= d2) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, Double.valueOf(d3), Double.valueOf(d), Double.valueOf(d2)));
        }
    }

    public static void exclusiveBetween(double d, double d2, double d3, String str) {
        if (d3 <= d || d3 >= d2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void exclusiveBetween(long j, long j2, long j3) {
        if (j3 <= j || j3 >= j2) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, Long.valueOf(j3), Long.valueOf(j), Long.valueOf(j2)));
        }
    }

    public static void exclusiveBetween(long j, long j2, long j3, String str) {
        if (j3 <= j || j3 >= j2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static <T> void exclusiveBetween(T t, T t2, Comparable<T> comparable) {
        if (comparable.compareTo(t) <= 0 || comparable.compareTo(t2) >= 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, comparable, t, t2));
        }
    }

    public static <T> void exclusiveBetween(T t, T t2, Comparable<T> comparable, String str, Object... objArr) {
        if (comparable.compareTo(t) <= 0 || comparable.compareTo(t2) >= 0) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    public static void finite(double d) {
        finite(d, DEFAULT_FINITE_EX_MESSAGE, Double.valueOf(d));
    }

    public static void finite(double d, String str, Object... objArr) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getMessage(String str, Object... objArr) {
        return ArrayUtils.isEmpty(objArr) ? str : String.format(str, objArr);
    }

    public static void inclusiveBetween(double d, double d2, double d3) {
        if (d3 < d || d3 > d2) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, Double.valueOf(d3), Double.valueOf(d), Double.valueOf(d2)));
        }
    }

    public static void inclusiveBetween(double d, double d2, double d3, String str) {
        if (d3 < d || d3 > d2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void inclusiveBetween(long j, long j2, long j3) {
        if (j3 < j || j3 > j2) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, Long.valueOf(j3), Long.valueOf(j), Long.valueOf(j2)));
        }
    }

    public static void inclusiveBetween(long j, long j2, long j3, String str) {
        if (j3 < j || j3 > j2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static <T> void inclusiveBetween(T t, T t2, Comparable<T> comparable) {
        if (comparable.compareTo(t) < 0 || comparable.compareTo(t2) > 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, comparable, t, t2));
        }
    }

    public static <T> void inclusiveBetween(T t, T t2, Comparable<T> comparable, String str, Object... objArr) {
        if (comparable.compareTo(t) < 0 || comparable.compareTo(t2) > 0) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    public static void isAssignableFrom(Class<?> cls, Class<?> cls2) {
        if (cls2 == null || cls == null || !cls.isAssignableFrom(cls2)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_ASSIGNABLE_EX_MESSAGE, ClassUtils.getName(cls2, "null type"), ClassUtils.getName(cls, "null type")));
        }
    }

    public static void isAssignableFrom(Class<?> cls, Class<?> cls2, String str, Object... objArr) {
        if (!cls.isAssignableFrom(cls2)) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    public static void isInstanceOf(Class<?> cls, Object obj) {
        if (!cls.isInstance(obj)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_INSTANCE_OF_EX_MESSAGE, cls.getName(), ClassUtils.getName(obj, "null")));
        }
    }

    public static void isInstanceOf(Class<?> cls, Object obj, String str, Object... objArr) {
        if (!cls.isInstance(obj)) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    public static void isTrue(boolean z) {
        if (!z) {
            throw new IllegalArgumentException(DEFAULT_IS_TRUE_EX_MESSAGE);
        }
    }

    public static void isTrue(boolean z, String str, double d) {
        if (!z) {
            throw new IllegalArgumentException(String.format(str, Double.valueOf(d)));
        }
    }

    public static void isTrue(boolean z, String str, long j) {
        if (!z) {
            throw new IllegalArgumentException(String.format(str, Long.valueOf(j)));
        }
    }

    public static void isTrue(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    public static void isTrue(boolean z, Supplier<String> supplier) {
        if (!z) {
            throw new IllegalArgumentException(supplier.get());
        }
    }

    public static void matchesPattern(CharSequence charSequence, String str) {
        if (!Pattern.matches(str, charSequence)) {
            throw new IllegalArgumentException(String.format(DEFAULT_MATCHES_PATTERN_EX, charSequence, str));
        }
    }

    public static void matchesPattern(CharSequence charSequence, String str, String str2, Object... objArr) {
        if (!Pattern.matches(str, charSequence)) {
            throw new IllegalArgumentException(getMessage(str2, objArr));
        }
    }

    public static <T extends Iterable<?>> T noNullElements(T t) {
        return (T) noNullElements(t, DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Iterable<?>> T noNullElements(T t, final String str, final Object... objArr) {
        Objects.requireNonNull(t, "iterable");
        final AtomicInteger atomicInteger = new AtomicInteger();
        t.forEach(new Consumer() { // from class: org.apache.commons.lang3.Validate$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Validate.lambda$noNullElements$0(str, objArr, atomicInteger, obj);
            }
        });
        return t;
    }

    static /* synthetic */ void lambda$noNullElements$0(String str, Object[] objArr, AtomicInteger atomicInteger, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException(getMessage(str, ArrayUtils.addAll(objArr, Integer.valueOf(atomicInteger.getAndIncrement()))));
        }
    }

    public static <T> T[] noNullElements(T[] tArr) {
        return (T[]) noNullElements(tArr, DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE, new Object[0]);
    }

    public static <T> T[] noNullElements(T[] tArr, String str, Object... objArr) {
        Objects.requireNonNull(tArr, "array");
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i] == null) {
                throw new IllegalArgumentException(getMessage(str, ArrayUtils.add((Integer[]) objArr, Integer.valueOf(i))));
            }
        }
        return tArr;
    }

    public static <T extends CharSequence> T notBlank(T t) {
        return (T) notBlank(t, DEFAULT_NOT_BLANK_EX_MESSAGE, new Object[0]);
    }

    public static <T extends CharSequence> T notBlank(T t, String str, Object... objArr) {
        Objects.requireNonNull(t, toSupplier(str, objArr));
        if (StringUtils.isBlank(t)) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
        return t;
    }

    public static <T extends Collection<?>> T notEmpty(T t) {
        return (T) notEmpty(t, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Map<?, ?>> T notEmpty(T t) {
        return (T) notEmpty(t, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE, new Object[0]);
    }

    public static <T extends CharSequence> T notEmpty(T t) {
        return (T) notEmpty(t, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Collection<?>> T notEmpty(T t, String str, Object... objArr) {
        Objects.requireNonNull(t, toSupplier(str, objArr));
        if (t.isEmpty()) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
        return t;
    }

    public static <T extends Map<?, ?>> T notEmpty(T t, String str, Object... objArr) {
        Objects.requireNonNull(t, toSupplier(str, objArr));
        if (t.isEmpty()) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
        return t;
    }

    public static <T extends CharSequence> T notEmpty(T t, String str, Object... objArr) {
        Objects.requireNonNull(t, toSupplier(str, objArr));
        if (t.length() != 0) {
            return t;
        }
        throw new IllegalArgumentException(getMessage(str, objArr));
    }

    public static <T> T[] notEmpty(T[] tArr) {
        return (T[]) notEmpty(tArr, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE, new Object[0]);
    }

    public static <T> T[] notEmpty(T[] tArr, String str, Object... objArr) {
        Objects.requireNonNull(tArr, toSupplier(str, objArr));
        if (tArr.length != 0) {
            return tArr;
        }
        throw new IllegalArgumentException(getMessage(str, objArr));
    }

    public static void notNaN(double d) {
        notNaN(d, DEFAULT_NOT_NAN_EX_MESSAGE, new Object[0]);
    }

    public static void notNaN(double d, String str, Object... objArr) {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException(getMessage(str, objArr));
        }
    }

    @Deprecated
    public static <T> T notNull(T t) {
        return (T) notNull(t, DEFAULT_IS_NULL_EX_MESSAGE, new Object[0]);
    }

    public static <T> T notNull(T t, String str, Object... objArr) {
        return (T) Objects.requireNonNull(t, toSupplier(str, objArr));
    }

    private static Supplier<String> toSupplier(final String str, final Object... objArr) {
        return new Supplier() { // from class: org.apache.commons.lang3.Validate$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Validate.getMessage(str, objArr);
            }
        };
    }

    public static <T extends Collection<?>> T validIndex(T t, int i) {
        return (T) validIndex(t, i, DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE, Integer.valueOf(i));
    }

    public static <T extends CharSequence> T validIndex(T t, int i) {
        return (T) validIndex(t, i, DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE, Integer.valueOf(i));
    }

    public static <T extends Collection<?>> T validIndex(T t, int i, String str, Object... objArr) {
        Objects.requireNonNull(t, "collection");
        if (i < 0 || i >= t.size()) {
            throw new IndexOutOfBoundsException(getMessage(str, objArr));
        }
        return t;
    }

    public static <T extends CharSequence> T validIndex(T t, int i, String str, Object... objArr) {
        Objects.requireNonNull(t, "chars");
        if (i < 0 || i >= t.length()) {
            throw new IndexOutOfBoundsException(getMessage(str, objArr));
        }
        return t;
    }

    public static <T> T[] validIndex(T[] tArr, int i) {
        return (T[]) validIndex(tArr, i, DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE, Integer.valueOf(i));
    }

    public static <T> T[] validIndex(T[] tArr, int i, String str, Object... objArr) {
        Objects.requireNonNull(tArr, "array");
        if (i < 0 || i >= tArr.length) {
            throw new IndexOutOfBoundsException(getMessage(str, objArr));
        }
        return tArr;
    }

    public static void validState(boolean z) {
        if (!z) {
            throw new IllegalStateException(DEFAULT_VALID_STATE_EX_MESSAGE);
        }
    }

    public static void validState(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalStateException(getMessage(str, objArr));
        }
    }
}
