package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderUtil;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.io.outputstream.SplitOutputStream;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.CrcUtil;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractAddFileToZipTask<T> extends AsyncZipTask<T> {
    private final HeaderWriter headerWriter;
    private final char[] password;
    private final ZipModel zipModel;

    AbstractAddFileToZipTask(ZipModel zipModel, char[] cArr, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.password = cArr;
        this.headerWriter = headerWriter;
    }

    void addFilesToZip(List<File> list, ProgressMonitor progressMonitor, ZipParameters zipParameters, Zip4jConfig zip4jConfig) throws IOException {
        FileUtils.assertFilesExist(list, zipParameters.getSymbolicLinkAction());
        byte[] bArr = new byte[zip4jConfig.getBufferSize()];
        List<File> listRemoveFilesIfExists = removeFilesIfExists(list, zipParameters, progressMonitor, zip4jConfig);
        SplitOutputStream splitOutputStream = new SplitOutputStream(this.zipModel.getZipFile(), this.zipModel.getSplitLength());
        try {
            ZipOutputStream zipOutputStreamInitializeOutputStream = initializeOutputStream(splitOutputStream, zip4jConfig);
            try {
                for (File file : listRemoveFilesIfExists) {
                    this.verifyIfTaskIsCancelled();
                    ZipParameters zipParametersCloneAndAdjustZipParameters = this.cloneAndAdjustZipParameters(zipParameters, file, progressMonitor);
                    progressMonitor.setFileName(file.getAbsolutePath());
                    if (FileUtils.isSymbolicLink(file) && this.addSymlink(zipParametersCloneAndAdjustZipParameters)) {
                        this.addSymlinkToZip(file, zipOutputStreamInitializeOutputStream, zipParametersCloneAndAdjustZipParameters, splitOutputStream);
                        if (ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals(zipParametersCloneAndAdjustZipParameters.getSymbolicLinkAction())) {
                        }
                    }
                    AbstractAddFileToZipTask<T> abstractAddFileToZipTask = this;
                    ProgressMonitor progressMonitor2 = progressMonitor;
                    abstractAddFileToZipTask.addFileToZip(file, zipOutputStreamInitializeOutputStream, zipParametersCloneAndAdjustZipParameters, splitOutputStream, progressMonitor2, bArr);
                    this = abstractAddFileToZipTask;
                    progressMonitor = progressMonitor2;
                }
                if (zipOutputStreamInitializeOutputStream != null) {
                    zipOutputStreamInitializeOutputStream.close();
                }
                splitOutputStream.close();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    if (zipOutputStreamInitializeOutputStream == null) {
                        throw th2;
                    }
                    try {
                        zipOutputStreamInitializeOutputStream.close();
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
    }

    private void addSymlinkToZip(File file, ZipOutputStream zipOutputStream, ZipParameters zipParameters, SplitOutputStream splitOutputStream) throws IOException {
        ZipParameters zipParameters2 = new ZipParameters(zipParameters);
        zipParameters2.setFileNameInZip(replaceFileNameInZip(zipParameters.getFileNameInZip(), file.getName()));
        zipParameters2.setEncryptFiles(false);
        zipParameters2.setCompressionMethod(CompressionMethod.STORE);
        zipOutputStream.putNextEntry(zipParameters2);
        zipOutputStream.write(FileUtils.readSymbolicLink(file).getBytes());
        closeEntry(zipOutputStream, splitOutputStream, file, true);
    }

    private void addFileToZip(File file, ZipOutputStream zipOutputStream, ZipParameters zipParameters, SplitOutputStream splitOutputStream, ProgressMonitor progressMonitor, byte[] bArr) throws IOException {
        zipOutputStream.putNextEntry(zipParameters);
        if (file.exists() && !file.isDirectory()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                try {
                    int i = fileInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    zipOutputStream.write(bArr, 0, i);
                    progressMonitor.updateWorkCompleted(i);
                    verifyIfTaskIsCancelled();
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            }
            fileInputStream.close();
        }
        closeEntry(zipOutputStream, splitOutputStream, file, false);
    }

    private void closeEntry(ZipOutputStream zipOutputStream, SplitOutputStream splitOutputStream, File file, boolean z) throws IOException {
        FileHeader fileHeaderCloseEntry = zipOutputStream.closeEntry();
        byte[] fileAttributes = FileUtils.getFileAttributes(file);
        if (!z) {
            fileAttributes[3] = BitUtils.unsetBit(fileAttributes[3], 5);
        }
        fileHeaderCloseEntry.setExternalFileAttributes(fileAttributes);
        updateLocalFileHeader(fileHeaderCloseEntry, splitOutputStream);
    }

    long calculateWorkForFiles(List<File> list, ZipParameters zipParameters) throws ZipException {
        long length;
        long length2 = 0;
        for (File file : list) {
            if (file.exists()) {
                if (zipParameters.isEncryptFiles() && zipParameters.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
                    length = file.length() * 2;
                } else {
                    length = file.length();
                }
                length2 += length;
                FileHeader fileHeader = HeaderUtil.getFileHeader(getZipModel(), FileUtils.getRelativeFileName(file, zipParameters));
                if (fileHeader != null) {
                    length2 += getZipModel().getZipFile().length() - fileHeader.getCompressedSize();
                }
            }
        }
        return length2;
    }

    ZipOutputStream initializeOutputStream(SplitOutputStream splitOutputStream, Zip4jConfig zip4jConfig) throws IOException {
        if (this.zipModel.getZipFile().exists()) {
            splitOutputStream.seek(HeaderUtil.getOffsetStartOfCentralDirectory(this.zipModel));
        }
        return new ZipOutputStream(splitOutputStream, this.password, zip4jConfig, this.zipModel);
    }

    void verifyZipParameters(ZipParameters zipParameters) throws ZipException {
        if (zipParameters == null) {
            throw new ZipException("cannot validate zip parameters");
        }
        if (zipParameters.getCompressionMethod() != CompressionMethod.STORE && zipParameters.getCompressionMethod() != CompressionMethod.DEFLATE) {
            throw new ZipException("unsupported compression type");
        }
        if (zipParameters.isEncryptFiles()) {
            if (zipParameters.getEncryptionMethod() == EncryptionMethod.NONE) {
                throw new ZipException("Encryption method has to be set, when encrypt files flag is set");
            }
            char[] cArr = this.password;
            if (cArr == null || cArr.length <= 0) {
                throw new ZipException("input password is empty or null");
            }
            return;
        }
        zipParameters.setEncryptionMethod(EncryptionMethod.NONE);
    }

    void updateLocalFileHeader(FileHeader fileHeader, SplitOutputStream splitOutputStream) throws IOException {
        this.headerWriter.updateLocalFileHeader(fileHeader, getZipModel(), splitOutputStream);
    }

    private ZipParameters cloneAndAdjustZipParameters(ZipParameters zipParameters, File file, ProgressMonitor progressMonitor) throws IOException {
        ZipParameters zipParameters2 = new ZipParameters(zipParameters);
        zipParameters2.setLastModifiedFileTime(Zip4jUtil.epochToExtendedDosTime(file.lastModified()));
        if (file.isDirectory()) {
            zipParameters2.setEntrySize(0L);
        } else {
            zipParameters2.setEntrySize(file.length());
        }
        zipParameters2.setWriteExtendedLocalFileHeader(false);
        zipParameters2.setLastModifiedFileTime(file.lastModified());
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(zipParameters.getFileNameInZip())) {
            zipParameters2.setFileNameInZip(FileUtils.getRelativeFileName(file, zipParameters));
        }
        if (file.isDirectory()) {
            zipParameters2.setCompressionMethod(CompressionMethod.STORE);
            zipParameters2.setEncryptionMethod(EncryptionMethod.NONE);
            zipParameters2.setEncryptFiles(false);
            return zipParameters2;
        }
        if (zipParameters2.isEncryptFiles() && zipParameters2.getEncryptionMethod() == EncryptionMethod.ZIP_STANDARD) {
            progressMonitor.setCurrentTask(ProgressMonitor.Task.CALCULATE_CRC);
            zipParameters2.setEntryCRC(CrcUtil.computeFileCrc(file, progressMonitor));
            progressMonitor.setCurrentTask(ProgressMonitor.Task.ADD_ENTRY);
        }
        if (file.length() == 0) {
            zipParameters2.setCompressionMethod(CompressionMethod.STORE);
        }
        return zipParameters2;
    }

    private List<File> removeFilesIfExists(List<File> list, ZipParameters zipParameters, ProgressMonitor progressMonitor, Zip4jConfig zip4jConfig) throws ZipException {
        ArrayList arrayList = new ArrayList(list);
        if (this.zipModel.getZipFile().exists()) {
            for (File file : list) {
                if (!Zip4jUtil.isStringNotNullAndNotEmpty(file.getName())) {
                    arrayList.remove(file);
                }
                FileHeader fileHeader = HeaderUtil.getFileHeader(this.zipModel, FileUtils.getRelativeFileName(file, zipParameters));
                if (fileHeader != null) {
                    if (zipParameters.isOverrideExistingFilesInZip()) {
                        progressMonitor.setCurrentTask(ProgressMonitor.Task.REMOVE_ENTRY);
                        removeFile(fileHeader, progressMonitor, zip4jConfig);
                        verifyIfTaskIsCancelled();
                        progressMonitor.setCurrentTask(ProgressMonitor.Task.ADD_ENTRY);
                    } else {
                        arrayList.remove(file);
                    }
                }
            }
        }
        return arrayList;
    }

    void removeFile(FileHeader fileHeader, ProgressMonitor progressMonitor, Zip4jConfig zip4jConfig) throws ZipException {
        new RemoveFilesFromZipTask(this.zipModel, this.headerWriter, new AsyncZipTask.AsyncTaskParameters(null, false, progressMonitor)).execute(new RemoveFilesFromZipTask.RemoveFilesFromZipTaskParameters(Collections.singletonList(fileHeader.getFileName()), zip4jConfig));
    }

    private String replaceFileNameInZip(String str, String str2) {
        return str.contains(InternalZipConstants.ZIP_FILE_SEPARATOR) ? str.substring(0, str.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1) + str2 : str2;
    }

    private boolean addSymlink(ZipParameters zipParameters) {
        return ZipParameters.SymbolicLinkAction.INCLUDE_LINK_ONLY.equals(zipParameters.getSymbolicLinkAction()) || ZipParameters.SymbolicLinkAction.INCLUDE_LINK_AND_LINKED_FILE.equals(zipParameters.getSymbolicLinkAction());
    }

    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.ADD_ENTRY;
    }

    protected ZipModel getZipModel() {
        return this.zipModel;
    }
}
