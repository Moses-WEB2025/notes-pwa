package org.apache.commons.text.lookup;

/* JADX INFO: loaded from: classes6.dex */
@FunctionalInterface
public interface BiStringLookup<U> extends StringLookup {
    default String lookup(String str, U u) {
        return apply(str);
    }
}
