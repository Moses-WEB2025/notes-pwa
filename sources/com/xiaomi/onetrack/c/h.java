package com.xiaomi.onetrack.c;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

/* JADX INFO: loaded from: classes4.dex */
class h implements Runnable {
    final /* synthetic */ d a;

    h(d dVar) {
        this.a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.k == null) {
            return;
        }
        synchronized (this.a.k) {
            Cursor cursorQuery = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.k.getWritableDatabase();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(6, calendar.get(6) - 7);
                    calendar.set(11, 0);
                    calendar.set(12, 0);
                    calendar.set(13, 0);
                    String[] strArr = {Long.toString(calendar.getTimeInMillis())};
                    cursorQuery = writableDatabase.query("events", new String[]{"timestamp"}, "timestamp < ? ", strArr, null, null, "timestamp ASC");
                    if (cursorQuery.getCount() != 0) {
                        com.xiaomi.onetrack.util.r.a("EventManager", "*** deleted obsolete item count=" + writableDatabase.delete("events", "timestamp < ? ", strArr));
                    }
                    long jD = d.a().d();
                    com.xiaomi.onetrack.b.n.a(jD == 0);
                    com.xiaomi.onetrack.util.r.a("EventManager", "after delete obsolete record remains=" + jD);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Exception e) {
                    com.xiaomi.onetrack.util.r.d("EventManager", "remove obsolete events failed with " + e);
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
}
