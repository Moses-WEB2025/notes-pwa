package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.ToDoStat;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.verificationsdk.internal.Constants;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;

/* JADX INFO: compiled from: CancellableContinuationImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\b\u0011\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00060\u0004j\u0002`\u00052\u00020\u0006B\u001d\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020!H\u0002J\b\u0010,\u001a\u00020!H\u0001J\u0015\u00100\u001a\n\u0018\u000101j\u0004\u0018\u0001`2H\u0016¢\u0006\u0002\u00103J\u000f\u00104\u001a\u0004\u0018\u00010\u0017H\u0010¢\u0006\u0002\b5J\u001f\u00106\u001a\u00020*2\b\u00107\u001a\u0004\u0018\u00010\u00172\u0006\u00108\u001a\u000209H\u0010¢\u0006\u0002\b:J\u0010\u0010;\u001a\u00020!2\u0006\u00108\u001a\u000209H\u0002J\u0012\u0010<\u001a\u00020!2\b\u00108\u001a\u0004\u0018\u000109H\u0016J\u0015\u0010=\u001a\u00020*2\u0006\u00108\u001a\u000209H\u0000¢\u0006\u0002\b>J\u0017\u0010?\u001a\u00020*2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020*0AH\u0082\bJ\u0018\u0010B\u001a\u00020*2\u0006\u0010C\u001a\u00020D2\b\u00108\u001a\u0004\u0018\u000109J\u001e\u0010E\u001a\u00020*2\n\u0010F\u001a\u0006\u0012\u0002\b\u00030G2\b\u00108\u001a\u0004\u0018\u000109H\u0002Jn\u0010H\u001a\u00020*\"\u0004\b\u0001\u0010I2K\u0010J\u001aG\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*0K2\u0006\u00108\u001a\u0002092\u0006\u0010N\u001a\u0002HI¢\u0006\u0002\u0010OJ\u0010\u0010P\u001a\u0002092\u0006\u0010Q\u001a\u00020RH\u0016J\b\u0010S\u001a\u00020!H\u0002J\b\u0010T\u001a\u00020!H\u0002J\n\u0010U\u001a\u0004\u0018\u00010\u0017H\u0001J\n\u0010V\u001a\u0004\u0018\u00010\u0019H\u0002J\r\u0010W\u001a\u00020*H\u0000¢\u0006\u0002\bXJ\u001b\u0010Y\u001a\u00020*2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00028\u00000[H\u0016¢\u0006\u0002\u0010\\J:\u0010]\u001a\u00020*2\u0006\u0010N\u001a\u00028\u00002#\u0010J\u001a\u001f\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0004\u0012\u00020*\u0018\u00010^H\u0016¢\u0006\u0002\u0010_Jn\u0010]\u001a\u00020*\"\b\b\u0001\u0010I*\u00028\u00002\u0006\u0010N\u001a\u0002HI2M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0016¢\u0006\u0002\u0010`J\u001c\u0010a\u001a\u00020*2\n\u0010F\u001a\u0006\u0012\u0002\b\u00030G2\u0006\u0010b\u001a\u00020\nH\u0016J1\u0010a\u001a\u00020*2'\u0010C\u001a#\u0012\u0015\u0012\u0013\u0018\u000109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0004\u0012\u00020*0^j\u0002`cH\u0016J\u0015\u0010d\u001a\u00020*2\u0006\u0010C\u001a\u00020DH\u0000¢\u0006\u0002\beJ\u0010\u0010f\u001a\u00020*2\u0006\u0010C\u001a\u00020\u0017H\u0002J\u001a\u0010g\u001a\u00020*2\u0006\u0010C\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u0017H\u0002J\u0010\u0010h\u001a\u00020*2\u0006\u0010i\u001a\u00020\nH\u0002J\u0086\u0001\u0010j\u001a\u0004\u0018\u00010\u0017\"\u0004\b\u0001\u0010I2\u0006\u0010\u001d\u001a\u00020k2\u0006\u0010l\u001a\u0002HI2\u0006\u0010\t\u001a\u00020\n2M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010K2\b\u0010m\u001a\u0004\u0018\u00010\u0017H\u0002¢\u0006\u0002\u0010nJv\u0010o\u001a\u00020*\"\u0004\b\u0001\u0010I2\u0006\u0010l\u001a\u0002HI2\u0006\u0010\t\u001a\u00020\n2O\b\u0002\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0000¢\u0006\u0004\bp\u0010qJv\u0010r\u001a\u0004\u0018\u00010s\"\u0004\b\u0001\u0010I2\u0006\u0010l\u001a\u0002HI2\b\u0010m\u001a\u0004\u0018\u00010\u00172M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0002¢\u0006\u0002\u0010tJ\u0012\u0010u\u001a\u00020v2\b\u0010l\u001a\u0004\u0018\u00010\u0017H\u0002J\b\u0010w\u001a\u00020*H\u0002J\r\u0010x\u001a\u00020*H\u0000¢\u0006\u0002\byJ!\u0010T\u001a\u0004\u0018\u00010\u00172\u0006\u0010N\u001a\u00028\u00002\b\u0010m\u001a\u0004\u0018\u00010\u0017H\u0016¢\u0006\u0002\u0010zJz\u0010T\u001a\u0004\u0018\u00010\u0017\"\b\b\u0001\u0010I*\u00028\u00002\u0006\u0010N\u001a\u0002HI2\b\u0010m\u001a\u0004\u0018\u00010\u00172M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0016¢\u0006\u0002\u0010{J\u0012\u0010|\u001a\u0004\u0018\u00010\u00172\u0006\u0010}\u001a\u000209H\u0016J\u0010\u0010~\u001a\u00020*2\u0006\u0010\u007f\u001a\u00020\u0017H\u0016J\u001c\u0010\u0080\u0001\u001a\u00020**\u00030\u0081\u00012\u0006\u0010N\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0082\u0001J\u0016\u0010\u0083\u0001\u001a\u00020**\u00030\u0081\u00012\u0006\u0010}\u001a\u000209H\u0016J\"\u0010\u0084\u0001\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0017H\u0010¢\u0006\u0006\b\u0085\u0001\u0010\u0086\u0001J\u001b\u0010\u0087\u0001\u001a\u0004\u0018\u0001092\b\u0010\u001d\u001a\u0004\u0018\u00010\u0017H\u0010¢\u0006\u0003\b\u0088\u0001J\t\u0010\u0089\u0001\u001a\u00020&H\u0016J\t\u0010\u008a\u0001\u001a\u00020&H\u0014R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\t\u0010\u0013\u001a\u00020\u0014X\u0082\u0004R\u0011\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0016X\u0082\u0004R\u0011\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0016X\u0082\u0004R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u00178@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\"R\u0014\u0010#\u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\"R\u0014\u0010$\u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\"R\u0014\u0010%\u001a\u00020&8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001c\u0010-\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/¨\u0006\u008b\u0001"}, d2 = {"Lkotlinx/coroutines/CancellableContinuationImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/Waiter;", "delegate", "Lkotlin/coroutines/Continuation;", "resumeMode", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lkotlin/coroutines/Continuation;I)V", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "_decisionAndIndex", "Lkotlinx/atomicfu/AtomicInt;", "_state", "Lkotlinx/atomicfu/AtomicRef;", "", "_parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "parentHandle", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "state", "getState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "isActive", "", "()Z", "isCompleted", "isCancelled", "stateDebugRepresentation", "", "getStateDebugRepresentation", "()Ljava/lang/String;", "initCancellability", "", "isReusable", "resetStateReusable", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "()Ljava/lang/StackTraceElement;", "takeState", "takeState$kotlinx_coroutines_core", "cancelCompletedResult", "takenState", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "cancelLater", ToDoStat.CANCEL, "parentCancelled", "parentCancelled$kotlinx_coroutines_core", "callCancelHandlerSafely", "block", "Lkotlin/Function0;", "callCancelHandler", "handler", "Lkotlinx/coroutines/CancelHandler;", "callSegmentOnCancellation", "segment", "Lkotlinx/coroutines/internal/Segment;", "callOnCancellation", "R", "onCancellation", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "value", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Throwable;Ljava/lang/Object;)V", "getContinuationCancellationCause", "parent", "Lkotlinx/coroutines/Job;", "trySuspend", "tryResume", "getResult", "installParentHandle", "releaseClaimedReusableContinuation", "releaseClaimedReusableContinuation$kotlinx_coroutines_core", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "resume", "Lkotlin/Function1;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)V", "invokeOnCancellation", "index", "Lkotlinx/coroutines/CompletionHandler;", "invokeOnCancellationInternal", "invokeOnCancellationInternal$kotlinx_coroutines_core", "invokeOnCancellationImpl", "multipleHandlersError", "dispatchResume", "mode", "resumedState", "Lkotlinx/coroutines/NotCompleted;", "proposedUpdate", "idempotent", "(Lkotlinx/coroutines/NotCompleted;Ljava/lang/Object;ILkotlin/jvm/functions/Function3;Ljava/lang/Object;)Ljava/lang/Object;", "resumeImpl", "resumeImpl$kotlinx_coroutines_core", "(Ljava/lang/Object;ILkotlin/jvm/functions/Function3;)V", "tryResumeImpl", "Lkotlinx/coroutines/internal/Symbol;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/internal/Symbol;", "alreadyResumedError", "", "detachChildIfNonReusable", "detachChild", "detachChild$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "tryResumeWithException", "exception", "completeResume", Constants.VERIFY_SUCESS_TOKEN, "resumeUndispatched", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getExceptionalResult", "getExceptionalResult$kotlinx_coroutines_core", "toString", "nameString", "kotlinx-coroutines-core"}, k = 1, mv = {2, 1, 0}, xi = 48)
public class CancellableContinuationImpl<T> extends DispatchedTask<T> implements CancellableContinuation<T>, CoroutineStackFrame, Waiter {
    private volatile /* synthetic */ int _decisionAndIndex$volatile;
    private volatile /* synthetic */ Object _parentHandle$volatile;
    private volatile /* synthetic */ Object _state$volatile;
    private final CoroutineContext context;
    private final Continuation<T> delegate;
    private static final /* synthetic */ AtomicIntegerFieldUpdater _decisionAndIndex$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(CancellableContinuationImpl.class, "_decisionAndIndex$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _state$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_state$volatile");
    private static final /* synthetic */ AtomicReferenceFieldUpdater _parentHandle$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(CancellableContinuationImpl.class, Object.class, "_parentHandle$volatile");

    private final /* synthetic */ int get_decisionAndIndex$volatile() {
        return this._decisionAndIndex$volatile;
    }

    private final /* synthetic */ Object get_parentHandle$volatile() {
        return this._parentHandle$volatile;
    }

    private final /* synthetic */ Object get_state$volatile() {
        return this._state$volatile;
    }

    private final /* synthetic */ void loop$atomicfu$ATOMIC_FIELD_UPDATER$Any(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Object obj, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void loop$atomicfu$ATOMIC_FIELD_UPDATER$Int(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Object obj, Function1<? super Integer, Unit> function1) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void set_decisionAndIndex$volatile(int i) {
        this._decisionAndIndex$volatile = i;
    }

    private final /* synthetic */ void set_parentHandle$volatile(Object obj) {
        this._parentHandle$volatile = obj;
    }

    private final /* synthetic */ void set_state$volatile(Object obj) {
        this._state$volatile = obj;
    }

    private final /* synthetic */ void update$atomicfu$ATOMIC_FIELD_UPDATER$Int(AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Object obj, Function1<? super Integer, Integer> function1) {
        int i;
        do {
            i = atomicIntegerFieldUpdater.get(obj);
        } while (!atomicIntegerFieldUpdater.compareAndSet(obj, i, function1.invoke(Integer.valueOf(i)).intValue()));
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public final Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this.delegate;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CancellableContinuationImpl(Continuation<? super T> continuation, int i) {
        super(i);
        this.delegate = continuation;
        if (DebugKt.getASSERTIONS_ENABLED() && i == -1) {
            throw new AssertionError();
        }
        this.context = continuation.getContext();
        this._decisionAndIndex$volatile = 536870911;
        this._state$volatile = Active.INSTANCE;
    }

    @Override // kotlin.coroutines.Continuation
    public CoroutineContext getContext() {
        return this.context;
    }

    private final DisposableHandle getParentHandle() {
        return (DisposableHandle) _parentHandle$volatile$FU.get(this);
    }

    public final Object getState$kotlinx_coroutines_core() {
        return _state$volatile$FU.get(this);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isActive() {
        return getState$kotlinx_coroutines_core() instanceof NotCompleted;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof NotCompleted);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean isCancelled() {
        return getState$kotlinx_coroutines_core() instanceof CancelledContinuation;
    }

    private final String getStateDebugRepresentation() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof NotCompleted) {
            return "Active";
        }
        return state$kotlinx_coroutines_core instanceof CancelledContinuation ? "Cancelled" : "Completed";
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void initCancellability() {
        DisposableHandle disposableHandleInstallParentHandle = installParentHandle();
        if (disposableHandleInstallParentHandle != null && isCompleted()) {
            disposableHandleInstallParentHandle.dispose();
            _parentHandle$volatile$FU.set(this, NonDisposableHandle.INSTANCE);
        }
    }

    private final boolean isReusable() {
        if (!DispatchedTaskKt.isReusableMode(this.resumeMode)) {
            return false;
        }
        Continuation<T> continuation = this.delegate;
        Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
        return ((DispatchedContinuation) continuation).isReusable$kotlinx_coroutines_core();
    }

    public final boolean resetStateReusable() {
        if (DebugKt.getASSERTIONS_ENABLED() && this.resumeMode != 2) {
            throw new AssertionError();
        }
        if (DebugKt.getASSERTIONS_ENABLED() && getParentHandle() == NonDisposableHandle.INSTANCE) {
            throw new AssertionError();
        }
        Object obj = _state$volatile$FU.get(this);
        if (DebugKt.getASSERTIONS_ENABLED() && (obj instanceof NotCompleted)) {
            throw new AssertionError();
        }
        if ((obj instanceof CompletedContinuation) && ((CompletedContinuation) obj).idempotentResume != null) {
            detachChild$kotlinx_coroutines_core();
            return false;
        }
        _decisionAndIndex$volatile$FU.set(this, 536870911);
        _state$volatile$FU.set(this, Active.INSTANCE);
        return true;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.delegate;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Object takeState$kotlinx_coroutines_core() {
        return getState$kotlinx_coroutines_core();
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
        Throwable th;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$volatile$FU;
        while (true) {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof NotCompleted) {
                throw new IllegalStateException("Not completed".toString());
            }
            if (obj instanceof CompletedExceptionally) {
                return;
            }
            if (obj instanceof CompletedContinuation) {
                CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                if (completedContinuation.getCancelled()) {
                    throw new IllegalStateException("Must be called at most once".toString());
                }
                Throwable th2 = cause;
                th = th2;
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, obj, CompletedContinuation.copy$default(completedContinuation, null, null, null, null, th2, 15, null))) {
                    completedContinuation.invokeHandlers(this, th);
                    return;
                }
            } else {
                th = cause;
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, obj, new CompletedContinuation(obj, null, null, null, th, 14, null))) {
                    return;
                }
            }
            cause = th;
        }
    }

    private final boolean cancelLater(Throwable cause) {
        if (!isReusable()) {
            return false;
        }
        Continuation<T> continuation = this.delegate;
        Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
        return ((DispatchedContinuation) continuation).postponeCancellation$kotlinx_coroutines_core(cause);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public boolean cancel(Throwable cause) {
        Object obj;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$volatile$FU;
        do {
            obj = atomicReferenceFieldUpdater.get(this);
            if (!(obj instanceof NotCompleted)) {
                return false;
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, obj, new CancelledContinuation(this, cause, (obj instanceof CancelHandler) || (obj instanceof Segment))));
        NotCompleted notCompleted = (NotCompleted) obj;
        if (notCompleted instanceof CancelHandler) {
            callCancelHandler((CancelHandler) obj, cause);
        } else if (notCompleted instanceof Segment) {
            callSegmentOnCancellation((Segment) obj, cause);
        }
        detachChildIfNonReusable();
        dispatchResume(this.resumeMode);
        return true;
    }

    public final void parentCancelled$kotlinx_coroutines_core(Throwable cause) {
        if (cancelLater(cause)) {
            return;
        }
        cancel(cause);
        detachChildIfNonReusable();
    }

    private final void callCancelHandlerSafely(Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    public final void callCancelHandler(CancelHandler handler, Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    private final void callSegmentOnCancellation(Segment<?> segment, Throwable cause) {
        int i = _decisionAndIndex$volatile$FU.get(this) & 536870911;
        if (i == 536870911) {
            throw new IllegalStateException("The index for Segment.onCancellation(..) is broken".toString());
        }
        try {
            segment.onCancellation(i, cause, getContext());
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> void callOnCancellation(Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation, Throwable cause, R value) {
        try {
            onCancellation.invoke(cause, value, getContext());
        } catch (Throwable th) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in resume onCancellation handler for " + this, th));
        }
    }

    public Throwable getContinuationCancellationCause(Job parent) {
        return parent.getCancellationException();
    }

    private final boolean trySuspend() {
        int i;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _decisionAndIndex$volatile$FU;
        do {
            i = atomicIntegerFieldUpdater.get(this);
            int i2 = i >> 29;
            if (i2 != 0) {
                if (i2 == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended".toString());
            }
        } while (!_decisionAndIndex$volatile$FU.compareAndSet(this, i, VisionAttribute.VISION_TYPE_CLASS_ENTITY + (536870911 & i)));
        return true;
    }

    private final boolean tryResume() {
        int i;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _decisionAndIndex$volatile$FU;
        do {
            i = atomicIntegerFieldUpdater.get(this);
            int i2 = i >> 29;
            if (i2 != 0) {
                if (i2 == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed".toString());
            }
        } while (!_decisionAndIndex$volatile$FU.compareAndSet(this, i, 1073741824 + (536870911 & i)));
        return true;
    }

    public final Object getResult() {
        Job job;
        boolean zIsReusable = isReusable();
        if (trySuspend()) {
            if (getParentHandle() == null) {
                installParentHandle();
            }
            if (zIsReusable) {
                releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            }
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (zIsReusable) {
            releaseClaimedReusableContinuation$kotlinx_coroutines_core();
        }
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (!(state$kotlinx_coroutines_core instanceof CompletedExceptionally)) {
            if (DispatchedTaskKt.isCancellableMode(this.resumeMode) && (job = (Job) getContext().get(Job.INSTANCE)) != null && !job.isActive()) {
                CancellationException cancellationException = job.getCancellationException();
                cancelCompletedResult$kotlinx_coroutines_core(state$kotlinx_coroutines_core, cancellationException);
                if (!DebugKt.getRECOVER_STACK_TRACES()) {
                    throw cancellationException;
                }
                CancellableContinuationImpl<T> cancellableContinuationImpl = this;
                if (cancellableContinuationImpl instanceof CoroutineStackFrame) {
                    throw StackTraceRecoveryKt.recoverFromStackFrame(cancellationException, cancellableContinuationImpl);
                }
                throw cancellationException;
            }
            return getSuccessfulResult$kotlinx_coroutines_core(state$kotlinx_coroutines_core);
        }
        Throwable th = ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        if (!DebugKt.getRECOVER_STACK_TRACES()) {
            throw th;
        }
        CancellableContinuationImpl<T> cancellableContinuationImpl2 = this;
        if (cancellableContinuationImpl2 instanceof CoroutineStackFrame) {
            throw StackTraceRecoveryKt.recoverFromStackFrame(th, cancellableContinuationImpl2);
        }
        throw th;
    }

    private final DisposableHandle installParentHandle() {
        Job job = (Job) getContext().get(Job.INSTANCE);
        if (job == null) {
            return null;
        }
        DisposableHandle disposableHandleInvokeOnCompletion$default = JobKt__JobKt.invokeOnCompletion$default(job, false, new ChildContinuation(this), 1, null);
        AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_parentHandle$volatile$FU, this, null, disposableHandleInvokeOnCompletion$default);
        return disposableHandleInvokeOnCompletion$default;
    }

    public final void releaseClaimedReusableContinuation$kotlinx_coroutines_core() {
        Throwable thTryReleaseClaimedContinuation$kotlinx_coroutines_core;
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        if (dispatchedContinuation == null || (thTryReleaseClaimedContinuation$kotlinx_coroutines_core = dispatchedContinuation.tryReleaseClaimedContinuation$kotlinx_coroutines_core(this)) == null) {
            return;
        }
        detachChild$kotlinx_coroutines_core();
        cancel(thTryReleaseClaimedContinuation$kotlinx_coroutines_core);
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(Object result) {
        resumeImpl$kotlinx_coroutines_core$default(this, CompletionStateKt.toState(result, this), this.resumeMode, null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resume$lambda$13$lambda$12(Function1 function1, Throwable th, Object obj, CoroutineContext coroutineContext) {
        function1.invoke(th);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resume(T value, final Function1<? super Throwable, Unit> onCancellation) {
        resumeImpl$kotlinx_coroutines_core(value, this.resumeMode, onCancellation != null ? new Function3() { // from class: kotlinx.coroutines.CancellableContinuationImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return CancellableContinuationImpl.resume$lambda$13$lambda$12(onCancellation, (Throwable) obj, obj2, (CoroutineContext) obj3);
            }
        } : null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public <R extends T> void resume(R value, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
        resumeImpl$kotlinx_coroutines_core(value, this.resumeMode, onCancellation);
    }

    @Override // kotlinx.coroutines.Waiter
    public void invokeOnCancellation(Segment<?> segment, int index) {
        int i;
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = _decisionAndIndex$volatile$FU;
        do {
            i = atomicIntegerFieldUpdater.get(this);
            if ((i & 536870911) != 536870911) {
                throw new IllegalStateException("invokeOnCancellation should be called at most once".toString());
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, ((i >> 29) << 29) + index));
        invokeOnCancellationImpl(segment);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void invokeOnCancellation(Function1<? super Throwable, Unit> handler) {
        CancellableContinuationKt.invokeOnCancellation(this, new CancelHandler.UserSupplied(handler));
    }

    public final void invokeOnCancellationInternal$kotlinx_coroutines_core(CancelHandler handler) {
        invokeOnCancellationImpl(handler);
    }

    private final void invokeOnCancellationImpl(Object handler) {
        if (DebugKt.getASSERTIONS_ENABLED() && !(handler instanceof CancelHandler) && !(handler instanceof Segment)) {
            throw new AssertionError();
        }
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$volatile$FU;
        while (true) {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof Active) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, obj, handler)) {
                    return;
                }
            } else if ((obj instanceof CancelHandler) || (obj instanceof Segment)) {
                multipleHandlersError(handler, obj);
            } else {
                boolean z = obj instanceof CompletedExceptionally;
                if (z) {
                    CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
                    if (!completedExceptionally.makeHandled()) {
                        multipleHandlersError(handler, obj);
                    }
                    if (obj instanceof CancelledContinuation) {
                        if (!z) {
                            completedExceptionally = null;
                        }
                        Throwable th = completedExceptionally != null ? completedExceptionally.cause : null;
                        if (handler instanceof CancelHandler) {
                            callCancelHandler((CancelHandler) handler, th);
                            return;
                        } else {
                            Intrinsics.checkNotNull(handler, "null cannot be cast to non-null type kotlinx.coroutines.internal.Segment<*>");
                            callSegmentOnCancellation((Segment) handler, th);
                            return;
                        }
                    }
                    return;
                }
                if (obj instanceof CompletedContinuation) {
                    CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                    if (completedContinuation.cancelHandler != null) {
                        multipleHandlersError(handler, obj);
                    }
                    if (handler instanceof Segment) {
                        return;
                    }
                    Intrinsics.checkNotNull(handler, "null cannot be cast to non-null type kotlinx.coroutines.CancelHandler");
                    CancelHandler cancelHandler = (CancelHandler) handler;
                    if (completedContinuation.getCancelled()) {
                        callCancelHandler(cancelHandler, completedContinuation.cancelCause);
                        return;
                    } else {
                        if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, obj, CompletedContinuation.copy$default(completedContinuation, null, cancelHandler, null, null, null, 29, null))) {
                            return;
                        }
                    }
                } else {
                    if (handler instanceof Segment) {
                        return;
                    }
                    Intrinsics.checkNotNull(handler, "null cannot be cast to non-null type kotlinx.coroutines.CancelHandler");
                    if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, obj, new CompletedContinuation(obj, (CancelHandler) handler, null, null, null, 28, null))) {
                        return;
                    }
                }
            }
        }
    }

    private final void multipleHandlersError(Object handler, Object state) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + handler + ", already has " + state).toString());
    }

    private final void dispatchResume(int mode) {
        if (tryResume()) {
            return;
        }
        DispatchedTaskKt.dispatch(this, mode);
    }

    private final <R> Object resumedState(NotCompleted state, R proposedUpdate, int resumeMode, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation, Object idempotent) {
        if (proposedUpdate instanceof CompletedExceptionally) {
            if (DebugKt.getASSERTIONS_ENABLED() && idempotent != null) {
                throw new AssertionError();
            }
            if (!DebugKt.getASSERTIONS_ENABLED() || onCancellation == null) {
                return proposedUpdate;
            }
            throw new AssertionError();
        }
        if ((DispatchedTaskKt.isCancellableMode(resumeMode) || idempotent != null) && (onCancellation != null || (state instanceof CancelHandler) || idempotent != null)) {
            return new CompletedContinuation(proposedUpdate, state instanceof CancelHandler ? (CancelHandler) state : null, onCancellation, idempotent, null, 16, null);
        }
        return proposedUpdate;
    }

    public static /* synthetic */ void resumeImpl$kotlinx_coroutines_core$default(CancellableContinuationImpl cancellableContinuationImpl, Object obj, int i, Function3 function3, int i2, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resumeImpl");
        }
        if ((i2 & 4) != 0) {
            function3 = null;
        }
        cancellableContinuationImpl.resumeImpl$kotlinx_coroutines_core(obj, i, function3);
    }

    public final <R> void resumeImpl$kotlinx_coroutines_core(R proposedUpdate, int resumeMode, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$volatile$FU;
        while (true) {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof NotCompleted) {
                CancellableContinuationImpl<T> cancellableContinuationImpl = this;
                R r = proposedUpdate;
                int i = resumeMode;
                Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> function3 = onCancellation;
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, cancellableContinuationImpl, obj, cancellableContinuationImpl.resumedState((NotCompleted) obj, r, i, function3, null))) {
                    cancellableContinuationImpl.detachChildIfNonReusable();
                    cancellableContinuationImpl.dispatchResume(i);
                    return;
                } else {
                    this = cancellableContinuationImpl;
                    proposedUpdate = r;
                    resumeMode = i;
                    onCancellation = function3;
                }
            } else {
                CancellableContinuationImpl<T> cancellableContinuationImpl2 = this;
                R r2 = proposedUpdate;
                Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> function4 = onCancellation;
                if (obj instanceof CancelledContinuation) {
                    CancelledContinuation cancelledContinuation = (CancelledContinuation) obj;
                    if (cancelledContinuation.makeResumed()) {
                        if (function4 != null) {
                            cancellableContinuationImpl2.callOnCancellation(function4, cancelledContinuation.cause, r2);
                            return;
                        }
                        return;
                    }
                }
                cancellableContinuationImpl2.alreadyResumedError(r2);
                throw new KotlinNothingValueException();
            }
        }
    }

    private final <R> Symbol tryResumeImpl(R proposedUpdate, Object idempotent, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = _state$volatile$FU;
        while (true) {
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof NotCompleted) {
                CancellableContinuationImpl<T> cancellableContinuationImpl = this;
                R r = proposedUpdate;
                Object obj2 = idempotent;
                Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> function3 = onCancellation;
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, cancellableContinuationImpl, obj, cancellableContinuationImpl.resumedState((NotCompleted) obj, r, this.resumeMode, function3, obj2))) {
                    cancellableContinuationImpl.detachChildIfNonReusable();
                    return CancellableContinuationImplKt.RESUME_TOKEN;
                }
                this = cancellableContinuationImpl;
                proposedUpdate = r;
                onCancellation = function3;
                idempotent = obj2;
            } else {
                R r2 = proposedUpdate;
                Object obj3 = idempotent;
                if ((obj instanceof CompletedContinuation) && obj3 != null) {
                    CompletedContinuation completedContinuation = (CompletedContinuation) obj;
                    if (completedContinuation.idempotentResume == obj3) {
                        if (!DebugKt.getASSERTIONS_ENABLED() || Intrinsics.areEqual(completedContinuation.result, r2)) {
                            return CancellableContinuationImplKt.RESUME_TOKEN;
                        }
                        throw new AssertionError();
                    }
                }
                return null;
            }
        }
    }

    private final Void alreadyResumedError(Object proposedUpdate) {
        throw new IllegalStateException(("Already resumed, but proposed with update " + proposedUpdate).toString());
    }

    private final void detachChildIfNonReusable() {
        if (isReusable()) {
            return;
        }
        detachChild$kotlinx_coroutines_core();
    }

    public final void detachChild$kotlinx_coroutines_core() {
        DisposableHandle parentHandle = getParentHandle();
        if (parentHandle == null) {
            return;
        }
        parentHandle.dispose();
        _parentHandle$volatile$FU.set(this, NonDisposableHandle.INSTANCE);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public Object tryResume(T value, Object idempotent) {
        return tryResumeImpl(value, idempotent, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public <R extends T> Object tryResume(R value, Object idempotent, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
        return tryResumeImpl(value, idempotent, onCancellation);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public Object tryResumeWithException(Throwable exception) {
        return tryResumeImpl(new CompletedExceptionally(exception, false, 2, null), null, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void completeResume(Object token) {
        if (DebugKt.getASSERTIONS_ENABLED() && token != CancellableContinuationImplKt.RESUME_TOKEN) {
            throw new AssertionError();
        }
        dispatchResume(this.resumeMode);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, T t) {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl$kotlinx_coroutines_core$default(this, t, (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null, 4, null);
    }

    @Override // kotlinx.coroutines.CancellableContinuation
    public void resumeUndispatchedWithException(CoroutineDispatcher coroutineDispatcher, Throwable th) {
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        resumeImpl$kotlinx_coroutines_core$default(this, new CompletedExceptionally(th, false, 2, null), (dispatchedContinuation != null ? dispatchedContinuation.dispatcher : null) == coroutineDispatcher ? 4 : this.resumeMode, null, 4, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.DispatchedTask
    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state instanceof CompletedContinuation ? (T) ((CompletedContinuation) state).result : state;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        Throwable exceptionalResult$kotlinx_coroutines_core = super.getExceptionalResult$kotlinx_coroutines_core(state);
        if (exceptionalResult$kotlinx_coroutines_core == null) {
            return null;
        }
        Continuation<T> continuation = this.delegate;
        return (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) ? StackTraceRecoveryKt.recoverFromStackFrame(exceptionalResult$kotlinx_coroutines_core, (CoroutineStackFrame) continuation) : exceptionalResult$kotlinx_coroutines_core;
    }

    public String toString() {
        return nameString() + CoreConstants.LEFT_PARENTHESIS_CHAR + DebugStringsKt.toDebugString(this.delegate) + "){" + getStateDebugRepresentation() + "}@" + DebugStringsKt.getHexAddress(this);
    }

    protected String nameString() {
        return "CancellableContinuation";
    }
}
