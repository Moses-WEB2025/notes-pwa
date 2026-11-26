package androidx.appsearch.utils;

import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public class BootCountUtil {
    private BootCountUtil() {
    }

    public static int getCurrentBootCount(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "boot_count", -1);
    }
}
