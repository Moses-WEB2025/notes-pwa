package com.xiaomi.onetrack.b;

import android.database.Cursor;
import android.text.TextUtils;
import com.xiaomi.onetrack.util.r;
import java.util.concurrent.Callable;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class k implements Callable<l> {
    final /* synthetic */ String a;
    final /* synthetic */ h b;

    k(h hVar, String str) {
        this.b = hVar;
        this.a = str;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x009b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x008f A[EXC_TOP_SPLITTER, PHI: r12
  0x008f: PHI (r12v4 android.database.Cursor) = (r12v3 android.database.Cursor), (r12v6 android.database.Cursor) binds: [B:24:0x008d, B:15:0x007d] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public l call() throws Exception {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = this.b.b.getWritableDatabase().query(g.b, null, "app_id=?", new String[]{this.a}, null, null, null);
            try {
                try {
                    int columnIndex = cursorQuery.getColumnIndex(g.d);
                    int columnIndex2 = cursorQuery.getColumnIndex(g.e);
                    int columnIndex3 = cursorQuery.getColumnIndex(g.f);
                    int columnIndex4 = cursorQuery.getColumnIndex("timestamp");
                    if (cursorQuery.moveToNext()) {
                        l lVar = new l();
                        lVar.a = cursorQuery.getString(columnIndex);
                        String string = cursorQuery.getString(columnIndex2);
                        if (!TextUtils.isEmpty(string)) {
                            lVar.e = new JSONObject(string);
                        }
                        lVar.b = h.b(lVar.e);
                        lVar.d = cursorQuery.getString(columnIndex3);
                        lVar.c = cursorQuery.getLong(columnIndex4);
                        if (cursorQuery != null) {
                            try {
                                cursorQuery.close();
                                return lVar;
                            } catch (Exception unused) {
                                r.a("ConfigDbManager", "getConfig  cursor.close");
                            }
                        }
                        return lVar;
                    }
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception unused2) {
                            r.a("ConfigDbManager", "getConfig  cursor.close");
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        try {
                            cursor.close();
                        } catch (Exception unused3) {
                            r.a("ConfigDbManager", "getConfig  cursor.close");
                        }
                    }
                    throw th;
                }
            } catch (Exception e) {
                e = e;
                r.a("ConfigDbManager", e.getMessage());
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            }
        } catch (Exception e2) {
            e = e2;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return null;
    }
}
