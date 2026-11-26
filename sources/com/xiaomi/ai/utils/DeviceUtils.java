package com.xiaomi.ai.utils;

import android.content.Context;
import com.xiaomi.ai.mibrain.MibrainUtils;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceUtils {
    public static String getDefaultState(Context context) {
        return MibrainUtils.sha1(("d=" + getDeviceId(context)).getBytes());
    }

    public static String getDefaultState(String str) {
        return MibrainUtils.sha1(("d=" + str).getBytes());
    }

    public static String getDeviceId(Context context) {
        return n.b(context);
    }

    public static String readDeviceId(Context context) {
        return n.a(context);
    }
}
