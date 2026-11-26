package com.xiaomi.passport.accountmanager;

import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Handler;
import android.os.Looper;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.accountsdk.utils.PassportExecutors;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class XiaomiAccountManagerFuture<T> extends FutureTask<T> implements AccountManagerFuture<T> {
    public static final String TAG = "XiaomiAccountManagerFuture";
    private static volatile Executor sExecutor = PassportExecutors.newFixedThreadPool(3, TAG);
    final XiaomiAccountManagerCallback<T> mCallback;
    final Handler mHandler;

    public abstract T doWork() throws Exception;

    public XiaomiAccountManagerFuture(XiaomiAccountManagerCallback<T> xiaomiAccountManagerCallback, Handler handler) {
        super(new Callable<T>() { // from class: com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture.1
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                throw new IllegalStateException("this should never be called");
            }
        });
        this.mHandler = handler;
        this.mCallback = xiaomiAccountManagerCallback;
    }

    @Override // java.util.concurrent.FutureTask
    protected void set(T t) {
        if (t == null) {
            AccountLogger.log(TAG, "the bundle must not be null", new Exception());
        }
        super.set(t);
    }

    public void work() {
        try {
            set(doWork());
        } catch (Exception e) {
            setException(e);
        }
    }

    public XiaomiAccountManagerFuture<T> start() {
        sExecutor.execute(new Runnable() { // from class: com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture.2
            @Override // java.lang.Runnable
            public void run() {
                XiaomiAccountManagerFuture.this.work();
            }
        });
        return this;
    }

    private T internalGetResult(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
        Looper looperMyLooper;
        if (!isDone() && (looperMyLooper = Looper.myLooper()) != null && looperMyLooper == Looper.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
        try {
            try {
                try {
                    if (l == null) {
                        T t = get();
                        cancel(true);
                        return t;
                    }
                    T t2 = get(l.longValue(), timeUnit);
                    cancel(true);
                    return t2;
                } catch (CancellationException unused) {
                    throw new OperationCanceledException();
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof IOException) {
                        throw ((IOException) cause);
                    }
                    if (cause instanceof UnsupportedOperationException) {
                        throw new AuthenticatorException(cause);
                    }
                    if (cause instanceof AuthenticatorException) {
                        throw ((AuthenticatorException) cause);
                    }
                    if (cause instanceof RuntimeException) {
                        throw ((RuntimeException) cause);
                    }
                    if (cause instanceof Error) {
                        throw ((Error) cause);
                    }
                    throw new IllegalStateException(cause);
                }
            } catch (InterruptedException | TimeoutException unused2) {
                cancel(true);
                throw new OperationCanceledException();
            }
        } catch (Throwable th) {
            cancel(true);
            throw th;
        }
    }

    @Override // android.accounts.AccountManagerFuture
    public T getResult() throws OperationCanceledException, IOException, AuthenticatorException {
        return internalGetResult(null, null);
    }

    @Override // android.accounts.AccountManagerFuture
    public T getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
        return internalGetResult(Long.valueOf(j), timeUnit);
    }

    @Override // java.util.concurrent.FutureTask
    protected void done() {
        XiaomiAccountManagerCallback<T> xiaomiAccountManagerCallback = this.mCallback;
        if (xiaomiAccountManagerCallback != null) {
            postToHandler(this.mHandler, xiaomiAccountManagerCallback, this);
        }
    }

    private void postToHandler(Handler handler, final XiaomiAccountManagerCallback<T> xiaomiAccountManagerCallback, final XiaomiAccountManagerFuture<T> xiaomiAccountManagerFuture) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new Runnable() { // from class: com.xiaomi.passport.accountmanager.XiaomiAccountManagerFuture.3
            @Override // java.lang.Runnable
            public void run() {
                xiaomiAccountManagerCallback.run(xiaomiAccountManagerFuture);
            }
        });
    }
}
