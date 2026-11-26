package com.google.common.collect;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public enum BoundType {
    OPEN(false),
    CLOSED(true);

    final boolean inclusive;

    BoundType(boolean inclusive) {
        this.inclusive = inclusive;
    }

    static BoundType forBoolean(boolean inclusive) {
        return inclusive ? CLOSED : OPEN;
    }
}
