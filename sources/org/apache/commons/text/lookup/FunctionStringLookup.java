package org.apache.commons.text.lookup;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: classes6.dex */
final class FunctionStringLookup<V> extends AbstractStringLookup {
    private final Function<String, V> function;

    static <R> FunctionStringLookup<R> on(Function<String, R> function) {
        return new FunctionStringLookup<>(function);
    }

    static <V> FunctionStringLookup<V> on(Map<String, V> map) {
        final Map map2 = StringLookupFactory.toMap(map);
        Objects.requireNonNull(map2);
        return on(new Function() { // from class: org.apache.commons.text.lookup.FunctionStringLookup$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return map2.get((String) obj);
            }
        });
    }

    private FunctionStringLookup(Function<String, V> function) {
        this.function = function;
    }

    @Override // org.apache.commons.text.lookup.StringLookup
    public String lookup(String str) {
        Function<String, V> function = this.function;
        if (function == null) {
            return null;
        }
        try {
            return Objects.toString(function.apply(str), null);
        } catch (IllegalArgumentException | NullPointerException | SecurityException unused) {
            return null;
        }
    }

    public String toString() {
        return super.toString() + " [function=" + this.function + "]";
    }
}
