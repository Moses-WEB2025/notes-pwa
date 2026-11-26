package org.apache.commons.text.similarity;

/* JADX INFO: loaded from: classes6.dex */
public class JaroWinklerDistance implements EditDistance<Double> {

    @Deprecated
    public static final int INDEX_NOT_FOUND = -1;

    @Deprecated
    protected static int[] matches(CharSequence charSequence, CharSequence charSequence2) {
        return JaroWinklerSimilarity.matches(charSequence, charSequence2);
    }

    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Double apply(CharSequence charSequence, CharSequence charSequence2) {
        return apply((SimilarityInput) SimilarityInput.input(charSequence), (SimilarityInput) SimilarityInput.input(charSequence2));
    }

    public <E> Double apply(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("CharSequences must not be null");
        }
        return Double.valueOf(1.0d - JaroWinklerSimilarity.INSTANCE.apply((SimilarityInput) similarityInput, (SimilarityInput) similarityInput2).doubleValue());
    }
}
