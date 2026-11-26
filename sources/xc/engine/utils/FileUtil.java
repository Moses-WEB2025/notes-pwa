package xc.engine.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.util.ab;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.api.CreationPageSDK;

/* JADX INFO: loaded from: classes6.dex */
public class FileUtil {
    public static final String DEFAULT_ENT_NAME = "multi_page_ent.ent";
    public static final String PAGE_EXPORT = "/page_export/";
    public static final String PRE_VIEW = "/preview";
    private static final String TAG = "FileUtil";
    public static final String TEMP_ENT_NAME = "temp_multi_page.ent";
    public static final String TMP_DATA = "/tmp_data";
    public static final String TMP_PAGE = "/tmp_page/";

    public static String getDir(Context context, String str) {
        String str2;
        if (TextUtils.equals(str, TMP_DATA)) {
            str2 = context.getExternalCacheDir() + str;
        } else if (TextUtils.equals(str, PAGE_EXPORT) || TextUtils.equals(str, TMP_PAGE)) {
            str2 = context.getExternalCacheDir() + str + UUID.randomUUID().toString() + InternalZipConstants.ZIP_FILE_SEPARATOR;
        } else {
            str2 = str + PRE_VIEW;
        }
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String copyPdfFileToMultiDir(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (file.exists() && file.isFile() && file.canRead()) {
            File file2 = new File(str2, file.getName());
            if (!file2.getParentFile().exists()) {
                file2.getParentFile().mkdirs();
            }
            if (file2.exists()) {
                return file2.getName();
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i > 0) {
                        fileOutputStream.write(bArr, 0, i);
                    } else {
                        fileInputStream.close();
                        fileOutputStream.close();
                        return file2.getName();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getUnbindDataPath() {
        File file = new File(CreationPageSDK.sApplication.getExternalCacheDir() + "/page_unbind/", UUID.randomUUID().toString());
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getUnbindDataPath:" + file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public static boolean createOrExistsDir(String str) {
        return createOrExistsDir(getFileByPath(str));
    }

    public static boolean createOrExistsDir(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdirs();
    }

    public static File getFileByPath(String str) {
        if ("".equals(str) || str == null) {
            return null;
        }
        return new File(str);
    }

    public static boolean deleteDirs(File file, boolean z) {
        File[] fileArrListFiles;
        boolean z2 = false;
        z2 = false;
        z2 = false;
        z2 = false;
        z2 = false;
        if (file != null && file.exists()) {
            if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length != 0) {
                boolean zDelete = false;
                for (File file2 : fileArrListFiles) {
                    zDelete = file2.delete();
                }
                z2 = zDelete;
            }
            if (z) {
                return file.delete();
            }
        }
        return z2;
    }

    public static String getFileExtension(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str.lastIndexOf(File.separator);
        if (iLastIndexOf == -1 || iLastIndexOf2 >= iLastIndexOf) {
            return "";
        }
        return str.substring(iLastIndexOf + 1);
    }

    public static String getEntPathByDir(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            return new File(file, "multi_page_ent.ent").getAbsolutePath();
        }
        return null;
    }

    public static String getTempEntPathByDir(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            return new File(file, TEMP_ENT_NAME).getAbsolutePath();
        }
        return null;
    }

    public static boolean deleteDir(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    if (!deleteFile(file2)) {
                        return false;
                    }
                } else if (file2.isDirectory() && !deleteDir(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile() && file.delete();
        }
        return true;
    }

    public static List<String> listFilesInDirs(String str) {
        return listFilesInDirs(getFileByPath(str));
    }

    public static List<String> listFilesInDirs(File file) {
        if (file == null || !isDir(file)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, (String[]) Objects.requireNonNull(file.list()));
        return arrayList;
    }

    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    public static boolean isFileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(str).exists();
    }

    private static void createDestDirectoryIfNecessary(String str) {
        File file;
        if (str.endsWith(File.separator)) {
            file = new File(str);
        } else {
            file = new File(str.substring(0, str.lastIndexOf(File.separator)));
        }
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    private static String buildDestinationZipFilePath(String str, String str2, String str3) {
        if (!str2.isEmpty()) {
            str2 = str2 + File.separator + str + "." + str3;
        } else {
            createDestDirectoryIfNecessary(str2);
            if (str2.endsWith(File.separator)) {
                str2 = str2 + str + "." + str3;
            }
        }
        if (!isFileExists(str2)) {
            return str2;
        }
        StringBuilder sb = new StringBuilder(str2.split(ab.a)[0]);
        int i = 1;
        while (true) {
            StringBuilder sb2 = new StringBuilder(sb.toString().split("\\(")[0]);
            String str4 = ((Object) sb2.append("(").append(i).append(")")) + "." + str3;
            if (!isFileExists(str4)) {
                return str4;
            }
            i++;
            sb = sb2;
        }
    }

    public static String zip(String[] strArr, String str, String str2, boolean z, String str3, String str4) {
        ZipFile zipFile;
        ArrayList arrayList = new ArrayList();
        for (String str5 : strArr) {
            File file = new File(str5);
            if (file.exists()) {
                arrayList.add(file);
            }
        }
        String strBuildDestinationZipFilePath = buildDestinationZipFilePath(str, str2, str4);
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(CompressionMethod.DEFLATE);
        zipParameters.setCompressionLevel(CompressionLevel.NORMAL);
        if (!str3.isEmpty()) {
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipFile = new ZipFile(strBuildDestinationZipFilePath, str3.toCharArray());
        } else {
            zipFile = new ZipFile(strBuildDestinationZipFilePath);
        }
        zipFile.setCharset(StandardCharsets.UTF_8);
        try {
            if (!((File) arrayList.get(0)).isDirectory()) {
                zipFile.addFiles(arrayList, zipParameters);
                return strBuildDestinationZipFilePath;
            }
            if (!z) {
                File[] fileArrListFiles = ((File) arrayList.get(0)).listFiles();
                ArrayList arrayList2 = new ArrayList();
                if (fileArrListFiles == null || fileArrListFiles.length <= 0) {
                    return null;
                }
                Collections.addAll(arrayList2, fileArrListFiles);
                zipFile.addFiles(arrayList2, zipParameters);
                return strBuildDestinationZipFilePath;
            }
            zipFile.addFolder((File) arrayList.get(0), zipParameters);
            return strBuildDestinationZipFilePath;
        } catch (ZipException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00a2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x00a4 A[Catch: IOException -> 0x00a0, TRY_LEAVE, TryCatch #6 {IOException -> 0x00a0, blocks: (B:45:0x009c, B:49:0x00a4), top: B:57:0x009c }] */
    /* JADX WARN: Code duplicated, block: B:57:0x009c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static void copyFile(String str, String str2) throws Throwable {
        FileOutputStream fileOutputStream;
        String str3;
        StringBuilder sb;
        FileInputStream fileInputStream = null;
        try {
            File file = new File(str2);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileInputStream fileInputStream2 = new FileInputStream(str);
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str2);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream2.read(bArr);
                        if (i > 0) {
                            fileOutputStream2.write(bArr, 0, i);
                        } else {
                            try {
                                fileInputStream2.close();
                                fileOutputStream2.close();
                                return;
                            } catch (IOException e) {
                                e = e;
                                e.printStackTrace();
                                str3 = TAG;
                                sb = new StringBuilder("copyFile:");
                            }
                        }
                        Log.d(str3, sb.append(e).toString());
                        return;
                    }
                } catch (Exception e2) {
                    fileOutputStream = fileOutputStream2;
                    e = e2;
                    fileInputStream = fileInputStream2;
                    try {
                        e.printStackTrace();
                        Log.d(TAG, "copyFile:" + e);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e3) {
                                e = e3;
                                e.printStackTrace();
                                str3 = TAG;
                                sb = new StringBuilder("copyFile:");
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                Log.d(TAG, "copyFile:" + e4);
                                throw th;
                            }
                        } else if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    fileOutputStream = fileOutputStream2;
                    th = th2;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } else if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                fileOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        } catch (Exception e6) {
            e = e6;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
    }

    public static void copyFile(File file, File file2) {
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return;
        }
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i > 0) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyUriToFile(Context context, Uri uri, File file) throws IOException {
        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStreamOpenInputStream.read(bArr);
            if (i > 0) {
                fileOutputStream.write(bArr, 0, i);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStreamOpenInputStream.close();
                return;
            }
        }
    }

    public static boolean isPDF(String str) {
        String fileExtension = getFileExtension(str);
        return fileExtension != null && fileExtension.equals("pdf");
    }

    public static String getFileName(String str) {
        String strSubstring = str.substring(str.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR) + 1, str.lastIndexOf(".") > 0 ? str.lastIndexOf(".") : str.length());
        try {
            return URLDecoder.decode(strSubstring, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return strSubstring;
        }
    }

    public static String getUniqueFileName(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return str;
        }
        String name = file.getName();
        String strSubstring = name.substring(name.lastIndexOf("."));
        int i = 0;
        while (file.exists()) {
            i++;
            file = new File(file.getParent(), name.substring(0, name.lastIndexOf(".")) + "(" + i + ")" + strSubstring);
        }
        return file.getAbsolutePath();
    }

    public static String getUniqueFolderName(String str, String str2) {
        if (!new File(str, str2).exists()) {
            return str2;
        }
        int i = 1;
        while (true) {
            String str3 = str2 + "(" + i + ")";
            if (!new File(str, str3).exists()) {
                return str3;
            }
            i++;
        }
    }

    public static String getBgFileName(String str) {
        int iLastIndexOf;
        return (str == null || "".equals(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? str : str.substring(iLastIndexOf + 1);
    }

    public static Bitmap decodeBgImageFile(String str, String str2, int i, int i2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File file = new File(str2);
        if (!file.exists()) {
            file = new File(str, str2);
        }
        if (file.exists()) {
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            options.inJustDecodeBounds = false;
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            if (bitmapDecodeFile != null) {
                float width = i / bitmapDecodeFile.getWidth();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_4444);
                if (bitmapCreateBitmap != null) {
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    canvas.drawColor(0);
                    Matrix matrix = new Matrix();
                    Paint paint = new Paint();
                    matrix.postScale(width, width);
                    canvas.drawBitmap(bitmapDecodeFile, matrix, paint);
                }
                return bitmapCreateBitmap;
            }
        }
        return null;
    }

    public static Bitmap decodeOriginImageFile(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        File file = new File(str2);
        if (!file.exists()) {
            file = new File(str, str2);
        }
        if (!file.exists()) {
            return null;
        }
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static void copyFilesAssets(Context context, String str, String str2) {
        try {
            String[] list = context.getAssets().list(str);
            if (list.length > 0) {
                new File(str2).mkdirs();
                for (String str3 : list) {
                    copyFilesAssets(context, str + InternalZipConstants.ZIP_FILE_SEPARATOR + str3, str2 + InternalZipConstants.ZIP_FILE_SEPARATOR + str3);
                }
                return;
            }
            InputStream inputStreamOpen = context.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStreamOpen.read(bArr);
                if (i != -1) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileOutputStream.flush();
                    inputStreamOpen.close();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyDirectory(File file, File file2) throws IOException {
        if (!file2.exists()) {
            file2.mkdir();
        }
        for (File file3 : file.listFiles()) {
            if (file3.isDirectory()) {
                copyDirectory(file3, new File(file2, file3.getName()));
            } else {
                Files.copy(file3.toPath(), new File(file2, file3.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void saveJsonToFile(String str, String str2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
        } catch (Exception unused) {
        }
    }

    public static String getJsonFromFile(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                sb.append(new String(bArr, 0, i));
            }
            fileInputStream.close();
        } catch (Exception unused) {
        }
        return sb.toString();
    }

    public static String removeExtension(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return (iLastIndexOf <= 0 || iLastIndexOf >= str.length() + (-1)) ? str : str.substring(0, iLastIndexOf);
    }

    public static boolean moveFile(File file, File file2) {
        if (file != null && file2 != null && file.exists() && file.isFile()) {
            if (file2.isDirectory()) {
                file2 = new File(file2, file.getName());
            }
            try {
                if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
                    return true;
                }
            } catch (IOException unused) {
            }
            File parentFile = file2.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                return false;
            }
            try {
                Files.move(file.toPath(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Log.d(TAG, "ww-- moveFile: ");
                return true;
            } catch (IOException unused2) {
            }
        }
        return false;
    }
}
