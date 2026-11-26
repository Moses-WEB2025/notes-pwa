package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.BooleanCompanionObject;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.CharCompanionObject;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ShortCompanionObject;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KClass;
import kotlin.text.StringsKt;
import kotlin.time.Duration;
import kotlin.uuid.Uuid;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Polymorphic;
import kotlinx.serialization.PolymorphicSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.builtins.BuiltinSerializersKt;

/* JADX INFO: compiled from: Platform.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0080\b¢\u0006\u0002\u0010\u0005\u001a\u0015\u0010\u0000\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0080\b\u001a\u001c\u0010\b\u001a\u00020\u0006\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\nH\u0000\u001a$\u0010\u000b\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\nH\u0000\u001aK\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0002\"\b\b\u0000\u0010\u0001*\u00020\t\"\n\b\u0001\u0010\u000e*\u0004\u0018\u0001H\u0001*\u0012\u0012\u0004\u0012\u0002H\u000e0\u000fj\b\u0012\u0004\u0012\u0002H\u000e`\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0000¢\u0006\u0002\u0010\u0012\u001a\u0010\u0010\u0013\u001a\u00020\u0014*\u0006\u0012\u0002\b\u00030\nH\u0000\u001a\u0010\u0010\u0015\u001a\u00020\u0014*\u0006\u0012\u0002\b\u00030\u0016H\u0000\u001aM\u0010\u0017\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\n2\"\u0010\u0018\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f0\u0002\"\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\fH\u0000¢\u0006\u0002\u0010\u0019\u001aM\u0010\u0017\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u00162\"\u0010\u0018\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f0\u0002\"\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\fH\u0000¢\u0006\u0002\u0010\u001a\u001aM\u0010\u001b\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u00162\"\u0010\u0018\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f0\u0002\"\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\fH\u0002¢\u0006\u0002\u0010\u001a\u001a\u001e\u0010\u001c\u001a\u0004\u0018\u00010\t\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u0016H\u0002\u001a\u001c\u0010\u001d\u001a\u00020\u0006\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u0016H\u0002\u001a\u001c\u0010\u001e\u001a\u00020\u0006\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u0016H\u0002\u001aO\u0010\u001f\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t2\n\u0010 \u001a\u0006\u0012\u0002\b\u00030\u00162\"\u0010\u0018\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f0\u0002\"\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\fH\u0002¢\u0006\u0002\u0010\u001a\u001aK\u0010!\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t2\u0006\u0010\"\u001a\u00020\t2\"\u0010\u0018\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\f0\u0002\"\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\fH\u0002¢\u0006\u0002\u0010#\u001a\u001a\u0010$\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\u00162\u0006\u0010%\u001a\u00020&H\u0002\u001a\"\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u0016H\u0002\u001a$\u0010(\u001a\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\f\"\b\b\u0000\u0010\u0001*\u00020\t*\b\u0012\u0004\u0012\u0002H\u00010\u0016H\u0002\u001a\u0016\u0010)\u001a\u00020\u00062\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\t0\nH\u0000\u001a\u001c\u0010+\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0,H\u0000\u001a\u0017\u0010-\u001a\u00020.2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020.00H\u0082\b¨\u00061"}, d2 = {"getChecked", ExifInterface.GPS_DIRECTION_TRUE, "", "index", "", "([Ljava/lang/Object;I)Ljava/lang/Object;", "", "", "isInterface", "", "Lkotlin/reflect/KClass;", "compiledSerializerImpl", "Lkotlinx/serialization/KSerializer;", "toNativeArrayImpl", ExifInterface.LONGITUDE_EAST, "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "eClass", "(Ljava/util/ArrayList;Lkotlin/reflect/KClass;)[Ljava/lang/Object;", "platformSpecificSerializerNotRegistered", "", "serializerNotRegistered", "Ljava/lang/Class;", "constructSerializerForGivenTypeArgs", "args", "(Lkotlin/reflect/KClass;[Lkotlinx/serialization/KSerializer;)Lkotlinx/serialization/KSerializer;", "(Ljava/lang/Class;[Lkotlinx/serialization/KSerializer;)Lkotlinx/serialization/KSerializer;", "findInNamedCompanion", "findNamedCompanionByAnnotation", "isNotAnnotated", "isPolymorphicSerializer", "invokeSerializerOnDefaultCompanion", "jClass", "invokeSerializerOnCompanion", "companion", "(Ljava/lang/Object;[Lkotlinx/serialization/KSerializer;)Lkotlinx/serialization/KSerializer;", "companionOrNull", "companionName", "", "createEnumSerializer", "findObjectSerializer", "isReferenceArray", "rootClass", "initBuiltins", "", "loadSafe", "", "block", "Lkotlin/Function0;", "kotlinx-serialization-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class PlatformKt {
    public static final <T> T getChecked(T[] tArr, int i) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        return tArr[i];
    }

    public static final boolean getChecked(boolean[] zArr, int i) {
        Intrinsics.checkNotNullParameter(zArr, "<this>");
        return zArr[i];
    }

    public static final <T> boolean isInterface(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        return JvmClassMappingKt.getJavaClass((KClass) kClass).isInterface();
    }

    public static final <T> KSerializer<T> compiledSerializerImpl(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        return constructSerializerForGivenTypeArgs(kClass, (KSerializer<Object>[]) new KSerializer[0]);
    }

    public static final <T, E extends T> E[] toNativeArrayImpl(ArrayList<E> arrayList, KClass<T> eClass) {
        Intrinsics.checkNotNullParameter(arrayList, "<this>");
        Intrinsics.checkNotNullParameter(eClass, "eClass");
        Object objNewInstance = Array.newInstance((Class<?>) JvmClassMappingKt.getJavaClass((KClass) eClass), arrayList.size());
        Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.Array<E of kotlinx.serialization.internal.PlatformKt.toNativeArrayImpl>");
        E[] eArr = (E[]) arrayList.toArray((Object[]) objNewInstance);
        Intrinsics.checkNotNullExpressionValue(eArr, "toArray(...)");
        return eArr;
    }

    public static final Void platformSpecificSerializerNotRegistered(KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Platform_commonKt.serializerNotRegistered(kClass);
        throw new KotlinNothingValueException();
    }

    public static final Void serializerNotRegistered(Class<?> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        throw new SerializationException(Platform_commonKt.notRegisteredMessage((KClass<?>) JvmClassMappingKt.getKotlinClass(cls)));
    }

    public static final <T> KSerializer<T> constructSerializerForGivenTypeArgs(KClass<T> kClass, KSerializer<Object>... args) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        return constructSerializerForGivenTypeArgs(JvmClassMappingKt.getJavaClass((KClass) kClass), (KSerializer<Object>[]) Arrays.copyOf(args, args.length));
    }

    public static final <T> KSerializer<T> constructSerializerForGivenTypeArgs(Class<T> cls, KSerializer<Object>... args) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        if (cls.isEnum() && isNotAnnotated(cls)) {
            return createEnumSerializer(cls);
        }
        KSerializer<T> kSerializerInvokeSerializerOnDefaultCompanion = invokeSerializerOnDefaultCompanion(cls, (KSerializer[]) Arrays.copyOf(args, args.length));
        if (kSerializerInvokeSerializerOnDefaultCompanion != null) {
            return kSerializerInvokeSerializerOnDefaultCompanion;
        }
        KSerializer<T> kSerializerFindObjectSerializer = findObjectSerializer(cls);
        if (kSerializerFindObjectSerializer != null) {
            return kSerializerFindObjectSerializer;
        }
        KSerializer<T> kSerializerFindInNamedCompanion = findInNamedCompanion(cls, (KSerializer[]) Arrays.copyOf(args, args.length));
        if (kSerializerFindInNamedCompanion != null) {
            return kSerializerFindInNamedCompanion;
        }
        if (isPolymorphicSerializer(cls)) {
            return new PolymorphicSerializer(JvmClassMappingKt.getKotlinClass(cls));
        }
        return null;
    }

    private static final <T> KSerializer<T> findInNamedCompanion(Class<T> cls, KSerializer<Object>... kSerializerArr) {
        Field field;
        KSerializer<T> kSerializerInvokeSerializerOnCompanion;
        Object objFindNamedCompanionByAnnotation = findNamedCompanionByAnnotation(cls);
        if (objFindNamedCompanionByAnnotation != null && (kSerializerInvokeSerializerOnCompanion = invokeSerializerOnCompanion(objFindNamedCompanionByAnnotation, (KSerializer[]) Arrays.copyOf(kSerializerArr, kSerializerArr.length))) != null) {
            return kSerializerInvokeSerializerOnCompanion;
        }
        try {
            Class<?>[] declaredClasses = cls.getDeclaredClasses();
            Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
            Class<?>[] clsArr = declaredClasses;
            int length = clsArr.length;
            int i = 0;
            Class<?> cls2 = null;
            boolean z = false;
            while (true) {
                if (i >= length) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Class<?> cls3 = clsArr[i];
                if (Intrinsics.areEqual(cls3.getSimpleName(), "$serializer")) {
                    if (!z) {
                        z = true;
                        cls2 = cls3;
                    }
                }
                i++;
                cls2 = null;
                break;
            }
            Class<?> cls4 = cls2;
            Object obj = (cls4 == null || (field = cls4.getField("INSTANCE")) == null) ? null : field.get(null);
            if (obj instanceof KSerializer) {
                return (KSerializer) obj;
            }
        } catch (NoSuchFieldException unused) {
        }
        return null;
    }

    private static final <T> Object findNamedCompanionByAnnotation(Class<T> cls) {
        Class<?> cls2;
        Class<?>[] declaredClasses = cls.getDeclaredClasses();
        Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
        Class<?>[] clsArr = declaredClasses;
        int length = clsArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                cls2 = null;
                break;
            }
            cls2 = clsArr[i];
            if (cls2.getAnnotation(NamedCompanion.class) != null) {
                break;
            }
            i++;
        }
        Class<?> cls3 = cls2;
        if (cls3 == null) {
            return null;
        }
        String simpleName = cls3.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        return companionOrNull(cls, simpleName);
    }

    private static final <T> boolean isNotAnnotated(Class<T> cls) {
        return cls.getAnnotation(Serializable.class) == null && cls.getAnnotation(Polymorphic.class) == null;
    }

    private static final <T> boolean isPolymorphicSerializer(Class<T> cls) {
        if (cls.getAnnotation(Polymorphic.class) != null) {
            return true;
        }
        Serializable serializable = (Serializable) cls.getAnnotation(Serializable.class);
        return serializable != null && Intrinsics.areEqual(Reflection.getOrCreateKotlinClass(serializable.with()), Reflection.getOrCreateKotlinClass(PolymorphicSerializer.class));
    }

    private static final <T> KSerializer<T> invokeSerializerOnDefaultCompanion(Class<?> cls, KSerializer<Object>... kSerializerArr) {
        Object objCompanionOrNull = companionOrNull(cls, "Companion");
        if (objCompanionOrNull == null) {
            return null;
        }
        return invokeSerializerOnCompanion(objCompanionOrNull, (KSerializer[]) Arrays.copyOf(kSerializerArr, kSerializerArr.length));
    }

    private static final <T> KSerializer<T> invokeSerializerOnCompanion(Object obj, KSerializer<Object>... kSerializerArr) throws IllegalAccessException, InvocationTargetException {
        Class[] clsArr;
        try {
            if (kSerializerArr.length == 0) {
                clsArr = new Class[0];
            } else {
                int length = kSerializerArr.length;
                Class[] clsArr2 = new Class[length];
                for (int i = 0; i < length; i++) {
                    clsArr2[i] = KSerializer.class;
                }
                clsArr = clsArr2;
            }
            Object objInvoke = obj.getClass().getDeclaredMethod("serializer", (Class[]) Arrays.copyOf(clsArr, clsArr.length)).invoke(obj, Arrays.copyOf(kSerializerArr, kSerializerArr.length));
            if (objInvoke instanceof KSerializer) {
                return (KSerializer) objInvoke;
            }
            return null;
        } catch (NoSuchMethodException unused) {
            return null;
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                throw e;
            }
            String message = cause.getMessage();
            if (message == null) {
                message = e.getMessage();
            }
            throw new InvocationTargetException(cause, message);
        }
    }

    private static final Object companionOrNull(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static final <T> KSerializer<T> createEnumSerializer(Class<T> cls) {
        T[] enumConstants = cls.getEnumConstants();
        String canonicalName = cls.getCanonicalName();
        Intrinsics.checkNotNullExpressionValue(canonicalName, "getCanonicalName(...)");
        Intrinsics.checkNotNull(enumConstants, "null cannot be cast to non-null type kotlin.Array<out kotlin.Enum<*>>");
        return new EnumSerializer(canonicalName, (Enum[]) enumConstants);
    }

    private static final <T> KSerializer<T> findObjectSerializer(Class<T> cls) throws IllegalAccessException, InvocationTargetException {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null && !StringsKt.startsWith$default(canonicalName, "java.", false, 2, (Object) null) && !StringsKt.startsWith$default(canonicalName, "kotlin.", false, 2, (Object) null)) {
            Field[] declaredFields = cls.getDeclaredFields();
            Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
            Field[] fieldArr = declaredFields;
            int length = fieldArr.length;
            Field field = null;
            int i = 0;
            boolean z = false;
            while (true) {
                if (i >= length) {
                    if (!z) {
                        break;
                    }
                    break;
                }
                Field field2 = fieldArr[i];
                Field field3 = field2;
                if (Intrinsics.areEqual(field3.getName(), "INSTANCE") && Intrinsics.areEqual(field3.getType(), cls) && Modifier.isStatic(field3.getModifiers())) {
                    if (!z) {
                        z = true;
                        field = field2;
                    }
                }
                i++;
                field = null;
                break;
            }
            Field field4 = field;
            if (field4 == null) {
                return null;
            }
            Object obj = field4.get(null);
            Method[] methods = cls.getMethods();
            Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
            Method[] methodArr = methods;
            int length2 = methodArr.length;
            Method method = null;
            int i2 = 0;
            boolean z2 = false;
            while (true) {
                if (i2 >= length2) {
                    if (!z2) {
                        break;
                    }
                    break;
                }
                Method method2 = methodArr[i2];
                Method method3 = method2;
                if (Intrinsics.areEqual(method3.getName(), "serializer")) {
                    Class<?>[] parameterTypes = method3.getParameterTypes();
                    Intrinsics.checkNotNullExpressionValue(parameterTypes, "getParameterTypes(...)");
                    if (parameterTypes.length == 0 && Intrinsics.areEqual(method3.getReturnType(), KSerializer.class)) {
                        if (!z2) {
                            z2 = true;
                            method = method2;
                        }
                    }
                }
                i2++;
                method = null;
                break;
            }
            Method method4 = method;
            if (method4 == null) {
                return null;
            }
            Object objInvoke = method4.invoke(obj, new Object[0]);
            if (objInvoke instanceof KSerializer) {
                return (KSerializer) objInvoke;
            }
        }
        return null;
    }

    public static final boolean isReferenceArray(KClass<Object> rootClass) {
        Intrinsics.checkNotNullParameter(rootClass, "rootClass");
        return JvmClassMappingKt.getJavaClass((KClass) rootClass).isArray();
    }

    public static final Map<KClass<?>, KSerializer<?>> initBuiltins() {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(String.class), BuiltinSerializersKt.serializer(StringCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Character.TYPE), BuiltinSerializersKt.serializer(CharCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(char[].class), BuiltinSerializersKt.CharArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Double.TYPE), BuiltinSerializersKt.serializer(DoubleCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(double[].class), BuiltinSerializersKt.DoubleArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Float.TYPE), BuiltinSerializersKt.serializer(FloatCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(float[].class), BuiltinSerializersKt.FloatArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Long.TYPE), BuiltinSerializersKt.serializer(LongCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(long[].class), BuiltinSerializersKt.LongArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(ULong.class), BuiltinSerializersKt.serializer(ULong.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Integer.TYPE), BuiltinSerializersKt.serializer(IntCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(int[].class), BuiltinSerializersKt.IntArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(UInt.class), BuiltinSerializersKt.serializer(UInt.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Short.TYPE), BuiltinSerializersKt.serializer(ShortCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(short[].class), BuiltinSerializersKt.ShortArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(UShort.class), BuiltinSerializersKt.serializer(UShort.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Byte.TYPE), BuiltinSerializersKt.serializer(ByteCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(byte[].class), BuiltinSerializersKt.ByteArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(UByte.class), BuiltinSerializersKt.serializer(UByte.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Boolean.TYPE), BuiltinSerializersKt.serializer(BooleanCompanionObject.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(boolean[].class), BuiltinSerializersKt.BooleanArraySerializer());
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Unit.class), BuiltinSerializersKt.serializer(Unit.INSTANCE));
        mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Void.class), BuiltinSerializersKt.NothingSerializer());
        try {
            mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Duration.class), BuiltinSerializersKt.serializer(Duration.INSTANCE));
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
        }
        try {
            mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(ULongArray.class), BuiltinSerializersKt.ULongArraySerializer());
        } catch (ClassNotFoundException | NoClassDefFoundError unused2) {
        }
        try {
            mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(UIntArray.class), BuiltinSerializersKt.UIntArraySerializer());
        } catch (ClassNotFoundException | NoClassDefFoundError unused3) {
        }
        try {
            mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(UShortArray.class), BuiltinSerializersKt.UShortArraySerializer());
        } catch (ClassNotFoundException | NoClassDefFoundError unused4) {
        }
        try {
            mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(UByteArray.class), BuiltinSerializersKt.UByteArraySerializer());
        } catch (ClassNotFoundException | NoClassDefFoundError unused5) {
        }
        try {
            mapCreateMapBuilder.put(Reflection.getOrCreateKotlinClass(Uuid.class), BuiltinSerializersKt.serializer(Uuid.INSTANCE));
        } catch (ClassNotFoundException | NoClassDefFoundError unused6) {
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    private static final void loadSafe(Function0<Unit> function0) {
        try {
            function0.invoke();
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
        }
    }
}
