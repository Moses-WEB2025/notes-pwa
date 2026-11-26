package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import ch.qos.logback.classic.pattern.CallerDataConverter;
import com.miui.common.tool.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareFileUtils {
    private static final String TAG = "TapShareFileUtils";

    public static File getCacheDir(Context context, File file) {
        if (file == null) {
            file = context.getExternalCacheDir();
        }
        if (file == null) {
            Logger.INSTANCE.e(TAG, "Cache directory is null");
        }
        return file;
    }

    public static String generateZipFileName(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            str = TapShareDataModel.DEFAULT_TITLE;
        }
        String strReplaceAll = str.replaceAll("[\\p{So}\\p{Punct}\\p{Cntrl}\\/:*?\"<>|]", "").replaceAll("[\\uFF01-\\uFF5E]", "");
        return strReplaceAll.substring(0, Math.min(strReplaceAll.length(), 20)) + "_" + j + ".zip";
    }

    public static void copyUriToFile(Context context, Uri uri, File file) throws IOException {
        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                if (inputStreamOpenInputStream == null) {
                    throw new IOException("Failed to open input stream for URI: " + uri);
                }
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = inputStreamOpenInputStream.read(bArr);
                    if (i == -1) {
                        break;
                    } else {
                        fileOutputStream.write(bArr, 0, i);
                    }
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i <= 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static void cleanupTempFiles(File file, List<File> list) {
        if (file != null && file.exists()) {
            if (file.delete()) {
                Logger.INSTANCE.d(TAG, "Cleaned up temp file: " + file.getAbsolutePath());
            } else {
                Logger.INSTANCE.w(TAG, "Failed to clean up temp file: " + file.getAbsolutePath());
            }
        }
        for (File file2 : list) {
            if (file2 != null && file2.exists()) {
                if (file2.delete()) {
                    Logger.INSTANCE.d(TAG, "Cleaned up temp file: " + file2.getAbsolutePath());
                } else {
                    Logger.INSTANCE.w(TAG, "Failed to clean up temp file: " + file2.getAbsolutePath());
                }
            }
        }
    }

    public static byte[] readBytesFromZipEntry(ZipInputStream zipInputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = zipInputStream.read(bArr);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        return byteArray;
                    }
                    Logger.INSTANCE.e(TAG, "Error reading bytes from ZIP entry: " + e.getMessage());
                    return null;
                }
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Error reading bytes from ZIP entry: " + e.getMessage());
            return null;
        }
    }

    static boolean isSafeAttachmentName(String str) {
        if (str == null || str.trim().isEmpty() || ".".equals(str) || CallerDataConverter.DEFAULT_RANGE_DELIMITER.equals(str) || str.indexOf(47) >= 0 || str.indexOf(92) >= 0 || str.indexOf(58) >= 0 || new File(str).isAbsolute() || !str.equals(new File(str).getName())) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (Character.isISOControl(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    static boolean isFileInsideDirectory(File file, File file2) throws IOException {
        if (file != null && file2 != null) {
            File canonicalFile = file.getCanonicalFile();
            for (File parentFile = file2.getCanonicalFile().getParentFile(); parentFile != null; parentFile = parentFile.getParentFile()) {
                if (canonicalFile.equals(parentFile)) {
                    return true;
                }
            }
        }
        return false;
    }

    static File resolveSafeChildFile(File file, String str) throws IOException {
        if (!isSafeAttachmentName(str)) {
            throw new IOException("Unsafe attachment name");
        }
        File canonicalFile = new File(file, str).getCanonicalFile();
        if (isFileInsideDirectory(file, canonicalFile)) {
            return canonicalFile;
        }
        throw new IOException("Attachment path escapes target directory");
    }

    public static String saveAttachmentFromZipEntry(Context context, ZipInputStream zipInputStream, String str) {
        try {
            File file = new File(context.getFilesDir(), "attachments");
            if (!file.exists() && !file.mkdirs()) {
                Logger.INSTANCE.e(TAG, "Failed to create attachments directory: " + file.getAbsolutePath());
                return null;
            }
            File fileResolveSafeChildFile = resolveSafeChildFile(file, str);
            FileOutputStream fileOutputStream = new FileOutputStream(fileResolveSafeChildFile);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = zipInputStream.read(bArr);
                    if (i == -1) {
                        fileOutputStream.close();
                        return fileResolveSafeChildFile.getAbsolutePath();
                    }
                    fileOutputStream.write(bArr, 0, i);
                    Logger.INSTANCE.e(TAG, "Error saving attachment from ZIP entry: " + e.getMessage());
                    return null;
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Error saving attachment from ZIP entry: " + e.getMessage());
            return null;
        }
    }

    public static void addFileToZip(ZipOutputStream zipOutputStream, File file, String str) throws IOException {
        ZipEntry zipEntry = new ZipEntry(str);
        zipEntry.setTime(file.lastModified());
        zipOutputStream.putNextEntry(zipEntry);
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i > 0) {
                    zipOutputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    zipOutputStream.closeEntry();
                    return;
                }
            }
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
