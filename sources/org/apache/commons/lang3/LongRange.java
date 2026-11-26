package org.apache.commons.lang3;

import java.util.stream.LongStream;

/* JADX INFO: loaded from: classes6.dex */
public final class LongRange extends NumberRange<Long> {
    private static final long serialVersionUID = 1;

    public static LongRange of(long j, long j2) {
        return of(Long.valueOf(j), Long.valueOf(j2));
    }

    public static LongRange of(Long l, Long l2) {
        return new LongRange(l, l2);
    }

    private LongRange(Long l, Long l2) {
        super(l, l2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LongStream toLongStream() {
        return LongStream.rangeClosed(((Long) getMinimum()).longValue(), ((Long) getMaximum()).longValue());
    }
}
