package com.market.sdk.utils;

import android.content.SharedPreferences;
import android.os.Looper;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class PrefUtils {

    public enum PrefFile {
        DEFAULT("com.xiaomi.market.sdk_pref", false);

        public final String fileName;
        public boolean isMultiProcess;

        PrefFile(String str, boolean z) {
            this.fileName = str;
            this.isMultiProcess = z;
        }
    }

    public static SharedPreferences getSharedPref() {
        return getSharedPref(PrefFile.DEFAULT);
    }

    public static SharedPreferences getSharedPref(PrefFile prefFile) {
        return AppGlobal.getContext().getSharedPreferences(prefFile.fileName, prefFile.isMultiProcess ? 4 : 0);
    }

    private static SharedPreferences getSharedPrefFromParams(PrefFile[] prefFileArr) {
        PrefFile prefFile;
        if (prefFileArr.length == 0) {
            prefFile = PrefFile.DEFAULT;
        } else {
            prefFile = prefFileArr[0];
        }
        return getSharedPref(prefFile);
    }

    private static void applyOrCommit(SharedPreferences.Editor editor) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static void setInt(String str, int i, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putInt(str, i);
        applyOrCommit(editorEdit);
    }

    public static boolean setIntSync(String str, int i, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putInt(str, i);
        return editorEdit.commit();
    }

    public static int getInt(String str, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getInt(str, 0);
    }

    public static int getInt(String str, int i, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getInt(str, i);
    }

    public static void setLong(String str, long j, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putLong(str, j);
        applyOrCommit(editorEdit);
    }

    public static boolean setLongSync(String str, long j, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putLong(str, j);
        return editorEdit.commit();
    }

    public static long getLong(String str, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getLong(str, 0L);
    }

    public static long getLong(String str, long j, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getLong(str, j);
    }

    public static boolean getBoolean(String str, boolean z, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getBoolean(str, z);
    }

    public static void setBoolean(String str, boolean z, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putBoolean(str, z);
        applyOrCommit(editorEdit);
    }

    public static boolean setBooleanSync(String str, boolean z, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putBoolean(str, z);
        return editorEdit.commit();
    }

    public static String getString(String str, String str2, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getString(str, str2);
    }

    public static Set<String> getStringSet(String str, Set<String> set, PrefFile... prefFileArr) {
        return getSharedPrefFromParams(prefFileArr).getStringSet(str, set);
    }

    public static void setString(String str, String str2, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putString(str, str2);
        applyOrCommit(editorEdit);
    }

    public static void setStringSet(String str, Set<String> set, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putStringSet(str, set);
        applyOrCommit(editorEdit);
    }

    public static boolean setStringSync(String str, String str2, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.putString(str, str2);
        return editorEdit.commit();
    }

    public static void remove(String str, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.remove(str);
        applyOrCommit(editorEdit);
    }

    public static boolean removeSync(String str, PrefFile... prefFileArr) {
        SharedPreferences.Editor editorEdit = getSharedPrefFromParams(prefFileArr).edit();
        editorEdit.remove(str);
        return editorEdit.commit();
    }
}
