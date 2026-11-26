package com.miui.maml.util;

import android.text.TextUtils;
import com.miui.maml.ResourceLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import net.lingala.zip4j.util.InternalZipConstants;

/* JADX INFO: loaded from: classes3.dex */
public class FolderResourceLoader extends ResourceLoader {
    private static final String LOG_TAG = "FolderResourceLoader";
    private final String mResourcePath;

    public FolderResourceLoader(String str) {
        this.mResourcePath = str;
    }

    @Override // com.miui.maml.ResourceLoader
    public boolean resourceExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return new File(this.mResourcePath + InternalZipConstants.ZIP_FILE_SEPARATOR + str).exists();
    }

    @Override // com.miui.maml.ResourceLoader
    public File getExtraFile(String str) {
        if (str.startsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
            return new File(str);
        }
        File file = new File(this.mResourcePath, str);
        if (file.exists()) {
            return file;
        }
        File parentFile = file.getParentFile();
        File parentFile2 = parentFile != null ? parentFile.getParentFile() : null;
        if (parentFile2 == null) {
            return null;
        }
        File file2 = new File(parentFile2, str);
        if (file2.exists()) {
            return file2;
        }
        return null;
    }

    @Override // com.miui.maml.ResourceLoader
    public InputStream getInputStream(String str, long[] jArr) {
        FileInputStream fileInputStream = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(this.mResourcePath + InternalZipConstants.ZIP_FILE_SEPARATOR + str);
                if (jArr != null) {
                    try {
                        if (jArr.length > 0) {
                            jArr[0] = fileInputStream2.available();
                            return fileInputStream2;
                        }
                    } catch (Exception e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        e.printStackTrace();
                        return fileInputStream;
                    }
                }
                return fileInputStream2;
            } catch (Exception e2) {
                e = e2;
            }
        }
        return fileInputStream;
    }

    @Override // com.miui.maml.ResourceLoader
    public String getID() {
        return LOG_TAG + this.mResourcePath;
    }
}
