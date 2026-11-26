package cn.kuaipan.android.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileLock;
import org.apache.http.util.LangUtils;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    private static final String TAG = "FileUtils";
    private static final Object mSync = new Object();
    private static File sExternalCacheDir;
    private static File sExternalDataDir;

    public static File getExternalStorageAppCacheDirectory(Context context) {
        return context.getCacheDir();
    }

    public static File getExternalStorageAppDataDirectory(Context context) {
        return context.getFilesDir();
    }

    public static File getCacheDir(Context context, String str, boolean z) {
        File cacheDir = getCacheDir(context, z);
        if (cacheDir == null) {
            return null;
        }
        File file = new File(cacheDir, str);
        if (!file.exists()) {
            file.mkdirs();
            if (!file.exists() || !file.isDirectory()) {
                Log.w(TAG, "Unable to create cache directory:" + file);
                return null;
            }
        }
        return file;
    }

    public static File getCacheDir(Context context, boolean z) {
        return getCacheDir(context, z, true);
    }

    public static File getCacheDir(Context context, boolean z, boolean z2) {
        if (z) {
            return getExternalCacheDir(context, z2);
        }
        return context.getCacheDir();
    }

    public static boolean deleteChildren(File file) {
        File[] fileArrListFiles;
        if (file == null || !file.exists() || !file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return true;
        }
        boolean z = true;
        for (File file2 : fileArrListFiles) {
            z = deletes(file2) && z;
        }
        return z;
    }

    public static boolean deletes(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            deleteChildren(file);
        }
        return file.delete();
    }

    public static boolean move(File file, File file2) {
        if (LangUtils.equals(file, file2)) {
            return true;
        }
        boolean zRenameTo = false;
        if (file2.exists()) {
            return false;
        }
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!parentFile.exists() || !parentFile.isDirectory() || !parentFile.canWrite()) {
            return false;
        }
        try {
            long jLastModified = file.lastModified();
            zRenameTo = file.renameTo(file2);
            if (zRenameTo) {
                file2.setLastModified(jLastModified);
            }
        } catch (Exception unused) {
        }
        if (zRenameTo) {
            return true;
        }
        return copyFiles(file, file2) ? deletes(file) : zRenameTo;
    }

    public static boolean copyFiles(File file, File file2) {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdirs();
            }
            if (!file2.exists() || !file2.canWrite() || !file2.isDirectory()) {
                return false;
            }
            for (File file3 : file.listFiles()) {
                if (!copyFiles(file3, new File(file2, file3.getName()))) {
                    return false;
                }
            }
            return true;
        }
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (parentFile.exists() && parentFile.canWrite() && (!file2.exists() || file2.isFile())) {
            return copy(file, file2, -1L);
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:100:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:59:0x00ff  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.io.FileInputStream, java.io.FileOutputStream, java.nio.channels.FileLock] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.nio.channels.FileLock] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    public static boolean copy(File file, File file2, long j) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        long length;
        FileOutputStream fileOutputStream2;
        long j2 = 0;
        FileLock fileLockLock = 0;
        fileLockLock = 0;
        fileLockLock = 0;
        try {
            if (j < 0) {
                try {
                    length = file.length();
                } catch (Exception unused) {
                    fileInputStream = null;
                    fileOutputStream2 = null;
                    Log.e(TAG, "Failed copy \"" + file + "\" to \"" + file2 + "\"");
                    try {
                        fileLockLock.release();
                    } catch (Throwable unused2) {
                    }
                    try {
                        fileInputStream.close();
                    } catch (Throwable unused3) {
                    }
                    try {
                        fileOutputStream2.close();
                    } catch (Throwable unused4) {
                    }
                    if (file2.length() == file.length()) {
                        file2.setLastModified(file.lastModified());
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = null;
                    fileOutputStream = null;
                    try {
                        fileLockLock.release();
                    } catch (Throwable unused5) {
                    }
                    try {
                        fileInputStream.close();
                    } catch (Throwable unused6) {
                    }
                    try {
                        fileOutputStream.close();
                    } catch (Throwable unused7) {
                    }
                    if (file2.length() == file.length()) {
                        throw th;
                    }
                    file2.setLastModified(file.lastModified());
                    throw th;
                }
            } else {
                length = j;
            }
            if (file.getCanonicalFile().equals(file2.getCanonicalFile()) && length >= file.length()) {
                try {
                    try {
                        try {
                            fileLockLock.release();
                            throw null;
                        } catch (Throwable unused8) {
                            if (file2.length() == file.length()) {
                                file2.setLastModified(file.lastModified());
                            }
                            return true;
                        }
                    } catch (Throwable unused9) {
                        fileLockLock.close();
                        throw null;
                    }
                } catch (Throwable unused10) {
                    fileLockLock.close();
                    throw null;
                }
            }
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream2 = new FileOutputStream(file2);
                try {
                    fileLockLock = fileOutputStream2.getChannel().lock();
                    int i = 8192;
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i2 = fileInputStream.read(bArr, 0, (int) Math.min(i, length - j2));
                        if (i2 < 0) {
                            break;
                        }
                        fileOutputStream2.write(bArr, 0, i2);
                        j2 += (long) i2;
                        if (j2 >= length) {
                            break;
                        }
                        i = 8192;
                    }
                    try {
                        fileLockLock.release();
                    } catch (Throwable unused11) {
                    }
                    try {
                        fileInputStream.close();
                    } catch (Throwable unused12) {
                    }
                    try {
                        fileOutputStream2.close();
                    } catch (Throwable unused13) {
                    }
                    if (file2.length() == file.length()) {
                        file2.setLastModified(file.lastModified());
                    }
                    return true;
                } catch (Exception unused14) {
                    Log.e(TAG, "Failed copy \"" + file + "\" to \"" + file2 + "\"");
                    fileLockLock.release();
                    fileInputStream.close();
                    fileOutputStream2.close();
                    if (file2.length() == file.length()) {
                        file2.setLastModified(file.lastModified());
                    }
                    return false;
                }
            } catch (Exception unused15) {
                fileOutputStream2 = null;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                fileLockLock.release();
                fileInputStream.close();
                fileOutputStream.close();
                if (file2.length() == file.length()) {
                    throw th;
                }
                file2.setLastModified(file.lastModified());
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static void writeTo(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        FileLock fileLockLock = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileLockLock = fileOutputStream.getChannel().lock();
                byte[] bArr = new byte[8192];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i >= 0) {
                        fileOutputStream.write(bArr, 0, i);
                    } else {
                        try {
                            break;
                        } catch (Throwable unused) {
                        }
                    }
                }
                fileLockLock.release();
                try {
                    fileOutputStream.close();
                } catch (Throwable unused2) {
                }
            } catch (Throwable th) {
                th = th;
                try {
                    fileLockLock.release();
                } catch (Throwable unused3) {
                }
                try {
                    fileOutputStream.close();
                    throw th;
                } catch (Throwable unused4) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
    }

    public static long getSize(File file) {
        File[] fileArrListFiles;
        if (file == null || !file.exists()) {
            return 0L;
        }
        long length = file.length();
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                length += getSize(file2);
            }
        }
        return length;
    }

    public static File getExternalCacheDir(Context context, boolean z) {
        synchronized (mSync) {
            if (sExternalCacheDir == null) {
                sExternalCacheDir = getExternalStorageAppCacheDirectory(context);
            }
            if (!sExternalCacheDir.exists()) {
                Log.w(TAG, "ExternalCacheDir not exists");
                sExternalCacheDir.mkdirs();
                if (z && (!sExternalCacheDir.exists() || !sExternalCacheDir.isDirectory())) {
                    Log.w(TAG, "Unable to create external cache directory");
                    return null;
                }
            }
            return sExternalCacheDir;
        }
    }

    public static int setPermissions(String str, int i, int i2, int i3) {
        try {
            return ((Integer) JavaCalls.callStaticMethodOrThrow(Class.forName("android.os.FileUtils"), "setPermissions", str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3))).intValue();
        } catch (Exception unused) {
            Log.e(TAG, "Failed set Permissions for file: " + str);
            return 0;
        }
    }

    public static boolean isChildPath(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                File file = new File(str);
                for (File file2 = new File(str2); file2 != null; file2 = file2.getParentFile()) {
                    if (file.equals(file2)) {
                        return true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }
}
