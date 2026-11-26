package com.miui.floatwindow.utils;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes2.dex */
public class GestureBroadcastUtils {
    public static final String FSG_STATE_CHANGE_ACTION = "com.android.systemui.fsgesture";
    public static final String IS_ENTER = "isEnter";
    public static final String TYPE_FROM = "typeFrom";
    public static final String TYPE_FROM_TASK_TOOLBOX = "typefrom_task_toolbox";

    public static void sendBroadcast(Context context, boolean z) {
        sendBroadcast(context, z, TYPE_FROM_TASK_TOOLBOX);
    }

    public static void sendBroadcast(Context context, boolean z, String str) {
        Intent intent = new Intent();
        intent.setAction("com.android.systemui.fsgesture");
        intent.putExtra(TYPE_FROM, str);
        intent.putExtra(IS_ENTER, z);
        context.sendBroadcast(intent, "miui.permission.USE_INTERNAL_GENERAL_API");
    }
}
