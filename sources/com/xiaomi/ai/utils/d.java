package com.xiaomi.ai.utils;

import com.xiaomi.ai.mibrain.Mibrainsdk;

/* JADX INFO: loaded from: classes4.dex */
final class d implements Mibrainsdk.MibrainsdkLogHook {
    d() {
    }

    @Override // com.xiaomi.ai.mibrain.Mibrainsdk.MibrainsdkLogHook
    public void onLog(int i, String str) {
        if (i == Mibrainsdk.MIBRAIN_DEBUG_LEVEL_DEBUG) {
            Log.d("mibrainsdk-jni", str);
        } else if (i == Mibrainsdk.MIBRAIN_DEBUG_LEVEL_WARNING) {
            Log.w("mibrainsdk-jni", str);
        } else {
            Log.e("mibrainsdk-jni", str);
        }
    }
}
