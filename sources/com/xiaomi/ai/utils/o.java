package com.xiaomi.ai.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/* JADX INFO: loaded from: classes4.dex */
public class o {
    private static final String b = "com.xiaomi.ai.utils.o";
    private static volatile a c;
    public static volatile a a = new p();
    private static volatile int d = 4;

    public interface a {
        void a(int i, String str, String str2);
    }

    public static a a() {
        return c;
    }

    private static String a(Object obj) {
        if (obj == null) {
            return "NULL";
        }
        return obj instanceof Throwable ? a((Throwable) obj) : obj.toString();
    }

    private static String a(Throwable th) {
        String strB;
        try {
            strB = b(th);
        } catch (IOException e) {
            android.util.Log.e("ContentValues", "IOException: ", e);
            strB = null;
        }
        return strB;
    }

    public static void a(int i) {
        d = i;
    }

    public static void a(int i, String str, Object... objArr) {
        a aVar = c;
        if (aVar == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(a(obj));
        }
        aVar.a(i, str, sb.toString());
    }

    public static void a(a aVar) {
        c = aVar;
    }

    public static void a(String str, Object... objArr) {
        a(d, str, objArr);
    }

    public static a b() {
        return a;
    }

    public static String b(String str, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + str + ": ");
        int length = objArr.length;
        boolean z = true;
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(a(obj));
            i++;
            z = false;
        }
        sb.append("]");
        return sb.toString();
    }

    private static String b(Throwable th) throws IOException {
        if (th == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            th.printStackTrace(new PrintStream(byteArrayOutputStream));
            return byteArrayOutputStream.toString();
        } finally {
            byteArrayOutputStream.close();
        }
    }

    private static String c() {
        boolean z = false;
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().equals(b)) {
                z = true;
            } else if (z) {
                return String.format("%s::%s@%s:%s, thread:%s", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getFileName(), Integer.valueOf(stackTraceElement.getLineNumber()), Long.valueOf(Thread.currentThread().getId()));
            }
        }
        return "";
    }
}
