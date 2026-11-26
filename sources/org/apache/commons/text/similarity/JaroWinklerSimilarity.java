package org.apache.commons.text.similarity;

import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes6.dex */
public class JaroWinklerSimilarity implements SimilarityScore<Double> {
    static final JaroWinklerSimilarity INSTANCE = new JaroWinklerSimilarity();

    protected static int[] matches(CharSequence charSequence, CharSequence charSequence2) {
        return matches(SimilarityInput.input(charSequence), SimilarityInput.input(charSequence2));
    }

    protected static <E> int[] matches(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        SimilarityInput<E> similarityInput3;
        SimilarityInput<E> similarityInput4;
        if (similarityInput.length() > similarityInput2.length()) {
            similarityInput4 = similarityInput;
            similarityInput3 = similarityInput2;
        } else {
            similarityInput3 = similarityInput;
            similarityInput4 = similarityInput2;
        }
        int iMax = Math.max((similarityInput4.length() / 2) - 1, 0);
        int[] iArr = new int[similarityInput3.length()];
        Arrays.fill(iArr, -1);
        boolean[] zArr = new boolean[similarityInput4.length()];
        int i = 0;
        for (int i2 = 0; i2 < similarityInput3.length(); i2++) {
            E eAt = similarityInput3.at(i2);
            int iMin = Math.min(i2 + iMax + 1, similarityInput4.length());
            for (int iMax2 = Math.max(i2 - iMax, 0); iMax2 < iMin; iMax2++) {
                if (!zArr[iMax2] && eAt.equals(similarityInput4.at(iMax2))) {
                    iArr[i2] = iMax2;
                    zArr[iMax2] = true;
                    i++;
                    break;
                }
            }
        }
        Object[] objArr = new Object[i];
        Object[] objArr2 = new Object[i];
        int i3 = 0;
        for (int i4 = 0; i4 < similarityInput3.length(); i4++) {
            if (iArr[i4] != -1) {
                objArr[i3] = similarityInput3.at(i4);
                i3++;
            }
        }
        int i5 = 0;
        for (int i6 = 0; i6 < similarityInput4.length(); i6++) {
            if (zArr[i6]) {
                objArr2[i5] = similarityInput4.at(i6);
                i5++;
            }
        }
        int i7 = 0;
        for (int i8 = 0; i8 < i; i8++) {
            if (!objArr[i8].equals(objArr2[i8])) {
                i7++;
            }
        }
        int i9 = 0;
        for (int i10 = 0; i10 < Math.min(4, similarityInput3.length()) && similarityInput.at(i10).equals(similarityInput2.at(i10)); i10++) {
            i9++;
        }
        return new int[]{i, i7, i9};
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Double apply(CharSequence charSequence, CharSequence charSequence2) {
        return apply((SimilarityInput) SimilarityInput.input(charSequence), (SimilarityInput) SimilarityInput.input(charSequence2));
    }

    public <E> Double apply(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("CharSequences must not be null");
        }
        if (Objects.equals(similarityInput, similarityInput2)) {
            return Double.valueOf(1.0d);
        }
        int[] iArrMatches = matches(similarityInput, similarityInput2);
        double d = iArrMatches[0];
        if (d == 0.0d) {
            return Double.valueOf(0.0d);
        }
        double length = (((d / ((double) similarityInput.length())) + (d / ((double) similarityInput2.length()))) + ((d - (((double) iArrMatches[1]) / 2.0d)) / d)) / 3.0d;
        if (length >= 0.7d) {
            length += ((double) iArrMatches[2]) * 0.1d * (1.0d - length);
        }
        return Double.valueOf(length);
    }
}
