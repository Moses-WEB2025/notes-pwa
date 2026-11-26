package xc.engine.core.graphics.engine.task;

import android.content.Context;
import android.opengl.EGLContext;
import android.opengl.GLES30;
import android.os.ConditionVariable;
import android.os.Trace;
import android.util.Log;
import android.view.Choreographer;
import androidx.graphics.opengl.egl.EGLManager;
import ch.qos.logback.core.CoreConstants;
import com.google.android.appfunctions.schema.tasks.v1.TasksKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.api.PenEngine;
import xc.engine.api.listener.RequestDrawCallback;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.TexturePool;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.structs.TileRenderResource;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: AsyncGLTaskManager.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 \"2\u00020\u0001:\u0004\"#$%B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014J\u0014\u0010\u001c\u001a\u00020\u00192\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\b\u0010\u001e\u001a\u00020\u0019H\u0014J\u0006\u0010\u001f\u001a\u00020\u0019J\u000e\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\bR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0013\u001a\u00060\u0014R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "tasksToExecute", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "tasksToCheck", "tickThread", "Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager$CheckThread;", "tileRenderThread", "Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager$RenderThread;", "tasksToExecNotEmptyCondition", "Landroid/os/ConditionVariable;", "tasksToCheckNotEmptyCondition", "choreographer", "Landroid/view/Choreographer;", "vsyncCallback", "Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager$VSyncCallback;", "pendingRefresh", "Ljava/util/concurrent/atomic/AtomicBoolean;", "destroyed", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "clearTaskQueue", "queue", "onDestroy", "invalidate", "enqueueTask", "task", "Companion", "VSyncCallback", "RenderThread", "CheckThread", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AsyncGLTaskManager extends BaseEngineStruct {
    private static final String TAG = "AsyncGLTaskManager";
    private Choreographer choreographer;
    private final AtomicBoolean destroyed;
    private final AtomicBoolean pendingRefresh;
    private final ConcurrentLinkedQueue<BaseAsyncGLTask> tasksToCheck;
    private final ConditionVariable tasksToCheckNotEmptyCondition;
    private final ConditionVariable tasksToExecNotEmptyCondition;
    private final ConcurrentLinkedQueue<BaseAsyncGLTask> tasksToExecute;
    private CheckThread tickThread;
    private RenderThread tileRenderThread;
    private final VSyncCallback vsyncCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsyncGLTaskManager(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.tasksToExecute = new ConcurrentLinkedQueue<>();
        this.tasksToCheck = new ConcurrentLinkedQueue<>();
        this.tasksToExecNotEmptyCondition = new ConditionVariable();
        this.tasksToCheckNotEmptyCondition = new ConditionVariable();
        this.vsyncCallback = new VSyncCallback(this, new WeakReference(getCurveEngine()));
        this.pendingRefresh = new AtomicBoolean(false);
        this.destroyed = new AtomicBoolean(false);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.tickThread = new CheckThread(getCurveEngine());
        this.tileRenderThread = new RenderThread(getCurveEngine());
        if (getCurveEngine().getPenEngine().isSurfaceView) {
            EGLManager eGLManager = getCurveEngine().getPenEngine().getFrontBufferedRenderer().getGLFrontBufferedRenderer().getMGLRenderer().getEGLManager();
            EGLContext mEglContext = eGLManager != null ? eGLManager.getMEglContext() : null;
            CheckThread checkThread = this.tickThread;
            Intrinsics.checkNotNull(checkThread);
            Intrinsics.checkNotNull(mEglContext);
            checkThread.setSharedContext(mEglContext);
            RenderThread renderThread = this.tileRenderThread;
            Intrinsics.checkNotNull(renderThread);
            renderThread.setSharedContext(mEglContext);
            CheckThread checkThread2 = this.tickThread;
            Intrinsics.checkNotNull(checkThread2);
            checkThread2.start();
            RenderThread renderThread2 = this.tileRenderThread;
            Intrinsics.checkNotNull(renderThread2);
            renderThread2.start();
            this.choreographer = Choreographer.getInstance();
            return;
        }
        javax.microedition.khronos.egl.EGLContext glContext = getCurveEngine().getGlContext();
        CheckThread checkThread3 = this.tickThread;
        Intrinsics.checkNotNull(checkThread3);
        checkThread3.setSharedContext(glContext);
        RenderThread renderThread3 = this.tileRenderThread;
        Intrinsics.checkNotNull(renderThread3);
        renderThread3.setSharedContext(glContext);
        CheckThread checkThread4 = this.tickThread;
        Intrinsics.checkNotNull(checkThread4);
        checkThread4.start();
        RenderThread renderThread4 = this.tileRenderThread;
        Intrinsics.checkNotNull(renderThread4);
        renderThread4.start();
    }

    public final void clearTaskQueue(ConcurrentLinkedQueue<BaseAsyncGLTask> queue) {
        Intrinsics.checkNotNullParameter(queue, "queue");
        while (true) {
            BaseAsyncGLTask baseAsyncGLTaskPoll = queue.poll();
            BaseAsyncGLTask baseAsyncGLTask = baseAsyncGLTaskPoll;
            if (baseAsyncGLTaskPoll == null) {
                return;
            }
            Intrinsics.checkNotNull(baseAsyncGLTask);
            if (baseAsyncGLTask.getSyncHandle() != 0) {
                GLES30.glDeleteSync(baseAsyncGLTask.getSyncHandle());
                baseAsyncGLTask.setSyncHandle(0L);
            }
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        this.destroyed.set(true);
        Choreographer choreographer = this.choreographer;
        if (choreographer != null) {
            choreographer.removeFrameCallback(this.vsyncCallback);
        }
        this.choreographer = null;
        CheckThread checkThread = this.tickThread;
        if (checkThread != null) {
            checkThread.terminate();
        }
        RenderThread renderThread = this.tileRenderThread;
        if (renderThread != null) {
            renderThread.terminate();
        }
        this.tasksToExecNotEmptyCondition.open();
        this.tasksToCheckNotEmptyCondition.open();
        clearTaskQueue(this.tasksToExecute);
        clearTaskQueue(this.tasksToCheck);
        for (BaseGLThread baseGLThread : CollectionsKt.listOf((Object[]) new BaseGLThread[]{this.tickThread, this.tileRenderThread})) {
            if (baseGLThread != null) {
                for (int i = 0; baseGLThread.isAlive() && i < 3; i++) {
                    try {
                        baseGLThread.join(2000L);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (baseGLThread.isAlive()) {
                    baseGLThread.interrupt();
                }
            }
        }
    }

    public final void invalidate() {
        if (this.destroyed.get()) {
            Log.w(TAG, "Attempt to invalidate Frame after manager destroyed");
            return;
        }
        if (this.pendingRefresh.compareAndSet(false, true)) {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                choreographer.postFrameCallback(this.vsyncCallback);
            } else {
                this.pendingRefresh.set(false);
            }
        }
    }

    public final void enqueueTask(BaseAsyncGLTask task) {
        Intrinsics.checkNotNullParameter(task, "task");
        if (this.destroyed.get()) {
            Log.w(TAG, "Attempt to enqueue task after manager destroyed");
            return;
        }
        XLog.v(TAG, " enqueueTask [%s] @%d", task.getClass().getSimpleName(), Integer.valueOf(task.getTaskId()));
        this.tasksToExecute.offer(task);
        this.tasksToExecNotEmptyCondition.open();
    }

    /* JADX INFO: compiled from: AsyncGLTaskManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0016\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager$VSyncCallback;", "Landroid/view/Choreographer$FrameCallback;", "engineRef", "Ljava/lang/ref/WeakReference;", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager;Ljava/lang/ref/WeakReference;)V", "doFrame", "", "frameTimeNanos", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private final class VSyncCallback implements Choreographer.FrameCallback {
        private final WeakReference<MiCurveEngine> engineRef;
        final /* synthetic */ AsyncGLTaskManager this$0;

        public VSyncCallback(AsyncGLTaskManager asyncGLTaskManager, WeakReference<MiCurveEngine> engineRef) {
            Intrinsics.checkNotNullParameter(engineRef, "engineRef");
            this.this$0 = asyncGLTaskManager;
            this.engineRef = engineRef;
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            this.this$0.pendingRefresh.set(false);
            MiCurveEngine miCurveEngine = this.engineRef.get();
            if (miCurveEngine != null) {
                MiCurveEngine.requestMultiBufferRender$default(miCurveEngine, null, 1, null);
            }
        }
    }

    /* JADX INFO: compiled from: AsyncGLTaskManager.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0010H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager$RenderThread;", "Lxc/engine/core/graphics/engine/task/BaseGLThread;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", TasksKt.APP_FUNCTION_SCHEMA_CATEGORY_TASKS, "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "inProgressTasks", "condition", "Landroid/os/ConditionVariable;", "checkCondition", "renderRes", "Lxc/engine/core/graphics/engine/structs/TileRenderResource;", "onInit", "", "onDestroy", "tick", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class RenderThread extends BaseGLThread {
        private final ConditionVariable checkCondition;
        private final ConditionVariable condition;
        private final ConcurrentLinkedQueue<BaseAsyncGLTask> inProgressTasks;
        private final TileRenderResource renderRes;
        private final ConcurrentLinkedQueue<BaseAsyncGLTask> tasks;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RenderThread(MiCurveEngine engine) {
            super(engine);
            Intrinsics.checkNotNullParameter(engine, "engine");
            this.tasks = engine.getTaskManager().tasksToExecute;
            this.inProgressTasks = engine.getTaskManager().tasksToCheck;
            this.condition = engine.getTaskManager().tasksToExecNotEmptyCondition;
            this.checkCondition = engine.getTaskManager().tasksToCheckNotEmptyCondition;
            this.renderRes = new TileRenderResource(0, 1, null);
        }

        @Override // xc.engine.core.graphics.engine.task.BaseGLThread
        public void onInit() {
            TexturePool texturePool;
            MiCurveEngine miCurveEngine = getCurveEngineRef().get();
            if (miCurveEngine == null || (texturePool = miCurveEngine.getTexturePool()) == null) {
                return;
            }
            this.renderRes.init(texturePool);
        }

        @Override // xc.engine.core.graphics.engine.task.BaseGLThread
        public void onDestroy() {
            MiCurveEngine miCurveEngine = getCurveEngineRef().get();
            if (miCurveEngine != null) {
                this.renderRes.destroy(miCurveEngine.getTexturePool());
            }
            super.onDestroy();
        }

        @Override // xc.engine.core.graphics.engine.task.BaseGLThread
        public void tick() {
            while (getBRunning() && this.tasks.isEmpty()) {
                this.condition.close();
                this.condition.block();
            }
            BaseAsyncGLTask baseAsyncGLTaskPoll = this.tasks.poll();
            if (baseAsyncGLTaskPoll == null) {
                return;
            }
            Trace.beginSection("RenderTick");
            if (baseAsyncGLTaskPoll.execute(this.renderRes)) {
                XLog.v(AsyncGLTaskManager.TAG, "Task [%s] @%d executed", baseAsyncGLTaskPoll.getClass().getSimpleName(), Integer.valueOf(baseAsyncGLTaskPoll.getTaskId()));
                this.inProgressTasks.offer(baseAsyncGLTaskPoll);
                this.checkCondition.open();
            }
            Trace.endSection();
        }
    }

    /* JADX INFO: compiled from: AsyncGLTaskManager.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lxc/engine/core/graphics/engine/task/AsyncGLTaskManager$CheckThread;", "Lxc/engine/core/graphics/engine/task/BaseGLThread;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", TasksKt.APP_FUNCTION_SCHEMA_CATEGORY_TASKS, "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lxc/engine/core/graphics/engine/task/BaseAsyncGLTask;", "condition", "Landroid/os/ConditionVariable;", "tick", "", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class CheckThread extends BaseGLThread {
        private final ConditionVariable condition;
        private final ConcurrentLinkedQueue<BaseAsyncGLTask> tasks;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CheckThread(MiCurveEngine engine) {
            super(engine);
            Intrinsics.checkNotNullParameter(engine, "engine");
            this.tasks = engine.getTaskManager().tasksToCheck;
            this.condition = engine.getTaskManager().tasksToCheckNotEmptyCondition;
        }

        @Override // xc.engine.core.graphics.engine.task.BaseGLThread
        public void tick() {
            RequestDrawCallback renderCallback;
            PenEngine penEngine;
            AsyncGLTaskManager taskManager;
            BaseAsyncGLTask baseAsyncGLTaskPeek;
            while (getBRunning() && this.tasks.isEmpty()) {
                this.condition.close();
                this.condition.block();
            }
            boolean z = false;
            boolean z2 = false;
            while (!this.tasks.isEmpty() && (baseAsyncGLTaskPeek = this.tasks.peek()) != null && (baseAsyncGLTaskPeek.getSyncHandle() == 0 || GLUtils.INSTANCE.isGLSyncSignaled(baseAsyncGLTaskPeek.getSyncHandle()))) {
                if (baseAsyncGLTaskPeek.getSyncHandle() != 0) {
                    GLES30.glDeleteSync(baseAsyncGLTaskPeek.getSyncHandle());
                }
                BaseAsyncGLTask baseAsyncGLTaskPoll = this.tasks.poll();
                if (baseAsyncGLTaskPoll != null) {
                    baseAsyncGLTaskPoll.onRenderCompleted();
                    if (baseAsyncGLTaskPoll.getBNeedRefreshRender()) {
                        z2 = true;
                    }
                    XLog.v(AsyncGLTaskManager.TAG, "Task [%s] @%d Completed", baseAsyncGLTaskPoll.getClass().getSimpleName(), Integer.valueOf(baseAsyncGLTaskPoll.getTaskId()));
                    z = true;
                }
            }
            if (z && z2) {
                MiCurveEngine miCurveEngine = getCurveEngineRef().get();
                if (miCurveEngine != null && (penEngine = miCurveEngine.getPenEngine()) != null && penEngine.isSurfaceView) {
                    MiCurveEngine miCurveEngine2 = getCurveEngineRef().get();
                    if (miCurveEngine2 == null || (taskManager = miCurveEngine2.getTaskManager()) == null) {
                        return;
                    }
                    taskManager.invalidate();
                    return;
                }
                MiCurveEngine miCurveEngine3 = getCurveEngineRef().get();
                if (miCurveEngine3 == null || (renderCallback = miCurveEngine3.getRenderCallback()) == null) {
                    return;
                }
                renderCallback.requestRender();
            }
        }
    }
}
