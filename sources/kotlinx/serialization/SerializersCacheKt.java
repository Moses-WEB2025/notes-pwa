package kotlinx.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlinx.serialization.builtins.BuiltinSerializersKt;
import kotlinx.serialization.internal.CachingKt;
import kotlinx.serialization.internal.ParametrizedSerializerCache;
import kotlinx.serialization.internal.PlatformKt;
import kotlinx.serialization.internal.SerializerCache;
import kotlinx.serialization.modules.SerializersModuleBuildersKt;

/* JADX INFO: compiled from: SerializersCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u000e\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0000\u001aA\u0010\u0014\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u000f0\u00152\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u00112\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0000¢\u0006\u0002\u0010\u0019\u001a\u001b\u0010\u001a\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0002\u0018\u00010\u001b*\u0006\u0012\u0002\b\u00030\u0011H\u0080\b\"$\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00018\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00018\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0004\"\u001e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\n8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\u0004\"\u001e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\n8\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u0004¨\u0006\u001c"}, d2 = {"SERIALIZERS_CACHE", "Lkotlinx/serialization/internal/SerializerCache;", "", "getSERIALIZERS_CACHE$annotations", "()V", "getSERIALIZERS_CACHE", "()Lkotlinx/serialization/internal/SerializerCache;", "SERIALIZERS_CACHE_NULLABLE", "getSERIALIZERS_CACHE_NULLABLE$annotations", "PARAMETRIZED_SERIALIZERS_CACHE", "Lkotlinx/serialization/internal/ParametrizedSerializerCache;", "getPARAMETRIZED_SERIALIZERS_CACHE$annotations", "PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE", "getPARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$annotations", "findCachedSerializer", "Lkotlinx/serialization/KSerializer;", "clazz", "Lkotlin/reflect/KClass;", "isNullable", "", "findParametrizedCachedSerializer", "Lkotlin/Result;", "types", "", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KClass;Ljava/util/List;Z)Ljava/lang/Object;", "polymorphicIfInterface", "Lkotlinx/serialization/PolymorphicSerializer;", "kotlinx-serialization-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
public final class SerializersCacheKt {
    private static final SerializerCache<? extends Object> SERIALIZERS_CACHE = CachingKt.createCache(new Function1() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return SerializersCacheKt.SERIALIZERS_CACHE$lambda$0((KClass) obj);
        }
    });
    private static final SerializerCache<Object> SERIALIZERS_CACHE_NULLABLE = CachingKt.createCache(new Function1() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return SerializersCacheKt.SERIALIZERS_CACHE_NULLABLE$lambda$1((KClass) obj);
        }
    });
    private static final ParametrizedSerializerCache<? extends Object> PARAMETRIZED_SERIALIZERS_CACHE = CachingKt.createParametrizedCache(new Function2() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return SerializersCacheKt.PARAMETRIZED_SERIALIZERS_CACHE$lambda$3((KClass) obj, (List) obj2);
        }
    });
    private static final ParametrizedSerializerCache<Object> PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE = CachingKt.createParametrizedCache(new Function2() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return SerializersCacheKt.PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$lambda$5((KClass) obj, (List) obj2);
        }
    });

    private static /* synthetic */ void getPARAMETRIZED_SERIALIZERS_CACHE$annotations() {
    }

    private static /* synthetic */ void getPARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$annotations() {
    }

    public static /* synthetic */ void getSERIALIZERS_CACHE$annotations() {
    }

    private static /* synthetic */ void getSERIALIZERS_CACHE_NULLABLE$annotations() {
    }

    public static final SerializerCache<? extends Object> getSERIALIZERS_CACHE() {
        return SERIALIZERS_CACHE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KSerializer SERIALIZERS_CACHE$lambda$0(KClass it) {
        Intrinsics.checkNotNullParameter(it, "it");
        KSerializer kSerializerSerializerOrNull = SerializersKt.serializerOrNull(it);
        if (kSerializerSerializerOrNull == null) {
            return PlatformKt.isInterface(it) ? new PolymorphicSerializer(it) : null;
        }
        return kSerializerSerializerOrNull;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KSerializer SERIALIZERS_CACHE_NULLABLE$lambda$1(KClass it) {
        KSerializer nullable;
        Intrinsics.checkNotNullParameter(it, "it");
        PolymorphicSerializer polymorphicSerializerSerializerOrNull = SerializersKt.serializerOrNull(it);
        if (polymorphicSerializerSerializerOrNull == null) {
            polymorphicSerializerSerializerOrNull = PlatformKt.isInterface(it) ? new PolymorphicSerializer(it) : null;
        }
        if (polymorphicSerializerSerializerOrNull == null || (nullable = BuiltinSerializersKt.getNullable(polymorphicSerializerSerializerOrNull)) == null) {
            return null;
        }
        return nullable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KSerializer PARAMETRIZED_SERIALIZERS_CACHE$lambda$3(KClass clazz, final List types) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(types, "types");
        List<KSerializer<Object>> listSerializersForParameters = SerializersKt.serializersForParameters(SerializersModuleBuildersKt.EmptySerializersModule(), types, true);
        Intrinsics.checkNotNull(listSerializersForParameters);
        return SerializersKt.parametrizedSerializerOrNull(clazz, listSerializersForParameters, new Function0() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SerializersCacheKt.PARAMETRIZED_SERIALIZERS_CACHE$lambda$3$lambda$2(types);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KClassifier PARAMETRIZED_SERIALIZERS_CACHE$lambda$3$lambda$2(List list) {
        return ((KType) list.get(0)).getClassifier();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KSerializer PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$lambda$5(KClass clazz, final List types) {
        KSerializer nullable;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(types, "types");
        List<KSerializer<Object>> listSerializersForParameters = SerializersKt.serializersForParameters(SerializersModuleBuildersKt.EmptySerializersModule(), types, true);
        Intrinsics.checkNotNull(listSerializersForParameters);
        KSerializer<? extends Object> kSerializerParametrizedSerializerOrNull = SerializersKt.parametrizedSerializerOrNull(clazz, listSerializersForParameters, new Function0() { // from class: kotlinx.serialization.SerializersCacheKt$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SerializersCacheKt.PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$lambda$5$lambda$4(types);
            }
        });
        if (kSerializerParametrizedSerializerOrNull == null || (nullable = BuiltinSerializersKt.getNullable(kSerializerParametrizedSerializerOrNull)) == null) {
            return null;
        }
        return nullable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KClassifier PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE$lambda$5$lambda$4(List list) {
        return ((KType) list.get(0)).getClassifier();
    }

    public static final KSerializer<Object> findCachedSerializer(KClass<Object> clazz, boolean z) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        if (!z) {
            KSerializer<? extends Object> kSerializer = SERIALIZERS_CACHE.get(clazz);
            if (kSerializer != null) {
                return kSerializer;
            }
            return null;
        }
        return SERIALIZERS_CACHE_NULLABLE.get(clazz);
    }

    public static final Object findParametrizedCachedSerializer(KClass<Object> clazz, List<? extends KType> types, boolean z) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(types, "types");
        if (!z) {
            return PARAMETRIZED_SERIALIZERS_CACHE.mo4142getgIAlus(clazz, types);
        }
        return PARAMETRIZED_SERIALIZERS_CACHE_NULLABLE.mo4142getgIAlus(clazz, types);
    }

    public static final PolymorphicSerializer<? extends Object> polymorphicIfInterface(KClass<?> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        if (PlatformKt.isInterface(kClass)) {
            return new PolymorphicSerializer<>(kClass);
        }
        return null;
    }
}
