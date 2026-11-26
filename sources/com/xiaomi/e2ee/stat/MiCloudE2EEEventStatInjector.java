package com.xiaomi.e2ee.stat;

import com.xiaomi.e2ee.E2EEException;

/* JADX INFO: loaded from: classes4.dex */
public class MiCloudE2EEEventStatInjector {
    private static final MiCloudE2EEEventStatInjector instance = new MiCloudE2EEEventStatInjector();
    private IMiCloudE2EEEventStatCallback mIMiCloudE2EEEVentStatCallback;

    public static MiCloudE2EEEventStatInjector getInstance() {
        return instance;
    }

    public void init(IMiCloudE2EEEventStatCallback iMiCloudE2EEEventStatCallback) {
        this.mIMiCloudE2EEEVentStatCallback = iMiCloudE2EEEventStatCallback;
    }

    public void addDecodeDataErrorEvent(E2EEException e2EEException) {
        IMiCloudE2EEEventStatCallback iMiCloudE2EEEventStatCallback = this.mIMiCloudE2EEEVentStatCallback;
        if (iMiCloudE2EEEventStatCallback != null) {
            iMiCloudE2EEEventStatCallback.onDecodeDataErrorEvent(e2EEException);
        }
    }

    private MiCloudE2EEEventStatInjector() {
    }
}
