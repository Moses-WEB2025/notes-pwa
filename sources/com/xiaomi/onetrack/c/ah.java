package com.xiaomi.onetrack.c;

import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.an;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ah {
    public static final String a = "config";
    public static final String b = "appId";
    public static final String c = "version";
    private static final String d = "UploaderEngine";
    private static final String e = "code";
    private static final String f = "UTF-8";
    private ae g;

    private ah() {
        d();
    }

    private static class a {
        private static final ah a = new ah();

        private a() {
        }
    }

    public static ah a() {
        return a.a;
    }

    public synchronized void a(int i, boolean z) {
        ae aeVar = this.g;
        if (aeVar != null) {
            aeVar.a(i, z);
        } else {
            com.xiaomi.onetrack.util.r.b(d, "*** impossible, upload timer should not be null");
        }
    }

    public void a(boolean z) {
        ae aeVar = this.g;
        if (aeVar != null) {
            aeVar.a(z);
        } else {
            com.xiaomi.onetrack.util.r.b(d, "*** impossible, upload timer should not be null");
        }
    }

    public boolean a(int i) {
        com.xiaomi.onetrack.util.r.a(d, "即将读取数据库并上传数据");
        if (an.k) {
            return c(i);
        }
        return b(i);
    }

    private boolean c(int i) {
        i iVarA;
        if (!b()) {
            return true;
        }
        do {
            iVarA = d.a().a(i);
            if (iVarA == null) {
                com.xiaomi.onetrack.util.r.a(d, "满足条件的记录为空，即将返回, priority=" + i);
                return true;
            }
            ArrayList<Long> arrayList = iVarA.c;
            boolean zA = a(iVarA.a);
            com.xiaomi.onetrack.util.r.a(d, "upload success:" + zA);
            if (!zA) {
                return false;
            }
            if (d.a().a(arrayList) == 0) {
                com.xiaomi.onetrack.util.r.b(d, "delete DB failed!", new Throwable());
            }
            return true;
        } while (!iVarA.d);
        com.xiaomi.onetrack.util.r.a(d, "No more records for prio=" + i);
        return true;
    }

    public boolean b(int i) {
        try {
            int i2 = 0;
            if (!c()) {
                return false;
            }
            int i3 = 100;
            while (true) {
                if (com.xiaomi.onetrack.util.r.a) {
                    i3 = 1000;
                }
                if (i2 >= i3) {
                    return true;
                }
                q qVarA = n.a().a(i);
                if (qVarA == null || qVarA.a == null || qVarA.a.size() == 0) {
                    com.xiaomi.onetrack.util.r.a(d, "满足条件的记录为空，即将返回");
                } else {
                    r.a(qVarA);
                    if (qVarA.d) {
                        com.xiaomi.onetrack.util.r.a(d, "No more records ");
                    } else {
                        i2++;
                    }
                }
                return true;
            }
        } catch (Exception unused) {
        }
    }

    private boolean b() {
        if (com.xiaomi.onetrack.b.n.a() && com.xiaomi.onetrack.g.c.a() && !com.xiaomi.onetrack.b.n.b()) {
            return true;
        }
        com.xiaomi.onetrack.util.r.a(d, "不用处理消息, available=" + com.xiaomi.onetrack.b.n.a() + ", 是否有网=" + com.xiaomi.onetrack.g.c.a() + ", 数据库是否为空=" + com.xiaomi.onetrack.b.n.b());
        return false;
    }

    private boolean c() {
        if (com.xiaomi.onetrack.g.c.a() && !com.xiaomi.onetrack.util.s.a(d) && !n.a().e()) {
            return true;
        }
        com.xiaomi.onetrack.util.r.a(d, "ps data not match the upload status，即将返回");
        return false;
    }

    private boolean a(JSONArray jSONArray) {
        try {
            String strB = com.xiaomi.onetrack.util.z.a().b();
            String string = jSONArray.toString();
            com.xiaomi.onetrack.util.r.a(d, " payload:" + string);
            byte[] bArrA = a(a(string), com.xiaomi.onetrack.d.f.a().b()[0]);
            com.xiaomi.onetrack.util.r.a(d, "before zip and encrypt, len=" + string.length() + ", after=" + bArrA.length);
            String strA = com.xiaomi.onetrack.g.b.a(strB, bArrA);
            com.xiaomi.onetrack.util.r.a(d, "sendDataToServer response: " + strA);
            if (TextUtils.isEmpty(strA)) {
                return false;
            }
            return b(strA);
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(d, "Exception while uploading ", e2);
            return false;
        }
    }

    private void d() {
        HandlerThread handlerThread = new HandlerThread("onetrack_uploader_worker");
        handlerThread.start();
        this.g = new ae(handlerThread.getLooper());
    }

    protected static byte[] a(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes("UTF-8").length);
                try {
                    gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    try {
                        gZIPOutputStream.write(str.getBytes("UTF-8"));
                        gZIPOutputStream.finish();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        com.xiaomi.onetrack.util.o.a((OutputStream) byteArrayOutputStream);
                        com.xiaomi.onetrack.util.o.a((OutputStream) gZIPOutputStream);
                        return byteArray;
                    } catch (Exception e2) {
                        e = e2;
                        com.xiaomi.onetrack.util.r.b(d, " zipData failed! " + e.toString());
                        com.xiaomi.onetrack.util.o.a((OutputStream) byteArrayOutputStream);
                        com.xiaomi.onetrack.util.o.a((OutputStream) gZIPOutputStream);
                        return null;
                    }
                } catch (Exception e3) {
                    e = e3;
                    gZIPOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    com.xiaomi.onetrack.util.o.a((OutputStream) byteArrayOutputStream2);
                    com.xiaomi.onetrack.util.o.a((OutputStream) 0);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                byteArrayOutputStream = null;
                gZIPOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                com.xiaomi.onetrack.util.o.a((OutputStream) byteArrayOutputStream2);
                com.xiaomi.onetrack.util.o.a((OutputStream) 0);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    protected static byte[] a(byte[] bArr, String str) {
        if (bArr == null) {
            com.xiaomi.onetrack.util.r.b(d, "content is null");
            return null;
        }
        return com.xiaomi.onetrack.d.a.a(bArr, com.xiaomi.onetrack.d.c.a(str));
    }

    private boolean b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int iOptInt = jSONObject.optInt("code");
            if (iOptInt == 0) {
                com.xiaomi.onetrack.util.r.a(d, "成功发送数据到服务端");
                com.xiaomi.onetrack.b.a.a().a(jSONObject);
                return true;
            }
            if (iOptInt != -3) {
                com.xiaomi.onetrack.util.r.b(d, "Error: status code=" + iOptInt);
                return false;
            }
            com.xiaomi.onetrack.util.r.b(d, "signature expired, will update");
            com.xiaomi.onetrack.d.f.a().c();
            return false;
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(d, "parseUploadingResult exception ", e2);
            return false;
        }
    }
}
