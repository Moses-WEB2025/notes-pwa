package com.miui.notes.cloudservice;

import android.content.Context;
import android.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NotesUtils {
    private static final String PREF_SYNCED_BEFORE = "com.miui.notes.cloudservice.notes.pref_synced_before";
    private static final String REPLACE_ANCIENT_IMAGE = "<img fileid=\"$1\" />";
    private static final String TAG = "NotesUtils";
    private static final Pattern PATTERN_ANCIENT_IMAGE = Pattern.compile("^☺ ([^\r\n]+)$", 8);
    private static final Pattern PATTERN_INTERNAL_MEDIA = Pattern.compile("fileid=\"([^\"]+?)\"");
    private static final char[] EMPTY_CHARS = {'\t', '\n', CharUtils.CR, ' '};

    public static String formatLong(long j) {
        return j == 0 ? "" : Long.toString(j);
    }

    public static String concatUrl(String str, Object... objArr) {
        return NotesConstants.URL_BASE + formatUrl(str, objArr);
    }

    public static String formatUrl(String str, Object... objArr) {
        return String.format(Locale.US, str, objArr);
    }

    public static boolean getSyncedBefore(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_SYNCED_BEFORE, false);
    }

    public static void setSyncedBefore(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREF_SYNCED_BEFORE, z).apply();
    }

    public static List<String> retrieveMedias(String str) {
        Matcher matcher = PATTERN_INTERNAL_MEDIA.matcher(PATTERN_ANCIENT_IMAGE.matcher(str).replaceAll(REPLACE_ANCIENT_IMAGE));
        ArrayList arrayList = new ArrayList();
        while (matcher.find()) {
            arrayList.add(matcher.group(1));
        }
        return arrayList;
    }

    public static String sqliteTrim(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (Arrays.binarySearch(EMPTY_CHARS, str.charAt(i)) < 0) {
                break;
            }
            i++;
        }
        while (length > i) {
            if (Arrays.binarySearch(EMPTY_CHARS, str.charAt(length - 1)) < 0) {
                break;
            }
            length--;
        }
        if (i < length) {
            return str.substring(i, length);
        }
        return "";
    }

    public static void checkInterrupted() throws NoteSyncException {
        if (Thread.interrupted()) {
            throw new NoteSyncException("Sync canceled");
        }
    }
}
