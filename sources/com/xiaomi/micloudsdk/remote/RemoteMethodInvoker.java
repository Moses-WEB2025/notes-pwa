package com.xiaomi.micloudsdk.remote;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.micloudsdk.utils.ThreadUtil;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RemoteMethodInvoker<R> implements ServiceConnection {
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_TIMEOUT_SECONDS = 5;
    private static final String TAG = "RemoteMethodInvoker";
    private final AsyncFuture<IBinder> future = new AsyncFuture<>();
    private final Context mContext;

    protected abstract boolean bindService(Context context, ServiceConnection serviceConnection);

    protected abstract R invokeRemoteMethod(IBinder iBinder) throws RemoteException;

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }

    public RemoteMethodInvoker(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        this.mContext = context.getApplicationContext();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.i(TAG, "RemoteMethodInvoker connects remote service " + componentName.getShortClassName());
        this.future.setResult(iBinder);
    }

    public R invoke() {
        ThreadUtil.ensureWorkerThread();
        R rInvokeRemoteMethod = null;
        if (bindService(this.mContext, this)) {
            for (int i = 3; i > 0; i--) {
                try {
                    rInvokeRemoteMethod = invokeRemoteMethod(this.future.get(5L, TimeUnit.SECONDS));
                    break;
                } catch (RemoteException e) {
                    Log.e(TAG, "invoke service method error - remaining retry count : " + i + ", exception : " + e);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (ExecutionException unused2) {
                    Log.e(TAG, "invoke service method occurs execution error - remaining retry count : " + i);
                } catch (TimeoutException unused3) {
                    Log.e(TAG, "invoke service method time out - remaining retry count : " + i);
                }
            }
            try {
                this.mContext.unbindService(this);
            } catch (NoSuchElementException unused4) {
            }
            return rInvokeRemoteMethod;
        }
        Log.e(TAG, "Cannot bind remote service.");
        return null;
    }

    private static class AsyncFuture<V> extends FutureTask<V> {
        public AsyncFuture() {
            super(new Callable<V>() { // from class: com.xiaomi.micloudsdk.remote.RemoteMethodInvoker.AsyncFuture.1
                @Override // java.util.concurrent.Callable
                public V call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
        }

        public void setResult(V v) {
            set(v);
        }
    }
}
