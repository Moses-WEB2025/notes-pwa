package com.xiaomi.onetrack.api;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import com.xiaomi.onetrack.util.DeviceUtil;

/* JADX INFO: loaded from: classes4.dex */
class i implements Application.ActivityLifecycleCallbacks {
    final /* synthetic */ c a;
    private int b = 0;
    private int c;
    private long d;
    private boolean e;
    private boolean f;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    i(c cVar) {
        this.a = cVar;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        if (this.b == 0) {
            this.a.i().a(1);
            this.e = true;
            this.f = false;
            DeviceUtil.a();
        } else {
            this.e = false;
        }
        this.b++;
        com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "onActivityStarted: " + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.a.e(this.f);
        this.c = System.identityHashCode(activity);
        this.d = SystemClock.elapsedRealtime();
        this.a.a(activity.getClass().getName(), this.e);
        if (com.xiaomi.onetrack.util.r.a) {
            com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "onActivityResumed:" + activity.getLocalClassName() + " isAppStart:" + this.e);
        }
        this.e = false;
        this.a.l();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.a.a(activity.getClass().getName(), this.c == System.identityHashCode(activity) ? SystemClock.elapsedRealtime() - this.d : 0L);
        if (com.xiaomi.onetrack.util.r.a) {
            com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "onActivityPaused:" + activity.getLocalClassName());
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        int i = this.b - 1;
        this.b = i;
        if (i == 0) {
            this.a.i().a(2);
            this.a.n();
            this.f = true;
            this.e = false;
        } else {
            this.f = false;
        }
        this.a.e(this.f);
        com.xiaomi.onetrack.util.r.a("BaseOneTrackImp", "onActivityStopped: " + activity.getLocalClassName());
    }
}
