package com.xiaomi.verificationsdk.internal;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes4.dex */
public class SharedPreferencesUtil {
    private final SharedPreferences mSharedPreferences;

    public SharedPreferencesUtil(Context context, String str) {
        this.mSharedPreferences = context.getSharedPreferences(str, 0);
    }

    public String getString(String str) {
        return this.mSharedPreferences.getString(str, null);
    }

    public long getLong(String str, long j) {
        return this.mSharedPreferences.getLong(str, j);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.mSharedPreferences.getBoolean(str, z);
    }

    public int getInt(String str, int i) {
        return this.mSharedPreferences.getInt(str, i);
    }

    public void saveString(String str, String str2) {
        this.mSharedPreferences.edit().putString(str, str2).apply();
    }

    public void saveLong(String str, long j) {
        this.mSharedPreferences.edit().putLong(str, j).apply();
    }

    public void saveBoolean(String str, boolean z) {
        this.mSharedPreferences.edit().putBoolean(str, z).apply();
    }

    public void saveInt(String str, int i) {
        this.mSharedPreferences.edit().putInt(str, i).apply();
    }

    public void remove(String str) {
        this.mSharedPreferences.edit().remove(str).apply();
    }

    public void clear() {
        this.mSharedPreferences.edit().clear().apply();
    }
}
