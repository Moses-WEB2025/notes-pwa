package com.xiaomi.onetrack.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaomi.onetrack.api.az;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class s {
    public static final int a = 104857600;
    private static final String b = "SystemImpCacheManager";
    private static final String c = "systemimp_cache";
    private static String d = "systemimp_cache_%s";
    private static volatile s e = null;
    private static String f = "system_imp_cache_manager";
    private static final int h = 307200;
    private static final String j = "_id ASC";
    private static final int k = 100;
    private static final int l = 7;
    private Handler g;
    private a i;

    public static s a() {
        if (e == null) {
            b();
        }
        return e;
    }

    public static void b() {
        if (e == null) {
            synchronized (s.class) {
                if (e == null) {
                    e = new s();
                }
            }
        }
    }

    private s() {
        try {
            this.i = new a(com.xiaomi.onetrack.f.a.a());
            HandlerThread handlerThread = new HandlerThread(f);
            handlerThread.start();
            this.g = new Handler(handlerThread.getLooper());
            c();
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b(b, "SystemImpCacheManager init Throwable: " + th.getMessage());
        }
    }

    private static String e() {
        return com.xiaomi.onetrack.f.a.a().getFilesDir().getAbsolutePath() + File.separator + c;
    }

    public void a(String str, String str2, String str3) {
        if (this.g == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.g.post(new t(this, str, str2, str3));
    }

    public void a(String str, String str2, String str3, String str4) {
        if (this.g == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.g.post(new u(this, str, str2, str3, str4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str, String str2, String str3, String str4) {
        try {
            synchronized (this.i) {
                com.xiaomi.onetrack.util.r.a(b, "addEvent->appId: " + str + ", eventName: " + str2);
                File databasePath = com.xiaomi.onetrack.f.a.a().getDatabasePath(this.i.getDatabaseName());
                if (databasePath != null && databasePath.length() > 104857600) {
                    com.xiaomi.onetrack.util.r.c(b, "DB size is " + databasePath.length() + " bytes, exceed max size!");
                    return;
                }
                SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("appid", str);
                contentValues.put("event_name", str2);
                contentValues.put("attribute", str4);
                contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
                byte[] bArrB = com.xiaomi.onetrack.d.a.b(str3);
                if (bArrB == null) {
                    com.xiaomi.onetrack.util.r.c(b, "encryptedBytes is null");
                    return;
                }
                contentValues.put("data", com.xiaomi.onetrack.d.c.a(bArrB));
                long jInsert = writableDatabase.insert("events", null, contentValues);
                com.xiaomi.onetrack.util.r.a(b, "doSaveCacheData , row=" + jInsert);
                if (jInsert != -1) {
                    if (com.xiaomi.onetrack.util.r.a) {
                        com.xiaomi.onetrack.util.r.a(b, "添加后，DB 中事件个数为 " + d());
                    }
                } else {
                    com.xiaomi.onetrack.util.r.c(b, "doSaveCacheData failed");
                }
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(b, "doSaveData exception: ", e2);
        }
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0135: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:62:0x0135 */
    public b a(String str) {
        Closeable closeable;
        b bVar;
        Cursor cursorQuery;
        Closeable closeable2;
        boolean z;
        synchronized (this.i) {
            try {
                try {
                    try {
                        com.xiaomi.onetrack.util.r.a(b, "getCacheEventsDataSync start");
                        cursorQuery = this.i.getReadableDatabase().query("events", new String[]{"_id", "event_name", "data", "attribute"}, "appid = ? ", new String[]{str}, null, null, j, String.valueOf(100));
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            int columnIndex2 = cursorQuery.getColumnIndex("event_name");
                            int columnIndex3 = cursorQuery.getColumnIndex("data");
                            int columnIndex4 = cursorQuery.getColumnIndex("attribute");
                            ArrayList arrayList = new ArrayList();
                            int length = 0;
                            while (true) {
                                if (!cursorQuery.moveToNext()) {
                                    bVar = null;
                                    break;
                                }
                                try {
                                    long j2 = cursorQuery.getLong(columnIndex);
                                    String string = cursorQuery.getString(columnIndex2);
                                    String string2 = cursorQuery.getString(columnIndex3);
                                    bVar = null;
                                    try {
                                        String string3 = cursorQuery.getString(columnIndex4);
                                        String strA = string2 != null ? com.xiaomi.onetrack.d.a.a(com.xiaomi.onetrack.d.c.a(string2)) : null;
                                        if (!TextUtils.isEmpty(strA)) {
                                            b.a aVar = new b.a();
                                            aVar.a = j2;
                                            aVar.b = string;
                                            String str2 = strA;
                                            aVar.c = str2;
                                            aVar.d = string3;
                                            arrayList.add(aVar);
                                            length += str2.length();
                                            if (length >= h) {
                                                com.xiaomi.onetrack.util.r.a(b, "reached max len: " + length);
                                                break;
                                            }
                                            continue;
                                            e = e;
                                            com.xiaomi.onetrack.util.r.b(b, "blob too big ***", e);
                                            f();
                                            com.xiaomi.onetrack.util.o.a(cursorQuery);
                                            return bVar;
                                        }
                                        continue;
                                    } catch (Throwable th) {
                                        th = th;
                                        try {
                                            com.xiaomi.onetrack.util.r.b(b, "Finally *** error ***", th);
                                        } catch (SQLiteBlobTooBigException e2) {
                                            e = e2;
                                        } catch (Exception e3) {
                                            e = e3;
                                            com.xiaomi.onetrack.util.r.b(b, "getEventsDataSync error", e);
                                            com.xiaomi.onetrack.util.o.a(cursorQuery);
                                            return bVar;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    bVar = null;
                                }
                            }
                            if (arrayList.size() > 0) {
                                if (!cursorQuery.isAfterLast()) {
                                    z = false;
                                } else if (arrayList.size() == 100) {
                                    com.xiaomi.onetrack.util.r.a(b, "reach max number per one query ");
                                    z = false;
                                } else {
                                    com.xiaomi.onetrack.util.r.a(b, "cursor isAfterLast");
                                    z = true;
                                }
                                b bVar2 = new b(arrayList, z);
                                com.xiaomi.onetrack.util.o.a(cursorQuery);
                                return bVar2;
                            }
                        } catch (SQLiteBlobTooBigException e4) {
                            e = e4;
                            bVar = null;
                        } catch (Exception e5) {
                            e = e5;
                            bVar = null;
                        }
                    } catch (SQLiteBlobTooBigException e6) {
                        e = e6;
                        bVar = null;
                        cursorQuery = null;
                    } catch (Exception e7) {
                        e = e7;
                        bVar = null;
                        cursorQuery = null;
                    } catch (Throwable th3) {
                        th = th3;
                        closeable = null;
                        com.xiaomi.onetrack.util.o.a(closeable);
                        throw th;
                    }
                    com.xiaomi.onetrack.util.o.a(cursorQuery);
                    return bVar;
                } catch (Throwable th4) {
                    th = th4;
                    closeable = closeable2;
                    com.xiaomi.onetrack.util.o.a(closeable);
                    throw th;
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
    }

    public synchronized void b(String str) {
        try {
            com.xiaomi.onetrack.util.r.a(b, "removeObsoleteFile->appId: " + str);
            File file = new File(e());
            if (file.exists() && file.isDirectory()) {
                String str2 = String.format(d, str);
                com.xiaomi.onetrack.util.r.a(b, "removeObsoleteFile->dirFile: " + file.getAbsolutePath() + ", fileName: " + str2);
                File[] fileArrListFiles = file.listFiles();
                for (int i = 0; i < fileArrListFiles.length; i++) {
                    if (fileArrListFiles[i].isFile() && fileArrListFiles[i].getName().equalsIgnoreCase(str2)) {
                        fileArrListFiles[i].delete();
                        break;
                    }
                }
            }
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(b, "systemimp removeObsoleteEvent error: " + e2.toString());
        }
    }

    public void c(String str) {
        if (this.g == null || TextUtils.isEmpty(str)) {
            com.xiaomi.onetrack.util.r.a(b, "trackPubSubSystemImpCache has id is null");
        } else {
            this.g.post(new v(this, str));
        }
    }

    public void b(String str, String str2, String str3, String str4) {
        if (this.g == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            com.xiaomi.onetrack.util.r.a(b, "trackPubSubSystemImpCache has id is null");
        } else {
            this.g.post(new w(this, str, str2, str3, str4));
        }
    }

    public synchronized void a(String str, String str2, String str3, String str4, boolean z) {
        boolean zA;
        try {
            ArrayList<Long> arrayList = new ArrayList<>();
            int i = 0;
            while (i <= 10000) {
                com.xiaomi.onetrack.util.r.a(b, "cycle start");
                arrayList.clear();
                b bVarA = a(str4);
                if (bVarA == null || bVarA.a == null || bVarA.a.size() == 0) {
                    com.xiaomi.onetrack.util.r.a(b, "满足条件的记录为空，即将返回, appId=" + str4);
                } else {
                    for (int i2 = 0; i2 < bVarA.a.size(); i2++) {
                        b.a aVar = bVarA.a.get(i2);
                        String str5 = aVar.b;
                        String str6 = aVar.c;
                        if (z) {
                            String strA = aVar.d;
                            if (TextUtils.isEmpty(strA)) {
                                strA = com.xiaomi.onetrack.util.a.a();
                            }
                            zA = az.a().a(str, str2, str3, str4, str5, str6, strA);
                        } else {
                            zA = az.a().a(str5, str6, str4);
                        }
                        if (!zA) {
                            com.xiaomi.onetrack.util.r.a(b, "Failed to trackEvent by binder, break!");
                            break;
                        }
                        arrayList.add(Long.valueOf(aVar.a));
                    }
                    if (a(arrayList) == 0) {
                        com.xiaomi.onetrack.util.r.b(b, "Delete DB failed!,eventIds.size:" + arrayList.size());
                    } else if (arrayList.size() != bVarA.a.size()) {
                        com.xiaomi.onetrack.util.r.c(b, "Failed to trackEvent by binder, exit cycle!");
                    } else if (bVarA.b) {
                        com.xiaomi.onetrack.util.r.a(b, "No more records for appId=" + str4);
                    } else {
                        i++;
                    }
                }
            }
            com.xiaomi.onetrack.util.r.a(b, "exceed max cycle count, maxCount: " + i);
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(b, "trackSystemImpCache error: " + e2.getMessage());
        }
    }

    public int a(ArrayList<Long> arrayList) {
        synchronized (this.i) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    try {
                        SQLiteDatabase writableDatabase = this.i.getWritableDatabase();
                        StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                        sb.append("_id").append(" in (");
                        sb.append(arrayList.get(0));
                        int size = arrayList.size();
                        for (int i = 1; i < size; i++) {
                            sb.append(",").append(arrayList.get(i));
                        }
                        sb.append(")");
                        int iDelete = writableDatabase.delete("events", sb.toString(), null);
                        com.xiaomi.onetrack.util.r.a(b, "*** *** deleted events count " + iDelete);
                        com.xiaomi.onetrack.util.r.a(b, "after delete DB record remains=" + d.a().d());
                        return iDelete;
                    } catch (Exception e2) {
                        com.xiaomi.onetrack.util.r.b(b, "deleteEventsSync error, e: ", e2);
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    public void c() {
        this.g.post(new x(this));
    }

    public long d() {
        try {
            return DatabaseUtils.queryNumEntries(this.i.getReadableDatabase(), "events");
        } catch (Exception e2) {
            com.xiaomi.onetrack.util.r.b(b, "getTotalCacheEventsNumberSync failed with " + e2.getMessage());
            return 0L;
        }
    }

    private void f() {
        try {
            this.i.getWritableDatabase().delete("events", null, null);
            com.xiaomi.onetrack.util.r.a(b, "delete table events");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class a extends SQLiteOpenHelper {
        public static final String a = "onetrack_cache";
        public static final String b = "events";
        public static final String c = "_id";
        public static final String d = "appid";
        public static final String e = "event_name";
        public static final String f = "data";
        public static final String g = "attribute";
        public static final String h = "timestamp";
        private static final int i = 1;
        private static final String j = "CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT,appid TEXT,event_name TEXT,data TEXT,attribute TEXT,timestamp INTEGER)";

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        }

        public a(Context context) {
            super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(j);
        }
    }
}
