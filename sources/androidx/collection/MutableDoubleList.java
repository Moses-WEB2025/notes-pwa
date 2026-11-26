package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoubleList.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0013\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\b\u001a\u00020\f2\b\b\u0001\u0010\r\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bJ\u0011\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\bJ\u0011\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\bJ\u0018\u0010\u000e\u001a\u00020\t2\b\b\u0001\u0010\r\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0001J\u0018\u0010\u000e\u001a\u00020\t2\b\b\u0001\u0010\r\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\fJ\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0003J\u0011\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\u0002J\u0011\u0010\u0013\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u000bH\u0086\nJ\u0011\u0010\u0013\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\u0002J\u0011\u0010\u0014\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\nJ\u0011\u0010\u0014\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u000bH\u0086\nJ\u0011\u0010\u0014\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\nJ\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001J\u000e\u0010\u0016\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0017\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u0003J\u001a\u0010\u0018\u001a\u00020\f2\b\b\u0001\u0010\u0019\u001a\u00020\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001J\u000e\u0010\u001b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010J\u001b\u0010\u001c\u001a\u00020\u000b2\b\b\u0001\u0010\r\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002J\u0006\u0010\u001d\u001a\u00020\fJ\u0006\u0010\u001e\u001a\u00020\fJ\u0010\u0010\u001f\u001a\u00020\f2\b\b\u0002\u0010 \u001a\u00020\u0003R\u0012\u0010\u0005\u001a\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006!"}, d2 = {"Landroidx/collection/MutableDoubleList;", "Landroidx/collection/DoubleList;", "initialCapacity", "", "(I)V", "capacity", "getCapacity", "()I", "add", "", "element", "", "", "index", "addAll", "elements", "", "clear", "ensureCapacity", "minusAssign", "plusAssign", "remove", "removeAll", "removeAt", "removeRange", "start", "end", "retainAll", "set", "sort", "sortDescending", "trim", "minCapacity", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableDoubleList extends DoubleList {
    public MutableDoubleList() {
        this(0, 1, null);
    }

    public MutableDoubleList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableDoubleList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public final int getCapacity() {
        return this.content.length;
    }

    public final boolean add(double element) {
        ensureCapacity(this._size + 1);
        this.content[this._size] = element;
        this._size++;
        return true;
    }

    public final void add(int index, double element) {
        if (index < 0 || index > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        ensureCapacity(this._size + 1);
        double[] dArr = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(dArr, dArr, index + 1, index, this._size);
        }
        dArr[index] = element;
        this._size++;
    }

    public final boolean addAll(int index, double[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (index < 0 || index > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
        }
        if (elements.length == 0) {
            return false;
        }
        ensureCapacity(this._size + elements.length);
        double[] dArr = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(dArr, dArr, elements.length + index, index, this._size);
        }
        ArraysKt.copyInto$default(elements, dArr, index, 0, 0, 12, (Object) null);
        this._size += elements.length;
        return true;
    }

    public final boolean addAll(int index, DoubleList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (index < 0 || index > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
        }
        if (elements._size == 0) {
            return false;
        }
        ensureCapacity(this._size + elements._size);
        double[] dArr = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(dArr, dArr, elements._size + index, index, this._size);
        }
        ArraysKt.copyInto(elements.content, dArr, index, 0, elements._size);
        this._size += elements._size;
        return true;
    }

    public final boolean addAll(DoubleList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final boolean addAll(double[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final void plusAssign(DoubleList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void plusAssign(double[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void clear() {
        this._size = 0;
    }

    public static /* synthetic */ void trim$default(MutableDoubleList mutableDoubleList, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = mutableDoubleList._size;
        }
        mutableDoubleList.trim(i);
    }

    public final void trim(int minCapacity) {
        int iMax = Math.max(minCapacity, this._size);
        if (this.content.length > iMax) {
            double[] dArrCopyOf = Arrays.copyOf(this.content, iMax);
            Intrinsics.checkNotNullExpressionValue(dArrCopyOf, "copyOf(...)");
            this.content = dArrCopyOf;
        }
    }

    public final void ensureCapacity(int capacity) {
        double[] dArr = this.content;
        if (dArr.length < capacity) {
            double[] dArrCopyOf = Arrays.copyOf(dArr, Math.max(capacity, (dArr.length * 3) / 2));
            Intrinsics.checkNotNullExpressionValue(dArrCopyOf, "copyOf(...)");
            this.content = dArrCopyOf;
        }
    }

    public final void plusAssign(double element) {
        add(element);
    }

    public final void minusAssign(double element) {
        remove(element);
    }

    public final boolean remove(double element) {
        int iIndexOf = indexOf(element);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public final boolean removeAll(double[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        for (double d : elements) {
            remove(d);
        }
        return i != this._size;
    }

    public final boolean removeAll(DoubleList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        int i2 = elements._size - 1;
        if (i2 >= 0) {
            int i3 = 0;
            while (true) {
                remove(elements.get(i3));
                if (i3 == i2) {
                    break;
                }
                i3++;
            }
        }
        return i != this._size;
    }

    public final double removeAt(int index) {
        if (index < 0 || index >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        double[] dArr = this.content;
        double d = dArr[index];
        if (index != this._size - 1) {
            ArraysKt.copyInto(dArr, dArr, index, index + 1, this._size);
        }
        this._size--;
        return d;
    }

    public final void removeRange(int start, int end) {
        if (start < 0 || start > this._size || end < 0 || end > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        if (end < start) {
            RuntimeHelpersKt.throwIllegalArgumentException("The end index must be < start index");
        }
        if (end != start) {
            if (end < this._size) {
                ArraysKt.copyInto(this.content, this.content, start, end, this._size);
            }
            this._size -= end - start;
        }
    }

    public final boolean retainAll(double[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        double[] dArr = this.content;
        int i2 = this._size - 1;
        while (true) {
            int i3 = -1;
            if (-1 >= i2) {
                break;
            }
            double d = dArr[i2];
            int length = elements.length;
            for (int i4 = 0; i4 < length; i4++) {
                if (elements[i4] == d) {
                    i3 = i4;
                    break;
                }
            }
            if (i3 < 0) {
                removeAt(i2);
            }
            i2--;
        }
        return i != this._size;
    }

    public final boolean retainAll(DoubleList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        double[] dArr = this.content;
        for (int i2 = this._size - 1; -1 < i2; i2--) {
            if (!elements.contains(dArr[i2])) {
                removeAt(i2);
            }
        }
        return i != this._size;
    }

    public final double set(int index, double element) {
        if (index < 0 || index >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        double[] dArr = this.content;
        double d = dArr[index];
        dArr[index] = element;
        return d;
    }

    public final void sort() {
        if (this._size == 0) {
            return;
        }
        ArraysKt.sort(this.content, 0, this._size);
    }

    public final void sortDescending() {
        if (this._size == 0) {
            return;
        }
        ArraysKt.sortDescending(this.content, 0, this._size);
    }

    public final void minusAssign(double[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        for (double d : elements) {
            remove(d);
        }
    }

    public final void minusAssign(DoubleList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        double[] dArr = elements.content;
        int i = elements._size;
        for (int i2 = 0; i2 < i; i2++) {
            remove(dArr[i2]);
        }
    }
}
