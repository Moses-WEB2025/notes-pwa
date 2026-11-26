package com.xiaomi.onetrack.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.onetrack.util.ab;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.t;
import com.xiaomi.onetrack.util.z;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class p {
    private static final String a = "PubSubConfigManager";
    private static final long b = 900000;
    private static volatile p e;
    private ConcurrentHashMap<String, o> c = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> d = new ConcurrentHashMap<>();
    private String h = t.b;
    private a f = new a(com.xiaomi.onetrack.f.a.a());
    private long g = ac.G();

    public static p a() {
        if (e == null) {
            synchronized (p.class) {
                if (e == null) {
                    e = new p();
                }
            }
        }
        return e;
    }

    private p() {
        ac.p(System.currentTimeMillis());
    }

    public o a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        o oVar = this.c.get(str);
        return oVar == null ? b(a.e, str) : oVar;
    }

    public o b(String str) {
        String str2 = this.d.get(str);
        if (ab.b(str2)) {
            return a(str2);
        }
        return b("appId", str);
    }

    public void a(String str, String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        o oVarA = a(str);
        if (oVarA == null) {
            o oVar = new o();
            oVar.b = str;
            oVar.c = str3;
            oVar.d.add(str2);
            oVar.a = str4;
            oVar.f = System.currentTimeMillis();
            a(oVar);
            return;
        }
        if (str.equals(oVarA.b) && str3.equals(oVarA.c) && !oVarA.d.contains(str2)) {
            oVarA.d.add(str2);
            oVarA.f = System.currentTimeMillis();
            a(oVarA);
        }
    }

    public void a(o oVar) {
        if (oVar == null || TextUtils.isEmpty(oVar.b)) {
            return;
        }
        this.d.put(oVar.a, oVar.b);
        this.c.put(oVar.b, oVar);
        b(oVar.b, oVar);
    }

    public String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (this.c.get(str) != null) {
            com.xiaomi.onetrack.h.a aVarA = this.c.get(str).a();
            if (a(aVarA)) {
                return aVarA.d;
            }
        }
        o oVarB = b(a.e, str);
        if (oVarB == null) {
            return "";
        }
        com.xiaomi.onetrack.h.a aVarA2 = oVarB.a();
        if (a(aVarA2)) {
            this.c.put(str, oVarB);
            return aVarA2.d;
        }
        com.xiaomi.onetrack.h.a aVarA3 = a(str, oVarB.c);
        if (!a(aVarA3)) {
            return "";
        }
        oVarB.a(aVarA3);
        a(oVarB);
        return aVarA3.d;
    }

    private com.xiaomi.onetrack.h.a a(String str, String str2) {
        com.xiaomi.onetrack.h.a aVar = null;
        try {
            if (!s.a(a) && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                HashMap map = new HashMap();
                map.put("timestamp", String.valueOf(System.currentTimeMillis()));
                String strA = com.xiaomi.onetrack.g.b.a(str2, z.a().f(), map);
                com.xiaomi.onetrack.h.a aVarA = com.xiaomi.onetrack.h.a.a(strA);
                if (aVarA != null) {
                    try {
                        if (aVarA.a == 0) {
                            r.a(a, "getToken success");
                            return aVarA;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        aVar = aVarA;
                        r.a(a, "getNewToken e:" + e.getMessage());
                        return aVar;
                    }
                }
                if (aVarA != null && aVarA.a == -10) {
                    r.a(a, "postGetToken invalid ,response:" + strA);
                    this.c.remove(str);
                    d(str);
                    return null;
                }
                r.a(a, "postGetToken responseData response:" + strA);
            }
            return null;
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0113 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:? A[Catch: all -> 0x012f, SYNTHETIC, TryCatch #8 {, blocks: (B:24:0x00c7, B:23:0x00b1, B:45:0x010c, B:30:0x00d0, B:31:0x00df, B:44:0x00fc, B:54:0x012e, B:53:0x0118, B:20:0x00ac, B:50:0x0113, B:41:0x00f7, B:27:0x00cb), top: B:69:0x000e, inners: #0, #2, #5, #7 }] */
    private o b(String str, String str2) {
        Throwable th;
        Cursor cursorQuery;
        String str3;
        StringBuilder sbAppend;
        List<String> list;
        synchronized (this.f) {
            Cursor cursor = null;
            try {
                cursorQuery = this.f.getWritableDatabase().query(a.b, null, str + "=?", new String[]{str2}, null, null, null);
                try {
                    try {
                        int columnIndex = cursorQuery.getColumnIndex(a.e);
                        int columnIndex2 = cursorQuery.getColumnIndex("appId");
                        int columnIndex3 = cursorQuery.getColumnIndex(a.f);
                        int columnIndex4 = cursorQuery.getColumnIndex(a.g);
                        int columnIndex5 = cursorQuery.getColumnIndex(a.h);
                        int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                        if (!cursorQuery.moveToNext()) {
                            if (cursorQuery != null) {
                                try {
                                    cursorQuery.close();
                                } catch (Throwable th2) {
                                    str3 = a;
                                    sbAppend = new StringBuilder("closeSafely e: ").append(th2.getMessage());
                                    r.b(str3, sbAppend.toString());
                                }
                            }
                            return null;
                        }
                        o oVar = new o();
                        oVar.b = cursorQuery.getString(columnIndex);
                        oVar.a = cursorQuery.getString(columnIndex2);
                        oVar.c = cursorQuery.getString(columnIndex3);
                        Object objA = com.xiaomi.onetrack.util.d.a(cursorQuery.getBlob(columnIndex4));
                        if ((objA instanceof List) && (list = (List) objA) != null && !list.isEmpty()) {
                            oVar.d = list;
                        }
                        String string = cursorQuery.getString(columnIndex5);
                        if (ab.b(string)) {
                            oVar.e = string;
                        }
                        oVar.f = cursorQuery.getLong(columnIndex6);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Throwable th3) {
                                r.b(a, "closeSafely e: " + th3.getMessage());
                            }
                        }
                        return oVar;
                    } catch (Throwable th4) {
                        th = th4;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            throw th;
                        }
                        try {
                            cursor.close();
                            throw th;
                        } catch (Throwable th5) {
                            r.b(a, "closeSafely e: " + th5.getMessage());
                            throw th;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    r.a(a, "exception while getConfig", e);
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Throwable th6) {
                            str3 = a;
                            sbAppend = new StringBuilder("closeSafely e: ").append(th6.getMessage());
                            r.b(str3, sbAppend.toString());
                        }
                    }
                }
            } catch (Exception e3) {
                e = e3;
                cursorQuery = null;
            } catch (Throwable th7) {
                th = th7;
                if (cursor != null) {
                    throw th;
                }
                cursor.close();
                throw th;
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b(String str, o oVar) {
        Throwable th;
        SQLiteDatabase sQLiteDatabase;
        String str2;
        StringBuilder sbAppend;
        synchronized (this.f) {
            try {
                try {
                    if (TextUtils.isEmpty(str) || oVar == null || TextUtils.isEmpty(oVar.b)) {
                        return;
                    }
                    Object obj = null;
                    String str3 = null;
                    SQLiteDatabase sQLiteDatabase2 = null;
                    try {
                        try {
                            SQLiteDatabase writableDatabase = this.f.getWritableDatabase();
                            try {
                                writableDatabase.beginTransaction();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("appId", oVar.a);
                                contentValues.put(a.e, oVar.b);
                                contentValues.put(a.f, oVar.c);
                                contentValues.put("timestamp", Long.valueOf(oVar.f));
                                if (oVar.d != null) {
                                    contentValues.put(a.g, com.xiaomi.onetrack.util.d.a(oVar.d));
                                }
                                if (oVar.e != null) {
                                    contentValues.put(a.h, oVar.e);
                                }
                                if (DatabaseUtils.queryNumEntries(writableDatabase, a.b, "projectId=?", new String[]{oVar.b}) > 0) {
                                    String str4 = a.b;
                                    writableDatabase.update(a.b, contentValues, "projectId=?", new String[]{oVar.b});
                                    str3 = str4;
                                } else {
                                    writableDatabase.insert(a.b, null, contentValues);
                                }
                                writableDatabase.setTransactionSuccessful();
                                obj = str3;
                                if (writableDatabase != null) {
                                    try {
                                        writableDatabase.endTransaction();
                                        obj = str3;
                                    } catch (Exception e2) {
                                        str2 = a;
                                        sbAppend = new StringBuilder("addProjectBeanToDB Exception while endTransaction:").append(e2.getMessage());
                                        r.b(str2, sbAppend.toString());
                                    }
                                }
                            } catch (Exception e3) {
                                e = e3;
                                sQLiteDatabase2 = writableDatabase;
                                r.b(a, "updateToDb error: ", e);
                                obj = sQLiteDatabase2;
                                if (sQLiteDatabase2 != null) {
                                    try {
                                        sQLiteDatabase2.endTransaction();
                                        obj = sQLiteDatabase2;
                                    } catch (Exception e4) {
                                        str2 = a;
                                        sbAppend = new StringBuilder("addProjectBeanToDB Exception while endTransaction:").append(e4.getMessage());
                                        r.b(str2, sbAppend.toString());
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                sQLiteDatabase = writableDatabase;
                                if (sQLiteDatabase != 0) {
                                    try {
                                        sQLiteDatabase.endTransaction();
                                        throw th;
                                    } catch (Exception e5) {
                                        r.b(a, "addProjectBeanToDB Exception while endTransaction:" + e5.getMessage());
                                        throw th;
                                    }
                                }
                                throw th;
                            }
                        } catch (Exception e6) {
                            e = e6;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        sQLiteDatabase = obj;
                    }
                } catch (Exception e7) {
                    r.a(a, "addProjectBeanToDB Exception:" + e7.getMessage());
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }

    private void c(String str, o oVar) {
        String str2;
        StringBuilder sbAppend;
        synchronized (this.f) {
            try {
                try {
                    if (TextUtils.isEmpty(str) || oVar == null || TextUtils.isEmpty(oVar.b)) {
                        return;
                    }
                    SQLiteDatabase writableDatabase = null;
                    try {
                        try {
                            writableDatabase = this.f.getWritableDatabase();
                            writableDatabase.beginTransaction();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("timestamp", Long.valueOf(oVar.f));
                            contentValues.put(a.h, oVar.e);
                            if (DatabaseUtils.queryNumEntries(writableDatabase, a.b, "projectId=?", new String[]{oVar.b}) > 0) {
                                writableDatabase.update(a.b, contentValues, "projectId=?", new String[]{oVar.b});
                            }
                            if (r.a) {
                                r.a(a, "forceRefreshTokenToDB: " + contentValues.toString() + "tid:" + Process.myTid());
                            }
                            writableDatabase.setTransactionSuccessful();
                            if (writableDatabase != null) {
                                try {
                                    writableDatabase.endTransaction();
                                } catch (Throwable th) {
                                    str2 = a;
                                    sbAppend = new StringBuilder("forceRefreshTokenToDB endTransactionSafely e: ").append(th.getMessage());
                                    r.b(str2, sbAppend.toString());
                                }
                            }
                        } catch (Exception e2) {
                            r.b(a, "updateToDb error: ", e2);
                            if (writableDatabase != null) {
                                try {
                                    writableDatabase.endTransaction();
                                } catch (Throwable th2) {
                                    str2 = a;
                                    sbAppend = new StringBuilder("forceRefreshTokenToDB endTransactionSafely e: ").append(th2.getMessage());
                                    r.b(str2, sbAppend.toString());
                                }
                            }
                        }
                    } catch (Throwable th3) {
                        if (writableDatabase != null) {
                            try {
                                writableDatabase.endTransaction();
                                throw th3;
                            } catch (Throwable th4) {
                                r.b(a, "forceRefreshTokenToDB endTransactionSafely e: " + th4.getMessage());
                                throw th3;
                            }
                        }
                        throw th3;
                    }
                } catch (Exception e3) {
                    r.a(a, "addProjectBeanToDB Exception:" + e3.getMessage());
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
    }

    public void d(String str) {
        synchronized (this.f) {
            try {
                this.f.getWritableDatabase().delete(a.b, "projectId=?", new String[]{str});
            } catch (Exception e2) {
                r.d(a, "delete project id info failed with " + e2);
            }
        }
    }

    public com.xiaomi.onetrack.h.a a(String str, o oVar) {
        if (oVar == null) {
            return null;
        }
        try {
            com.xiaomi.onetrack.h.a aVarA = a(str, oVar.c);
            if (a(aVarA)) {
                oVar.a(aVarA);
                a(oVar);
                return aVarA;
            }
            oVar.b();
            this.c.put(str, oVar);
            c(str, oVar);
            return aVarA;
        } catch (Exception e2) {
            r.b(a, "forceRefreshToken Exception：" + e2.getMessage());
            return null;
        }
    }

    public String b() {
        try {
            String strL = s.l();
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(strL)) {
                jSONObject.put("region", strL);
                if (com.xiaomi.onetrack.util.a.j.contains(strL.toUpperCase())) {
                    jSONObject.put(com.xiaomi.onetrack.util.a.e, com.xiaomi.onetrack.util.a.f);
                }
            }
            return jSONObject.toString();
        } catch (Exception e2) {
            r.a(a, "getDefaultAttributes exception:" + e2.getMessage());
            return "";
        }
    }

    public Map<String, String> c() {
        HashMap map = new HashMap();
        try {
            String strL = s.l();
            if (!TextUtils.isEmpty(strL)) {
                map.put("region", strL);
                if (com.xiaomi.onetrack.util.a.j.contains(strL.toUpperCase())) {
                    map.put(com.xiaomi.onetrack.util.a.e, com.xiaomi.onetrack.util.a.f);
                }
            }
            return map;
        } catch (Exception e2) {
            r.a(a, "getDefaultAttributes exception:" + e2.getMessage());
            return map;
        }
    }

    public String e(String str) {
        return this.h + str;
    }

    private static class a extends SQLiteOpenHelper {
        public static final String a = "ot_ps_project_info";
        public static final String b = "project_info";
        public static final String c = "_id";
        public static final String d = "appId";
        public static final String e = "projectId";
        public static final String f = "privateKeyId";
        public static final String g = "topics";
        public static final String h = "tokenBean";
        public static final String i = "timestamp";
        private static final int j = 1;
        private static final String k = "CREATE TABLE project_info (_id INTEGER PRIMARY KEY AUTOINCREMENT,appId TEXT,projectId TEXT,privateKeyId TEXT,topics BLOB,tokenBean TEXT,timestamp INTEGER)";

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(k);
        }
    }

    public boolean a(com.xiaomi.onetrack.h.a aVar) {
        if (aVar == null) {
            return false;
        }
        try {
            if (TextUtils.isEmpty(aVar.d)) {
                return false;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis < aVar.f) {
                return false;
            }
            long j = ((jCurrentTimeMillis - aVar.f) / 1000) + 180;
            r.a(a, "interval:" + j + "_currentTimeMillis:" + jCurrentTimeMillis + "_token.local_time:" + aVar.f);
            if (j < aVar.e) {
                return true;
            }
            r.a(a, "interval:" + j + "_expires:" + aVar.e + "_isValid:false");
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean b(o oVar) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (Math.abs(jCurrentTimeMillis - this.g) < b) {
                r.a(a, "init interval between two is too short,interval time:" + (jCurrentTimeMillis - this.g));
                return false;
            }
            if (Math.abs(jCurrentTimeMillis - oVar.h) > 3600000) {
                oVar.g = 0;
                oVar.h = jCurrentTimeMillis;
                r.a(a, "Interval over one hour mCallbackNumber:" + oVar.g + "_projectEntity.mLastCallbackTime:" + oVar.h);
                return true;
            }
            if (oVar.g < o.i) {
                oVar.g++;
                oVar.h = jCurrentTimeMillis;
                r.a(a, "+1 mCallbackNumber:" + oVar.g + "_projectEntity.mLastCallbackTime:" + oVar.h);
                return true;
            }
            r.a(a, "false mCallbackNumber:" + oVar.g + "_projectEntity.mLastCallbackTime:" + oVar.h);
            return false;
        } catch (Exception unused) {
        }
    }
}
