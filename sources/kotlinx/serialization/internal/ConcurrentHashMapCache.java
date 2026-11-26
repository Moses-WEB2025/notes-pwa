package kotlinx.serialization.internal;

import androidx.exifinterface.media.ExifInterface;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;

/* JADX INFO: compiled from: Caching.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012\u001e\u0010\u0003\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00060\u0004¢\u0006\u0004\b\u0007\u0010\bJ\u001e\u0010\r\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0005H\u0016J\u0014\u0010\u0010\u001a\u00020\u00112\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016R&\u0010\u0003\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\t\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lkotlinx/serialization/internal/ConcurrentHashMapCache;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/serialization/internal/SerializerCache;", "compute", "Lkotlin/Function1;", "Lkotlin/reflect/KClass;", "Lkotlinx/serialization/KSerializer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlin/jvm/functions/Function1;)V", "cache", "Ljava/util/concurrent/ConcurrentHashMap;", "Ljava/lang/Class;", "Lkotlinx/serialization/internal/CacheEntry;", CallMethod.METHOD_GET, "key", "", "isStored", "", "kotlinx-serialization-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
final class ConcurrentHashMapCache<T> implements SerializerCache<T> {
    private final ConcurrentHashMap<Class<?>, CacheEntry<T>> cache;
    private final Function1<KClass<?>, KSerializer<T>> compute;

    /* JADX WARN: Multi-variable type inference failed */
    public ConcurrentHashMapCache(Function1<? super KClass<?>, ? extends KSerializer<T>> compute) {
        Intrinsics.checkNotNullParameter(compute, "compute");
        this.compute = compute;
        this.cache = new ConcurrentHashMap<>();
    }

    @Override // kotlinx.serialization.internal.SerializerCache
    public KSerializer<T> get(KClass<Object> key) {
        CacheEntry<T> cacheEntryPutIfAbsent;
        Intrinsics.checkNotNullParameter(key, "key");
        ConcurrentHashMap<Class<?>, CacheEntry<T>> concurrentHashMap = this.cache;
        Class<?> javaClass = JvmClassMappingKt.getJavaClass((KClass) key);
        CacheEntry<T> cacheEntry = concurrentHashMap.get(javaClass);
        if (cacheEntry == null && (cacheEntryPutIfAbsent = concurrentHashMap.putIfAbsent(javaClass, (cacheEntry = new CacheEntry<>(this.compute.invoke(key))))) != null) {
            cacheEntry = cacheEntryPutIfAbsent;
        }
        return cacheEntry.serializer;
    }

    @Override // kotlinx.serialization.internal.SerializerCache
    public boolean isStored(KClass<?> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.cache.containsKey(JvmClassMappingKt.getJavaClass((KClass) key));
    }
}
