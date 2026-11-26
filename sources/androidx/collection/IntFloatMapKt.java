package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IntFloatMap.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\n\u001a2\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\n\u001a\u00020\u0003\u001a\u0006\u0010\u000b\u001a\u00020\u0003\u001a\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a&\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e\u001a6\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000e\u001aF\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u000e\u001aV\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000e\u001a\u0006\u0010\u0017\u001a\u00020\u0001\u001a\u0016\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e\u001a&\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e\u001a6\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000e\u001aF\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u000e\u001aV\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000e\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0018"}, d2 = {"EmptyIntFloatMap", "Landroidx/collection/MutableIntFloatMap;", "buildIntFloatMap", "Landroidx/collection/IntFloatMap;", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "emptyIntFloatMap", "intFloatMapOf", "key1", "value1", "", "key2", "value2", "key3", "value3", "key4", "value4", "key5", "value5", "mutableIntFloatMapOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntFloatMapKt {
    private static final MutableIntFloatMap EmptyIntFloatMap = new MutableIntFloatMap(0);

    public static final IntFloatMap emptyIntFloatMap() {
        return EmptyIntFloatMap;
    }

    public static final IntFloatMap intFloatMapOf() {
        return EmptyIntFloatMap;
    }

    public static final IntFloatMap intFloatMapOf(int i, float f) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        return mutableIntFloatMap;
    }

    public static final IntFloatMap intFloatMapOf(int i, float f, int i2, float f2) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        return mutableIntFloatMap;
    }

    public static final IntFloatMap intFloatMapOf(int i, float f, int i2, float f2, int i3, float f3) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        mutableIntFloatMap.set(i3, f3);
        return mutableIntFloatMap;
    }

    public static final IntFloatMap intFloatMapOf(int i, float f, int i2, float f2, int i3, float f3, int i4, float f4) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        mutableIntFloatMap.set(i3, f3);
        mutableIntFloatMap.set(i4, f4);
        return mutableIntFloatMap;
    }

    public static final IntFloatMap intFloatMapOf(int i, float f, int i2, float f2, int i3, float f3, int i4, float f4, int i5, float f5) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        mutableIntFloatMap.set(i3, f3);
        mutableIntFloatMap.set(i4, f4);
        mutableIntFloatMap.set(i5, f5);
        return mutableIntFloatMap;
    }

    public static final MutableIntFloatMap mutableIntFloatMapOf() {
        return new MutableIntFloatMap(0, 1, null);
    }

    public static final MutableIntFloatMap mutableIntFloatMapOf(int i, float f) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        return mutableIntFloatMap;
    }

    public static final MutableIntFloatMap mutableIntFloatMapOf(int i, float f, int i2, float f2) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        return mutableIntFloatMap;
    }

    public static final MutableIntFloatMap mutableIntFloatMapOf(int i, float f, int i2, float f2, int i3, float f3) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        mutableIntFloatMap.set(i3, f3);
        return mutableIntFloatMap;
    }

    public static final MutableIntFloatMap mutableIntFloatMapOf(int i, float f, int i2, float f2, int i3, float f3, int i4, float f4) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        mutableIntFloatMap.set(i3, f3);
        mutableIntFloatMap.set(i4, f4);
        return mutableIntFloatMap;
    }

    public static final MutableIntFloatMap mutableIntFloatMapOf(int i, float f, int i2, float f2, int i3, float f3, int i4, float f4, int i5, float f5) {
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        mutableIntFloatMap.set(i, f);
        mutableIntFloatMap.set(i2, f2);
        mutableIntFloatMap.set(i3, f3);
        mutableIntFloatMap.set(i4, f4);
        mutableIntFloatMap.set(i5, f5);
        return mutableIntFloatMap;
    }

    public static final IntFloatMap buildIntFloatMap(Function1<? super MutableIntFloatMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(0, 1, null);
        builderAction.invoke(mutableIntFloatMap);
        return mutableIntFloatMap;
    }

    public static final IntFloatMap buildIntFloatMap(int i, Function1<? super MutableIntFloatMap, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableIntFloatMap mutableIntFloatMap = new MutableIntFloatMap(i);
        builderAction.invoke(mutableIntFloatMap);
        return mutableIntFloatMap;
    }
}
