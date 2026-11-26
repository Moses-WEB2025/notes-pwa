package net.lingala.zip4j.crypto.PBKDF2;

import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
public class PBKDF2Engine {
    private PBKDF2Parameters parameters;
    private PRF prf;

    public PBKDF2Engine(PBKDF2Parameters pBKDF2Parameters) {
        this(pBKDF2Parameters, null);
    }

    public PBKDF2Engine(PBKDF2Parameters pBKDF2Parameters, PRF prf) {
        this.parameters = pBKDF2Parameters;
        this.prf = prf;
    }

    public byte[] deriveKey(char[] cArr) {
        return deriveKey(cArr, 0);
    }

    public byte[] deriveKey(char[] cArr, int i) {
        cArr.getClass();
        assertPRF(Zip4jUtil.convertCharArrayToByteArray(cArr));
        if (i == 0) {
            i = this.prf.getHLen();
        }
        return PBKDF2(this.prf, this.parameters.getSalt(), this.parameters.getIterationCount(), i);
    }

    public boolean verifyKey(char[] cArr) {
        byte[] bArrDeriveKey;
        byte[] derivedKey = getParameters().getDerivedKey();
        if (derivedKey == null || derivedKey.length == 0 || (bArrDeriveKey = deriveKey(cArr, derivedKey.length)) == null || bArrDeriveKey.length != derivedKey.length) {
            return false;
        }
        for (int i = 0; i < bArrDeriveKey.length; i++) {
            if (bArrDeriveKey[i] != derivedKey[i]) {
                return false;
            }
        }
        return true;
    }

    private void assertPRF(byte[] bArr) {
        if (this.prf == null) {
            this.prf = new MacBasedPRF(this.parameters.getHashAlgorithm());
        }
        this.prf.init(bArr);
    }

    public PRF getPseudoRandomFunction() {
        return this.prf;
    }

    private byte[] PBKDF2(PRF prf, byte[] bArr, int i, int i2) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        byte[] bArr2 = bArr;
        int hLen = prf.getHLen();
        int iCeil = ceil(i2, hLen);
        int i3 = i2 - ((iCeil - 1) * hLen);
        byte[] bArr3 = new byte[iCeil * hLen];
        int i4 = 0;
        for (int i5 = 1; i5 <= iCeil; i5++) {
            _F(bArr3, i4, prf, bArr2, i, i5);
            i4 += hLen;
        }
        if (i3 >= hLen) {
            return bArr3;
        }
        byte[] bArr4 = new byte[i2];
        System.arraycopy(bArr3, 0, bArr4, 0, i2);
        return bArr4;
    }

    private int ceil(int i, int i2) {
        return (i / i2) + (i % i2 > 0 ? 1 : 0);
    }

    private void _F(byte[] bArr, int i, PRF prf, byte[] bArr2, int i2, int i3) {
        int hLen = prf.getHLen();
        byte[] bArr3 = new byte[hLen];
        byte[] bArrDoFinal = new byte[bArr2.length + 4];
        System.arraycopy(bArr2, 0, bArrDoFinal, 0, bArr2.length);
        INT(bArrDoFinal, bArr2.length, i3);
        for (int i4 = 0; i4 < i2; i4++) {
            bArrDoFinal = prf.doFinal(bArrDoFinal);
            xor(bArr3, bArrDoFinal);
        }
        System.arraycopy(bArr3, 0, bArr, i, hLen);
    }

    private void xor(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
    }

    protected void INT(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 / 16777216);
        bArr[i + 1] = (byte) (i2 / 65536);
        bArr[i + 2] = (byte) (i2 / 256);
        bArr[i + 3] = (byte) i2;
    }

    public PBKDF2Parameters getParameters() {
        return this.parameters;
    }

    public void setParameters(PBKDF2Parameters pBKDF2Parameters) {
        this.parameters = pBKDF2Parameters;
    }

    public void setPseudoRandomFunction(PRF prf) {
        this.prf = prf;
    }
}
