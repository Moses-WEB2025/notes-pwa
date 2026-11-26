package com.xiaomi.aiinput;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import miuix.android.content.MiuiIntent;

/* JADX INFO: loaded from: classes4.dex */
public class XiaoaiLauncher {
    private static final String LAUNCH_AI_KEY = "voice_assist_start_from_key";
    private static final String LAUNCH_AI_VALUE = "system_input_method";
    private static final String XIAOAI_PKG_NAME = "com.miui.voiceassist";
    private static final String XIAOAI_Service_NAME = "com.xiaomi.voiceassistant.VoiceService";

    public static boolean launch(Context context) {
        if (context == null) {
            Log.e("XiaoaiLauncher", "launch error: context is null");
            return false;
        }
        Intent intent = new Intent(MiuiIntent.ACTION_ASSIST);
        intent.putExtra(LAUNCH_AI_KEY, LAUNCH_AI_VALUE);
        intent.setClassName(XIAOAI_PKG_NAME, XIAOAI_Service_NAME);
        context.startForegroundService(intent);
        return true;
    }
}
