package com.google.common.math;

import com.google.common.base.Preconditions;
import com.miui.maml.folme.AnimatedProperty;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
public final class BigIntegerMath {
    static final int SQRT2_PRECOMPUTE_THRESHOLD = 256;
    static final BigInteger SQRT2_PRECOMPUTED_BITS = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    private static final double LN_10 = Math.log(10.0d);
    private static final double LN_2 = Math.log(2.0d);

    public static BigInteger ceilingPowerOfTwo(BigInteger x) {
        return BigInteger.ZERO.setBit(log2(x, RoundingMode.CEILING));
    }

    public static BigInteger floorPowerOfTwo(BigInteger x) {
        return BigInteger.ZERO.setBit(log2(x, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger x) {
        Preconditions.checkNotNull(x);
        return x.signum() > 0 && x.getLowestSetBit() == x.bitLength() - 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:20:0x0051 A[RETURN] */
    public static int log2(BigInteger x, RoundingMode mode) {
        MathPreconditions.checkPositive(AnimatedProperty.PROPERTY_NAME_X, (BigInteger) Preconditions.checkNotNull(x));
        int iBitLength = x.bitLength();
        int i = iBitLength - 1;
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
                return i;
            case 2:
            case 3:
                return i;
            case 4:
            case 5:
                if (isPowerOfTwo(x)) {
                    return i;
                }
                return iBitLength;
            case 6:
            case 7:
            case 8:
                if (i < 256) {
                    if (x.compareTo(SQRT2_PRECOMPUTED_BITS.shiftRight(256 - i)) > 0) {
                        return iBitLength;
                    }
                } else if (x.pow(2).bitLength() - 1 >= (i * 2) + 1) {
                    return iBitLength;
                }
                return i;
            default:
                throw new AssertionError();
        }
    }

    /* JADX INFO: renamed from: com.google.common.math.BigIntegerMath$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static int log10(BigInteger x, RoundingMode mode) {
        int i;
        int iCompareTo;
        MathPreconditions.checkPositive(AnimatedProperty.PROPERTY_NAME_X, x);
        if (fitsInLong(x)) {
            return LongMath.log10(x.longValue(), mode);
        }
        int iLog2 = (int) ((((double) log2(x, RoundingMode.FLOOR)) * LN_2) / LN_10);
        BigInteger bigIntegerPow = BigInteger.TEN.pow(iLog2);
        int iCompareTo2 = bigIntegerPow.compareTo(x);
        if (iCompareTo2 > 0) {
            do {
                iLog2--;
                bigIntegerPow = bigIntegerPow.divide(BigInteger.TEN);
                iCompareTo = bigIntegerPow.compareTo(x);
            } while (iCompareTo > 0);
        } else {
            BigInteger bigIntegerMultiply = BigInteger.TEN.multiply(bigIntegerPow);
            int iCompareTo3 = bigIntegerMultiply.compareTo(x);
            while (true) {
                int i2 = iCompareTo3;
                i = iCompareTo2;
                iCompareTo2 = i2;
                if (iCompareTo2 > 0) {
                    break;
                }
                iLog2++;
                BigInteger bigIntegerMultiply2 = BigInteger.TEN.multiply(bigIntegerMultiply);
                iCompareTo3 = bigIntegerMultiply2.compareTo(x);
                BigInteger bigInteger = bigIntegerMultiply;
                bigIntegerMultiply = bigIntegerMultiply2;
                bigIntegerPow = bigInteger;
            }
            iCompareTo = i;
        }
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(iCompareTo == 0);
                return iLog2;
            case 2:
            case 3:
                return iLog2;
            case 4:
            case 5:
                return bigIntegerPow.equals(x) ? iLog2 : iLog2 + 1;
            case 6:
            case 7:
            case 8:
                return x.pow(2).compareTo(bigIntegerPow.pow(2).multiply(BigInteger.TEN)) <= 0 ? iLog2 : iLog2 + 1;
            default:
                throw new AssertionError();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static BigInteger sqrt(BigInteger x, RoundingMode mode) {
        MathPreconditions.checkNonNegative(AnimatedProperty.PROPERTY_NAME_X, x);
        if (fitsInLong(x)) {
            return BigInteger.valueOf(LongMath.sqrt(x.longValue(), mode));
        }
        BigInteger bigIntegerSqrtFloor = sqrtFloor(x);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(bigIntegerSqrtFloor.pow(2).equals(x));
                return bigIntegerSqrtFloor;
            case 2:
            case 3:
                return bigIntegerSqrtFloor;
            case 4:
            case 5:
                int iIntValue = bigIntegerSqrtFloor.intValue();
                return (iIntValue * iIntValue == x.intValue() && bigIntegerSqrtFloor.pow(2).equals(x)) ? bigIntegerSqrtFloor : bigIntegerSqrtFloor.add(BigInteger.ONE);
            case 6:
            case 7:
            case 8:
                if (bigIntegerSqrtFloor.pow(2).add(bigIntegerSqrtFloor).compareTo(x) < 0) {
                    return bigIntegerSqrtFloor.add(BigInteger.ONE);
                }
                return bigIntegerSqrtFloor;
            default:
                throw new AssertionError();
        }
    }

    private static BigInteger sqrtFloor(BigInteger x) {
        BigInteger bigIntegerShiftLeft;
        int iLog2 = log2(x, RoundingMode.FLOOR);
        if (iLog2 < 1023) {
            bigIntegerShiftLeft = sqrtApproxWithDoubles(x);
        } else {
            int i = (iLog2 - 52) & (-2);
            bigIntegerShiftLeft = sqrtApproxWithDoubles(x.shiftRight(i)).shiftLeft(i >> 1);
        }
        BigInteger bigIntegerShiftRight = bigIntegerShiftLeft.add(x.divide(bigIntegerShiftLeft)).shiftRight(1);
        if (bigIntegerShiftLeft.equals(bigIntegerShiftRight)) {
            return bigIntegerShiftLeft;
        }
        while (true) {
            BigInteger bigIntegerShiftRight2 = bigIntegerShiftRight.add(x.divide(bigIntegerShiftRight)).shiftRight(1);
            if (bigIntegerShiftRight2.compareTo(bigIntegerShiftRight) >= 0) {
                return bigIntegerShiftRight;
            }
            bigIntegerShiftRight = bigIntegerShiftRight2;
        }
    }

    private static BigInteger sqrtApproxWithDoubles(BigInteger x) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.bigToDouble(x)), RoundingMode.HALF_EVEN);
    }

    public static double roundToDouble(BigInteger x, RoundingMode mode) {
        return BigIntegerToDoubleRounder.INSTANCE.roundToDouble(x, mode);
    }

    private static class BigIntegerToDoubleRounder extends ToDoubleRounder<BigInteger> {
        static final BigIntegerToDoubleRounder INSTANCE = new BigIntegerToDoubleRounder();

        private BigIntegerToDoubleRounder() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        public double roundToDoubleArbitrarily(BigInteger bigInteger) {
            return DoubleUtils.bigToDouble(bigInteger);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        public int sign(BigInteger bigInteger) {
            return bigInteger.signum();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        public BigInteger toX(double d, RoundingMode mode) {
            return DoubleMath.roundToBigInteger(d, mode);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.math.ToDoubleRounder
        public BigInteger minus(BigInteger a, BigInteger b) {
            return a.subtract(b);
        }
    }

    public static BigInteger divide(BigInteger p, BigInteger q, RoundingMode mode) {
        return new BigDecimal(p).divide(new BigDecimal(q), 0, mode).toBigIntegerExact();
    }

    public static BigInteger factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n < LongMath.factorials.length) {
            return BigInteger.valueOf(LongMath.factorials[n]);
        }
        ArrayList arrayList = new ArrayList(IntMath.divide(IntMath.log2(n, RoundingMode.CEILING) * n, 64, RoundingMode.CEILING));
        int length = LongMath.factorials.length;
        long j = LongMath.factorials[length - 1];
        int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long j2 = j >> iNumberOfTrailingZeros;
        int i = 1;
        int iLog2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
        long j3 = length;
        int iLog3 = LongMath.log2(j3, RoundingMode.FLOOR);
        int i2 = iLog3 + 1;
        int i3 = 1 << iLog3;
        while (j3 <= n) {
            int i4 = i;
            long j4 = j3;
            if ((((long) i3) & j4) != 0) {
                i3 <<= 1;
                i2++;
            }
            int iNumberOfTrailingZeros2 = Long.numberOfTrailingZeros(j4);
            long j5 = j4 >> iNumberOfTrailingZeros2;
            iNumberOfTrailingZeros += iNumberOfTrailingZeros2;
            if ((i2 - iNumberOfTrailingZeros2) + iLog2 >= 64) {
                arrayList.add(BigInteger.valueOf(j2));
                j2 = 1;
            }
            j2 *= j5;
            iLog2 = LongMath.log2(j2, RoundingMode.FLOOR) + i4;
            j3 = j4 + 1;
            i = i4;
        }
        if (j2 > 1) {
            arrayList.add(BigInteger.valueOf(j2));
        }
        return listProduct(arrayList).shiftLeft(iNumberOfTrailingZeros);
    }

    static BigInteger listProduct(List<BigInteger> nums) {
        return listProduct(nums, 0, nums.size());
    }

    static BigInteger listProduct(List<BigInteger> nums, int start, int end) {
        int i = end - start;
        if (i == 0) {
            return BigInteger.ONE;
        }
        if (i == 1) {
            return nums.get(start);
        }
        if (i == 2) {
            return nums.get(start).multiply(nums.get(start + 1));
        }
        if (i == 3) {
            return nums.get(start).multiply(nums.get(start + 1)).multiply(nums.get(start + 2));
        }
        int i2 = (end + start) >>> 1;
        return listProduct(nums, start, i2).multiply(listProduct(nums, i2, end));
    }

    public static BigInteger binomial(int n, int k) {
        MathPreconditions.checkNonNegative("n", n);
        MathPreconditions.checkNonNegative("k", k);
        int i = 1;
        Preconditions.checkArgument(k <= n, "k (%s) > n (%s)", k, n);
        if (k > (n >> 1)) {
            k = n - k;
        }
        if (k < LongMath.biggestBinomials.length && n <= LongMath.biggestBinomials[k]) {
            return BigInteger.valueOf(LongMath.binomial(n, k));
        }
        BigInteger bigIntegerDivide = BigInteger.ONE;
        long j = n;
        int iLog2 = LongMath.log2(j, RoundingMode.CEILING);
        long j2 = 1;
        while (true) {
            int i2 = iLog2;
            while (i < k) {
                int i3 = n - i;
                i++;
                i2 += iLog2;
                if (i2 >= 63) {
                    bigIntegerDivide = bigIntegerDivide.multiply(BigInteger.valueOf(j)).divide(BigInteger.valueOf(j2));
                    j = i3;
                    j2 = i;
                } else {
                    j *= (long) i3;
                    j2 *= (long) i;
                }
            }
            return bigIntegerDivide.multiply(BigInteger.valueOf(j)).divide(BigInteger.valueOf(j2));
        }
    }

    static boolean fitsInLong(BigInteger x) {
        return x.bitLength() <= 63;
    }

    private BigIntegerMath() {
    }
}
