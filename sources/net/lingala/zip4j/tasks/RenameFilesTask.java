package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.RawIO;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
public class RenameFilesTask extends AbstractModifyFileTask<RenameFilesTaskParameters> {
    private final HeaderWriter headerWriter;
    private final RawIO rawIO;
    private final ZipModel zipModel;

    public RenameFilesTask(ZipModel zipModel, HeaderWriter headerWriter, RawIO rawIO, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.headerWriter = headerWriter;
        this.rawIO = rawIO;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(RenameFilesTaskParameters renameFilesTaskParameters, ProgressMonitor progressMonitor) throws IOException {
        RandomAccessFile randomAccessFile;
        Throwable th;
        SplitOutputStream splitOutputStream;
        Throwable th2;
        List<FileHeader> list;
        RenameFilesTask renameFilesTask = this;
        Map<String, String> mapFilterNonExistingEntriesAndAddSeparatorIfNeeded = renameFilesTask.filterNonExistingEntriesAndAddSeparatorIfNeeded(renameFilesTaskParameters.fileNamesMap);
        if (mapFilterNonExistingEntriesAndAddSeparatorIfNeeded.size() == 0) {
            return;
        }
        File temporaryFile = renameFilesTask.getTemporaryFile(renameFilesTask.zipModel.getZipFile().getPath());
        try {
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(renameFilesTask.zipModel.getZipFile(), RandomAccessFileMode.WRITE.getValue());
            try {
                try {
                    SplitOutputStream splitOutputStream2 = new SplitOutputStream(temporaryFile);
                    try {
                        Charset charset = renameFilesTaskParameters.zip4jConfig.getCharset();
                        List<FileHeader> listCloneAndSortFileHeadersByOffset = renameFilesTask.cloneAndSortFileHeadersByOffset(renameFilesTask.zipModel.getCentralDirectory().getFileHeaders());
                        long jCopyFile = 0;
                        for (FileHeader fileHeader : listCloneAndSortFileHeadersByOffset) {
                            Map.Entry<String, String> correspondingEntryFromMap = renameFilesTask.getCorrespondingEntryFromMap(fileHeader, mapFilterNonExistingEntriesAndAddSeparatorIfNeeded);
                            progressMonitor.setFileName(fileHeader.getFileName());
                            long offsetOfNextEntry = renameFilesTask.getOffsetOfNextEntry(listCloneAndSortFileHeadersByOffset, fileHeader, renameFilesTask.zipModel) - splitOutputStream2.getFilePointer();
                            if (correspondingEntryFromMap == null) {
                                jCopyFile += renameFilesTask.copyFile(randomAccessFile2, splitOutputStream2, jCopyFile, offsetOfNextEntry, progressMonitor, renameFilesTaskParameters.zip4jConfig.getBufferSize());
                                randomAccessFile = randomAccessFile2;
                                splitOutputStream = splitOutputStream2;
                                list = listCloneAndSortFileHeadersByOffset;
                            } else {
                                String newFileName = renameFilesTask.getNewFileName(correspondingEntryFromMap.getValue(), correspondingEntryFromMap.getKey(), fileHeader.getFileName());
                                randomAccessFile = randomAccessFile2;
                                try {
                                    byte[] bytesFromString = HeaderUtil.getBytesFromString(newFileName, charset);
                                    int length = bytesFromString.length - fileHeader.getFileNameLength();
                                    splitOutputStream = splitOutputStream2;
                                    List<FileHeader> list2 = listCloneAndSortFileHeadersByOffset;
                                    long jCopyEntryAndChangeFileName = renameFilesTask.copyEntryAndChangeFileName(bytesFromString, fileHeader, jCopyFile, offsetOfNextEntry, randomAccessFile, splitOutputStream, progressMonitor, renameFilesTaskParameters.zip4jConfig.getBufferSize());
                                    renameFilesTask = this;
                                    renameFilesTask.updateHeadersInZipModel(list2, fileHeader, newFileName, bytesFromString, length);
                                    list = list2;
                                    jCopyFile = jCopyEntryAndChangeFileName;
                                } catch (Throwable th3) {
                                    th = th3;
                                    splitOutputStream = splitOutputStream2;
                                    th2 = th;
                                    try {
                                        throw th2;
                                    } catch (Throwable th4) {
                                        try {
                                            splitOutputStream.close();
                                            throw th4;
                                        } catch (Throwable th5) {
                                            th2.addSuppressed(th5);
                                            throw th4;
                                        }
                                    }
                                }
                            }
                            try {
                                renameFilesTask.verifyIfTaskIsCancelled();
                                randomAccessFile2 = randomAccessFile;
                                splitOutputStream2 = splitOutputStream;
                                listCloneAndSortFileHeadersByOffset = list;
                            } catch (Throwable th6) {
                                th = th6;
                                th2 = th;
                                throw th2;
                            }
                        }
                        randomAccessFile = randomAccessFile2;
                        splitOutputStream = splitOutputStream2;
                        renameFilesTask.headerWriter.finalizeZipFile(renameFilesTask.zipModel, splitOutputStream, charset);
                        splitOutputStream.close();
                        randomAccessFile.close();
                        renameFilesTask.cleanupFile(true, renameFilesTask.zipModel.getZipFile(), temporaryFile);
                    } catch (Throwable th7) {
                        th = th7;
                        randomAccessFile = randomAccessFile2;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    randomAccessFile = randomAccessFile2;
                    th = th;
                    try {
                        throw th;
                    } catch (Throwable th9) {
                        try {
                            randomAccessFile.close();
                            throw th9;
                        } catch (Throwable th10) {
                            th.addSuppressed(th10);
                            throw th9;
                        }
                    }
                }
            } catch (Throwable th11) {
                th = th11;
                th = th;
                throw th;
            }
        } catch (Throwable th12) {
            renameFilesTask.cleanupFile(false, renameFilesTask.zipModel.getZipFile(), temporaryFile);
            throw th12;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(RenameFilesTaskParameters renameFilesTaskParameters) {
        return this.zipModel.getZipFile().length();
    }

    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.RENAME_FILE;
    }

    private long copyEntryAndChangeFileName(byte[] bArr, FileHeader fileHeader, long j, long j2, RandomAccessFile randomAccessFile, OutputStream outputStream, ProgressMonitor progressMonitor, int i) throws IOException {
        long jCopyFile = j + copyFile(randomAccessFile, outputStream, j, 26L, progressMonitor, i);
        this.rawIO.writeShortLittleEndian(outputStream, bArr.length);
        long j3 = jCopyFile + 2;
        long jCopyFile2 = j3 + copyFile(randomAccessFile, outputStream, j3, 2L, progressMonitor, i);
        outputStream.write(bArr);
        long fileNameLength = jCopyFile2 + ((long) fileHeader.getFileNameLength());
        return fileNameLength + copyFile(randomAccessFile, outputStream, fileNameLength, j2 - (fileNameLength - j), progressMonitor, i);
    }

    private Map.Entry<String, String> getCorrespondingEntryFromMap(FileHeader fileHeader, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (fileHeader.getFileName().startsWith(entry.getKey())) {
                return entry;
            }
        }
        return null;
    }

    private void updateHeadersInZipModel(List<FileHeader> list, FileHeader fileHeader, String str, byte[] bArr, int i) throws ZipException {
        FileHeader fileHeader2 = HeaderUtil.getFileHeader(this.zipModel, fileHeader.getFileName());
        if (fileHeader2 == null) {
            throw new ZipException("could not find any header with name: " + fileHeader.getFileName());
        }
        fileHeader2.setFileName(str);
        fileHeader2.setFileNameLength(bArr.length);
        long j = i;
        updateOffsetsForAllSubsequentFileHeaders(list, this.zipModel, fileHeader2, j);
        this.zipModel.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(this.zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory() + j);
        if (this.zipModel.isZip64Format()) {
            this.zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(this.zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() + j);
            this.zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(this.zipModel.getZip64EndOfCentralDirectoryLocator().getOffsetZip64EndOfCentralDirectoryRecord() + j);
        }
    }

    private Map<String, String> filterNonExistingEntriesAndAddSeparatorIfNeeded(Map<String, String> map) throws ZipException {
        FileHeader fileHeader;
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Zip4jUtil.isStringNotNullAndNotEmpty(entry.getKey()) && (fileHeader = HeaderUtil.getFileHeader(this.zipModel, entry.getKey())) != null) {
                if (fileHeader.isDirectory() && !entry.getValue().endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                    map2.put(entry.getKey(), entry.getValue() + InternalZipConstants.ZIP_FILE_SEPARATOR);
                } else {
                    map2.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return map2;
    }

    private String getNewFileName(String str, String str2, String str3) throws ZipException {
        if (str3.equals(str2)) {
            return str;
        }
        if (str3.startsWith(str2)) {
            return str + str3.substring(str2.length());
        }
        throw new ZipException("old file name was neither an exact match nor a partial match");
    }

    public static class RenameFilesTaskParameters extends AbstractZipTaskParameters {
        private final Map<String, String> fileNamesMap;

        public RenameFilesTaskParameters(Map<String, String> map, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.fileNamesMap = map;
        }
    }
}
