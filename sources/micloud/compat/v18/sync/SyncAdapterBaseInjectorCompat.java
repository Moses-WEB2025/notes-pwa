package micloud.compat.v18.sync;

import android.content.Context;
import android.content.SyncResult;
import com.xiaomi.micloudsdk.utils.MiCloudSdkBuild;
import micloud.compat.independent.sync.GdprUtilsCompat;

/* JADX INFO: loaded from: classes5.dex */
public class SyncAdapterBaseInjectorCompat {
    private static final ISyncAdapterBaseInjectorCompat sSyncAdapterBaseInjectorImpl;

    static {
        if (MiCloudSdkBuild.CURRENT_VERSION >= 23) {
            sSyncAdapterBaseInjectorImpl = new SyncAdapterBaseInjectorCompat_V23();
        } else {
            sSyncAdapterBaseInjectorImpl = new SyncAdapterBaseInjectorCompat_Base();
        }
    }

    public static boolean isGdprPermissionGranted(Context context, SyncResult syncResult) {
        boolean zIsGdprPermissionGranted = GdprUtilsCompat.isGdprPermissionGranted(context);
        sSyncAdapterBaseInjectorImpl.setResultByGDPRStatus(zIsGdprPermissionGranted, syncResult);
        return zIsGdprPermissionGranted;
    }
}
