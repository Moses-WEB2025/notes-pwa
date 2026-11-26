package org.apache.commons.text.similarity;

/* JADX INFO: loaded from: classes6.dex */
public class HammingDistance implements EditDistance<Integer> {
    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Integer apply(CharSequence charSequence, CharSequence charSequence2) {
        return apply((SimilarityInput) SimilarityInput.input(charSequence), (SimilarityInput) SimilarityInput.input(charSequence2));
    }

    public <E> Integer apply(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("SimilarityInput must not be null");
        }
        if (similarityInput.length() != similarityInput2.length()) {
            throw new IllegalArgumentException("SimilarityInput must have the same length");
        }
        int i = 0;
        for (int i2 = 0; i2 < similarityInput.length(); i2++) {
            if (!similarityInput.at(i2).equals(similarityInput2.at(i2))) {
                i++;
            }
        }
        return Integer.valueOf(i);
    }
}
