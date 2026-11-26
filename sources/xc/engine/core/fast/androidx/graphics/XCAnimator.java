package xc.engine.core.fast.androidx.graphics;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: XCAnimator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\u0018\u0000 \u001f2\u00020\u0001:\u0002\u001f B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0010\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\rJ\u0006\u0010\u0017\u001a\u00020\u0012J\u0006\u0010\u0018\u001a\u00020\u0012J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0016J\b\u0010\u001b\u001a\u00020\u0012H\u0002J\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u000fJ\u0006\u0010\u001e\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lxc/engine/core/fast/androidx/graphics/XCAnimator;", "Lxc/engine/core/fast/androidx/graphics/FrameSyncThread$XCAnimationFrameCallback;", "syncThread", "Lxc/engine/core/fast/androidx/graphics/FrameSyncThread;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/fast/androidx/graphics/FrameSyncThread;)V", "mStartTime", "", "mLastFrameTime", "mRunning", "", "mCanceled", "mDuration", "", "mUpdateListeners", "Lxc/engine/core/fast/androidx/graphics/XCAnimator$XCAnimatorListener;", "getDuration", "setFPS", "", "fps", "", "setDuration", "duration", "start", ToDoStat.CANCEL, "doAnimationFrame", "frameTimeNanos", "resetAnimation", "addUpdateListener", "listener", "removeUpdateListener", "Companion", "XCAnimatorListener", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class XCAnimator implements FrameSyncThread.XCAnimationFrameCallback {
    private static long FRAME_INTERVAL = 16000000;
    private boolean mCanceled;
    private float mDuration;
    private long mLastFrameTime;
    private boolean mRunning;
    private long mStartTime;
    private XCAnimatorListener mUpdateListeners;
    private final FrameSyncThread syncThread;

    /* JADX INFO: compiled from: XCAnimator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lxc/engine/core/fast/androidx/graphics/XCAnimator$XCAnimatorListener;", "", "onAnimationStart", "", "passTime", "", "onAnimationEnd", "onAnimationUpdate", "onAnimationCancel", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface XCAnimatorListener {
        void onAnimationCancel(float passTime);

        void onAnimationEnd(float passTime);

        void onAnimationStart(float passTime);

        void onAnimationUpdate(float passTime);
    }

    public XCAnimator(FrameSyncThread syncThread) {
        Intrinsics.checkNotNullParameter(syncThread, "syncThread");
        this.syncThread = syncThread;
        this.mStartTime = -1L;
        this.mLastFrameTime = -1L;
        this.mDuration = 300.0f;
    }

    /* JADX INFO: renamed from: getDuration, reason: from getter */
    public final float getMDuration() {
        return this.mDuration;
    }

    public final void setFPS(int fps) {
        FRAME_INTERVAL = 900000000 / ((long) fps);
    }

    public final XCAnimator setDuration(float duration) {
        if (duration < 0.0f) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDuration = duration;
        return this;
    }

    public final void start() {
        resetAnimation();
        this.mRunning = true;
        this.syncThread.startFrameSync(this);
    }

    public final void cancel() {
        this.mCanceled = true;
    }

    @Override // xc.engine.core.fast.androidx.graphics.FrameSyncThread.XCAnimationFrameCallback
    public void doAnimationFrame(long frameTimeNanos) {
        if (this.mRunning && frameTimeNanos - this.mLastFrameTime >= FRAME_INTERVAL) {
            this.mLastFrameTime = frameTimeNanos;
            long j = this.mStartTime;
            if (j < 0) {
                this.mStartTime = frameTimeNanos;
                XCAnimatorListener xCAnimatorListener = this.mUpdateListeners;
                if (xCAnimatorListener != null) {
                    xCAnimatorListener.onAnimationStart(0.0f);
                }
                if (this.mCanceled) {
                    XCAnimatorListener xCAnimatorListener2 = this.mUpdateListeners;
                    if (xCAnimatorListener2 != null) {
                        xCAnimatorListener2.onAnimationCancel(0.0f);
                    }
                    this.syncThread.stopFrameSync();
                    resetAnimation();
                    return;
                }
                return;
            }
            float f = (frameTimeNanos - j) / 1000000.0f;
            if (this.mCanceled) {
                XCAnimatorListener xCAnimatorListener3 = this.mUpdateListeners;
                if (xCAnimatorListener3 != null) {
                    xCAnimatorListener3.onAnimationCancel(f);
                }
                this.syncThread.stopFrameSync();
                resetAnimation();
                return;
            }
            float f2 = this.mDuration;
            if (f > f2) {
                XCAnimatorListener xCAnimatorListener4 = this.mUpdateListeners;
                if (xCAnimatorListener4 != null) {
                    xCAnimatorListener4.onAnimationEnd(f2);
                }
                this.syncThread.stopFrameSync();
                resetAnimation();
                return;
            }
            XCAnimatorListener xCAnimatorListener5 = this.mUpdateListeners;
            if (xCAnimatorListener5 != null) {
                xCAnimatorListener5.onAnimationUpdate(f);
            }
        }
    }

    private final void resetAnimation() {
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        this.mRunning = false;
        this.mCanceled = false;
    }

    public final XCAnimator addUpdateListener(XCAnimatorListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mUpdateListeners = listener;
        return this;
    }

    public final void removeUpdateListener() {
        this.mUpdateListeners = null;
    }
}
