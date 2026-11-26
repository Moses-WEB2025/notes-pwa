package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import net.lingala.zip4j.crypto.AESDecrypter;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
class AesCipherInputStream extends CipherInputStream<AESDecrypter> {
    private byte[] aes16ByteBlock;
    private int aes16ByteBlockPointer;
    private int aes16ByteBlockReadLength;
    private int bytesCopiedInThisIteration;
    private int lengthToCopyInThisIteration;
    private int lengthToRead;
    private int offsetWithAesBlock;
    private int remainingAes16ByteBlockLength;
    private byte[] singleByteBuffer;

    public AesCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader, char[] cArr, int i) throws IOException {
        super(zipEntryInputStream, localFileHeader, cArr, i);
        this.singleByteBuffer = new byte[1];
        this.aes16ByteBlock = new byte[16];
        this.aes16ByteBlockPointer = 0;
        this.remainingAes16ByteBlockLength = 0;
        this.lengthToRead = 0;
        this.offsetWithAesBlock = 0;
        this.bytesCopiedInThisIteration = 0;
        this.lengthToCopyInThisIteration = 0;
        this.aes16ByteBlockReadLength = 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.io.inputstream.CipherInputStream
    public AESDecrypter initializeDecrypter(LocalFileHeader localFileHeader, char[] cArr) throws IOException {
        return new AESDecrypter(localFileHeader.getAesExtraDataRecord(), cArr, getSalt(localFileHeader), getPasswordVerifier());
    }

    @Override // net.lingala.zip4j.io.inputstream.CipherInputStream, java.io.InputStream
    public int read() throws IOException {
        if (read(this.singleByteBuffer) == -1) {
            return -1;
        }
        return this.singleByteBuffer[0];
    }

    @Override // net.lingala.zip4j.io.inputstream.CipherInputStream, java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // net.lingala.zip4j.io.inputstream.CipherInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.lengthToRead = i2;
        this.offsetWithAesBlock = i;
        this.bytesCopiedInThisIteration = 0;
        if (this.remainingAes16ByteBlockLength != 0) {
            copyBytesFromBuffer(bArr, i);
            int i3 = this.bytesCopiedInThisIteration;
            if (i3 == i2) {
                return i3;
            }
        }
        if (this.lengthToRead < 16) {
            byte[] bArr2 = this.aes16ByteBlock;
            int i4 = super.read(bArr2, 0, bArr2.length);
            this.aes16ByteBlockReadLength = i4;
            this.aes16ByteBlockPointer = 0;
            if (i4 == -1) {
                this.remainingAes16ByteBlockLength = 0;
                int i5 = this.bytesCopiedInThisIteration;
                if (i5 > 0) {
                    return i5;
                }
                return -1;
            }
            this.remainingAes16ByteBlockLength = i4;
            copyBytesFromBuffer(bArr, this.offsetWithAesBlock);
            int i6 = this.bytesCopiedInThisIteration;
            if (i6 == i2) {
                return i6;
            }
        }
        int i7 = this.offsetWithAesBlock;
        int i8 = this.lengthToRead;
        int i9 = super.read(bArr, i7, i8 - (i8 % 16));
        if (i9 == -1) {
            int i10 = this.bytesCopiedInThisIteration;
            if (i10 > 0) {
                return i10;
            }
            return -1;
        }
        return i9 + this.bytesCopiedInThisIteration;
    }

    private void copyBytesFromBuffer(byte[] bArr, int i) {
        int i2 = this.lengthToRead;
        int i3 = this.remainingAes16ByteBlockLength;
        if (i2 >= i3) {
            i2 = i3;
        }
        this.lengthToCopyInThisIteration = i2;
        System.arraycopy(this.aes16ByteBlock, this.aes16ByteBlockPointer, bArr, i, i2);
        incrementAesByteBlockPointer(this.lengthToCopyInThisIteration);
        decrementRemainingAesBytesLength(this.lengthToCopyInThisIteration);
        int i4 = this.bytesCopiedInThisIteration;
        int i5 = this.lengthToCopyInThisIteration;
        this.bytesCopiedInThisIteration = i4 + i5;
        this.lengthToRead -= i5;
        this.offsetWithAesBlock += i5;
    }

    @Override // net.lingala.zip4j.io.inputstream.CipherInputStream
    protected void endOfEntryReached(InputStream inputStream) throws IOException {
        verifyContent(readStoredMac(inputStream));
    }

    private void verifyContent(byte[] bArr) throws IOException {
        if (getLocalFileHeader().isDataDescriptorExists() && CompressionMethod.DEFLATE.equals(Zip4jUtil.getCompressionMethod(getLocalFileHeader()))) {
            return;
        }
        byte[] bArr2 = new byte[10];
        System.arraycopy(getDecrypter().getCalculatedAuthenticationBytes(), 0, bArr2, 0, 10);
        if (!Arrays.equals(bArr, bArr2)) {
            throw new IOException("Reached end of data for this entry, but aes verification failed");
        }
    }

    protected byte[] readStoredMac(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[10];
        if (Zip4jUtil.readFully(inputStream, bArr) == 10) {
            return bArr;
        }
        throw new ZipException("Invalid AES Mac bytes. Could not read sufficient data");
    }

    private byte[] getSalt(LocalFileHeader localFileHeader) throws IOException {
        if (localFileHeader.getAesExtraDataRecord() == null) {
            throw new IOException("invalid aes extra data record");
        }
        byte[] bArr = new byte[localFileHeader.getAesExtraDataRecord().getAesKeyStrength().getSaltLength()];
        readRaw(bArr);
        return bArr;
    }

    private byte[] getPasswordVerifier() throws IOException {
        byte[] bArr = new byte[2];
        readRaw(bArr);
        return bArr;
    }

    private void incrementAesByteBlockPointer(int i) {
        int i2 = this.aes16ByteBlockPointer + i;
        this.aes16ByteBlockPointer = i2;
        if (i2 >= 15) {
            this.aes16ByteBlockPointer = 15;
        }
    }

    private void decrementRemainingAesBytesLength(int i) {
        int i2 = this.remainingAes16ByteBlockLength - i;
        this.remainingAes16ByteBlockLength = i2;
        if (i2 <= 0) {
            this.remainingAes16ByteBlockLength = 0;
        }
    }
}
