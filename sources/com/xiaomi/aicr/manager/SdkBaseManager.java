package com.xiaomi.aicr.manager;

import android.os.Bundle;

/* JADX INFO: loaded from: classes4.dex */
public interface SdkBaseManager {
    int[] getAllCapabilityByDomainId(int i);

    int[] getAllSupportDomain();

    Bundle getSceneStatus(int i, int i2, int i3, Bundle bundle);

    void handleSceneMessage(int i, int i2, int i3, Bundle bundle);

    void notifySceneStatusChanged(Bundle bundle);

    void registerSceneStatusChanged(SdkSceneStatusCallback sdkSceneStatusCallback);

    void release();
}
