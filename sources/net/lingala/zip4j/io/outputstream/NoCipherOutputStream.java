package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.io.OutputStream;
import net.lingala.zip4j.crypto.Encrypter;
import net.lingala.zip4j.model.ZipParameters;

/* JADX INFO: loaded from: classes6.dex */
class NoCipherOutputStream extends CipherOutputStream<NoEncrypter> {
    public NoCipherOutputStream(ZipEntryOutputStream zipEntryOutputStream, ZipParameters zipParameters, char[] cArr) throws IOException {
        super(zipEntryOutputStream, zipParameters, cArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.io.outputstream.CipherOutputStream
    public NoEncrypter initializeEncrypter(OutputStream outputStream, ZipParameters zipParameters, char[] cArr) {
        return new NoEncrypter();
    }

    static class NoEncrypter implements Encrypter {
        @Override // net.lingala.zip4j.crypto.Encrypter
        public int encryptData(byte[] bArr, int i, int i2) {
            return i2;
        }

        NoEncrypter() {
        }

        @Override // net.lingala.zip4j.crypto.Encrypter
        public int encryptData(byte[] bArr) {
            return encryptData(bArr, 0, bArr.length);
        }
    }
}
