package net.lingala.zip4j.tasks;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.io.inputstream.SplitInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.UnzipUtil;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ExtractFileTask extends AbstractExtractFileTask<ExtractFileTaskParameters> {
    private char[] password;
    private SplitInputStream splitInputStream;

    public ExtractFileTask(ZipModel zipModel, char[] cArr, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, unzipParameters, asyncTaskParameters);
        this.password = cArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:38:0x006e  */
    /* JADX WARN: Code duplicated, block: B:54:? A[SYNTHETIC] */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(ExtractFileTaskParameters extractFileTaskParameters, ProgressMonitor progressMonitor) throws Throwable {
        ExtractFileTask extractFileTask;
        Throwable th;
        SplitInputStream splitInputStream;
        try {
            try {
                ZipInputStream zipInputStreamCreateZipInputStream = createZipInputStream(extractFileTaskParameters.fileHeader, extractFileTaskParameters.zip4jConfig);
                try {
                    List<FileHeader> fileHeadersToExtract = getFileHeadersToExtract(extractFileTaskParameters.fileHeader);
                    byte[] bArr = new byte[extractFileTaskParameters.zip4jConfig.getBufferSize()];
                    for (FileHeader fileHeader : fileHeadersToExtract) {
                        extractFileTask = this;
                        ProgressMonitor progressMonitor2 = progressMonitor;
                        try {
                            extractFileTask.extractFile(zipInputStreamCreateZipInputStream, fileHeader, extractFileTaskParameters.outputPath, this.determineNewFileName(extractFileTaskParameters.newFileName, extractFileTaskParameters.fileHeader, fileHeader), progressMonitor2, bArr);
                            this = extractFileTask;
                            progressMonitor = progressMonitor2;
                        } catch (Throwable th2) {
                            th = th2;
                            Throwable th3 = th;
                            try {
                                throw th3;
                            } catch (Throwable th4) {
                                if (zipInputStreamCreateZipInputStream == null) {
                                    throw th4;
                                }
                                try {
                                    zipInputStreamCreateZipInputStream.close();
                                    throw th4;
                                } catch (Throwable th5) {
                                    th3.addSuppressed(th5);
                                    throw th4;
                                }
                            }
                        }
                    }
                    ExtractFileTask extractFileTask2 = this;
                    if (zipInputStreamCreateZipInputStream != null) {
                        zipInputStreamCreateZipInputStream.close();
                    }
                    SplitInputStream splitInputStream2 = extractFileTask2.splitInputStream;
                    if (splitInputStream2 != null) {
                        splitInputStream2.close();
                    }
                } catch (Throwable th6) {
                    th = th6;
                    extractFileTask = this;
                }
            } catch (Throwable th7) {
                th = th7;
                extractFileTask = this;
                th = th;
                splitInputStream = extractFileTask.splitInputStream;
                if (splitInputStream != null) {
                    splitInputStream.close();
                    throw th;
                }
                throw th;
            }
        } catch (Throwable th8) {
            th = th8;
            th = th;
            splitInputStream = extractFileTask.splitInputStream;
            if (splitInputStream != null) {
                splitInputStream.close();
                throw th;
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(ExtractFileTaskParameters extractFileTaskParameters) {
        return HeaderUtil.getTotalUncompressedSizeOfAllFileHeaders(getFileHeadersToExtract(extractFileTaskParameters.fileHeader));
    }

    private List<FileHeader> getFileHeadersToExtract(FileHeader fileHeader) {
        if (!fileHeader.isDirectory()) {
            return Collections.singletonList(fileHeader);
        }
        return HeaderUtil.getFileHeadersUnderDirectory(getZipModel().getCentralDirectory().getFileHeaders(), fileHeader);
    }

    private ZipInputStream createZipInputStream(FileHeader fileHeader, Zip4jConfig zip4jConfig) throws IOException {
        SplitInputStream splitInputStreamCreateSplitInputStream = UnzipUtil.createSplitInputStream(getZipModel());
        this.splitInputStream = splitInputStreamCreateSplitInputStream;
        splitInputStreamCreateSplitInputStream.prepareExtractionForFileHeader(fileHeader);
        return new ZipInputStream(this.splitInputStream, this.password, zip4jConfig);
    }

    private String determineNewFileName(String str, FileHeader fileHeader, FileHeader fileHeader2) {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str) || !fileHeader.isDirectory()) {
            return str;
        }
        String str2 = InternalZipConstants.ZIP_FILE_SEPARATOR;
        if (str.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            str2 = "";
        }
        return fileHeader2.getFileName().replaceFirst(fileHeader.getFileName(), str + str2);
    }

    public static class ExtractFileTaskParameters extends AbstractZipTaskParameters {
        private FileHeader fileHeader;
        private String newFileName;
        private String outputPath;

        public ExtractFileTaskParameters(String str, FileHeader fileHeader, String str2, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.outputPath = str;
            this.fileHeader = fileHeader;
            this.newFileName = str2;
        }
    }
}
