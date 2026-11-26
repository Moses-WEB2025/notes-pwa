package com.xiaomi.phonenum.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class SharedPrefsStringCache {
    public static final int DEFAULT_CLEAR_KEEP_COUNT = 10;
    public static final int DEFAULT_CLEAR_MAX_COUNT = 20;
    private static final String PREFIX_ACCESS_TIMESTAMP = "at-";
    private static final String PREFIX_ENTITY = "et-";
    private final SharedPreferences sharedPrefs;

    public SharedPrefsStringCache(Context context, String str) {
        this.sharedPrefs = context.getSharedPreferences(str, 0);
    }

    public synchronized String get(String str) {
        String string;
        string = this.sharedPrefs.getString(wrapToEntityKey(str), null);
        if (string != null) {
            this.sharedPrefs.edit().putLong(wrapToAccessTimestampKey(str), System.currentTimeMillis()).commit();
        }
        return string;
    }

    public synchronized void put(String str, String str2) {
        this.sharedPrefs.edit().putString(wrapToEntityKey(str), str2).putLong(wrapToAccessTimestampKey(str), System.currentTimeMillis()).commit();
        clearIfNeededDefaultLocked();
    }

    public synchronized void remove(String str) {
        SharedPreferences.Editor editorEdit = this.sharedPrefs.edit();
        removeWithoutCommitLocked(editorEdit, str);
        editorEdit.commit();
    }

    private void removeWithoutCommitLocked(SharedPreferences.Editor editor, String str) {
        editor.remove(wrapToEntityKey(str)).remove(wrapToAccessTimestampKey(str));
    }

    private String wrapToEntityKey(String str) {
        return PREFIX_ENTITY + str;
    }

    private String wrapToAccessTimestampKey(String str) {
        return PREFIX_ACCESS_TIMESTAMP + str;
    }

    private String getRealKeyFromAccessTimestampKey(String str) {
        return str.substring(PREFIX_ACCESS_TIMESTAMP.length());
    }

    private boolean isAccessTimestampKey(String str) {
        return str.startsWith(PREFIX_ACCESS_TIMESTAMP);
    }

    private void clearIfNeededDefaultLocked() {
        clearIfNeededLocked(20, 10);
    }

    private void clearIfNeededLocked(int i, int i2) {
        Map<String, ?> all = this.sharedPrefs.getAll();
        if (all.size() <= i * 2) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : all.keySet()) {
            if (isAccessTimestampKey(str)) {
                arrayList.add(new Pair(getRealKeyFromAccessTimestampKey(str), Long.valueOf(this.sharedPrefs.getLong(str, 0L))));
            }
        }
        Collections.sort(arrayList, new Comparator<Pair<String, Long>>() { // from class: com.xiaomi.phonenum.data.SharedPrefsStringCache.1
            @Override // java.util.Comparator
            public int compare(Pair<String, Long> pair, Pair<String, Long> pair2) {
                if (((Long) pair.second).longValue() < ((Long) pair2.second).longValue()) {
                    return -1;
                }
                return pair.second == pair2.second ? 0 : 1;
            }
        });
        SharedPreferences.Editor editorEdit = this.sharedPrefs.edit();
        for (int i3 = 0; i3 < arrayList.size() - i2; i3++) {
            removeWithoutCommitLocked(editorEdit, (String) ((Pair) arrayList.get(i3)).first);
        }
        editorEdit.commit();
    }
}
