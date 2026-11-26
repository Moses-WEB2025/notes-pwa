package com.xiaomi.onetrack.util;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.system.Os;
import android.text.TextUtils;
import com.miui.aiengine.common.result.SSEConst;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class b {
    static final String a = "3.0.2";
    static final String b = "OneTrack 3.0.2";
    static final String c = "CrashUtil";
    public static final String d = "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***";
    public static final String e = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---";
    public static final String f = "+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++";
    static final String g = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String h = "java";
    static final String i = "tombstone";
    static final String j = ".java.xcrash";
    public static final String k = "dynamic common property:";
    public static final String l = "dynamic common property:\n\t";
    private static final String m = "%21s %8s\n";
    private static final String n = "%21s %8s %21s %8s\n";
    private static final String[] o = {"/data/local/su", "/data/local/bin/su", "/data/local/xbin/su", "/system/xbin/su", "/system/bin/su", "/system/bin/.ext/su", "/system/bin/failsafe/su", "/system/sd/xbin/su", "/system/usr/we-need-root/su", "/sbin/su", "/su/bin/su"};

    private b() {
    }

    public static String a(Context context, int i2) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/" + i2 + "/cmdline"));
            try {
                String line = bufferedReader.readLine();
                if (!TextUtils.isEmpty(line)) {
                    String strTrim = line.trim();
                    if (!TextUtils.isEmpty(strTrim)) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused) {
                        }
                        return strTrim;
                    }
                }
            } catch (Exception unused2) {
                if (bufferedReader != null) {
                }
                return null;
            } catch (Throwable th) {
                th = th;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (Exception unused3) {
                    }
                }
                throw th;
            }
        } catch (Exception unused4) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedReader.close();
        } catch (Exception unused5) {
        }
        return null;
    }

    static boolean a() {
        try {
            for (String str : o) {
                if (new File(str).exists()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    static String b() {
        return TextUtils.join(",", Build.SUPPORTED_ABIS);
    }

    public static String a(Context context) {
        String str;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            str = null;
        }
        return TextUtils.isEmpty(str) ? "unknown" : str;
    }

    static String c() {
        StringBuilder sb = new StringBuilder(" Process Summary (From: android.os.Debug.MemoryInfo)\n");
        sb.append(String.format(Locale.US, m, "", "Pss(KB)"));
        sb.append(String.format(Locale.US, m, "", "------"));
        try {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            sb.append(String.format(Locale.US, m, "Java Heap:", memoryInfo.getMemoryStat("summary.java-heap")));
            sb.append(String.format(Locale.US, m, "Native Heap:", memoryInfo.getMemoryStat("summary.native-heap")));
            sb.append(String.format(Locale.US, m, "Code:", memoryInfo.getMemoryStat("summary.code")));
            sb.append(String.format(Locale.US, m, "Stack:", memoryInfo.getMemoryStat("summary.stack")));
            sb.append(String.format(Locale.US, m, "Graphics:", memoryInfo.getMemoryStat("summary.graphics")));
            sb.append(String.format(Locale.US, m, "Private Other:", memoryInfo.getMemoryStat("summary.private-other")));
            sb.append(String.format(Locale.US, m, "System:", memoryInfo.getMemoryStat("summary.system")));
            sb.append(String.format(Locale.US, n, "TOTAL:", memoryInfo.getMemoryStat("summary.total-pss"), "TOTAL SWAP:", memoryInfo.getMemoryStat("summary.total-swap")));
        } catch (Exception e2) {
            r.b(c, "CrashUtil getProcessMemoryInfo failed", e2);
        }
        return sb.toString();
    }

    private static String c(String str) {
        return a(str, 0);
    }

    private static String a(String str, int i2) throws Throwable {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                    int i3 = 0;
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            String strTrim = line.trim();
                            if (strTrim.length() > 0) {
                                i3++;
                                if (i2 == 0 || i3 <= i2) {
                                    sb.append("  ").append(strTrim).append(StringUtils.LF);
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            r.c(c, "CrashUtil getInfo(" + str + ") failed", e);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return sb.toString();
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    if (i2 > 0 && i3 > i2) {
                        sb.append("  ......\n").append("  (number of records: ").append(i3).append(")\n");
                    }
                    bufferedReader2.close();
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception unused2) {
        }
        return sb.toString();
    }

    public static String a(Date date, Date date2, String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(g, Locale.US);
        return "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\nTombstone maker: 'OneTrack 3.0.2'\nCrash type: '" + str + "'\nStart time: '" + simpleDateFormat.format(date) + "'\nCrash time: '" + simpleDateFormat.format(date2) + "'\nApp ID: '" + str2 + "'\nApp version: '" + str3 + "'\nRooted: '" + (a() ? "Yes" : "No") + "'\nAPI level: '" + Build.VERSION.SDK_INT + "'\nOS version: '" + Build.VERSION.RELEASE + "'\nABI list: '" + b() + "'\nManufacturer: '" + Build.MANUFACTURER + "'\nBrand: '" + Build.BRAND + "'\nModel: '" + Build.MODEL + "'\nBuild fingerprint: '" + Build.FINGERPRINT + "'\n";
    }

    public static String d() {
        return "memory info:\n System Summary (From: /proc/meminfo)\n" + c("/proc/meminfo") + "-\n Process Status (From: /proc/PID/status)\n" + c("/proc/self/status") + "-\n Process Limits (From: /proc/PID/limits)\n" + c("/proc/self/limits") + "-\n" + c() + StringUtils.LF;
    }

    public static String e() {
        return "network info:\nNot supported on Android Q (API level 29) and later.\n\n";
    }

    public static String f() {
        String str;
        StringBuilder sb = new StringBuilder("open files:\n");
        try {
            File[] fileArrListFiles = new File("/proc/self/fd").listFiles(new c());
            if (fileArrListFiles != null) {
                int i2 = 0;
                for (File file : fileArrListFiles) {
                    try {
                        str = Os.readlink(file.getAbsolutePath());
                    } catch (Exception unused) {
                        str = null;
                    }
                    sb.append("    fd ").append(file.getName()).append(": ").append(TextUtils.isEmpty(str) ? "???" : str.trim()).append('\n');
                    i2++;
                    if (i2 > 1024) {
                        break;
                    }
                }
                if (fileArrListFiles.length > 1024) {
                    sb.append("    ......\n");
                }
                sb.append("    (number of FDs: ").append(fileArrListFiles.length).append(")\n");
            }
        } catch (Exception unused2) {
        }
        sb.append('\n');
        return sb.toString();
    }

    public static String a(int i2, int i3, int i4) throws Throwable {
        int iMyPid = Process.myPid();
        StringBuilder sb = new StringBuilder();
        sb.append("logcat:\n");
        if (i2 > 0) {
            a(iMyPid, sb, "main", i2, 'D');
        }
        if (i3 > 0) {
            a(iMyPid, sb, "system", i3, 'W');
        }
        if (i4 > 0) {
            a(iMyPid, sb, "events", i3, 'I');
        }
        sb.append(StringUtils.LF);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r6v7 */
    private static void a(int i2, StringBuilder sb, String str, int i3, char c2) throws Throwable {
        BufferedReader bufferedReader;
        Exception e2;
        String string = Integer.toString(i2);
        String str2 = StringUtils.SPACE + string + StringUtils.SPACE;
        ArrayList arrayList = new ArrayList();
        arrayList.add("/system/bin/logcat");
        arrayList.add("-b");
        arrayList.add(str);
        arrayList.add("-d");
        arrayList.add("-v");
        arrayList.add("threadtime");
        arrayList.add("-t");
        arrayList.add(Integer.toString(i3));
        arrayList.add("--pid");
        arrayList.add(string);
        arrayList.add("*:" + c2);
        Object[] array = arrayList.toArray();
        ?? Append = sb.append("--------- tail end of log ");
        Append.append(str);
        sb.append(" (").append(TextUtils.join(StringUtils.SPACE, array)).append(")\n");
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[0]).command(arrayList).start().getInputStream()));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                sb.append(line).append(StringUtils.LF);
                            } else {
                                bufferedReader.close();
                                return;
                            }
                        } catch (Exception e3) {
                            e2 = e3;
                            r.b(c, "CrashUtil run logcat command failed", e2);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                return;
                            }
                            return;
                        }
                    }
                } catch (Exception e4) {
                    bufferedReader = null;
                    e2 = e4;
                } catch (Throwable th) {
                    Append = 0;
                    th = th;
                    if (Append != 0) {
                        try {
                            Append.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static long a(String str) {
        try {
            return new SimpleDateFormat(g, Locale.US).parse(str).getTime();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public static String b(String str) {
        int iIndexOf;
        String[] strArrSplit;
        try {
            int iIndexOf2 = str.indexOf("\nbacktrace:\n");
            if (iIndexOf2 != -1 && (iIndexOf = str.indexOf(SSEConst.MSG_SPLIT, iIndexOf2 + 12)) != -1) {
                String strSubstring = str.substring(iIndexOf2, iIndexOf);
                if (!TextUtils.isEmpty(strSubstring.trim()) && (strArrSplit = strSubstring.split(StringUtils.LF)) != null && strArrSplit.length > 0) {
                    for (String str2 : strArrSplit) {
                        int iIndexOf3 = str2.indexOf("(");
                        if (iIndexOf3 != -1) {
                            String strSubstring2 = str2.substring(iIndexOf3);
                            r.a(c, "getErrorReason->errorReason: " + strSubstring2);
                            return strSubstring2;
                        }
                    }
                    return "";
                }
                return "";
            }
            return "";
        } catch (Exception e2) {
            r.b(c, "getErrorReason error, e: ", e2);
            return "";
        }
    }
}
