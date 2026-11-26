package org.apache.commons.text.lookup;

import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface StringLookup extends UnaryOperator<String> {
    @Deprecated
    String lookup(String str);

    @Override // java.util.function.Function
    default String apply(String str) {
        return lookup(str);
    }
}
