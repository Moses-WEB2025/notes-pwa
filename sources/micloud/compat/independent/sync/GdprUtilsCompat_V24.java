package micloud.compat.independent.sync;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
class GdprUtilsCompat_V24 implements IGdprUtilsCompat {
    @Override // micloud.compat.independent.sync.IGdprUtilsCompat
    public boolean isGdprPermissionGranted(Context context) {
        return true;
    }

    @Override // micloud.compat.independent.sync.IGdprUtilsCompat
    public void notifyPrivacyDenied(Context context) {
    }

    GdprUtilsCompat_V24() {
    }
}
