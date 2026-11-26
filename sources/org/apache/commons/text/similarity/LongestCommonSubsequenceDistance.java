package org.apache.commons.text.similarity;

/* JADX INFO: loaded from: classes6.dex */
public class LongestCommonSubsequenceDistance implements EditDistance<Integer> {
    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Integer apply(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Inputs must not be null");
        }
        return Integer.valueOf((charSequence.length() + charSequence2.length()) - (LongestCommonSubsequence.INSTANCE.apply(charSequence, charSequence2).intValue() * 2));
    }
}
