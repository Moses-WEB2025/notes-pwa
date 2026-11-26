package org.apache.commons.text.similarity;

import java.util.function.BiFunction;

/* JADX INFO: loaded from: classes6.dex */
public interface ObjectSimilarityScore<T, R> extends BiFunction<T, T, R> {
    @Override // java.util.function.BiFunction
    R apply(T t, T t2);
}
