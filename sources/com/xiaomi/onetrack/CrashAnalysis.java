package com.xiaomi.onetrack;

import android.content.Context;
import android.text.TextUtils;
import com.miui.aiengine.common.result.SSEConst;
import com.xiaomi.onetrack.api.c;
import com.xiaomi.onetrack.d.d;
import com.xiaomi.onetrack.f.a;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.ae;
import com.xiaomi.onetrack.util.b;
import com.xiaomi.onetrack.util.m;
import com.xiaomi.onetrack.util.r;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CrashAnalysis {
    public static final String ANR_CRASH = "anr";
    public static final String JAVA_CRASH = "java";
    public static final String NATIVE_CRASH = "native";
    private static final String a = "CrashAnalysis";
    private static final String b = "backtrace feature id:\n\t";
    private static final String c = "error reason:\n\t";
    private static final String d = "Crash time: '";
    private static final String e = ".xcrash";
    private static final int f = 604800000;
    private static final int g = 358400;
    private static final int h = 10;
    private static final int i = 20;
    private static final String j = "@[0-9a-fA-F]{1,10}";
    private static final String k = "\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}";
    private static final String l = "0x[0-9a-fA-F]{1,10}";
    private static final String m = "\\d+[B,KB,MB]*";
    private static final String n = "((java:)|(length=)|(index=)|(Index:)|(Size:))\\d+";
    private static final int o = 20;
    private static final boolean p = false;
    private static volatile CrashAnalysis s;
    private final FileProcessor[] q;
    private final c r;
    private boolean t;

    public static CrashAnalysis getInstance(Context context, c cVar) {
        if (s == null) {
            synchronized (CrashAnalysis.class) {
                if (s == null) {
                    s = new CrashAnalysis(context, cVar);
                }
            }
        }
        return s;
    }

    private CrashAnalysis(Context context, c cVar) {
        try {
            Object objNewInstance = Class.forName("xcrash.XCrash$InitParameters").getConstructor(new Class[0]).newInstance(new Object[0]);
            a(objNewInstance, "setNativeDumpAllThreads", false);
            a(objNewInstance, "setLogDir", getCrashPath());
            a(objNewInstance, "setNativeDumpMap", false);
            a(objNewInstance, "setNativeDumpFds", false);
            a(objNewInstance, "setJavaDumpAllThreads", false);
            if (DeviceUtil.g()) {
                r.a(a, "isMiTv:true");
                a(objNewInstance, "setAnrCheckProcessState", false);
            } else {
                r.a(a, "isMiTv:false");
            }
            Class.forName("xcrash.XCrash").getDeclaredMethod("init", Context.class, objNewInstance.getClass()).invoke(null, context.getApplicationContext(), objNewInstance);
            r.a(a, "XCrash init success");
            this.t = true;
        } catch (Throwable th) {
            r.c(a, "XCrash init failed: ", th);
        }
        this.r = cVar;
        this.q = new FileProcessor[]{new FileProcessor("java"), new FileProcessor(ANR_CRASH), new FileProcessor("native")};
        a();
    }

    public boolean isSupport() {
        return this.t;
    }

    static void a(Context context) {
        try {
            a.a(context.getApplicationContext());
            Class.forName("xcrash.XCrash").getDeclaredMethod("initHooker", Context.class, String.class).invoke(null, context.getApplicationContext(), getCrashPath());
            r.a(a, "registerHook succeeded");
        } catch (Throwable th) {
            r.c(a, "registerHook failed: ", th);
        }
    }

    private void a() {
        try {
            if (!d()) {
                r.a(a, "no crash file found");
            } else {
                e();
            }
        } catch (Throwable th) {
            r.b(a, "processCrash error: ", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(String str, String str2) {
        int iIndexOf;
        int iIndexOf2;
        if (!TextUtils.isEmpty(str)) {
            try {
                if (str2.equals(ANR_CRASH)) {
                    int iIndexOf3 = str.indexOf(" tid=1 ");
                    if (iIndexOf3 != -1 && (iIndexOf = str.indexOf("\n  at ", iIndexOf3)) != -1 && (iIndexOf2 = str.indexOf(10, iIndexOf + 6)) != -1) {
                        return str.substring(iIndexOf + 2, iIndexOf2);
                    }
                    return "uncategoried";
                }
                int iIndexOf4 = str.indexOf(c);
                if (iIndexOf4 != -1) {
                    int length = iIndexOf4 + c.length();
                    int iIndexOf5 = str.indexOf(SSEConst.MSG_SPLIT, length);
                    if (iIndexOf5 != -1) {
                        return str.substring(length, iIndexOf5);
                    }
                    return "uncategoried";
                }
                if (str2.equals("native")) {
                    String strB = b.b(str);
                    return !TextUtils.isEmpty(strB) ? strB : "uncategoried";
                }
                return "uncategoried";
            } catch (Exception e2) {
                r.b(a, "getErrorReasonString error: " + e2.toString());
                return "uncategoried";
            }
        }
        return "uncategoried";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(String str, String str2) {
        int length;
        int iIndexOf;
        int iIndexOf2;
        if (!TextUtils.isEmpty(str)) {
            try {
                if (str2.equals(ANR_CRASH)) {
                    int iIndexOf3 = str.indexOf(" tid=1 ");
                    if (iIndexOf3 != -1 && (iIndexOf2 = str.indexOf(SSEConst.MSG_SPLIT, iIndexOf3)) != -1) {
                        return calculateJavaDigest(str.substring(iIndexOf3, iIndexOf2));
                    }
                    return "";
                }
                int iIndexOf4 = str.indexOf(b);
                if (iIndexOf4 != -1 && (iIndexOf = str.indexOf(SSEConst.MSG_SPLIT, (length = iIndexOf4 + b.length()))) != -1) {
                    return str.substring(length, iIndexOf);
                }
                return "";
            } catch (Exception e2) {
                r.b(a, "calculateFeatureId error: " + e2.toString());
                return "";
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, Object> c(String str) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str.substring(1));
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.opt(next));
                }
            } catch (Exception e2) {
                r.b(a, "getDynamicCommonProperty error：", e2);
            }
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long d(String str) {
        int length;
        int iIndexOf;
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            int iIndexOf2 = str.indexOf(d);
            if (iIndexOf2 == -1 || (iIndexOf = str.indexOf("'\n", (length = iIndexOf2 + d.length()))) == -1) {
                return 0L;
            }
            return b.a(str.substring(length, iIndexOf));
        } catch (Exception e2) {
            r.b(a, "getCrashTimeStamp error: " + e2.toString());
            return 0L;
        }
    }

    private void a(Object obj, String str, Object obj2) throws Exception {
        obj.getClass().getDeclaredMethod(str, obj2.getClass() == Boolean.class ? Boolean.TYPE : obj2.getClass()).invoke(obj, obj2);
    }

    public static String getCrashPath() {
        return m.a();
    }

    private long b() {
        long jC = ac.c();
        if (jC == 0) {
            r.a(a, "no ticket data found, return max count");
            return 10L;
        }
        long jB = ae.b();
        if (jC / 100 != jB) {
            r.a(a, "no today's ticket, return max count");
            return 10L;
        }
        long j2 = jC - (jB * 100);
        r.a(a, "today's remain ticket is " + j2);
        return j2;
    }

    private void a(long j2) {
        ac.d((ae.b() * 100) + j2);
    }

    private List<File> c() {
        File[] fileArrListFiles = new File(getCrashPath()).listFiles();
        if (fileArrListFiles == null) {
            r.a(a, "this path does not denote a directory, or if an I/O error occurs.");
            return null;
        }
        List<File> listAsList = Arrays.asList(fileArrListFiles);
        Collections.sort(listAsList, new Comparator<File>() { // from class: com.xiaomi.onetrack.CrashAnalysis.1
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                return (int) (file.lastModified() - file2.lastModified());
            }
        });
        int size = listAsList.size();
        if (size <= 20) {
            return listAsList;
        }
        int i2 = size - 20;
        for (int i3 = 0; i3 < i2; i3++) {
            m.a(listAsList.get(i3));
        }
        return listAsList.subList(i2, size);
    }

    private boolean d() {
        boolean z;
        Iterator<File> it;
        List<File> listC = c();
        long jB = b();
        if (listC == null || listC.size() <= 0) {
            z = false;
        } else {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jB2 = ac.b();
            long j2 = 604800000;
            if (jB2 > jCurrentTimeMillis) {
                jB2 = jCurrentTimeMillis - 604800000;
            }
            Iterator<File> it2 = listC.iterator();
            long j3 = 0;
            long j4 = 0;
            boolean z2 = false;
            while (it2.hasNext()) {
                File next = it2.next();
                long jLastModified = next.lastModified();
                if (jLastModified < jCurrentTimeMillis - j2 || jLastModified > jCurrentTimeMillis) {
                    it = it2;
                    r.a(a, "remove obsolete crash files: " + next.getName());
                    m.a(next);
                } else if (jLastModified <= jB2) {
                    r.a(a, "found already reported crash file, ignore");
                    it = it2;
                } else if (jB > j3) {
                    FileProcessor[] fileProcessorArr = this.q;
                    int length = fileProcessorArr.length;
                    int i2 = 0;
                    while (true) {
                        it = it2;
                        if (i2 < length) {
                            if (fileProcessorArr[i2].a(next)) {
                                r.a(a, "find crash file:" + next.getName());
                                jB--;
                                z2 = true;
                                if (j4 < jLastModified) {
                                    j4 = jLastModified;
                                }
                            }
                            i2++;
                            it2 = it;
                        }
                    }
                } else {
                    it = it2;
                }
                it2 = it;
                j3 = j3;
                j2 = 604800000;
            }
            if (j4 > j3) {
                ac.c(j4);
            }
            z = z2;
        }
        if (z) {
            a(jB);
        }
        return z;
    }

    private void e() throws Throwable {
        for (FileProcessor fileProcessor : this.q) {
            fileProcessor.a();
        }
    }

    private class FileProcessor {
        final List<File> a = new ArrayList();
        final String b;
        final String c;

        FileProcessor(String str) {
            this.c = str;
            this.b = str + CrashAnalysis.e;
        }

        boolean a(File file) {
            if (!file.getName().contains(this.b)) {
                return false;
            }
            this.a.add(file);
            return true;
        }

        private String a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            String[] strArrSplit = str.split("__");
            if (strArrSplit.length != 2) {
                return null;
            }
            String[] strArrSplit2 = strArrSplit[0].split("_");
            if (strArrSplit2.length == 3) {
                return strArrSplit2[2];
            }
            return null;
        }

        void a() throws Throwable {
            for (int i = 0; i < this.a.size(); i++) {
                String absolutePath = this.a.get(i).getAbsoluteFile().getAbsolutePath();
                String strA = a(absolutePath);
                String[] strArrA = m.a(absolutePath, CrashAnalysis.g);
                r.a(CrashAnalysis.a, "crash content size: " + strArrA[1].length());
                if (!TextUtils.isEmpty(strArrA[1]) && CrashAnalysis.this.r != null) {
                    String strD = CrashAnalysis.d(strArrA[1], this.c);
                    String strC = CrashAnalysis.c(strArrA[1], this.c);
                    long jD = CrashAnalysis.d(strArrA[1]);
                    Map<String, Object> mapC = CrashAnalysis.c(strArrA[0]);
                    r.a(CrashAnalysis.a, "fileName: " + absolutePath);
                    r.a(CrashAnalysis.a, "feature id: " + strD);
                    r.a(CrashAnalysis.a, "error: " + strC);
                    r.a(CrashAnalysis.a, "crashTimeStamp: " + jD);
                    r.a(CrashAnalysis.a, "dynamicCommonProperty: " + (mapC == null ? "" : mapC.toString()));
                    CrashAnalysis.this.r.a(strArrA[1], strC, this.c, strA, strD, jD, mapC);
                    m.a(new File(absolutePath));
                    r.a(CrashAnalysis.a, "remove reported crash file");
                }
            }
        }
    }

    public static String calculateJavaDigest(String str) {
        String[] strArrSplit = str.replaceAll("\\t", "").split("\\n");
        StringBuilder sb = new StringBuilder();
        int iMin = Math.min(strArrSplit.length, 20);
        for (int i2 = 0; i2 < iMin; i2++) {
            strArrSplit[i2] = strArrSplit[i2].replaceAll(n, "$1XX").replaceAll("\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}|@[0-9a-fA-F]{1,10}|0x[0-9a-fA-F]{1,10}", "XX").replaceAll(m, "");
        }
        for (int i3 = 0; i3 < iMin && (!strArrSplit[i3].contains("...") || !strArrSplit[i3].contains("more")); i3++) {
            sb.append(strArrSplit[i3]);
            sb.append('\n');
        }
        return d.h(sb.toString());
    }
}
