package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.model.EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryLocator;
import net.lingala.zip4j.model.Zip64EndOfCentralDirectoryRecord;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.enums.RandomAccessFileMode;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.RawIO;

/* JADX INFO: loaded from: classes6.dex */
public class MergeSplitZipFileTask extends AsyncZipTask<MergeSplitZipFileTaskParameters> {
    private final RawIO rawIO;
    private final ZipModel zipModel;

    public MergeSplitZipFileTask(ZipModel zipModel, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.rawIO = new RawIO();
        this.zipModel = zipModel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(MergeSplitZipFileTaskParameters mergeSplitZipFileTaskParameters, ProgressMonitor progressMonitor) throws IOException {
        FileOutputStream fileOutputStream;
        int i;
        int i2;
        if (!this.zipModel.isSplitArchive()) {
            ZipException zipException = new ZipException("archive not a split zip file");
            progressMonitor.endProgressMonitor(zipException);
            throw zipException;
        }
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(mergeSplitZipFileTaskParameters.outputZipFile);
            try {
                int numberOfThisDisk = this.zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk();
                try {
                    if (numberOfThisDisk <= 0) {
                        throw new ZipException("zip archive not a split zip file");
                    }
                    long j = 0;
                    long j2 = 0;
                    fileOutputStream = fileOutputStream2;
                    int i3 = 0;
                    int i4 = 0;
                    while (i4 <= numberOfThisDisk) {
                        RandomAccessFile randomAccessFileCreateSplitZipFileStream = createSplitZipFileStream(this.zipModel, i4);
                        try {
                            long length = randomAccessFileCreateSplitZipFileStream.length();
                            if (i4 != 0) {
                                i = i3;
                                i2 = 0;
                            } else if (this.rawIO.readIntLittleEndian(randomAccessFileCreateSplitZipFileStream) == HeaderSignature.SPLIT_ZIP.getValue()) {
                                i2 = 4;
                                i = 4;
                            } else {
                                randomAccessFileCreateSplitZipFileStream.seek(j);
                                i = i3;
                                i2 = 0;
                            }
                            if (i4 == numberOfThisDisk) {
                                length = this.zipModel.getEndOfCentralDirectoryRecord().getOffsetOfStartOfCentralDirectory();
                            }
                            long j3 = length;
                            long j4 = i2;
                            FileUtils.copyFile(randomAccessFileCreateSplitZipFileStream, fileOutputStream, j4, j3, progressMonitor, mergeSplitZipFileTaskParameters.zip4jConfig.getBufferSize());
                            j2 += j3 - j4;
                            long j5 = j;
                            updateFileHeaderOffsetsForIndex(this.zipModel.getCentralDirectory().getFileHeaders(), i4 == 0 ? j5 : j2, i4, i);
                            verifyIfTaskIsCancelled();
                            if (randomAccessFileCreateSplitZipFileStream != null) {
                                randomAccessFileCreateSplitZipFileStream.close();
                            }
                            i4++;
                            i3 = i;
                            j = j5;
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                if (randomAccessFileCreateSplitZipFileStream == null) {
                                    throw th2;
                                }
                                try {
                                    randomAccessFileCreateSplitZipFileStream.close();
                                    throw th2;
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                    throw th2;
                                }
                            }
                        }
                    }
                    fileOutputStream2 = fileOutputStream;
                    updateHeadersForMergeSplitFileAction(this.zipModel, j2, fileOutputStream2, mergeSplitZipFileTaskParameters.zip4jConfig.getCharset());
                    progressMonitor.endProgressMonitor();
                    fileOutputStream2.close();
                } catch (Throwable th4) {
                    th = th4;
                    Throwable th5 = th;
                    try {
                        throw th5;
                    } catch (Throwable th6) {
                        try {
                            fileOutputStream.close();
                            throw th6;
                        } catch (Throwable th7) {
                            th5.addSuppressed(th7);
                            throw th6;
                        }
                    }
                }
            } catch (Throwable th8) {
                th = th8;
                fileOutputStream = fileOutputStream2;
            }
        } catch (CloneNotSupportedException e) {
            throw new ZipException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(MergeSplitZipFileTaskParameters mergeSplitZipFileTaskParameters) {
        long length = 0;
        if (!this.zipModel.isSplitArchive()) {
            return 0L;
        }
        for (int i = 0; i <= this.zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk(); i++) {
            length += getNextSplitZipFile(this.zipModel, i).length();
        }
        return length;
    }

    private void updateFileHeaderOffsetsForIndex(List<FileHeader> list, long j, int i, int i2) {
        for (FileHeader fileHeader : list) {
            if (fileHeader.getDiskNumberStart() == i) {
                fileHeader.setOffsetLocalHeader((fileHeader.getOffsetLocalHeader() + j) - ((long) i2));
                fileHeader.setDiskNumberStart(0);
            }
        }
    }

    private File getNextSplitZipFile(ZipModel zipModel, int i) {
        String str;
        if (i == zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk()) {
            return zipModel.getZipFile();
        }
        if (i < 9) {
            str = ".z0";
        } else {
            str = ".z";
        }
        return new File(zipModel.getZipFile().getPath().substring(0, zipModel.getZipFile().getPath().lastIndexOf(".")) + str + (i + 1));
    }

    private RandomAccessFile createSplitZipFileStream(ZipModel zipModel, int i) throws FileNotFoundException {
        return new RandomAccessFile(getNextSplitZipFile(zipModel, i), RandomAccessFileMode.READ.getValue());
    }

    private void updateHeadersForMergeSplitFileAction(ZipModel zipModel, long j, OutputStream outputStream, Charset charset) throws IOException, CloneNotSupportedException {
        ZipModel zipModel2 = (ZipModel) zipModel.clone();
        zipModel2.getEndOfCentralDirectoryRecord().setOffsetOfStartOfCentralDirectory(j);
        updateSplitZipModel(zipModel2, j);
        new HeaderWriter().finalizeZipFileWithoutValidations(zipModel2, outputStream, charset);
    }

    private void updateSplitZipModel(ZipModel zipModel, long j) {
        zipModel.setSplitArchive(false);
        updateSplitEndCentralDirectory(zipModel);
        if (zipModel.isZip64Format()) {
            updateSplitZip64EndCentralDirLocator(zipModel, j);
            updateSplitZip64EndCentralDirRec(zipModel, j);
        }
    }

    private void updateSplitEndCentralDirectory(ZipModel zipModel) {
        int size = zipModel.getCentralDirectory().getFileHeaders().size();
        EndOfCentralDirectoryRecord endOfCentralDirectoryRecord = zipModel.getEndOfCentralDirectoryRecord();
        endOfCentralDirectoryRecord.setNumberOfThisDisk(0);
        endOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDir(0);
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectory(size);
        endOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(size);
    }

    private void updateSplitZip64EndCentralDirLocator(ZipModel zipModel, long j) {
        if (zipModel.getZip64EndOfCentralDirectoryLocator() == null) {
            return;
        }
        Zip64EndOfCentralDirectoryLocator zip64EndOfCentralDirectoryLocator = zipModel.getZip64EndOfCentralDirectoryLocator();
        zip64EndOfCentralDirectoryLocator.setNumberOfDiskStartOfZip64EndOfCentralDirectoryRecord(0);
        zip64EndOfCentralDirectoryLocator.setOffsetZip64EndOfCentralDirectoryRecord(zip64EndOfCentralDirectoryLocator.getOffsetZip64EndOfCentralDirectoryRecord() + j);
        zip64EndOfCentralDirectoryLocator.setTotalNumberOfDiscs(1);
    }

    private void updateSplitZip64EndCentralDirRec(ZipModel zipModel, long j) {
        if (zipModel.getZip64EndOfCentralDirectoryRecord() == null) {
            return;
        }
        Zip64EndOfCentralDirectoryRecord zip64EndOfCentralDirectoryRecord = zipModel.getZip64EndOfCentralDirectoryRecord();
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDisk(0);
        zip64EndOfCentralDirectoryRecord.setNumberOfThisDiskStartOfCentralDirectory(0);
        zip64EndOfCentralDirectoryRecord.setTotalNumberOfEntriesInCentralDirectoryOnThisDisk(zipModel.getEndOfCentralDirectoryRecord().getTotalNumberOfEntriesInCentralDirectory());
        zip64EndOfCentralDirectoryRecord.setOffsetStartCentralDirectoryWRTStartDiskNumber(zip64EndOfCentralDirectoryRecord.getOffsetStartCentralDirectoryWRTStartDiskNumber() + j);
    }

    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.MERGE_ZIP_FILES;
    }

    public static class MergeSplitZipFileTaskParameters extends AbstractZipTaskParameters {
        private File outputZipFile;

        public MergeSplitZipFileTaskParameters(File file, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputZipFile = file;
        }
    }
}
