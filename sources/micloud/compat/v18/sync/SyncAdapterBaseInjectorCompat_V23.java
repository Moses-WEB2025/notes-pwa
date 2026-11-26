package micloud.compat.v18.sync;

import android.content.SyncResult;

/* JADX INFO: loaded from: classes5.dex */
class SyncAdapterBaseInjectorCompat_V23 extends SyncAdapterBaseInjectorCompat_Base {
    SyncAdapterBaseInjectorCompat_V23() {
    }

    @Override // micloud.compat.v18.sync.SyncAdapterBaseInjectorCompat_Base, micloud.compat.v18.sync.ISyncAdapterBaseInjectorCompat
    public void setResultByGDPRStatus(boolean z, SyncResult syncResult) {
        if (z) {
            return;
        }
        syncResult.stats.numAuthExceptions++;
    }
}
