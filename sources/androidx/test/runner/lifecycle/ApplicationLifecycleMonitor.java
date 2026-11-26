package androidx.test.runner.lifecycle;

/* JADX INFO: loaded from: classes.dex */
public interface ApplicationLifecycleMonitor {
    void addLifecycleCallback(ApplicationLifecycleCallback callback);

    void removeLifecycleCallback(ApplicationLifecycleCallback callback);
}
