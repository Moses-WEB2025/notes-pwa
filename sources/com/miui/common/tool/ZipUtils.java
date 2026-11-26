package com.miui.common.tool;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class ZipUtils {
    private static final int BUFFER_SIZE = 2048;
    private static final long MAX_FILE_COUNT = 2000;
    private static final long MAX_TOTAL_FILE_SIZE = 1048576;
    private static final String TAG = "ZipUtils";

    private static void ZipFiles(String str, String str2, ZipOutputStream zipOutputStream) {
        if (zipOutputStream == null) {
            return;
        }
        File file = new File(str + str2);
        if (file.isFile()) {
            ZipFileEntry(str2, file, zipOutputStream);
        } else {
            ZipFileFolder(str, str2, file, zipOutputStream);
        }
    }

    private static void ZipFileEntry(String str, File file, ZipOutputStream zipOutputStream) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), 2048);
            try {
                byte[] bArr = new byte[2048];
                zipOutputStream.putNextEntry(new ZipEntry(str));
                while (true) {
                    int i = bufferedInputStream.read(bArr);
                    if (i > 0) {
                        zipOutputStream.write(bArr, 0, i);
                    } else {
                        zipOutputStream.closeEntry();
                        bufferedInputStream.close();
                        return;
                    }
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    bufferedInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ZipFileFolder(String str, String str2, File file, ZipOutputStream zipOutputStream) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        if (fileArrListFiles.length == 0) {
            addEmptyDirectoryEntry(str2, zipOutputStream);
        } else {
            processDirectoryContents(fileArrListFiles, str, str2, zipOutputStream);
        }
    }

    private static void addEmptyDirectoryEntry(String str, ZipOutputStream zipOutputStream) {
        try {
            zipOutputStream.putNextEntry(new ZipEntry(str + File.separator));
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processDirectoryContents(File[] fileArr, String str, String str2, ZipOutputStream zipOutputStream) {
        for (File file : fileArr) {
            ZipFiles(str, str2 + File.separator + file.getName(), zipOutputStream);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.miui.common.tool.ZipUtils] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.io.Closeable] */
    public void zipFolder(String str, String str2, String str3) throws Throwable {
        Throwable th;
        Exception e;
        ZipOutputStream zipOutputStream;
        File file = new File(str);
        ?? file2 = new File(str2);
        if (!file2.exists() && !file2.mkdirs()) {
            return;
        }
        try {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File((File) file2, str3));
                zipOutputStream = new ZipOutputStream(fileOutputStream);
                try {
                    ZipFiles(file.getParent() + File.separator, file.getName(), zipOutputStream);
                    zipOutputStream.flush();
                    fileOutputStream.getFD().sync();
                    closeResource(zipOutputStream);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    closeResource(zipOutputStream);
                }
            } catch (Exception e3) {
                e = e3;
                zipOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                file2 = 0;
                closeResource(file2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            closeResource(file2);
            throw th;
        }
    }

    private void closeResource(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (String str : file.list()) {
                if (!deleteDirectory(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static void unZip(String str, String str2) {
        try {
            Logger.INSTANCE.d(TAG, "unZip targetFolder:" + str2);
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            ZipFile zipFile = new ZipFile(str);
            Logger.INSTANCE.d(TAG, "unZip zipFileName:" + str + " size:" + zipFile.size());
            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
            int i = 0;
            int iUnZipChild = 0;
            while (enumerationEntries.hasMoreElements()) {
                i++;
                zipBombCheckFile(i);
                iUnZipChild += unZipChild(zipFile, enumerationEntries.nextElement(), str2);
                zipBombCheckSize(iUnZipChild);
            }
            zipFile.close();
            Logger.INSTANCE.d(TAG, "unZip all file:" + Arrays.toString(file.list()));
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "unZip error:" + e.getMessage());
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static int unZipChild(ZipFile zipFile, ZipEntry zipEntry, String str) {
        if (zipEntry.isDirectory()) {
            String str2 = str + zipEntry.getName();
            new File(str2).mkdirs();
            Logger.INSTANCE.d(TAG, "unZipChild mkdirs " + str2);
        } else {
            try {
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(str, zipEntry.getName()));
                    try {
                        byte[] bArr = new byte[1024];
                        int i = 0;
                        while (true) {
                            int i2 = inputStream.read(bArr);
                            if (i2 <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, i2);
                            i += i2;
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable th) {
                                    th.addSuppressed(th);
                                }
                            }
                            throw th;
                        }
                        Logger.INSTANCE.d(TAG, "outputStream zipFile size:" + i);
                        fileOutputStream.close();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        return i;
                    } catch (Throwable th2) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                } catch (Throwable th4) {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    throw th4;
                }
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "unZipChild error:" + e.getMessage());
            }
        }
        return 0;
    }

    private static void zipBombCheckFile(long j) throws IOException {
        if (j > 2000) {
            throw new IOException("The ZIP package contains too many files." + j);
        }
    }

    private static void zipBombCheckSize(long j) throws IOException {
        if (j > 1048576) {
            throw new IOException("Zip Bomb! The size of the file extracted from the ZIP package is too large." + j);
        }
    }
}
