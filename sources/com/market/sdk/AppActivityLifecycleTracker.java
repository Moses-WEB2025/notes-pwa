package com.market.sdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* JADX INFO: loaded from: classes2.dex */
public class AppActivityLifecycleTracker implements Application.ActivityLifecycleCallbacks {
    private static final int ACTIVITY_CREATED = 0;
    private static final int ACTIVITY_DESTROYED = 5;
    private static final int ACTIVITY_PAUSED = 3;
    private static final int ACTIVITY_RESUMED = 2;
    private static final int ACTIVITY_SAVE_INSTANCE = 6;
    private static final int ACTIVITY_STARTED = 1;
    private static final int ACTIVITY_STOPED = 4;
    private FloatCardManager floatCardManager = MarketManager.getManager().getFloatCardManager();

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.floatCardManager.lifecycleChanged(activity, 0);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.floatCardManager.lifecycleChanged(activity, 1);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.floatCardManager.lifecycleChanged(activity, 2);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.floatCardManager.lifecycleChanged(activity, 3);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        this.floatCardManager.lifecycleChanged(activity, 4);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.floatCardManager.lifecycleChanged(activity, 6);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        this.floatCardManager.lifecycleChanged(activity, 5);
    }
}
