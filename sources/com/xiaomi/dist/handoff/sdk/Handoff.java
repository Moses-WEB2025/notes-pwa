package com.xiaomi.dist.handoff.sdk;

import android.app.Activity;
import android.content.Context;
import ho.e;
import ho.j;

/* JADX INFO: loaded from: classes4.dex */
public final class Handoff {
    public static HandoffSession.Builder from(Activity activity) {
        j.a("Handoff", "from:%s", activity.getClass().getName());
        return new HandoffSession.Builder(activity);
    }

    public static boolean isSupport(Context context) {
        boolean zB = e.b(context);
        j.a("Handoff", "isSupport=%s", Boolean.valueOf(zB));
        return zB;
    }
}
