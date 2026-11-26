package cn.kuaipan.android.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public class SyncAccessor extends Handler {
    public Object handleAccess(int i, Object... objArr) {
        return null;
    }

    public static class Args {
        private static final int MAX_POOL_SIZE = 10;
        private static Args mPool;
        private static int mPoolSize;
        private static Object mPoolSync = new Object();
        public RuntimeException err;
        public volatile boolean handled = false;
        private Args next;
        public Object[] params;
        public Object result;

        public static Args obtain() {
            synchronized (mPoolSync) {
                Args args = mPool;
                if (args != null) {
                    mPool = args.next;
                    args.next = null;
                    return args;
                }
                return new Args();
            }
        }

        public void recycle() {
            synchronized (mPoolSync) {
                if (mPoolSize < 10) {
                    clearForRecycle();
                    this.next = mPool;
                    mPool = this;
                }
            }
        }

        private void clearForRecycle() {
            this.params = null;
            this.result = null;
            this.err = null;
            this.handled = false;
        }
    }

    public SyncAccessor(Looper looper) {
        super(looper);
    }

    public synchronized <T> T access(int i, Object... objArr) throws InterruptedException {
        T t;
        Args argsObtain = Args.obtain();
        argsObtain.params = objArr;
        if (sendMessage(obtainMessage(i, argsObtain))) {
            while (!argsObtain.handled) {
                if (!isAlive()) {
                    throw new RuntimeException("SyncAccessor has dead.");
                }
                synchronized (argsObtain) {
                    argsObtain.wait(50L);
                }
            }
            t = (T) argsObtain.result;
            RuntimeException runtimeException = argsObtain.err;
            argsObtain.recycle();
            if (runtimeException != null) {
                throw runtimeException;
            }
        } else {
            throw new RuntimeException("SyncAccessor has dead.");
        }
        return t;
    }

    private boolean isAlive() {
        Thread thread;
        Looper looper = getLooper();
        return (looper == null || (thread = looper.getThread()) == null || !thread.isAlive()) ? false : true;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        Object obj = message.obj;
        if (obj == null || !(obj instanceof Args)) {
            super.dispatchMessage(message);
            return;
        }
        Args args = (Args) obj;
        try {
            try {
                args.result = handleAccess(message.what, args.params);
                args.handled = true;
                synchronized (args) {
                    args.notifyAll();
                }
            } catch (RuntimeException e) {
                args.err = e;
                args.handled = true;
                synchronized (args) {
                    args.notifyAll();
                }
            }
        } catch (Throwable th) {
            args.handled = true;
            synchronized (args) {
                args.notifyAll();
                throw th;
            }
        }
    }
}
