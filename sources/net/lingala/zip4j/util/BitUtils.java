package net.lingala.zip4j.util;

/* JADX INFO: loaded from: classes6.dex */
public class BitUtils {
    public static boolean isBitSet(byte b, int i) {
        return ((1 << i) & ((long) b)) != 0;
    }

    public static byte setBit(byte b, int i) {
        return (byte) (b | (1 << i));
    }

    public static byte unsetBit(byte b, int i) {
        return (byte) (b & (~(1 << i)));
    }
}
