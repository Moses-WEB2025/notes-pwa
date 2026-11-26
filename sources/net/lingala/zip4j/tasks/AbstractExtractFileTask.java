package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.regex.Matcher;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.BitUtils;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.UnzipUtil;
import net.lingala.zip4j.util.Zip4jUtil;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractExtractFileTask<T> extends AsyncZipTask<T> {
    private final UnzipParameters unzipParameters;
    private final ZipModel zipModel;

    public AbstractExtractFileTask(ZipModel zipModel, UnzipParameters unzipParameters, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(asyncTaskParameters);
        this.zipModel = zipModel;
        this.unzipParameters = unzipParameters;
    }

    protected void extractFile(ZipInputStream zipInputStream, FileHeader fileHeader, String str, String str2, ProgressMonitor progressMonitor, byte[] bArr) throws Exception {
        if (!isSymbolicLink(fileHeader) || this.unzipParameters.isExtractSymbolicLinks()) {
            if (!str.endsWith(InternalZipConstants.FILE_SEPARATOR)) {
                str = str + InternalZipConstants.FILE_SEPARATOR;
            }
            File fileDetermineOutputFile = determineOutputFile(fileHeader, str, str2);
            progressMonitor.setFileName(fileDetermineOutputFile.getAbsolutePath());
            if (!fileDetermineOutputFile.getCanonicalPath().startsWith(new File(str).getCanonicalPath() + File.separator)) {
                throw new ZipException("illegal file name that breaks out of the target directory: " + fileHeader.getFileName());
            }
            verifyNextEntry(zipInputStream, fileHeader);
            if (fileHeader.isDirectory()) {
                if (!fileDetermineOutputFile.exists() && !fileDetermineOutputFile.mkdirs()) {
                    throw new ZipException("Could not create directory: " + fileDetermineOutputFile);
                }
            } else if (isSymbolicLink(fileHeader)) {
                createSymLink(zipInputStream, fileHeader, fileDetermineOutputFile, progressMonitor);
            } else {
                checkOutputDirectoryStructure(fileDetermineOutputFile);
                unzipFile(zipInputStream, fileDetermineOutputFile, progressMonitor, bArr);
            }
            UnzipUtil.applyFileAttributes(fileHeader, fileDetermineOutputFile);
        }
    }

    private boolean isSymbolicLink(FileHeader fileHeader) {
        byte[] externalFileAttributes = fileHeader.getExternalFileAttributes();
        if (externalFileAttributes == null || externalFileAttributes.length < 4) {
            return false;
        }
        return BitUtils.isBitSet(externalFileAttributes[3], 5);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x002f  */
    private void unzipFile(ZipInputStream zipInputStream, File file, ProgressMonitor progressMonitor, byte[] bArr) throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while (true) {
                try {
                    int i = zipInputStream.read(bArr);
                    if (i != -1) {
                        fileOutputStream.write(bArr, 0, i);
                        progressMonitor.updateWorkCompleted(i);
                        verifyIfTaskIsCancelled();
                    } else {
                        fileOutputStream.close();
                        return;
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
                if (file.exists()) {
                    file.delete();
                }
                throw e;
            }
        } catch (Exception e) {
            if (file.exists()) {
                file.delete();
            }
            throw e;
        }
    }

    private void createSymLink(ZipInputStream zipInputStream, FileHeader fileHeader, File file, ProgressMonitor progressMonitor) throws IOException {
        String str = new String(readCompleteEntry(zipInputStream, fileHeader, progressMonitor));
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new ZipException("Could not create parent directories");
        }
        try {
            Files.createSymbolicLink(file.toPath(), Paths.get(str, new String[0]), new FileAttribute[0]);
        } catch (NoSuchMethodError unused) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(str.getBytes());
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        }
    }

    private byte[] readCompleteEntry(ZipInputStream zipInputStream, FileHeader fileHeader, ProgressMonitor progressMonitor) throws IOException {
        int uncompressedSize = (int) fileHeader.getUncompressedSize();
        byte[] bArr = new byte[uncompressedSize];
        if (zipInputStream.read(bArr) != uncompressedSize) {
            throw new ZipException("Could not read complete entry");
        }
        progressMonitor.updateWorkCompleted(uncompressedSize);
        return bArr;
    }

    private void verifyNextEntry(ZipInputStream zipInputStream, FileHeader fileHeader) throws IOException {
        if (BitUtils.isBitSet(fileHeader.getGeneralPurposeFlag()[0], 6)) {
            throw new ZipException("Entry with name " + fileHeader.getFileName() + " is encrypted with Strong Encryption. Zip4j does not support Strong Encryption, as this is patented.");
        }
        LocalFileHeader nextEntry = zipInputStream.getNextEntry(fileHeader);
        if (nextEntry == null) {
            throw new ZipException("Could not read corresponding local file header for file header: " + fileHeader.getFileName());
        }
        if (!fileHeader.getFileName().equals(nextEntry.getFileName())) {
            throw new ZipException("File header and local file header mismatch");
        }
    }

    private void checkOutputDirectoryStructure(File file) throws ZipException {
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new ZipException("Unable to create parent directories: " + file.getParentFile());
        }
    }

    private File determineOutputFile(FileHeader fileHeader, String str, String str2) {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str2)) {
            str2 = getFileNameWithSystemFileSeparators(fileHeader.getFileName());
        }
        return new File(str + InternalZipConstants.FILE_SEPARATOR + str2);
    }

    private String getFileNameWithSystemFileSeparators(String str) {
        return str.replaceAll("[/\\\\]", Matcher.quoteReplacement(InternalZipConstants.FILE_SEPARATOR));
    }

    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    protected ProgressMonitor.Task getTask() {
        return ProgressMonitor.Task.EXTRACT_ENTRY;
    }

    public ZipModel getZipModel() {
        return this.zipModel;
    }
}
