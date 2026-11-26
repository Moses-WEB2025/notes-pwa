package com.miui.creation.logger.inner;

import android.util.Log;
import java.util.Arrays;
import java.util.Locale;
import okhttp3.internal.url._UrlKt;

/* JADX INFO: loaded from: classes2.dex */
public abstract class LoggerBase {
    protected static String logFormat(String str, Object... objArr) {
        if (objArr == null) {
            return str;
        }
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj instanceof String[]) {
                objArr[i] = prettyArray((String[]) obj);
            }
        }
        try {
            return String.format(Locale.US, str, objArr);
        } catch (Exception e) {
            Log.d("XCreation", "log error: the format is \"" + str + "\", the args is: " + Arrays.toString(objArr));
            e.printStackTrace();
            return "";
        }
    }

    private static String prettyArray(String[] strArr) {
        if (strArr.length == 0) {
            return _UrlKt.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder("[");
        int length = strArr.length - 1;
        for (int i = 0; i < length; i++) {
            sb.append(strArr[i]);
            sb.append(", ");
        }
        sb.append(strArr[length]);
        sb.append("]");
        return sb.toString();
    }
}
