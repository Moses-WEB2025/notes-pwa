package com.miui.pad.feature.notes.handwrite;

import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class SinglePagePathUtils {
    private static final String TAG = "SinglePagePathUtils";

    public static String getHandWrittenCalcPath(String str) {
        File handWrittenCalcDir = getHandWrittenCalcDir();
        return (handWrittenCalcDir != null ? handWrittenCalcDir.getAbsolutePath() : "") + File.separator + str;
    }

    public static final File getHandWrittenCalcDir() {
        File filesDir = NoteApp.getInstance().getFilesDir();
        if (filesDir == null) {
            Logger.INSTANCE.e(TAG, "Fail to retrieve files directory");
            return null;
        }
        File file = new File(filesDir.getAbsolutePath() + "/handwritten_calc/");
        boolean zExists = file.exists();
        if (!mkdirs(file)) {
            Logger.INSTANCE.e(TAG, "Fail to create handwritten_calc directory: " + file.getPath());
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
            Logger.INSTANCE.e(TAG, "File path is not a directory: " + file.getPath());
            return false;
        }
        if (file.mkdirs()) {
            return true;
        }
        Logger.INSTANCE.e(TAG, "Fail to create directory: " + file.getPath());
        return false;
    }
}
