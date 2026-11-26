package com.xiaomi.xms.ai.recorder.callback;

import android.content.ComponentName;
import android.os.IBinder;

/* JADX INFO: loaded from: classes4.dex */
public interface AIRecorderServiceStateCallback {
    void onServiceConnected(ComponentName componentName, IBinder iBinder);

    void onServiceDisconnected(ComponentName componentName);
}
