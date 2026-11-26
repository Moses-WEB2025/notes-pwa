package xc.engine.utils;

import android.content.Context;

/* JADX INFO: loaded from: classes6.dex */
public class PreferenceUtils {
    public static void setV(Context context, int i) {
        context.getSharedPreferences("post_spname", 0).edit().putInt("aaa_key", i).apply();
    }

    public static int getV(Context context) {
        return context.getSharedPreferences("post_spname", 0).getInt("aaa_key", -1);
    }
}
