package com.market;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.market.sdk.ThreadExecutors;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ServiceProxy {
    private static final boolean DEBUG_PROXY = false;
    public static final String EXTRA_FORCE_SHUTDOWN = "ServiceProxy.FORCE_SHUTDOWN";
    private final Context mContext;
    protected final Intent mIntent;
    private Executor mServiceExecutor;
    private long mStartTime;
    private ProxyTask mTask;
    private String mName = " unnamed";
    private final ServiceConnection mConnection = new ProxyConnection();
    private int mTimeout = 45;
    private boolean mTaskSet = false;
    private boolean mTaskCompleted = false;
    protected final String mTag = getClass().getSimpleName();

    public interface ProxyTask {
        void run() throws RemoteException;
    }

    public abstract void onConnected(IBinder iBinder);

    public abstract void onDisconnected();

    public ServiceProxy(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
        if (Debug.isDebuggerConnected()) {
            this.mTimeout <<= 2;
        }
        if (this.mServiceExecutor == null) {
            this.mServiceExecutor = ThreadExecutors.newCachedThreadPool(5, 100, 5, "ServiceProxy");
        }
    }

    private class ProxyConnection implements ServiceConnection {
        private ProxyConnection() {
        }

        /* JADX WARN: Type inference failed for: r1v2, types: [com.market.ServiceProxy$ProxyConnection$1] */
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ServiceProxy.this.onConnected(iBinder);
            new AsyncTask<Void, Void, Void>() { // from class: com.market.ServiceProxy.ProxyConnection.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public Void doInBackground(Void... voidArr) {
                    try {
                        ServiceProxy.this.mTask.run();
                    } catch (RemoteException unused) {
                    }
                    try {
                        ServiceProxy.this.mContext.unbindService(ServiceProxy.this.mConnection);
                    } catch (RuntimeException e) {
                        Log.e(ServiceProxy.this.mTag, "RuntimeException when trying to unbind from service", e);
                    }
                    ServiceProxy.this.mTaskCompleted = true;
                    synchronized (ServiceProxy.this.mConnection) {
                        ServiceProxy.this.mConnection.notify();
                    }
                    return null;
                }
            }.executeOnExecutor(ServiceProxy.this.mServiceExecutor, new Void[0]);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            ServiceProxy.this.onDisconnected();
        }
    }

    public ServiceProxy setTimeout(int i) {
        this.mTimeout = i;
        return this;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    protected boolean setTask(ProxyTask proxyTask, String str) throws IllegalStateException {
        if (this.mTaskSet) {
            throw new IllegalStateException("Cannot call setTask twice on the same ServiceProxy.");
        }
        this.mTaskSet = true;
        this.mName = str;
        this.mTask = proxyTask;
        this.mStartTime = System.currentTimeMillis();
        System.currentTimeMillis();
        return this.mContext.bindService(this.mIntent, this.mConnection, 1);
    }

    protected void waitForCompletion() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("This cannot be called on the main thread.");
        }
        synchronized (this.mConnection) {
            System.currentTimeMillis();
            try {
                this.mConnection.wait(((long) this.mTimeout) * 1000);
            } catch (InterruptedException unused) {
            }
        }
    }

    public boolean test() {
        try {
            return setTask(new ProxyTask() { // from class: com.market.ServiceProxy.1
                @Override // com.market.ServiceProxy.ProxyTask
                public void run() throws RemoteException {
                }
            }, "test");
        } catch (Exception unused) {
            return false;
        }
    }
}
