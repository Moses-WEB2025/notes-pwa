package micloud.compat.independent.sync;

import android.content.Context;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes5.dex */
public interface IGdprUtilsCompat {
    boolean isGdprPermissionGranted(Context context);

    void notifyPrivacyDenied(Context context);
}
