package kotlin.collections;

import com.miui.common.stat.EditStat;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: UArraySorting.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\n\u0010\u000b\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\r\u0010\u000e\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0012\u0010\u0013\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0014\u0010\u0015\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u000b\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0010\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0015\u001a'\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", EditStat.ELEMENT_RIGHT, "partition-4UcCI2c", "([BII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort-Aa5vz7o", "([SII)V", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "quickSort-oBK06Vg", "([III)V", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "quickSort--nroSd4", "([JII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-Aa5vz7o", "sortArray-oBK06Vg", "sortArray--nroSd4", "kotlin-stdlib"}, k = 2, mv = {2, 2, 0}, xi = 48)
public final class UArraySortingKt {
    /* JADX INFO: renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m2992partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM2608getw2LRezQ = UByteArray.m2608getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                i3 = bM2608getw2LRezQ & 255;
                if (Intrinsics.compare(UByteArray.m2608getw2LRezQ(bArr, i) & 255, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m2608getw2LRezQ(bArr, i2) & 255, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM2608getw2LRezQ2 = UByteArray.m2608getw2LRezQ(bArr, i);
                UByteArray.m2613setVurrAj0(bArr, i, UByteArray.m2608getw2LRezQ(bArr, i2));
                UByteArray.m2613setVurrAj0(bArr, i2, bM2608getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m2996quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM2992partition4UcCI2c = m2992partition4UcCI2c(bArr, i, i2);
        int i3 = iM2992partition4UcCI2c - 1;
        if (i < i3) {
            m2996quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM2992partition4UcCI2c < i2) {
            m2996quickSort4UcCI2c(bArr, iM2992partition4UcCI2c, i2);
        }
    }

    /* JADX INFO: renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m2993partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM2871getMh2AYeg = UShortArray.m2871getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM2871getMh2AYeg = UShortArray.m2871getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM2871getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM2871getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m2871getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM2871getMh2AYeg2 = UShortArray.m2871getMh2AYeg(sArr, i);
                UShortArray.m2876set01HTLdE(sArr, i, UShortArray.m2871getMh2AYeg(sArr, i2));
                UShortArray.m2876set01HTLdE(sArr, i2, sM2871getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m2997quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM2993partitionAa5vz7o = m2993partitionAa5vz7o(sArr, i, i2);
        int i3 = iM2993partitionAa5vz7o - 1;
        if (i < i3) {
            m2997quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM2993partitionAa5vz7o < i2) {
            m2997quickSortAa5vz7o(sArr, iM2993partitionAa5vz7o, i2);
        }
    }

    /* JADX INFO: renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m2994partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM2687getpVg5ArA = UIntArray.m2687getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Integer.compareUnsigned(UIntArray.m2687getpVg5ArA(iArr, i), iM2687getpVg5ArA) < 0) {
                i++;
            }
            while (Integer.compareUnsigned(UIntArray.m2687getpVg5ArA(iArr, i2), iM2687getpVg5ArA) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM2687getpVg5ArA2 = UIntArray.m2687getpVg5ArA(iArr, i);
                UIntArray.m2692setVXSXFK8(iArr, i, UIntArray.m2687getpVg5ArA(iArr, i2));
                UIntArray.m2692setVXSXFK8(iArr, i2, iM2687getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m2998quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM2994partitionoBK06Vg = m2994partitionoBK06Vg(iArr, i, i2);
        int i3 = iM2994partitionoBK06Vg - 1;
        if (i < i3) {
            m2998quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM2994partitionoBK06Vg < i2) {
            m2998quickSortoBK06Vg(iArr, iM2994partitionoBK06Vg, i2);
        }
    }

    /* JADX INFO: renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m2991partitionnroSd4(long[] jArr, int i, int i2) {
        long jM2766getsVKNKU = ULongArray.m2766getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (Long.compareUnsigned(ULongArray.m2766getsVKNKU(jArr, i), jM2766getsVKNKU) < 0) {
                i++;
            }
            while (Long.compareUnsigned(ULongArray.m2766getsVKNKU(jArr, i2), jM2766getsVKNKU) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM2766getsVKNKU2 = ULongArray.m2766getsVKNKU(jArr, i);
                ULongArray.m2771setk8EXiF4(jArr, i, ULongArray.m2766getsVKNKU(jArr, i2));
                ULongArray.m2771setk8EXiF4(jArr, i2, jM2766getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* JADX INFO: renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m2995quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM2991partitionnroSd4 = m2991partitionnroSd4(jArr, i, i2);
        int i3 = iM2991partitionnroSd4 - 1;
        if (i < i3) {
            m2995quickSortnroSd4(jArr, i, i3);
        }
        if (iM2991partitionnroSd4 < i2) {
            m2995quickSortnroSd4(jArr, iM2991partitionnroSd4, i2);
        }
    }

    /* JADX INFO: renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m3000sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m2996quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m3001sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m2997quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m3002sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m2998quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* JADX INFO: renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m2999sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m2995quickSortnroSd4(array, i, i2 - 1);
    }
}
