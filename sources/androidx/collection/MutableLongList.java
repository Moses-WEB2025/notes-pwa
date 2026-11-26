package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LongList.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\b\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\fJ\u0011\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\bJ\u0018\u0010\u000e\u001a\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0001J\u0018\u0010\u000e\u001a\u00020\r2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010J\u0011\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\bJ\u0006\u0010\u0011\u001a\u00020\tJ\u000e\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0003J\u0011\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\u0002J\u0011\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086\nJ\u0011\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\u0002J\u0011\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0001H\u0086\nJ\u0011\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0086\nJ\u0011\u0010\u0014\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\nJ\u000e\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0001J\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0017\u001a\u00020\f2\b\b\u0001\u0010\n\u001a\u00020\u0003J\u001a\u0010\u0018\u001a\u00020\t2\b\b\u0001\u0010\u0019\u001a\u00020\u00032\b\b\u0001\u0010\u001a\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0001J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u001b\u0010\u001c\u001a\u00020\f2\b\b\u0001\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u0006\u0010\u001d\u001a\u00020\tJ\u0006\u0010\u001e\u001a\u00020\tJ\u0010\u0010\u001f\u001a\u00020\t2\b\b\u0002\u0010 \u001a\u00020\u0003R\u0012\u0010\u0005\u001a\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006!"}, d2 = {"Landroidx/collection/MutableLongList;", "Landroidx/collection/LongList;", "initialCapacity", "", "(I)V", "capacity", "getCapacity", "()I", "add", "", "index", "element", "", "", "addAll", "elements", "", "clear", "ensureCapacity", "minusAssign", "plusAssign", "remove", "removeAll", "removeAt", "removeRange", "start", "end", "retainAll", "set", "sort", "sortDescending", "trim", "minCapacity", "collection"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableLongList extends LongList {
    public MutableLongList() {
        this(0, 1, null);
    }

    public MutableLongList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableLongList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public final int getCapacity() {
        return this.content.length;
    }

    public final boolean add(long element) {
        ensureCapacity(this._size + 1);
        this.content[this._size] = element;
        this._size++;
        return true;
    }

    public final void add(int index, long element) {
        if (index < 0 || index > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        ensureCapacity(this._size + 1);
        long[] jArr = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(jArr, jArr, index + 1, index, this._size);
        }
        jArr[index] = element;
        this._size++;
    }

    public final boolean addAll(int index, long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (index < 0 || index > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
        }
        if (elements.length == 0) {
            return false;
        }
        ensureCapacity(this._size + elements.length);
        long[] jArr = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(jArr, jArr, elements.length + index, index, this._size);
        }
        ArraysKt.copyInto$default(elements, jArr, index, 0, 0, 12, (Object) null);
        this._size += elements.length;
        return true;
    }

    public final boolean addAll(int index, LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (index < 0 || index > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
        }
        if (elements._size == 0) {
            return false;
        }
        ensureCapacity(this._size + elements._size);
        long[] jArr = this.content;
        if (index != this._size) {
            ArraysKt.copyInto(jArr, jArr, elements._size + index, index, this._size);
        }
        ArraysKt.copyInto(elements.content, jArr, index, 0, elements._size);
        this._size += elements._size;
        return true;
    }

    public final boolean addAll(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final boolean addAll(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return addAll(this._size, elements);
    }

    public final void plusAssign(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void plusAssign(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        addAll(this._size, elements);
    }

    public final void clear() {
        this._size = 0;
    }

    public static /* synthetic */ void trim$default(MutableLongList mutableLongList, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = mutableLongList._size;
        }
        mutableLongList.trim(i);
    }

    public final void trim(int minCapacity) {
        int iMax = Math.max(minCapacity, this._size);
        if (this.content.length > iMax) {
            long[] jArrCopyOf = Arrays.copyOf(this.content, iMax);
            Intrinsics.checkNotNullExpressionValue(jArrCopyOf, "copyOf(...)");
            this.content = jArrCopyOf;
        }
    }

    public final void ensureCapacity(int capacity) {
        long[] jArr = this.content;
        if (jArr.length < capacity) {
            long[] jArrCopyOf = Arrays.copyOf(jArr, Math.max(capacity, (jArr.length * 3) / 2));
            Intrinsics.checkNotNullExpressionValue(jArrCopyOf, "copyOf(...)");
            this.content = jArrCopyOf;
        }
    }

    public final void plusAssign(long element) {
        add(element);
    }

    public final void minusAssign(long element) {
        remove(element);
    }

    public final boolean remove(long element) {
        int iIndexOf = indexOf(element);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public final boolean removeAll(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        for (long j : elements) {
            remove(j);
        }
        return i != this._size;
    }

    public final boolean removeAll(LongList elements) {
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

    public final long removeAt(int index) {
        if (index < 0 || index >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        long[] jArr = this.content;
        long j = jArr[index];
        if (index != this._size - 1) {
            ArraysKt.copyInto(jArr, jArr, index, index + 1, this._size);
        }
        this._size--;
        return j;
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

    public final boolean retainAll(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        long[] jArr = this.content;
        int i2 = this._size - 1;
        while (true) {
            int i3 = -1;
            if (-1 >= i2) {
                break;
            }
            long j = jArr[i2];
            int length = elements.length;
            for (int i4 = 0; i4 < length; i4++) {
                if (elements[i4] == j) {
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

    public final boolean retainAll(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int i = this._size;
        long[] jArr = this.content;
        for (int i2 = this._size - 1; -1 < i2; i2--) {
            if (!elements.contains(jArr[i2])) {
                removeAt(i2);
            }
        }
        return i != this._size;
    }

    public final long set(int index, long element) {
        if (index < 0 || index >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        long[] jArr = this.content;
        long j = jArr[index];
        jArr[index] = element;
        return j;
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

    public final void minusAssign(long[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        for (long j : elements) {
            remove(j);
        }
    }

    public final void minusAssign(LongList elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        long[] jArr = elements.content;
        int i = elements._size;
        for (int i2 = 0; i2 < i; i2++) {
            remove(jArr[i2]);
        }
    }
}
