package kotlinx.coroutines.internal;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExceptionsConstructor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000'\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0005\bÃ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J*\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\t0\bj\u0002`\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\fH\u0016R\u0010\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\r"}, d2 = {"Lkotlinx/coroutines/internal/ClassValueCtorCache;", "Lkotlinx/coroutines/internal/CtorCache;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "cache", "kotlinx/coroutines/internal/ClassValueCtorCache$cache$1", "Lkotlinx/coroutines/internal/ClassValueCtorCache$cache$1;", CallMethod.METHOD_GET, "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/Ctor;", "key", "Ljava/lang/Class;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class ClassValueCtorCache extends CtorCache {
    public static final ClassValueCtorCache INSTANCE = new ClassValueCtorCache();
    private static final ClassValueCtorCache$cache$1 cache = new ClassValue<Function1<? super Throwable, ? extends Throwable>>() { // from class: kotlinx.coroutines.internal.ClassValueCtorCache$cache$1
        @Override // java.lang.ClassValue
        public /* bridge */ /* synthetic */ Function1<? super Throwable, ? extends Throwable> computeValue(Class cls) {
            return computeValue2((Class<?>) cls);
        }

        @Override // java.lang.ClassValue
        /* JADX INFO: renamed from: computeValue, reason: avoid collision after fix types in other method */
        protected Function1<? super Throwable, ? extends Throwable> computeValue2(Class<?> type) {
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type java.lang.Class<out kotlin.Throwable>");
            return ExceptionsConstructorKt.createConstructor(type);
        }
    };

    private ClassValueCtorCache() {
    }

    @Override // kotlinx.coroutines.internal.CtorCache
    public Function1<Throwable, Throwable> get(Class<? extends Throwable> key) {
        return (Function1) cache.get(key);
    }
}
