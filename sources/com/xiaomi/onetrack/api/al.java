package com.xiaomi.onetrack.api;

import android.os.Process;
import android.util.Log;
import com.miui.aiengine.common.result.SSEConst;
import com.xiaomi.onetrack.CrashAnalysis;
import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class al implements Thread.UncaughtExceptionHandler {
    private static final String a = "OneTrackExceptionHandler";
    private static final String c = "tombstone";
    private static final String d = ".java.xcrash";
    private static final String e = "backtrace feature id:\n\t";
    private static final String f = "error reason:\n\t";
    private static final long h = 2;
    private Thread.UncaughtExceptionHandler b;
    private c i;
    private final Date g = new Date();
    private int j = 50;
    private int k = 50;
    private int l = 200;
    private boolean m = true;
    private boolean n = true;

    public al(c cVar) {
        this.i = cVar;
    }

    public void a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler instanceof al) {
            return;
        }
        this.b = defaultUncaughtExceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Log.d(com.xiaomi.onetrack.util.r.a(a), "crash happened->stacktrace: " + th.getStackTrace());
        FutureTask futureTask = new FutureTask(new am(this, thread, th), null);
        com.xiaomi.onetrack.util.j.a(futureTask);
        try {
            futureTask.get(2L, TimeUnit.SECONDS);
        } catch (Exception e2) {
            Log.e(com.xiaomi.onetrack.util.r.a(a), "handleException error :" + e2.getMessage());
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: Removed unreachable split cross block B:92:0x0181 */
    /* JADX WARN: Code duplicated, block: B:31:0x00c9 A[Catch: all -> 0x015b, Exception -> 0x015f, TryCatch #11 {Exception -> 0x015f, all -> 0x015b, blocks: (B:29:0x008f, B:31:0x00c9, B:32:0x00d0, B:34:0x00d4, B:36:0x00d8, B:39:0x00eb, B:41:0x00ef, B:42:0x00fa, B:44:0x00fe, B:45:0x0109, B:47:0x0125, B:48:0x0132, B:50:0x0139, B:52:0x013f, B:38:0x00dc), top: B:94:0x008f }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00dc A[Catch: all -> 0x015b, Exception -> 0x015f, TryCatch #11 {Exception -> 0x015f, all -> 0x015b, blocks: (B:29:0x008f, B:31:0x00c9, B:32:0x00d0, B:34:0x00d4, B:36:0x00d8, B:39:0x00eb, B:41:0x00ef, B:42:0x00fa, B:44:0x00fe, B:45:0x0109, B:47:0x0125, B:48:0x0132, B:50:0x0139, B:52:0x013f, B:38:0x00dc), top: B:94:0x008f }] */
    /* JADX WARN: Code duplicated, block: B:41:0x00ef A[Catch: all -> 0x015b, Exception -> 0x015f, TryCatch #11 {Exception -> 0x015f, all -> 0x015b, blocks: (B:29:0x008f, B:31:0x00c9, B:32:0x00d0, B:34:0x00d4, B:36:0x00d8, B:39:0x00eb, B:41:0x00ef, B:42:0x00fa, B:44:0x00fe, B:45:0x0109, B:47:0x0125, B:48:0x0132, B:50:0x0139, B:52:0x013f, B:38:0x00dc), top: B:94:0x008f }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00fe A[Catch: all -> 0x015b, Exception -> 0x015f, TryCatch #11 {Exception -> 0x015f, all -> 0x015b, blocks: (B:29:0x008f, B:31:0x00c9, B:32:0x00d0, B:34:0x00d4, B:36:0x00d8, B:39:0x00eb, B:41:0x00ef, B:42:0x00fa, B:44:0x00fe, B:45:0x0109, B:47:0x0125, B:48:0x0132, B:50:0x0139, B:52:0x013f, B:38:0x00dc), top: B:94:0x008f }] */
    /* JADX WARN: Code duplicated, block: B:47:0x0125 A[Catch: all -> 0x015b, Exception -> 0x015f, TryCatch #11 {Exception -> 0x015f, all -> 0x015b, blocks: (B:29:0x008f, B:31:0x00c9, B:32:0x00d0, B:34:0x00d4, B:36:0x00d8, B:39:0x00eb, B:41:0x00ef, B:42:0x00fa, B:44:0x00fe, B:45:0x0109, B:47:0x0125, B:48:0x0132, B:50:0x0139, B:52:0x013f, B:38:0x00dc), top: B:94:0x008f }] */
    /* JADX WARN: Code duplicated, block: B:74:0x0181 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:88:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v2, types: [java.io.RandomAccessFile] */
    /* JADX WARN: Type inference failed for: r10v5 */
    public void a(Thread thread, Throwable th) throws Throwable {
        File file;
        Throwable th2;
        String string;
        String strA;
        Throwable th3;
        ?? r10;
        RandomAccessFile randomAccessFile;
        int i;
        JSONObject jSONObjectA;
        Date date = new Date();
        Object obj = null;
        RandomAccessFile randomAccessFile2 = null;
        obj = null;
        try {
            file = new File(String.format(Locale.US, "%s/%s_%020d_%s__%s%s", CrashAnalysis.getCrashPath(), c, Long.valueOf(this.g.getTime() * 1000), com.xiaomi.onetrack.util.b.a(com.xiaomi.onetrack.f.a.b()), com.xiaomi.onetrack.f.a.e(), d));
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception e2) {
                e = e2;
                com.xiaomi.onetrack.util.r.b(a, "JavaCrashHandler createLogFile failed", e);
            }
        } catch (Exception e3) {
            e = e3;
            file = null;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th2 = th;
            try {
                th2.printStackTrace(new PrintWriter(stringWriter));
                string = stringWriter.toString();
                try {
                    strA = a(date, thread, string);
                } catch (Exception e4) {
                    e = e4;
                    com.xiaomi.onetrack.util.r.b(a, "JavaCrashHandler getEmergency failed", e);
                    strA = null;
                }
            } catch (Exception e5) {
                e = e5;
                string = null;
                com.xiaomi.onetrack.util.r.b(a, "JavaCrashHandler getEmergency failed", e);
                strA = null;
                try {
                    if (file != null) {
                        try {
                            randomAccessFile = new RandomAccessFile(file, "rws");
                            try {
                                randomAccessFile.write((e + CrashAnalysis.calculateJavaDigest(string) + SSEConst.MSG_SPLIT).getBytes("UTF-8"));
                                randomAccessFile.write((f + th2.toString() + SSEConst.MSG_SPLIT).getBytes("UTF-8"));
                                if (strA != null) {
                                    randomAccessFile.write(strA.getBytes("UTF-8"));
                                }
                                i = this.l;
                                if (i <= 0) {
                                    randomAccessFile.write(com.xiaomi.onetrack.util.b.a(i, this.j, this.k).getBytes("UTF-8"));
                                } else {
                                    randomAccessFile.write(com.xiaomi.onetrack.util.b.a(i, this.j, this.k).getBytes("UTF-8"));
                                }
                                if (this.m) {
                                    randomAccessFile.write(com.xiaomi.onetrack.util.b.f().getBytes("UTF-8"));
                                }
                                if (this.n) {
                                    randomAccessFile.write(com.xiaomi.onetrack.util.b.e().getBytes("UTF-8"));
                                }
                                randomAccessFile.write(com.xiaomi.onetrack.util.b.d().getBytes("UTF-8"));
                                randomAccessFile.write("foreground:\nyes\n\n".getBytes("UTF-8"));
                                Map<String, Object> dynamicProperty = this.i.c() != null ? this.i.c().getDynamicProperty("onetrack_bug_report") : null;
                                jSONObjectA = com.xiaomi.onetrack.util.t.a(dynamicProperty, false);
                                if (jSONObjectA != null) {
                                    randomAccessFile.write((com.xiaomi.onetrack.util.b.l + jSONObjectA + SSEConst.MSG_SPLIT).getBytes("UTF-8"));
                                }
                                randomAccessFile.close();
                                obj = dynamicProperty;
                            } catch (Exception e6) {
                                e = e6;
                                randomAccessFile2 = randomAccessFile;
                                com.xiaomi.onetrack.util.r.b(a, "JavaCrashHandler write log file failed", e);
                                obj = randomAccessFile2;
                                if (randomAccessFile2 != null) {
                                    randomAccessFile2.close();
                                    obj = randomAccessFile2;
                                }
                            } catch (Throwable th4) {
                                th3 = th4;
                                r10 = randomAccessFile;
                                if (r10 == 0) {
                                    throw th3;
                                }
                                try {
                                    r10.close();
                                    throw th3;
                                } catch (Exception e7) {
                                    com.xiaomi.onetrack.util.r.b(a, "JavaCrashHandler close RandomAccessFile failed", e7);
                                    throw th3;
                                }
                            }
                        } catch (Exception e8) {
                            e = e8;
                        }
                    }
                } catch (Exception e9) {
                    com.xiaomi.onetrack.util.r.b(a, "JavaCrashHandler close RandomAccessFile failed", e9);
                }
            }
        } catch (Exception e10) {
            e = e10;
            th2 = th;
        }
        try {
            if (file != null) {
                randomAccessFile = new RandomAccessFile(file, "rws");
                randomAccessFile.write((e + CrashAnalysis.calculateJavaDigest(string) + SSEConst.MSG_SPLIT).getBytes("UTF-8"));
                randomAccessFile.write((f + th2.toString() + SSEConst.MSG_SPLIT).getBytes("UTF-8"));
                if (strA != null) {
                    randomAccessFile.write(strA.getBytes("UTF-8"));
                }
                i = this.l;
                if (i <= 0 || this.j > 0 || this.k > 0) {
                    randomAccessFile.write(com.xiaomi.onetrack.util.b.a(i, this.j, this.k).getBytes("UTF-8"));
                }
                if (this.m) {
                    randomAccessFile.write(com.xiaomi.onetrack.util.b.f().getBytes("UTF-8"));
                }
                if (this.n) {
                    randomAccessFile.write(com.xiaomi.onetrack.util.b.e().getBytes("UTF-8"));
                }
                randomAccessFile.write(com.xiaomi.onetrack.util.b.d().getBytes("UTF-8"));
                randomAccessFile.write("foreground:\nyes\n\n".getBytes("UTF-8"));
                if (this.i.c() != null) {
                }
                jSONObjectA = com.xiaomi.onetrack.util.t.a(dynamicProperty, false);
                if (jSONObjectA != null && jSONObjectA.length() != 0) {
                    randomAccessFile.write((com.xiaomi.onetrack.util.b.l + jSONObjectA + SSEConst.MSG_SPLIT).getBytes("UTF-8"));
                }
                randomAccessFile.close();
                obj = dynamicProperty;
            }
        } catch (Throwable th5) {
            th3 = th5;
            r10 = obj;
        }
    }

    private String a(Date date, Thread thread, String str) {
        return com.xiaomi.onetrack.util.b.a(this.g, date, "java", com.xiaomi.onetrack.f.a.e(), com.xiaomi.onetrack.util.b.a(com.xiaomi.onetrack.f.a.b())) + "pid: " + Process.myPid() + ", tid: " + Process.myTid() + ", name: " + thread.getName() + "  >>> " + com.xiaomi.onetrack.f.a.e() + " <<<\n\njava stacktrace:\n" + str + StringUtils.LF;
    }
}
