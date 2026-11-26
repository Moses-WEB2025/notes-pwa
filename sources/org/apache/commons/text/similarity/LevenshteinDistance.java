package org.apache.commons.text.similarity;

import java.util.Arrays;

/* JADX INFO: loaded from: classes6.dex */
public class LevenshteinDistance implements EditDistance<Integer> {
    private static final LevenshteinDistance INSTANCE = new LevenshteinDistance();
    private final Integer threshold;

    public static LevenshteinDistance getDefaultInstance() {
        return INSTANCE;
    }

    private static <E> int limitedCompare(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2, int i) {
        int i2;
        int length;
        SimilarityInput<E> similarityInput3;
        SimilarityInput<E> similarityInput4;
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("CharSequences must not be null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        int length2 = similarityInput.length();
        int length3 = similarityInput2.length();
        int i3 = -1;
        if (length2 == 0) {
            if (length3 <= i) {
                return length3;
            }
            return -1;
        }
        if (length3 == 0) {
            if (length2 <= i) {
                return length2;
            }
            return -1;
        }
        if (length2 > length3) {
            length = similarityInput.length();
            i2 = length3;
            similarityInput4 = similarityInput;
            similarityInput3 = similarityInput2;
        } else {
            i2 = length2;
            length = length3;
            similarityInput3 = similarityInput;
            similarityInput4 = similarityInput2;
        }
        if (length - i2 > i) {
            return -1;
        }
        int i4 = i2 + 1;
        int[] iArr = new int[i4];
        int[] iArr2 = new int[i4];
        int i5 = 1;
        int iMin = Math.min(i2, i) + 1;
        char c = 0;
        for (int i6 = 0; i6 < iMin; i6++) {
            iArr[i6] = i6;
        }
        Arrays.fill(iArr, iMin, i4, Integer.MAX_VALUE);
        Arrays.fill(iArr2, Integer.MAX_VALUE);
        int i7 = 1;
        while (i7 <= length) {
            E eAt = similarityInput4.at(i7 - 1);
            iArr2[c] = i7;
            int iMax = Math.max(i5, i7 - i);
            int iMin2 = i7 > Integer.MAX_VALUE - i ? i2 : Math.min(i2, i7 + i);
            if (iMax > i5) {
                iArr2[iMax - 1] = Integer.MAX_VALUE;
            }
            int iMin3 = Integer.MAX_VALUE;
            while (iMax <= iMin2) {
                int i8 = i3;
                int i9 = iMax - 1;
                int i10 = i5;
                if (similarityInput3.at(i9).equals(eAt)) {
                    iArr2[iMax] = iArr[i9];
                } else {
                    iArr2[iMax] = Math.min(Math.min(iArr2[i9], iArr[iMax]), iArr[i9]) + 1;
                }
                iMin3 = Math.min(iMin3, iArr2[iMax]);
                iMax++;
                i5 = i10;
                i3 = i8;
            }
            int i11 = i3;
            int i12 = i5;
            if (iMin3 > i) {
                return i11;
            }
            i7++;
            int[] iArr3 = iArr2;
            iArr2 = iArr;
            iArr = iArr3;
            i5 = i12;
            i3 = i11;
            c = 0;
        }
        int i13 = i3;
        int i14 = iArr[i2];
        return i14 <= i ? i14 : i13;
    }

    private static <E> int unlimitedCompare(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("CharSequences must not be null");
        }
        int length = similarityInput.length();
        int length2 = similarityInput2.length();
        if (length == 0) {
            return length2;
        }
        if (length2 == 0) {
            return length;
        }
        if (length > length2) {
            length2 = similarityInput.length();
            length = length2;
        } else {
            similarityInput2 = similarityInput;
            similarityInput = similarityInput2;
        }
        int[] iArr = new int[length + 1];
        for (int i = 0; i <= length; i++) {
            iArr[i] = i;
        }
        for (int i2 = 1; i2 <= length2; i2++) {
            int i3 = iArr[0];
            E eAt = similarityInput.at(i2 - 1);
            iArr[0] = i2;
            int i4 = 1;
            while (i4 <= length) {
                int i5 = iArr[i4];
                int i6 = i4 - 1;
                iArr[i4] = Math.min(Math.min(iArr[i6] + 1, iArr[i4] + 1), i3 + (!similarityInput2.at(i6).equals(eAt) ? 1 : 0));
                i4++;
                i3 = i5;
            }
        }
        return iArr[length];
    }

    @Deprecated
    public LevenshteinDistance() {
        this(null);
    }

    public LevenshteinDistance(Integer num) {
        if (num != null && num.intValue() < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        this.threshold = num;
    }

    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public Integer apply(CharSequence charSequence, CharSequence charSequence2) {
        return apply((SimilarityInput) SimilarityInput.input(charSequence), (SimilarityInput) SimilarityInput.input(charSequence2));
    }

    public <E> Integer apply(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        Integer num = this.threshold;
        if (num != null) {
            return Integer.valueOf(limitedCompare(similarityInput, similarityInput2, num.intValue()));
        }
        return Integer.valueOf(unlimitedCompare(similarityInput, similarityInput2));
    }

    public Integer getThreshold() {
        return this.threshold;
    }
}
