package androidx.graphics.utils;

import android.os.Handler;
import android.os.HandlerThread;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HandlerUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0012\u0010\u0015\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0006\u0010\u001a\u001a\u00020\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001b"}, d2 = {"Landroidx/graphics/utils/HandlerThreadExecutor;", "Ljava/util/concurrent/Executor;", "name", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "mHandlerThread", "Landroid/os/HandlerThread;", "mHandler", "Landroid/os/Handler;", "post", "", Constants.VERIFY_SUCESS_TOKEN, "", "runnable", "Ljava/lang/Runnable;", "postDelayed", "delayMillis", "", "removeCallbacksAndMessages", "removeCallbacks", "execute", "mIsQuit", "", "isRunning", "()Z", "quit", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class HandlerThreadExecutor implements Executor {
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private boolean mIsQuit;

    public HandlerThreadExecutor(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        HandlerThread handlerThread = new HandlerThread(name);
        handlerThread.start();
        this.mHandlerThread = handlerThread;
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    public final void post(Object token, Runnable runnable) {
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        HandlerUtilsKt.post(this.mHandler, token, runnable);
    }

    public final void post(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        this.mHandler.post(runnable);
    }

    public final void postDelayed(Runnable runnable, long delayMillis) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        this.mHandler.postDelayed(runnable, delayMillis);
    }

    public final void removeCallbacksAndMessages(Object token) {
        Intrinsics.checkNotNullParameter(token, "token");
        this.mHandler.removeCallbacksAndMessages(token);
    }

    public final void removeCallbacks(Runnable runnable) {
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        this.mHandler.removeCallbacks(runnable);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable != null) {
            this.mHandler.post(runnable);
        }
    }

    public final boolean isRunning() {
        return !this.mIsQuit;
    }

    public final void quit() {
        this.mHandlerThread.quit();
        this.mIsQuit = true;
    }
}
