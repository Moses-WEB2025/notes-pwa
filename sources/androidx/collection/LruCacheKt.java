package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LruCache.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u001aû\u0001\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\b\b\u0000\u0010\u0004*\u00020\u0006\"\b\b\u0001\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b28\b\u0006\u0010\t\u001a2\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0013\u0012\u0011H\u0005¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\b0\n2%\b\u0006\u0010\u000f\u001a\u001f\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0006\u0012\u0004\u0018\u0001H\u00050\u00102d\b\u0006\u0010\u0011\u001a^\u0012\u0013\u0012\u00110\u0013¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u0014\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0013\u0012\u0011H\u0005¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u0015\u0012\u0015\u0012\u0013\u0018\u0001H\u0005¢\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00170\u0012H\u0086\bø\u0001\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0018"}, d2 = {"MAX_SIZE", "", "lruCache", "Landroidx/collection/LruCache;", "K", "V", "", "maxSize", "", "sizeOf", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "key", "value", "create", "Lkotlin/Function1;", "onEntryRemoved", "Lkotlin/Function4;", "", "evicted", "oldValue", "newValue", "", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LruCacheKt {
    private static final long MAX_SIZE = 2147483647L;

    public static /* synthetic */ LruCache lruCache$default(int i, Function2 sizeOf, Function1 create, Function4 onEntryRemoved, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            sizeOf = new Function2<K, V, Integer>() { // from class: androidx.collection.LruCacheKt.lruCache.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function2
                public final Integer invoke(K k, V v) {
                    Intrinsics.checkNotNullParameter(k, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(v, "<anonymous parameter 1>");
                    return 1;
                }
            };
        }
        if ((i2 & 4) != 0) {
            create = new Function1<K, V>() { // from class: androidx.collection.LruCacheKt.lruCache.2
                @Override // kotlin.jvm.functions.Function1
                public final V invoke(K it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return null;
                }
            };
        }
        if ((i2 & 8) != 0) {
            onEntryRemoved = new Function4<Boolean, K, V, V, Unit>() { // from class: androidx.collection.LruCacheKt.lruCache.3
                public final void invoke(boolean z, K k, V v, V v2) {
                    Intrinsics.checkNotNullParameter(k, "<anonymous parameter 1>");
                    Intrinsics.checkNotNullParameter(v, "<anonymous parameter 2>");
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool, Object obj2, Object obj3, Object obj4) {
                    invoke(bool.booleanValue(), obj2, obj3, obj4);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(sizeOf, "sizeOf");
        Intrinsics.checkNotNullParameter(create, "create");
        Intrinsics.checkNotNullParameter(onEntryRemoved, "onEntryRemoved");
        return new AnonymousClass4(i, sizeOf, create, onEntryRemoved);
    }

    /* JADX INFO: Add missing generic type declarations: [V, K] */
    /* JADX INFO: renamed from: androidx.collection.LruCacheKt$lruCache$4, reason: invalid class name */
    /* JADX INFO: compiled from: LruCache.kt */
    @Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0001J\u0017\u0010\u0002\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0003\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0004J/\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\t\u001a\u00028\u00012\b\u0010\n\u001a\u0004\u0018\u00018\u0001H\u0014¢\u0006\u0002\u0010\u000bJ\u001d\u0010\f\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u0001H\u0014¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"androidx/collection/LruCacheKt$lruCache$4", "Landroidx/collection/LruCache;", "create", "key", "(Ljava/lang/Object;)Ljava/lang/Object;", "entryRemoved", "", "evicted", "", "oldValue", "newValue", "(ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "sizeOf", "", "value", "(Ljava/lang/Object;Ljava/lang/Object;)I", "collection"}, k = 1, mv = {1, 9, 0}, xi = 176)
    public static final class AnonymousClass4<K, V> extends LruCache<K, V> {
        final /* synthetic */ Function1<K, V> $create;
        final /* synthetic */ Function4<Boolean, K, V, V, Unit> $onEntryRemoved;
        final /* synthetic */ Function2<K, V, Integer> $sizeOf;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass4(int i, Function2<? super K, ? super V, Integer> function2, Function1<? super K, ? extends V> function1, Function4<? super Boolean, ? super K, ? super V, ? super V, Unit> function4) {
            super(i);
            this.$sizeOf = function2;
            this.$create = function1;
            this.$onEntryRemoved = function4;
        }

        @Override // androidx.collection.LruCache
        protected int sizeOf(K key, V value) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(value, "value");
            return this.$sizeOf.invoke(key, value).intValue();
        }

        @Override // androidx.collection.LruCache
        protected V create(K key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return this.$create.invoke(key);
        }

        @Override // androidx.collection.LruCache
        protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(oldValue, "oldValue");
            this.$onEntryRemoved.invoke(Boolean.valueOf(evicted), key, oldValue, newValue);
        }
    }

    public static final <K, V> LruCache<K, V> lruCache(int i, Function2<? super K, ? super V, Integer> sizeOf, Function1<? super K, ? extends V> create, Function4<? super Boolean, ? super K, ? super V, ? super V, Unit> onEntryRemoved) {
        Intrinsics.checkNotNullParameter(sizeOf, "sizeOf");
        Intrinsics.checkNotNullParameter(create, "create");
        Intrinsics.checkNotNullParameter(onEntryRemoved, "onEntryRemoved");
        return new AnonymousClass4(i, sizeOf, create, onEntryRemoved);
    }
}
