package net.lingala.zip4j.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.NumberedSplitInputStream;
import net.lingala.zip4j.io.inputstream.SplitInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.io.inputstream.ZipStandardSplitInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;

/* JADX INFO: loaded from: classes6.dex */
public class UnzipUtil {
    public static ZipInputStream createZipInputStream(ZipModel zipModel, FileHeader fileHeader, char[] cArr) throws IOException {
        SplitInputStream splitInputStreamCreateSplitInputStream;
        try {
            splitInputStreamCreateSplitInputStream = createSplitInputStream(zipModel);
            try {
                splitInputStreamCreateSplitInputStream.prepareExtractionForFileHeader(fileHeader);
                ZipInputStream zipInputStream = new ZipInputStream(splitInputStreamCreateSplitInputStream, cArr);
                if (zipInputStream.getNextEntry(fileHeader) != null) {
                    return zipInputStream;
                }
                throw new ZipException("Could not locate local file header for corresponding file header");
            } catch (IOException e) {
                e = e;
                if (splitInputStreamCreateSplitInputStream != null) {
                    splitInputStreamCreateSplitInputStream.close();
                }
                throw e;
            }
        } catch (IOException e2) {
            e = e2;
            splitInputStreamCreateSplitInputStream = null;
        }
    }

    public static void applyFileAttributes(FileHeader fileHeader, File file) {
        try {
            Path path = file.toPath();
            FileUtils.setFileAttributes(path, fileHeader.getExternalFileAttributes());
            FileUtils.setFileLastModifiedTime(path, fileHeader.getLastModifiedTime());
        } catch (NoSuchMethodError unused) {
            FileUtils.setFileLastModifiedTimeWithoutNio(file, fileHeader.getLastModifiedTime());
        }
    }

    public static SplitInputStream createSplitInputStream(ZipModel zipModel) throws IOException {
        if (zipModel.getZipFile().getName().endsWith(InternalZipConstants.SEVEN_ZIP_SPLIT_FILE_EXTENSION_PATTERN)) {
            return new NumberedSplitInputStream(zipModel.getZipFile(), true, zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
        }
        return new ZipStandardSplitInputStream(zipModel.getZipFile(), zipModel.isSplitArchive(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
    }
}
