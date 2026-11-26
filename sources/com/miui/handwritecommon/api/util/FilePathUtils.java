package com.miui.handwritecommon.api.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class FilePathUtils {
    public static final String ENT_MULTI_ENGINE_AND_PAGE = "ent_multi_engine_and_page_";
    private static final String TAG = "FilePathUtils";
    public static final String THUMBNAIL_TEMP_NAME = "thumbnail.png";
    public static final String WEBIMAGE_TEMP_NAME = "webimage.png";

    public static File getSinglePageExportDir(Context context) {
        File handWrittenTempDir = getHandWrittenTempDir(context);
        if (handWrittenTempDir == null) {
            return null;
        }
        File file = new File(handWrittenTempDir.getAbsolutePath() + "/handwrite_share");
        boolean zExists = file.exists();
        if (!mkdirs(file)) {
            return null;
        }
        if (!zExists) {
            file.setExecutable(true, false);
        }
        return file;
    }

    public static String getSinglePageExportImageName(int i) {
        return "exportImage" + System.currentTimeMillis() + ".png";
    }

    public static String getMultiRecognizePath(String str) {
        return str + "/multiRecognize.json";
    }

    public static String getRecognizePath(String str) {
        return str + "/miuiRecognize.json";
    }

    public static String getSelectRecognizePath(String str) {
        return str + "/miuiSelectRecognize.json";
    }

    public static File getMultiPageExportFileDir(Context context) {
        File handWrittenTempDir = getHandWrittenTempDir(context);
        if (handWrittenTempDir == null) {
            return null;
        }
        File file = new File(handWrittenTempDir.getAbsolutePath() + "/multi_handwrite_share");
        boolean zExists = file.exists();
        if (!mkdirs(file)) {
            return null;
        }
        if (!zExists) {
            file.setExecutable(true, false);
        }
        return file;
    }

    public static String createMultiEntPathDir(Context context) {
        File handWrittenTempDir = getHandWrittenTempDir(context);
        return (handWrittenTempDir != null ? handWrittenTempDir.getAbsolutePath() : "") + File.separator + "ent_multi_engine_and_page_1000000000";
    }

    public static String getHandWrittenTempPath(Context context, String str) {
        File handWrittenTempDir = getHandWrittenTempDir(context);
        return (handWrittenTempDir != null ? handWrittenTempDir.getAbsolutePath() : "") + File.separator + str;
    }

    public static final File getHandWrittenTempDir(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir == null) {
            Log.e(TAG, "Fail to retrieve files directory");
            return null;
        }
        File file = new File(filesDir.getAbsolutePath() + "/handwritten_temp/");
        boolean zExists = file.exists();
        if (!mkdirs(file)) {
            Log.e(TAG, "Fail to create handwritten_temp directory: " + file.getPath());
            return null;
        }
        if (!zExists) {
            file.setExecutable(true, false);
        }
        return file;
    }

    private static boolean mkdirs(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            }
            Log.e(TAG, "File path is not a directory: " + file.getPath());
            return false;
        }
        if (file.mkdirs()) {
            return true;
        }
        Log.e(TAG, "Fail to create directory: " + file.getPath());
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x0073 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x0075 A[Catch: IOException -> 0x0071, TRY_LEAVE, TryCatch #4 {IOException -> 0x0071, blocks: (B:43:0x006d, B:47:0x0075), top: B:51:0x006d }] */
    /* JADX WARN: Code duplicated, block: B:51:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static String copyFileFromUri(Context context, Uri uri, String str) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        InputStream inputStream = null;
        try {
            try {
                File file = new File(str);
                if (!file.exists()) {
                    file.createNewFile();
                }
                InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                try {
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str, false));
                    try {
                        byte[] bArr = new byte[1024];
                        if (inputStreamOpenInputStream != null) {
                            while (true) {
                                int i = inputStreamOpenInputStream.read(bArr);
                                if (i <= 0) {
                                    break;
                                }
                                bufferedOutputStream.write(bArr, 0, i);
                            }
                        }
                        if (inputStreamOpenInputStream != null) {
                            inputStreamOpenInputStream.close();
                        }
                        bufferedOutputStream.close();
                        return str;
                    } catch (IOException e) {
                        inputStream = inputStreamOpenInputStream;
                        e = e;
                        try {
                            e.printStackTrace();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                            return str;
                        } catch (Throwable th) {
                            th = th;
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                    if (bufferedOutputStream != null) {
                                        bufferedOutputStream.close();
                                    }
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    throw th;
                                }
                            } else if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        inputStream = inputStreamOpenInputStream;
                        th = th2;
                        if (inputStream != null) {
                            inputStream.close();
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                        } else if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    inputStream = inputStreamOpenInputStream;
                    e = e3;
                    bufferedOutputStream = null;
                } catch (Throwable th3) {
                    inputStream = inputStreamOpenInputStream;
                    th = th3;
                    bufferedOutputStream = null;
                }
            } catch (IOException e4) {
                e = e4;
                bufferedOutputStream = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedOutputStream = null;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            return str;
        }
    }
}
