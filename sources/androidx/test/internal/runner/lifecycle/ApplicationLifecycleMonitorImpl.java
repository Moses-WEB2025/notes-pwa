package androidx.test.internal.runner.lifecycle;

import android.app.Application;
import android.util.Log;
import androidx.test.internal.util.Checks;
import androidx.test.runner.lifecycle.ApplicationLifecycleCallback;
import androidx.test.runner.lifecycle.ApplicationLifecycleMonitor;
import androidx.test.runner.lifecycle.ApplicationStage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationLifecycleMonitorImpl implements ApplicationLifecycleMonitor {
    private static final String TAG = "ApplicationLifecycleMonitorImpl";
    private final List<WeakReference<ApplicationLifecycleCallback>> callbacks = new ArrayList();

    @Override // androidx.test.runner.lifecycle.ApplicationLifecycleMonitor
    public void addLifecycleCallback(ApplicationLifecycleCallback callback) {
        Checks.checkNotNull(callback);
        synchronized (this.callbacks) {
            Iterator<WeakReference<ApplicationLifecycleCallback>> it = this.callbacks.iterator();
            boolean z = true;
            while (it.hasNext()) {
                ApplicationLifecycleCallback applicationLifecycleCallback = it.next().get();
                if (applicationLifecycleCallback == null) {
                    it.remove();
                } else if (applicationLifecycleCallback == callback) {
                    z = false;
                }
            }
            if (z) {
                this.callbacks.add(new WeakReference<>(callback));
            }
        }
    }

    @Override // androidx.test.runner.lifecycle.ApplicationLifecycleMonitor
    public void removeLifecycleCallback(ApplicationLifecycleCallback callback) {
        Checks.checkNotNull(callback);
        synchronized (this.callbacks) {
            Iterator<WeakReference<ApplicationLifecycleCallback>> it = this.callbacks.iterator();
            while (it.hasNext()) {
                ApplicationLifecycleCallback applicationLifecycleCallback = it.next().get();
                if (applicationLifecycleCallback == null) {
                    it.remove();
                } else if (applicationLifecycleCallback == callback) {
                    it.remove();
                }
            }
        }
    }

    public void signalLifecycleChange(Application app, ApplicationStage stage) {
        synchronized (this.callbacks) {
            Iterator<WeakReference<ApplicationLifecycleCallback>> it = this.callbacks.iterator();
            while (it.hasNext()) {
                ApplicationLifecycleCallback applicationLifecycleCallback = it.next().get();
                if (applicationLifecycleCallback == null) {
                    it.remove();
                } else {
                    try {
                        applicationLifecycleCallback.onApplicationLifecycleChanged(app, stage);
                    } catch (RuntimeException e) {
                        Log.e(TAG, String.format("Callback threw exception! (callback: %s stage: %s)", applicationLifecycleCallback, stage), e);
                    }
                }
            }
        }
    }
}
