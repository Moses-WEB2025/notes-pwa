package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LongFloatMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\u001a2\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\n\u001a\u00020\u0003\u001a\u0006\u0010\u000b\u001a\u00020\u0003\u001a\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a&\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f\u001a6\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f\u001aF\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f\u001aV\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u000f\u001a\u0006\u0010\u0018\u001a\u00020\u0001\u001a\u0016\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a&\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f\u001a6\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f\u001aF\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f\u001aV\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u000f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0019"}, d2 = {"EmptyLongFloatMap", "Landroidx/collection/MutableLongFloatMap;", "buildLongFloatMap", "Landroidx/collection/LongFloatMap;", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "emptyLongFloatMap", "longFloatMapOf", "key1", "", "value1", "", "key2", "value2", "key3", "value3", "key4", "value4", "key5", "value5", "mutableLongFloatMapOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LongFloatMapKt {
    private static final MutableLongFloatMap EmptyLongFloatMap = new MutableLongFloatMap(0);

    public static final LongFloatMap emptyLongFloatMap() {
        return EmptyLongFloatMap;
    }

    public static final LongFloatMap longFloatMapOf() {
        return EmptyLongFloatMap;
    }

    public static final LongFloatMap longFloatMapOf(long j, float f) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        return mutableLongFloatMap;
    }

    public static final LongFloatMap longFloatMapOf(long j, float f, long j2, float f2) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        return mutableLongFloatMap;
    }

    public static final LongFloatMap longFloatMapOf(long j, float f, long j2, float f2, long j3, float f3) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        mutableLongFloatMap.set(j3, f3);
        return mutableLongFloatMap;
    }

    public static final LongFloatMap longFloatMapOf(long j, float f, long j2, float f2, long j3, float f3, long j4, float f4) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        mutableLongFloatMap.set(j3, f3);
        mutableLongFloatMap.set(j4, f4);
        return mutableLongFloatMap;
    }

    public static final LongFloatMap longFloatMapOf(long j, float f, long j2, float f2, long j3, float f3, long j4, float f4, long j5, float f5) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        mutableLongFloatMap.set(j3, f3);
        mutableLongFloatMap.set(j4, f4);
        mutableLongFloatMap.set(j5, f5);
        return mutableLongFloatMap;
    }

    public static final MutableLongFloatMap mutableLongFloatMapOf() {
        return new MutableLongFloatMap(0, 1, null);
    }

    public static final MutableLongFloatMap mutableLongFloatMapOf(long j, float f) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        return mutableLongFloatMap;
    }

    public static final MutableLongFloatMap mutableLongFloatMapOf(long j, float f, long j2, float f2) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        return mutableLongFloatMap;
    }

    public static final MutableLongFloatMap mutableLongFloatMapOf(long j, float f, long j2, float f2, long j3, float f3) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        mutableLongFloatMap.set(j3, f3);
        return mutableLongFloatMap;
    }

    public static final MutableLongFloatMap mutableLongFloatMapOf(long j, float f, long j2, float f2, long j3, float f3, long j4, float f4) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        mutableLongFloatMap.set(j3, f3);
        mutableLongFloatMap.set(j4, f4);
        return mutableLongFloatMap;
    }

    public static final MutableLongFloatMap mutableLongFloatMapOf(long j, float f, long j2, float f2, long j3, float f3, long j4, float f4, long j5, float f5) {
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        mutableLongFloatMap.set(j, f);
        mutableLongFloatMap.set(j2, f2);
        mutableLongFloatMap.set(j3, f3);
        mutableLongFloatMap.set(j4, f4);
        mutableLongFloatMap.set(j5, f5);
        return mutableLongFloatMap;
    }

    public static final LongFloatMap buildLongFloatMap(Function1<? super MutableLongFloatMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(0, 1, null);
        builderAction.invoke(mutableLongFloatMap);
        return mutableLongFloatMap;
    }

    public static final LongFloatMap buildLongFloatMap(int i, Function1<? super MutableLongFloatMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableLongFloatMap mutableLongFloatMap = new MutableLongFloatMap(i);
        builderAction.invoke(mutableLongFloatMap);
        return mutableLongFloatMap;
    }
}
