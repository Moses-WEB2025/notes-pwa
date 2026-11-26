package ho;

import android.app.Application;
import android.util.Log;
import java.util.Arrays;
import java.util.IllegalFormatException;

/* JADX INFO: loaded from: classes4.dex */
public final class j {
    public static String a(String str) {
        String processName = Application.getProcessName();
        return "ho_" + (processName != null ? processName.substring(processName.lastIndexOf(".") + 1) + "_" : "") + str;
    }

    public static void a(String str, Object... objArr) {
        try {
            Log.d(a("HH"), String.format(str, objArr));
        } catch (IllegalFormatException unused) {
            Log.d(a("HH"), str + Arrays.toString(objArr));
        }
    }

    public static void a(String str, String str2, Throwable th) {
        Log.e(a(str), str2, th);
    }

    public static void a(String str, String str2, Object... objArr) {
        try {
            Log.i(a(str), String.format(str2, objArr));
        } catch (IllegalFormatException unused) {
            Log.i(a(str), str2 + Arrays.toString(objArr));
        }
    }

    public static void a(String str, String str2) {
        Log.w(a(str), str2 + '\n' + Log.getStackTraceString(null));
    }
}
