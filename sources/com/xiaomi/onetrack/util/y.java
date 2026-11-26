package com.xiaomi.onetrack.util;

import android.os.Looper;

/* JADX INFO: loaded from: classes4.dex */
public class y {
    private static final String a = "ProcessUtil";

    public static boolean a() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
