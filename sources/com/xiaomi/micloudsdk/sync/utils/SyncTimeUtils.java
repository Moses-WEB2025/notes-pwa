package com.xiaomi.micloudsdk.sync.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: loaded from: classes4.dex */
public class SyncTimeUtils {
    private static final int MAX_SYNC_COUNT_IN_WINDOW = 30;
    private static final String PREF_SYNC_COUNT_IN_WINDOW = "SyncTimes_%s";
    private static final String PREF_SYNC_WINDOW_START = "SyncWindowStart_%s";
    private static final long SYNC_TIME_WINDOW_IN_MILLIS = 600000;

    public static synchronized long getGuardDelayUntilInSec(Context context, String str) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str2 = String.format(PREF_SYNC_WINDOW_START, str);
        String str3 = String.format(PREF_SYNC_COUNT_IN_WINDOW, str);
        long j = defaultSharedPreferences.getLong(str2, 0L);
        int i = defaultSharedPreferences.getInt(str3, 1);
        if (Math.abs(jCurrentTimeMillis - j) < SYNC_TIME_WINDOW_IN_MILLIS) {
            if (i > 30) {
                return ((j + SYNC_TIME_WINDOW_IN_MILLIS) - jCurrentTimeMillis) / 1000;
            }
            defaultSharedPreferences.edit().putInt(str3, i + 1).commit();
            return 0L;
        }
        SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
        editorEdit.putLong(str2, jCurrentTimeMillis);
        editorEdit.putInt(str3, 1);
        editorEdit.commit();
        return 0L;
    }
}
