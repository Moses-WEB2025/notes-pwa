package com.google.common.hash;

import com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class Fingerprint2011 extends AbstractNonStreamingHashFunction {
    static final HashFunction FINGERPRINT_2011 = new Fingerprint2011();
    private static final long K0 = -6505348102511208375L;
    private static final long K1 = -8261664234251669945L;
    private static final long K2 = -4288712594273399085L;
    private static final long K3 = -4132994306676758123L;

    static long hash128to64(long high, long low) {
        long j = (low ^ high) * K3;
        long j2 = (high ^ (j ^ (j >>> 47))) * K3;
        return (j2 ^ (j2 >>> 47)) * K3;
    }

    private static long shiftMix(long val) {
        return val ^ (val >>> 47);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    Fingerprint2011() {
    }

    @Override // com.google.common.hash.AbstractNonStreamingHashFunction, com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] input, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, input.length);
        return HashCode.fromLong(fingerprint(input, off, len));
    }

    public String toString() {
        return "Hashing.fingerprint2011()";
    }

    static long fingerprint(byte[] bytes, int offset, int length) {
        long jFullFingerprint;
        if (length <= 32) {
            jFullFingerprint = murmurHash64WithSeed(bytes, offset, length, -1397348546323613475L);
        } else if (length <= 64) {
            jFullFingerprint = hashLength33To64(bytes, offset, length);
        } else {
            jFullFingerprint = fullFingerprint(bytes, offset, length);
        }
        long jLoad64 = K0;
        long jLoad65 = length >= 8 ? LittleEndianByteArray.load64(bytes, offset) : -6505348102511208375L;
        if (length >= 9) {
            jLoad64 = LittleEndianByteArray.load64(bytes, (offset + length) - 8);
        }
        long jHash128to64 = hash128to64(jFullFingerprint + jLoad64, jLoad65);
        return (jHash128to64 == 0 || jHash128to64 == 1) ? jHash128to64 - 2 : jHash128to64;
    }

    private static void weakHashLength32WithSeeds(byte[] bytes, int offset, long seedA, long seedB, long[] output) {
        long jLoad64 = LittleEndianByteArray.load64(bytes, offset);
        long jLoad65 = LittleEndianByteArray.load64(bytes, offset + 8);
        long jLoad66 = LittleEndianByteArray.load64(bytes, offset + 16);
        long jLoad67 = LittleEndianByteArray.load64(bytes, offset + 24);
        long j = seedA + jLoad64;
        long j2 = jLoad65 + j + jLoad66;
        long jRotateRight = Long.rotateRight(seedB + j + jLoad67, 51) + Long.rotateRight(j2, 23);
        output[0] = j2 + jLoad67;
        output[1] = jRotateRight + j;
    }

    private static long fullFingerprint(byte[] bytes, int offset, int length) {
        byte[] bArr = bytes;
        long jLoad64 = LittleEndianByteArray.load64(bytes, offset);
        int i = offset + length;
        long jLoad65 = LittleEndianByteArray.load64(bArr, i - 16) ^ K1;
        long jLoad66 = K0 ^ LittleEndianByteArray.load64(bArr, i - 56);
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long j = length;
        weakHashLength32WithSeeds(bArr, i - 64, j, jLoad65, jArr);
        weakHashLength32WithSeeds(bArr, i - 32, j * K1, K0, jArr2);
        long[] jArr3 = jArr2;
        long jShiftMix = jLoad66 + (shiftMix(jArr[1]) * K1);
        long jRotateRight = Long.rotateRight(jLoad64 + jShiftMix, 39) * K1;
        int i2 = (length - 1) & (-64);
        long jRotateRight2 = Long.rotateRight(jLoad65, 33) * K1;
        long j2 = jRotateRight;
        int i3 = offset;
        while (true) {
            long jRotateRight3 = Long.rotateRight(j2 + jRotateRight2 + jArr[0] + LittleEndianByteArray.load64(bArr, i3 + 16), 37) * K1;
            long jRotateRight4 = Long.rotateRight(jRotateRight2 + jArr[1] + LittleEndianByteArray.load64(bArr, i3 + 48), 42) * K1;
            long j3 = jArr3[1] ^ jRotateRight3;
            long j4 = jRotateRight4 ^ jArr[0];
            long jRotateRight5 = Long.rotateRight(jArr3[0] ^ jShiftMix, 33);
            weakHashLength32WithSeeds(bArr, i3, jArr[1] * K1, jArr3[0] + j3, jArr);
            int i4 = i3;
            long[] jArr4 = jArr3;
            jRotateRight2 = j4;
            weakHashLength32WithSeeds(bytes, i4 + 32, jArr3[1] + jRotateRight5, jRotateRight2, jArr4);
            i3 = i4 + 64;
            i2 -= 64;
            if (i2 == 0) {
                return hash128to64(hash128to64(jArr[0], jArr4[0]) + (shiftMix(jRotateRight2) * K1) + j3, hash128to64(jArr[1], jArr4[1]) + jRotateRight5);
            }
            bArr = bytes;
            jArr3 = jArr4;
            j2 = jRotateRight5;
            jShiftMix = j3;
        }
    }

    private static long hashLength33To64(byte[] bytes, int offset, int length) {
        long jLoad64 = LittleEndianByteArray.load64(bytes, offset + 24);
        int i = offset + length;
        int i2 = i - 16;
        long jLoad65 = LittleEndianByteArray.load64(bytes, offset) + ((((long) length) + LittleEndianByteArray.load64(bytes, i2)) * K0);
        long jRotateRight = Long.rotateRight(jLoad65 + jLoad64, 52);
        long jRotateRight2 = Long.rotateRight(jLoad65, 37);
        long jLoad66 = jLoad65 + LittleEndianByteArray.load64(bytes, offset + 8);
        long jRotateRight3 = jRotateRight2 + Long.rotateRight(jLoad66, 7);
        int i3 = offset + 16;
        long jLoad67 = jLoad66 + LittleEndianByteArray.load64(bytes, i3);
        long j = jLoad64 + jLoad67;
        long jRotateRight4 = jRotateRight + Long.rotateRight(jLoad67, 31) + jRotateRight3;
        long jLoad68 = LittleEndianByteArray.load64(bytes, i3) + LittleEndianByteArray.load64(bytes, i - 32);
        long jLoad69 = LittleEndianByteArray.load64(bytes, i - 8);
        long jRotateRight5 = Long.rotateRight(jLoad68 + jLoad69, 52);
        long jRotateRight6 = Long.rotateRight(jLoad68, 37);
        long jLoad610 = jLoad68 + LittleEndianByteArray.load64(bytes, i - 24);
        long jRotateRight7 = jRotateRight6 + Long.rotateRight(jLoad610, 7);
        long jLoad611 = jLoad610 + LittleEndianByteArray.load64(bytes, i2);
        return shiftMix((shiftMix(((j + jRotateRight5 + Long.rotateRight(jLoad611, 31) + jRotateRight7) * K2) + ((jLoad69 + jLoad611 + jRotateRight4) * K0)) * K0) + jRotateRight4) * K2;
    }

    static long murmurHash64WithSeed(byte[] bytes, int offset, int length, long seed) {
        int i = length & (-8);
        int i2 = length & 7;
        long jLoad64Safely = seed ^ (((long) length) * K3);
        for (int i3 = 0; i3 < i; i3 += 8) {
            jLoad64Safely = (jLoad64Safely ^ (shiftMix(LittleEndianByteArray.load64(bytes, offset + i3) * K3) * K3)) * K3;
        }
        if (i2 != 0) {
            jLoad64Safely = (LittleEndianByteArray.load64Safely(bytes, offset + i, i2) ^ jLoad64Safely) * K3;
        }
        return shiftMix(shiftMix(jLoad64Safely) * K3);
    }
}
