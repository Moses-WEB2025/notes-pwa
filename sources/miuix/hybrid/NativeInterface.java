package miuix.hybrid;

import android.app.Activity;
import miuix.internal.hybrid.HybridManager;

/* JADX INFO: loaded from: classes5.dex */
public class NativeInterface {
    private HybridManager mManager;

    public NativeInterface(HybridManager hybridManager) {
        this.mManager = hybridManager;
    }

    public Activity getActivity() {
        return this.mManager.getActivity();
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        this.mManager.addLifecycleListener(lifecycleListener);
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        this.mManager.removeLifecycleListener(lifecycleListener);
    }
}
