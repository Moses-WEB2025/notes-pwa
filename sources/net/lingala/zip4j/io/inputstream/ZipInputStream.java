package net.lingala.zip4j.io.inputstream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderReader;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.model.DataDescriptor;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ZipInputStream extends InputStream {
    private boolean canSkipExtendedLocalFileHeader;
    private CRC32 crc32;
    private DecompressedInputStream decompressedInputStream;
    private byte[] endOfEntryBuffer;
    private boolean entryEOFReached;
    private HeaderReader headerReader;
    private PushbackInputStream inputStream;
    private LocalFileHeader localFileHeader;
    private char[] password;
    private boolean streamClosed;
    private Zip4jConfig zip4jConfig;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ZipInputStream(InputStream inputStream) {
        this(inputStream, (char[]) null, (Charset) null);
    }

    public ZipInputStream(InputStream inputStream, Charset charset) {
        this(inputStream, (char[]) null, charset);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ZipInputStream(InputStream inputStream, char[] cArr) {
        this(inputStream, cArr, (Charset) null);
    }

    public ZipInputStream(InputStream inputStream, char[] cArr, Charset charset) {
        this(inputStream, cArr, new Zip4jConfig(charset, 4096));
    }

    public ZipInputStream(InputStream inputStream, char[] cArr, Zip4jConfig zip4jConfig) {
        this.headerReader = new HeaderReader();
        this.crc32 = new CRC32();
        this.canSkipExtendedLocalFileHeader = false;
        this.streamClosed = false;
        this.entryEOFReached = false;
        if (zip4jConfig.getBufferSize() < 512) {
            throw new IllegalArgumentException("Buffer size cannot be less than 512 bytes");
        }
        this.inputStream = new PushbackInputStream(inputStream, zip4jConfig.getBufferSize());
        this.password = cArr;
        this.zip4jConfig = zip4jConfig;
    }

    public LocalFileHeader getNextEntry() throws IOException {
        return getNextEntry(null);
    }

    public LocalFileHeader getNextEntry(FileHeader fileHeader) throws IOException {
        if (this.localFileHeader != null) {
            readUntilEndOfEntry();
        }
        LocalFileHeader localFileHeader = this.headerReader.readLocalFileHeader(this.inputStream, this.zip4jConfig.getCharset());
        this.localFileHeader = localFileHeader;
        if (localFileHeader == null) {
            return null;
        }
        verifyLocalFileHeader(localFileHeader);
        this.crc32.reset();
        if (fileHeader != null) {
            this.localFileHeader.setCrc(fileHeader.getCrc());
            this.localFileHeader.setCompressedSize(fileHeader.getCompressedSize());
            this.localFileHeader.setUncompressedSize(fileHeader.getUncompressedSize());
            this.localFileHeader.setDirectory(fileHeader.isDirectory());
            this.canSkipExtendedLocalFileHeader = true;
        } else {
            this.canSkipExtendedLocalFileHeader = false;
        }
        this.decompressedInputStream = initializeEntryInputStream(this.localFileHeader);
        this.entryEOFReached = false;
        return this.localFileHeader;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        byte[] bArr = new byte[1];
        if (read(bArr) == -1) {
            return -1;
        }
        return bArr[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 < 0) {
            throw new IllegalArgumentException("Negative read length");
        }
        if (i2 == 0) {
            return 0;
        }
        LocalFileHeader localFileHeader = this.localFileHeader;
        if (localFileHeader == null || localFileHeader.isDirectory()) {
            return -1;
        }
        try {
            int i3 = this.decompressedInputStream.read(bArr, i, i2);
            if (i3 == -1) {
                endOfCompressedDataReached();
                return i3;
            }
            this.crc32.update(bArr, i, i3);
            return i3;
        } catch (IOException e) {
            if (isEncryptionMethodZipStandard(this.localFileHeader)) {
                throw new ZipException(e.getMessage(), e.getCause(), ZipException.Type.WRONG_PASSWORD);
            }
            throw e;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        DecompressedInputStream decompressedInputStream = this.decompressedInputStream;
        if (decompressedInputStream != null) {
            decompressedInputStream.close();
        }
        this.streamClosed = true;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        assertStreamOpen();
        return !this.entryEOFReached ? 1 : 0;
    }

    public void setPassword(char[] cArr) {
        this.password = cArr;
    }

    private void endOfCompressedDataReached() throws IOException {
        this.decompressedInputStream.pushBackInputStreamIfNecessary(this.inputStream);
        this.decompressedInputStream.endOfEntryReached(this.inputStream);
        readExtendedLocalFileHeaderIfPresent();
        verifyCrc();
        resetFields();
        this.entryEOFReached = true;
    }

    private DecompressedInputStream initializeEntryInputStream(LocalFileHeader localFileHeader) throws IOException {
        return initializeDecompressorForThisEntry(initializeCipherInputStream(new ZipEntryInputStream(this.inputStream, getCompressedSize(localFileHeader)), localFileHeader), localFileHeader);
    }

    private CipherInputStream initializeCipherInputStream(ZipEntryInputStream zipEntryInputStream, LocalFileHeader localFileHeader) throws IOException {
        if (!localFileHeader.isEncrypted()) {
            return new NoCipherInputStream(zipEntryInputStream, localFileHeader, this.password, this.zip4jConfig.getBufferSize());
        }
        if (localFileHeader.getEncryptionMethod() == EncryptionMethod.AES) {
            return new AesCipherInputStream(zipEntryInputStream, localFileHeader, this.password, this.zip4jConfig.getBufferSize());
        }
        if (localFileHeader.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
            return new ZipStandardCipherInputStream(zipEntryInputStream, localFileHeader, this.password, this.zip4jConfig.getBufferSize());
        }
        throw new ZipException(String.format("Entry [%s] Strong Encryption not supported", localFileHeader.getFileName()), ZipException.Type.UNSUPPORTED_ENCRYPTION);
    }

    private DecompressedInputStream initializeDecompressorForThisEntry(CipherInputStream cipherInputStream, LocalFileHeader localFileHeader) {
        if (Zip4jUtil.getCompressionMethod(localFileHeader) == CompressionMethod.DEFLATE) {
            return new InflaterInputStream(cipherInputStream, this.zip4jConfig.getBufferSize());
        }
        return new StoreInputStream(cipherInputStream);
    }

    private void readExtendedLocalFileHeaderIfPresent() throws IOException {
        if (!this.localFileHeader.isDataDescriptorExists() || this.canSkipExtendedLocalFileHeader) {
            return;
        }
        DataDescriptor dataDescriptor = this.headerReader.readDataDescriptor(this.inputStream, checkIfZip64ExtraDataRecordPresentInLFH(this.localFileHeader.getExtraDataRecords()));
        this.localFileHeader.setCompressedSize(dataDescriptor.getCompressedSize());
        this.localFileHeader.setUncompressedSize(dataDescriptor.getUncompressedSize());
        this.localFileHeader.setCrc(dataDescriptor.getCrc());
    }

    private void verifyLocalFileHeader(LocalFileHeader localFileHeader) throws IOException {
        if (!isEntryDirectory(localFileHeader.getFileName()) && localFileHeader.getCompressionMethod() == CompressionMethod.STORE && localFileHeader.getUncompressedSize() < 0) {
            throw new IOException("Invalid local file header for: " + localFileHeader.getFileName() + ". Uncompressed size has to be set for entry of compression type store which is not a directory");
        }
    }

    private boolean checkIfZip64ExtraDataRecordPresentInLFH(List<ExtraDataRecord> list) {
        if (list == null) {
            return false;
        }
        Iterator<ExtraDataRecord> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getHeader() == HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                return true;
            }
        }
        return false;
    }

    private void verifyCrc() throws IOException {
        if ((this.localFileHeader.getEncryptionMethod() == EncryptionMethod.AES && this.localFileHeader.getAesExtraDataRecord().getAesVersion().equals(AesVersion.TWO)) || this.localFileHeader.getCrc() == this.crc32.getValue()) {
            return;
        }
        ZipException.Type type = ZipException.Type.CHECKSUM_MISMATCH;
        if (isEncryptionMethodZipStandard(this.localFileHeader)) {
            type = ZipException.Type.WRONG_PASSWORD;
        }
        throw new ZipException("Reached end of entry, but crc verification failed for " + this.localFileHeader.getFileName(), type);
    }

    private void resetFields() {
        this.localFileHeader = null;
        this.crc32.reset();
    }

    private boolean isEntryDirectory(String str) {
        return str.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR) || str.endsWith("\\");
    }

    private long getCompressedSize(LocalFileHeader localFileHeader) {
        if (Zip4jUtil.getCompressionMethod(localFileHeader).equals(CompressionMethod.STORE)) {
            return localFileHeader.getUncompressedSize();
        }
        if (!localFileHeader.isDataDescriptorExists() || this.canSkipExtendedLocalFileHeader) {
            return localFileHeader.getCompressedSize() - ((long) getEncryptionHeaderSize(localFileHeader));
        }
        return -1L;
    }

    private int getEncryptionHeaderSize(LocalFileHeader localFileHeader) {
        if (!localFileHeader.isEncrypted()) {
            return 0;
        }
        if (localFileHeader.getEncryptionMethod().equals(EncryptionMethod.AES)) {
            return localFileHeader.getAesExtraDataRecord().getAesKeyStrength().getSaltLength() + 12;
        }
        return localFileHeader.getEncryptionMethod().equals(EncryptionMethod.ZIP_STANDARD) ? 12 : 0;
    }

    private void readUntilEndOfEntry() throws IOException {
        if ((this.localFileHeader.isDirectory() || this.localFileHeader.getCompressedSize() == 0) && !this.localFileHeader.isDataDescriptorExists()) {
            return;
        }
        if (this.endOfEntryBuffer == null) {
            this.endOfEntryBuffer = new byte[512];
        }
        while (read(this.endOfEntryBuffer) != -1) {
        }
        this.entryEOFReached = true;
    }

    private boolean isEncryptionMethodZipStandard(LocalFileHeader localFileHeader) {
        return localFileHeader.isEncrypted() && EncryptionMethod.ZIP_STANDARD.equals(localFileHeader.getEncryptionMethod());
    }

    private void assertStreamOpen() throws IOException {
        if (this.streamClosed) {
            throw new IOException("Stream closed");
        }
    }
}
