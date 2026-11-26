package kotlinx.coroutines.intrinsics;

import androidx.exifinterface.media.ExifInterface;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DispatchException;
import kotlinx.coroutines.JobSupportKt;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;

/* JADX INFO: compiled from: Undispatched.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aO\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00042\u0006\u0010\u0007\u001a\u0002H\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0000¢\u0006\u0002\u0010\t\u001aV\u0010\n\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0006\u0010\u0007\u001a\u0002H\u00022'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\b\rH\u0000¢\u0006\u0002\u0010\u000e\u001aV\u0010\u000f\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0006\u0010\u0007\u001a\u0002H\u00022'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\b\rH\u0000¢\u0006\u0002\u0010\u000e\u001a^\u0010\u0010\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u0002H\u00022'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\b\rH\u0002¢\u0006\u0002\u0010\u0013\u001a\u0018\u0010\u0014\u001a\u00020\u0012*\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a\u0018\u0010\u0017\u001a\u00020\u0018*\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002¨\u0006\u001b"}, d2 = {"startCoroutineUndispatched", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "receiver", "completion", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "startUndispatchedOrReturn", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "block", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/internal/ScopeCoroutine;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "startUndispatchedOrReturnIgnoreTimeout", "startUndspatched", "alwaysRethrow", "", "(Lkotlinx/coroutines/internal/ScopeCoroutine;ZLjava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "notOwnTimeout", "cause", "", "dispatchExceptionAndMakeCompleting", "", "e", "Lkotlinx/coroutines/DispatchException;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class UndispatchedKt {
    public static final <T, R> Object startUndispatchedOrReturn(ScopeCoroutine<? super T> scopeCoroutine, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2) {
        return startUndspatched(scopeCoroutine, true, r, function2);
    }

    public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(ScopeCoroutine<? super T> scopeCoroutine, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2) {
        return startUndspatched(scopeCoroutine, false, r, function2);
    }

    private static final <T, R> Object startUndspatched(ScopeCoroutine<? super T> scopeCoroutine, boolean z, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2) throws Throwable {
        Object completedExceptionally;
        Object objMakeCompletingOnce$kotlinx_coroutines_core;
        try {
            completedExceptionally = !(function2 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function2, r, scopeCoroutine) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(r, scopeCoroutine);
        } catch (DispatchException e) {
            dispatchExceptionAndMakeCompleting(scopeCoroutine, e);
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            completedExceptionally = new CompletedExceptionally(th, false, 2, null);
        }
        if (completedExceptionally != IntrinsicsKt.getCOROUTINE_SUSPENDED() && (objMakeCompletingOnce$kotlinx_coroutines_core = scopeCoroutine.makeCompletingOnce$kotlinx_coroutines_core(completedExceptionally)) != JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            scopeCoroutine.afterCompletionUndispatched();
            if (objMakeCompletingOnce$kotlinx_coroutines_core instanceof CompletedExceptionally) {
                if (z || notOwnTimeout(scopeCoroutine, ((CompletedExceptionally) objMakeCompletingOnce$kotlinx_coroutines_core).cause)) {
                    Throwable th2 = ((CompletedExceptionally) objMakeCompletingOnce$kotlinx_coroutines_core).cause;
                    Continuation<? super T> continuation = scopeCoroutine.uCont;
                    if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
                        throw StackTraceRecoveryKt.recoverFromStackFrame(th2, (CoroutineStackFrame) continuation);
                    }
                    throw th2;
                }
                if (!(completedExceptionally instanceof CompletedExceptionally)) {
                    return completedExceptionally;
                }
                Throwable th3 = ((CompletedExceptionally) completedExceptionally).cause;
                Continuation<? super T> continuation2 = scopeCoroutine.uCont;
                if (DebugKt.getRECOVER_STACK_TRACES() && (continuation2 instanceof CoroutineStackFrame)) {
                    throw StackTraceRecoveryKt.recoverFromStackFrame(th3, (CoroutineStackFrame) continuation2);
                }
                throw th3;
            }
            return JobSupportKt.unboxState(objMakeCompletingOnce$kotlinx_coroutines_core);
        }
        return IntrinsicsKt.getCOROUTINE_SUSPENDED();
    }

    private static final boolean notOwnTimeout(ScopeCoroutine<?> scopeCoroutine, Throwable th) {
        return ((th instanceof TimeoutCancellationException) && ((TimeoutCancellationException) th).coroutine == scopeCoroutine) ? false : true;
    }

    private static final Void dispatchExceptionAndMakeCompleting(ScopeCoroutine<?> scopeCoroutine, DispatchException dispatchException) throws Throwable {
        scopeCoroutine.makeCompleting$kotlinx_coroutines_core(new CompletedExceptionally(dispatchException.getCause(), false, 2, null));
        Throwable cause = dispatchException.getCause();
        Continuation<?> continuation = scopeCoroutine.uCont;
        if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
            throw StackTraceRecoveryKt.recoverFromStackFrame(cause, (CoroutineStackFrame) continuation);
        }
        throw cause;
    }

    public static final <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> function2, R r, Continuation<? super T> continuation) {
        Continuation continuationProbeCoroutineCreated = DebugProbesKt.probeCoroutineCreated(continuation);
        try {
            CoroutineContext context = continuationProbeCoroutineCreated.getContext();
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, null);
            try {
                DebugProbesKt.probeCoroutineResumed(continuationProbeCoroutineCreated);
                Object objWrapWithContinuationImpl = !(function2 instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(function2, r, continuationProbeCoroutineCreated) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(r, continuationProbeCoroutineCreated);
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                if (objWrapWithContinuationImpl != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    Result.Companion companion = Result.INSTANCE;
                    continuationProbeCoroutineCreated.resumeWith(Result.m2532constructorimpl(objWrapWithContinuationImpl));
                }
            } catch (Throwable th) {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (th instanceof DispatchException) {
                th = ((DispatchException) th).getCause();
            }
            Result.Companion companion2 = Result.INSTANCE;
            continuationProbeCoroutineCreated.resumeWith(Result.m2532constructorimpl(ResultKt.createFailure(th)));
        }
    }
}
