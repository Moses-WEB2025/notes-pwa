package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FloatList.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0002\u001a2\u0010\u0002\u001a\u00020\u00012\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0002\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\n\u001a\u00020\u0001\u001a\u0006\u0010\u000b\u001a\u00020\u0001\u001a\u000e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r\u001a\u0016\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r\u001a\u001e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r\u001a\u0012\u0010\u000b\u001a\u00020\u00012\n\u0010\u0010\u001a\u00020\u0011\"\u00020\r\u001a\t\u0010\u0012\u001a\u00020\u0005H\u0086\b\u001a\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r\u001a\u0016\u0010\u0012\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r\u001a\u001e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r\u001a\u0015\u0010\u0012\u001a\u00020\u00052\n\u0010\u0010\u001a\u00020\u0011\"\u00020\rH\u0086\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"EmptyFloatList", "Landroidx/collection/FloatList;", "buildFloatList", "builderAction", "Lkotlin/Function1;", "Landroidx/collection/MutableFloatList;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "emptyFloatList", "floatListOf", "element1", "", "element2", "element3", "elements", "", "mutableFloatListOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FloatListKt {
    private static final FloatList EmptyFloatList = new MutableFloatList(0);

    public static final FloatList emptyFloatList() {
        return EmptyFloatList;
    }

    public static final FloatList floatListOf() {
        return EmptyFloatList;
    }

    public static final FloatList floatListOf(float f) {
        return mutableFloatListOf(f);
    }

    public static final FloatList floatListOf(float f, float f2) {
        return mutableFloatListOf(f, f2);
    }

    public static final FloatList floatListOf(float f, float f2, float f3) {
        return mutableFloatListOf(f, f2, f3);
    }

    public static final FloatList floatListOf(float... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableFloatList mutableFloatList = new MutableFloatList(elements.length);
        mutableFloatList.addAll(mutableFloatList._size, elements);
        return mutableFloatList;
    }

    public static final MutableFloatList mutableFloatListOf() {
        return new MutableFloatList(0, 1, null);
    }

    public static final MutableFloatList mutableFloatListOf(float f) {
        MutableFloatList mutableFloatList = new MutableFloatList(1);
        mutableFloatList.add(f);
        return mutableFloatList;
    }

    public static final MutableFloatList mutableFloatListOf(float f, float f2) {
        MutableFloatList mutableFloatList = new MutableFloatList(2);
        mutableFloatList.add(f);
        mutableFloatList.add(f2);
        return mutableFloatList;
    }

    public static final MutableFloatList mutableFloatListOf(float f, float f2, float f3) {
        MutableFloatList mutableFloatList = new MutableFloatList(3);
        mutableFloatList.add(f);
        mutableFloatList.add(f2);
        mutableFloatList.add(f3);
        return mutableFloatList;
    }

    public static final MutableFloatList mutableFloatListOf(float... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableFloatList mutableFloatList = new MutableFloatList(elements.length);
        mutableFloatList.addAll(mutableFloatList._size, elements);
        return mutableFloatList;
    }

    public static final FloatList buildFloatList(Function1<? super MutableFloatList, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableFloatList mutableFloatList = new MutableFloatList(0, 1, null);
        builderAction.invoke(mutableFloatList);
        return mutableFloatList;
    }

    public static final FloatList buildFloatList(int i, Function1<? super MutableFloatList, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableFloatList mutableFloatList = new MutableFloatList(i);
        builderAction.invoke(mutableFloatList);
        return mutableFloatList;
    }
}
