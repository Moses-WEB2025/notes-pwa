package org.apache.commons.lang3;

import java.util.stream.IntStream;

/* JADX INFO: loaded from: classes6.dex */
public final class IntegerRange extends NumberRange<Integer> {
    private static final long serialVersionUID = 1;

    public static IntegerRange of(int i, int i2) {
        return of(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static IntegerRange of(Integer num, Integer num2) {
        return new IntegerRange(num, num2);
    }

    private IntegerRange(Integer num, Integer num2) {
        super(num, num2, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IntStream toIntStream() {
        return IntStream.rangeClosed(((Integer) getMinimum()).intValue(), ((Integer) getMaximum()).intValue());
    }
}
