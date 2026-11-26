package xc.engine.core.fast.androidx.graphics;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Choreographer;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrameSyncThread.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001f2\u00020\u00012\u00020\u0002:\u0002\u001f B\u0011\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0010\u001a\u00020\u0011H\u0014J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\bJ\u0006\u0010\u0017\u001a\u00020\u0011J\b\u0010\u0018\u001a\u00020\u000eH\u0016J\b\u0010\u0019\u001a\u00020\fH\u0002J\b\u0010\u001a\u001a\u00020\u000eH\u0016J\"\u0010\u001b\u001a\u00020\u00112\u0017\u0010\u001c\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00110\u001d¢\u0006\u0002\b\u001eH\u0082\bR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/core/fast/androidx/graphics/FrameSyncThread;", "Landroid/os/HandlerThread;", "Landroid/view/Choreographer$FrameCallback;", "name", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;)V", "animatorCallback", "Lxc/engine/core/fast/androidx/graphics/FrameSyncThread$XCAnimationFrameCallback;", "renderHandler", "Landroid/os/Handler;", "choreographer", "Landroid/view/Choreographer;", "isRendering", "", "firstFrame", "onLooperPrepared", "", "doFrame", "frameTimeNanos", "", "startFrameSync", CallMethod.ARG_CALLBACK, "stopFrameSync", "quit", "getChoreographer", "quitSafely", "withHandler", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "Companion", "XCAnimationFrameCallback", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrameSyncThread extends HandlerThread implements Choreographer.FrameCallback {
    private static final String TAG = "FrameSyncThread";
    private XCAnimationFrameCallback animatorCallback;
    private Choreographer choreographer;
    private boolean firstFrame;
    private boolean isRendering;
    private Handler renderHandler;

    /* JADX INFO: compiled from: FrameSyncThread.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lxc/engine/core/fast/androidx/graphics/FrameSyncThread$XCAnimationFrameCallback;", "", "doAnimationFrame", "", "frameTimeNanos", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface XCAnimationFrameCallback {
        void doAnimationFrame(long frameTimeNanos);
    }

    public FrameSyncThread() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ FrameSyncThread(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "FrameSyncThread" : str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FrameSyncThread(String name) {
        super(name);
        Intrinsics.checkNotNullParameter(name, "name");
    }

    @Override // android.os.HandlerThread
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        this.renderHandler = new Handler(getLooper());
        Log.d(TAG, getName() + " started");
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long frameTimeNanos) {
        if (this.isRendering) {
            XCAnimationFrameCallback xCAnimationFrameCallback = this.animatorCallback;
            if (xCAnimationFrameCallback != null) {
                xCAnimationFrameCallback.doAnimationFrame(frameTimeNanos);
            }
            getChoreographer().postFrameCallback(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startFrameSync$lambda$1$lambda$0(FrameSyncThread frameSyncThread, XCAnimationFrameCallback xCAnimationFrameCallback) {
        frameSyncThread.animatorCallback = xCAnimationFrameCallback;
        if (frameSyncThread.isRendering) {
            return;
        }
        frameSyncThread.isRendering = true;
        frameSyncThread.firstFrame = true;
        frameSyncThread.getChoreographer().postFrameCallback(frameSyncThread);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void stopFrameSync$lambda$3$lambda$2(FrameSyncThread frameSyncThread) {
        frameSyncThread.animatorCallback = null;
        if (frameSyncThread.isRendering) {
            frameSyncThread.isRendering = false;
            frameSyncThread.getChoreographer().removeFrameCallback(frameSyncThread);
        }
    }

    @Override // android.os.HandlerThread
    public boolean quit() {
        stopFrameSync();
        return super.quit();
    }

    private final Choreographer getChoreographer() {
        if (this.choreographer == null) {
            this.choreographer = Choreographer.getInstance();
        }
        Choreographer choreographer = this.choreographer;
        Intrinsics.checkNotNull(choreographer);
        return choreographer;
    }

    @Override // android.os.HandlerThread
    public boolean quitSafely() {
        stopFrameSync();
        return super.quitSafely();
    }

    private final void withHandler(Function1<? super Handler, Unit> block) {
        Handler handler = this.renderHandler;
        if (handler == null) {
            Log.e(TAG, "renderHandler not initialized, ignoring operation");
        } else if (handler != null) {
            block.invoke(handler);
        }
    }

    public final void startFrameSync(final XCAnimationFrameCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Handler handler = this.renderHandler;
        if (handler == null) {
            Log.e(TAG, "renderHandler not initialized, ignoring operation");
        } else if (handler != null) {
            handler.post(new Runnable() { // from class: xc.engine.core.fast.androidx.graphics.FrameSyncThread$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FrameSyncThread.startFrameSync$lambda$1$lambda$0(this.f$0, callback);
                }
            });
        }
    }

    public final void stopFrameSync() {
        Handler handler = this.renderHandler;
        if (handler == null) {
            Log.e(TAG, "renderHandler not initialized, ignoring operation");
        } else if (handler != null) {
            handler.post(new Runnable() { // from class: xc.engine.core.fast.androidx.graphics.FrameSyncThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FrameSyncThread.stopFrameSync$lambda$3$lambda$2(this.f$0);
                }
            });
        }
    }
}
