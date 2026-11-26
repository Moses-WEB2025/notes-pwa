package com.xiaomi.onetrack.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.xiaomi.onetrack.b.n;
import com.xiaomi.onetrack.util.r;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    private static final String a = "AdMonitorManager";
    private static final int b = 204800;
    private static final int c = 100;
    private static final int d = 4;
    private static final int e = 300;
    private static final String f = "_id ASC";
    private static final int g = 7;
    private static a h;
    private final C0120a i = new C0120a(com.xiaomi.onetrack.f.a.a());

    public static a a() {
        if (h == null) {
            a(com.xiaomi.onetrack.f.a.a());
        }
        return h;
    }

    public static void a(Context context) {
        if (h == null) {
            synchronized (a.class) {
                if (h == null) {
                    h = new a();
                }
            }
        }
    }

    private a() {
        c();
    }

    public void a(com.xiaomi.onetrack.f.b bVar) {
        try {
            com.xiaomi.onetrack.a.a.a.a(new b(this, bVar));
        } catch (Throwable th) {
            r.a(a, "filterAdMonitor Throwable：" + th.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long a(com.xiaomi.onetrack.a.b.a aVar) {
        synchronized (this.i) {
            try {
                if (!aVar.h()) {
                    r.c(a, "addAdMonitorToDatabase event is inValid, event:" + aVar.a());
                    return -1L;
                }
                SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("appid", aVar.e());
                contentValues.put("package", aVar.f());
                contentValues.put("event_name", aVar.a());
                contentValues.put("timestamp", Long.valueOf(aVar.d()));
                contentValues.put("url", aVar.c());
                long jInsert = writableDatabase.insert(C0120a.b, null, contentValues);
                r.a(a, "DB-Thread: AdMonitorManager.addAdMonitorToDatabase , row=" + jInsert);
                if (r.a) {
                    r.a(a, "添加后，ad monitor url 中事件个数为 " + e());
                }
                return jInsert;
            } catch (Throwable th) {
                r.a(a, "addAdMonitorToDatabase Throwable：" + th.getMessage());
                return -1L;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:43:0x00eb A[Catch: all -> 0x010b, PHI: r9 r16
  0x00eb: PHI (r9v4 android.database.Cursor) = (r9v3 android.database.Cursor), (r9v5 android.database.Cursor) binds: [B:42:0x00e9, B:48:0x00fe] A[DONT_GENERATE, DONT_INLINE]
  0x00eb: PHI (r16v4 com.xiaomi.onetrack.a.c.a) = (r16v3 com.xiaomi.onetrack.a.c.a), (r16v6 com.xiaomi.onetrack.a.c.a) binds: [B:42:0x00e9, B:48:0x00fe] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #7 {all -> 0x010b, blocks: (B:26:0x00c6, B:27:0x00c9, B:43:0x00eb, B:50:0x0101, B:55:0x0107, B:56:0x010a), top: B:61:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0107 A[Catch: all -> 0x010b, TryCatch #7 {all -> 0x010b, blocks: (B:26:0x00c6, B:27:0x00c9, B:43:0x00eb, B:50:0x0101, B:55:0x0107, B:56:0x010a), top: B:61:0x0005 }] */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0104: MOVE (r8 I:??[OBJECT, ARRAY]) = (r9 I:??[OBJECT, ARRAY]), block:B:53:0x0104 */
    public com.xiaomi.onetrack.a.c.a b() {
        Cursor cursor;
        com.xiaomi.onetrack.a.c.a aVar;
        Cursor cursorQuery;
        Cursor cursor2;
        boolean z;
        a aVar2 = this;
        synchronized (aVar2.i) {
            try {
                try {
                    try {
                        cursorQuery = aVar2.i.getReadableDatabase().query(C0120a.b, null, null, null, null, null, f);
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            cursorQuery.getColumnIndex("appid");
                            cursorQuery.getColumnIndex("package");
                            cursorQuery.getColumnIndex("event_name");
                            int columnIndex2 = cursorQuery.getColumnIndex("timestamp");
                            int columnIndex3 = cursorQuery.getColumnIndex("url");
                            int columnIndex4 = cursorQuery.getColumnIndex(C0120a.i);
                            ArrayList arrayList = new ArrayList();
                            long jCurrentTimeMillis = System.currentTimeMillis();
                            int i = 0;
                            while (true) {
                                if (!cursorQuery.moveToNext()) {
                                    aVar = null;
                                    break;
                                }
                                int i2 = cursorQuery.getInt(columnIndex4);
                                long j = cursorQuery.getLong(columnIndex2);
                                if (aVar2.a(jCurrentTimeMillis, j, i2)) {
                                    int i3 = cursorQuery.getInt(columnIndex);
                                    aVar = null;
                                    try {
                                        String string = cursorQuery.getString(columnIndex3);
                                        com.xiaomi.onetrack.a.b.a aVar3 = new com.xiaomi.onetrack.a.b.a();
                                        aVar3.b(string);
                                        aVar3.a(i3);
                                        aVar3.a(j);
                                        aVar3.b(i2);
                                        arrayList.add(aVar3);
                                        i++;
                                        if (i >= 100) {
                                            break;
                                        }
                                    } catch (SQLiteBlobTooBigException e2) {
                                        e = e2;
                                        r.b(a, "blob too big ***", e);
                                        f();
                                        if (cursorQuery != null) {
                                            cursorQuery.close();
                                        }
                                        return aVar;
                                    } catch (Exception e3) {
                                        e = e3;
                                        r.a(a, "", e);
                                        if (cursorQuery != null) {
                                            cursorQuery.close();
                                        }
                                        return aVar;
                                    }
                                }
                                aVar2 = this;
                            }
                            if (arrayList.size() > 0) {
                                r.a(a, "get ad monitor size :" + arrayList.size());
                                if (cursorQuery.isAfterLast()) {
                                    r.a(a, "cursor isAfterLast");
                                    z = true;
                                } else {
                                    z = false;
                                }
                            } else {
                                z = true;
                            }
                            com.xiaomi.onetrack.a.c.a aVar4 = new com.xiaomi.onetrack.a.c.a(i, arrayList, z);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return aVar4;
                        } catch (SQLiteBlobTooBigException e4) {
                            e = e4;
                            aVar = null;
                        } catch (Exception e5) {
                            e = e5;
                            aVar = null;
                        }
                    } catch (SQLiteBlobTooBigException e6) {
                        e = e6;
                        aVar = null;
                        cursorQuery = null;
                    } catch (Exception e7) {
                        e = e7;
                        aVar = null;
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
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public int a(ArrayList<Integer> arrayList) {
        synchronized (this.i) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    try {
                        SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                        StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).intValue()).length() + 1) * arrayList.size()) + 16);
                        sb.append("_id").append(" in (");
                        sb.append(arrayList.get(0));
                        int size = arrayList.size();
                        for (int i = 1; i < size; i++) {
                            sb.append(",").append(arrayList.get(i));
                        }
                        sb.append(")");
                        int iDelete = writableDatabase.delete(C0120a.b, sb.toString(), null);
                        r.a(a, "*** *** deleted ad monitor count " + iDelete);
                        if (r.a) {
                            r.a(a, "after delete ad monitor record remains=" + e());
                        }
                        return iDelete;
                    } catch (Exception e2) {
                        r.b(a, "e=" + e2);
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    public void b(ArrayList<Integer> arrayList) {
        String str;
        StringBuilder sbAppend;
        synchronized (this.i) {
            if (arrayList != null) {
                try {
                    if (arrayList.size() > 0) {
                        SQLiteDatabase writableDatabase = null;
                        try {
                            try {
                                writableDatabase = this.i.getWritableDatabase();
                                writableDatabase.beginTransaction();
                                Iterator<Integer> it = arrayList.iterator();
                                while (it.hasNext()) {
                                    writableDatabase.execSQL(String.format("update %s set %s = %s + 1 where %s = %s", C0120a.b, C0120a.i, C0120a.i, "_id", Integer.valueOf(it.next().intValue())));
                                }
                                writableDatabase.setTransactionSuccessful();
                                if (writableDatabase != null) {
                                    try {
                                        writableDatabase.endTransaction();
                                        writableDatabase.close();
                                    } catch (Exception e2) {
                                        str = a;
                                        sbAppend = new StringBuilder().append("addAdMonitorsRetryCount endTransaction error: ").append(e2.getMessage());
                                        r.b(str, sbAppend.toString());
                                    }
                                }
                            } catch (Exception e3) {
                                r.b(a, "addAdMonitorsRetryCount Exception: " + e3.getMessage());
                                if (writableDatabase != null) {
                                    try {
                                        writableDatabase.endTransaction();
                                        writableDatabase.close();
                                    } catch (Exception e4) {
                                        str = a;
                                        sbAppend = new StringBuilder().append("addAdMonitorsRetryCount endTransaction error: ").append(e4.getMessage());
                                        r.b(str, sbAppend.toString());
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            if (writableDatabase != null) {
                                try {
                                    writableDatabase.endTransaction();
                                    writableDatabase.close();
                                } catch (Exception e5) {
                                    r.b(a, "addAdMonitorsRetryCount endTransaction error: " + e5.getMessage());
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    r.a(a, "addAdMonitorsRetryCount Throwable:" + th2.getMessage());
                }
            }
        }
    }

    public void c() {
        com.xiaomi.onetrack.a.a.a.a(new c(this), 1000L);
    }

    public void d() {
        synchronized (this.i) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                    String[] strArr = {Long.toString(4L)};
                    cursorQuery = writableDatabase.query(C0120a.b, new String[]{"timestamp"}, "send_count >= ? ", strArr, null, null, f);
                    if (cursorQuery.getCount() != 0) {
                        r.a(a, "*** deleted obsolete ad monitor count=" + writableDatabase.delete(C0120a.b, "send_count >= ? ", strArr));
                    }
                    if (r.a) {
                        r.a(a, "after delete obsolete ad monitor record remains=" + e());
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Exception e2) {
                    r.d(a, "remove obsolete ad monitor failed with " + e2);
                    if (cursorQuery != null) {
                    }
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                    throw th;
                }
                throw th;
            }
        }
    }

    public long e() {
        try {
            return DatabaseUtils.queryNumEntries(this.i.getReadableDatabase(), C0120a.b);
        } catch (Exception e2) {
            r.b(a, "getTotalEventsNumberSync failed with " + e2.getMessage());
            return 0L;
        }
    }

    private void f() {
        try {
            this.i.getWritableDatabase().delete(C0120a.b, null, null);
            r.a(a, "delete table monitor");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public boolean a(long j, long j2, int i) {
        if (i <= 0) {
            return true;
        }
        if (i < 4) {
            if (Math.abs(j - j2) >= n.a(i - 1)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: com.xiaomi.onetrack.a.a$a, reason: collision with other inner class name */
    static class C0120a extends SQLiteOpenHelper {
        public static final String a = "onetrack_ad";
        public static final String b = "monitor";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "package";
        public static final String f = "event_name";
        public static final String g = "url";
        public static final String h = "timestamp";
        public static final String i = "send_count";
        private static final int j = 1;
        private static final String k = "CREATE TABLE monitor (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,package TEXT,event_name TEXT,url TEXT,send_count INTEGER DEFAULT 0,timestamp INTEGER)";

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }

        public C0120a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(k);
        }
    }
}
