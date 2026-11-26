package com.xiaomi.accountsdk.account.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.xiaomi.accountsdk.utils.AccountLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/* JADX INFO: loaded from: classes4.dex */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    public static Bitmap getImageBitmap(Context context, File file) {
        if (file.exists() && file.isFile()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    public static Bitmap getImageBitmap(Context context, String str) {
        File fileStreamPath = context.getFileStreamPath(str);
        if (fileStreamPath.isFile() && fileStreamPath.exists()) {
            return BitmapFactory.decodeFile(fileStreamPath.getAbsolutePath());
        }
        return null;
    }

    public static File saveAsFile(Context context, InputStream inputStream, String str) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(context.openFileOutput(str, 0));
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int i = bufferedInputStream.read(bArr);
                if (i != -1) {
                    bufferedOutputStream.write(bArr, 0, i);
                } else {
                    try {
                        break;
                    } catch (IOException unused) {
                    }
                }
            } catch (Throwable th) {
                try {
                    bufferedOutputStream.flush();
                } catch (IOException unused2) {
                }
                try {
                    bufferedOutputStream.close();
                    throw th;
                } catch (IOException unused3) {
                    throw th;
                }
            }
        }
        bufferedOutputStream.flush();
        try {
            bufferedOutputStream.close();
        } catch (IOException unused4) {
        }
        return context.getFileStreamPath(str);
    }

    public static Bitmap getUserAvatarByAbsPath(Context context, String str) {
        try {
            FileInputStream fileInputStreamOpenFileInput = context.openFileInput(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                try {
                    int i = fileInputStreamOpenFileInput.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                } catch (Throwable th) {
                    closeQuietly(byteArrayOutputStream);
                    closeQuietly(fileInputStreamOpenFileInput);
                    throw th;
                }
            }
            closeQuietly(byteArrayOutputStream);
            closeQuietly(fileInputStreamOpenFileInput);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (byteArray != null) {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
            return null;
        } catch (FileNotFoundException unused) {
            AccountLogger.log(TAG, "File is not found");
            return null;
        } catch (IOException unused2) {
            AccountLogger.log(TAG, "Read data error");
            return null;
        }
    }

    public static String saveUserAvatarByUrl(Context context, String str, String str2) {
        String str3 = null;
        fileOutputStreamOpenFileOutput = null;
        fileOutputStreamOpenFileOutput = null;
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(new URL(str).openStream());
            if (bitmapDecodeStream == null) {
                return null;
            }
            String str4 = str2 + "_" + Uri.parse(str).getLastPathSegment();
            try {
                try {
                    try {
                        fileOutputStreamOpenFileOutput = context.openFileOutput(str4, 0);
                        bitmapDecodeStream.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStreamOpenFileOutput);
                        fileOutputStreamOpenFileOutput.flush();
                    } catch (FileNotFoundException e) {
                        AccountLogger.log(TAG, "File is not found", e);
                    } catch (IOException e2) {
                        AccountLogger.log(TAG, "Save file exception", e2);
                    }
                    closeQuietly(fileOutputStreamOpenFileOutput);
                    return str4;
                } catch (Throwable th) {
                    closeQuietly(fileOutputStreamOpenFileOutput);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                str3 = str4;
            }
        } catch (Exception e4) {
            e = e4;
        }
        AccountLogger.log(TAG, "Get data exception", e);
        return str3;
    }

    private static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException unused) {
            }
            try {
                outputStream.close();
            } catch (IOException unused2) {
            }
        }
    }
}
