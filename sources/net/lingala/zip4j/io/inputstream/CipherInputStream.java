package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import net.lingala.zip4j.crypto.Decrypter;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
abstract class CipherInputStream<T extends Decrypter> extends InputStream {
    private T decrypter;
    private byte[] lastReadRawDataCache;
    private LocalFileHeader localFileHeader;
    private byte[] singleByteBuffer = new byte[1];
    private ZipEntryInputStream zipEntryInputStream;

    protected void endOfEntryReached(InputStream inputStream) throws IOException {
    }

    protected abstract T initializeDecrypter(LocalFileHeader localFileHeader, char[] cArr) throws IOException;

    public CipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] cArr, int i) throws IOException {
        this.zipEntryInputStream = zipEntryInputStream;
        this.decrypter = (T) initializeDecrypter(localFileHeader, cArr);
        this.localFileHeader = localFileHeader;
        if (Zip4jUtil.getCompressionMethod(localFileHeader).equals(CompressionMethod.DEFLATE)) {
            this.lastReadRawDataCache = new byte[i];
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.singleByteBuffer) == -1) {
            return -1;
        }
        return this.singleByteBuffer[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int fully = Zip4jUtil.readFully(this.zipEntryInputStream, bArr, i, i2);
        if (fully > 0) {
            cacheRawData(bArr, fully);
            this.decrypter.decryptData(bArr, i, fully);
        }
        return fully;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.zipEntryInputStream.close();
    }

    public byte[] getLastReadRawDataCache() {
        return this.lastReadRawDataCache;
    }

    protected int readRaw(byte[] bArr) throws IOException {
        return this.zipEntryInputStream.readRawFully(bArr);
    }

    private void cacheRawData(byte[] bArr, int i) {
        byte[] bArr2 = this.lastReadRawDataCache;
        if (bArr2 != null) {
            System.arraycopy(bArr, 0, bArr2, 0, i);
        }
    }

    public T getDecrypter() {
        return this.decrypter;
    }

    protected long getNumberOfBytesReadForThisEntry() {
        return this.zipEntryInputStream.getNumberOfBytesRead();
    }

    public LocalFileHeader getLocalFileHeader() {
        return this.localFileHeader;
    }
}
