package org.apache.commons.text.similarity;

/* JADX INFO: loaded from: classes6.dex */
public interface SimilarityScore<R> extends ObjectSimilarityScore<CharSequence, R> {
    @Override // org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    R apply(CharSequence charSequence, CharSequence charSequence2);
}
