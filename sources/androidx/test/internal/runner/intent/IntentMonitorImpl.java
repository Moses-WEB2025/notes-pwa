package androidx.test.internal.runner.intent;

import android.content.Intent;
import android.util.Log;
import androidx.test.runner.intent.IntentCallback;
import androidx.test.runner.intent.IntentMonitor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class IntentMonitorImpl implements IntentMonitor {
    private static final String TAG = "IntentMonitorImpl";
    private final List<WeakReference<IntentCallback>> callbacks = new ArrayList();

    @Override // androidx.test.runner.intent.IntentMonitor
    public void addIntentCallback(IntentCallback callback) {
        if (callback == null) {
            throw new NullPointerException("callback cannot be null!");
        }
        synchronized (this.callbacks) {
            Iterator<WeakReference<IntentCallback>> it = this.callbacks.iterator();
            boolean z = true;
            while (it.hasNext()) {
                IntentCallback intentCallback = it.next().get();
                if (intentCallback == null) {
                    it.remove();
                } else if (intentCallback == callback) {
                    z = false;
                }
            }
            if (z) {
                this.callbacks.add(new WeakReference<>(callback));
            }
        }
    }

    @Override // androidx.test.runner.intent.IntentMonitor
    public void removeIntentCallback(IntentCallback callback) {
        if (callback == null) {
            throw new NullPointerException("callback cannot be null!");
        }
        synchronized (this.callbacks) {
            Iterator<WeakReference<IntentCallback>> it = this.callbacks.iterator();
            while (it.hasNext()) {
                IntentCallback intentCallback = it.next().get();
                if (intentCallback == null) {
                    it.remove();
                } else if (intentCallback == callback) {
                    it.remove();
                }
            }
        }
    }

    public void signalIntent(Intent intent) {
        synchronized (this.callbacks) {
            Iterator<WeakReference<IntentCallback>> it = this.callbacks.iterator();
            while (it.hasNext()) {
                IntentCallback intentCallback = it.next().get();
                if (intentCallback == null) {
                    it.remove();
                } else {
                    try {
                        intentCallback.onIntentSent(new Intent(intent));
                    } catch (RuntimeException e) {
                        Log.e(TAG, String.format("Callback threw exception! (callback: %s intent: %s)", intentCallback, intent), e);
                    }
                }
            }
        }
    }
}
