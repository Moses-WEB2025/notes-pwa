package com.miui.handwriting.common.tools;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/* JADX INFO: loaded from: classes3.dex */
public class RendToBitmapHandlerThread {
    public static final int MSG_RENDTOBITMAP = 288;
    private static final String TAG = "RendToBitmapHandlerThread";
    private static Handler handler;
    private static HandlerThread handlerThread;

    private static void init() {
        HandlerThread handlerThread2 = new HandlerThread("");
        handlerThread = handlerThread2;
        handlerThread2.start();
        handler = new Handler(handlerThread.getLooper(), new ChildHandlerCallback());
    }

    public static void postRendToBitmapMsg(Runnable runnable) {
        if (handler == null) {
            init();
        }
        postRunnable(runnable, 288);
    }

    public static void postRunnable(Runnable runnable, int i) {
        if (handler == null) {
            init();
        }
        Message message = new Message();
        message.obj = runnable;
        message.what = i;
        handler.sendMessage(message);
    }

    public static void quit() {
        Handler handler2 = handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
        HandlerThread handlerThread2 = handlerThread;
        if (handlerThread2 != null) {
            handlerThread2.quitSafely();
        }
        handler = null;
        handlerThread = null;
    }

    public static void clearAll() {
        Handler handler2 = handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
    }

    private static class ChildHandlerCallback implements Handler.Callback {
        private ChildHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            ((Runnable) message.obj).run();
            return false;
        }
    }
}
