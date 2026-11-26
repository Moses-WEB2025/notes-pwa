package com.xiaomi.onetrack.c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

/* JADX INFO: loaded from: classes4.dex */
class x implements Runnable {
    final /* synthetic */ s a;

    x(s sVar) {
        this.a = sVar;
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00a5 A[EXC_TOP_SPLITTER, PHI: r4
  0x00a5: PHI (r4v3 android.database.Cursor) = (r4v2 android.database.Cursor), (r4v4 android.database.Cursor) binds: [B:20:0x00c0, B:12:0x00a3] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    @Override // java.lang.Runnable
    public void run() {
        if (this.a.i == null) {
            return;
        }
        synchronized (this.a.i) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.i.getWritableDatabase();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(6, calendar.get(6) - 7);
                    calendar.set(11, 0);
                    calendar.set(12, 0);
                    calendar.set(13, 0);
                    String[] strArr = {Long.toString(calendar.getTimeInMillis())};
                    cursorQuery = writableDatabase.query("events", new String[]{"timestamp"}, "timestamp < ? ", strArr, null, null, "timestamp ASC");
                    if (cursorQuery.getCount() != 0) {
                        com.xiaomi.onetrack.util.r.a("SystemImpCacheManager", "*** deleted obsolete item count=" + writableDatabase.delete("events", "timestamp < ? ", strArr));
                    }
                    com.xiaomi.onetrack.util.r.a("SystemImpCacheManager", "after delete obsolete record remains=" + this.a.d());
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception unused) {
                        }
                    }
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.r.d("SystemImpCacheManager", "remove obsolete messages failed with " + e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                        throw th;
                    } catch (Exception unused2) {
                        throw th;
                    }
                }
                throw th;
            }
        }
    }
}
