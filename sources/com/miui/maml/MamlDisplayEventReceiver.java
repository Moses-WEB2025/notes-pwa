package com.miui.maml;

import android.os.Looper;
import android.view.DisplayEventReceiver;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MamlDisplayEventReceiver extends DisplayEventReceiver {
    public abstract void onVsync(long j);

    public MamlDisplayEventReceiver(Looper looper) {
        super(looper);
    }

    public void onVsync(long j, long j2, int i) {
        onVsync(j);
    }

    public void onVsync(long j, int i, int i2) {
        onVsync(j);
    }

    public void onVsync(long j, long j2, int i, DisplayEventReceiver.VsyncEventData vsyncEventData) {
        onVsync(j);
    }
}
