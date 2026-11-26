package net.lingala.zip4j.crypto;

import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.enums.AesKeyStrength;

/* JADX INFO: loaded from: classes6.dex */
public class AesCipherUtil {
    private static final int START_INDEX = 0;

    public static byte[] derivePasswordBasedKey(byte[] bArr, char[] cArr, AesKeyStrength aesKeyStrength) throws ZipException {
        PBKDF2Engine pBKDF2Engine = new PBKDF2Engine(new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", bArr, 1000));
        int keyLength = aesKeyStrength.getKeyLength();
        int macLength = aesKeyStrength.getMacLength();
        int i = keyLength + macLength + 2;
        byte[] bArrDeriveKey = pBKDF2Engine.deriveKey(cArr, i);
        if (bArrDeriveKey == null || bArrDeriveKey.length != i) {
            throw new ZipException(String.format("Derived Key invalid for Key Length [%d] MAC Length [%d]", Integer.valueOf(keyLength), Integer.valueOf(macLength)));
        }
        return bArrDeriveKey;
    }

    public static byte[] derivePasswordVerifier(byte[] bArr, AesKeyStrength aesKeyStrength) {
        byte[] bArr2 = new byte[2];
        System.arraycopy(bArr, aesKeyStrength.getKeyLength() + aesKeyStrength.getMacLength(), bArr2, 0, 2);
        return bArr2;
    }

    public static MacBasedPRF getMacBasedPRF(byte[] bArr, AesKeyStrength aesKeyStrength) {
        int macLength = aesKeyStrength.getMacLength();
        byte[] bArr2 = new byte[macLength];
        System.arraycopy(bArr, aesKeyStrength.getKeyLength(), bArr2, 0, macLength);
        MacBasedPRF macBasedPRF = new MacBasedPRF("HmacSHA1");
        macBasedPRF.init(bArr2);
        return macBasedPRF;
    }

    public static AESEngine getAESEngine(byte[] bArr, AesKeyStrength aesKeyStrength) throws ZipException {
        int keyLength = aesKeyStrength.getKeyLength();
        byte[] bArr2 = new byte[keyLength];
        System.arraycopy(bArr, 0, bArr2, 0, keyLength);
        return new AESEngine(bArr2);
    }

    public static void prepareBuffAESIVBytes(byte[] bArr, int i) {
        bArr[0] = (byte) i;
        bArr[1] = (byte) (i >> 8);
        bArr[2] = (byte) (i >> 16);
        bArr[3] = (byte) (i >> 24);
        for (int i2 = 4; i2 <= 15; i2++) {
            bArr[i2] = 0;
        }
    }
}
