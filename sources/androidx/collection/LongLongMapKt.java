package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LongLongMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000b\u001a2\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\n\u001a\u00020\u0003\u001a\u0006\u0010\u000b\u001a\u00020\u0003\u001a\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r\u001a&\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r\u001a6\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r\u001aF\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r\u001aV\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\r\u001a\u0006\u0010\u0017\u001a\u00020\u0001\u001a\u0016\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r\u001a&\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r\u001a6\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r\u001aF\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r\u001aV\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\r\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0018"}, d2 = {"EmptyLongLongMap", "Landroidx/collection/MutableLongLongMap;", "buildLongLongMap", "Landroidx/collection/LongLongMap;", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "emptyLongLongMap", "longLongMapOf", "key1", "", "value1", "key2", "value2", "key3", "value3", "key4", "value4", "key5", "value5", "mutableLongLongMapOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LongLongMapKt {
    private static final MutableLongLongMap EmptyLongLongMap = new MutableLongLongMap(0);

    public static final LongLongMap emptyLongLongMap() {
        return EmptyLongLongMap;
    }

    public static final LongLongMap longLongMapOf() {
        return EmptyLongLongMap;
    }

    public static final LongLongMap longLongMapOf(long j, long j2) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        return mutableLongLongMap;
    }

    public static final LongLongMap longLongMapOf(long j, long j2, long j3, long j4) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        return mutableLongLongMap;
    }

    public static final LongLongMap longLongMapOf(long j, long j2, long j3, long j4, long j5, long j6) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        mutableLongLongMap.set(j5, j6);
        return mutableLongLongMap;
    }

    public static final LongLongMap longLongMapOf(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        mutableLongLongMap.set(j5, j6);
        mutableLongLongMap.set(j7, j8);
        return mutableLongLongMap;
    }

    public static final LongLongMap longLongMapOf(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        mutableLongLongMap.set(j5, j6);
        mutableLongLongMap.set(j7, j8);
        mutableLongLongMap.set(j9, j10);
        return mutableLongLongMap;
    }

    public static final MutableLongLongMap mutableLongLongMapOf() {
        return new MutableLongLongMap(0, 1, null);
    }

    public static final MutableLongLongMap mutableLongLongMapOf(long j, long j2) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        return mutableLongLongMap;
    }

    public static final MutableLongLongMap mutableLongLongMapOf(long j, long j2, long j3, long j4) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        return mutableLongLongMap;
    }

    public static final MutableLongLongMap mutableLongLongMapOf(long j, long j2, long j3, long j4, long j5, long j6) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        mutableLongLongMap.set(j5, j6);
        return mutableLongLongMap;
    }

    public static final MutableLongLongMap mutableLongLongMapOf(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        mutableLongLongMap.set(j5, j6);
        mutableLongLongMap.set(j7, j8);
        return mutableLongLongMap;
    }

    public static final MutableLongLongMap mutableLongLongMapOf(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        mutableLongLongMap.set(j, j2);
        mutableLongLongMap.set(j3, j4);
        mutableLongLongMap.set(j5, j6);
        mutableLongLongMap.set(j7, j8);
        mutableLongLongMap.set(j9, j10);
        return mutableLongLongMap;
    }

    public static final LongLongMap buildLongLongMap(Function1<? super MutableLongLongMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(0, 1, null);
        builderAction.invoke(mutableLongLongMap);
        return mutableLongLongMap;
    }

    public static final LongLongMap buildLongLongMap(int i, Function1<? super MutableLongLongMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableLongLongMap mutableLongLongMap = new MutableLongLongMap(i);
        builderAction.invoke(mutableLongLongMap);
        return mutableLongLongMap;
    }
}
