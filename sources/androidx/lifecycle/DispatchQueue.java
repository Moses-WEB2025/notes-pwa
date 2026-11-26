package androidx.lifecycle;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import java.util.ArrayDeque;
import java.util.Queue;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* JADX INFO: compiled from: DispatchQueue.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\fH\u0007J\b\u0010\u000e\u001a\u00020\fH\u0007J\b\u0010\u000f\u001a\u00020\fH\u0007J\b\u0010\u0010\u001a\u00020\u0005H\u0007J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\nH\u0007J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Landroidx/lifecycle/DispatchQueue;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "paused", "", ToDoStat.FINISHED, "isDraining", "queue", "Ljava/util/Queue;", "Ljava/lang/Runnable;", "pause", "", "resume", "finish", "drainQueue", "canRun", "dispatchAndEnqueue", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "runnable", "enqueue", "lifecycle-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class DispatchQueue {
    private boolean finished;
    private boolean isDraining;
    private boolean paused = true;
    private final Queue<Runnable> queue = new ArrayDeque();

    public final void pause() {
        this.paused = true;
    }

    public final void resume() {
        if (this.paused) {
            if (this.finished) {
                throw new IllegalStateException("Cannot resume a finished dispatcher".toString());
            }
            this.paused = false;
            drainQueue();
        }
    }

    public final void finish() {
        this.finished = true;
        drainQueue();
    }

    public final void drainQueue() {
        if (this.isDraining) {
            return;
        }
        try {
            this.isDraining = true;
            while (!this.queue.isEmpty() && canRun()) {
                Runnable runnablePoll = this.queue.poll();
                if (runnablePoll != null) {
                    runnablePoll.run();
                }
            }
            this.isDraining = false;
        } catch (Throwable th) {
            this.isDraining = false;
            throw th;
        }
    }

    public final boolean canRun() {
        return this.finished || !this.paused;
    }

    public final void dispatchAndEnqueue(CoroutineContext context, final Runnable runnable) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        MainCoroutineDispatcher immediate = Dispatchers.getMain().getImmediate();
        if (immediate.isDispatchNeeded(context) || canRun()) {
            immediate.mo4121dispatch(context, new Runnable() { // from class: androidx.lifecycle.DispatchQueue$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.enqueue(runnable);
                }
            });
        } else {
            enqueue(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enqueue(Runnable runnable) {
        if (!this.queue.offer(runnable)) {
            throw new IllegalStateException("cannot enqueue any more runnables".toString());
        }
        drainQueue();
    }
}
