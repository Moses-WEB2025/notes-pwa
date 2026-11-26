package xc.engine.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes6.dex */
public class PenEngineHandlerWrapper {
    public static final int WHAT_COMMON = 10;
    public static final int WHAT_SELECT = 21;
    public final MyHandler mHandler;

    public static class MyHandler extends Handler {
        public final PenEngine mPenEngine;

        public MyHandler(PenEngine penEngine, Looper looper) {
            super(looper);
            this.mPenEngine = penEngine;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mPenEngine.isRelease()) {
                return;
            }
            ((Runnable) message.obj).run();
        }
    }

    public PenEngineHandlerWrapper(PenEngine penEngine, Looper looper) {
        this.mHandler = new MyHandler(penEngine, looper);
    }

    public void sendMessage(Runnable runnable) {
        Message messageObtain = Message.obtain();
        messageObtain.obj = runnable;
        messageObtain.what = 10;
        this.mHandler.sendMessage(messageObtain);
    }

    public void sendMessageAtFront(Runnable runnable) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 10;
        messageObtain.obj = runnable;
        this.mHandler.sendMessageAtFrontOfQueue(messageObtain);
    }

    public void sendMessageDelayed(Runnable runnable, int i, long j) {
        Message messageObtain = Message.obtain();
        messageObtain.obj = runnable;
        messageObtain.what = i;
        this.mHandler.sendMessageDelayed(messageObtain, j);
    }

    public void sendToTargetHandler(Runnable runnable) {
        Message messageObtain = Message.obtain(this.mHandler, runnable);
        messageObtain.what = 10;
        messageObtain.sendToTarget();
    }

    public boolean hasMessages(Runnable runnable) {
        return this.mHandler.hasMessages(10, runnable);
    }

    public void removeMessages(Runnable runnable) {
        this.mHandler.removeMessages(10, runnable);
    }

    public Looper getLooper() {
        return this.mHandler.getLooper();
    }
}
