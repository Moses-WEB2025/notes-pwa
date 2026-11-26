package com.miui.notes.tool.tapshareutils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.common.tool.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes4.dex */
public class TapShareFileCleaner {
    private static final String TAG = "TapShareFileCleaner";

    private static boolean isValidParameters(Context context, Uri uri) {
        return (context == null || uri == null) ? false : true;
    }

    private static boolean isValidParameters(Context context, TapShareDataModel.SingleNoteTapShareData singleNoteTapShareData) {
        return (context == null || singleNoteTapShareData == null) ? false : true;
    }

    public static boolean cleanTapShareFile(Context context, Uri uri) {
        if (!isValidParameters(context, uri)) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for cleanTapShareFile");
            return false;
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            Logger.INSTANCE.e(TAG, "File path is null for URI: ");
            return false;
        }
        return deleteFileIfExists(new File(path));
    }

    public static void cleanTapShareFiles(Context context, List<Uri> list) {
        if (!isValidParameters(context, list)) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for cleanTapShareFiles");
            return;
        }
        Iterator<Uri> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (cleanTapShareFile(context, it.next())) {
                i++;
            }
        }
        Logger.INSTANCE.d(TAG, "Cleaned " + i + InternalZipConstants.ZIP_FILE_SEPARATOR + list.size() + " TapShare files");
    }

    public static void cleanSingleNoteTapShareData(Context context, TapShareDataModel.SingleNoteTapShareData singleNoteTapShareData) {
        if (!isValidParameters(context, singleNoteTapShareData)) {
            Logger.INSTANCE.e(TAG, "Invalid parameters for cleanSingleNoteTapShareData");
        } else {
            cleanTapShareFiles(context, collectFilesFromTapShareData(singleNoteTapShareData));
        }
    }

    public static int cleanAllTapShareFilesInCache(Context context) {
        if (context == null) {
            Logger.INSTANCE.e(TAG, "Context is null for cleanAllTapShareFilesInCache");
            return 0;
        }
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            return cleanFilesInDirectory(externalCacheDir, new String[]{".zip", ".png"});
        }
        Logger.INSTANCE.e(TAG, "Cache directory is null");
        return 0;
    }

    public static void asyncCleanAllTapShareFiles(final Context context) {
        if (context == null) {
            return;
        }
        new Thread(new Runnable() { // from class: com.miui.notes.tool.tapshareutils.TapShareFileCleaner$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TapShareFileCleaner.cleanAllTapShareFilesInCache(context);
            }
        }).start();
    }

    private static boolean isValidParameters(Context context, List<Uri> list) {
        return (context == null || list == null || list.isEmpty()) ? false : true;
    }

    private static boolean deleteFileIfExists(File file) {
        try {
            if (!file.exists()) {
                Logger.INSTANCE.d(TAG, "TapShare file does not exist: " + file.getPath());
                return true;
            }
            boolean zDelete = file.delete();
            logDeletionResult(zDelete, file.getPath());
            return zDelete;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "Error cleaning TapShare file: " + e.getMessage());
            return false;
        }
    }

    private static void logDeletionResult(boolean z, String str) {
        if (z) {
            Logger.INSTANCE.d(TAG, "Successfully deleted TapShare file: " + str);
        } else {
            Logger.INSTANCE.w(TAG, "Failed to delete TapShare file: " + str);
        }
    }

    private static List<Uri> collectFilesFromTapShareData(TapShareDataModel.SingleNoteTapShareData singleNoteTapShareData) {
        ArrayList arrayList = new ArrayList();
        if (singleNoteTapShareData.zipUri != null) {
            arrayList.add(singleNoteTapShareData.zipUri);
        }
        if (singleNoteTapShareData.previewImageUri != null) {
            arrayList.add(singleNoteTapShareData.previewImageUri);
        }
        return arrayList;
    }

    private static int cleanFilesInDirectory(File file, String[] strArr) {
        File[] fileArrListFiles;
        if (file == null || !file.exists() || !file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return 0;
        }
        return processFilesInDirectory(fileArrListFiles, strArr);
    }

    private static int processFilesInDirectory(File[] fileArr, String[] strArr) {
        int iProcessFileInDirectory = 0;
        for (File file : fileArr) {
            iProcessFileInDirectory += processFileInDirectory(file, strArr);
        }
        Logger.INSTANCE.d(TAG, "Cleaned " + iProcessFileInDirectory + " files from directory");
        return iProcessFileInDirectory;
    }

    private static int processFileInDirectory(File file, String[] strArr) {
        if (file.isFile()) {
            return processRegularFile(file, strArr);
        }
        if (file.isDirectory()) {
            return cleanFilesInDirectory(file, strArr);
        }
        return 0;
    }

    private static int processRegularFile(File file, String[] strArr) {
        if (!shouldDeleteFile(file, strArr)) {
            return 0;
        }
        if (file.delete()) {
            Logger.INSTANCE.d(TAG, "Deleted TapShare file: " + file.getPath());
            return 1;
        }
        Logger.INSTANCE.w(TAG, "Failed to delete TapShare file: " + file.getPath());
        return 0;
    }

    private static boolean shouldDeleteFile(File file, String[] strArr) {
        String lowerCase = file.getName().toLowerCase();
        return hasMatchingExtension(lowerCase, strArr) && hasTapSharePattern(lowerCase);
    }

    private static boolean hasMatchingExtension(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str.endsWith(str2.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTapSharePattern(String str) {
        return str.contains("tapshare") || str.startsWith("preview_") || str.matches(".*id\\d+_note\\.zip");
    }
}
