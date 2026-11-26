package org.apache.commons.text.similarity;

import java.lang.reflect.Array;
import java.util.Arrays;

/* JADX INFO: loaded from: classes6.dex */
public class LevenshteinDetailedDistance implements EditDistance<LevenshteinResults> {
    private static final LevenshteinDetailedDistance INSTANCE = new LevenshteinDetailedDistance();
    private final Integer threshold;

    /* JADX WARN: Code duplicated, block: B:34:0x006a A[PHI: r0 r1
  0x006a: PHI (r0v6 int) = (r0v3 int), (r0v1 int) binds: [B:42:0x007f, B:33:0x0068] A[DONT_GENERATE, DONT_INLINE]
  0x006a: PHI (r1v5 int) = (r1v1 int), (r1v6 int) binds: [B:42:0x007f, B:33:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:35:0x006e A[PHI: r0 r1
  0x006e: PHI (r0v4 int) = (r0v3 int), (r0v1 int) binds: [B:42:0x007f, B:33:0x0068] A[DONT_GENERATE, DONT_INLINE]
  0x006e: PHI (r1v3 int) = (r1v1 int), (r1v6 int) binds: [B:42:0x007f, B:33:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    private static <E> LevenshteinResults findDetailedResults(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2, int[][] iArr, boolean z) {
        boolean z2;
        int length = similarityInput2.length();
        int length2 = similarityInput.length();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (length >= 0 && length2 >= 0) {
            int i4 = length2 == 0 ? -1 : iArr[length][length2 - 1];
            int i5 = length == 0 ? -1 : iArr[length - 1][length2];
            int i6 = (length <= 0 || length2 <= 0) ? -1 : iArr[length - 1][length2 - 1];
            if (i4 == -1 && i5 == -1 && i6 == -1) {
                break;
            }
            int i7 = iArr[length][length2];
            if (length2 <= 0 || length <= 0 || !similarityInput.at(length2 - 1).equals(similarityInput2.at(length - 1))) {
                int i8 = i7 - 1;
                boolean z3 = true;
                if ((i8 == i4 && i7 <= i6 && i7 <= i5) || (i6 == -1 && i5 == -1)) {
                    length2--;
                    if (z) {
                        i2++;
                        z2 = false;
                    } else {
                        i++;
                        z2 = true;
                        z3 = false;
                    }
                } else if ((i8 != i5 || i7 > i6 || i7 > i4) && !(i6 == -1 && i4 == -1)) {
                    z2 = false;
                    z3 = false;
                } else {
                    length--;
                    if (z) {
                        i++;
                        z2 = true;
                        z3 = false;
                    } else {
                        i2++;
                        z2 = false;
                    }
                }
                if (!z3 && !z2) {
                    i3++;
                }
            }
            length2--;
            length--;
        }
        return new LevenshteinResults(Integer.valueOf(i2 + i + i3), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i3));
    }

    public static LevenshteinDetailedDistance getDefaultInstance() {
        return INSTANCE;
    }

    private static <E> LevenshteinResults limitedCompare(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2, int i) {
        int i2;
        int length;
        boolean z;
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
        int i4 = 0;
        if (length2 == 0) {
            return length3 <= i ? new LevenshteinResults(Integer.valueOf(length3), Integer.valueOf(length3), 0, 0) : new LevenshteinResults(-1, 0, 0, 0);
        }
        if (length3 == 0) {
            return length2 <= i ? new LevenshteinResults(Integer.valueOf(length2), 0, Integer.valueOf(length2), 0) : new LevenshteinResults(-1, 0, 0, 0);
        }
        int i5 = 1;
        if (length2 > length3) {
            length = similarityInput.length();
            i2 = length3;
            z = true;
            similarityInput4 = similarityInput;
            similarityInput3 = similarityInput2;
        } else {
            i2 = length2;
            length = length3;
            z = false;
            similarityInput3 = similarityInput;
            similarityInput4 = similarityInput2;
        }
        int i6 = i2 + 1;
        int[] iArr = new int[i6];
        int[] iArr2 = new int[i6];
        int[][] iArr3 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length + 1, i6);
        for (int i7 = 0; i7 <= i2; i7++) {
            iArr3[0][i7] = i7;
        }
        for (int i8 = 0; i8 <= length; i8++) {
            iArr3[i8][0] = i8;
        }
        int iMin = Math.min(i2, i) + 1;
        for (int i9 = 0; i9 < iMin; i9++) {
            iArr[i9] = i9;
        }
        int i10 = Integer.MAX_VALUE;
        Arrays.fill(iArr, iMin, i6, Integer.MAX_VALUE);
        Arrays.fill(iArr2, Integer.MAX_VALUE);
        int i11 = 1;
        while (i11 <= length) {
            E eAt = similarityInput4.at(i11 - 1);
            iArr2[i4] = i11;
            int iMax = Math.max(i5, i11 - i);
            int i12 = i3;
            int iMin2 = i11 > i10 - i ? i2 : Math.min(i2, i11 + i);
            if (iMax > iMin2) {
                return new LevenshteinResults(Integer.valueOf(i12), Integer.valueOf(i4), Integer.valueOf(i4), Integer.valueOf(i4));
            }
            if (iMax > i5) {
                iArr2[iMax - 1] = i10;
            }
            while (iMax <= iMin2) {
                int i13 = i4;
                int i14 = iMax - 1;
                int i15 = i5;
                if (similarityInput3.at(i14).equals(eAt)) {
                    iArr2[iMax] = iArr[i14];
                } else {
                    iArr2[iMax] = Math.min(Math.min(iArr2[i14], iArr[iMax]), iArr[i14]) + 1;
                }
                iArr3[i11][iMax] = iArr2[iMax];
                iMax++;
                i4 = i13;
                i5 = i15;
            }
            i11++;
            int[] iArr4 = iArr2;
            iArr2 = iArr;
            iArr = iArr4;
            i3 = i12;
            i10 = Integer.MAX_VALUE;
        }
        int i16 = i3;
        int i17 = i4;
        if (iArr[i2] <= i) {
            return findDetailedResults(similarityInput3, similarityInput4, iArr3, z);
        }
        return new LevenshteinResults(Integer.valueOf(i16), Integer.valueOf(i17), Integer.valueOf(i17), Integer.valueOf(i17));
    }

    private static <E> LevenshteinResults unlimitedCompare(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        int i;
        int length;
        boolean z;
        SimilarityInput<E> similarityInput3;
        SimilarityInput<E> similarityInput4;
        if (similarityInput == null || similarityInput2 == null) {
            throw new IllegalArgumentException("CharSequences must not be null");
        }
        int length2 = similarityInput.length();
        int length3 = similarityInput2.length();
        char c = 0;
        if (length2 == 0) {
            return new LevenshteinResults(Integer.valueOf(length3), Integer.valueOf(length3), 0, 0);
        }
        if (length3 == 0) {
            return new LevenshteinResults(Integer.valueOf(length2), 0, Integer.valueOf(length2), 0);
        }
        if (length2 > length3) {
            length = similarityInput.length();
            i = length3;
            z = true;
            similarityInput4 = similarityInput;
            similarityInput3 = similarityInput2;
        } else {
            i = length2;
            length = length3;
            z = false;
            similarityInput3 = similarityInput;
            similarityInput4 = similarityInput2;
        }
        int i2 = i + 1;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        int[][] iArr3 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length + 1, i2);
        for (int i3 = 0; i3 <= i; i3++) {
            iArr3[0][i3] = i3;
        }
        for (int i4 = 0; i4 <= length; i4++) {
            iArr3[i4][0] = i4;
        }
        for (int i5 = 0; i5 <= i; i5++) {
            iArr[i5] = i5;
        }
        int[] iArr4 = iArr;
        int[] iArr5 = iArr2;
        int i6 = 1;
        while (i6 <= length) {
            E eAt = similarityInput4.at(i6 - 1);
            iArr5[c] = i6;
            for (int i7 = 1; i7 <= i; i7++) {
                int i8 = i7 - 1;
                int iMin = Math.min(Math.min(iArr5[i8] + 1, iArr4[i7] + 1), iArr4[i8] + (!similarityInput3.at(i8).equals(eAt) ? 1 : 0));
                iArr5[i7] = iMin;
                iArr3[i6][i7] = iMin;
            }
            i6++;
            int[] iArr6 = iArr4;
            iArr4 = iArr5;
            iArr5 = iArr6;
            c = 0;
        }
        return findDetailedResults(similarityInput3, similarityInput4, iArr3, z);
    }

    @Deprecated
    public LevenshteinDetailedDistance() {
        this(null);
    }

    public LevenshteinDetailedDistance(Integer num) {
        if (num != null && num.intValue() < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        this.threshold = num;
    }

    @Override // org.apache.commons.text.similarity.SimilarityScore, org.apache.commons.text.similarity.ObjectSimilarityScore, java.util.function.BiFunction
    public LevenshteinResults apply(CharSequence charSequence, CharSequence charSequence2) {
        return apply((SimilarityInput) SimilarityInput.input(charSequence), (SimilarityInput) SimilarityInput.input(charSequence2));
    }

    public <E> LevenshteinResults apply(SimilarityInput<E> similarityInput, SimilarityInput<E> similarityInput2) {
        Integer num = this.threshold;
        if (num != null) {
            return limitedCompare(similarityInput, similarityInput2, num.intValue());
        }
        return unlimitedCompare(similarityInput, similarityInput2);
    }

    public Integer getThreshold() {
        return this.threshold;
    }
}
