package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import ch.qos.logback.core.CoreConstants;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.internal.ThreadContextKt;

/* JADX INFO: compiled from: CoroutineContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0007\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0001H\u0002\u001a \u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0006H\u0002\u001a4\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\u0003\u001a\u00020\u00012\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\f0\u0010H\u0080\b¢\u0006\u0002\u0010\u0011\u001a8\u0010\u0012\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\f0\u0010H\u0080\b¢\u0006\u0002\u0010\u0015\u001a(\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0017*\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0003\u001a\u00020\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\u000eH\u0000\u001a\u0013\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0017*\u00020\u001aH\u0080\u0010\"\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u00018@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u000e\u0010\u001f\u001a\u00020\u001cX\u0082T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"newCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlinx/coroutines/CoroutineScope;", CoreConstants.CONTEXT_SCOPE_VALUE, "addedContext", "hasCopyableElements", "", "foldCopies", "originalContext", "appendContext", "isNewCoroutine", "withCoroutineContext", ExifInterface.GPS_DIRECTION_TRUE, "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withContinuationContext", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "updateUndispatchedCompletion", "Lkotlinx/coroutines/UndispatchedCoroutine;", "oldValue", "undispatchedCompletion", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "coroutineName", "", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "DEBUG_THREAD_NAME_SEPARATOR", "kotlinx-coroutines-core"}, k = 2, mv = {2, 1, 0}, xi = 48)
public final class CoroutineContextKt {
    private static final String DEBUG_THREAD_NAME_SEPARATOR = " @";

    public static final CoroutineContext newCoroutineContext(CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        CoroutineContext coroutineContextFoldCopies = foldCopies(coroutineScope.getCoroutineContext(), coroutineContext, true);
        CoroutineContext coroutineContextPlus = DebugKt.getDEBUG() ? coroutineContextFoldCopies.plus(new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet())) : coroutineContextFoldCopies;
        return (coroutineContextFoldCopies == Dispatchers.getDefault() || coroutineContextFoldCopies.get(ContinuationInterceptor.INSTANCE) != null) ? coroutineContextPlus : coroutineContextPlus.plus(Dispatchers.getDefault());
    }

    public static final CoroutineContext newCoroutineContext(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
        return !hasCopyableElements(coroutineContext2) ? coroutineContext.plus(coroutineContext2) : foldCopies(coroutineContext, coroutineContext2, false);
    }

    private static final boolean hasCopyableElements(CoroutineContext coroutineContext) {
        return ((Boolean) coroutineContext.fold(false, new Function2() { // from class: kotlinx.coroutines.CoroutineContextKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(CoroutineContextKt.hasCopyableElements$lambda$0(((Boolean) obj).booleanValue(), (CoroutineContext.Element) obj2));
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasCopyableElements$lambda$0(boolean z, CoroutineContext.Element element) {
        return z || (element instanceof CopyableThreadContextElement);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v6, types: [T, java.lang.Object] */
    private static final CoroutineContext foldCopies(CoroutineContext coroutineContext, CoroutineContext coroutineContext2, final boolean z) {
        boolean zHasCopyableElements = hasCopyableElements(coroutineContext);
        boolean zHasCopyableElements2 = hasCopyableElements(coroutineContext2);
        if (!zHasCopyableElements && !zHasCopyableElements2) {
            return coroutineContext.plus(coroutineContext2);
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = coroutineContext2;
        CoroutineContext coroutineContext3 = (CoroutineContext) coroutineContext.fold(EmptyCoroutineContext.INSTANCE, new Function2() { // from class: kotlinx.coroutines.CoroutineContextKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return CoroutineContextKt.foldCopies$lambda$1(objectRef, z, (CoroutineContext) obj, (CoroutineContext.Element) obj2);
            }
        });
        if (zHasCopyableElements2) {
            objectRef.element = ((CoroutineContext) objectRef.element).fold(EmptyCoroutineContext.INSTANCE, new Function2() { // from class: kotlinx.coroutines.CoroutineContextKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return CoroutineContextKt.foldCopies$lambda$2((CoroutineContext) obj, (CoroutineContext.Element) obj2);
                }
            });
        }
        return coroutineContext3.plus((CoroutineContext) objectRef.element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, kotlin.coroutines.CoroutineContext] */
    public static final CoroutineContext foldCopies$lambda$1(Ref.ObjectRef objectRef, boolean z, CoroutineContext coroutineContext, CoroutineContext.Element element) {
        if (!(element instanceof CopyableThreadContextElement)) {
            return coroutineContext.plus(element);
        }
        CoroutineContext.Element element2 = ((CoroutineContext) objectRef.element).get(element.getKey());
        if (element2 == null) {
            return coroutineContext.plus(z ? ((CopyableThreadContextElement) element).copyForChild() : (CopyableThreadContextElement) element);
        }
        objectRef.element = ((CoroutineContext) objectRef.element).minusKey(element.getKey());
        return coroutineContext.plus(((CopyableThreadContextElement) element).mergeForChild(element2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineContext foldCopies$lambda$2(CoroutineContext coroutineContext, CoroutineContext.Element element) {
        if (element instanceof CopyableThreadContextElement) {
            return coroutineContext.plus(((CopyableThreadContextElement) element).copyForChild());
        }
        return coroutineContext.plus(element);
    }

    public static final <T> T withCoroutineContext(CoroutineContext coroutineContext, Object obj, Function0<? extends T> function0) {
        Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, obj);
        try {
            return function0.invoke();
        } finally {
            ThreadContextKt.restoreThreadContext(coroutineContext, objUpdateThreadContext);
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001e A[DONT_GENERATE] */
    public static final <T> T withContinuationContext(Continuation<?> continuation, Object obj, Function0<? extends T> function0) {
        CoroutineContext context = continuation.getContext();
        Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
        UndispatchedCoroutine<?> undispatchedCoroutineUpdateUndispatchedCompletion = objUpdateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS ? updateUndispatchedCompletion(continuation, context, objUpdateThreadContext) : null;
        try {
            return function0.invoke();
        } finally {
            if (undispatchedCoroutineUpdateUndispatchedCompletion == null || undispatchedCoroutineUpdateUndispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, objUpdateThreadContext);
            }
        }
    }

    public static final UndispatchedCoroutine<?> updateUndispatchedCompletion(Continuation<?> continuation, CoroutineContext coroutineContext, Object obj) {
        if (!(continuation instanceof CoroutineStackFrame) || coroutineContext.get(UndispatchedMarker.INSTANCE) == null) {
            return null;
        }
        UndispatchedCoroutine<?> undispatchedCoroutineUndispatchedCompletion = undispatchedCompletion((CoroutineStackFrame) continuation);
        if (undispatchedCoroutineUndispatchedCompletion != null) {
            undispatchedCoroutineUndispatchedCompletion.saveThreadContext(coroutineContext, obj);
        }
        return undispatchedCoroutineUndispatchedCompletion;
    }

    public static final UndispatchedCoroutine<?> undispatchedCompletion(CoroutineStackFrame coroutineStackFrame) {
        while (!(coroutineStackFrame instanceof DispatchedCoroutine) && (coroutineStackFrame = coroutineStackFrame.getCallerFrame()) != null) {
            if (coroutineStackFrame instanceof UndispatchedCoroutine) {
                return (UndispatchedCoroutine) coroutineStackFrame;
            }
        }
        return null;
    }

    public static final String getCoroutineName(CoroutineContext coroutineContext) {
        CoroutineId coroutineId;
        String name;
        if (!DebugKt.getDEBUG() || (coroutineId = (CoroutineId) coroutineContext.get(CoroutineId.INSTANCE)) == null) {
            return null;
        }
        CoroutineName coroutineName = (CoroutineName) coroutineContext.get(CoroutineName.INSTANCE);
        if (coroutineName == null || (name = coroutineName.getName()) == null) {
            name = "coroutine";
        }
        return name + '#' + coroutineId.getId();
    }
}
