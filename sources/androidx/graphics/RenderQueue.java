package androidx.graphics;

import android.hardware.HardwareBuffer;
import androidx.graphics.utils.HandlerThreadExecutor;
import androidx.hardware.SyncFenceCompat;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RenderQueue.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001:\u0003 !\"B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u000e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000eJ\u0006\u0010\u0017\u001a\u00020\u0013J\u001e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00102\u000e\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000eH\u0003J\u0006\u0010\u001c\u001a\u00020\u0010J\b\u0010\u001d\u001a\u00020\u0013H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Landroidx/graphics/RenderQueue;", "", "handlerThread", "Landroidx/graphics/utils/HandlerThreadExecutor;", "frameProducer", "Landroidx/graphics/RenderQueue$FrameProducer;", "frameCallback", "Landroidx/graphics/RenderQueue$FrameCallback;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroidx/graphics/utils/HandlerThreadExecutor;Landroidx/graphics/RenderQueue$FrameProducer;Landroidx/graphics/RenderQueue$FrameCallback;)V", "mIsCancelling", "Ljava/util/concurrent/atomic/AtomicBoolean;", "mRequests", "Lkotlin/collections/ArrayDeque;", "Landroidx/graphics/RenderQueue$Request;", "mRequestPending", "", "mReleaseCallback", "Lkotlin/Function0;", "", "mIsReleasing", "enqueue", StatConstants.Event.REQUEST, "cancelPending", "release", "onReleaseComplete", "isPendingRequest", "executeRequest", "isReleased", "executeReleaseCallback", "cancelRequestsRunnable", "Ljava/lang/Runnable;", "FrameCallback", "FrameProducer", "Request", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class RenderQueue {
    private final Runnable cancelRequestsRunnable;
    private final FrameCallback frameCallback;
    private final FrameProducer frameProducer;
    private final HandlerThreadExecutor handlerThread;
    private final AtomicBoolean mIsCancelling;
    private final AtomicBoolean mIsReleasing;
    private Function0<Unit> mReleaseCallback;
    private boolean mRequestPending;
    private final ArrayDeque<Request> mRequests;

    /* JADX INFO: compiled from: RenderQueue.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\u001a\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/RenderQueue$FrameCallback;", "", "onFrameComplete", "", "hardwareBuffer", "Landroid/hardware/HardwareBuffer;", "fence", "Landroidx/hardware/SyncFenceCompat;", "onFrameCancelled", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface FrameCallback {
        void onFrameCancelled(HardwareBuffer hardwareBuffer, SyncFenceCompat fence);

        void onFrameComplete(HardwareBuffer hardwareBuffer, SyncFenceCompat fence);
    }

    /* JADX INFO: compiled from: RenderQueue.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u001a\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u00030\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/RenderQueue$FrameProducer;", "", "renderFrame", "", "executor", "Ljava/util/concurrent/Executor;", "requestComplete", "Lkotlin/Function2;", "Landroid/hardware/HardwareBuffer;", "Landroidx/hardware/SyncFenceCompat;", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface FrameProducer {
        void renderFrame(Executor executor, Function2<? super HardwareBuffer, ? super SyncFenceCompat, Unit> requestComplete);
    }

    /* JADX INFO: compiled from: RenderQueue.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0017J\b\u0010\u0004\u001a\u00020\u0003H'J\b\u0010\u0005\u001a\u00020\u0003H'J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Landroidx/graphics/RenderQueue$Request;", "", "onEnqueued", "", "execute", "onComplete", "isMergeable", "", "id", "", "getId", "()I", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface Request {
        void execute();

        int getId();

        default boolean isMergeable() {
            return true;
        }

        void onComplete();

        default void onEnqueued() {
        }
    }

    public RenderQueue(HandlerThreadExecutor handlerThread, FrameProducer frameProducer, FrameCallback frameCallback) {
        Intrinsics.checkNotNullParameter(handlerThread, "handlerThread");
        Intrinsics.checkNotNullParameter(frameProducer, "frameProducer");
        Intrinsics.checkNotNullParameter(frameCallback, "frameCallback");
        this.handlerThread = handlerThread;
        this.frameProducer = frameProducer;
        this.frameCallback = frameCallback;
        this.mIsCancelling = new AtomicBoolean(false);
        this.mRequests = new ArrayDeque<>();
        this.mIsReleasing = new AtomicBoolean(false);
        this.cancelRequestsRunnable = new Runnable() { // from class: androidx.graphics.RenderQueue$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RenderQueue.cancelRequestsRunnable$lambda$3(this.f$0);
            }
        };
    }

    public final void enqueue(final Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        if (this.mIsReleasing.get()) {
            return;
        }
        this.handlerThread.post(this, new Runnable() { // from class: androidx.graphics.RenderQueue$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RenderQueue.enqueue$lambda$0(request, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void enqueue$lambda$0(Request request, RenderQueue renderQueue) {
        request.onEnqueued();
        renderQueue.executeRequest(request);
    }

    public final void cancelPending() {
        if (this.mIsReleasing.get()) {
            return;
        }
        this.mIsCancelling.set(true);
        this.handlerThread.removeCallbacksAndMessages(this);
        this.handlerThread.post(this.cancelRequestsRunnable);
    }

    public final void release(boolean cancelPending, final Function0<Unit> onReleaseComplete) {
        if (this.mIsReleasing.get()) {
            return;
        }
        if (cancelPending) {
            cancelPending();
        }
        this.handlerThread.post(new Runnable() { // from class: androidx.graphics.RenderQueue$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                RenderQueue.release$lambda$1(this.f$0, onReleaseComplete);
            }
        });
        this.mIsReleasing.set(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void release$lambda$1(RenderQueue renderQueue, Function0 function0) {
        renderQueue.mReleaseCallback = function0;
        if (renderQueue.isPendingRequest()) {
            return;
        }
        renderQueue.executeReleaseCallback();
    }

    private final boolean isPendingRequest() {
        return this.mRequestPending || !this.mRequests.isEmpty();
    }

    private final void executeRequest(final Request request) {
        if (!this.mRequestPending) {
            this.mRequestPending = true;
            request.execute();
            this.frameProducer.renderFrame(this.handlerThread, new Function2() { // from class: androidx.graphics.RenderQueue$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return RenderQueue.executeRequest$lambda$2(this.f$0, request, (HardwareBuffer) obj, (SyncFenceCompat) obj2);
                }
            });
        } else {
            Request requestLastOrNull = this.mRequests.lastOrNull();
            if (request.isMergeable() && requestLastOrNull != null && requestLastOrNull.getId() == request.getId()) {
                return;
            }
            this.mRequests.add(request);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit executeRequest$lambda$2(RenderQueue renderQueue, Request request, HardwareBuffer hardwareBuffer, SyncFenceCompat syncFenceCompat) {
        Intrinsics.checkNotNullParameter(hardwareBuffer, "hardwareBuffer");
        renderQueue.mRequestPending = false;
        if (!renderQueue.mIsCancelling.getAndSet(false)) {
            renderQueue.frameCallback.onFrameComplete(hardwareBuffer, syncFenceCompat);
        } else {
            renderQueue.frameCallback.onFrameCancelled(hardwareBuffer, syncFenceCompat);
        }
        request.onComplete();
        if (!renderQueue.mRequests.isEmpty()) {
            renderQueue.executeRequest(renderQueue.mRequests.removeFirst());
        } else if (renderQueue.mIsReleasing.get()) {
            renderQueue.executeReleaseCallback();
        }
        return Unit.INSTANCE;
    }

    public final boolean isReleased() {
        return this.mIsReleasing.get();
    }

    private final void executeReleaseCallback() {
        Function0<Unit> function0 = this.mReleaseCallback;
        if (function0 != null) {
            function0.invoke();
        }
        this.mReleaseCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cancelRequestsRunnable$lambda$3(RenderQueue renderQueue) {
        renderQueue.mRequests.clear();
        if (renderQueue.mRequestPending) {
            return;
        }
        renderQueue.mIsCancelling.set(false);
    }
}
