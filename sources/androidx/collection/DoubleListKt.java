package androidx.collection;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoubleList.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0013\n\u0002\b\u0003\u001a2\u0010\u0002\u001a\u00020\u00012\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0002\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\b\u0007H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\n\u001a\u00020\u0001\u001a\u000e\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f\u001a\u0016\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f\u001a\u001e\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f\u001a\u0012\u0010\n\u001a\u00020\u00012\n\u0010\u000f\u001a\u00020\u0010\"\u00020\f\u001a\u0006\u0010\u0011\u001a\u00020\u0001\u001a\t\u0010\u0012\u001a\u00020\u0005H\u0086\b\u001a\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f\u001a\u0016\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f\u001a\u001e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f\u001a\u0015\u0010\u0012\u001a\u00020\u00052\n\u0010\u000f\u001a\u00020\u0010\"\u00020\fH\u0086\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"EmptyDoubleList", "Landroidx/collection/DoubleList;", "buildDoubleList", "builderAction", "Lkotlin/Function1;", "Landroidx/collection/MutableDoubleList;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "doubleListOf", "element1", "", "element2", "element3", "elements", "", "emptyDoubleList", "mutableDoubleListOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DoubleListKt {
    private static final DoubleList EmptyDoubleList = new MutableDoubleList(0);

    public static final DoubleList emptyDoubleList() {
        return EmptyDoubleList;
    }

    public static final DoubleList doubleListOf() {
        return EmptyDoubleList;
    }

    public static final DoubleList doubleListOf(double d) {
        return mutableDoubleListOf(d);
    }

    public static final DoubleList doubleListOf(double d, double d2) {
        return mutableDoubleListOf(d, d2);
    }

    public static final DoubleList doubleListOf(double d, double d2, double d3) {
        return mutableDoubleListOf(d, d2, d3);
    }

    public static final DoubleList doubleListOf(double... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableDoubleList mutableDoubleList = new MutableDoubleList(elements.length);
        mutableDoubleList.addAll(mutableDoubleList._size, elements);
        return mutableDoubleList;
    }

    public static final MutableDoubleList mutableDoubleListOf() {
        return new MutableDoubleList(0, 1, null);
    }

    public static final MutableDoubleList mutableDoubleListOf(double d) {
        MutableDoubleList mutableDoubleList = new MutableDoubleList(1);
        mutableDoubleList.add(d);
        return mutableDoubleList;
    }

    public static final MutableDoubleList mutableDoubleListOf(double d, double d2) {
        MutableDoubleList mutableDoubleList = new MutableDoubleList(2);
        mutableDoubleList.add(d);
        mutableDoubleList.add(d2);
        return mutableDoubleList;
    }

    public static final MutableDoubleList mutableDoubleListOf(double d, double d2, double d3) {
        MutableDoubleList mutableDoubleList = new MutableDoubleList(3);
        mutableDoubleList.add(d);
        mutableDoubleList.add(d2);
        mutableDoubleList.add(d3);
        return mutableDoubleList;
    }

    public static final MutableDoubleList mutableDoubleListOf(double... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableDoubleList mutableDoubleList = new MutableDoubleList(elements.length);
        mutableDoubleList.addAll(mutableDoubleList._size, elements);
        return mutableDoubleList;
    }

    public static final DoubleList buildDoubleList(Function1<? super MutableDoubleList, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableDoubleList mutableDoubleList = new MutableDoubleList(0, 1, null);
        builderAction.invoke(mutableDoubleList);
        return mutableDoubleList;
    }

    public static final DoubleList buildDoubleList(int i, Function1<? super MutableDoubleList, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableDoubleList mutableDoubleList = new MutableDoubleList(i);
        builderAction.invoke(mutableDoubleList);
        return mutableDoubleList;
    }
}
