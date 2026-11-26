package net.lingala.zip4j.headers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.outputstream.CountingOutputStream;
import net.lingala.zip4j.io.outputstream.OutputStreamWithSplitZipSupport;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.ExtraDataRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;
import okhttp3.internal.ws.WebSocketProtocol;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
public class HeaderWriter {
    private static final short AES_EXTRA_DATA_RECORD_SIZE = 11;
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_FH = 28;
    private static final short ZIP64_EXTRA_DATA_RECORD_SIZE_LFH = 16;
    private final RawIO rawIO = new RawIO();
    private final byte[] longBuff = new byte[8];
    private final byte[] intBuff = new byte[4];

    public void writeLocalFileHeader(ZipModel zipModel, LocalFileHeader localFileHeader, OutputStream outputStream, Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            this.rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) localFileHeader.getSignature().getValue());
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(localFileHeader.getGeneralPurposeFlag());
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, localFileHeader.getCompressionMethod().getCode());
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            boolean z = localFileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || localFileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT;
            if (z) {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                zipModel.setZip64Format(true);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(true);
            } else {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                localFileHeader.setWriteCompressedSizeInZip64ExtraRecord(false);
            }
            byte[] bytesFromString = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(localFileHeader.getFileName())) {
                bytesFromString = HeaderUtil.getBytesFromString(localFileHeader.getFileName(), charset);
            }
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString.length);
            int i = z ? 20 : 0;
            if (localFileHeader.getAesExtraDataRecord() != null) {
                i += 11;
            }
            this.rawIO.writeShortLittleEndian(byteArrayOutputStream, i);
            if (bytesFromString.length > 0) {
                byteArrayOutputStream.write(bytesFromString);
            }
            if (z) {
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, 16);
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
            }
            if (localFileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) aesExtraDataRecord.getSignature().getValue());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getDataSize());
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getAesVersion().getVersionNumber());
                byteArrayOutputStream.write(aesExtraDataRecord.getVendorID().getBytes());
                byteArrayOutputStream.write(new byte[]{(byte) aesExtraDataRecord.getAesKeyStrength().getRawCode()});
                this.rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getCompressionMethod().getCode());
            }
            outputStream.write(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public void writeExtendedLocalHeader(LocalFileHeader localFileHeader, OutputStream outputStream) throws IOException {
        if (localFileHeader == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot write extended local header");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            this.rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.EXTRA_DATA_RECORD.getValue());
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            if (localFileHeader.isWriteCompressedSizeInZip64ExtraRecord()) {
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getCompressedSize());
                this.rawIO.writeLongLittleEndian(byteArrayOutputStream, localFileHeader.getUncompressedSize());
            } else {
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                this.rawIO.writeLongLittleEndian(this.longBuff, 0, localFileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
            }
            outputStream.write(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public void finalizeZipFile(ZipModel zipModel, OutputStream outputStream, Charset charset) throws IOException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            processHeaderData(zipModel, outputStream);
            long offsetOfCentralDirectory = getOffsetOfCentralDirectory(zipModel);
            writeCentralDirectory(zipModel, byteArrayOutputStream, this.rawIO, charset);
            int size = byteArrayOutputStream.size();
            if (zipModel.isZip64Format() || offsetOfCentralDirectory >= InternalZipConstants.ZIP_64_SIZE_LIMIT || zipModel.getCentralDirectory().getFileHeaders().size() >= 65535) {
                if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                    zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
                }
                if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                    zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
                }
                zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(((long) size) + offsetOfCentralDirectory);
                if (isSplitZipFile(outputStream)) {
                    int currentSplitFileCounter = getCurrentSplitFileCounter(outputStream);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(currentSplitFileCounter);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
                } else {
                    zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(0);
                    zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(1);
                }
                Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecordBuildZip64EndOfCentralDirectoryRecord = buildZip64EndOfCentralDirectoryRecord(zipModel, size, offsetOfCentralDirectory);
                zipModel.setZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecordBuildZip64EndOfCentralDirectoryRecord);
                writeZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecordBuildZip64EndOfCentralDirectoryRecord, byteArrayOutputStream, this.rawIO);
                writeZip64EndOfCentralDirectoryLocator(zipModel.getZip64EndOfCentralDirectoryLocator(), byteArrayOutputStream, this.rawIO);
            }
            writeEndOfCentralDirectoryRecord(zipModel, size, offsetOfCentralDirectory, byteArrayOutputStream, this.rawIO, charset);
            writeZipHeaderBytes(zipModel, outputStream, byteArrayOutputStream.toByteArray(), charset);
            byteArrayOutputStream.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                    throw th2;
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                    throw th2;
                }
            }
        }
    }

    public void finalizeZipFileWithoutValidations(ZipModel zipModel, OutputStream outputStream, Charset charset) throws IOException {
        if (zipModel == null || outputStream == null) {
            throw new ZipException("input parameters is null, cannot finalize zip file without validations");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            long offsetOfStartOfCentralDirectory = zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
            writeCentralDirectory(zipModel, byteArrayOutputStream, this.rawIO, charset);
            int size = byteArrayOutputStream.size();
            if (zipModel.isZip64Format() || offsetOfStartOfCentralDirectory >= InternalZipConstants.ZIP_64_SIZE_LIMIT || zipModel.getCentralDirectory().getFileHeaders().size() >= 65535) {
                if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                    zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
                }
                if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                    zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
                }
                zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(((long) size) + offsetOfStartOfCentralDirectory);
                Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecordBuildZip64EndOfCentralDirectoryRecord = buildZip64EndOfCentralDirectoryRecord(zipModel, size, offsetOfStartOfCentralDirectory);
                zipModel.setZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecordBuildZip64EndOfCentralDirectoryRecord);
                writeZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryRecordBuildZip64EndOfCentralDirectoryRecord, byteArrayOutputStream, this.rawIO);
                writeZip64EndOfCentralDirectoryLocator(zipModel.getZip64EndOfCentralDirectoryLocator(), byteArrayOutputStream, this.rawIO);
            }
            writeEndOfCentralDirectoryRecord(zipModel, size, offsetOfStartOfCentralDirectory, byteArrayOutputStream, this.rawIO, charset);
            writeZipHeaderBytes(zipModel, outputStream, byteArrayOutputStream.toByteArray(), charset);
            byteArrayOutputStream.close();
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                    throw th2;
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                    throw th2;
                }
            }
        }
    }

    public void updateLocalFileHeader(FileHeader fileHeader, ZipModel zipModel, SplitOutputStream splitOutputStream) throws IOException {
        SplitOutputStream splitOutputStream2;
        boolean z;
        String str;
        String str2;
        if (fileHeader == null || zipModel == null) {
            throw new ZipException("invalid input parameters, cannot update local file header");
        }
        if (fileHeader.getDiskNumberStart() != splitOutputStream.getCurrentSplitFileCounter()) {
            String parent = zipModel.getZipFile().getParent();
            String zipFileNameWithoutExtension = FileUtils.getZipFileNameWithoutExtension(zipModel.getZipFile().getName());
            if (parent == null) {
                str = "";
            } else {
                str = parent + System.getProperty(SystemProperties.FILE_SEPARATOR);
            }
            z = true;
            if (fileHeader.getDiskNumberStart() < 9) {
                str2 = str + zipFileNameWithoutExtension + ".z0" + (fileHeader.getDiskNumberStart() + 1);
            } else {
                str2 = str + zipFileNameWithoutExtension + ".z" + (fileHeader.getDiskNumberStart() + 1);
            }
            splitOutputStream2 = new SplitOutputStream(new File(str2));
        } else {
            splitOutputStream2 = splitOutputStream;
            z = false;
        }
        long filePointer = splitOutputStream2.getFilePointer();
        splitOutputStream2.seek(fileHeader.getOffsetLocalHeader() + 14);
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCrc());
        splitOutputStream2.write(this.longBuff, 0, 4);
        updateFileSizesInLocalFileHeader(splitOutputStream2, fileHeader);
        if (z) {
            splitOutputStream2.close();
        } else {
            splitOutputStream.seek(filePointer);
        }
    }

    private void updateFileSizesInLocalFileHeader(SplitOutputStream splitOutputStream, FileHeader fileHeader) throws IOException {
        if (fileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT) {
            this.rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
            splitOutputStream.write(this.longBuff, 0, 4);
            splitOutputStream.write(this.longBuff, 0, 4);
            int fileNameLength = fileHeader.getFileNameLength() + 8;
            if (splitOutputStream.skipBytes(fileNameLength) != fileNameLength) {
                throw new ZipException("Unable to skip " + fileNameLength + " bytes to update LFH");
            }
            this.rawIO.writeLongLittleEndian(splitOutputStream, fileHeader.getUncompressedSize());
            this.rawIO.writeLongLittleEndian(splitOutputStream, fileHeader.getCompressedSize());
            return;
        }
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCompressedSize());
        splitOutputStream.write(this.longBuff, 0, 4);
        this.rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getUncompressedSize());
        splitOutputStream.write(this.longBuff, 0, 4);
    }

    private boolean isSplitZipFile(OutputStream outputStream) {
        if (outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream) outputStream).isSplitZipFile();
        }
        if (outputStream instanceof CountingOutputStream) {
            return ((CountingOutputStream) outputStream).isSplitZipFile();
        }
        return false;
    }

    private int getCurrentSplitFileCounter(OutputStream outputStream) {
        if (outputStream instanceof SplitOutputStream) {
            return ((SplitOutputStream) outputStream).getCurrentSplitFileCounter();
        }
        return ((CountingOutputStream) outputStream).getCurrentSplitFileCounter();
    }

    private void writeZipHeaderBytes(ZipModel zipModel, OutputStream outputStream, byte[] bArr, Charset charset) throws IOException {
        if (bArr == null) {
            throw new ZipException("invalid buff to write as zip headers");
        }
        if ((outputStream instanceof CountingOutputStream) && ((CountingOutputStream) outputStream).checkBuffSizeAndStartNextSplitFile(bArr.length)) {
            finalizeZipFile(zipModel, outputStream, charset);
        } else {
            outputStream.write(bArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void processHeaderData(ZipModel zipModel, OutputStream outputStream) throws IOException {
        int currentSplitFileCounter;
        if (outputStream instanceof OutputStreamWithSplitZipSupport) {
            OutputStreamWithSplitZipSupport outputStreamWithSplitZipSupport = (OutputStreamWithSplitZipSupport) outputStream;
            zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(outputStreamWithSplitZipSupport.getFilePointer());
            currentSplitFileCounter = outputStreamWithSplitZipSupport.getCurrentSplitFileCounter();
        } else {
            currentSplitFileCounter = 0;
        }
        if (zipModel.isZip64Format()) {
            if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
                zipModel.setZip64EndOfCentralDirectoryRecord(new Zip64EndOfCentralDirectoryRecord());
            }
            if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
                zipModel.setZip64EndOfCentralDirectoryLocator(new Zip64EndOfCentralDirectoryLocator());
            }
            zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory());
            zipModel.getZip64EndOfCentralDirectoryLocator().setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(currentSplitFileCounter);
            zipModel.getZip64EndOfCentralDirectoryLocator().setTotalNumberOfDiscs(currentSplitFileCounter + 1);
        }
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDisk(currentSplitFileCounter);
        zipModel.getEndOfCentralDirectoryRecord().setNumberOfThisDiskStartOfCentralDir(currentSplitFileCounter);
    }

    private void writeCentralDirectory(ZipModel zipModel, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws ZipException {
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() <= 0) {
            return;
        }
        Iterator<FileHeader> it = zipModel.getCentralDirectory().getFileHeaders().iterator();
        while (it.hasNext()) {
            writeFileHeader(zipModel, it.next(), byteArrayOutputStream, rawIO, charset);
        }
    }

    private void writeFileHeader(ZipModel zipModel, FileHeader fileHeader, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("input parameters is null, cannot write local file header");
        }
        try {
            byte[] bArr = {0, 0};
            boolean zIsZip64Entry = isZip64Entry(fileHeader);
            rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) fileHeader.getSignature().getValue());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionMadeBy());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getVersionNeededToExtract());
            byteArrayOutputStream.write(fileHeader.getGeneralPurposeFlag());
            rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getCompressionMethod().getCode());
            rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getLastModifiedTime());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCrc());
            byteArrayOutputStream.write(this.longBuff, 0, 4);
            if (zIsZip64Entry) {
                rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                zipModel.setZip64Format(true);
            } else {
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getCompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getUncompressedSize());
                byteArrayOutputStream.write(this.longBuff, 0, 4);
            }
            byte[] bytesFromString = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileHeader.getFileName())) {
                bytesFromString = HeaderUtil.getBytesFromString(fileHeader.getFileName(), charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString.length);
            byte[] bArr2 = new byte[4];
            if (zIsZip64Entry) {
                rawIO.writeLongLittleEndian(this.longBuff, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
                System.arraycopy(this.longBuff, 0, bArr2, 0, 4);
            } else {
                rawIO.writeLongLittleEndian(this.longBuff, 0, fileHeader.getOffsetLocalHeader());
                System.arraycopy(this.longBuff, 0, bArr2, 0, 4);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, calculateExtraDataRecordsSize(fileHeader, zIsZip64Entry));
            String fileComment = fileHeader.getFileComment();
            byte[] bytesFromString2 = new byte[0];
            if (Zip4jUtil.isStringNotNullAndNotEmpty(fileComment)) {
                bytesFromString2 = HeaderUtil.getBytesFromString(fileComment, charset);
            }
            rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString2.length);
            if (zIsZip64Entry) {
                rawIO.writeIntLittleEndian(this.intBuff, 0, 65535);
                byteArrayOutputStream.write(this.intBuff, 0, 2);
            } else {
                rawIO.writeShortLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            byteArrayOutputStream.write(bArr);
            byteArrayOutputStream.write(fileHeader.getExternalFileAttributes());
            byteArrayOutputStream.write(bArr2);
            if (bytesFromString.length > 0) {
                byteArrayOutputStream.write(bytesFromString);
            }
            if (zIsZip64Entry) {
                zipModel.setZip64Format(true);
                rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, 28);
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getUncompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getCompressedSize());
                rawIO.writeLongLittleEndian(byteArrayOutputStream, fileHeader.getOffsetLocalHeader());
                rawIO.writeIntLittleEndian(byteArrayOutputStream, fileHeader.getDiskNumberStart());
            }
            if (fileHeader.getAesExtraDataRecord() != null) {
                AESExtraDataRecord aesExtraDataRecord = fileHeader.getAesExtraDataRecord();
                rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) aesExtraDataRecord.getSignature().getValue());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getDataSize());
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getAesVersion().getVersionNumber());
                byteArrayOutputStream.write(aesExtraDataRecord.getVendorID().getBytes());
                byteArrayOutputStream.write(new byte[]{(byte) aesExtraDataRecord.getAesKeyStrength().getRawCode()});
                rawIO.writeShortLittleEndian(byteArrayOutputStream, aesExtraDataRecord.getCompressionMethod().getCode());
            }
            writeRemainingExtraDataRecordsIfPresent(fileHeader, byteArrayOutputStream);
            if (bytesFromString2.length > 0) {
                byteArrayOutputStream.write(bytesFromString2);
            }
        } catch (Exception e) {
            throw new ZipException(e);
        }
    }

    private int calculateExtraDataRecordsSize(FileHeader fileHeader, boolean z) {
        int sizeOfData = z ? 32 : 0;
        if (fileHeader.getAesExtraDataRecord() != null) {
            sizeOfData += 11;
        }
        if (fileHeader.getExtraDataRecords() != null) {
            for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
                if (extraDataRecord.getHeader() != HeaderSignature.AES_EXTRA_DATA_RECORD.getValue() && extraDataRecord.getHeader() != HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                    sizeOfData += extraDataRecord.getSizeOfData() + 4;
                }
            }
        }
        return sizeOfData;
    }

    private void writeRemainingExtraDataRecordsIfPresent(FileHeader fileHeader, OutputStream outputStream) throws IOException {
        if (fileHeader.getExtraDataRecords() == null || fileHeader.getExtraDataRecords().size() == 0) {
            return;
        }
        for (ExtraDataRecord extraDataRecord : fileHeader.getExtraDataRecords()) {
            if (extraDataRecord.getHeader() != HeaderSignature.AES_EXTRA_DATA_RECORD.getValue() && extraDataRecord.getHeader() != HeaderSignature.ZIP64_EXTRA_FIELD_SIGNATURE.getValue()) {
                this.rawIO.writeShortLittleEndian(outputStream, (int) extraDataRecord.getHeader());
                this.rawIO.writeShortLittleEndian(outputStream, extraDataRecord.getSizeOfData());
                if (extraDataRecord.getSizeOfData() > 0 && extraDataRecord.getData() != null) {
                    outputStream.write(extraDataRecord.getData());
                }
            }
        }
    }

    private void writeZip64EndOfCentralDirectoryRecord(Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) zip64EndOfCentralDirectoryRecord.getSignature().getValue());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfZip64EndCentralDirectoryRecord());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionMadeBy());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getVersionNeededToExtract());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDisk());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getNumberOfThisDiskStartOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getSizeOfCentralDirectory());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryRecord.getOffsetStartCentralDirectoryWRTStartDiskNumber());
    }

    private void writeZip64EndOfCentralDirectoryLocator(Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO) throws IOException {
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_LOCATOR.getValue());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord());
        rawIO.writeLongLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getOffsetZip64EndOfCentralDirectoryRecord());
        rawIO.writeIntLittleEndian(byteArrayOutputStream, zip64EndOfCentralDirectoryLocator.getTotalNumberOfDiscs());
    }

    private void writeEndOfCentralDirectoryRecord(ZipModel zipModel, int i, long j, ByteArrayOutputStream byteArrayOutputStream, RawIO rawIO, Charset charset) throws IOException {
        byte[] bArr = new byte[8];
        rawIO.writeIntLittleEndian(byteArrayOutputStream, (int) HeaderSignature.END_OF_CENTRAL_DIRECTORY.getValue());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        rawIO.writeShortLittleEndian(byteArrayOutputStream, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());
        long size = zipModel.getCentralDirectory().getFileHeaders().size();
        long jCountNumberOfFileHeaderEntriesOnDisk = zipModel.isSplitArchive() ? countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk()) : size;
        if (jCountNumberOfFileHeaderEntriesOnDisk > WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            jCountNumberOfFileHeaderEntriesOnDisk = 65535;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) jCountNumberOfFileHeaderEntriesOnDisk);
        if (size > WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            size = 65535;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, (int) size);
        rawIO.writeIntLittleEndian(byteArrayOutputStream, i);
        if (j > InternalZipConstants.ZIP_64_SIZE_LIMIT) {
            rawIO.writeLongLittleEndian(bArr, 0, InternalZipConstants.ZIP_64_SIZE_LIMIT);
            byteArrayOutputStream.write(bArr, 0, 4);
        } else {
            rawIO.writeLongLittleEndian(bArr, 0, j);
            byteArrayOutputStream.write(bArr, 0, 4);
        }
        String comment = zipModel.getEndOfCentralDirectoryRecord().getComment();
        if (Zip4jUtil.isStringNotNullAndNotEmpty(comment)) {
            byte[] bytesFromString = HeaderUtil.getBytesFromString(comment, charset);
            rawIO.writeShortLittleEndian(byteArrayOutputStream, bytesFromString.length);
            byteArrayOutputStream.write(bytesFromString);
            return;
        }
        rawIO.writeShortLittleEndian(byteArrayOutputStream, 0);
    }

    private long countNumberOfFileHeaderEntriesOnDisk(List<FileHeader> list, int i) throws ZipException {
        if (list == null) {
            throw new ZipException("file headers are null, cannot calculate number of entries on this disk");
        }
        Iterator<FileHeader> it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().getDiskNumberStart() == i) {
                i2++;
            }
        }
        return i2;
    }

    private boolean isZip64Entry(FileHeader fileHeader) {
        return fileHeader.getCompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || fileHeader.getUncompressedSize() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || fileHeader.getOffsetLocalHeader() >= InternalZipConstants.ZIP_64_SIZE_LIMIT || fileHeader.getDiskNumberStart() >= 65535;
    }

    private long getOffsetOfCentralDirectory(ZipModel zipModel) {
        if (zipModel.isZip64Format() && zipModel.getZip64EndOfCentralDirectoryRecord() != null && zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() != -1) {
            return zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber();
        }
        return zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
    }

    private Zip64EndOfCentralDirectoryRecord buildZip64EndOfCentralDirectoryRecord(ZipModel zipModel, int i, long j) throws ZipException {
        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = new Zip64EndOfCentralDirectoryRecord();
        zip64EndOfCentralDirectoryRecord.setSignature(HeaderSignature.ZIP64_END_CENTRAL_DIRECTORY_RECORD);
        zip64EndOfCentralDirectoryRecord.setSizeOfZip64EndCentralDirectoryRecord(44L);
        if (zipModel.getCentralDirectory() != null && zipModel.getCentralDirectory().getFileHeaders() != null && zipModel.getCentralDirectory().getFileHeaders().size() > 0) {
            FileHeader fileHeader = zipModel.getCentralDirectory().getFileHeaders().get(0);
            zip64EndOfCentralDirectoryRecord.setVersionMadeBy(fileHeader.getVersionMadeBy());
            zip64EndOfCentralDirectoryRecord.setVersionNeededToExtract(fileHeader.getVersionNeededToExtract());
        }
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDiskStartOfCentralDir());
        long size = zipModel.getCentralDirectory().getFileHeaders().size();
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(zipModel.isSplitArchive() ? countNumberOfFileHeaderEntriesOnDisk(zipModel.getCentralDirectory().getFileHeaders(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk()) : size);
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(size);
        zip64EndOfCentralDirectoryRecord.setSizeOfCentralDirectory(i);
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(j);
        return zip64EndOfCentralDirectoryRecord;
    }
}
