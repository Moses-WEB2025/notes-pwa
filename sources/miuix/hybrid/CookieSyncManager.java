package miuix.hybrid;

import android.content.Context;
import miuix.internal.hybrid.provider.AbsCookieSyncManager;
import miuix.internal.hybrid.provider.WebViewFactory;

/* JADX INFO: loaded from: classes5.dex */
public final class CookieSyncManager {
    private static CookieSyncManager sRef;

    private CookieSyncManager() {
    }

    public static CookieSyncManager createInstance(Context context) {
        getCookieSyncManager().createInstance(context);
        return getInstance();
    }

    public static CookieSyncManager getInstance() {
        getCookieSyncManager().getInstance();
        if (sRef == null) {
            sRef = new CookieSyncManager();
        }
        return sRef;
    }

    public static void sync() {
        getCookieSyncManager().sync();
    }

    private static AbsCookieSyncManager getCookieSyncManager() {
        return WebViewFactory.getProvider(null).getCookieSyncManager();
    }
}
