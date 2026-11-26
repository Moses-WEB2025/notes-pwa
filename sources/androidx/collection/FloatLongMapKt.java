package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FloatLongMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\n\u001a2\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\n\u001a\u00020\u0003\u001a\u0006\u0010\u000b\u001a\u00020\u0003\u001a\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a&\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f\u001a6\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f\u001aF\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f\u001aV\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u000f\u001a\u0006\u0010\u0018\u001a\u00020\u0001\u001a\u0016\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a&\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f\u001a6\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f\u001aF\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f\u001aV\u0010\u0018\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u000f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0019"}, d2 = {"EmptyFloatLongMap", "Landroidx/collection/MutableFloatLongMap;", "buildFloatLongMap", "Landroidx/collection/FloatLongMap;", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "emptyFloatLongMap", "floatLongMapOf", "key1", "", "value1", "", "key2", "value2", "key3", "value3", "key4", "value4", "key5", "value5", "mutableFloatLongMapOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FloatLongMapKt {
    private static final MutableFloatLongMap EmptyFloatLongMap = new MutableFloatLongMap(0);

    public static final FloatLongMap emptyFloatLongMap() {
        return EmptyFloatLongMap;
    }

    public static final FloatLongMap floatLongMapOf() {
        return EmptyFloatLongMap;
    }

    public static final FloatLongMap floatLongMapOf(float f, long j) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        return mutableFloatLongMap;
    }

    public static final FloatLongMap floatLongMapOf(float f, long j, float f2, long j2) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        return mutableFloatLongMap;
    }

    public static final FloatLongMap floatLongMapOf(float f, long j, float f2, long j2, float f3, long j3) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        mutableFloatLongMap.set(f3, j3);
        return mutableFloatLongMap;
    }

    public static final FloatLongMap floatLongMapOf(float f, long j, float f2, long j2, float f3, long j3, float f4, long j4) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        mutableFloatLongMap.set(f3, j3);
        mutableFloatLongMap.set(f4, j4);
        return mutableFloatLongMap;
    }

    public static final FloatLongMap floatLongMapOf(float f, long j, float f2, long j2, float f3, long j3, float f4, long j4, float f5, long j5) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        mutableFloatLongMap.set(f3, j3);
        mutableFloatLongMap.set(f4, j4);
        mutableFloatLongMap.set(f5, j5);
        return mutableFloatLongMap;
    }

    public static final MutableFloatLongMap mutableFloatLongMapOf() {
        return new MutableFloatLongMap(0, 1, null);
    }

    public static final MutableFloatLongMap mutableFloatLongMapOf(float f, long j) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        return mutableFloatLongMap;
    }

    public static final MutableFloatLongMap mutableFloatLongMapOf(float f, long j, float f2, long j2) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        return mutableFloatLongMap;
    }

    public static final MutableFloatLongMap mutableFloatLongMapOf(float f, long j, float f2, long j2, float f3, long j3) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        mutableFloatLongMap.set(f3, j3);
        return mutableFloatLongMap;
    }

    public static final MutableFloatLongMap mutableFloatLongMapOf(float f, long j, float f2, long j2, float f3, long j3, float f4, long j4) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        mutableFloatLongMap.set(f3, j3);
        mutableFloatLongMap.set(f4, j4);
        return mutableFloatLongMap;
    }

    public static final MutableFloatLongMap mutableFloatLongMapOf(float f, long j, float f2, long j2, float f3, long j3, float f4, long j4, float f5, long j5) {
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        mutableFloatLongMap.set(f, j);
        mutableFloatLongMap.set(f2, j2);
        mutableFloatLongMap.set(f3, j3);
        mutableFloatLongMap.set(f4, j4);
        mutableFloatLongMap.set(f5, j5);
        return mutableFloatLongMap;
    }

    public static final FloatLongMap buildFloatLongMap(Function1<? super MutableFloatLongMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(0, 1, null);
        builderAction.invoke(mutableFloatLongMap);
        return mutableFloatLongMap;
    }

    public static final FloatLongMap buildFloatLongMap(int i, Function1<? super MutableFloatLongMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableFloatLongMap mutableFloatLongMap = new MutableFloatLongMap(i);
        builderAction.invoke(mutableFloatLongMap);
        return mutableFloatLongMap;
    }
}
