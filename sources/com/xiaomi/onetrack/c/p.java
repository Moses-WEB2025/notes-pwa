package com.xiaomi.onetrack.c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

/* JADX INFO: loaded from: classes4.dex */
class p implements Runnable {
    final /* synthetic */ n a;

    p(n nVar) {
        this.a = nVar;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00b2 A[EXC_TOP_SPLITTER, PHI: r4
  0x00b2: PHI (r4v3 android.database.Cursor) = (r4v1 android.database.Cursor), (r4v4 android.database.Cursor) binds: [B:23:0x00cd, B:15:0x00b0] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    @Override // java.lang.Runnable
    public void run() {
        if (this.a.c == null) {
            return;
        }
        synchronized (this.a.c) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.c.getWritableDatabase();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(6, calendar.get(6) - 7);
                    calendar.set(11, 0);
                    calendar.set(12, 0);
                    calendar.set(13, 0);
                    String[] strArr = {Long.toString(calendar.getTimeInMillis())};
                    cursorQuery = writableDatabase.query(n.a.b, new String[]{"timestamp"}, "timestamp < ? ", strArr, null, null, "timestamp ASC");
                    if (cursorQuery.getCount() != 0) {
                        com.xiaomi.onetrack.util.r.a("PubSubEventManager", "*** deleted obsolete item count=" + writableDatabase.delete(n.a.b, "timestamp < ? ", strArr));
                    }
                    long jF = n.a().f();
                    this.a.a(jF == 0);
                    com.xiaomi.onetrack.util.r.a("PubSubEventManager", "after delete obsolete record remains=" + jF);
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception unused) {
                        }
                    }
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.r.d("PubSubEventManager", "remove obsolete messages failed with " + e);
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
