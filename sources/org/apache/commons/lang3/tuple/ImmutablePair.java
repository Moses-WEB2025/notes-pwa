package org.apache.commons.lang3.tuple;

import com.miui.common.stat.EditStat;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutablePair<L, R> extends Pair<L, R> {
    public static final ImmutablePair<?, ?>[] EMPTY_ARRAY = new ImmutablePair[0];
    private static final ImmutablePair NULL = new ImmutablePair(null, null);
    private static final long serialVersionUID = 4954918890077093841L;
    public final L left;
    public final R right;

    /* JADX WARN: Multi-variable type inference failed */
    public static <L, R> ImmutablePair<L, R>[] emptyArray() {
        return (ImmutablePair<L, R>[]) EMPTY_ARRAY;
    }

    public static <L, R> Pair<L, R> left(L l) {
        return of((Object) l, (Object) null);
    }

    public static <L, R> ImmutablePair<L, R> nullPair() {
        return NULL;
    }

    public static <L, R> ImmutablePair<L, R> of(L l, R r) {
        return (l == null && r == null) ? nullPair() : new ImmutablePair<>(l, r);
    }

    public static <L, R> ImmutablePair<L, R> of(Map.Entry<L, R> entry) {
        return entry != null ? new ImmutablePair<>(entry.getKey(), entry.getValue()) : nullPair();
    }

    public static <L, R> ImmutablePair<L, R> ofNonNull(L l, R r) {
        return of(Objects.requireNonNull(l, "left"), Objects.requireNonNull(r, EditStat.ELEMENT_RIGHT));
    }

    public static <L, R> Pair<L, R> right(R r) {
        return of((Object) null, (Object) r);
    }

    public ImmutablePair(L l, R r) {
        this.left = l;
        this.right = r;
    }

    @Override // org.apache.commons.lang3.tuple.Pair
    public L getLeft() {
        return this.left;
    }

    @Override // org.apache.commons.lang3.tuple.Pair
    public R getRight() {
        return this.right;
    }

    @Override // java.util.Map.Entry
    public R setValue(R r) {
        throw new UnsupportedOperationException();
    }
}
