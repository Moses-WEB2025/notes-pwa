package com.miui.privacypolicy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/* JADX INFO: loaded from: classes4.dex */
public class FileUtils {
    protected static final String ERROR_FILE_NAME = "privacy_agree_error";
    private static final String FOLDER_NAME = "privacypolicy";
    protected static final String INFO_FILE_NAME = "privacy_update";
    private static final String TAG = "Privacy_FileUtils";
    protected static final String TEMP_UPDATE_VERSION_FILE_NAME = "privacy_temp_update_version";
    protected static final String VERSION_FILE_NAME = "privacy_version";

    private FileUtils() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v5 */
    protected static String readData(Context context, String str, String str2) throws Throwable {
        FileInputStream fileInputStream;
        StringBuilder sbAppend = new StringBuilder().append(context.getFilesDir().getPath()).append(File.separator).append(FOLDER_NAME).append(File.separator).append(str2);
        String str3 = File.separator;
        String string = sbAppend.append(str3).append(str).toString();
        if (!new File(string).exists()) {
            return "";
        }
        ?? r3 = 0;
        try {
            try {
                fileInputStream = new FileInputStream(string);
                try {
                    String inputStream = readInputStream(fileInputStream);
                    closeQuietly(fileInputStream);
                    return inputStream;
                } catch (Exception e) {
                    e = e;
                    Log.e(TAG, "readData fail!", e);
                    closeQuietly(fileInputStream);
                    return null;
                }
            } catch (Exception e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                closeQuietly((InputStream) r3);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            r3 = str3;
            closeQuietly((InputStream) r3);
            throw th;
        }
    }

    private static String readInputStream(FileInputStream fileInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                try {
                    int i = fileInputStream.read(bArr, 0, 1024);
                    if (i > 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        String string = byteArrayOutputStream.toString();
                        closeQuietly(byteArrayOutputStream);
                        return string;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "readInputStream fail!", e);
                    closeQuietly(byteArrayOutputStream);
                    return null;
                }
            } catch (Throwable th) {
                closeQuietly(byteArrayOutputStream);
                throw th;
            }
            closeQuietly(byteArrayOutputStream);
            throw th;
        }
    }

    protected static void saveData(String str, Context context, String str2, String str3) throws Throwable {
        FileOutputStream fileOutputStream;
        File file = new File(context.getFilesDir().getPath() + File.separator + FOLDER_NAME + File.separator + str3);
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(new File(file, str2));
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    try {
                        bufferedWriter2.write(str);
                        closeQuietly(bufferedWriter2);
                    } catch (Exception e) {
                        e = e;
                        bufferedWriter = bufferedWriter2;
                        Log.e(TAG, "saveData fail!", e);
                        closeQuietly(bufferedWriter);
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        closeQuietly(bufferedWriter);
                        closeQuietly(fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
        closeQuietly(fileOutputStream);
    }

    protected static boolean deleteFile(Context context, String str, String str2) {
        File file = new File(context.getFilesDir().getPath() + File.separator + FOLDER_NAME + File.separator + str2 + File.separator + str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    protected static boolean isVersionFileExit(Context context, String str) {
        return !TextUtils.isEmpty(readData(context, VERSION_FILE_NAME, str));
    }

    protected static boolean isAgreeErrorFileExit(Context context, String str) {
        return !TextUtils.isEmpty(readData(context, ERROR_FILE_NAME, str));
    }

    protected static void closeQuietly(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                Log.e(TAG, "closeQuietly Writer error " + e);
            }
        }
    }

    protected static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "closeQuietly InputStream error " + e);
            }
        }
    }

    protected static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                Log.e(TAG, "closeQuietly OutputStream error " + e);
            }
            try {
                outputStream.close();
            } catch (IOException e2) {
                Log.e(TAG, "closeQuietly OutputStream error " + e2);
            }
        }
    }
}
