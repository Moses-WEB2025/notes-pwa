package com.xiaomi.onetrack.c;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class d {
    private static final String a = "EventManager";
    private static final boolean b = false;
    private static final int c = 204800;
    private static final int d = 512000;
    private static final int e = 307200;
    private static final int f = 300;
    private static final int g = 300;
    private static final String h = "priority ASC, _id ASC";
    private static final int i = 7;
    private static d j = null;
    private static final int l = 5242880;
    private static BroadcastReceiver m = new e();
    private a k;

    public static d a() {
        if (j == null) {
            a(com.xiaomi.onetrack.f.a.b());
        }
        return j;
    }

    public static void a(Context context) {
        if (j == null) {
            synchronized (d.class) {
                if (j == null) {
                    j = new d();
                }
            }
        }
    }

    private d() {
        Context contextA = com.xiaomi.onetrack.f.a.a();
        this.k = new a(contextA);
        c();
        b(contextA);
    }

    private static void b(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(m, intentFilter);
    }

    public synchronized void a(com.xiaomi.onetrack.f.b bVar, boolean z) {
        c.a(new g(this, bVar, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.xiaomi.onetrack.f.b bVar, boolean z) {
        synchronized (this.k) {
            if (!bVar.i()) {
                com.xiaomi.onetrack.util.r.c(a, "addEventToDatabase event is inValid, event:" + bVar.e());
                return;
            }
            SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("appid", bVar.c());
            contentValues.put("package", bVar.d());
            contentValues.put("event_name", bVar.e());
            contentValues.put("priority", Integer.valueOf(bVar.f()));
            contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            byte[] bArrB = com.xiaomi.onetrack.d.a.b(bVar.g().toString());
            if (z || "onetrack_bug_report".equals(bVar.e())) {
                if (bArrB.length > d) {
                    com.xiaomi.onetrack.util.r.b(a, "ad or bug Too large data, discard ***");
                    return;
                }
            } else if (bArrB.length > c) {
                com.xiaomi.onetrack.util.r.b(a, "Too large data, discard ***");
                return;
            }
            contentValues.put("data", bArrB);
            long jInsert = writableDatabase.insert("events", null, contentValues);
            com.xiaomi.onetrack.util.r.a(a, "DB-Thread: EventManager.addEventToDatabase , row=" + jInsert);
            if (jInsert != -1) {
                if (com.xiaomi.onetrack.util.r.a) {
                    com.xiaomi.onetrack.util.r.a(a, "添加后，DB 中事件个数为 " + d());
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (com.xiaomi.onetrack.util.a.a.equals(bVar.e())) {
                    com.xiaomi.onetrack.util.ac.a(jCurrentTimeMillis);
                }
                com.xiaomi.onetrack.b.n.a(false);
            }
        }
    }

    public boolean a(ac acVar) {
        synchronized (this.k) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                if (acVar != null) {
                    try {
                        if (acVar.a != null && acVar.a.size() != 0) {
                            SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
                            try {
                                writableDatabase.beginTransaction();
                                for (y yVar : acVar.a) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("appid", yVar.b());
                                    contentValues.put("package", yVar.c());
                                    contentValues.put("event_name", yVar.j());
                                    contentValues.put("priority", Integer.valueOf(yVar.g()));
                                    contentValues.put("timestamp", Long.valueOf(yVar.i()));
                                    contentValues.put("data", yVar.f());
                                    com.xiaomi.onetrack.util.r.a(a, "DB-Thread: EventManager.addTransformDataToDB , row=" + writableDatabase.insert("events", null, contentValues));
                                }
                                com.xiaomi.onetrack.b.n.a(false);
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

    /* JADX WARN: Code duplicated, block: B:64:0x0142 A[PHI: r5 r20
  0x0142: PHI (r5v5 android.database.Cursor) = (r5v3 android.database.Cursor), (r5v4 android.database.Cursor), (r5v7 android.database.Cursor) binds: [B:63:0x0140, B:68:0x0153, B:48:0x0121] A[DONT_GENERATE, DONT_INLINE]
  0x0142: PHI (r20v5 com.xiaomi.onetrack.c.i) = (r20v3 com.xiaomi.onetrack.c.i), (r20v4 com.xiaomi.onetrack.c.i), (r20v10 com.xiaomi.onetrack.c.i) binds: [B:63:0x0140, B:68:0x0153, B:48:0x0121] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:74:0x015b  */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x0158: MOVE (r3 I:??[OBJECT, ARRAY]) = (r5 I:??[OBJECT, ARRAY]), block:B:72:0x0158 */
    public i a(int i2) {
        Cursor cursor;
        i iVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        boolean z2;
        try {
            try {
                cursorQuery = this.k.getReadableDatabase().query("events", null, null, null, null, null, h, String.valueOf(300));
                try {
                    int columnIndex = cursorQuery.getColumnIndex("_id");
                    int columnIndex2 = cursorQuery.getColumnIndex("appid");
                    cursorQuery.getColumnIndex("package");
                    cursorQuery.getColumnIndex("event_name");
                    int columnIndex3 = cursorQuery.getColumnIndex("priority");
                    int columnIndex4 = cursorQuery.getColumnIndex("data");
                    int columnIndex5 = cursorQuery.getColumnIndex("timestamp");
                    JSONArray jSONArray = new JSONArray();
                    ArrayList arrayList = new ArrayList();
                    int i3 = 0;
                    int i4 = 0;
                    boolean z3 = true;
                    while (true) {
                        if (!cursorQuery.moveToNext()) {
                            iVar = null;
                            break;
                        }
                        long j2 = cursorQuery.getLong(columnIndex);
                        cursorQuery.getLong(columnIndex5);
                        cursorQuery.getString(columnIndex2);
                        byte[] blob = cursorQuery.getBlob(columnIndex4);
                        String strA = blob != null ? com.xiaomi.onetrack.d.a.a(blob) : null;
                        iVar = null;
                        if (z3) {
                            try {
                                try {
                                    int i5 = cursorQuery.getInt(columnIndex3);
                                    if (i5 > i2) {
                                        com.xiaomi.onetrack.util.r.a(a, "No records of priority[" + i2 + "], first record priority=" + i5);
                                        if (cursorQuery != null) {
                                            cursorQuery.close();
                                        }
                                        return null;
                                    }
                                    z2 = false;
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
                                e();
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                            }
                        } else {
                            z2 = z3;
                        }
                        try {
                            jSONArray.put(new JSONObject(strA));
                            arrayList.add(Long.valueOf(j2));
                            i3++;
                        } catch (Exception e4) {
                            com.xiaomi.onetrack.util.r.b(a, "*** error ***", e4);
                        }
                        int length = i4 + strA.length();
                        if (length >= e) {
                            com.xiaomi.onetrack.util.r.a(a, "reached max len: " + length);
                            break;
                        }
                        i4 = length;
                        z3 = z2;
                    }
                    int i6 = i3;
                    if (arrayList.size() <= 0) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return iVar;
                    }
                    if (!cursorQuery.isAfterLast()) {
                        z = cursorQuery.getInt(columnIndex3) > i2;
                    } else if (i6 == 300) {
                        com.xiaomi.onetrack.util.r.a(a, "cursor max one query number");
                    } else {
                        com.xiaomi.onetrack.util.r.a(a, "cursor isAfterLast");
                    }
                    i iVar2 = new i(jSONArray, i6, arrayList, z);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return iVar2;
                } catch (SQLiteBlobTooBigException e5) {
                    e = e5;
                    iVar = null;
                } catch (Exception e6) {
                    e = e6;
                    iVar = null;
                }
            } catch (SQLiteBlobTooBigException e7) {
                e = e7;
                iVar = null;
                cursorQuery = null;
            } catch (Exception e8) {
                e = e8;
                iVar = null;
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
                cursor = null;
                if (cursor != null) {
                    cursor.close();
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

    /* JADX WARN: Code duplicated, block: B:66:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x00f4 A[EXC_TOP_SPLITTER, PHI: r4 r18
  0x00f4: PHI (r4v5 android.database.Cursor) = (r4v3 android.database.Cursor), (r4v4 android.database.Cursor), (r4v7 android.database.Cursor) binds: [B:55:0x0124, B:51:0x0116, B:34:0x00f2] A[DONT_GENERATE, DONT_INLINE]
  0x00f4: PHI (r18v5 com.xiaomi.onetrack.c.ac) = (r18v3 com.xiaomi.onetrack.c.ac), (r18v4 com.xiaomi.onetrack.c.ac), (r18v12 com.xiaomi.onetrack.c.ac) binds: [B:55:0x0124, B:51:0x0116, B:34:0x00f2] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0129: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:59:0x0129 */
    public ac b() throws Throwable {
        Cursor cursor;
        ac acVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        byte[] bArr;
        try {
            try {
                cursorQuery = this.k.getReadableDatabase().query("events", null, null, null, null, null, h, String.valueOf(300));
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
                                        e();
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

    public int a(ArrayList<Long> arrayList) {
        synchronized (this.k) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    try {
                        SQLiteDatabase writableDatabase = this.k.getWritableDatabase();
                        boolean z = true;
                        StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                        sb.append("_id").append(" in (");
                        sb.append(arrayList.get(0));
                        int size = arrayList.size();
                        for (int i2 = 1; i2 < size; i2++) {
                            sb.append(",").append(arrayList.get(i2));
                        }
                        sb.append(")");
                        int iDelete = writableDatabase.delete("events", sb.toString(), null);
                        com.xiaomi.onetrack.util.r.a(a, "deleted events count " + iDelete);
                        long jD = a().d();
                        if (jD != 0) {
                            z = false;
                        }
                        com.xiaomi.onetrack.b.n.a(z);
                        com.xiaomi.onetrack.util.r.a(a, "after delete DB record remains=" + jD);
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

    public void c() {
        c.a(new h(this), 1000L);
    }

    public long d() {
        try {
            return DatabaseUtils.queryNumEntries(this.k.getReadableDatabase(), "events");
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(a, "getTotalEventsNumberSync failed with " + e2.getMessage());
            return 0L;
        }
    }

    private void e() {
        try {
            this.k.getWritableDatabase().delete("events", null, null);
            com.xiaomi.onetrack.util.r.a(a, "delete table events");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class a extends SQLiteOpenHelper {
        public static final String a = "onetrack";
        public static final String b = "events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "event_name";
        public static final String g = "priority";
        public static final String h = "data";
        public static final String i = "timestamp";
        private static final int j = 1;
        private static final String k = "CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,priority INTEGER,data BLOB,timestamp INTEGER)";

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
}
