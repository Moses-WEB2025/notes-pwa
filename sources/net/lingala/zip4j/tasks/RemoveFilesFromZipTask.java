package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes6.dex */
public class RemoveFilesFromZipTask extends AbstractModifyFileTask<RemoveFilesFromZipTaskParameters> {
    private final HeaderWriter headerWriter;
    private final ZipModel zipModel;

    public RemoveFilesFromZipTask(ZipModel zipModel, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.headerWriter = headerWriter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(RemoveFilesFromZipTaskParameters removeFilesFromZipTaskParameters, ProgressMonitor progressMonitor) throws IOException {
        if (this.zipModel.isSplitArchive()) {
            throw new ZipException("This is a split archive. Zip file format does not allow updating split/spanned files");
        }
        List<String> listFilterNonExistingEntries = filterNonExistingEntries(removeFilesFromZipTaskParameters.filesToRemove);
        if (listFilterNonExistingEntries.isEmpty()) {
            return;
        }
        File temporaryFile = getTemporaryFile(this.zipModel.getZipFile().getPath());
        try {
            SplitOutputStream splitOutputStream = new SplitOutputStream(temporaryFile);
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(this.zipModel.getZipFile(), RandomAccessFileMode.READ.getValue());
                try {
                    List<FileHeader> listCloneAndSortFileHeadersByOffset = cloneAndSortFileHeadersByOffset(this.zipModel.getCentralDirectory().getFileHeaders());
                    long jCopyFile = 0;
                    for (FileHeader fileHeader : listCloneAndSortFileHeadersByOffset) {
                        long offsetOfNextEntry = getOffsetOfNextEntry(listCloneAndSortFileHeadersByOffset, fileHeader, this.zipModel) - splitOutputStream.getFilePointer();
                        if (shouldEntryBeRemoved(fileHeader, listFilterNonExistingEntries)) {
                            updateHeaders(listCloneAndSortFileHeadersByOffset, fileHeader, offsetOfNextEntry);
                            if (!this.zipModel.getCentralDirectory().getFileHeaders().remove(fileHeader)) {
                                throw new ZipException("Could not remove entry from list of central directory headers");
                            }
                            jCopyFile += offsetOfNextEntry;
                        } else {
                            jCopyFile += super.copyFile(randomAccessFile, splitOutputStream, jCopyFile, offsetOfNextEntry, progressMonitor, removeFilesFromZipTaskParameters.zip4jConfig.getBufferSize());
                        }
                        verifyIfTaskIsCancelled();
                    }
                    this.headerWriter.finalizeZipFile(this.zipModel, splitOutputStream, removeFilesFromZipTaskParameters.zip4jConfig.getCharset());
                    randomAccessFile.close();
                    splitOutputStream.close();
                    cleanupFile(true, this.zipModel.getZipFile(), temporaryFile);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            randomAccessFile.close();
                            throw th2;
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                            throw th2;
                        }
                    }
                }
            } catch (Throwable th4) {
                try {
                    throw th4;
                } catch (Throwable th5) {
                    try {
                        splitOutputStream.close();
                        throw th5;
                    } catch (Throwable th6) {
                        th4.addSuppressed(th6);
                        throw th5;
                    }
                }
            }
        } catch (Throwable th7) {
            cleanupFile(false, this.zipModel.getZipFile(), temporaryFile);
            throw th7;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(RemoveFilesFromZipTaskParameters removeFilesFromZipTaskParameters) {
        return this.zipModel.getZipFile().length();
    }

    private List<String> filterNonExistingEntries(List<String> list) throws ZipException {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (HeaderUtil.getFileHeader(this.zipModel, str) != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private boolean shouldEntryBeRemoved(FileHeader fileHeader, List<String> list) {
        for (String str : list) {
            if ((str.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR) && fileHeader.getFileName().startsWith(str)) || fileHeader.getFileName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void updateHeaders(List<FileHeader> list, FileHeader fileHeader, long j) throws ZipException {
        updateOffsetsForAllSubsequentFileHeaders(list, this.zipModel, fileHeader, negate(j));
        EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = this.zipModel.getEndOfCentralDirectoryRecord();
        endOfCentralDirectoryRecord.setOffsetOfStartOfCentralDirectory(endOfCentralDirectoryRecord.getOffsetOfStartOfCentralDirectory() - j);
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(endOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectory() - 1);
        if (endOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk() > 0) {
            endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(endOfCentralDirectoryRecord.getTotalNumberOfEntriesInCentralDirectoryOnThisDisk() - 1);
        }
        if (this.zipModel.isZip64Format()) {
            this.zipModel.getZip64EndOfCentralDirectoryRecord().setOffsetStartCentralDirectoryWRTStartDiskNumber(this.zipModel.getZip64EndOfCentralDirectoryRecord().getOffsetStartCentralDirectoryWRTStartDiskNumber() - j);
            this.zipModel.getZip64EndOfCentralDirectoryRecord().setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(this.zipModel.getZip64EndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory() - 1);
            this.zipModel.getZip64EndOfCentralDirectoryLocator().setOffsetZip64EndOfCentralDirectoryRecord(this.zipModel.getZip64EndOfCentralDirectoryLocator().getOffsetZip64EndOfCentralDirectoryRecord() - j);
        }
    }

    private long negate(long j) {
        if (j != Long.MIN_VALUE) {
            return -j;
        }
        throw new ArithmeticException("long overflow");
    }

    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.REMOVE_ENTRY;
    }

    public static class RemoveFilesFromZipTaskParameters extends AbstractZipTaskParameters {
        private final List<String> filesToRemove;

        public RemoveFilesFromZipTaskParameters(List<String> list, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.filesToRemove = list;
        }
    }
}
