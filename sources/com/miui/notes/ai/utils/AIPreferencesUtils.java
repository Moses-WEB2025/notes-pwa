package com.miui.notes.ai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AIPreferencesUtils {
    private static final String DAY_AI_USE = "ai_use";
    private final SharedPreferences sharedPreferences;

    public AIPreferencesUtils(Context context) {
        this.sharedPreferences = context.getSharedPreferences(DAY_AI_USE, 0);
    }

    public void addValue(String str, int i) {
        int i2 = this.sharedPreferences.getInt(str, 0) + i;
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putInt(str, i2);
        editorEdit.apply();
    }

    public int getValue(String str) {
        return this.sharedPreferences.getInt(str, 0);
    }

    public Map<String, ?> getAll() {
        return this.sharedPreferences.getAll();
    }

    public void clearAll() {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.clear();
        editorEdit.apply();
    }
}
