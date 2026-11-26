package com.miui.aiengine.common.request;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KVPair.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00028\u0001¢\u0006\u0004\b\u0006\u0010\u0007J\u000e\u0010\f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\tJ\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\tJ.\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00002\b\b\u0002\u0010\u0005\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u000b\u0010\t¨\u0006\u0017"}, d2 = {"Lcom/miui/aiengine/common/request/KVPair;", "K", "V", "", "key", "value", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getValue", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/miui/aiengine/common/request/KVPair;", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "net_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class KVPair<K, V> {
    private final K key;
    private final V value;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ KVPair copy$default(KVPair kVPair, Object obj, Object obj2, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = kVPair.key;
        }
        if ((i & 2) != 0) {
            obj2 = kVPair.value;
        }
        return kVPair.copy(obj, obj2);
    }

    public final K component1() {
        return this.key;
    }

    public final V component2() {
        return this.value;
    }

    public final KVPair<K, V> copy(K key, V value) {
        return new KVPair<>(key, value);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KVPair)) {
            return false;
        }
        KVPair kVPair = (KVPair) other;
        return Intrinsics.areEqual(this.key, kVPair.key) && Intrinsics.areEqual(this.value, kVPair.value);
    }

    public int hashCode() {
        K k = this.key;
        int iHashCode = (k == null ? 0 : k.hashCode()) * 31;
        V v = this.value;
        return iHashCode + (v != null ? v.hashCode() : 0);
    }

    public String toString() {
        return "KVPair(key=" + this.key + ", value=" + this.value + ")";
    }

    public KVPair(K k, V v) {
        this.key = k;
        this.value = v;
    }

    public final K getKey() {
        return this.key;
    }

    public final V getValue() {
        return this.value;
    }
}
