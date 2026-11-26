package com.xiaomi.onetrack.util.oaid;

import android.content.Context;
import android.os.SystemClock;
import com.miui.hybirdeditor.EmptyOverwriteGuard;
import com.xiaomi.onetrack.util.oaid.helpers.b;
import com.xiaomi.onetrack.util.oaid.helpers.h;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.r;
import com.xiaomi.onetrack.util.s;
import com.xiaomi.onetrack.util.y;

/* JADX INFO: loaded from: classes4.dex */
public class a {
    private static final String a = "a";
    private static volatile a b;
    private static long d;
    private volatile String c = "";
    private final int e = 3;
    private volatile int f = 0;
    private boolean g = false;

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    public void b() {
        this.f = 0;
    }

    private boolean d() {
        if (this.f > 3) {
            return true;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long jAbs = Math.abs(jElapsedRealtime - d);
        if (this.f == 1) {
            if (jAbs < EmptyOverwriteGuard.USER_EDIT_VALID_WINDOW_MS) {
                return true;
            }
        } else if (this.f == 2) {
            if (jAbs < 10000) {
                return true;
            }
        } else if (this.f == 3 && jAbs < 60000) {
            return true;
        }
        r.a(a, "get time：" + this.f);
        d = jElapsedRealtime;
        return false;
    }

    public void a(boolean z) {
        this.g = z;
        r.a(a, "setCloseOaidDependMsaSDK：" + this.g);
    }

    public boolean c() {
        return (this.c == null || this.c.equals("")) ? false : true;
    }

    public String a(Context context) {
        String strA;
        synchronized (this.c) {
            if (y.a()) {
                if (r.a) {
                    throw new IllegalStateException("Don't use it on the main thread");
                }
                r.b(a, "getOaid() throw exception : Don't use it on the main thread");
                return "";
            }
            if (this.c != null && !this.c.equals("")) {
                return this.c;
            }
            if (d()) {
                r.a(a, "isNotAllowedGetOaid");
                return this.c;
            }
            if (s.b()) {
                this.c = p.a(context);
                this.f++;
                return this.c;
            }
            if (!this.g && (strA = new h().a(context)) != null && !strA.equals("")) {
                this.c = strA;
                this.f++;
                return strA;
            }
            String strA2 = new b().a(context);
            if (strA2 != null && !strA2.equals("")) {
                this.c = strA2;
                this.f++;
                return strA2;
            }
            this.f++;
            return this.c;
        }
    }
}
