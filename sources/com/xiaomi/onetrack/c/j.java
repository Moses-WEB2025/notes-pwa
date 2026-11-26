package com.xiaomi.onetrack.c;

import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.ak;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class j {
    public static final long a = 0;
    public static final String b = "eventName";
    public static final String c = "data";
    private static final String d = "NetworkAccessManager";
    private static final String e = "networkAccess";
    private static String f = "onetrack_netaccess_%s";
    private static final long h = 10485760;
    private static SimpleDateFormat g = new SimpleDateFormat("yyyyMMdd");
    private static boolean i = false;
    private static volatile boolean j = true;
    private static volatile boolean k = false;

    public static boolean a() {
        return i;
    }

    public static void a(boolean z) {
        i = z;
    }

    public static boolean b() {
        return !new File(com.xiaomi.onetrack.f.a.a().getFilesDir(), ".ot_net_disallowed").exists();
    }

    public static void b(boolean z) {
        File file = new File(com.xiaomi.onetrack.f.a.a().getFilesDir(), ".ot_net_allowed");
        File file2 = new File(com.xiaomi.onetrack.f.a.a().getFilesDir(), ".ot_net_disallowed");
        try {
            if (z) {
                file.createNewFile();
                if (file2.exists()) {
                    file2.delete();
                    return;
                }
                return;
            }
            file2.createNewFile();
            if (file.exists()) {
                file.delete();
            }
        } catch (IOException e2) {
            com.xiaomi.onetrack.util.r.b(d, "setNetworkAccessStateEnabled: " + z + "failed ", e2);
        }
    }

    private static String e() {
        return com.xiaomi.onetrack.f.a.a().getFilesDir().getAbsolutePath() + File.separator + e;
    }

    public static void a(String str, String str2) {
        com.xiaomi.onetrack.util.j.a(new k(str, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void c(String str, String str2) {
        File file = new File(e(), String.format(f, g.format(new Date())));
        try {
            if (!file.exists()) {
                if (file.getParentFile().exists()) {
                    file.createNewFile();
                } else {
                    new File(file.getParentFile().getAbsolutePath()).mkdirs();
                    file.createNewFile();
                }
                a(file, str, str2);
                com.xiaomi.onetrack.util.r.a(d, "cache file size: " + file.length() + " bytes");
            } else if (file.length() < 10485760) {
                a(file, str, str2);
                com.xiaomi.onetrack.util.r.a(d, "cache file size: " + file.length() + " bytes");
            } else {
                com.xiaomi.onetrack.util.r.a(d, "cache file reach max size, ignore!");
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(d, "cta doSaveData error: " + e2.getMessage());
        }
    }

    private static void a(File file, String str, String str2) throws Throwable {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter = null;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(b, str);
            jSONObject.put("data", str2);
            byte[] bArrB = com.xiaomi.onetrack.d.a.b(jSONObject.toString());
            if (bArrB == null) {
                com.xiaomi.onetrack.util.o.a((Closeable) null);
                com.xiaomi.onetrack.util.o.a((Closeable) null);
                return;
            }
            fileWriter = new FileWriter(file, true);
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter);
                    try {
                        bufferedWriter2.write(com.xiaomi.onetrack.d.c.a(bArrB));
                        bufferedWriter2.newLine();
                        j = true;
                        com.xiaomi.onetrack.util.o.a(bufferedWriter2);
                        com.xiaomi.onetrack.util.o.a(fileWriter);
                        return;
                    } catch (Exception e2) {
                        e = e2;
                        bufferedWriter = bufferedWriter2;
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        com.xiaomi.onetrack.util.o.a(bufferedWriter);
                        com.xiaomi.onetrack.util.o.a(fileWriter);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
            fileWriter = null;
        } catch (Throwable th3) {
            th = th3;
            fileWriter = null;
        }
        com.xiaomi.onetrack.util.r.b(d, "writeData error: " + e.getMessage());
        com.xiaomi.onetrack.util.o.a(bufferedWriter);
        com.xiaomi.onetrack.util.o.a(fileWriter);
    }

    public static synchronized void c(boolean z) {
        try {
            File file = new File(e());
            if (file.exists() && file.isDirectory()) {
                String str = String.format(f, g.format(new Date()));
                File[] fileArrListFiles = file.listFiles();
                for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                    if (fileArrListFiles[i2].isFile() && (z || !fileArrListFiles[i2].getName().equalsIgnoreCase(str))) {
                        fileArrListFiles[i2].delete();
                    }
                }
                if (file.listFiles().length == 0) {
                    j = false;
                }
                return;
            }
            j = false;
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(d, "cta removeObsoleteEvent error: " + e2.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.Closeable] */
    public static synchronized List<JSONObject> c() {
        FileReader fileReader;
        Exception e2;
        BufferedReader bufferedReader;
        File file = new File(e(), String.format(f, g.format(new Date())));
        ?? r3 = 0;
        r3 = 0;
        if (!file.exists()) {
            return null;
        }
        try {
            List<JSONObject> arrayList = new ArrayList<>();
            try {
                fileReader = new FileReader(file);
                try {
                    bufferedReader = new BufferedReader(fileReader);
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            arrayList.add(new JSONObject(com.xiaomi.onetrack.d.a.a(com.xiaomi.onetrack.d.c.a(line))));
                        } catch (Exception e3) {
                            e2 = e3;
                            com.xiaomi.onetrack.util.r.b(d, "cta getCacheData error: " + e2.getMessage());
                        }
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    bufferedReader = null;
                } catch (Throwable th) {
                    th = th;
                    com.xiaomi.onetrack.util.o.a((Closeable) r3);
                    com.xiaomi.onetrack.util.o.a(fileReader);
                    throw th;
                }
            } catch (Exception e5) {
                fileReader = null;
                e2 = e5;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                fileReader = null;
            }
            com.xiaomi.onetrack.util.o.a(bufferedReader);
            com.xiaomi.onetrack.util.o.a(fileReader);
            if (arrayList.size() > 200) {
                arrayList = arrayList.subList(arrayList.size() - 200, arrayList.size());
            }
            if (arrayList.size() > 0) {
                j = true;
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            r3 = file;
        }
    }

    public static boolean d() {
        return !k && j;
    }

    public static synchronized void a(ak akVar) {
        if (d()) {
            if (akVar != null && b() && !OneTrack.isDisable()) {
                k = true;
                com.xiaomi.onetrack.util.j.a(new l(akVar));
            }
        }
    }
}
