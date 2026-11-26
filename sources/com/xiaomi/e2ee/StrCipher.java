package com.xiaomi.e2ee;

import com.google.common.base.Ascii;
import com.xiaomi.e2ee.appkey.KeyEntry;
import com.xiaomi.e2ee.stat.MiCloudE2EEEventStatInjector;
import com.xiaomi.e2ee.utils.CipherUtils;
import com.xiaomi.e2ee.utils.E2EECommonUtils;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes4.dex */
class StrCipher extends E2EECipher<String> {
    private static final byte[] ENCRYPT_DATA_PREFIX = {1, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, 126};

    StrCipher() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.xiaomi.e2ee.E2EECipher
    public String decodeData(String str, KeyEntry keyEntry, int i) throws InterruptedException, E2EEException {
        try {
            Cipher decodeStrCipher = CipherUtils.getDecodeStrCipher(keyEntry, i);
            String strEncodeToString = E2EECommonUtils.encodeToString(str.getBytes());
            if (i == 1) {
                return E2EECommonUtils.encodeToString(addPrefix(decodeStrCipher.doFinal(E2EECommonUtils.decode(strEncodeToString))));
            }
            return new String(decodeStrCipher.doFinal(delPrefix(E2EECommonUtils.decode(str))));
        } catch (E2EEException e) {
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent(e);
            throw e;
        } catch (BadPaddingException | IllegalBlockSizeException e2) {
            E2EEException e2EEException = new E2EEException(E2EEException.DECODE_DATA_ERROR, e2);
            MiCloudE2EEEventStatInjector.getInstance().addDecodeDataErrorEvent(e2EEException);
            throw e2EEException;
        }
    }

    private byte[] addPrefix(byte[] bArr) {
        byte[] bArr2 = ENCRYPT_DATA_PREFIX;
        byte[] bArr3 = new byte[bArr2.length + bArr.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
        return bArr3;
    }

    private byte[] delPrefix(byte[] bArr) throws E2EEException {
        int length = bArr.length;
        byte[] bArr2 = ENCRYPT_DATA_PREFIX;
        if (length < bArr2.length) {
            throw new E2EEException(E2EEException.DECODE_DATA_ERROR, "encrypt data length too short");
        }
        if (!Arrays.equals(Arrays.copyOfRange(bArr, 0, bArr2.length), bArr2)) {
            throw new E2EEException(E2EEException.DECODE_DATA_ERROR, "need prefix");
        }
        return Arrays.copyOfRange(bArr, bArr2.length, bArr.length);
    }
}
