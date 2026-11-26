package miuix.internal.hybrid.webkit;

import android.content.Context;
import android.webkit.CookieSyncManager;
import miuix.internal.hybrid.provider.AbsCookieSyncManager;

/* JADX INFO: loaded from: classes5.dex */
public class CookieSyncManagerDelegate extends AbsCookieSyncManager {
    @Override // miuix.internal.hybrid.provider.AbsCookieSyncManager
    public void getInstance() {
        CookieSyncManager.getInstance();
    }

    @Override // miuix.internal.hybrid.provider.AbsCookieSyncManager
    public void createInstance(Context context) {
        CookieSyncManager.createInstance(context);
    }

    @Override // miuix.internal.hybrid.provider.AbsCookieSyncManager
    public void sync() {
        CookieSyncManager.getInstance().sync();
    }
}
