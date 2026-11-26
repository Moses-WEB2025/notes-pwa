package org.apache.commons.text.similarity;

import java.util.HashSet;

/* JADX INFO: loaded from: classes6.dex */
public class JaccardSimilarity implements SimilarityScore<Double> {
    static final JaccardSimilarity INSTANCE = new JaccardSimilarity();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Double apply(CharSequence charSequence, CharSequence charSequence2) {
        return apply((SimilarityInput) SimilarityInput.input(charSequence), (SimilarityInput) SimilarityInput.input(charSequence2));
    }

    public <E> Double apply(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        int length = similarityInput.length();
        int length2 = similarityInput2.length();
        if (length == 0 && length2 == 0) {
            return Double.valueOf(1.0d);
        }
        if (length == 0 || length2 == 0) {
            return Double.valueOf(0.0d);
        }
        HashSet hashSet = new HashSet();
        for (int i = 0; i < length; i++) {
            hashSet.add(similarityInput.at(i));
        }
        HashSet hashSet2 = new HashSet();
        for (int i2 = 0; i2 < length2; i2++) {
            hashSet2.add(similarityInput2.at(i2));
        }
        HashSet hashSet3 = new HashSet(hashSet);
        hashSet3.addAll(hashSet2);
        return Double.valueOf((((double) ((hashSet.size() + hashSet2.size()) - hashSet3.size())) * 1.0d) / ((double) hashSet3.size()));
    }
}
