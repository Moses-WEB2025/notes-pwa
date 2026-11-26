package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes6.dex */
public class EnumUtils {
    private static final String CANNOT_STORE_S_S_VALUES_IN_S_BITS = "Cannot store %s %s values in %s bits";
    private static final String ENUM_CLASS_MUST_BE_DEFINED = "EnumClass must be defined.";
    private static final String NULL_ELEMENTS_NOT_PERMITTED = "null elements not permitted";
    private static final String S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE = "%s does not seem to be an Enum type";

    private static <E extends Enum<E>> Class<E> asEnum(Class<E> cls) {
        Objects.requireNonNull(cls, ENUM_CLASS_MUST_BE_DEFINED);
        Validate.isTrue(cls.isEnum(), S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE, cls);
        return cls;
    }

    private static <E extends Enum<E>> Class<E> checkBitVectorable(Class<E> cls) {
        Enum[] enumArr = (Enum[]) asEnum(cls).getEnumConstants();
        Validate.isTrue(enumArr.length <= 64, CANNOT_STORE_S_S_VALUES_IN_S_BITS, Integer.valueOf(enumArr.length), cls.getSimpleName(), 64);
        return cls;
    }

    @SafeVarargs
    public static <E extends Enum<E>> long generateBitVector(Class<E> cls, E... eArr) {
        Validate.noNullElements(eArr);
        return generateBitVector(cls, Arrays.asList(eArr));
    }

    public static <E extends Enum<E>> long generateBitVector(Class<E> cls, Iterable<? extends E> iterable) {
        checkBitVectorable(cls);
        Objects.requireNonNull(iterable, "values");
        long jOrdinal = 0;
        for (E e : iterable) {
            Objects.requireNonNull(e, NULL_ELEMENTS_NOT_PERMITTED);
            jOrdinal |= 1 << e.ordinal();
        }
        return jOrdinal;
    }

    @SafeVarargs
    public static <E extends Enum<E>> long[] generateBitVectors(Class<E> cls, E... eArr) {
        asEnum(cls);
        Validate.noNullElements(eArr);
        EnumSet<Enum> enumSetNoneOf = EnumSet.noneOf(cls);
        Collections.addAll(enumSetNoneOf, eArr);
        long[] jArr = new long[((cls.getEnumConstants().length - 1) / 64) + 1];
        for (Enum r0 : enumSetNoneOf) {
            int iOrdinal = r0.ordinal() / 64;
            jArr[iOrdinal] = jArr[iOrdinal] | (1 << (r0.ordinal() % 64));
        }
        ArrayUtils.reverse(jArr);
        return jArr;
    }

    public static <E extends Enum<E>> long[] generateBitVectors(Class<E> cls, Iterable<? extends E> iterable) {
        asEnum(cls);
        Objects.requireNonNull(iterable, "values");
        final EnumSet<Enum> enumSetNoneOf = EnumSet.noneOf(cls);
        iterable.forEach(new Consumer() { // from class: org.apache.commons.lang3.EnumUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                enumSetNoneOf.add((Enum) Objects.requireNonNull((Enum) obj, EnumUtils.NULL_ELEMENTS_NOT_PERMITTED));
            }
        });
        long[] jArr = new long[((cls.getEnumConstants().length - 1) / 64) + 1];
        for (Enum r0 : enumSetNoneOf) {
            int iOrdinal = r0.ordinal() / 64;
            jArr[iOrdinal] = jArr[iOrdinal] | (1 << (r0.ordinal() % 64));
        }
        ArrayUtils.reverse(jArr);
        return jArr;
    }

    public static <E extends Enum<E>> E getEnum(Class<E> cls, String str) {
        return (E) getEnum(cls, str, null);
    }

    public static <E extends Enum<E>> E getEnum(Class<E> cls, String str, E e) {
        if (cls != null && str != null) {
            try {
                return (E) Enum.valueOf(cls, str);
            } catch (IllegalArgumentException unused) {
            }
        }
        return e;
    }

    public static <E extends Enum<E>> E getEnumIgnoreCase(Class<E> cls, String str) {
        return (E) getEnumIgnoreCase(cls, str, null);
    }

    public static <E extends Enum<E>> E getEnumIgnoreCase(Class<E> cls, String str, E e) {
        return (E) getFirstEnumIgnoreCase(cls, str, new EnumUtils$$ExternalSyntheticLambda4(), e);
    }

    public static <E extends Enum<E>> List<E> getEnumList(Class<E> cls) {
        return new ArrayList(Arrays.asList(cls.getEnumConstants()));
    }

    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> cls) {
        return getEnumMap(cls, new EnumUtils$$ExternalSyntheticLambda4());
    }

    public static <E extends Enum<E>, K> Map<K, E> getEnumMap(Class<E> cls, final Function<E, K> function) {
        Stream stream = stream(cls);
        Objects.requireNonNull(function);
        return (Map) stream.collect(Collectors.toMap(new Function() { // from class: org.apache.commons.lang3.EnumUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Enum) obj);
            }
        }, Function.identity()));
    }

    public static <E extends Enum<E>> E getEnumSystemProperty(Class<E> cls, String str, E e) {
        return (E) getEnum(cls, SystemProperties.getProperty(str), e);
    }

    public static <E extends Enum<E>> E getFirstEnum(Class<E> cls, final int i, final ToIntFunction<E> toIntFunction, E e) {
        return isEnum(cls) ? e : (E) stream(cls).filter(new Predicate() { // from class: org.apache.commons.lang3.EnumUtils$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return EnumUtils.lambda$getFirstEnum$1(i, toIntFunction, (Enum) obj);
            }
        }).findFirst().orElse(e);
    }

    static /* synthetic */ boolean lambda$getFirstEnum$1(int i, ToIntFunction toIntFunction, Enum r2) {
        return i == toIntFunction.applyAsInt(r2);
    }

    public static <E extends Enum<E>> E getFirstEnumIgnoreCase(Class<E> cls, final String str, final Function<E, String> function, E e) {
        return str == null ? e : (E) stream(cls).filter(new Predicate() { // from class: org.apache.commons.lang3.EnumUtils$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equalsIgnoreCase((String) function.apply((Enum) obj));
            }
        }).findFirst().orElse(e);
    }

    private static <E extends Enum<E>> boolean isEnum(Class<E> cls) {
        return (cls == null || cls.isEnum()) ? false : true;
    }

    public static <E extends Enum<E>> boolean isValidEnum(Class<E> cls, String str) {
        return getEnum(cls, str) != null;
    }

    public static <E extends Enum<E>> boolean isValidEnumIgnoreCase(Class<E> cls, String str) {
        return getEnumIgnoreCase(cls, str) != null;
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVector(Class<E> cls, long j) {
        return processBitVectors(checkBitVectorable(cls), j);
    }

    public static <E extends Enum<E>> EnumSet<E> processBitVectors(Class<E> cls, long... jArr) {
        final EnumSet<E> enumSetNoneOf = EnumSet.noneOf(asEnum(cls));
        final long[] jArrClone = ArrayUtils.clone((long[]) Objects.requireNonNull(jArr, "values"));
        ArrayUtils.reverse(jArrClone);
        stream(cls).forEach(new Consumer() { // from class: org.apache.commons.lang3.EnumUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                EnumUtils.lambda$processBitVectors$3(jArrClone, enumSetNoneOf, (Enum) obj);
            }
        });
        return enumSetNoneOf;
    }

    static /* synthetic */ void lambda$processBitVectors$3(long[] jArr, EnumSet enumSet, Enum r6) {
        int iOrdinal = r6.ordinal() / 64;
        if (iOrdinal >= jArr.length || (jArr[iOrdinal] & (1 << (r6.ordinal() % 64))) == 0) {
            return;
        }
        enumSet.add(r6);
    }

    public static <T> Stream<T> stream(Class<T> cls) {
        return cls != null ? org.apache.commons.lang3.stream.Streams.of(cls.getEnumConstants()) : Stream.empty();
    }

    @Deprecated
    public EnumUtils() {
    }
}
