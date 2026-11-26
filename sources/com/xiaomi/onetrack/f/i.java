package com.xiaomi.onetrack.f;

import android.content.Context;

/* JADX INFO: loaded from: classes4.dex */
public class i {
    private static final String a = "OneTrackApp";
    private static i b;

    public static void a(Context context) {
        if (b == null) {
            b = new i(context);
        }
    }

    private i(Context context) {
        com.xiaomi.onetrack.util.j.a(new j(this, context.getApplicationContext()));
    }
}
