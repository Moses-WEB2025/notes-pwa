package com.miui.libhandwritingpreview.manager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PenPreEngine;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PrePenEngineHandlerWrapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001c\u001dB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010\u0010\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ \u0010\u0011\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010\u0017\u001a\u00020\u00182\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010\u0019\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001e"}, d2 = {"Lcom/miui/libhandwritingpreview/manager/PrePenEngineHandlerWrapper;", "", "penEngine", "Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PenPreEngine;Landroid/os/Looper;)V", "mHandler", "Lcom/miui/libhandwritingpreview/manager/PrePenEngineHandlerWrapper$MyHandler;", "getMHandler", "()Lcom/miui/libhandwritingpreview/manager/PrePenEngineHandlerWrapper$MyHandler;", "sendMessage", "", "runnable", "Ljava/lang/Runnable;", "sendMessageAtFront", "sendMessageDelayed", "what", "", "delayMillis", "", "sendToTargetHandler", "hasMessages", "", "removeMessages", "getLooper", "()Landroid/os/Looper;", "MyHandler", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PrePenEngineHandlerWrapper {
    public static final int WHAT_COMMON = 10;
    public static final int WHAT_SELECT = 21;
    private final MyHandler mHandler;

    public PrePenEngineHandlerWrapper(PenPreEngine penEngine, Looper looper) {
        Intrinsics.checkNotNullParameter(penEngine, "penEngine");
        Intrinsics.checkNotNullParameter(looper, "looper");
        this.mHandler = new MyHandler(penEngine, looper);
    }

    public final MyHandler getMHandler() {
        return this.mHandler;
    }

    /* JADX INFO: compiled from: PrePenEngineHandlerWrapper.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lcom/miui/libhandwritingpreview/manager/PrePenEngineHandlerWrapper$MyHandler;", "Landroid/os/Handler;", "mPenEngine", "Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", "looper", "Landroid/os/Looper;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PenPreEngine;Landroid/os/Looper;)V", "getMPenEngine", "()Lcom/miui/libhandwritingpreview/engine/PenPreEngine;", "handleMessage", "", "message", "Landroid/os/Message;", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class MyHandler extends Handler {
        private final PenPreEngine mPenEngine;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyHandler(PenPreEngine mPenEngine, Looper looper) {
            super(looper);
            Intrinsics.checkNotNullParameter(mPenEngine, "mPenEngine");
            Intrinsics.checkNotNullParameter(looper, "looper");
            this.mPenEngine = mPenEngine;
        }

        public final PenPreEngine getMPenEngine() {
            return this.mPenEngine;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Intrinsics.checkNotNullParameter(message, "message");
            if (this.mPenEngine.isRelease()) {
                return;
            }
            Object obj = message.obj;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.lang.Runnable");
            ((Runnable) obj).run();
        }
    }

    public final void sendMessage(Runnable runnable) {
        Message messageObtain = Message.obtain();
        messageObtain.obj = runnable;
        messageObtain.what = 10;
        this.mHandler.sendMessage(messageObtain);
    }

    public final void sendMessageAtFront(Runnable runnable) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 10;
        messageObtain.obj = runnable;
        this.mHandler.sendMessageAtFrontOfQueue(messageObtain);
    }

    public final void sendMessageDelayed(Runnable runnable, int what, long delayMillis) {
        Message messageObtain = Message.obtain();
        messageObtain.obj = runnable;
        messageObtain.what = what;
        this.mHandler.sendMessageDelayed(messageObtain, delayMillis);
    }

    public final void sendToTargetHandler(Runnable runnable) {
        Message messageObtain = Message.obtain(this.mHandler, runnable);
        messageObtain.what = 10;
        messageObtain.sendToTarget();
    }

    public final boolean hasMessages(Runnable runnable) {
        return this.mHandler.hasMessages(10, runnable);
    }

    public final void removeMessages(Runnable runnable) {
        this.mHandler.removeMessages(10, runnable);
    }

    public final Looper getLooper() {
        Looper looper = this.mHandler.getLooper();
        Intrinsics.checkNotNullExpressionValue(looper, "getLooper(...)");
        return looper;
    }
}
