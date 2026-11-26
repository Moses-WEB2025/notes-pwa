package com.xiaomi.mirror;

import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes4.dex */
public class SystemSettingsUtils {
    public static final String KEY_MIRROR_SEAT = "pref_key_mirror_seat";
    public static final String KEY_MIRROR_SWITCH = "pref_key_mirror_switch";

    public static int getInt(Context context, String str, int i) {
        return Settings.Secure.getInt(context.getContentResolver(), str, i);
    }

    public static String getString(Context context, String str) {
        return Settings.Secure.getString(context.getContentResolver(), str);
    }

    public static void putInt(Context context, String str, int i) {
        Settings.Secure.putInt(context.getContentResolver(), str, i);
    }

    public static void putString(Context context, String str, String str2) {
        Settings.Secure.putString(context.getContentResolver(), str, str2);
    }
}
