package net.lingala.zip4j.tasks;

import java.io.IOException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.inputstream.SplitInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.UnzipUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ExtractAllFilesTask extends AbstractExtractFileTask<ExtractAllFilesTaskParameters> {
    private final char[] password;
    private SplitInputStream splitInputStream;

    public ExtractAllFilesTask(ZipModel zipModel, char[] cArr, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, unzipParameters, asyncTaskParameters);
        this.password = cArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:44:0x0083  */
    /* JADX WARN: Code duplicated, block: B:66:? A[SYNTHETIC] */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(ExtractAllFilesTaskParameters extractAllFilesTaskParameters, ProgressMonitor progressMonitor) throws Throwable {
        ExtractAllFilesTask extractAllFilesTask;
        Throwable th;
        SplitInputStream splitInputStream;
        Throwable th2;
        try {
            try {
                ZipInputStream zipInputStreamPrepareZipInputStream = prepareZipInputStream(extractAllFilesTaskParameters.zip4jConfig);
                try {
                    for (FileHeader fileHeader : getZipModel().getCentralDirectory().getFileHeaders()) {
                        if (fileHeader.getFileName().startsWith("__MACOSX")) {
                            try {
                                progressMonitor.updateWorkCompleted(fileHeader.getUncompressedSize());
                            } catch (Throwable th3) {
                                th2 = th3;
                                extractAllFilesTask = this;
                                try {
                                    throw th2;
                                } catch (Throwable th4) {
                                    if (zipInputStreamPrepareZipInputStream == null) {
                                        throw th4;
                                    }
                                    try {
                                        zipInputStreamPrepareZipInputStream.close();
                                        throw th4;
                                    } catch (Throwable th5) {
                                        th2.addSuppressed(th5);
                                        throw th4;
                                    }
                                }
                            }
                        } else {
                            this.splitInputStream.prepareExtractionForFileHeader(fileHeader);
                            extractAllFilesTask = this;
                            ProgressMonitor progressMonitor2 = progressMonitor;
                            try {
                                extractAllFilesTask.extractFile(zipInputStreamPrepareZipInputStream, fileHeader, extractAllFilesTaskParameters.outputPath, null, progressMonitor2, new byte[extractAllFilesTaskParameters.zip4jConfig.getBufferSize()]);
                                extractAllFilesTask.verifyIfTaskIsCancelled();
                                this = extractAllFilesTask;
                                progressMonitor = progressMonitor2;
                            } catch (Throwable th6) {
                                th = th6;
                                th2 = th;
                                throw th2;
                            }
                        }
                    }
                    ExtractAllFilesTask extractAllFilesTask2 = this;
                    if (zipInputStreamPrepareZipInputStream != null) {
                        zipInputStreamPrepareZipInputStream.close();
                    }
                    SplitInputStream splitInputStream2 = extractAllFilesTask2.splitInputStream;
                    if (splitInputStream2 != null) {
                        splitInputStream2.close();
                    }
                } catch (Throwable th7) {
                    th = th7;
                    extractAllFilesTask = this;
                }
            } catch (Throwable th8) {
                th = th8;
                extractAllFilesTask = this;
                th = th;
                splitInputStream = extractAllFilesTask.splitInputStream;
                if (splitInputStream != null) {
                    splitInputStream.close();
                    throw th;
                }
                throw th;
            }
        } catch (Throwable th9) {
            th = th9;
            th = th;
            splitInputStream = extractAllFilesTask.splitInputStream;
            if (splitInputStream != null) {
                splitInputStream.close();
                throw th;
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(ExtractAllFilesTaskParameters extractAllFilesTaskParameters) {
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(getZipModel().getCentralDirectory().getFileHeaders());
    }

    private ZipInputStream prepareZipInputStream(Zip4jConfig zip4jConfig) throws IOException {
        this.splitInputStream = UnzipUtil.createSplitInputStream(getZipModel());
        FileHeader firstFileHeader = getFirstFileHeader(getZipModel());
        if (firstFileHeader != null) {
            this.splitInputStream.prepareExtractionForFileHeader(firstFileHeader);
        }
        return new ZipInputStream(this.splitInputStream, this.password, zip4jConfig);
    }

    private FileHeader getFirstFileHeader(ZipModel zipModel) {
        if (zipModel.getCentralDirectory() == null || zipModel.getCentralDirectory().getFileHeaders() == null || zipModel.getCentralDirectory().getFileHeaders().size() == 0) {
            return null;
        }
        return zipModel.getCentralDirectory().getFileHeaders().get(0);
    }

    public static class ExtractAllFilesTaskParameters extends AbstractZipTaskParameters {
        private final String outputPath;

        public ExtractAllFilesTaskParameters(String str, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputPath = str;
        }
    }
}
