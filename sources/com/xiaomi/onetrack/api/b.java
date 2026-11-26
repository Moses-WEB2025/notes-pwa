package com.xiaomi.onetrack.api;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes4.dex */
class b implements Callable<String> {
    final /* synthetic */ Intent a;
    final /* synthetic */ a b;

    b(a aVar, Intent intent) {
        this.b = aVar;
        this.a = intent;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:33:0x00f1  */
    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public String call() throws Exception {
        Cursor cursorQuery;
        String string = "";
        try {
            try {
                if (!a.f()) {
                    return "";
                }
                String stringExtra = this.a.getStringExtra("package");
                String stringExtra2 = this.a.getStringExtra("installer");
                String stringExtra3 = this.a.getStringExtra("miuiActiveId");
                long longExtra = this.a.getLongExtra("miuiActiveTime", -1L);
                long longExtra2 = this.a.getLongExtra("activeTime", -1L);
                int intExtra = this.a.getIntExtra("userId", -1);
                String strValueOf = String.valueOf(System.currentTimeMillis());
                try {
                    Uri.Builder builderBuildUpon = Uri.parse("content://com.miui.analytics.OneTrackProvider/traceId").buildUpon();
                    builderBuildUpon.appendQueryParameter("pkg", a.j);
                    builderBuildUpon.appendQueryParameter("sign", com.xiaomi.onetrack.d.a.a("traceId" + a.j + strValueOf));
                    builderBuildUpon.appendQueryParameter("package", stringExtra);
                    builderBuildUpon.appendQueryParameter("installer", stringExtra2);
                    builderBuildUpon.appendQueryParameter("miuiActiveId", stringExtra3);
                    builderBuildUpon.appendQueryParameter("miuiActiveTime", String.valueOf(longExtra));
                    builderBuildUpon.appendQueryParameter("activeTime", String.valueOf(longExtra2));
                    builderBuildUpon.appendQueryParameter("userId", String.valueOf(intExtra));
                    builderBuildUpon.appendQueryParameter("queryTime", strValueOf);
                    cursorQuery = this.b.i.getContentResolver().query(builderBuildUpon.build(), null, null, null, null);
                    string = "";
                    if (cursorQuery != null) {
                        while (cursorQuery.moveToNext()) {
                            try {
                                try {
                                    string = cursorQuery.getString(0);
                                } catch (Throwable th) {
                                    th = th;
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    throw th;
                                }
                            } catch (Exception e) {
                                e = e;
                                com.xiaomi.onetrack.util.r.b("AppActiveBroadcastManager", "exception while getTraceId", e);
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                return string;
                            }
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return string;
                } catch (Exception e2) {
                    e = e2;
                    string = "";
                    cursorQuery = null;
                }
            } catch (Throwable th2) {
                th = th2;
                cursorQuery = null;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
        }
        cursorQuery = null;
        com.xiaomi.onetrack.util.r.b("AppActiveBroadcastManager", "exception while getTraceId", e);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return string;
    }
}
