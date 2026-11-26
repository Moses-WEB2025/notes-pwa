package kotlinx.coroutines;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.api.ba;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: Interruptible.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0011J\u0012\u0010\u0015\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\t\u0010\u0004\u001a\u00020\u0005X\u0082\u0004R\u0018\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lkotlinx/coroutines/ThreadState;", "Lkotlinx/coroutines/JobNode;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "_state", "Lkotlinx/atomicfu/AtomicInt;", "targetThread", "Ljava/lang/Thread;", "kotlin.jvm.PlatformType", "Ljava/lang/Thread;", "cancelHandle", "Lkotlinx/coroutines/DisposableHandle;", "onCancelling", "", "getOnCancelling", "()Z", "setup", "", ba.e, "Lkotlinx/coroutines/Job;", "clearInterrupt", "invoke", "cause", "", "invalidState", "", "state", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
final class ThreadState extends JobNode {
    private static final /* synthetic */ AtomicIntegerFieldUpdater _state$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(ThreadState.class, "_state$volatile");
    private volatile /* synthetic */ int _state$volatile;
    private DisposableHandle cancelHandle;
    private final Thread targetThread = Thread.currentThread();

    private final /* synthetic */ int get_state$volatile() {
        return this._state$volatile;
    }

    private final /* synthetic */ void loop$atomicfu$ATOMIC_FIELD_UPDATER$Int(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Object obj, Function1<? super Integer, Unit> function1) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void set_state$volatile(int i) {
        this._state$volatile = i;
    }

    @Override // kotlinx.coroutines.JobNode
    public boolean getOnCancelling() {
        return true;
    }

    public final void setup(Job job) {
        int i;
        this.cancelHandle = JobKt__JobKt.invokeOnCompletion$default(job, false, this, 1, null);
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _state$volatile$FU;
        do {
            i = atomicIntegerFieldUpdater.get(this);
            if (i != 0) {
                if (i == 2 || i == 3) {
                    return;
                }
                invalidState(i);
                throw new KotlinNothingValueException();
            }
        } while (!_state$volatile$FU.compareAndSet(this, i, 0));
    }

    public final void clearInterrupt() {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _state$volatile$FU;
        while (true) {
            int i = atomicIntegerFieldUpdater.get(this);
            if (i != 0) {
                if (i != 2) {
                    if (i == 3) {
                        Thread.interrupted();
                        return;
                    } else {
                        invalidState(i);
                        throw new KotlinNothingValueException();
                    }
                }
            } else if (_state$volatile$FU.compareAndSet(this, i, 1)) {
                DisposableHandle disposableHandle = this.cancelHandle;
                if (disposableHandle != null) {
                    disposableHandle.dispose();
                    return;
                }
                return;
            }
        }
    }

    @Override // kotlinx.coroutines.JobNode
    public void invoke(Throwable cause) {
        int i;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _state$volatile$FU;
        do {
            i = atomicIntegerFieldUpdater.get(this);
            if (i != 0) {
                if (i == 1 || i == 2 || i == 3) {
                    return;
                }
                invalidState(i);
                throw new KotlinNothingValueException();
            }
        } while (!_state$volatile$FU.compareAndSet(this, i, 2));
        this.targetThread.interrupt();
        _state$volatile$FU.set(this, 3);
    }

    private final Void invalidState(int state) {
        throw new IllegalStateException(("Illegal state " + state).toString());
    }
}
