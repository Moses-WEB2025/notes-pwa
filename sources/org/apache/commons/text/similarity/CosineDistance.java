package org.apache.commons.text.similarity;

/* JADX INFO: loaded from: classes6.dex */
public class CosineDistance implements EditDistance<Double> {
    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Double apply(CharSequence charSequence, CharSequence charSequence2) {
        CharSequence[] charSequenceArrApply = RegexTokenizer.INSTANCE.apply(charSequence);
        CharSequence[] charSequenceArrApply2 = RegexTokenizer.INSTANCE.apply(charSequence2);
        return Double.valueOf(1.0d - CosineSimilarity.INSTANCE.cosineSimilarity(Counter.of(charSequenceArrApply), Counter.of(charSequenceArrApply2)).doubleValue());
    }
}
