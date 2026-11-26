package miuix.internal.hybrid.provider;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbsCookieSyncManager {
    public abstract void createInstance(Context context);

    public abstract void getInstance();

    public abstract void sync();
}
