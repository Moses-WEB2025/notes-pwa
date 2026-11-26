package org.apache.commons.lang3.stream;

import java.util.stream.IntStream;

/* JADX INFO: loaded from: classes6.dex */
public class IntStreams {
    @SafeVarargs
    public static IntStream of(int... iArr) {
        return iArr == null ? IntStream.empty() : IntStream.of(iArr);
    }

    public static IntStream range(int i) {
        return IntStream.range(0, i);
    }

    public static IntStream rangeClosed(int i) {
        return IntStream.rangeClosed(0, i);
    }

    @Deprecated
    public IntStreams() {
    }
}
