package org.apache.commons.lang3.function;

import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
public final class Functions {
    public static <T, R> Function<T, R> function(Function<T, R> function) {
        return function;
    }

    public static <T, R> R apply(Function<T, R> function, T t) {
        if (function != null) {
            return function.apply(t);
        }
        return null;
    }

    private Functions() {
    }
}
