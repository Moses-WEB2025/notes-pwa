package com.miui.todo.data.utils;

import android.content.SharedPreferences;
import com.miui.notes.NoteApp;

/* JADX INFO: loaded from: classes4.dex */
public class DragSoftPreferenceUtils {
    private static final String PREF_KEY_IS_FIRST_TODO_CLOUD_INIT = "key_is_first_todo_cloud_initial";
    private static final String PREF_KEY_MAX_SORT_ID = "todo_max_sort_id";

    private static SharedPreferences getMaxSortIDSharedPreferences() {
        return NoteApp.getInstance().getSharedPreferences(PREF_KEY_MAX_SORT_ID, 0);
    }

    public static void setMaxSortID(long j) {
        getMaxSortIDSharedPreferences().edit().putLong(PREF_KEY_MAX_SORT_ID, j).apply();
    }

    public static long getMaxSortID() {
        return getMaxSortIDSharedPreferences().getLong(PREF_KEY_MAX_SORT_ID, 0L);
    }

    public static void setIsFirstTodoCloud(boolean z) {
        getMaxSortIDSharedPreferences().edit().putBoolean(PREF_KEY_IS_FIRST_TODO_CLOUD_INIT, z).apply();
    }

    public static boolean getIsFirstTodoCloud() {
        return getMaxSortIDSharedPreferences().getBoolean(PREF_KEY_IS_FIRST_TODO_CLOUD_INIT, true);
    }
}
