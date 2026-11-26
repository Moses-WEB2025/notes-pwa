package androidx.test.runner.lifecycle;

import android.app.Application;

/* JADX INFO: loaded from: classes.dex */
public interface ApplicationLifecycleCallback {
    void onApplicationLifecycleChanged(Application app, ApplicationStage stage);
}
