package kotlin.reflect.jvm.internal.impl.metadata.serialization;

import java.util.HashMap;

/* JADX INFO: compiled from: Interner.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class Interner<T> {
    private final int firstIndex;
    private final HashMap<T, Integer> interned;
    private final Interner<T> parent;

    private final Integer find(T t) {
        Integer numFind;
        Interner<T> interner = this.parent;
        if (interner != null) {
            int size = interner.interned.size() + this.parent.firstIndex;
            int i = this.firstIndex;
        }
        Interner<T> interner2 = this.parent;
        return (interner2 == null || (numFind = interner2.find(t)) == null) ? this.interned.get(t) : numFind;
    }

    public final int intern(T t) {
        Integer numFind = find(t);
        if (numFind != null) {
            return numFind.intValue();
        }
        int size = this.firstIndex + this.interned.size();
        this.interned.put(t, Integer.valueOf(size));
        return size;
    }
}
