package com.xiaomi.aicr.manager;

import android.os.Bundle;

/* JADX INFO: loaded from: classes4.dex */
public interface SdkMainController {
    int algoListen(int i, int i2, int i3, Bundle bundle);

    int algoUnListen(int i, int i2, int i3, Bundle bundle);

    Bundle getSceneMessage(int i, int i2, int i3, Bundle bundle);

    void initPush();

    int setSceneMessage(int i, int i2, int i3, Bundle bundle);
}
