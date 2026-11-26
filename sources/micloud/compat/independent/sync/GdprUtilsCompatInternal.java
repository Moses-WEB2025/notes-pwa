package micloud.compat.independent.sync;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/* JADX INFO: loaded from: classes5.dex */
public class GdprUtilsCompatInternal implements IGdprUtilsCompat {
    @Override // micloud.compat.independent.sync.IGdprUtilsCompat
    public void notifyPrivacyDenied(Context context) {
        Intent intent = new Intent("com.xiaomi.action.PRIVACY_DENIED");
        intent.setPackage("com.miui.cloudservice");
        if (context.getPackageManager().resolveService(intent, 0) != null) {
            context.startService(intent);
        } else {
            context.sendBroadcast(intent);
        }
    }

    @Override // micloud.compat.independent.sync.IGdprUtilsCompat
    public boolean isGdprPermissionGranted(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "micloud_gdpr_permission_granted", 1) != 0;
    }
}
