package com.xiaomi.passport.uicontroller;

import android.os.Handler;
import android.os.Looper;
import com.xiaomi.accountsdk.account.utils.ReferenceHolder;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes4.dex */
public class SimpleFutureTask<T> extends FutureTask<T> {
    private final ReferenceHolder<Callback<T>> mCallbackRef;

    public static abstract class Callback<T> {
        public abstract void call(SimpleFutureTask<T> simpleFutureTask);
    }

    public SimpleFutureTask(Callable<T> callable, Callback<T> callback) {
        super(callable);
        this.mCallbackRef = new ReferenceHolder<>(callback);
    }

    @Override // java.util.concurrent.FutureTask
    protected void done() {
        if (!isCancelled()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaomi.passport.uicontroller.SimpleFutureTask.1
                @Override // java.lang.Runnable
                public void run() {
                    Callback callback = (Callback) SimpleFutureTask.this.mCallbackRef.get();
                    if (callback != null) {
                        callback.call(SimpleFutureTask.this);
                    }
                }
            });
        }
        super.done();
    }

    @Override // java.util.concurrent.FutureTask, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        this.mCallbackRef.set(null);
        return super.cancel(z);
    }
}
