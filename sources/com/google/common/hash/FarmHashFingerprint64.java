package com.google.common.hash;

import com.google.common.base.Preconditions;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes2.dex */
@ElementTypesAreNonnullByDefault
final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
    static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    private static long hashLength16(long u, long v, long mul) {
        long j = (u ^ v) * mul;
        long j2 = ((j ^ (j >>> 47)) ^ v) * mul;
        return (j2 ^ (j2 >>> 47)) * mul;
    }

    private static long shiftMix(long val) {
        return val ^ (val >>> 47);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    FarmHashFingerprint64() {
    }

    @Override // com.google.common.hash.AbstractNonStreamingHashFunction, com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] input, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, input.length);
        return HashCode.fromLong(fingerprint(input, off, len));
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }

    static long fingerprint(byte[] bytes, int offset, int length) {
        if (length <= 32) {
            if (length <= 16) {
                return hashLength0to16(bytes, offset, length);
            }
            return hashLength17to32(bytes, offset, length);
        }
        if (length <= 64) {
            return hashLength33To64(bytes, offset, length);
        }
        return hashLength65Plus(bytes, offset, length);
    }

    private static void weakHashLength32WithSeeds(byte[] bytes, int offset, long seedA, long seedB, long[] output) {
        long jLoad64 = LittleEndianByteArray.load64(bytes, offset);
        long jLoad65 = LittleEndianByteArray.load64(bytes, offset + 8);
        long jLoad66 = LittleEndianByteArray.load64(bytes, offset + 16);
        long jLoad67 = LittleEndianByteArray.load64(bytes, offset + 24);
        long j = seedA + jLoad64;
        long j2 = jLoad65 + j + jLoad66;
        long jRotateRight = Long.rotateRight(seedB + j + jLoad67, 21) + Long.rotateRight(j2, 44);
        output[0] = j2 + jLoad67;
        output[1] = jRotateRight + j;
    }

    private static long hashLength0to16(byte[] bytes, int offset, int length) {
        if (length >= 8) {
            long j = (((long) length) * 2) + K2;
            long jLoad64 = LittleEndianByteArray.load64(bytes, offset) + K2;
            long jLoad65 = LittleEndianByteArray.load64(bytes, (offset + length) - 8);
            return hashLength16((Long.rotateRight(jLoad65, 37) * j) + jLoad64, (Long.rotateRight(jLoad64, 25) + jLoad65) * j, j);
        }
        if (length >= 4) {
            return hashLength16(((long) length) + ((((long) LittleEndianByteArray.load32(bytes, offset)) & InternalZipConstants.ZIP_64_SIZE_LIMIT) << 3), ((long) LittleEndianByteArray.load32(bytes, (offset + length) - 4)) & InternalZipConstants.ZIP_64_SIZE_LIMIT, ((long) (length * 2)) + K2);
        }
        if (length <= 0) {
            return K2;
        }
        return shiftMix((((long) ((bytes[offset] & 255) + ((bytes[(length >> 1) + offset] & 255) << 8))) * K2) ^ (((long) (length + ((bytes[offset + (length - 1)] & 255) << 2))) * K0)) * K2;
    }

    private static long hashLength17to32(byte[] bytes, int offset, int length) {
        long j = (((long) length) * 2) + K2;
        long jLoad64 = LittleEndianByteArray.load64(bytes, offset) * K1;
        long jLoad65 = LittleEndianByteArray.load64(bytes, offset + 8);
        int i = offset + length;
        long jLoad66 = LittleEndianByteArray.load64(bytes, i - 8) * j;
        return hashLength16(Long.rotateRight(jLoad64 + jLoad65, 43) + Long.rotateRight(jLoad66, 30) + (LittleEndianByteArray.load64(bytes, i - 16) * K2), jLoad66 + jLoad64 + Long.rotateRight(jLoad65 + K2, 18), j);
    }

    private static long hashLength33To64(byte[] bytes, int offset, int length) {
        long j = (((long) length) * 2) + K2;
        long jLoad64 = LittleEndianByteArray.load64(bytes, offset) * K2;
        long jLoad65 = LittleEndianByteArray.load64(bytes, offset + 8);
        int i = offset + length;
        long jLoad66 = LittleEndianByteArray.load64(bytes, i - 8) * j;
        long jRotateRight = Long.rotateRight(jLoad64 + jLoad65, 43) + Long.rotateRight(jLoad66, 30) + (LittleEndianByteArray.load64(bytes, i - 16) * K2);
        long jHashLength16 = hashLength16(jRotateRight, jLoad66 + Long.rotateRight(jLoad65 + K2, 18) + jLoad64, j);
        long jLoad67 = LittleEndianByteArray.load64(bytes, offset + 16) * j;
        long jLoad68 = LittleEndianByteArray.load64(bytes, offset + 24);
        long jLoad69 = (jRotateRight + LittleEndianByteArray.load64(bytes, i - 32)) * j;
        return hashLength16(Long.rotateRight(jLoad67 + jLoad68, 43) + Long.rotateRight(jLoad69, 30) + ((jHashLength16 + LittleEndianByteArray.load64(bytes, i - 24)) * j), jLoad67 + Long.rotateRight(jLoad64 + jLoad68, 18) + jLoad69, j);
    }

    private static long hashLength65Plus(byte[] bytes, int offset, int length) {
        long j = 81;
        long j2 = K1;
        long j3 = (j * K1) + 113;
        long jShiftMix = shiftMix((j3 * K2) + 113) * K2;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        char c = 1;
        int i = length - 1;
        int i2 = offset + ((i / 64) * 64);
        int i3 = i & 63;
        int i4 = i2 + i3;
        int i5 = i4 - 63;
        long j4 = j3;
        long jLoad64 = (j * K2) + LittleEndianByteArray.load64(bytes, offset);
        int i6 = offset;
        while (true) {
            long j5 = j2;
            long jRotateRight = Long.rotateRight(jLoad64 + j4 + jArr[0] + LittleEndianByteArray.load64(bytes, i6 + 8), 37) * j5;
            long jRotateRight2 = Long.rotateRight(j4 + jArr[c] + LittleEndianByteArray.load64(bytes, i6 + 48), 42) * j5;
            long j6 = jRotateRight ^ jArr2[c];
            char c2 = c;
            long jLoad65 = jRotateRight2 + jArr[0] + LittleEndianByteArray.load64(bytes, i6 + 40);
            long jRotateRight3 = Long.rotateRight(jShiftMix + jArr2[0], 33) * j5;
            weakHashLength32WithSeeds(bytes, i6, jArr[c2] * j5, j6 + jArr2[0], jArr);
            int i7 = i6;
            long[] jArr3 = jArr;
            weakHashLength32WithSeeds(bytes, i7 + 32, jArr2[c2] + jRotateRight3, jLoad65 + LittleEndianByteArray.load64(bytes, i7 + 16), jArr2);
            i6 = i7 + 64;
            if (i6 == i2) {
                long j7 = ((j6 & 255) << c2) + j5;
                long j8 = jArr2[0] + ((long) i3);
                jArr2[0] = j8;
                long j9 = jArr3[0] + j8;
                jArr3[0] = j9;
                jArr2[0] = jArr2[0] + j9;
                long jRotateRight4 = Long.rotateRight(jRotateRight3 + jLoad65 + jArr3[0] + LittleEndianByteArray.load64(bytes, i4 - 55), 37) * j7;
                long jRotateRight5 = Long.rotateRight(jLoad65 + jArr3[c2] + LittleEndianByteArray.load64(bytes, i4 - 15), 42) * j7;
                long j10 = jRotateRight4 ^ (jArr2[c2] * 9);
                long jLoad66 = jRotateRight5 + (jArr3[0] * 9) + LittleEndianByteArray.load64(bytes, i4 - 23);
                long jRotateRight6 = Long.rotateRight(j6 + jArr2[0], 33) * j7;
                weakHashLength32WithSeeds(bytes, i5, jArr3[c2] * j7, jArr2[0] + j10, jArr3);
                weakHashLength32WithSeeds(bytes, i4 - 31, jArr2[c2] + jRotateRight6, LittleEndianByteArray.load64(bytes, i4 - 47) + jLoad66, jArr2);
                return hashLength16(hashLength16(jArr3[0], jArr2[0], j7) + (shiftMix(jLoad66) * K0) + j10, hashLength16(jArr3[c2], jArr2[c2], j7) + jRotateRight6, j7);
            }
            jLoad64 = jRotateRight3;
            j2 = j5;
            jShiftMix = j6;
            c = c2;
            j4 = jLoad65;
            jArr = jArr3;
        }
    }
}
