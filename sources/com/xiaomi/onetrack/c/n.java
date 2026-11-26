package com.xiaomi.onetrack.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class n {
    private static final String a = "PubSubEventManager";
    private static n b = null;
    private static final int d = 1000;
    private static final int e = 204800;
    private static final int f = 512000;
    private static final int g = 307200;
    private static final int h = 7;
    private static final int i = 300;
    private static final String j = "priority ASC, _id ASC";
    private static volatile boolean k = false;
    private static final int l = 5242880;
    private a c = new a(com.xiaomi.onetrack.f.a.a());

    public static n a() {
        if (b == null) {
            b();
        }
        return b;
    }

    public static void b() {
        if (b == null) {
            synchronized (n.class) {
                if (b == null) {
                    b = new n();
                }
            }
        }
    }

    private n() {
        d();
    }

    public synchronized void a(String str, String str2, com.xiaomi.onetrack.f.b bVar, Map<String, String> map, boolean z) throws Throwable {
        try {
            try {
                c.a(new o(this, str, str2, bVar, map, z));
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(String str, String str2, com.xiaomi.onetrack.f.b bVar, Map<String, String> map, boolean z) {
        try {
            synchronized (this.c) {
                if (bVar != null) {
                    if (bVar.i() && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        byte[] bArrB = com.xiaomi.onetrack.d.a.b(bVar.g().toString());
                        if (z || "onetrack_bug_report".equals(bVar.e())) {
                            if (bArrB.length > f) {
                                com.xiaomi.onetrack.util.r.b(a, "ad or bug Too large data, discard ***");
                                return false;
                            }
                        } else if (bArrB.length > e) {
                            com.xiaomi.onetrack.util.r.b(a, "Too large data, discard ***");
                            return false;
                        }
                        SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("appid", bVar.c());
                        contentValues.put("package", bVar.d());
                        contentValues.put("event_name", bVar.e());
                        contentValues.put(a.f, str);
                        contentValues.put(a.g, str2);
                        contentValues.put("attribute", com.xiaomi.onetrack.util.d.a((Object) map));
                        contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
                        contentValues.put("data", bArrB);
                        contentValues.put("priority", Integer.valueOf(bVar.f()));
                        long jInsert = writableDatabase.insert(a.b, null, contentValues);
                        com.xiaomi.onetrack.util.r.a(a, "DB-Thread: addOTMessageToDatabase , row=" + jInsert);
                        if (jInsert != -1) {
                            if (com.xiaomi.onetrack.util.r.a) {
                                com.xiaomi.onetrack.util.r.a(a, "添加后，DB 中事件个数为 " + f());
                            }
                            a(false);
                        }
                        return jInsert != -1;
                    }
                }
                com.xiaomi.onetrack.util.r.c(a, "addEventToDatabase message is inValid. topic:" + str2 + ", data EventName:" + bVar.e() + ", projectId:" + str);
                return false;
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(a, "addEvent exception: ", e2);
            return false;
        }
    }

    public boolean a(ac acVar) {
        synchronized (this.c) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                if (acVar != null) {
                    try {
                        if (acVar.a != null && acVar.a.size() != 0) {
                            SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                            try {
                                writableDatabase.beginTransaction();
                                for (y yVar : acVar.a) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("appid", yVar.b());
                                    contentValues.put("package", yVar.c());
                                    contentValues.put("event_name", yVar.j());
                                    contentValues.put(a.f, yVar.d());
                                    contentValues.put(a.g, yVar.e());
                                    contentValues.put("attribute", com.xiaomi.onetrack.util.d.a((Object) yVar.h()));
                                    contentValues.put("timestamp", Long.valueOf(yVar.i()));
                                    contentValues.put("data", yVar.f());
                                    contentValues.put("priority", Integer.valueOf(yVar.g()));
                                    com.xiaomi.onetrack.util.r.a(a, "DB-Thread: EventManager.addTransformDataToDB , row=" + writableDatabase.insert(a.b, null, contentValues));
                                }
                                a(false);
                                writableDatabase.setTransactionSuccessful();
                                if (writableDatabase != null) {
                                    try {
                                        writableDatabase.endTransaction();
                                    } catch (Exception e2) {
                                        com.xiaomi.onetrack.util.r.b(a, "addTransformDataToDB Exception while endTransaction:" + e2);
                                    }
                                }
                                return true;
                            } catch (Exception e3) {
                                e = e3;
                                sQLiteDatabase = writableDatabase;
                                com.xiaomi.onetrack.util.r.a(a, "DB-Thread: addTransformDataToDB , exception:" + e.getMessage());
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.endTransaction();
                                    } catch (Exception e4) {
                                        com.xiaomi.onetrack.util.r.b(a, "addTransformDataToDB Exception while endTransaction:" + e4);
                                    }
                                }
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                sQLiteDatabase = writableDatabase;
                                if (sQLiteDatabase != null) {
                                    try {
                                        sQLiteDatabase.endTransaction();
                                    } catch (Exception e5) {
                                        com.xiaomi.onetrack.util.r.b(a, "addTransformDataToDB Exception while endTransaction:" + e5);
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                }
                com.xiaomi.onetrack.util.r.a(a, "TransformEvent记录为空，即将返回");
                return false;
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:111:0x010b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:112:0x0111 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x010c  */
    /* JADX WARN: Code duplicated, block: B:41:0x0129 A[LOOP:0: B:5:0x0069->B:41:0x0129, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:93:0x016d A[EXC_TOP_SPLITTER, PHI: r5 r20
  0x016d: PHI (r5v4 android.database.Cursor) = (r5v2 android.database.Cursor), (r5v3 android.database.Cursor), (r5v6 android.database.Cursor) binds: [B:77:0x018d, B:81:0x019b, B:60:0x016b] A[DONT_GENERATE, DONT_INLINE]
  0x016d: PHI (r20v5 com.xiaomi.onetrack.c.q) = (r20v3 com.xiaomi.onetrack.c.q), (r20v4 com.xiaomi.onetrack.c.q), (r20v13 com.xiaomi.onetrack.c.q) binds: [B:77:0x018d, B:81:0x019b, B:60:0x016b] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x0167 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r26v0 */
    /* JADX WARN: Type inference failed for: r26v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r26v2 */
    /* JADX WARN: Type inference failed for: r27v0 */
    /* JADX WARN: Type inference failed for: r27v1, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r27v2 */
    public q a(int i2) {
        q qVar;
        Cursor cursorQuery;
        boolean z;
        boolean z2;
        ?? A;
        Object objA;
        ?? r27;
        int length;
        Map map;
        Cursor cursor = null;
        qVar = null;
        qVar = null;
        q qVar2 = null;
        try {
            cursorQuery = this.c.getReadableDatabase().query(a.b, null, null, null, null, null, j, String.valueOf(300));
            try {
                try {
                    int columnIndex = cursorQuery.getColumnIndex("_id");
                    cursorQuery.getColumnIndex("appid");
                    int columnIndex2 = cursorQuery.getColumnIndex(a.f);
                    int columnIndex3 = cursorQuery.getColumnIndex(a.g);
                    int columnIndex4 = cursorQuery.getColumnIndex("data");
                    int columnIndex5 = cursorQuery.getColumnIndex("attribute");
                    int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                    int columnIndex7 = cursorQuery.getColumnIndex("priority");
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    boolean z3 = true;
                    boolean z4 = true;
                    int i3 = 0;
                    int i4 = 0;
                    while (true) {
                        if (!cursorQuery.moveToNext()) {
                            qVar = qVar2;
                            break;
                        }
                        long j2 = cursorQuery.getLong(columnIndex);
                        String string = cursorQuery.getString(columnIndex2);
                        String string2 = cursorQuery.getString(columnIndex3);
                        byte[] blob = cursorQuery.getBlob(columnIndex5);
                        byte[] blob2 = cursorQuery.getBlob(columnIndex4);
                        long j3 = cursorQuery.getLong(columnIndex6);
                        try {
                            if (z4) {
                                int i5 = cursorQuery.getInt(columnIndex7);
                                if (i5 > i2) {
                                    com.xiaomi.onetrack.util.r.a(a, "ps no records of priority[" + i2 + "], first record priority=" + i5);
                                    if (cursorQuery != null) {
                                        try {
                                            cursorQuery.close();
                                        } catch (Exception unused) {
                                        }
                                    }
                                    return qVar2;
                                }
                                z2 = false;
                                com.xiaomi.onetrack.util.r.b(a, "blob too big ***", e);
                                g();
                                if (cursorQuery != null) {
                                    try {
                                        cursorQuery.close();
                                    } catch (Exception unused2) {
                                    }
                                }
                                return qVar;
                            }
                            z2 = z4;
                            if (objA != null) {
                                qVar = qVar2;
                                try {
                                    try {
                                        if (objA instanceof Map) {
                                            map = (Map) objA;
                                        }
                                        r27 = map;
                                        arrayList.add(new m(j2, string, string2, A, r27, j3));
                                        arrayList2.add(Long.valueOf(j2));
                                        i3++;
                                        length = i4 + A.length();
                                        if (arrayList.size() < 1000) {
                                            break;
                                        }
                                        if (length >= g) {
                                            com.xiaomi.onetrack.util.r.a(a, "reached max len: " + length);
                                            break;
                                        }
                                        i4 = length;
                                        qVar2 = qVar;
                                        z4 = z2;
                                    } catch (Exception e2) {
                                        e = e2;
                                        com.xiaomi.onetrack.util.r.a(a, "", e);
                                        if (cursorQuery != null) {
                                            cursorQuery.close();
                                        }
                                    }
                                } catch (SQLiteBlobTooBigException e3) {
                                    e = e3;
                                    com.xiaomi.onetrack.util.r.b(a, "blob too big ***", e);
                                    g();
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                }
                            } else {
                                qVar = qVar2;
                            }
                            arrayList.add(new m(j2, string, string2, A, r27, j3));
                            arrayList2.add(Long.valueOf(j2));
                            i3++;
                        } catch (Exception e4) {
                            com.xiaomi.onetrack.util.r.b(a, "*** error ***", e4);
                        }
                        A = blob2 != null ? com.xiaomi.onetrack.d.a.a(blob2) : qVar2;
                        objA = com.xiaomi.onetrack.util.d.a(blob);
                        if (objA == null) {
                            map = new HashMap();
                            r27 = map;
                        } else {
                            r27 = qVar;
                        }
                        length = i4 + A.length();
                        if (arrayList.size() < 1000) {
                            break;
                            break;
                        }
                        if (length >= g) {
                            com.xiaomi.onetrack.util.r.a(a, "reached max len: " + length);
                            break;
                        }
                        i4 = length;
                        qVar2 = qVar;
                        z4 = z2;
                    }
                    int i6 = i3;
                    if (arrayList2.size() <= 0) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return qVar;
                    }
                    if (!cursorQuery.isAfterLast()) {
                        if (cursorQuery.getInt(columnIndex7) <= i2) {
                            z3 = false;
                        }
                    } else {
                        if (i6 == 300) {
                            com.xiaomi.onetrack.util.r.a(a, "cursor max one query number");
                            z = false;
                        } else {
                            com.xiaomi.onetrack.util.r.a(a, "cursor isAfterLast");
                        }
                        q qVar3 = new q(arrayList, i6, arrayList2, z);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Exception unused3) {
                            }
                        }
                        return qVar3;
                    }
                    z = z3;
                    q qVar4 = new q(arrayList, i6, arrayList2, z);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return qVar4;
                } catch (SQLiteBlobTooBigException e5) {
                    e = e5;
                    qVar = qVar2;
                } catch (Exception e6) {
                    e = e6;
                    qVar = qVar2;
                }
            } catch (Throwable th) {
                th = th;
                cursor = cursorQuery;
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        } catch (SQLiteBlobTooBigException e7) {
            e = e7;
            qVar = null;
            cursorQuery = null;
        } catch (Exception e8) {
            e = e8;
            qVar = null;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:66:0x012d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00f5 A[EXC_TOP_SPLITTER, PHI: r4 r18
  0x00f5: PHI (r4v5 android.database.Cursor) = (r4v3 android.database.Cursor), (r4v4 android.database.Cursor), (r4v7 android.database.Cursor) binds: [B:55:0x0125, B:51:0x0117, B:34:0x00f3] A[DONT_GENERATE, DONT_INLINE]
  0x00f5: PHI (r18v5 com.xiaomi.onetrack.c.ac) = (r18v3 com.xiaomi.onetrack.c.ac), (r18v4 com.xiaomi.onetrack.c.ac), (r18v12 com.xiaomi.onetrack.c.ac) binds: [B:55:0x0125, B:51:0x0117, B:34:0x00f3] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x012a: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:59:0x012a */
    public ac c() throws Throwable {
        Cursor cursor;
        ac acVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        byte[] bArr;
        try {
            try {
                cursorQuery = this.c.getReadableDatabase().query(a.b, null, null, null, null, null, j, String.valueOf(300));
                try {
                    try {
                        int columnIndex = cursorQuery.getColumnIndex("_id");
                        int columnIndex2 = cursorQuery.getColumnIndex("appid");
                        int columnIndex3 = cursorQuery.getColumnIndex("package");
                        int columnIndex4 = cursorQuery.getColumnIndex("event_name");
                        int columnIndex5 = cursorQuery.getColumnIndex("data");
                        int columnIndex6 = cursorQuery.getColumnIndex("timestamp");
                        int columnIndex7 = cursorQuery.getColumnIndex("priority");
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        int i2 = 0;
                        int i3 = 0;
                        while (true) {
                            if (!cursorQuery.moveToNext()) {
                                acVar = null;
                                break;
                            }
                            long j2 = cursorQuery.getLong(columnIndex);
                            String string = cursorQuery.getString(columnIndex2);
                            String string2 = cursorQuery.getString(columnIndex4);
                            String string3 = cursorQuery.getString(columnIndex3);
                            byte[] blob = cursorQuery.getBlob(columnIndex5);
                            try {
                                bArr = blob;
                                acVar = null;
                                try {
                                    try {
                                        arrayList.add(new y(j2, null, null, string, string3, string2, blob, null, cursorQuery.getLong(columnIndex6), cursorQuery.getInt(columnIndex7)));
                                        arrayList2.add(Long.valueOf(j2));
                                        i2++;
                                    } catch (SQLiteBlobTooBigException e2) {
                                        e = e2;
                                        com.xiaomi.onetrack.util.r.b(a, "blob too big ***", e);
                                        g();
                                        if (cursorQuery != null) {
                                            try {
                                                cursorQuery.close();
                                            } catch (Exception unused) {
                                            }
                                        }
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    com.xiaomi.onetrack.util.r.b(a, "*** error ***", e);
                                }
                            } catch (Exception e4) {
                                e = e4;
                                bArr = blob;
                                acVar = null;
                            }
                            try {
                                int length = i3 + bArr.length;
                                if (length >= l) {
                                    com.xiaomi.onetrack.util.r.a(a, "reached max len: " + length);
                                    break;
                                }
                                i3 = length;
                            } catch (Exception e5) {
                                e = e5;
                                com.xiaomi.onetrack.util.r.a(a, "", e);
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                            }
                        }
                        int i4 = i2;
                        if (arrayList2.size() <= 0) {
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return acVar;
                        }
                        if (!cursorQuery.isAfterLast()) {
                            z = false;
                        } else if (i4 == 300) {
                            com.xiaomi.onetrack.util.r.a(a, "cursor max one query number");
                            z = false;
                        } else {
                            com.xiaomi.onetrack.util.r.a(a, "cursor isAfterLast");
                            z = true;
                        }
                        ac acVar2 = new ac(arrayList, i4, arrayList2, z);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                            } catch (Exception unused2) {
                            }
                        }
                        return acVar2;
                    } catch (SQLiteBlobTooBigException e6) {
                        e = e6;
                        acVar = null;
                    }
                } catch (Exception e7) {
                    e = e7;
                    acVar = null;
                }
            } catch (SQLiteBlobTooBigException e8) {
                e = e8;
                acVar = null;
                cursorQuery = null;
            } catch (Exception e9) {
                e = e9;
                acVar = null;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                cursor = null;
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception unused3) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            cursor = cursor2;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private void g() {
        try {
            this.c.getWritableDatabase().delete(a.b, null, null);
            a(true);
            com.xiaomi.onetrack.util.r.a(a, "delete table otps_events");
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(a, "delete table error: " + e2.getMessage());
        }
    }

    public int a(List<m> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        ArrayList<Long> arrayList = new ArrayList<>();
        Iterator<m> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Long.valueOf(it.next().b()));
        }
        return a(arrayList);
    }

    public int a(ArrayList<Long> arrayList) {
        int iDelete;
        synchronized (this.c) {
            try {
                try {
                    SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                    boolean z = true;
                    StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                    sb.append("_id").append(" in (");
                    sb.append(arrayList.get(0));
                    int size = arrayList.size();
                    for (int i2 = 1; i2 < size; i2++) {
                        sb.append(",").append(arrayList.get(i2));
                    }
                    sb.append(")");
                    iDelete = writableDatabase.delete(a.b, sb.toString(), null);
                    com.xiaomi.onetrack.util.r.a(a, "*** *** deleted events count " + iDelete);
                    long jF = a().f();
                    if (jF != 0) {
                        z = false;
                    }
                    a(z);
                    com.xiaomi.onetrack.util.r.a(a, "after delete DB record remains=" + jF);
                } catch (Exception e2) {
                    com.xiaomi.onetrack.util.r.b(a, "e=" + e2);
                    return 0;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iDelete;
    }

    public int b(List<m> list) {
        synchronized (this.c) {
            if (list != null) {
                if (list.size() != 0) {
                    try {
                        SQLiteDatabase writableDatabase = this.c.getWritableDatabase();
                        boolean z = true;
                        StringBuilder sb = new StringBuilder(((Long.toString(list.get(0).b()).length() + 1) * list.size()) + 16);
                        sb.append("_id").append(" in (");
                        int size = list.size();
                        boolean z2 = false;
                        for (int i2 = 0; i2 < size; i2++) {
                            if (com.xiaomi.onetrack.util.ae.a(list.get(i2).g(), 86400000L)) {
                                if (i2 == size - 1) {
                                    sb.append(list.get(i2).b());
                                } else {
                                    sb.append(list.get(i2).b()).append(",");
                                }
                                z2 = true;
                            }
                        }
                        sb.append(")");
                        if (!z2) {
                            return 0;
                        }
                        com.xiaomi.onetrack.util.r.a(a, "*** *** deleted events sb id " + sb.toString());
                        int iDelete = writableDatabase.delete(a.b, sb.toString(), null);
                        com.xiaomi.onetrack.util.r.a(a, "*** *** deleted events count " + iDelete);
                        long jF = a().f();
                        if (jF != 0) {
                            z = false;
                        }
                        a(z);
                        com.xiaomi.onetrack.util.r.a(a, "after delete DB record remains=" + jF);
                        return iDelete;
                    } catch (Exception e2) {
                        com.xiaomi.onetrack.util.r.b(a, "e=" + e2);
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    public void d() {
        c.a(new p(this));
    }

    public synchronized void a(boolean z) {
        k = z;
    }

    public synchronized boolean e() {
        return k;
    }

    public long f() {
        return DatabaseUtils.queryNumEntries(this.c.getReadableDatabase(), a.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class a extends SQLiteOpenHelper {
        public static final String a = "ot_ps_sdk";
        public static final String b = "otps_events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "projectid";
        public static final String g = "topic";
        public static final String h = "data";
        public static final String i = "attribute";
        public static final String j = "timestamp";
        public static final String k = "event_name";
        public static final String l = "priority";
        private static final int m = 1;
        private static final String n = "CREATE TABLE otps_events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,projectid TEXT,topic TEXT,data BLOB,attribute BLOB,priority INTEGER,timestamp INTEGER)";

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(n);
        }
    }
}
