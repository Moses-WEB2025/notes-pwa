package com.xiaomi.onetrack.api;

import android.content.Context;
import com.xiaomi.onetrack.Configuration;

/* JADX INFO: loaded from: classes4.dex */
public class an extends c {
    public static volatile boolean k = false;
    private static final String l = "OneTrackImp";

    public an(Context context, Configuration configuration) {
        super(context, configuration);
    }

    @Override // com.xiaomi.onetrack.api.c
    protected void a(Context context) {
        if (com.xiaomi.onetrack.util.s.b(null)) {
            k = true;
            if (com.xiaomi.onetrack.util.s.b() && j() && a()) {
                this.a = new aq(this.f, this.i);
            } else {
                this.a = new ao(context, this.f, this.i);
            }
            ad.a().a(this.a);
            return;
        }
        k = false;
        if (com.xiaomi.onetrack.util.s.b() && l() && a()) {
            this.b = new at(this.f, this.i);
        } else {
            this.b = new aw(this.f, this.i);
        }
        ad.a().a(this.b);
    }

    private boolean l() {
        if (com.xiaomi.onetrack.util.r.a) {
            com.xiaomi.onetrack.util.r.a(l, "enable:" + k() + " isSupportPBSystem: " + m());
        }
        return k() && m();
    }

    private static boolean m() {
        try {
            int i = com.xiaomi.onetrack.f.a.b().getPackageManager().getPackageInfo(az.a, 0).versionCode;
            if (i >= 2024040800) {
                return true;
            }
            com.xiaomi.onetrack.util.r.a(l, "system analytics version: " + i);
        } catch (Throwable th) {
            com.xiaomi.onetrack.util.r.b(l, "isSupportEmptyEvent error:" + th.getMessage());
        }
        return false;
    }

    @Override // com.xiaomi.onetrack.api.c
    protected ak i() {
        if (k) {
            if (this.a == null) {
                a(this.d);
            }
            com.xiaomi.onetrack.util.r.a(l, "getTrackImp mIOneTrack" + this.a);
            return this.a;
        }
        if (this.b == null) {
            a(this.d);
        }
        com.xiaomi.onetrack.util.r.a(l, "getTrackImp mIPubSubTrack" + this.b);
        return this.b;
    }

    public static void a(String str, String str2) {
        boolean zB = com.xiaomi.onetrack.util.s.b(str2);
        if (k != zB) {
            k = zB;
            com.xiaomi.onetrack.util.r.a(l, "channel changed to ".concat(k ? "OneTrack" : "PubSub"));
        }
        com.xiaomi.onetrack.c.z.a().a(str, zB);
    }
}
