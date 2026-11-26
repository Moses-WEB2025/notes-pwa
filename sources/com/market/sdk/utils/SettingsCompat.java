package com.market.sdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes2.dex */
public class SettingsCompat {
    public static final String SYS_STORAGE_THRESHOLD_MAX_BYTES = "sys_storage_threshold_max_bytes";
    private static final String TAG = "SettingsCompat";

    public static class Global {
        public static int getInt(Context context, String str, int i) {
            try {
                return Settings.Global.getInt(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return i;
            }
        }

        public static long getLong(Context context, String str, long j) {
            try {
                return Settings.Global.getLong(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return j;
            }
        }

        public static String getString(Context context, String str, String str2) {
            try {
                return Settings.Global.getString(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return str2;
            }
        }

        public static boolean getBoolean(Context context, String str) {
            return getInt(context, str, 0) != 0;
        }
    }

    public static class System {
        public static final int DEFAULT_DISCOVER_AUTO_UPDATE = 1;
        public static final String KEY_DISCOVER_AUTO_UPDATE = "com.xiaomi.discover.auto_update_enabled";
        public static final String KEY_DISCOVER_METERED_UPDATE_ANSWERED = "com.xiaomi.discover.metered_update_answered";
        public static final String KEY_DISCOVER_METERED_UPDATE_CONFIRM_NEEDED_BY_REGION = "com.xiaomi.discover.metered_update_confirm_needed_by_region";
        public static final int VALUE_DISCOVER_AUTO_UPDATE_4G = 2;
        public static final int VALUE_DISCOVER_AUTO_UPDATE_DISABLED = 0;
        public static final int VALUE_DISCOVER_AUTO_UPDATE_WIFI = 1;

        public static int getInt(Context context, String str, int i) {
            try {
                return Settings.System.getInt(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return i;
            }
        }

        public static String getString(Context context, String str, String str2) {
            try {
                return Settings.System.getString(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return str2;
            }
        }

        public static boolean getBoolean(Context context, String str) {
            return getInt(context, str, 0) != 0;
        }

        public static boolean putInt(Context context, String str, int i) {
            try {
                return Settings.System.putInt(context.getContentResolver(), str, i);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return false;
            }
        }
    }

    public static class Secure {
        public static int getInt(Context context, String str, int i) {
            try {
                return Settings.Secure.getInt(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return i;
            }
        }

        public static int getIntForUser(Context context, String str, int i, int i2) {
            try {
                Integer num = (Integer) ReflectUtils.invokeObject(Settings.Secure.class, Settings.Secure.class, "getIntForUser", ReflectUtils.getMethodSignature(Integer.TYPE, ContentResolver.class, String.class, Integer.TYPE, Integer.TYPE), context.getContentResolver(), str, Integer.valueOf(i), Integer.valueOf(i2));
                return num != null ? num.intValue() : i;
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return i;
            }
        }

        public static String getString(Context context, String str, String str2) {
            try {
                return Settings.Secure.getString(context.getContentResolver(), str);
            } catch (Exception e) {
                Log.w(SettingsCompat.TAG, e.toString());
                return str2;
            }
        }

        public static boolean getBoolean(Context context, String str) {
            return getInt(context, str, 0) != 0;
        }
    }
}
