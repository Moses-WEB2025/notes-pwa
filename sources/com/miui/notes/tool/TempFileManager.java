package com.miui.notes.tool;

import android.content.Context;
import com.miui.notes.NoteApp;
import com.miui.notes.base.utils.Logger;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class TempFileManager {
    private static final int MAX_COUNT = 4;
    private static final String TAG = "TempFileManager";
    private static final String TEMP_DIR_NAME = "temp";
    private static volatile TempFileManager mInstance;
    private Context mContext = NoteApp.getInstance();
    private List<File> mTempFiles = new ArrayList();
    private File mTempRoot;

    public static TempFileManager getInstance() {
        if (mInstance == null) {
            synchronized (TempFileManager.class) {
                if (mInstance == null) {
                    mInstance = new TempFileManager();
                }
            }
        }
        return mInstance;
    }

    private TempFileManager() {
        initialize();
    }

    private void initialize() {
        File externalCacheDir = this.mContext.getExternalCacheDir();
        if (externalCacheDir == null) {
            Logger.INSTANCE.e(TAG, "Fail to getCacheDir");
            return;
        }
        File file = new File(externalCacheDir, "temp");
        if (!file.exists() && !file.mkdirs() && !file.exists()) {
            Logger.INSTANCE.e(TAG, "Fail to create tempRoot: " + file);
            return;
        }
        this.mTempRoot = file;
        file.setExecutable(true, false);
        this.mTempRoot.listFiles(new FileFilter() { // from class: com.miui.notes.tool.TempFileManager.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                file2.delete();
                return false;
            }
        });
    }

    public File createFile(String str, String str2) {
        File file = this.mTempRoot;
        if (file == null) {
            Logger.INSTANCE.e(TAG, "Fail to createFile, mTempRoot is null");
            return null;
        }
        try {
            File fileCreateTempFile = File.createTempFile(str, str2, file);
            this.mTempFiles.add(fileCreateTempFile);
            trim();
            return fileCreateTempFile;
        } catch (IOException e) {
            Logger.INSTANCE.e(TAG, "Fail to createFile,exception:" + e);
            return null;
        }
    }

    private void trim() {
        if (this.mTempFiles.size() > 4) {
            for (int size = this.mTempFiles.size() - 5; size >= 0; size--) {
                this.mTempFiles.remove(size).delete();
            }
        }
    }
}
