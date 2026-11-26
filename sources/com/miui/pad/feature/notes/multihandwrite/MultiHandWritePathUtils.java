package com.miui.pad.feature.notes.multihandwrite;

import com.miui.notes.NoteApp;
import com.miui.notes.tool.AttachmentUtils;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class MultiHandWritePathUtils {
    public static final String ENT_MULTI_ENGINE_AND_PAGE = "ent_multi_engine_and_page_";
    public static final String ENT_NAME = "multi_page_ent.ent";
    public static final String MULTI_PAGE_RECOGNIZE_JSON = "multiRecognize.json";
    private static final String TAG = "MultiHandWritePathUtils";

    public static final String getEntFileCompletePath(String str) {
        return AttachmentUtils.getAttachmentDir(NoteApp.getInstance()) + File.separator + str;
    }

    public static String generateEngineAndPageEntDir() {
        File file = new File(AttachmentUtils.getAttachmentDir(NoteApp.getInstance()) + File.separator + "ent_multi_engine_and_page_" + System.currentTimeMillis());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static String getRelativePathByComplete(String str) {
        return str.substring(str.indexOf("attachment/") + "attachment/".length());
    }

    public static String getRelativePathByFileName(String str, String str2) {
        return str.substring(str.lastIndexOf(File.separator) + 1) + File.separator + str2;
    }

    public static String getMultiPageRecognizeJsonPath(String str) {
        return str + File.separator + MULTI_PAGE_RECOGNIZE_JSON;
    }
}
