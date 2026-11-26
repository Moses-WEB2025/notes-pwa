package com.miui.notes.excerpt;

import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class LongLogUtil {
    private static int LOG_MAXLENGTH = 2000;

    public static void e(String str, String str2) {
        int length = str2.length();
        int i = LOG_MAXLENGTH;
        int i2 = 0;
        int i3 = 0;
        while (i2 < 100) {
            if (length > i) {
                Logger.INSTANCE.e(str + i2, str2.substring(i3, i));
                i2++;
                i3 = i;
                i = LOG_MAXLENGTH + i;
            } else {
                Logger.INSTANCE.e(str, str2.substring(i3, length));
                return;
            }
        }
    }
}
