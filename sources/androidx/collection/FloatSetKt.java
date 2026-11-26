package androidx.collection;

import com.xiaomi.onetrack.g.a;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FloatSet.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\u001a2\u0010\u0006\u001a\u00020\u00072\u0017\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\b\u000bH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0017\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\b\u000bH\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a\u0006\u0010\u000e\u001a\u00020\u0007\u001a\u0006\u0010\u000f\u001a\u00020\u0007\u001a\u000e\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0016\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011\u001a\u001e\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011\u001a\u0012\u0010\u000f\u001a\u00020\u00072\n\u0010\u0014\u001a\u00020\u0001\"\u00020\u0011\u001a\u0011\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0011H\u0080\b\u001a\u0006\u0010\u0017\u001a\u00020\u0005\u001a\u000e\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0016\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011\u001a\u001e\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011\u001a\u0012\u0010\u0017\u001a\u00020\u00052\n\u0010\u0014\u001a\u00020\u0001\"\u00020\u0011\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0018"}, d2 = {"EmptyFloatArray", "", "getEmptyFloatArray", "()[F", "EmptyFloatSet", "Landroidx/collection/MutableFloatSet;", "buildFloatSet", "Landroidx/collection/FloatSet;", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "initialCapacity", "", "emptyFloatSet", "floatSetOf", "element1", "", "element2", "element3", "elements", a.e, "k", "mutableFloatSetOf", "collection"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FloatSetKt {
    private static final MutableFloatSet EmptyFloatSet = new MutableFloatSet(0);
    private static final float[] EmptyFloatArray = new float[0];

    public static final float[] getEmptyFloatArray() {
        return EmptyFloatArray;
    }

    public static final FloatSet emptyFloatSet() {
        return EmptyFloatSet;
    }

    public static final FloatSet floatSetOf() {
        return EmptyFloatSet;
    }

    public static final FloatSet floatSetOf(float f) {
        return mutableFloatSetOf(f);
    }

    public static final FloatSet floatSetOf(float f, float f2) {
        return mutableFloatSetOf(f, f2);
    }

    public static final FloatSet floatSetOf(float f, float f2, float f3) {
        return mutableFloatSetOf(f, f2, f3);
    }

    public static final FloatSet floatSetOf(float... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableFloatSet mutableFloatSet = new MutableFloatSet(elements.length);
        mutableFloatSet.plusAssign(elements);
        return mutableFloatSet;
    }

    public static final MutableFloatSet mutableFloatSetOf() {
        return new MutableFloatSet(0, 1, null);
    }

    public static final MutableFloatSet mutableFloatSetOf(float f) {
        MutableFloatSet mutableFloatSet = new MutableFloatSet(1);
        mutableFloatSet.plusAssign(f);
        return mutableFloatSet;
    }

    public static final MutableFloatSet mutableFloatSetOf(float f, float f2) {
        MutableFloatSet mutableFloatSet = new MutableFloatSet(2);
        mutableFloatSet.plusAssign(f);
        mutableFloatSet.plusAssign(f2);
        return mutableFloatSet;
    }

    public static final MutableFloatSet mutableFloatSetOf(float f, float f2, float f3) {
        MutableFloatSet mutableFloatSet = new MutableFloatSet(3);
        mutableFloatSet.plusAssign(f);
        mutableFloatSet.plusAssign(f2);
        mutableFloatSet.plusAssign(f3);
        return mutableFloatSet;
    }

    public static final MutableFloatSet mutableFloatSetOf(float... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableFloatSet mutableFloatSet = new MutableFloatSet(elements.length);
        mutableFloatSet.plusAssign(elements);
        return mutableFloatSet;
    }

    public static final FloatSet buildFloatSet(Function1<? super MutableFloatSet, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableFloatSet mutableFloatSet = new MutableFloatSet(0, 1, null);
        builderAction.invoke(mutableFloatSet);
        return mutableFloatSet;
    }

    public static final FloatSet buildFloatSet(int i, Function1<? super MutableFloatSet, Unit> builderAction) {
        Intrinsics.checkNotNullParameter(builderAction, "builderAction");
        MutableFloatSet mutableFloatSet = new MutableFloatSet(i);
        builderAction.invoke(mutableFloatSet);
        return mutableFloatSet;
    }

    public static final int hash(float f) {
        int iHashCode = Float.hashCode(f) * ScatterMapKt.MurmurHashC1;
        return iHashCode ^ (iHashCode << 16);
    }
}
