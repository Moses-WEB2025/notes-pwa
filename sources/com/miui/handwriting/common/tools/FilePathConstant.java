package com.miui.handwriting.common.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class FilePathConstant {
    public static final String ENT_MULTI_ENGINE_AND_PAGE = "ent_multi_engine_and_page_";
    public static final String ENT_SINGLE_LIMIT_LESS = "ent_single_page_";
    public static final String TEMP = "temp";
    public static final String THUMBNAIL = "thumbnail";
    public static final String THUMBNAIL_FILE_NAME = "Export_multi_page-0.jpg";
    public static String entFilePath;
    public static String exportFilePath;

    public static void setPath() {
    }

    public static String getSinglePageEntPath() {
        return new File(entFilePath + File.separator + ENT_SINGLE_LIMIT_LESS + System.currentTimeMillis()).getAbsolutePath();
    }

    public static String getEntFileName() {
        return "xiaoxi_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date(System.currentTimeMillis())) + ".ent";
    }

    public static String getEntFilePathThumb(String str) {
        return new File(str + File.separator + "thumbnail").getAbsolutePath();
    }

    public static String getEntFilePath() {
        return entFilePath;
    }

    public static String getEngineAndPageEntPath() {
        return new File(entFilePath + File.separator + "ent_multi_engine_and_page_" + System.currentTimeMillis()).getAbsolutePath();
    }

    public static String getExportFilePath() {
        return exportFilePath;
    }

    public static String getCoverBitmapName() {
        return "img_cover.png";
    }

    public static String keepLastLevels(String str, int i) {
        if (i <= 0 || str == null) {
            return "";
        }
        String str2 = File.separator;
        String[] strArrSplit = str.split(Pattern.quote(str2), -1);
        if (i > strArrSplit.length) {
            return "";
        }
        return String.join(str2, (String[]) Arrays.copyOfRange(strArrSplit, strArrSplit.length - i, strArrSplit.length));
    }
}
