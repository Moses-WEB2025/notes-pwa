package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CopyableThrowable;

/* JADX INFO: compiled from: ExceptionsConstructor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a!\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00042\u0006\u0010\t\u001a\u0002H\bH\u0000¢\u0006\u0002\u0010\n\u001a2\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003j\u0002`\f\"\b\b\u0000\u0010\b*\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\b0\u000eH\u0002\u001a.\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003j\u0002`\f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\u0002\u001a\u0018\u0010\u0011\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0012\u001a\u00020\u0001H\u0002\u001a\u001b\u0010\u0013\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u0001H\u0082\u0010\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0002\"\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¨\u0006\u0015"}, d2 = {"throwableFields", "", "Ctor", "Lkotlin/Function1;", "", "ctorCache", "Lkotlinx/coroutines/internal/CtorCache;", "tryCopyException", ExifInterface.LONGITUDE_EAST, "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "createConstructor", "Lkotlinx/coroutines/internal/Ctor;", "clz", "Ljava/lang/Class;", "safeCtor", "block", "fieldsCountOrDefault", "defaultValue", "fieldsCount", "accumulator", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class ExceptionsConstructorKt {
    private static final CtorCache ctorCache;
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    static {
        WeakMapCtorCache weakMapCtorCache;
        try {
            weakMapCtorCache = FastServiceLoaderKt.getANDROID_DETECTED() ? WeakMapCtorCache.INSTANCE : ClassValueCtorCache.INSTANCE;
        } catch (Throwable unused) {
            weakMapCtorCache = WeakMapCtorCache.INSTANCE;
        }
        ctorCache = weakMapCtorCache;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <E extends Throwable> E tryCopyException(E e) {
        Object objM2532constructorimpl;
        if (e instanceof CopyableThrowable) {
            try {
                Result.Companion companion = Result.INSTANCE;
                objM2532constructorimpl = Result.m2532constructorimpl(((CopyableThrowable) e).createCopy());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM2532constructorimpl = Result.m2532constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m2538isFailureimpl(objM2532constructorimpl)) {
                objM2532constructorimpl = null;
            }
            return (E) objM2532constructorimpl;
        }
        return (E) ctorCache.get(e.getClass()).invoke(e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <E extends Throwable> Function1<Throwable, Throwable> createConstructor(Class<E> cls) {
        Object next;
        Function1<Throwable, Throwable> function1;
        Pair pair;
        ExceptionsConstructorKt$createConstructor$nullResult$1 exceptionsConstructorKt$createConstructor$nullResult$1 = new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createConstructor$nullResult$1
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(Throwable th) {
                return null;
            }
        };
        if (throwableFields == fieldsCountOrDefault(cls, 0)) {
            Constructor<?>[] constructors = cls.getConstructors();
            ArrayList arrayList = new ArrayList(constructors.length);
            int length = constructors.length;
            int i = 0;
            while (true) {
                next = null;
                if (i >= length) {
                    break;
                }
                final Constructor<?> constructor = constructors[i];
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                int length2 = parameterTypes.length;
                if (length2 == 0) {
                    pair = TuplesKt.to(safeCtor(new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$$ExternalSyntheticLambda4
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return ExceptionsConstructorKt.createConstructor$lambda$7$lambda$6(constructor, (Throwable) obj);
                        }
                    }), 0);
                } else if (length2 == 1) {
                    Class<?> cls2 = parameterTypes[0];
                    if (Intrinsics.areEqual(cls2, String.class)) {
                        pair = TuplesKt.to(safeCtor(new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return ExceptionsConstructorKt.createConstructor$lambda$7$lambda$3(constructor, (Throwable) obj);
                            }
                        }), 2);
                    } else if (Intrinsics.areEqual(cls2, Throwable.class)) {
                        pair = TuplesKt.to(safeCtor(new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return ExceptionsConstructorKt.createConstructor$lambda$7$lambda$4(constructor, (Throwable) obj);
                            }
                        }), 1);
                    } else {
                        pair = TuplesKt.to(null, -1);
                    }
                } else if (length2 == 2) {
                    if (Intrinsics.areEqual(parameterTypes[0], String.class) && Intrinsics.areEqual(parameterTypes[1], Throwable.class)) {
                        pair = TuplesKt.to(safeCtor(new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                return ExceptionsConstructorKt.createConstructor$lambda$7$lambda$1(constructor, (Throwable) obj);
                            }
                        }), 3);
                    } else {
                        pair = TuplesKt.to(null, -1);
                    }
                } else {
                    pair = TuplesKt.to(null, -1);
                }
                arrayList.add(pair);
                i++;
            }
            Iterator it = arrayList.iterator();
            if (it.hasNext()) {
                next = it.next();
                if (it.hasNext()) {
                    int iIntValue = ((Number) ((Pair) next).getSecond()).intValue();
                    do {
                        Object next2 = it.next();
                        int iIntValue2 = ((Number) ((Pair) next2).getSecond()).intValue();
                        if (iIntValue < iIntValue2) {
                            next = next2;
                            iIntValue = iIntValue2;
                        }
                    } while (it.hasNext());
                }
            }
            Pair pair2 = (Pair) next;
            if (pair2 != null && (function1 = (Function1) pair2.getFirst()) != null) {
                return function1;
            }
        }
        return exceptionsConstructorKt$createConstructor$nullResult$1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$1(Constructor constructor, Throwable th) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object objNewInstance = constructor.newInstance(th.getMessage(), th);
        Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.Throwable");
        return (Throwable) objNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$3(Constructor constructor, Throwable th) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object objNewInstance = constructor.newInstance(th.getMessage());
        Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.Throwable");
        Throwable th2 = (Throwable) objNewInstance;
        th2.initCause(th);
        return th2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$4(Constructor constructor, Throwable th) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object objNewInstance = constructor.newInstance(th);
        Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.Throwable");
        return (Throwable) objNewInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable createConstructor$lambda$7$lambda$6(Constructor constructor, Throwable th) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object objNewInstance = constructor.newInstance(new Object[0]);
        Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type kotlin.Throwable");
        Throwable th2 = (Throwable) objNewInstance;
        th2.initCause(th);
        return th2;
    }

    private static final Function1<Throwable, Throwable> safeCtor(final Function1<? super Throwable, ? extends Throwable> function1) {
        return new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ExceptionsConstructorKt.safeCtor$lambda$9(function1, (Throwable) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable safeCtor$lambda$9(Function1 function1, Throwable th) {
        Object objM2532constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            Throwable th2 = (Throwable) function1.invoke(th);
            if (!Intrinsics.areEqual(th.getMessage(), th2.getMessage()) && !Intrinsics.areEqual(th2.getMessage(), th.toString())) {
                th2 = null;
            }
            objM2532constructorimpl = Result.m2532constructorimpl(th2);
        } catch (Throwable th3) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2532constructorimpl = Result.m2532constructorimpl(ResultKt.createFailure(th3));
        }
        return (Throwable) (Result.m2538isFailureimpl(objM2532constructorimpl) ? null : objM2532constructorimpl);
    }

    private static final int fieldsCountOrDefault(Class<?> cls, int i) {
        Object objM2532constructorimpl;
        JvmClassMappingKt.getKotlinClass(cls);
        try {
            Result.Companion companion = Result.INSTANCE;
            objM2532constructorimpl = Result.m2532constructorimpl(Integer.valueOf(fieldsCount$default(cls, 0, 1, null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2532constructorimpl = Result.m2532constructorimpl(ResultKt.createFailure(th));
        }
        Integer numValueOf = Integer.valueOf(i);
        if (Result.m2538isFailureimpl(objM2532constructorimpl)) {
            objM2532constructorimpl = numValueOf;
        }
        return ((Number) objM2532constructorimpl).intValue();
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }

    private static final int fieldsCount(Class<?> cls, int i) {
        do {
            int i2 = 0;
            for (Field field : cls.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    i2++;
                }
            }
            i += i2;
            cls = cls.getSuperclass();
        } while (cls != null);
        return i;
    }
}
