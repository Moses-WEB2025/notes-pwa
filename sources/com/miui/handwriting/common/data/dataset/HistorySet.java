package com.miui.handwriting.common.data.dataset;

import android.content.SharedPreferences;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.tools.constants.Constants;

/* JADX INFO: loaded from: classes3.dex */
public class HistorySet {
    public static boolean isIsDoubleClickUndo() {
        return HandWritingViewApplication.INSTANCE.getApplication().getSharedPreferences(Constants.CREATION_SHPREF, 0).getBoolean("isDoubleClickUndo", true);
    }

    public static void setIsDoubleClickUndo(boolean z) {
        SharedPreferences.Editor editorEdit = HandWritingViewApplication.INSTANCE.getApplication().getSharedPreferences(Constants.CREATION_SHPREF, 0).edit();
        editorEdit.putBoolean("isDoubleClickUndo", z);
        editorEdit.apply();
    }

    public static void clear() {
        setIsDoubleClickUndo(true);
    }
}
